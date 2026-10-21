package com.orion.visor.module.javaagent.service;

import com.orion.visor.module.javaagent.model.req.JavaAppCreateReq;
import com.orion.visor.module.javaagent.model.req.JavaAppPageReq;
import com.orion.visor.module.javaagent.model.req.JavaAppUpdateReq;
import com.orion.visor.module.javaagent.model.resp.JavaAppResp;
import com.orion.visor.module.javaagent.model.resp.JvmSnapshotResp;

import java.util.List;

/**
 * Java JVM 诊断 Service
 *
 * @author ops-platform
 */
public interface JavaAgentService {

    /**
     * 创建 Java 应用监控
     */
    Long createJavaApp(JavaAppCreateReq req);

    /**
     * 更新 Java 应用
     */
    void updateJavaApp(JavaAppUpdateReq req);

    /**
     * 删除 Java 应用
     */
    void deleteJavaApp(Long id);

    /**
     * 测试 JMX 连接
     */
    boolean testConnection(Long id);

    /**
     * 分页查询
     */
    List<JavaAppResp> getJavaAppPage(JavaAppPageReq req);

    /**
     * 获取详情
     */
    JavaAppResp getJavaApp(Long id);

    /**
     * 采集 JVM 指标快照
     */
    JvmSnapshotResp collectSnapshot(Long appId);

    /**
     * 查询历史快照
     */
    List<JvmSnapshotResp> getSnapshotHistory(Long appId, int limit);
}
