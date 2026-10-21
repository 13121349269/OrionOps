package com.orion.visor.module.asset.convert;

import com.orion.visor.module.asset.dal.dataobject.HostProductDO;
import com.orion.visor.module.asset.model.req.HostProductBindReq;
import com.orion.visor.module.asset.model.resp.HostProductResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 主机产品线关联转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface HostProductConvert {

    HostProductConvert INSTANCE = Mappers.getMapper(HostProductConvert.class);

    /**
     * 绑定请求 -> DO
     */
    HostProductDO convert(HostProductBindReq req);

    /**
     * DO -> 响应 VO
     */
    HostProductResp convert(HostProductDO entity);

    /**
     * DO 列表 -> 响应 VO 列表
     */
    List<HostProductResp> convertList(List<HostProductDO> list);
}
