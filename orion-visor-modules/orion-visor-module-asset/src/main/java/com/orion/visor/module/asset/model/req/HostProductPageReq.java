package com.orion.visor.module.asset.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 主机产品线关联分页查询请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "主机产品线关联分页查询请求")
public class HostProductPageReq {

    @Schema(description = "主机ID")
    private Long hostId;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "主机角色")
    private String role;

    @Schema(description = "环境")
    private String env;

    @Schema(description = "页码", example = "1")
    private Integer pageNo = 1;

    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}
