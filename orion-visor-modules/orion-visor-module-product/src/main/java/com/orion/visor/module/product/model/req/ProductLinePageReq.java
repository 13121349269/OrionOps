package com.orion.visor.module.product.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 产品线分页查询请求 DTO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "产品线分页查询请求")
public class ProductLinePageReq {

    @Schema(description = "产品线名称(模糊查询)", example = "基础")
    private String name;

    @Schema(description = "产品线编码", example = "infra")
    private String code;

    @Schema(description = "状态 1启用 0停用", example = "1")
    private Integer status;

    @Schema(description = "负责人用户ID", example = "1")
    private Long ownerId;

    @Schema(description = "页码", example = "1")
    private Integer pageNo = 1;

    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}
