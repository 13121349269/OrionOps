package com.orion.visor.module.javaagent.service.impl;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.javaagent.convert.JavaAppConvert;
import com.orion.visor.module.javaagent.dal.dataobject.JavaAppDO;
import com.orion.visor.module.javaagent.dal.dataobject.JvmSnapshotDO;
import com.orion.visor.module.javaagent.dal.mysql.JavaAppMapper;
import com.orion.visor.module.javaagent.dal.mysql.JvmSnapshotMapper;
import com.orion.visor.module.javaagent.jmx.JmxConnectorManager;
import com.orion.visor.module.javaagent.model.req.JavaAppCreateReq;
import com.orion.visor.module.javaagent.model.req.JavaAppPageReq;
import com.orion.visor.module.javaagent.model.req.JavaAppUpdateReq;
import com.orion.visor.module.javaagent.model.resp.JavaAppResp;
import com.orion.visor.module.javaagent.model.resp.JvmSnapshotResp;
import com.orion.visor.module.javaagent.service.JavaAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.orion.visor.module.javaagent.enums.JavaAgentErrorCodeConstants.*;

/**
 * Java JVM 诊断 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class JavaAgentServiceImpl implements JavaAgentService {

    @Resource
    private JavaAppMapper javaAppMapper;

    @Resource
    private JvmSnapshotMapper jvmSnapshotMapper;

    @Resource
    private JmxConnectorManager jmxConnectorManager;

    @Override
    public Long createJavaApp(JavaAppCreateReq req) {
        // 1. 校验名称唯一
        JavaAppDO existing = javaAppMapper.selectByName(req.getName());
        if (existing != null) {
            throw ServiceException.of(JAVA_APP_NAME_DUPLICATE);
        }
        // 2. 转换并插入
        JavaAppDO entity = JavaAppConvert.INSTANCE.convert(req);
        entity.setStatus(1);
        // TODO: JMX密码加密存储 (可复用 module-vault 的 AesEncryptor)
        javaAppMapper.insert(entity);
        log.info("[createJavaApp] Java应用创建成功, id={}, name={}", entity.getId(), entity.getName());
        return entity.getId();
    }

    @Override
    public void updateJavaApp(JavaAppUpdateReq req) {
        JavaAppDO existing = validateExists(req.getId());
        JavaAppDO update = new JavaAppDO();
        update.setId(req.getId());
        JavaAppConvert.INSTANCE.convert(req, update);
        javaAppMapper.updateById(update);
        log.info("[updateJavaApp] Java应用更新成功, id={}", req.getId());
    }

    @Override
    public void deleteJavaApp(Long id) {
        validateExists(id);
        javaAppMapper.deleteById(id);
        log.info("[deleteJavaApp] Java应用删除成功, id={}", id);
    }

    @Override
    public boolean testConnection(Long id) {
        JavaAppDO app = validateExists(id);
        boolean connected = jmxConnectorManager.testConnection(app);
        // 更新检测时间
        JavaAppDO update = new JavaAppDO();
        update.setId(id);
        update.setLastCheck(LocalDateTime.now());
        javaAppMapper.updateById(update);
        return connected;
    }

    @Override
    public List<JavaAppResp> getJavaAppPage(JavaAppPageReq req) {
        List<JavaAppDO> list = javaAppMapper.selectPage(req);
        return JavaAppConvert.INSTANCE.convertList(list);
    }

    @Override
    public JavaAppResp getJavaApp(Long id) {
        JavaAppDO entity = validateExists(id);
        return JavaAppConvert.INSTANCE.convert(entity);
    }

    @Override
    public JvmSnapshotResp collectSnapshot(Long appId) {
        JavaAppDO app = validateExists(appId);
        if (app.getStatus() == null || app.getStatus() == 0) {
            throw ServiceException.of(JAVA_APP_DISABLED);
        }

        // 使用 executeWithConnection 确保 JMX 连接自动关闭，避免 RMI 资源泄露
        JvmSnapshotDO snapshot = jmxConnectorManager.executeWithConnection(app, connection -> {
            try {
                return collectMetrics(connection, appId);
            } catch (Exception e) {
                log.error("[collectSnapshot] 指标采集失败, appId={}", appId, e);
                throw new RuntimeException(e);
            }
        });

        jvmSnapshotMapper.insert(snapshot);
        log.info("[collectSnapshot] 指标采集成功, appId={}", appId);
        return JavaAppConvert.INSTANCE.convertSnapshot(snapshot);
    }

    @Override
    public List<JvmSnapshotResp> getSnapshotHistory(Long appId, int limit) {
        List<JvmSnapshotDO> list = jvmSnapshotMapper.selectByAppId(appId, limit);
        return list.stream()
                .map(JavaAppConvert.INSTANCE::convertSnapshot)
                .collect(Collectors.toList());
    }

    // ==================== JMX 指标采集 ====================

    /**
     * 采集 JVM 指标
     */
    private JvmSnapshotDO collectMetrics(MBeanServerConnection connection, Long appId) throws Exception {
        JvmSnapshotDO snapshot = new JvmSnapshotDO();
        snapshot.setAppId(appId);
        snapshot.setCreateTime(LocalDateTime.now());

        // Runtime MBean - 运行时长
        ObjectName runtimeName = new ObjectName("java.lang:type=Runtime");
        Long uptime = (Long) connection.getAttribute(runtimeName, "Uptime");
        snapshot.setUptime(uptime);

        // Memory MBean - 堆内存
        ObjectName memoryName = new ObjectName("java.lang:type=Memory");
        CompositeData heap = (CompositeData) connection.getAttribute(memoryName, "HeapMemoryUsage");
        if (heap != null) {
            snapshot.setHeapUsed(toLong(heap.get("used")));
            snapshot.setHeapMax(toLong(heap.get("max")));
        }
        CompositeData nonHeap = (CompositeData) connection.getAttribute(memoryName, "NonHeapMemoryUsage");
        if (nonHeap != null) {
            snapshot.setNonHeapUsed(toLong(nonHeap.get("used")));
        }

        // Threading MBean
        ObjectName threadName = new ObjectName("java.lang:type=Threading");
        snapshot.setThreadCount((Integer) connection.getAttribute(threadName, "ThreadCount"));
        snapshot.setThreadDaemon((Integer) connection.getAttribute(threadName, "DaemonThreadCount"));
        snapshot.setThreadPeak((Integer) connection.getAttribute(threadName, "PeakThreadCount"));

        // ClassLoading MBean
        ObjectName className = new ObjectName("java.lang:type=ClassLoading");
        snapshot.setClassCount((Integer) connection.getAttribute(className, "LoadedClassCount"));

        // GarbageCollector MBeans
        // Minor GC (Copy 或 ParNew 等)
        try {
            ObjectName gcMinorName = new ObjectName("java.lang:type=GarbageCollector,name=PS Scavenge");
            snapshot.setGcMinorCount((Integer) connection.getAttribute(gcMinorName, "CollectionCount"));
            snapshot.setGcMinorTime((Long) connection.getAttribute(gcMinorName, "CollectionTime"));
        } catch (Exception e) {
            log.debug("[collectMetrics] Minor GC MBean 不可用");
        }

        // Major GC (MarkSweep 等)
        try {
            ObjectName gcMajorName = new ObjectName("java.lang:type=GarbageCollector,name=PS MarkSweep");
            snapshot.setGcMajorCount((Integer) connection.getAttribute(gcMajorName, "CollectionCount"));
            snapshot.setGcMajorTime((Long) connection.getAttribute(gcMajorName, "CollectionTime"));
        } catch (Exception e) {
            log.debug("[collectMetrics] Major GC MBean 不可用");
        }

        // OperatingSystem MBean - CPU 使用率
        try {
            ObjectName osName = new ObjectName("java.lang:type=OperatingSystem");
            Double cpuUsage = (Double) connection.getAttribute(osName, "ProcessCpuLoad");
            if (cpuUsage != null) {
                snapshot.setCpuUsage(Math.round(cpuUsage * 100 * 100) / 100.0);
            }
        } catch (Exception e) {
            log.debug("[collectMetrics] OperatingSystem MBean CPU 指标不可用");
        }

        return snapshot;
    }

    private Long toLong(Object value) {
        if (value == null) return null;
        if (value instanceof Long) return (Long) value;
        if (value instanceof Integer) return ((Integer) value).longValue();
        if (value instanceof Number) return ((Number) value).longValue();
        return null;
    }

    private JavaAppDO validateExists(Long id) {
        JavaAppDO entity = javaAppMapper.selectById(id);
        if (entity == null) {
            throw ServiceException.of(JAVA_APP_NOT_EXISTS);
        }
        return entity;
    }
}
