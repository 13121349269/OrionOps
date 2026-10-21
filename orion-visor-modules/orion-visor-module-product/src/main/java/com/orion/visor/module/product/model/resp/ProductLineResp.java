package com.orion.visor.module.product.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 产品线响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "产品线响应")
public class ProductLineResp {

    @Schema(description = "产品线ID")
    private Long id;

    @Schema(description = "产品线名称")
    private String name;

    @Schema(description = "产品线编码")
    private String code;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态 1启用 0停用")
    private Integer status;

    @Schema(description = "负责人用户ID")
    private Long ownerId;

    @Schema(description = "负责人用户名 (联表查询填充)")
    private String ownerName;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
