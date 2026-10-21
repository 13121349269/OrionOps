package com.orion.visor.module.asset.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 主机产品线关联绑定请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "主机产品线关联绑定请求")
public class HostProductBindReq {

    @Schema(description = "主机ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主机ID不能为空")
    private Long hostId;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "主机角色 (web/app/db/cache/mq/other)", example = "app")
    private String role;

    @Schema(description = "环境 (dev/test/staging/prod)", example = "prod")
    private String env;
}
