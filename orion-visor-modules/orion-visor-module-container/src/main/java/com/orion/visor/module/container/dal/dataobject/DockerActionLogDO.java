package com.orion.visor.module.container.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Docker 容器操作记录 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_docker_action_log")
public class DockerActionLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long dockerHostId;

    private String containerId;

    private String containerName;

    /**
     * 操作 (start/stop/restart/pause/unpause/remove/exec)
     */
    private String action;

    /**
     * 操作参数JSON
     */
    private String params;

    /**
     * 执行状态 (success/failed)
     */
    private String status;

    /**
     * 执行消息
     */
    private String message;

    /**
     * 操作用户ID
     */
    private Long userId;

    private String creator;

    private LocalDateTime createTime;
}
