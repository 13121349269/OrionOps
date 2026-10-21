-- =============================================
-- P2: 资产扩展 - 主机关联产品线/项目
-- 模块: module-asset (扩展原生资产模块)
-- 说明: 在原生 ops_host 表上增加产品线/项目维度关联
-- =============================================

-- 主机-产品线-项目关联表
-- 说明: 不修改原生 ops_host 表结构，而是通过关联表建立维度映射
CREATE TABLE IF NOT EXISTS `ops_host_product` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `host_id`         BIGINT       NOT NULL COMMENT '主机ID (关联 ops_host.id)',
    `product_line_id` BIGINT       DEFAULT NULL COMMENT '产品线ID',
    `project_id`      BIGINT       DEFAULT NULL COMMENT '项目ID',
    `role`            VARCHAR(32)  NOT NULL DEFAULT 'app' COMMENT '主机角色 (web/app/db/cache/mq/other)',
    `env`             VARCHAR(32)  NOT NULL DEFAULT 'prod' COMMENT '环境 (dev/test/staging/prod)',
    `creator`         VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_host` (`host_id`, `deleted`),
    KEY `idx_product_line` (`product_line_id`),
    KEY `idx_project` (`project_id`),
    KEY `idx_env` (`env`),
    KEY `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='主机-产品线-项目关联';

-- 资产分组视图 (按产品线聚合)
-- 说明: 通过视图实现资产树按业务维度展示，不额外建表
CREATE OR REPLACE VIEW `ops_v_asset_by_product_line` AS
SELECT
    h.id AS host_id,
    h.host_name,
    h.host_address,
    h.status AS host_status,
    h.tag_id,
    hp.product_line_id,
    pl.name AS product_line_name,
    pl.code AS product_line_code,
    hp.project_id,
    pj.name AS project_name,
    pj.code AS project_code,
    hp.role AS host_role,
    hp.env AS host_env
FROM ops_host h
LEFT JOIN ops_host_product hp ON h.id = hp.host_id AND hp.deleted = b'0'
LEFT JOIN ops_product_line pl ON hp.product_line_id = pl.id AND pl.deleted = b'0'
LEFT JOIN ops_project pj ON hp.project_id = pj.id AND pj.deleted = b'0'
WHERE h.deleted = b'0';
