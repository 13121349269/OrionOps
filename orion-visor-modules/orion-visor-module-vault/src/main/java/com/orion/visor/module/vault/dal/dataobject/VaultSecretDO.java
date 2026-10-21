package com.orion.visor.module.vault.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 密钥条目 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_vault_secret")
public class VaultSecretDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 密钥名称
     */
    private String name;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 所属产品线ID
     */
    private Long productLineId;

    /**
     * 所属项目ID
     */
    private Long projectId;

    /**
     * 关联主机ID (可选)
     */
    private Long hostId;

    /**
     * 密钥类型 (text/json/file)
     */
    private String secretType;

    /**
     * 加密后的密钥值 (AES-256)
     */
    private String encryptedValue;

    /**
     * 加密密钥标识
     */
    private String encryptionKey;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 1有效 0失效
     */
    private Integer status;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 最后轮换时间
     */
    private LocalDateTime lastRotated;

    /**
     * 轮换周期(天), 0=不自动轮换
     */
    private Integer rotationDays;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;
}
