package com.orion.visor.module.javaagent.enums;

import com.orion.visor.framework.common.exception.ErrorCode;

/**
 * JVM诊断错误码常量
 *
 * @author ops-platform
 */
public interface JavaAgentErrorCodeConstants {

    // ========== Java应用 1_024_001_000 ==========
    ErrorCode JAVA_APP_NOT_EXISTS      = new ErrorCode(1_024_001_000, "Java应用不存在");
    ErrorCode JAVA_APP_NAME_DUPLICATE  = new ErrorCode(1_024_001_001, "Java应用名称已存在");
    ErrorCode JMX_CONNECT_FAILED       = new ErrorCode(1_024_001_002, "JMX连接失败");
    ErrorCode JAVA_APP_DISABLED        = new ErrorCode(1_024_001_003, "Java应用监控未启用");
}
