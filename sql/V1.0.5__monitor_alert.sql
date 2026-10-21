-- =============================================
-- P6: 监控告警扩展 - 容器/JVM告警规则
-- 模块: module-monitor (扩展原生监控模块)
-- 说明: 在原生告警体系上增加容器和JVM维度规则
-- =============================================

-- 告警规则扩展表
CREATE TABLE IF NOT EXISTS `ops_alert_rule_ext` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`            VARCHAR(128) NOT NULL COMMENT '规则名称',
    `rule_type`       VARCHAR(32)  NOT NULL COMMENT '规则类型 (container/javaagent)',
    `target_id`       BIGINT       DEFAULT NULL COMMENT '目标ID (Docker主机ID 或 Java应用ID)',
    `target_type`     VARCHAR(32)  NOT NULL DEFAULT 'host' COMMENT '目标类型 (docker_host/java_app/host)',
    `metric`          VARCHAR(64)  NOT NULL COMMENT '指标 (cpu/memory/heap/thread/gc/container_status)',
    `operator`        VARCHAR(16)  NOT NULL COMMENT '比较操作符 (gt/lt/gte/lte/eq)',
    `threshold`       DOUBLE       NOT NULL COMMENT '阈值',
    `duration_sec`    INT          NOT NULL DEFAULT 60 COMMENT '持续时长(秒) 满足阈值后告警',
    `severity`        VARCHAR(16)  NOT NULL DEFAULT 'warning' COMMENT '严重级别 (info/warning/critical)',
    `enabled`         TINYINT      NOT NULL DEFAULT 1 COMMENT '是否启用 1是 0否',
    `notify_channels` VARCHAR(256) DEFAULT NULL COMMENT '通知渠道 (email/dingtalk/wecom, 逗号分隔)',
    `product_line_id` BIGINT       DEFAULT NULL COMMENT '产品线ID',
    `project_id`      BIGINT       DEFAULT NULL COMMENT '项目ID',
    `creator`         VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`         VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         BIT(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY `idx_rule_type` (`rule_type`),
    KEY `idx_target` (`target_id`, `target_type`),
    KEY `idx_enabled` (`enabled`),
    KEY `idx_product_line` (`product_line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='告警规则扩展';

-- 告警事件记录表
CREATE TABLE IF NOT EXISTS `ops_alert_event` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `rule_id`         BIGINT       DEFAULT NULL COMMENT '触发规则ID',
    `rule_name`       VARCHAR(128) DEFAULT NULL COMMENT '规则名称',
    `target_type`     VARCHAR(32)  NOT NULL COMMENT '目标类型 (docker_host/java_app/host)',
    `target_id`       BIGINT       DEFAULT NULL COMMENT '目标ID',
    `target_name`     VARCHAR(128) DEFAULT NULL COMMENT '目标名称',
    `metric`          VARCHAR(64)  NOT NULL COMMENT '触发指标',
    `current_value`   DOUBLE       DEFAULT NULL COMMENT '当前值',
    `threshold`       DOUBLE       DEFAULT NULL COMMENT '阈值',
    `severity`        VARCHAR(16)  NOT NULL DEFAULT 'warning' COMMENT '严重级别',
    `status`          VARCHAR(16)  NOT NULL DEFAULT 'firing' COMMENT '状态 (firing/resolved)',
    `message`         VARCHAR(512) DEFAULT NULL COMMENT '告警消息',
    `notify_status`   VARCHAR(16)  NOT NULL DEFAULT 'pending' COMMENT '通知状态 (pending/sent/failed)',
    `fired_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '触发时间',
    `resolved_time`   DATETIME     DEFAULT NULL COMMENT '恢复时间',
    PRIMARY KEY (`id`),
    KEY `idx_rule` (`rule_id`),
    KEY `idx_target` (`target_id`, `target_type`),
    KEY `idx_status` (`status`),
    KEY `idx_severity` (`severity`),
    KEY `idx_fired` (`fired_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='告警事件记录';
