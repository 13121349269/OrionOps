package com.orion.visor.module.monitor.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.monitor.dal.dataobject.AlertRuleExtDO;
import com.orion.visor.module.monitor.model.req.AlertRulePageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 告警规则 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface AlertRuleExtMapper extends BaseMapperX<AlertRuleExtDO> {

    default AlertRuleExtDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<AlertRuleExtDO>()
                .eqIfPresent(AlertRuleExtDO::getName, name));
    }

    default List<AlertRuleExtDO> selectPage(AlertRulePageReq req) {
        return selectList(new LambdaQueryWrapperX<AlertRuleExtDO>()
                .likeIfPresent(AlertRuleExtDO::getName, req.getName())
                .eqIfPresent(AlertRuleExtDO::getRuleType, req.getRuleType())
                .eqIfPresent(AlertRuleExtDO::getTargetId, req.getTargetId())
                .eqIfPresent(AlertRuleExtDO::getTargetType, req.getTargetType())
                .eqIfPresent(AlertRuleExtDO::getEnabled, req.getEnabled())
                .eqIfPresent(AlertRuleExtDO::getProductLineId, req.getProductLineId())
                .orderByDesc(AlertRuleExtDO::getCreateTime));
    }

    default List<AlertRuleExtDO> selectEnabled() {
        return selectList(new LambdaQueryWrapperX<AlertRuleExtDO>()
                .eq(AlertRuleExtDO::getEnabled, 1));
    }
}
