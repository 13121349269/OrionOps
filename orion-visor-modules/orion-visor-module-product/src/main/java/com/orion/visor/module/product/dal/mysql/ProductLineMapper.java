package com.orion.visor.module.product.dal.mysql;

import com.orion.visor.module.product.dal.dataobject.ProductLineDO;
import com.orion.visor.module.product.model.req.ProductLinePageReq;
import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 产品线 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface ProductLineMapper extends BaseMapperX<ProductLineDO> {

    /**
     * 根据编码查询产品线
     */
    default ProductLineDO selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<ProductLineDO>()
                .eqIfPresent(ProductLineDO::getCode, code));
    }

    /**
     * 分页查询产品线列表
     */
    default List<ProductLineDO> selectPage(ProductLinePageReq req) {
        return selectList(new LambdaQueryWrapperX<ProductLineDO>()
                .likeIfPresent(ProductLineDO::getName, req.getName())
                .eqIfPresent(ProductLineDO::getCode, req.getCode())
                .eqIfPresent(ProductLineDO::getStatus, req.getStatus())
                .eqIfPresent(ProductLineDO::getOwnerId, req.getOwnerId())
                .orderByAsc(ProductLineDO::getSort)
                .orderByDesc(ProductLineDO::getCreateTime));
    }

    /**
     * 查询所有启用的产品线
     */
    default List<ProductLineDO> selectEnabledList() {
        return selectList(new LambdaQueryWrapperX<ProductLineDO>()
                .eq(ProductLineDO::getStatus, 1)
                .orderByAsc(ProductLineDO::getSort));
    }
}
