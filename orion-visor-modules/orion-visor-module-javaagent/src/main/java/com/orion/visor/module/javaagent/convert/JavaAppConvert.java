package com.orion.visor.module.javaagent.convert;

import com.orion.visor.module.javaagent.dal.dataobject.JavaAppDO;
import com.orion.visor.module.javaagent.dal.dataobject.JvmSnapshotDO;
import com.orion.visor.module.javaagent.model.req.JavaAppCreateReq;
import com.orion.visor.module.javaagent.model.req.JavaAppUpdateReq;
import com.orion.visor.module.javaagent.model.resp.JavaAppResp;
import com.orion.visor.module.javaagent.model.resp.JvmSnapshotResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Java应用转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface JavaAppConvert {

    JavaAppConvert INSTANCE = Mappers.getMapper(JavaAppConvert.class);

    JavaAppDO convert(JavaAppCreateReq req);

    void convert(JavaAppUpdateReq req, @MappingTarget JavaAppDO entity);

    JavaAppResp convert(JavaAppDO entity);

    List<JavaAppResp> convertList(List<JavaAppDO> list);

    default JvmSnapshotResp convertSnapshot(JvmSnapshotDO entity) {
        if (entity == null) return null;
        JvmSnapshotResp resp = new JvmSnapshotResp();
        resp.setAppId(entity.getAppId());
        resp.setHeapUsed(entity.getHeapUsed());
        resp.setHeapMax(entity.getHeapMax());
        resp.setHeapUsagePercent(
                entity.getHeapMax() != null && entity.getHeapMax() > 0
                        ? Math.round((double) entity.getHeapUsed() / entity.getHeapMax() * 100 * 100) / 100.0
                        : null
        );
        resp.setNonHeapUsed(entity.getNonHeapUsed());
        resp.setThreadCount(entity.getThreadCount());
        resp.setThreadDaemon(entity.getThreadDaemon());
        resp.setThreadPeak(entity.getThreadPeak());
        resp.setClassCount(entity.getClassCount());
        resp.setGcMinorCount(entity.getGcMinorCount());
        resp.setGcMinorTime(entity.getGcMinorTime());
        resp.setGcMajorCount(entity.getGcMajorCount());
        resp.setGcMajorTime(entity.getGcMajorTime());
        resp.setCpuUsage(entity.getCpuUsage());
        resp.setUptime(entity.getUptime());
        resp.setCreateTime(entity.getCreateTime());
        return resp;
    }
}
