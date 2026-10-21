package com.orion.visor.module.vault.enums;

import com.orion.visor.framework.common.exception.ErrorCode;

/**
 * 密钥保险箱错误码常量
 *
 * @author ops-platform
 */
public interface VaultErrorCodeConstants {

    // ========== 密钥条目 1_022_001_000 ==========
    ErrorCode SECRET_NOT_EXISTS          = new ErrorCode(1_022_001_000, "密钥不存在");
    ErrorCode SECRET_NAME_DUPLICATE      = new ErrorCode(1_022_001_001, "密钥名称已存在");
    ErrorCode SECRET_TYPE_INVALID         = new ErrorCode(1_022_001_002, "密钥类型不合法");
    ErrorCode SECRET_ENCRYPT_FAILED       = new ErrorCode(1_022_001_003, "密钥加密失败");
    ErrorCode SECRET_DECRYPT_FAILED       = new ErrorCode(1_022_001_004, "密钥解密失败");
    ErrorCode SECRET_EXPIRED              = new ErrorCode(1_022_001_005, "密钥已过期");
    ErrorCode SECRET_REVEAL_DENIED        = new ErrorCode(1_022_001_006, "明文查看需要二次验证");
    ErrorCode SECRET_REVEAL_VERIFICATION_FAILED = new ErrorCode(1_022_001_007, "二次验证失败");
    ErrorCode SECRET_ROTATION_IN_PROGRESS     = new ErrorCode(1_022_001_008, "密钥正在轮换中");

    // ========== 分类 1_022_002_000 ==========
    ErrorCode CATEGORY_NOT_EXISTS         = new ErrorCode(1_022_002_000, "密钥分类不存在");
    ErrorCode CATEGORY_CODE_DUPLICATE     = new ErrorCode(1_022_002_001, "分类编码已存在");
    ErrorCode CATEGORY_HAS_SECRETS        = new ErrorCode(1_022_002_002, "分类下仍有密钥，无法删除");
}
