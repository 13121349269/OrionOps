package com.orion.visor.module.asset.enums;

import com.orion.visor.framework.common.exception.ErrorCode;

/**
 * 资产扩展模块错误码常量
 *
 * @author ops-platform
 */
public interface AssetErrorCodeConstants {

    // ========== 主机关联 1_021_001_000 ==========
    ErrorCode HOST_PRODUCT_NOT_EXISTS      = new ErrorCode(1_021_001_000, "主机产品线关联不存在");
    ErrorCode HOST_ALREADY_BOUND           = new ErrorCode(1_021_001_001, "主机已绑定产品线关联");
    ErrorCode HOST_ROLE_INVALID            = new ErrorCode(1_021_001_002, "主机角色值不合法");
    ErrorCode HOST_ENV_INVALID             = new ErrorCode(1_021_001_003, "环境值不合法");
    ErrorCode PRODUCT_LINE_NOT_ENABLED     = new ErrorCode(1_021_001_004, "产品线未启用");
    ErrorCode PROJECT_NOT_ENABLED          = new ErrorCode(1_021_001_005, "项目未启用");
    ErrorCode PROJECT_NOT_IN_PRODUCT_LINE  = new ErrorCode(1_021_001_006, "项目不属于指定产品线");
}
