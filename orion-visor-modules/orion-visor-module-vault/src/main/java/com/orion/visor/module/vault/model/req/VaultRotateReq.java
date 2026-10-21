package com.orion.visor.module.vault.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 密钥轮换请求 (POST Body，避免明文出现在 URL 中)
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥轮换请求")
public class VaultRotateReq {

    @Schema(description = "密钥ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "密钥ID不能为空")
    private Long id;

    @Schema(description = "新密钥值 (明文，服务端加密存储)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "新密钥值不能为空")
    private String newSecretValue;

    @Schema(description = "二次验证密码 (当前用户密码)")
    @NotBlank(message = "验证密码不能为空")
    private String verifyPassword;
}
