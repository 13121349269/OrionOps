package com.orion.visor.module.javaagent.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Java应用监控配置 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_java_app")
public class JavaAppDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long hostId;

    private Long productLineId;

    private Long projectId;

    private String jmxHost;

    private Integer jmxPort;

    private String jmxUsername;

    /**
     * JMX密码 (加密存储)
     */
    private String jmxPassword;

    /**
     * 目标进程PID
     */
    private Integer pid;

    /**
     * 监控状态 1启用 0停用
     */
    private Integer status;

    private LocalDateTime lastCheck;

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
