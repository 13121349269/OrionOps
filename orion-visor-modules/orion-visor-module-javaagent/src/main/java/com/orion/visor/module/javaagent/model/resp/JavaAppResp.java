package com.orion.visor.module.javaagent.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Java应用响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Java应用响应")
public class JavaAppResp {

    @Schema(description = "应用ID")
    private Long id;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "主机ID")
    private Long hostId;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "JMX地址")
    private String jmxHost;

    @Schema(description = "JMX端口")
    private Integer jmxPort;

    @Schema(description = "进程PID")
    private Integer pid;

    @Schema(description = "监控状态 1启用 0停用")
    private Integer status;

    @Schema(description = "最后检测时间")
    private LocalDateTime lastCheck;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
