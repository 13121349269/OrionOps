package com.orion.visor.module.container.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Docker容器响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Docker容器响应")
public class ContainerResp {

    @Schema(description = "容器ID")
    private String id;

    @Schema(description = "容器名称")
    private List<String> names;

    @Schema(description = "镜像")
    private String image;

    @Schema(description = "状态")
    private String state;

    @Schema(description = "状态文本")
    private String status;

    @Schema(description = "端口映射")
    private List<PortBinding> ports;

    @Schema(description = "创建时间戳")
    private Long created;

    @lombok.Data
    public static class PortBinding {
        private Integer privatePort;
        private Integer publicPort;
        private String type;
        private String ip;
    }
}
