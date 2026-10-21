package com.orion.visor.module.vault.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 密钥类型枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum SecretTypeEnum {

    TEXT("text", "文本"),
    JSON("json", "JSON"),
    FILE("file", "文件");

    private final String code;
    private final String name;

    public static boolean isValid(String code) {
        if (code == null) {
            return false;
        }
        for (SecretTypeEnum e : values()) {
            if (e.code.equals(code)) {
                return true;
            }
        }
        return false;
    }
}
