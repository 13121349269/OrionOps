package com.orion.visor.module.container.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Docker 主机更新请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Docker主机更新请求")
public class DockerHostUpdateReq {

    @Schema(description = "主机ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "主机ID不能为空")
    private Long id;

    @Schema(description = "主机名称")
    private String name;

    @Schema(description = "关联Orion-Visor主机ID")
    private Long hostId;

    @Schema(description = "Docker Engine地址")
    private String dockerHost;

    @Schema(description = "API版本")
    private String apiVersion;

    @Schema(description = "是否TLS验证")
    private Integer tlsVerify;

    @Schema(description = "证书路径")
    private String certPath;

    @Schema(description = "连接状态")
    private Integer status;
}
