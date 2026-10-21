-- =============================================
-- P3: 密钥保险箱
-- 模块: module-vault
-- 说明: 应用层密钥/Key/证书管理，AES-256加密存储
-- =============================================

-- 密钥分类表
CREATE TABLE IF NOT EXISTS `ops_vault_category` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name`        VARCHAR(64)  NOT NULL COMMENT '分类名称',
    `code`        VARCHAR(32)  NOT NULL COMMENT '分类编码 (api_key/db_password/cert/ssh_key/token)',
    `description` VARCHAR(256) DEFAULT NULL COMMENT '描述',
    `sort`        INT          NOT NULL DEFAULT 0 COMMENT '排序',
    `creator`     VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='密钥分类';

-- 密钥条目表
CREATE TABLE IF NOT EXISTS `ops_vault_secret` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '密钥ID',
    `name`            VARCHAR(128) NOT NULL COMMENT '密钥名称',
    `category_code`   VARCHAR(32)  NOT NULL COMMENT '分类编码',
    `product_line_id` BIGINT       DEFAULT NULL COMMENT '所属产品线ID',
    `project_id`      BIGINT       DEFAULT NULL COMMENT '所属项目ID',
    `host_id`         BIGINT       DEFAULT NULL COMMENT '关联主机ID (可选)',
    `secret_type`     VARCHAR(32)  NOT NULL DEFAULT 'text' COMMENT '类型 (text/json/file)',
    `encrypted_value` TEXT         NOT NULL COMMENT '加密后的密钥值 (AES-256)',
    `encryption_key`  VARCHAR(128) DEFAULT NULL COMMENT '加密密钥标识',
    `description`     VARCHAR(512) DEFAULT NULL COMMENT '描述',
    `status`          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 1有效 0失效',
    `expire_time`     DATETIME     DEFAULT NULL COMMENT '过期时间',
    `last_rotated`    DATETIME     DEFAULT NULL COMMENT '最后轮换时间',
    `rotation_days`   INT          NOT NULL DEFAULT 0 COMMENT '轮换周期(天), 0=不自动轮换',
    `creator`         VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_category` (`category_code`),
    KEY `idx_product_line` (`product_line_id`),
    KEY `idx_project` (`project_id`),
    KEY `idx_host` (`host_id`),
    KEY `idx_status` (`status`),
    KEY `idx_expire` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='密钥条目';

-- 密钥访问日志表
CREATE TABLE IF NOT EXISTS `ops_vault_access_log` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `secret_id`   BIGINT       NOT NULL COMMENT '密钥ID',
    `user_id`     BIGINT       NOT NULL COMMENT '操作用户ID',
    `action`      VARCHAR(32)  NOT NULL COMMENT '操作 (view/reveal/rotate/delete/export)',
    `ip_address`  VARCHAR(64)  DEFAULT NULL COMMENT 'IP地址',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    PRIMARY KEY (`id`),
    KEY `idx_secret` (`secret_id`),
    KEY `idx_user` (`user_id`),
    KEY `idx_action` (`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='密钥访问日志';
