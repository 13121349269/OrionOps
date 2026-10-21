package com.orion.visor.module.monitor.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 告警规则分页查询请求
 *
 * @author ops-platform
 */
@Data
@Schema(description = "告警规则分页查询请求")
public class AlertRulePageReq {

    @Schema(description = "规则名称(模糊)")
    private String name;

    @Schema(description = "规则类型")
    private String ruleType;

    @Schema(description = "目标ID")
    private Long targetId;

    @Schema(description = "目标类型")
    private String targetType;

    @Schema(description = "是否启用")
    private Integer enabled;

    @Schema(description = "产品线ID")
    private Long productLineId;

    @Schema(description = "页码")
    private Integer pageNo = 1;

    @Schema(description = "每页条数")
    private Integer pageSize = 10;
}
