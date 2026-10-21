package com.orion.visor.module.product.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_project")
public class ProjectDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 所属产品线ID
     */
    private Long productLineId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目编码
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
     * 代码仓库地址
     */
    private String repoUrl;

    /**
     * 技术栈
     */
    private String techStack;

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
