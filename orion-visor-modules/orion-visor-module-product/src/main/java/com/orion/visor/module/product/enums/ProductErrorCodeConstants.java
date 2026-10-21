package com.orion.visor.module.product.enums;

import com.orion.visor.framework.common.exception.ErrorCode;

/**
 * 产品模块错误码常量
 *
 * @author ops-platform
 */
public interface ProductErrorCodeConstants {

    // ========== 产品线相关 1_020_001_000 ==========
    ErrorCode PRODUCT_LINE_NOT_EXISTS      = new ErrorCode(1_020_001_000, "产品线不存在");
    ErrorCode PRODUCT_LINE_CODE_DUPLICATE  = new ErrorCode(1_020_001_001, "产品线编码已存在");
    ErrorCode PRODUCT_LINE_STATUS_INVALID  = new ErrorCode(1_020_001_002, "产品线状态值不合法");
    ErrorCode PRODUCT_LINE_HAS_PROJECTS     = new ErrorCode(1_020_001_003, "产品线下仍有项目，无法删除");

    // ========== 项目相关 1_020_002_000 ==========
    ErrorCode PROJECT_NOT_EXISTS           = new ErrorCode(1_020_002_000, "项目不存在");
    ErrorCode PROJECT_CODE_DUPLICATE        = new ErrorCode(1_020_002_001, "同一产品线下项目编码已存在");
    ErrorCode PROJECT_STATUS_INVALID        = new ErrorCode(1_020_002_002, "项目状态值不合法");
    ErrorCode PROJECT_PRODUCT_LINE_DISABLED = new ErrorCode(1_020_002_003, "所属产品线已停用，无法创建项目");

    // ========== 产品线授权相关 1_020_003_000 ==========
    ErrorCode PRODUCT_LINE_USER_DUPLICATE   = new ErrorCode(1_020_003_000, "用户已在该产品线下授权");
    ErrorCode PRODUCT_LINE_USER_NOT_EXISTS  = new ErrorCode(1_020_003_001, "产品线用户授权不存在");
}
