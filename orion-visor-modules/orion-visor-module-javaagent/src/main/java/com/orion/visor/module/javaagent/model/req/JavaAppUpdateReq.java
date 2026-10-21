package com.orion.visor.module.javaagent.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Java应用更新请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Java应用更新请求")
public class JavaAppUpdateReq {

    @Schema(description = "应用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "应用ID不能为空")
    private Long id;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "关联主机ID")
    private Long hostId;

    @Schema(description = "JMX地址")
    private String jmxHost;

    @Schema(description = "JMX端口")
    private Integer jmxPort;

    @Schema(description = "JMX用户名")
    private String jmxUsername;

    @Schema(description = "JMX密码")
    private String jmxPassword;

    @Schema(description = "进程PID")
    private Integer pid;

    @Schema(description = "监控状态 1启用 0停用")
    private Integer status;
}
