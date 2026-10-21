package com.orion.visor.module.vault.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 密钥分页查询请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥分页查询请求")
public class VaultSecretPageReq {

    @Schema(description = "密钥名称 (模糊)")
    private String name;

    @Schema(description = "分类编码")
    private String categoryCode;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "主机ID")
    private Long hostId;

    @Schema(description = "状态 1有效 0失效")
    private Integer status;

    @Schema(description = "密钥类型")
    private String secretType;

    @Schema(description = "页码", example = "1")
    private Integer pageNo = 1;

    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}
