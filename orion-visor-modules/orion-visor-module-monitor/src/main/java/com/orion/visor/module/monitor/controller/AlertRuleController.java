package com.orion.visor.module.monitor.controller;

import com.orion.visor.framework.common.pojo.CommonResult;
import com.orion.visor.module.monitor.model.req.AlertRuleCreateReq;
import com.orion.visor.module.monitor.model.req.AlertRulePageReq;
import com.orion.visor.module.monitor.model.req.AlertRuleUpdateReq;
import com.orion.visor.module.monitor.model.resp.AlertEventResp;
import com.orion.visor.module.monitor.model.resp.AlertRuleResp;
import com.orion.visor.module.monitor.service.AlertRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.orion.visor.framework.common.pojo.CommonResult.success;

/**
 * 告警规则 Controller
 *
 * @author ops-platform
 */
@Tag(name = "监控告警")
@RestController
@RequestMapping("/alert")
@Validated
public class AlertRuleController {

    @Resource
    private AlertRuleService alertRuleService;

    // ========== 告警规则 ==========

    @PostMapping("/rule/create")
    @Operation(summary = "创建告警规则")
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-rule:create')")
    public CommonResult<Long> createRule(@Valid @RequestBody AlertRuleCreateReq req) {
        return success(alertRuleService.createRule(req));
    }

    @PutMapping("/rule/update")
    @Operation(summary = "更新告警规则")
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-rule:update')")
    public CommonResult<Boolean> updateRule(@Valid @RequestBody AlertRuleUpdateReq req) {
        alertRuleService.updateRule(req);
        return success(true);
    }

    @DeleteMapping("/rule/delete")
    @Operation(summary = "删除告警规则")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-rule:delete')")
    public CommonResult<Boolean> deleteRule(@RequestParam("id") Long id) {
        alertRuleService.deleteRule(id);
        return success(true);
    }

    @GetMapping("/rule/get")
    @Operation(summary = "获取告警规则详情")
    @Parameter(name = "id", required = true)
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-rule:query')")
    public CommonResult<AlertRuleResp> getRule(@RequestParam("id") Long id) {
        return success(alertRuleService.getRule(id));
    }

    @GetMapping("/rule/page")
    @Operation(summary = "分页查询告警规则")
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-rule:query')")
    public CommonResult<List<AlertRuleResp>> getRulePage(AlertRulePageReq req) {
        return success(alertRuleService.getRulePage(req));
    }

    // ========== 告警事件 ==========

    @GetMapping("/event/list")
    @Operation(summary = "查询告警事件列表")
    @Parameter(name = "status", description = "状态 (firing/resolved)")
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-event:query')")
    public CommonResult<List<AlertEventResp>> getAlertEvents(@RequestParam(value = "status", required = false) String status) {
        return success(alertRuleService.getAlertEvents(status));
    }

    @GetMapping("/event/list-by-target")
    @Operation(summary = "按目标查询告警事件")
    @Parameter(name = "targetType", required = true)
    @Parameter(name = "targetId", required = true)
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-event:query')")
    public CommonResult<List<AlertEventResp>> getAlertEventsByTarget(@RequestParam("targetType") String targetType,
                                                                      @RequestParam("targetId") Long targetId) {
        return success(alertRuleService.getAlertEventsByTarget(targetType, targetId));
    }

    @PostMapping("/event/resolve")
    @Operation(summary = "恢复告警事件")
    @Parameter(name = "eventId", required = true)
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-event:update')")
    public CommonResult<Boolean> resolveAlertEvent(@RequestParam("eventId") Long eventId) {
        alertRuleService.resolveAlertEvent(eventId);
        return success(true);
    }

    @PostMapping("/rule/evaluate")
    @Operation(summary = "手动触发规则评估")
    @PreAuthorize("@ss.hasPermission('ops:monitor:alert-rule:update')")
    public CommonResult<Boolean> evaluateAllRules() {
        alertRuleService.evaluateAllRules();
        return success(true);
    }
}
