package com.orion.visor.module.monitor.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 告警规则创建请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "告警规则创建请求")
public class AlertRuleCreateReq {

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "规则名称不能为空")
    @Size(max = 128)
    private String name;

    @Schema(description = "规则类型 (container/javaagent/host)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "规则类型不能为空")
    private String ruleType;

    @Schema(description = "目标ID")
    private Long targetId;

    @Schema(description = "目标类型 (docker_host/java_app/host)")
    private String targetType;

    @Schema(description = "指标", requiredMode = Schema.RequiredMode.REQUIRED, example = "cpu")
    @NotBlank(message = "指标不能为空")
    private String metric;

    @Schema(description = "操作符 (gt/lt/gte/lte/eq)", requiredMode = Schema.RequiredMode.REQUIRED, example = "gt")
    @NotBlank(message = "操作符不能为空")
    private String operator;

    @Schema(description = "阈值", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    @NotNull(message = "阈值不能为空")
    private Double threshold;

    @Schema(description = "持续时长(秒)", example = "60")
    private Integer durationSec;

    @Schema(description = "严重级别 (info/warning/critical)")
    private String severity;

    @Schema(description = "是否启用 1是 0否")
    private Integer enabled;

    @Schema(description = "通知渠道 (email/dingtalk/wecom, 逗号分隔)")
    private String notifyChannels;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "项目ID")
    private Long projectId;
}
