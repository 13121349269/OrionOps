package com.orion.visor.module.vault.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 密钥更新请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥更新请求")
public class VaultSecretUpdateReq {

    @Schema(description = "密钥ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "密钥ID不能为空")
    private Long id;

    @Schema(description = "密钥名称")
    @Size(max = 128, message = "密钥名称长度不能超过128")
    private String name;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "关联主机ID")
    private Long hostId;

    @Schema(description = "描述")
    @Size(max = 512, message = "描述长度不能超过512")
    private String description;

    @Schema(description = "状态 1有效 0失效")
    private Integer status;

    @Schema(description = "过期时间 (yyyy-MM-dd HH:mm:ss)")
    private String expireTime;

    @Schema(description = "轮换周期(天), 0=不自动轮换")
    private Integer rotationDays;
}
