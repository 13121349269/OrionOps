package com.orion.visor.module.asset.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.asset.dal.dataobject.HostProductDO;
import com.orion.visor.module.asset.model.req.HostProductPageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 主机-产品线关联 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface HostProductMapper extends BaseMapperX<HostProductDO> {

    /**
     * 根据主机ID查询关联
     */
    default HostProductDO selectByHostId(Long hostId) {
        return selectOne(new LambdaQueryWrapperX<HostProductDO>()
                .eqIfPresent(HostProductDO::getHostId, hostId));
    }

    /**
     * 根据产品线ID查询主机列表
     */
    default List<HostProductDO> selectByProductLineId(Long productLineId) {
        return selectList(new LambdaQueryWrapperX<HostProductDO>()
                .eqIfPresent(HostProductDO::getProductLineId, productLineId));
    }

    /**
     * 根据项目ID查询主机列表
     */
    default List<HostProductDO> selectByProjectId(Long projectId) {
        return selectList(new LambdaQueryWrapperX<HostProductDO>()
                .eqIfPresent(HostProductDO::getProjectId, projectId));
    }

    /**
     * 分页查询
     */
    default List<HostProductDO> selectPage(HostProductPageReq req) {
        return selectList(new LambdaQueryWrapperX<HostProductDO>()
                .eqIfPresent(HostProductDO::getHostId, req.getHostId())
                .eqIfPresent(HostProductDO::getProductLineId, req.getProductLineId())
                .eqIfPresent(HostProductDO::getProjectId, req.getProjectId())
                .eqIfPresent(HostProductDO::getRole, req.getRole())
                .eqIfPresent(HostProductDO::getEnv, req.getEnv())
                .orderByDesc(HostProductDO::getCreateTime));
    }
}
