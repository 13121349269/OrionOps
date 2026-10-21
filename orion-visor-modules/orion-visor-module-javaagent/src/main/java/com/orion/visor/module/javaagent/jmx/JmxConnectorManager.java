package com.orion.visor.module.javaagent.jmx;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.javaagent.dal.dataobject.JavaAppDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.orion.visor.module.javaagent.enums.JavaAgentErrorCodeConstants.*;

/**
 * JMX 连接管理器
 *
 * <p>通过 JMX 远程连接目标 Java 应用，采集 JVM 指标。
 * 支持 SSL + 认证。
 * 所有连接使用 try-with-resources 确保资源关闭，避免 RMI 连接泄露。</p>
 *
 * @author ops-platform
 */
@Slf4j
@Component
public class JmxConnectorManager {

    /**
     * 在 JMX 连接中执行操作 (自动关闭连接)
     *
     * @param app      Java应用配置
     * @param action   要执行的操作 (接收 MBeanServerConnection)
     * @param <T>      返回类型
     * @return 操作结果
     */
    public <T> T executeWithConnection(JavaAppDO app, Function<MBeanServerConnection, T> action) {
        try (JMXConnector connector = createConnector(app)) {
            MBeanServerConnection connection = connector.getMBeanServerConnection();
            return action.apply(connection);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("[executeWithConnection] JMX操作失败, app={}", app.getName(), e);
            throw ServiceException.of(JMX_CONNECT_FAILED);
        }
    }

    /**
     * 测试 JMX 连接
     */
    public boolean testConnection(JavaAppDO app) {
        try (JMXConnector connector = createConnector(app)) {
            MBeanServerConnection connection = connector.getMBeanServerConnection();
            // 测试读取 Runtime MBean
            ObjectName runtimeName = new ObjectName("java.lang:type=Runtime");
            connection.getAttribute(runtimeName, "Name");
            log.info("[testConnection] JMX连接测试成功, app={}", app.getName());
            return true;
        } catch (Exception e) {
            log.warn("[testConnection] JMX连接测试失败, app={}", app.getName());
            return false;
        }
    }

    /**
     * 创建 JMX 连接
     */
    private JMXConnector createConnector(JavaAppDO app) throws Exception {
        String url = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi",
                app.getJmxHost(), app.getJmxPort());
        JMXServiceURL serviceUrl = new JMXServiceURL(url);

        Map<String, Object> env = new HashMap<>();
        if (app.getJmxUsername() != null && !app.getJmxUsername().isEmpty()) {
            String[] credentials = new String[]{app.getJmxUsername(), app.getJmxPassword()};
            env.put(JMXConnector.CREDENTIALS, credentials);
        }

        return JMXConnectorFactory.connect(serviceUrl, env);
    }
}
