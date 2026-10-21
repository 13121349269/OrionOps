package com.orion.visor.module.monitor.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警事件响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "告警事件响应")
public class AlertEventResp {

    @Schema(description = "事件ID")
    private Long id;

    @Schema(description = "规则ID")
    private Long ruleId;

    @Schema(description = "规则名称")
    private String ruleName;

    @Schema(description = "目标类型")
    private String targetType;

    @Schema(description = "目标ID")
    private Long targetId;

    @Schema(description = "目标名称")
    private String targetName;

    @Schema(description = "触发指标")
    private String metric;

    @Schema(description = "当前值")
    private Double currentValue;

    @Schema(description = "阈值")
    private Double threshold;

    @Schema(description = "严重级别")
    private String severity;

    @Schema(description = "状态 (firing/resolved)")
    private String status;

    @Schema(description = "告警消息")
    private String message;

    @Schema(description = "通知状态")
    private String notifyStatus;

    @Schema(description = "触发时间")
    private LocalDateTime firedTime;

    @Schema(description = "恢复时间")
    private LocalDateTime resolvedTime;
}
