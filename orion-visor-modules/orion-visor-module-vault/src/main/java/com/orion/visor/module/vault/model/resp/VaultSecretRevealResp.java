package com.orion.visor.module.vault.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 密钥明文响应 VO (仅在二次验证通过后返回)
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥明文响应")
public class VaultSecretRevealResp {

    @Schema(description = "密钥ID")
    private Long id;

    @Schema(description = "密钥名称")
    private String name;

    @Schema(description = "明文值")
    private String secretValue;

    @Schema(description = "过期时间")
    private String expireTime;
}
