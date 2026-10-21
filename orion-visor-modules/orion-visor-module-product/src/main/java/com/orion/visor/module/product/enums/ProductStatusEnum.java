package com.orion.visor.module.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 产品线/项目状态枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    ENABLED(1, "启用"),
    DISABLED(0, "停用");

    private final Integer status;
    private final String name;

    public static boolean isValid(Integer status) {
        return status != null && (status == 1 || status == 0);
    }
}
