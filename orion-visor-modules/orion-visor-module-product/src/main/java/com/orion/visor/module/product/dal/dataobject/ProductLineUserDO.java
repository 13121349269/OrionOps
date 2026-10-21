package com.orion.visor.module.product.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 产品线-用户授权 DO
 * 将用户关联到产品线维度，复用现有 RBAC 角色体系
 *
 * @author ops-platform
 */
@Data
@TableName("ops_product_line_user")
public class ProductLineUserDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品线ID
     */
    private Long productLineId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID (复用现有 RBAC 角色)
     */
    private Long roleId;

    @TableField(fill = FieldFill.INSERT)
    private String creator;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Boolean deleted;
}
