package com.orion.visor.module.container.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.container.dal.dataobject.DockerHostDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.orion.visor.module.container.enums.ContainerErrorCodeConstants.*;

/**
 * Docker 客户端工厂
 *
 * <p>管理多个 Docker Engine 连接，按 hostId 缓存 DockerClient 实例。
 * 基于 docker-java SDK，支持 TCP 和 Unix Socket 连接。</p>
 *
 * @author ops-platform
 */
@Slf4j
@Component
public class DockerClientFactory {

    private final ConcurrentMap<String, DockerClient> clientCache = new ConcurrentHashMap<>();

    /**
     * 获取或创建 DockerClient
     */
    public DockerClient getOrCreate(DockerHostDO host) {
        String key = host.getId() + ":" + host.getDockerHost();
        return clientCache.computeIfAbsent(key, k -> createClient(host));
    }

    /**
     * 创建 DockerClient
     */
    private DockerClient createClient(DockerHostDO host) {
        try {
            DefaultDockerClientConfig.Builder builder = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(host.getDockerHost());

            if (host.getTlsVerify() != null && host.getTlsVerify() == 1) {
                builder.withDockerTlsVerify(true);
                if (host.getCertPath() != null) {
                    builder.withDockerCertPath(host.getCertPath());
                }
            }

            DockerClientConfig config = builder.build();
            DockerClient client = DockerClientBuilder.getInstance(config).build();

            // 测试连接
            InfoPingCmd ping = client.pingCmd();
            ping.exec();

            log.info("[createClient] Docker连接成功, host={}, dockerHost={}", host.getName(), host.getDockerHost());
            return client;
        } catch (Exception e) {
            log.error("[createClient] Docker连接失败, host={}, dockerHost={}", host.getName(), host.getDockerHost(), e);
            throw ServiceException.of(DOCKER_CONNECT_FAILED);
        }
    }

    /**
     * 移除客户端缓存并关闭连接
     */
    public void remove(Long hostId) {
        String prefix = hostId + ":";
        // 先收集 key 再批量删除，避免并发修改
        java.util.List<String> keysToRemove = new java.util.ArrayList<>();
        clientCache.keySet().stream()
                .filter(k -> k.startsWith(prefix))
                .forEach(keysToRemove::add);
        for (String key : keysToRemove) {
            DockerClient client = clientCache.remove(key);
            if (client != null) {
                try {
                    client.close();
                } catch (Exception e) {
                    log.warn("[remove] DockerClient关闭失败, key={}", key, e);
                }
            }
        }
    }

    /**
     * 测试连接
     */
    public boolean testConnection(DockerHostDO host) {
        try {
            DockerClient client = getOrCreate(host);
            client.pingCmd().exec();
            return true;
        } catch (Exception e) {
            log.warn("[testConnection] Docker连接测试失败, host={}", host.getName());
            return false;
        }
    }
}
