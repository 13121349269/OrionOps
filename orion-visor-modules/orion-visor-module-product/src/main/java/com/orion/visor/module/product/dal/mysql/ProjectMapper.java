package com.orion.visor.module.product.dal.mysql;

import com.orion.visor.module.product.dal.dataobject.ProjectDO;
import com.orion.visor.module.product.model.req.ProjectPageReq;
import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 项目 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface ProjectMapper extends BaseMapperX<ProjectDO> {

    /**
     * 根据产品线ID和编码查询项目
     */
    default ProjectDO selectByLineAndCode(Long productLineId, String code) {
        return selectOne(new LambdaQueryWrapperX<ProjectDO>()
                .eqIfPresent(ProjectDO::getProductLineId, productLineId)
                .eqIfPresent(ProjectDO::getCode, code));
    }

    /**
     * 分页查询项目列表
     */
    default List<ProjectDO> selectPage(ProjectPageReq req) {
        return selectList(new LambdaQueryWrapperX<ProjectDO>()
                .eqIfPresent(ProjectDO::getProductLineId, req.getProductLineId())
                .likeIfPresent(ProjectDO::getName, req.getName())
                .eqIfPresent(ProjectDO::getCode, req.getCode())
                .eqIfPresent(ProjectDO::getStatus, req.getStatus())
                .eqIfPresent(ProjectDO::getOwnerId, req.getOwnerId())
                .eqIfPresent(ProjectDO::getTechStack, req.getTechStack())
                .orderByAsc(ProjectDO::getSort)
                .orderByDesc(ProjectDO::getCreateTime));
    }

    /**
     * 根据产品线ID查询项目列表
     */
    default List<ProjectDO> selectByProductLineId(Long productLineId) {
        return selectList(new LambdaQueryWrapperX<ProjectDO>()
                .eqIfPresent(ProjectDO::getProductLineId, productLineId)
                .eq(ProjectDO::getStatus, 1)
                .orderByAsc(ProjectDO::getSort));
    }
}
