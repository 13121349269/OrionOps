-- =============================================
-- P5: Java JVM 诊断
-- 模块: module-javaagent
-- 说明: Java应用JMX连接配置 + JVM指标采集
-- =============================================

-- Java应用监控配置表
CREATE TABLE IF NOT EXISTS `ops_java_app` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         VARCHAR(128) NOT NULL COMMENT '应用名称',
    `host_id`      BIGINT       DEFAULT NULL COMMENT '关联Orion-Visor主机ID',
    `product_line_id` BIGINT    DEFAULT NULL COMMENT '产品线ID',
    `project_id`   BIGINT       DEFAULT NULL COMMENT '项目ID',
    `jmx_host`     VARCHAR(128) NOT NULL COMMENT 'JMX连接地址',
    `jmx_port`     INT          NOT NULL COMMENT 'JMX端口',
    `jmx_username`  VARCHAR(64)  DEFAULT NULL COMMENT 'JMX用户名',
    `jmx_password`  VARCHAR(256) DEFAULT NULL COMMENT 'JMX密码 (加密存储)',
    `pid`          INT          DEFAULT NULL COMMENT '目标进程PID',
    `status`       TINYINT      NOT NULL DEFAULT 1 COMMENT '监控状态 1启用 0停用',
    `last_check`   DATETIME     DEFAULT NULL COMMENT '最后检测时间',
    `creator`      VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_host` (`host_id`),
    KEY `idx_product_line` (`product_line_id`),
    KEY `idx_project` (`project_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Java应用监控配置';

-- JVM 指标快照表 (时序数据主存InfluxDB, 此表存最近一次快照)
CREATE TABLE IF NOT EXISTS `ops_jvm_snapshot` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`        BIGINT       NOT NULL COMMENT 'Java应用ID',
    `heap_used`     BIGINT       DEFAULT NULL COMMENT '堆内存使用(字节)',
    `heap_max`      BIGINT       DEFAULT NULL COMMENT '堆内存最大(字节)',
    `non_heap_used` BIGINT       DEFAULT NULL COMMENT '非堆内存使用(字节)',
    `thread_count`  INT          DEFAULT NULL COMMENT '线程总数',
    `thread_daemon` INT          DEFAULT NULL COMMENT '守护线程数',
    `thread_peak`   INT          DEFAULT NULL COMMENT '线程峰值',
    `class_count`   INT          DEFAULT NULL COMMENT '已加载类数',
    `gc_minor_count` INT         DEFAULT NULL COMMENT 'Minor GC次数',
    `gc_minor_time`  BIGINT      DEFAULT NULL COMMENT 'Minor GC总耗时(ms)',
    `gc_major_count` INT         DEFAULT NULL COMMENT 'Major GC次数',
    `gc_major_time`  BIGINT      DEFAULT NULL COMMENT 'Major GC总耗时(ms)',
    `cpu_usage`     DOUBLE       DEFAULT NULL COMMENT 'CPU使用率(%)',
    `uptime`        BIGINT       DEFAULT NULL COMMENT '运行时长(ms)',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '采集时间',
    PRIMARY KEY (`id`),
    KEY `idx_app` (`app_id`),
    KEY `idx_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='JVM指标快照';
