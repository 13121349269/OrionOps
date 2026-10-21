package com.orion.visor.module.monitor.convert;

import com.orion.visor.module.monitor.dal.dataobject.AlertRuleExtDO;
import com.orion.visor.module.monitor.dal.dataobject.AlertEventDO;
import com.orion.visor.module.monitor.model.req.AlertRuleCreateReq;
import com.orion.visor.module.monitor.model.req.AlertRuleUpdateReq;
import com.orion.visor.module.monitor.model.resp.AlertRuleResp;
import com.orion.visor.module.monitor.model.resp.AlertEventResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 告警规则转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface AlertRuleConvert {

    AlertRuleConvert INSTANCE = Mappers.getMapper(AlertRuleConvert.class);

    AlertRuleExtDO convert(AlertRuleCreateReq req);

    void convert(AlertRuleUpdateReq req, @MappingTarget AlertRuleExtDO entity);

    AlertRuleResp convert(AlertRuleExtDO entity);

    List<AlertRuleResp> convertList(List<AlertRuleExtDO> list);

    AlertEventResp convertEvent(AlertEventDO entity);

    List<AlertEventResp> convertEventList(List<AlertEventDO> list);
}
