package com.orion.visor.module.monitor.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警规则响应 VO
 *
 * @author ops-platform
 */
@Data
@Schema(description = "告警规则响应")
public class AlertRuleResp {

    @Schema(description = "规则ID")
    private Long id;

    @Schema(description = "规则名称")
    private String name;

    @Schema(description = "规则类型")
    private String ruleType;

    @Schema(description = "目标ID")
    private Long targetId;

    @Schema(description = "目标类型")
    private String targetType;

    @Schema(description = "指标")
    private String metric;

    @Schema(description = "操作符")
    private String operator;

    @Schema(description = "阈值")
    private Double threshold;

    @Schema(description = "持续时长(秒)")
    private Integer durationSec;

    @Schema(description = "严重级别")
    private String severity;

    @Schema(description = "是否启用")
    private Integer enabled;

    @Schema(description = "通知渠道")
    private String notifyChannels;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
