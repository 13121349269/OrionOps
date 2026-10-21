package com.orion.visor.module.container.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.container.dal.dataobject.DockerHostDO;
import com.orion.visor.module.container.model.req.DockerHostPageReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Docker 主机 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface DockerHostMapper extends BaseMapperX<DockerHostDO> {

    /**
     * 根据 dockerHost 地址查询
     */
    default DockerHostDO selectByDockerHost(String dockerHost) {
        return selectOne(new LambdaQueryWrapperX<DockerHostDO>()
                .eqIfPresent(DockerHostDO::getDockerHost, dockerHost));
    }

    /**
     * 分页查询
     */
    default List<DockerHostDO> selectPage(DockerHostPageReq req) {
        return selectList(new LambdaQueryWrapperX<DockerHostDO>()
                .likeIfPresent(DockerHostDO::getName, req.getName())
                .eqIfPresent(DockerHostDO::getHostId, req.getHostId())
                .eqIfPresent(DockerHostDO::getStatus, req.getStatus())
                .orderByDesc(DockerHostDO::getCreateTime));
    }
}
