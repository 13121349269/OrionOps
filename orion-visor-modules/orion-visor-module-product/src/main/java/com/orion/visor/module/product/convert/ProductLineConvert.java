package com.orion.visor.module.product.convert;

import com.orion.visor.module.product.dal.dataobject.ProductLineDO;
import com.orion.visor.module.product.model.req.ProductLineCreateReq;
import com.orion.visor.module.product.model.req.ProductLineUpdateReq;
import com.orion.visor.module.product.model.resp.ProductLineResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 产品线对象转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface ProductLineConvert {

    ProductLineConvert INSTANCE = Mappers.getMapper(ProductLineConvert.class);

    /**
     * 创建请求 -> DO
     */
    ProductLineDO convert(ProductLineCreateReq req);

    /**
     * 更新请求 -> DO (部分更新)
     */
    void convert(ProductLineUpdateReq req, @MappingTarget ProductLineDO entity);

    /**
     * DO -> 响应 VO
     */
    ProductLineResp convert(ProductLineDO entity);

    /**
     * DO 列表 -> 响应 VO 列表
     */
    List<ProductLineResp> convertList(List<ProductLineDO> list);
}
