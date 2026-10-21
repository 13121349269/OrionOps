package com.orion.visor.module.vault.audit;

import com.orion.visor.module.vault.dal.dataobject.VaultAccessLogDO;
import com.orion.visor.module.vault.dal.mysql.VaultAccessLogMapper;
import com.orion.visor.module.vault.enums.VaultActionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 密钥访问审计组件
 *
 * @author ops-platform
 */
@Slf4j
@Component
public class VaultAuditLogger {

    @Resource
    private VaultAccessLogMapper accessLogMapper;

    /**
     * 异步记录访问日志
     *
     * @param secretId 密钥ID
     * @param userId   用户ID
     * @param action   操作类型
     * @param request  HTTP请求 (获取IP)
     */
    @Async
    public void logAccess(Long secretId, Long userId, VaultActionEnum action, HttpServletRequest request) {
        try {
            VaultAccessLogDO logDO = new VaultAccessLogDO();
            logDO.setSecretId(secretId);
            logDO.setUserId(userId);
            logDO.setAction(action.getCode());
            logDO.setIpAddress(getClientIp(request));
            accessLogMapper.insert(logDO);
            log.info("[logAccess] 密钥访问审计, secretId={}, userId={}, action={}", secretId, userId, action.getCode());
        } catch (Exception e) {
            log.error("[logAccess] 审计日志记录失败", e);
        }
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多层代理取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
