package com.orion.visor.module.product.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 项目分页查询请求 DTO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "项目分页查询请求")
public class ProjectPageReq {

    @Schema(description = "产品线ID", example = "1")
    private Long productLineId;

    @Schema(description = "项目名称(模糊查询)", example = "用户")
    private String name;

    @Schema(description = "项目编码", example = "user-center")
    private String code;

    @Schema(description = "状态 1启用 0停用", example = "1")
    private Integer status;

    @Schema(description = "负责人用户ID", example = "1")
    private Long ownerId;

    @Schema(description = "技术栈", example = "java")
    private String techStack;

    @Schema(description = "页码", example = "1")
    private Integer pageNo = 1;

    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}
