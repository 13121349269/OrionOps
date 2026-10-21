package com.orion.visor.module.monitor.enums;

import com.orion.visor.framework.common.exception.ErrorCode;

/**
 * 监控告警错误码常量
 *
 * @author ops-platform
 */
public interface MonitorErrorCodeConstants {

    // ========== 告警规则 1_025_001_000 ==========
    ErrorCode ALERT_RULE_NOT_EXISTS     = new ErrorCode(1_025_001_000, "告警规则不存在");
    ErrorCode ALERT_RULE_NAME_DUPLICATE  = new ErrorCode(1_025_001_001, "告警规则名称已存在");
    ErrorCode ALERT_RULE_TYPE_INVALID    = new ErrorCode(1_025_001_002, "规则类型不合法");
    ErrorCode ALERT_OPERATOR_INVALID     = new ErrorCode(1_025_001_003, "比较操作符不合法");
    ErrorCode ALERT_SEVERITY_INVALID     = new ErrorCode(1_025_001_004, "严重级别不合法");
    ErrorCode ALERT_EVALUATE_FAILED      = new ErrorCode(1_025_001_005, "告警评估失败");

    // ========== 告警事件 1_025_002_000 ==========
    ErrorCode ALERT_EVENT_NOT_EXISTS    = new ErrorCode(1_025_002_000, "告警事件不存在");
}
