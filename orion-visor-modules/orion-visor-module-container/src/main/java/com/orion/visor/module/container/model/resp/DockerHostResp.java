package com.orion.visor.module.container.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Docker主机响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Docker主机响应")
public class DockerHostResp {

    @Schema(description = "主机ID")
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

    @Schema(description = "连接状态 1在线 0离线")
    private Integer status;

    @Schema(description = "最后检测时间")
    private LocalDateTime lastCheck;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
