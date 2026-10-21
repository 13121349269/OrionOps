package com.orion.visor.module.container.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Docker 主机创建请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Docker主机创建请求")
public class DockerHostCreateReq {

    @Schema(description = "主机名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "主机名称不能为空")
    @Size(max = 128)
    private String name;

    @Schema(description = "关联Orion-Visor主机ID")
    private Long hostId;

    @Schema(description = "Docker Engine地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "tcp://192.168.1.10:2375")
    @NotBlank(message = "Docker Engine地址不能为空")
    @Size(max = 256)
    private String dockerHost;

    @Schema(description = "API版本")
    @Size(max = 32)
    private String apiVersion;

    @Schema(description = "是否TLS验证 1是 0否")
    private Integer tlsVerify;

    @Schema(description = "证书路径")
    @Size(max = 512)
    private String certPath;
}
