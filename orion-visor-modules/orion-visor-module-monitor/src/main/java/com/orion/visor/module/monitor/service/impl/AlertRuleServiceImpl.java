package com.orion.visor.module.monitor.service.impl;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.monitor.convert.AlertRuleConvert;
import com.orion.visor.module.monitor.dal.dataobject.AlertEventDO;
import com.orion.visor.module.monitor.dal.dataobject.AlertRuleExtDO;
import com.orion.visor.module.monitor.dal.mysql.AlertEventMapper;
import com.orion.visor.module.monitor.dal.mysql.AlertRuleExtMapper;
import com.orion.visor.module.monitor.enums.AlertOperatorEnum;
import com.orion.visor.module.monitor.enums.AlertRuleTypeEnum;
import com.orion.visor.module.monitor.enums.AlertSeverityEnum;
import com.orion.visor.module.monitor.model.req.AlertRuleCreateReq;
import com.orion.visor.module.monitor.model.req.AlertRulePageReq;
import com.orion.visor.module.monitor.model.req.AlertRuleUpdateReq;
import com.orion.visor.module.monitor.model.resp.AlertEventResp;
import com.orion.visor.module.monitor.model.resp.AlertRuleResp;
import com.orion.visor.module.monitor.service.AlertRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.orion.visor.module.monitor.enums.MonitorErrorCodeConstants.*;

/**
 * 告警规则 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class AlertRuleServiceImpl implements AlertRuleService {

    @Resource
    private AlertRuleExtMapper alertRuleExtMapper;

    @Resource
    private AlertEventMapper alertEventMapper;

    @Override
    public Long createRule(AlertRuleCreateReq req) {
        // 1. 校验名称唯一
        AlertRuleExtDO existing = alertRuleExtMapper.selectByName(req.getName());
        if (existing != null) {
            throw ServiceException.of(ALERT_RULE_NAME_DUPLICATE);
        }
        // 2. 校验类型
        if (!AlertRuleTypeEnum.isValid(req.getRuleType())) {
            throw ServiceException.of(ALERT_RULE_TYPE_INVALID);
        }
        // 3. 校验操作符
        if (AlertOperatorEnum.fromCode(req.getOperator()) == null) {
            throw ServiceException.of(ALERT_OPERATOR_INVALID);
        }
        // 4. 校验严重级别
        if (req.getSeverity() != null && !AlertSeverityEnum.isValid(req.getSeverity())) {
            throw ServiceException.of(ALERT_SEVERITY_INVALID);
        }
        // 5. 转换并插入
        AlertRuleExtDO entity = AlertRuleConvert.INSTANCE.convert(req);
        if (entity.getDurationSec() == null) {
            entity.setDurationSec(60);
        }
        if (entity.getSeverity() == null) {
            entity.setSeverity(AlertSeverityEnum.WARNING.getCode());
        }
        if (entity.getEnabled() == null) {
            entity.setEnabled(1);
        }
        alertRuleExtMapper.insert(entity);
        log.info("[createRule] 告警规则创建成功, id={}, name={}", entity.getId(), entity.getName());
        return entity.getId();
    }

    @Override
    public void updateRule(AlertRuleUpdateReq req) {
        AlertRuleExtDO existing = validateExists(req.getId());
        AlertRuleExtDO update = new AlertRuleExtDO();
        update.setId(req.getId());
        AlertRuleConvert.INSTANCE.convert(req, update);
        alertRuleExtMapper.updateById(update);
        log.info("[updateRule] 告警规则更新成功, id={}", req.getId());
    }

    @Override
    public void deleteRule(Long id) {
        validateExists(id);
        alertRuleExtMapper.deleteById(id);
        log.info("[deleteRule] 告警规则删除成功, id={}", id);
    }

    @Override
    public AlertRuleResp getRule(Long id) {
        AlertRuleExtDO entity = validateExists(id);
        return AlertRuleConvert.INSTANCE.convert(entity);
    }

    @Override
    public List<AlertRuleResp> getRulePage(AlertRulePageReq req) {
        List<AlertRuleExtDO> list = alertRuleExtMapper.selectPage(req);
        return AlertRuleConvert.INSTANCE.convertList(list);
    }

    @Override
    public List<AlertEventResp> getAlertEvents(String status) {
        List<AlertEventDO> list = alertEventMapper.selectByStatus(status);
        return AlertRuleConvert.INSTANCE.convertEventList(list);
    }

    @Override
    public List<AlertEventResp> getAlertEventsByTarget(String targetType, Long targetId) {
        List<AlertEventDO> list = alertEventMapper.selectByTarget(targetType, targetId);
        return AlertRuleConvert.INSTANCE.convertEventList(list);
    }

    @Override
    public void resolveAlertEvent(Long eventId) {
        AlertEventDO event = alertEventMapper.selectById(eventId);
        if (event == null) {
            throw ServiceException.of(ALERT_EVENT_NOT_EXISTS);
        }
        AlertEventDO update = new AlertEventDO();
        update.setId(eventId);
        update.setStatus("resolved");
        update.setResolvedTime(LocalDateTime.now());
        alertEventMapper.updateById(update);
        log.info("[resolveAlertEvent] 告警事件恢复, id={}", eventId);
    }

    @Override
    public void evaluateAllRules() {
        List<AlertRuleExtDO> rules = alertRuleExtMapper.selectEnabled();
        for (AlertRuleExtDO rule : rules) {
            try {
                evaluateRule(rule);
            } catch (Exception e) {
                log.error("[evaluateAllRules] 规则评估失败, ruleId={}, ruleName={}",
                        rule.getId(), rule.getName(), e);
            }
        }
    }

    /**
     * 评估单条规则
     *
     * <p>TODO: 对接 module-container 和 module-javaagent 获取实时指标值，
     * 根据操作符和阈值判断是否触发告警。此处仅提供框架。</p>
     */
    private void evaluateRule(AlertRuleExtDO rule) {
        // TODO: 按规则类型采集目标指标
        // 1. container 类型: 查询 module-container 获取容器 CPU/内存/状态
        // 2. javaagent 类型: 查询 module-javaagent 获取 JVM 堆/线程/GC 指标
        // 3. host 类型: 查询原生监控获取主机 CPU/内存/磁盘

        double currentValue = 0.0; // TODO: 采集实际指标

        AlertOperatorEnum operator = AlertOperatorEnum.fromCode(rule.getOperator());
        if (operator == null) {
            log.warn("[evaluateRule] 操作符无效, ruleId={}, operator={}", rule.getId(), rule.getOperator());
            return;
        }

        boolean triggered = operator.evaluate(currentValue, rule.getThreshold());
        if (triggered) {
            // 检查是否已有 firing 状态的事件 (避免重复告警)
            // TODO: 查询活跃告警事件，如果已存在则不重复创建
            AlertEventDO event = new AlertEventDO();
            event.setRuleId(rule.getId());
            event.setRuleName(rule.getName());
            event.setTargetType(rule.getTargetType());
            event.setTargetId(rule.getTargetId());
            event.setMetric(rule.getMetric());
            event.setCurrentValue(currentValue);
            event.setThreshold(rule.getThreshold());
            event.setSeverity(rule.getSeverity());
            event.setStatus("firing");
            event.setNotifyStatus("pending");
            event.setMessage(String.format("%s: %s %s %.2f (当前: %.2f)",
                    rule.getName(), rule.getMetric(), operator.getSymbol(),
                    rule.getThreshold(), currentValue));
            event.setFiredTime(LocalDateTime.now());
            alertEventMapper.insert(event);
            log.warn("[evaluateRule] 告警触发, ruleId={}, metric={}, value={}",
                    rule.getId(), rule.getMetric(), currentValue);
        }
    }

    private AlertRuleExtDO validateExists(Long id) {
        AlertRuleExtDO entity = alertRuleExtMapper.selectById(id);
        if (entity == null) {
            throw ServiceException.of(ALERT_RULE_NOT_EXISTS);
        }
        return entity;
    }
}
