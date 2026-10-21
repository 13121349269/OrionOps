package com.orion.visor.module.product.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 产品线 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_product_line")
public class ProductLineDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 产品线名称
     */
    private String name;

    /**
     * 产品线编码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 1启用 0停用
     */
    private Integer status;

    /**
     * 负责人用户ID
     */
    private Long ownerId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
}
