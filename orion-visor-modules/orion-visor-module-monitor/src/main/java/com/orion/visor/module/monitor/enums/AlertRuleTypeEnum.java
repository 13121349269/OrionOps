package com.orion.visor.module.monitor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 告警规则类型枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum AlertRuleTypeEnum {

    CONTAINER("container", "容器监控"),
    JAVAAGENT("javaagent", "JVM监控"),
    HOST("host", "主机监控");

    private final String code;
    private final String name;

    public static boolean isValid(String code) {
        if (code == null) return false;
        for (AlertRuleTypeEnum e : values()) {
            if (e.code.equals(code)) return true;
        }
        return false;
    }
}
