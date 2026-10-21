package com.orion.visor.module.container.enums;

import com.orion.visor.framework.common.exception.ErrorCode;

/**
 * Docker 容器管理错误码常量
 *
 * @author ops-platform
 */
public interface ContainerErrorCodeConstants {

    // ========== Docker主机 1_023_001_000 ==========
    ErrorCode DOCKER_HOST_NOT_EXISTS   = new ErrorCode(1_023_001_000, "Docker主机配置不存在");
    ErrorCode DOCKER_HOST_DUPLICATE    = new ErrorCode(1_023_001_001, "Docker主机地址已存在");
    ErrorCode DOCKER_HOST_OFFLINE      = new ErrorCode(1_023_001_002, "Docker主机离线");
    ErrorCode DOCKER_CONNECT_FAILED    = new ErrorCode(1_023_001_003, "Docker连接失败");

    // ========== 容器操作 1_023_002_000 ==========
    ErrorCode CONTAINER_NOT_FOUND      = new ErrorCode(1_023_002_000, "容器不存在");
    ErrorCode CONTAINER_ACTION_INVALID = new ErrorCode(1_023_002_001, "不支持的容器操作");
    ErrorCode CONTAINER_ACTION_FAILED  = new ErrorCode(1_023_002_002, "容器操作执行失败");
}
