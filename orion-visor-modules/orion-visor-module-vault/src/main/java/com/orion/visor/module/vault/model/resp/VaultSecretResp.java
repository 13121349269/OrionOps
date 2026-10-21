package com.orion.visor.module.vault.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 密钥响应 VO (不含明文值)
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥响应")
public class VaultSecretResp {

    @Schema(description = "密钥ID")
    private Long id;

    @Schema(description = "密钥名称")
    private String name;

    @Schema(description = "分类编码")
    private String categoryCode;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "关联主机ID")
    private Long hostId;

    @Schema(description = "密钥类型")
    private String secretType;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态 1有效 0失效")
    private Integer status;

    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

    @Schema(description = "最后轮换时间")
    private LocalDateTime lastRotated;

    @Schema(description = "轮换周期(天)")
    private Integer rotationDays;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否已过期")
    private Boolean expired;
}
