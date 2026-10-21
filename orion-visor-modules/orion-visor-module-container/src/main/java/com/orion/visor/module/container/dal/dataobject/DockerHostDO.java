package com.orion.visor.module.container.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Docker 主机配置 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_docker_host")
public class DockerHostDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 关联 Orion-Visor 主机ID
     */
    private Long hostId;

    /**
     * Docker Engine 地址
     */
    private String dockerHost;

    /**
     * API版本
     */
    private String apiVersion;

    /**
     * 是否TLS验证
     */
    private Integer tlsVerify;

    /**
     * 证书路径
     */
    private String certPath;

    /**
     * 连接状态 1在线 0离线
     */
    private Integer status;

    /**
     * 最后检测时间
     */
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
