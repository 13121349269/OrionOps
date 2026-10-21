package com.orion.visor.module.container.service;

import com.orion.visor.module.container.model.req.ContainerActionReq;
import com.orion.visor.module.container.model.req.DockerHostCreateReq;
import com.orion.visor.module.container.model.req.DockerHostPageReq;
import com.orion.visor.module.container.model.req.DockerHostUpdateReq;
import com.orion.visor.module.container.model.resp.ContainerResp;
import com.orion.visor.module.container.model.resp.DockerHostResp;

import java.util.List;

/**
 * Docker 容器管理 Service
 *
 * @author ops-platform
 */
public interface DockerService {

    /**
     * 创建 Docker 主机配置
     */
    Long createDockerHost(DockerHostCreateReq req);

    /**
     * 更新 Docker 主机配置
     */
    void updateDockerHost(DockerHostUpdateReq req);

    /**
     * 删除 Docker 主机配置
     */
    void deleteDockerHost(Long id);

    /**
     * 测试 Docker 主机连接
     */
    boolean testConnection(Long id);

    /**
     * 分页查询 Docker 主机
     */
    List<DockerHostResp> getDockerHostPage(DockerHostPageReq req);

    /**
     * 获取 Docker 主机详情
     */
    DockerHostResp getDockerHost(Long id);

    /**
     * 列出容器
     */
    List<ContainerResp> listContainers(Long dockerHostId, boolean all);

    /**
     * 容器操作 (start/stop/restart/pause/unpause/remove)
     */
    void containerAction(ContainerActionReq req);

    /**
     * 获取容器日志 (最近 N 行)
     */
    String getContainerLogs(Long dockerHostId, String containerId, int tailLines);
}
