package com.orion.visor.module.monitor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 比较操作符枚举
 *
 * @author ops-platform
 */
@Getter
@AllArgsConstructor
public enum AlertOperatorEnum {

    GT("gt", ">"),
    LT("lt", "<"),
    GTE("gte", ">="),
    LTE("lte", "<="),
    EQ("eq", "==");

    private final String code;
    private final String symbol;

    /**
     * 评估是否满足条件
     */
    public boolean evaluate(double current, double threshold) {
        switch (this) {
            case GT: return current > threshold;
            case LT: return current < threshold;
            case GTE: return current >= threshold;
            case LTE: return current <= threshold;
            case EQ: return current == threshold;
            default: return false;
        }
    }

    public static AlertOperatorEnum fromCode(String code) {
        if (code == null) return null;
        for (AlertOperatorEnum e : values()) {
            if (e.code.equals(code)) return e;
        }
        return null;
    }
}
