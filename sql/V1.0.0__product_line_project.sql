-- =============================================
-- P1: 产品线 + 项目管理
-- 模块: module-product
-- 说明: 基于 Orion-Visor 扩展，遵循原有表命名规范 (ops_ 前缀)
-- =============================================

-- 产品线表
CREATE TABLE IF NOT EXISTS `ops_product_line` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '产品线ID',
    `name`            VARCHAR(128) NOT NULL COMMENT '产品线名称',
    `code`            VARCHAR(64)  NOT NULL COMMENT '产品线编码',
    `description`     VARCHAR(512) DEFAULT NULL COMMENT '描述',
    `status`          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 1启用 0停用',
    `owner_id`        BIGINT       DEFAULT NULL COMMENT '负责人用户ID',
    `sort`            INT          NOT NULL DEFAULT 0 COMMENT '排序',
    `creator`         VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `updater`         VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`, `deleted`),
    KEY `idx_status` (`status`),
    KEY `idx_owner` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品线';

-- 项目表
CREATE TABLE IF NOT EXISTS `ops_project` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `product_line_id` BIGINT       NOT NULL COMMENT '所属产品线ID',
    `name`            VARCHAR(128) NOT NULL COMMENT '项目名称',
    `code`            VARCHAR(64)  NOT NULL COMMENT '项目编码',
    `description`     VARCHAR(512) DEFAULT NULL COMMENT '描述',
    `status`          TINYINT      NOT NULL DEFAULT 1 COMMENT '状态 1启用 0停用',
    `owner_id`        BIGINT       DEFAULT NULL COMMENT '负责人用户ID',
    `repo_url`        VARCHAR(512) DEFAULT NULL COMMENT '代码仓库地址',
    `tech_stack`      VARCHAR(128) DEFAULT NULL COMMENT '技术栈 (java/go/python/node/...)',
    `sort`            INT          NOT NULL DEFAULT 0 COMMENT '排序',
    `creator`         VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `updater`         VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_line_code` (`product_line_id`, `code`, `deleted`),
    KEY `idx_product_line` (`product_line_id`),
    KEY `idx_status` (`status`),
    KEY `idx_owner` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目';

-- 产品线-用户授权表 (RBAC 扩展，将用户关联到产品线维度)
CREATE TABLE IF NOT EXISTS `ops_product_line_user` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_line_id` BIGINT       NOT NULL COMMENT '产品线ID',
    `user_id`         BIGINT       NOT NULL COMMENT '用户ID',
    `role_id`         BIGINT       NOT NULL COMMENT '角色ID (复用现有RBAC角色体系)',
    `creator`         VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_line_user_role` (`product_line_id`, `user_id`, `role_id`, `deleted`),
    KEY `idx_user` (`user_id`),
    KEY `idx_product_line` (`product_line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品线用户授权';
