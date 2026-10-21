package com.orion.visor.module.vault.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 密钥访问日志响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥访问日志响应")
public class VaultAccessLogResp {

    @Schema(description = "日志ID")
    private Long id;

    @Schema(description = "密钥ID")
    private Long secretId;

    @Schema(description = "密钥名称 (联表填充)")
    private String secretName;

    @Schema(description = "操作用户ID")
    private Long userId;

    @Schema(description = "操作用户名 (联表填充)")
    private String userName;

    @Schema(description = "操作")
    private String action;

    @Schema(description = "IP地址")
    private String ipAddress;

    @Schema(description = "操作时间")
    private LocalDateTime createTime;
}
