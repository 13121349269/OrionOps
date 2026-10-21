-- =============================================
-- P4: Docker 容器管理
-- 模块: module-container
-- 说明: Docker 主机配置 + 容器操作记录
-- =============================================

-- Docker 主机配置表
CREATE TABLE IF NOT EXISTS `ops_docker_host` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         VARCHAR(128) NOT NULL COMMENT '主机名称',
    `host_id`      BIGINT       DEFAULT NULL COMMENT '关联 Orion-Visor 主机ID',
    `docker_host`  VARCHAR(256) NOT NULL COMMENT 'Docker Engine 地址 (tcp://ip:2375 或 unix:///var/run/docker.sock)',
    `api_version`  VARCHAR(32)  DEFAULT NULL COMMENT 'API版本',
    `tls_verify`   TINYINT      NOT NULL DEFAULT 0 COMMENT '是否TLS验证 1是 0否',
    `cert_path`    VARCHAR(512) DEFAULT NULL COMMENT '证书路径',
    `status`       TINYINT      NOT NULL DEFAULT 1 COMMENT '连接状态 1在线 0离线',
    `last_check`   DATETIME     DEFAULT NULL COMMENT '最后检测时间',
    `creator`      VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_docker_host` (`docker_host`, `deleted`),
    KEY `idx_host_id` (`host_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Docker主机配置';

-- Docker 容器操作记录表
CREATE TABLE IF NOT EXISTS `ops_docker_action_log` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `docker_host_id` BIGINT    NOT NULL COMMENT 'Docker主机ID',
    `container_id` VARCHAR(128) NOT NULL COMMENT '容器ID',
    `container_name` VARCHAR(128) DEFAULT NULL COMMENT '容器名称',
    `action`      VARCHAR(32)  NOT NULL COMMENT '操作 (start/stop/restart/pause/unpause/remove/exec)',
    `params`      TEXT         DEFAULT NULL COMMENT '操作参数JSON',
    `status`      VARCHAR(16)  NOT NULL DEFAULT 'success' COMMENT '执行状态 (success/failed)',
    `message`     VARCHAR(512) DEFAULT NULL COMMENT '执行消息',
    `user_id`     BIGINT       DEFAULT NULL COMMENT '操作用户ID',
    `creator`     VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_docker_host` (`docker_host_id`),
    KEY `idx_container` (`container_id`),
    KEY `idx_action` (`action`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Docker容器操作记录';
