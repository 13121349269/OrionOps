package com.orion.visor.module.monitor.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 告警规则扩展 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_alert_rule_ext")
public class AlertRuleExtDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 规则类型 (container/javaagent/host)
     */
    private String ruleType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 目标类型 (docker_host/java_app/host)
     */
    private String targetType;

    /**
     * 指标 (cpu/memory/heap/thread/gc/container_status)
     */
    private String metric;

    /**
     * 比较操作符 (gt/lt/gte/lte/eq)
     */
    private String operator;

    /**
     * 阈值
     */
    private Double threshold;

    /**
     * 持续时长(秒)
     */
    private Integer durationSec;

    /**
     * 严重级别 (info/warning/critical)
     */
    private String severity;

    /**
     * 是否启用
     */
    private Integer enabled;

    /**
     * 通知渠道
     */
    private String notifyChannels;

    private Long productLineId;

    private Long projectId;

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
