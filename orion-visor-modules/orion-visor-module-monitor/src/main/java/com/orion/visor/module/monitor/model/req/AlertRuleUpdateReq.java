package com.orion.visor.module.monitor.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 告警规则更新请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "告警规则更新请求")
public class AlertRuleUpdateReq {

    @Schema(description = "规则ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规则ID不能为空")
    private Long id;

    @Schema(description = "规则名称")
    private String name;

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
}
