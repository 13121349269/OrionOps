package com.orion.visor.module.monitor.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.monitor.dal.dataobject.AlertEventDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 告警事件 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface AlertEventMapper extends BaseMapperX<AlertEventDO> {

    default List<AlertEventDO> selectByStatus(String status) {
        return selectList(new LambdaQueryWrapperX<AlertEventDO>()
                .eqIfPresent(AlertEventDO::getStatus, status)
                .orderByDesc(AlertEventDO::getFiredTime));
    }

    default List<AlertEventDO> selectByRuleId(Long ruleId) {
        return selectList(new LambdaQueryWrapperX<AlertEventDO>()
                .eqIfPresent(AlertEventDO::getRuleId, ruleId)
                .orderByDesc(AlertEventDO::getFiredTime));
    }

    default List<AlertEventDO> selectByTarget(String targetType, Long targetId) {
        return selectList(new LambdaQueryWrapperX<AlertEventDO>()
                .eqIfPresent(AlertEventDO::getTargetType, targetType)
                .eqIfPresent(AlertEventDO::getTargetId, targetId)
                .orderByDesc(AlertEventDO::getFiredTime));
    }
}
