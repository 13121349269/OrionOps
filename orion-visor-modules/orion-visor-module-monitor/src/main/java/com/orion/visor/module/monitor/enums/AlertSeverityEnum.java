package com.orion.visor.module.monitor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 告警严重级别枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum AlertSeverityEnum {

    INFO("info", "提示"),
    WARNING("warning", "警告"),
    CRITICAL("critical", "严重");

    private final String code;
    private final String name;

    public static boolean isValid(String code) {
        if (code == null) return false;
        for (AlertSeverityEnum e : values()) {
            if (e.code.equals(code)) return true;
        }
        return false;
    }
}
