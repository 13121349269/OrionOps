package com.orion.visor.module.container.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.container.dal.dataobject.DockerActionLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Docker 操作日志 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface DockerActionLogMapper extends BaseMapperX<DockerActionLogDO> {

    /**
     * 按主机ID查询日志
     */
    default List<DockerActionLogDO> selectByHostId(Long dockerHostId) {
        return selectList(new LambdaQueryWrapperX<DockerActionLogDO>()
                .eqIfPresent(DockerActionLogDO::getDockerHostId, dockerHostId)
                .orderByDesc(DockerActionLogDO::getCreateTime));
    }

    /**
     * 按容器ID查询日志
     */
    default List<DockerActionLogDO> selectByContainerId(String containerId) {
        return selectList(new LambdaQueryWrapperX<DockerActionLogDO>()
                .eqIfPresent(DockerActionLogDO::getContainerId, containerId)
                .orderByDesc(DockerActionLogDO::getCreateTime));
    }
}
