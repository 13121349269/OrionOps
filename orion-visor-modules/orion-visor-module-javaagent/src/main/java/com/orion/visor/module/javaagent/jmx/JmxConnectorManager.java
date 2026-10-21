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

import static com.orion.visor.module.javaagent.enums.JavaAgentErrorCodeConstants.*;

/**
 * JMX 连接管理器
 *
 * <p>通过 JMX 远程连接目标 Java 应用，采集 JVM 指标。
 * 支持 SSL + 认证。</p>
 *
 * @author ops-platform
 */
@Slf4j
@Component
public class JmxConnectorManager {

    /**
     * 创建 JMX 连接
     *
     * @param app Java应用配置
     * @return MBeanServerConnection
     */
    public MBeanServerConnection connect(JavaAppDO app) {
        try {
            String url = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi",
                    app.getJmxHost(), app.getJmxPort());
            JMXServiceURL serviceUrl = new JMXServiceURL(url);

            Map<String, Object> env = new HashMap<>();
            if (app.getJmxUsername() != null && !app.getJmxUsername().isEmpty()) {
                String[] credentials = new String[]{app.getJmxUsername(), app.getJmxPassword()};
                env.put(JMXConnector.CREDENTIALS, credentials);
            }

            JMXConnector connector = JMXConnectorFactory.connect(serviceUrl, env);
            MBeanServerConnection connection = connector.getMBeanServerConnection();

            log.info("[connect] JMX连接成功, app={}, host={}:{}", app.getName(), app.getJmxHost(), app.getJmxPort());
            return connection;
        } catch (Exception e) {
            log.error("[connect] JMX连接失败, app={}", app.getName(), e);
            throw ServiceException.of(JMX_CONNECT_FAILED);
        }
    }

    /**
     * 测试 JMX 连接
     */
    public boolean testConnection(JavaAppDO app) {
        try {
            JMXConnector connector = null;
            try {
                String url = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi",
                        app.getJmxHost(), app.getJmxPort());
                JMXServiceURL serviceUrl = new JMXServiceURL(url);

                Map<String, Object> env = new HashMap<>();
                if (app.getJmxUsername() != null && !app.getJmxUsername().isEmpty()) {
                    String[] credentials = new String[]{app.getJmxUsername(), app.getJmxPassword()};
                    env.put(JMXConnector.CREDENTIALS, credentials);
                }

                connector = JMXConnectorFactory.connect(serviceUrl, env);
                MBeanServerConnection connection = connector.getMBeanServerConnection();

                // 测试读取 Runtime MBean
                ObjectName runtimeName = new ObjectName("java.lang:type=Runtime");
                connection.getAttribute(runtimeName, "Name");

                return true;
            } finally {
                if (connector != null) {
                    connector.close();
                }
            }
        } catch (Exception e) {
            log.warn("[testConnection] JMX连接测试失败, app={}", app.getName());
            return false;
        }
    }
}
