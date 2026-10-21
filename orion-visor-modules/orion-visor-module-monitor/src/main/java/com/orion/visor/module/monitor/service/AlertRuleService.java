package com.orion.visor.module.monitor.service;

import com.orion.visor.module.monitor.model.req.AlertRuleCreateReq;
import com.orion.visor.module.monitor.model.req.AlertRulePageReq;
import com.orion.visor.module.monitor.model.req.AlertRuleUpdateReq;
import com.orion.visor.module.monitor.model.resp.AlertEventResp;
import com.orion.visor.module.monitor.model.resp.AlertRuleResp;

import java.util.List;

/**
 * 告警规则 Service
 *
 * @author ops-platform
 */
public interface AlertRuleService {

    /**
     * 创建告警规则
     */
    Long createRule(AlertRuleCreateReq req);

    /**
     * 更新告警规则
     */
    void updateRule(AlertRuleUpdateReq req);

    /**
     * 删除告警规则
     */
    void deleteRule(Long id);

    /**
     * 获取告警规则详情
     */
    AlertRuleResp getRule(Long id);

    /**
     * 分页查询告警规则
     */
    List<AlertRuleResp> getRulePage(AlertRulePageReq req);

    /**
     * 查询告警事件列表
     */
    List<AlertEventResp> getAlertEvents(String status);

    /**
     * 查询目标告警事件
     */
    List<AlertEventResp> getAlertEventsByTarget(String targetType, Long targetId);

    /**
     * 恢复告警事件
     */
    void resolveAlertEvent(Long eventId);

    /**
     * 评估所有规则 (定时任务调用)
     */
    void evaluateAllRules();
}
