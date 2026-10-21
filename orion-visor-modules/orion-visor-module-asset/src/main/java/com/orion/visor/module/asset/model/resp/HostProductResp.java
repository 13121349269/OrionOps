package com.orion.visor.module.asset.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 主机产品线关联响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "主机产品线关联响应")
public class HostProductResp {

    @Schema(description = "关联ID")
    private Long id;

    @Schema(description = "主机ID")
    private Long hostId;

    @Schema(description = "主机名称 (联表填充)")
    private String hostName;

    @Schema(description = "主机地址 (联表填充)")
    private String hostAddress;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "产品线名称 (联表填充)")
    private String productLineName;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "项目名称 (联表填充)")
    private String projectName;

    @Schema(description = "主机角色")
    private String role;

    @Schema(description = "环境")
    private String env;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
