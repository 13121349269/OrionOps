package com.orion.visor.module.asset.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 环境枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum EnvEnum {

    DEV("dev", "开发环境"),
    TEST("test", "测试环境"),
    STAGING("staging", "预发环境"),
    PROD("prod", "生产环境");

    private final String code;
    private final String name;

    public static boolean isValid(String code) {
        if (code == null) {
            return false;
        }
        for (EnvEnum e : values()) {
            if (e.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
