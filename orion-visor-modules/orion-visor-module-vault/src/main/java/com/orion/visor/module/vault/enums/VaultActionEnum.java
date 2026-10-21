package com.orion.visor.module.vault.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 密钥访问操作枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum VaultActionEnum {

    VIEW("view", "查看"),
    REVEAL("reveal", "明文查看"),
    ROTATE("rotate", "轮换"),
    DELETE("delete", "删除"),
    EXPORT("export", "导出");

    private final String code;
    private final String name;
}
