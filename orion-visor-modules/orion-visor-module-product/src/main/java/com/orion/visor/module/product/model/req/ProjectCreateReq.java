package com.orion.visor.module.product.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 项目创建请求 DTO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "项目创建请求")
public class ProjectCreateReq {

    @Schema(description = "所属产品线ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "产品线ID不能为空")
    private Long productLineId;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "用户中心")
    @NotBlank(message = "项目名称不能为空")
    @Size(max = 128, message = "项目名称长度不能超过128")
    private String name;

    @Schema(description = "项目编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "user-center")
    @NotBlank(message = "项目编码不能为空")
    @Size(max = 64, message = "项目编码长度不能超过64")
    private String code;

    @Schema(description = "描述")
    @Size(max = 512, message = "描述长度不能超过512")
    private String description;

    @Schema(description = "状态 1启用 0停用", example = "1")
    private Integer status;

    @Schema(description = "负责人用户ID", example = "1")
    private Long ownerId;

    @Schema(description = "代码仓库地址", example = "https://github.com/xxx/xxx.git")
    @Size(max = 512, message = "仓库地址长度不能超过512")
    private String repoUrl;

    @Schema(description = "技术栈", example = "java")
    @Size(max = 128, message = "技术栈长度不能超过128")
    private String techStack;

    @Schema(description = "排序", example = "0")
    private Integer sort;
}
