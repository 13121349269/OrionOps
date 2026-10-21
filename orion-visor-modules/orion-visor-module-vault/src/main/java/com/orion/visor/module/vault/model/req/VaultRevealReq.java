package com.orion.visor.module.vault.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 明文查看二次验证请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "明文查看二次验证请求")
public class VaultRevealReq {

    @Schema(description = "密钥ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "密钥ID不能为空")
    private Long id;

    @Schema(description = "二次验证密码 (当前用户密码)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证密码不能为空")
    private String verifyPassword;
}
