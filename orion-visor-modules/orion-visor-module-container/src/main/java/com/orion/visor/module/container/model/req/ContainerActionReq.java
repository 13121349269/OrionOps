package com.orion.visor.module.container.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 容器操作请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "容器操作请求")
public class ContainerActionReq {

    @Schema(description = "Docker主机ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Docker主机ID不能为空")
    private Long dockerHostId;

    @Schema(description = "容器ID或名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "容器ID不能为空")
    private String containerId;

    @Schema(description = "操作 (start/stop/restart/pause/unpause/remove/exec)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "操作类型不能为空")
    private String action;

    @Schema(description = "操作参数 (exec命令等)")
    private String params;
}
