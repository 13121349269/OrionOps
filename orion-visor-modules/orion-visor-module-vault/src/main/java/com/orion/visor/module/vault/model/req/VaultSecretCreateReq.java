package com.orion.visor.module.vault.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 密钥创建请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "密钥创建请求")
public class VaultSecretCreateReq {

    @Schema(description = "密钥名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "生产环境数据库密码")
    @NotBlank(message = "密钥名称不能为空")
    @Size(max = 128, message = "密钥名称长度不能超过128")
    private String name;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "db_password")
    @NotBlank(message = "分类编码不能为空")
    @Size(max = 32, message = "分类编码长度不能超过32")
    private String categoryCode;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "关联主机ID")
    private Long hostId;

    @Schema(description = "密钥类型 (text/json/file)", example = "text")
    private String secretType;

    @Schema(description = "密钥值 (明文，服务端加密存储)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密钥值不能为空")
    private String secretValue;

    @Schema(description = "描述")
    @Size(max = 512, message = "描述长度不能超过512")
    private String description;

    @Schema(description = "过期时间 (yyyy-MM-dd HH:mm:ss)")
    private String expireTime;

    @Schema(description = "轮换周期(天), 0=不自动轮换", example = "0")
    private Integer rotationDays;
}
