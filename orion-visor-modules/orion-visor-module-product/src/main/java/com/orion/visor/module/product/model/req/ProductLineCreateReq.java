package com.orion.visor.module.product.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 产品线创建请求 DTO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "产品线创建请求")
public class ProductLineCreateReq {

    @Schema(description = "产品线名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "基础架构")
    @NotBlank(message = "产品线名称不能为空")
    @Size(max = 128, message = "产品线名称长度不能超过128")
    private String name;

    @Schema(description = "产品线编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "infra")
    @NotBlank(message = "产品线编码不能为空")
    @Size(max = 64, message = "产品线编码长度不能超过64")
    private String code;

    @Schema(description = "描述", example = "基础架构产品线")
    @Size(max = 512, message = "描述长度不能超过512")
    private String description;

    @Schema(description = "状态 1启用 0停用", example = "1")
    private Integer status;

    @Schema(description = "负责人用户ID", example = "1")
    private Long ownerId;

    @Schema(description = "排序", example = "0")
    private Integer sort;
}
