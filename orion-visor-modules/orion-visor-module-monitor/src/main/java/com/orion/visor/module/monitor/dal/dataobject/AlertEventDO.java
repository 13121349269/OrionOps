package com.orion.visor.module.monitor.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 告警事件记录 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_alert_event")
public class AlertEventDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long ruleId;

    private String ruleName;

    private String targetType;

    private Long targetId;

    private String targetName;

    private String metric;

    private Double currentValue;

    private Double threshold;

    private String severity;

    /**
     * 状态 (firing/resolved)
     */
    private String status;

    private String message;

    /**
     * 通知状态 (pending/sent/failed)
     */
    private String notifyStatus;

    private LocalDateTime firedTime;

    private LocalDateTime resolvedTime;
}
