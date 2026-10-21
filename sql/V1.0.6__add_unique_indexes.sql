-- =============================================
-- P6.1: 补充唯一索引 (第二轮Bug扫描修复)
-- 说明: 为业务唯一性校验字段添加数据库层唯一索引兜底
-- =============================================

-- ops_vault_secret.name 唯一索引
ALTER TABLE `ops_vault_secret` ADD UNIQUE KEY `uk_name` (`name`, `deleted`);

-- ops_java_app.name 唯一索引
ALTER TABLE `ops_java_app` ADD UNIQUE KEY `uk_name` (`name`, `deleted`);

-- ops_alert_rule_ext.name 唯一索引
ALTER TABLE `ops_alert_rule_ext` ADD UNIQUE KEY `uk_name` (`name`, `deleted`);

-- ops_vault_category.name 唯一索引
ALTER TABLE `ops_vault_category` ADD UNIQUE KEY `uk_name` (`name`, `deleted`);
