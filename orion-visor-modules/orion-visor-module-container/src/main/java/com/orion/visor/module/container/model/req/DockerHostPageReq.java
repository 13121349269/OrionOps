package com.orion.visor.module.container.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Docker主机分页查询请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Docker主机分页查询请求")
public class DockerHostPageReq {

    @Schema(description = "主机名称(模糊)")
    private String name;

    @Schema(description = "关联主机ID")
    private Long hostId;

    @Schema(description = "连接状态")
    private Integer status;

    @Schema(description = "页码")
    private Integer pageNo = 1;

    @Schema(description = "每页条数")
    private Integer pageSize = 10;
}
