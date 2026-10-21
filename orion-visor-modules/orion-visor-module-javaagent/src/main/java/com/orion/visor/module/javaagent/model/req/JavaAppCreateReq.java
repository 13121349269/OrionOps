package com.orion.visor.module.javaagent.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Java应用创建请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Java应用创建请求")
public class JavaAppCreateReq {

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "应用名称不能为空")
    @Size(max = 128)
    private String name;

    @Schema(description = "关联主机ID")
    private Long hostId;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "JMX地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "192.168.1.10")
    @NotBlank(message = "JMX地址不能为空")
    private String jmxHost;

    @Schema(description = "JMX端口", requiredMode = Schema.RequiredMode.REQUIRED, example = "9099")
    @NotNull(message = "JMX端口不能为空")
    private Integer jmxPort;

    @Schema(description = "JMX用户名")
    private String jmxUsername;

    @Schema(description = "JMX密码")
    private String jmxPassword;

    @Schema(description = "进程PID")
    private Integer pid;
}
