package com.orion.visor.module.javaagent.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Java应用分页查询请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "Java应用分页查询请求")
public class JavaAppPageReq {

    @Schema(description = "应用名称(模糊)")
    private String name;

    @Schema(description = "主机ID")
    private Long hostId;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "监控状态")
    private Integer status;

    @Schema(description = "页码")
    private Integer pageNo = 1;

    @Schema(description = "每页条数")
    private Integer pageSize = 10;
}
