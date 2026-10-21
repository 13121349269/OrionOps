package com.orion.visor.module.container.service.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.container.convert.DockerHostConvert;
import com.orion.visor.module.container.dal.dataobject.DockerActionLogDO;
import com.orion.visor.module.container.dal.dataobject.DockerHostDO;
import com.orion.visor.module.container.dal.mysql.DockerActionLogMapper;
import com.orion.visor.module.container.dal.mysql.DockerHostMapper;
import com.orion.visor.module.container.docker.DockerClientFactory;
import com.orion.visor.module.container.enums.DockerActionEnum;
import com.orion.visor.module.container.model.req.ContainerActionReq;
import com.orion.visor.module.container.model.req.DockerHostCreateReq;
import com.orion.visor.module.container.model.req.DockerHostPageReq;
import com.orion.visor.module.container.model.req.DockerHostUpdateReq;
import com.orion.visor.module.container.model.resp.ContainerResp;
import com.orion.visor.module.container.model.resp.DockerHostResp;
import com.orion.visor.module.container.service.DockerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.orion.visor.module.container.enums.ContainerErrorCodeConstants.*;

/**
 * Docker 容器管理 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class DockerServiceImpl implements DockerService {

    @Resource
    private DockerHostMapper dockerHostMapper;

    @Resource
    private DockerActionLogMapper dockerActionLogMapper;

    @Resource
    private DockerClientFactory dockerClientFactory;

    @Override
    public Long createDockerHost(DockerHostCreateReq req) {
        // 1. 校验地址唯一
        DockerHostDO existing = dockerHostMapper.selectByDockerHost(req.getDockerHost());
        if (existing != null) {
            throw ServiceException.of(DOCKER_HOST_DUPLICATE);
        }
        // 2. 转换并插入
        DockerHostDO entity = DockerHostConvert.INSTANCE.convert(req);
        if (entity.getTlsVerify() == null) {
            entity.setTlsVerify(0);
        }
        entity.setStatus(0); // 默认离线，待连接测试
        dockerHostMapper.insert(entity);
        log.info("[createDockerHost] Docker主机创建成功, id={}, name={}", entity.getId(), entity.getName());
        return entity.getId();
    }

    @Override
    public void updateDockerHost(DockerHostUpdateReq req) {
        DockerHostDO existing = validateExists(req.getId());
        DockerHostDO update = new DockerHostDO();
        update.setId(req.getId());
        DockerHostConvert.INSTANCE.convert(req, update);
        dockerHostMapper.updateById(update);
        // 清除客户端缓存
        dockerClientFactory.remove(req.getId());
        log.info("[updateDockerHost] Docker主机更新成功, id={}", req.getId());
    }

    @Override
    public void deleteDockerHost(Long id) {
        validateExists(id);
        dockerHostMapper.deleteById(id);
        dockerClientFactory.remove(id);
        log.info("[deleteDockerHost] Docker主机删除成功, id={}", id);
    }

    @Override
    public boolean testConnection(Long id) {
        DockerHostDO host = validateExists(id);
        boolean connected = dockerClientFactory.testConnection(host);
        // 更新状态
        DockerHostDO update = new DockerHostDO();
        update.setId(id);
        update.setStatus(connected ? 1 : 0);
        update.setLastCheck(LocalDateTime.now());
        dockerHostMapper.updateById(update);
        return connected;
    }

    @Override
    public List<DockerHostResp> getDockerHostPage(DockerHostPageReq req) {
        List<DockerHostDO> list = dockerHostMapper.selectPage(req);
        return DockerHostConvert.INSTANCE.convertList(list);
    }

    @Override
    public DockerHostResp getDockerHost(Long id) {
        DockerHostDO entity = validateExists(id);
        return DockerHostConvert.INSTANCE.convert(entity);
    }

    @Override
    public List<ContainerResp> listContainers(Long dockerHostId, boolean all) {
        DockerHostDO host = validateHostOnline(dockerHostId);
        DockerClient client = dockerClientFactory.getOrCreate(host);
        List<Container> containers = client.listContainersCmd()
                .withShowAll(all)
                .exec();
        List<ContainerResp> result = new ArrayList<>();
        for (Container c : containers) {
            ContainerResp resp = new ContainerResp();
            resp.setId(c.getId());
            resp.setNames(Arrays.asList(c.getNames()));
            resp.setImage(c.getImage());
            resp.setState(c.getState());
            resp.setStatus(c.getStatus());
            resp.setCreated(c.getCreated());
            // 端口映射转换
            if (c.getPorts() != null && c.getPorts().length > 0) {
                List<ContainerResp.PortBinding> ports = new ArrayList<>();
                for (com.github.dockerjava.api.model.Container.Port p : c.getPorts()) {
                    ContainerResp.PortBinding pb = new ContainerResp.PortBinding();
                    pb.setPrivatePort(p.getPrivatePort());
                    pb.setPublicPort(p.getPublicPort());
                    pb.setType(p.getType());
                    pb.setIp(p.getIp());
                    ports.add(pb);
                }
                resp.setPorts(ports);
            }
            result.add(resp);
        }
        return result;
    }

    @Override
    public void containerAction(ContainerActionReq req) {
        DockerHostDO host = validateHostOnline(req.getDockerHostId());
        DockerClient client = dockerClientFactory.getOrCreate(host);

        // 校验操作类型
        if (!DockerActionEnum.isValid(req.getAction())) {
            throw ServiceException.of(CONTAINER_ACTION_INVALID);
        }
        DockerActionEnum action = DockerActionEnum.valueOf(req.getAction().toUpperCase());

        String actionStatus = "success";
        String message = null;
        try {
            switch (action) {
                case START:
                    client.startContainerCmd(req.getContainerId()).exec();
                    break;
                case STOP:
                    client.stopContainerCmd(req.getContainerId()).exec();
                    break;
                case RESTART:
                    client.restartContainerCmd(req.getContainerId()).exec();
                    break;
                case PAUSE:
                    client.pauseContainerCmd(req.getContainerId()).exec();
                    break;
                case UNPAUSE:
                    client.unpauseContainerCmd(req.getContainerId()).exec();
                    break;
                case REMOVE:
                    client.removeContainerCmd(req.getContainerId()).withForce(true).exec();
                    break;
                default:
                    throw ServiceException.of(CONTAINER_ACTION_INVALID);
            }
            log.info("[containerAction] 容器操作成功, action={}, containerId={}", req.getAction(), req.getContainerId());
        } catch (Exception e) {
            actionStatus = "failed";
            message = e.getMessage();
            log.error("[containerAction] 容器操作失败, action={}, containerId={}", req.getAction(), req.getContainerId(), e);
            throw ServiceException.of(CONTAINER_ACTION_FAILED);
        } finally {
            // 记录操作日志
            recordActionLog(host, req, actionStatus, message);
        }
    }

    @Override
    public String getContainerLogs(Long dockerHostId, String containerId, int tailLines) {
        DockerHostDO host = validateHostOnline(dockerHostId);
        DockerClient client = dockerClientFactory.getOrCreate(host);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        LogContainerCmd logCmd = client.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withTail(tailLines > 0 ? String.valueOf(tailLines) : "100")
                .withFollowStream(false);
        logCmd.exec(outputStream);

        return outputStream.toString();
    }

    // ==================== 内部方法 ====================

    private DockerHostDO validateExists(Long id) {
        DockerHostDO entity = dockerHostMapper.selectById(id);
        if (entity == null) {
            throw ServiceException.of(DOCKER_HOST_NOT_EXISTS);
        }
        return entity;
    }

    private DockerHostDO validateHostOnline(Long dockerHostId) {
        DockerHostDO host = validateExists(dockerHostId);
        if (host.getStatus() == null || host.getStatus() == 0) {
            throw ServiceException.of(DOCKER_HOST_OFFLINE);
        }
        return host;
    }

    private void recordActionLog(DockerHostDO host, ContainerActionReq req, String status, String message) {
        try {
            DockerActionLogDO logDO = new DockerActionLogDO();
            logDO.setDockerHostId(host.getId());
            logDO.setContainerId(req.getContainerId());
            logDO.setAction(req.getAction());
            logDO.setParams(req.getParams());
            logDO.setStatus(status);
            logDO.setMessage(message);
            dockerActionLogMapper.insert(logDO);
        } catch (Exception e) {
            log.error("[recordActionLog] 操作日志记录失败", e);
        }
    }
}
