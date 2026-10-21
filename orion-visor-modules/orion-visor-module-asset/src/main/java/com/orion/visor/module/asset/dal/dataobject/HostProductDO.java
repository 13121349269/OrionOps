package com.orion.visor.module.asset.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 主机-产品线-项目关联 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_host_product")
public class HostProductDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 主机ID
     */
    private Long hostId;

    /**
     * 产品线ID
     */
    private Long productLineId;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 主机角色 (web/app/db/cache/mq/other)
     */
    private String role;

    /**
     * 环境 (dev/test/staging/prod)
     */
    private String env;

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
