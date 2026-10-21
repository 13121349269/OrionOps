package com.orion.visor.module.javaagent.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.javaagent.dal.dataobject.JvmSnapshotDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * JVM快照 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface JvmSnapshotMapper extends BaseMapperX<JvmSnapshotDO> {

    /**
     * 查询应用最近的N条快照
     */
    default List<JvmSnapshotDO> selectByAppId(Long appId, int limit) {
        return selectList(new LambdaQueryWrapperX<JvmSnapshotDO>()
                .eqIfPresent(JvmSnapshotDO::getAppId, appId)
                .orderByDesc(JvmSnapshotDO::getCreateTime)
                .last("LIMIT " + limit));
    }

    /**
     * 查询应用最近一条快照
     */
    default JvmSnapshotDO selectLatestByAppId(Long appId) {
        List<JvmSnapshotDO> list = selectByAppId(appId, 1);
        return list.isEmpty() ? null : list.get(0);
    }
}
