package com.orion.visor.module.javaagent.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.javaagent.dal.dataobject.JavaAppDO;
import com.orion.visor.module.javaagent.model.req.JavaAppPageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Java应用 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface JavaAppMapper extends BaseMapperX<JavaAppDO> {

    default JavaAppDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<JavaAppDO>()
                .eqIfPresent(JavaAppDO::getName, name));
    }

    default List<JavaAppDO> selectPage(JavaAppPageReq req) {
        return selectList(new LambdaQueryWrapperX<JavaAppDO>()
                .likeIfPresent(JavaAppDO::getName, req.getName())
                .eqIfPresent(JavaAppDO::getHostId, req.getHostId())
                .eqIfPresent(JavaAppDO::getProductLineId, req.getProductLineId())
                .eqIfPresent(JavaAppDO::getProjectId, req.getProjectId())
                .eqIfPresent(JavaAppDO::getStatus, req.getStatus())
                .orderByDesc(JavaAppDO::getCreateTime));
    }

    default List<JavaAppDO> selectEnabled() {
        return selectList(new LambdaQueryWrapperX<JavaAppDO>()
                .eq(JavaAppDO::getStatus, 1));
    }
}
