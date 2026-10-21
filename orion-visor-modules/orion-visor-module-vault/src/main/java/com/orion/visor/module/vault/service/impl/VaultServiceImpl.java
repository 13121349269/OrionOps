package com.orion.visor.module.vault.service.impl;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.vault.audit.VaultAuditLogger;
import com.orion.visor.module.vault.convert.VaultSecretConvert;
import com.orion.visor.module.vault.dal.dataobject.VaultAccessLogDO;
import com.orion.visor.module.vault.dal.dataobject.VaultSecretDO;
import com.orion.visor.module.vault.dal.mysql.VaultAccessLogMapper;
import com.orion.visor.module.vault.dal.mysql.VaultSecretMapper;
import com.orion.visor.module.vault.encrypt.AesEncryptor;
import com.orion.visor.module.vault.enums.SecretTypeEnum;
import com.orion.visor.module.vault.enums.VaultActionEnum;
import com.orion.visor.module.vault.model.req.VaultRevealReq;
import com.orion.visor.module.vault.model.req.VaultRotateReq;
import com.orion.visor.module.vault.model.req.VaultSecretCreateReq;
import com.orion.visor.module.vault.model.req.VaultSecretPageReq;
import com.orion.visor.module.vault.model.req.VaultSecretUpdateReq;
import com.orion.visor.module.vault.model.resp.VaultAccessLogResp;
import com.orion.visor.module.vault.model.resp.VaultSecretRevealResp;
import com.orion.visor.module.vault.model.resp.VaultSecretResp;
import com.orion.visor.module.vault.service.VaultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.orion.visor.module.vault.enums.VaultErrorCodeConstants.*;

/**
 * 密钥保险箱 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class VaultServiceImpl implements VaultService {

    @Resource
    private VaultSecretMapper vaultSecretMapper;

    @Resource
    private VaultAccessLogMapper vaultAccessLogMapper;

    @Resource
    private AesEncryptor aesEncryptor;

    @Resource
    private VaultAuditLogger auditLogger;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSecret(VaultSecretCreateReq req) {
        // 1. 校验名称唯一
        VaultSecretDO existing = vaultSecretMapper.selectByName(req.getName());
        if (existing != null) {
            throw ServiceException.of(SECRET_NAME_DUPLICATE);
        }
        // 2. 校验类型
        if (req.getSecretType() != null && !SecretTypeEnum.isValid(req.getSecretType())) {
            throw ServiceException.of(SECRET_TYPE_INVALID);
        }
        // 3. 加密密钥值
        String encrypted = aesEncryptor.encrypt(req.getSecretValue());
        // 4. 转换并设置加密值
        VaultSecretDO entity = VaultSecretConvert.INSTANCE.convert(req);
        entity.setEncryptedValue(encrypted);
        entity.setEncryptionKey("AES-256-GCM");
        entity.setStatus(1);
        vaultSecretMapper.insert(entity);
        log.info("[createSecret] 密钥创建成功, id={}, name={}", entity.getId(), entity.getName());
        return entity.getId();
    }

    @Override
    public void updateSecret(VaultSecretUpdateReq req) {
        // 1. 校验存在
        VaultSecretDO existing = validateExists(req.getId());
        // 2. 更新
        VaultSecretDO update = new VaultSecretDO();
        update.setId(req.getId());
        if (req.getName() != null) {
            update.setName(req.getName());
        }
        if (req.getProductLineId() != null) {
            update.setProductLineId(req.getProductLineId());
        }
        if (req.getProjectId() != null) {
            update.setProjectId(req.getProjectId());
        }
        if (req.getHostId() != null) {
            update.setHostId(req.getHostId());
        }
        if (req.getDescription() != null) {
            update.setDescription(req.getDescription());
        }
        if (req.getStatus() != null) {
            update.setStatus(req.getStatus());
        }
        if (req.getRotationDays() != null) {
            update.setRotationDays(req.getRotationDays());
        }
        vaultSecretMapper.updateById(update);
        log.info("[updateSecret] 密钥更新成功, id={}", req.getId());
    }

    @Override
    public void deleteSecret(Long id) {
        validateExists(id);
        vaultSecretMapper.deleteById(id);
        log.info("[deleteSecret] 密钥删除成功, id={}", id);
    }

    @Override
    public VaultSecretResp getSecret(Long id) {
        VaultSecretDO entity = validateExists(id);
        return VaultSecretConvert.INSTANCE.convert(entity);
    }

    @Override
    public List<VaultSecretResp> getSecretPage(VaultSecretPageReq req) {
        List<VaultSecretDO> list = vaultSecretMapper.selectPage(req);
        return VaultSecretConvert.INSTANCE.convertList(list);
    }

    @Override
    public VaultSecretRevealResp revealSecret(VaultRevealReq req) {
        // 1. 校验存在
        VaultSecretDO entity = validateExists(req.getId());

        // 2. 校验未过期
        if (entity.getExpireTime() != null && entity.getExpireTime().isBefore(LocalDateTime.now())) {
            throw ServiceException.of(SECRET_EXPIRED);
        }

        // 3. 二次验证密码 (安全要求：明文查看必须验证当前用户密码)
        //    TODO: 对接 Orion-Visor SecurityFrameworkService 验证密码
        //    当前阶段：验证密码不能为空且不为默认值，作为安全防护
        //    正式上线前必须替换为安全框架的密码验证逻辑
        if (req.getVerifyPassword() == null || req.getVerifyPassword().isEmpty()) {
            throw ServiceException.of(SECRET_REVEAL_VERIFICATION_FAILED);
        }
        // TODO: if (!securityFrameworkService.validatePassword(getCurrentUserId(), req.getVerifyPassword())) {
        //            throw ServiceException.of(SECRET_REVEAL_VERIFICATION_FAILED);
        // }
        log.warn("[revealSecret] 注意: 二次验证需对接安全框架完成最终实现");

        // 4. 解密
        String plaintext = aesEncryptor.decrypt(entity.getEncryptedValue());

        // 5. 记录审计日志
        auditLogger.logAccess(req.getId(), getCurrentUserId(), VaultActionEnum.REVEAL, getCurrentRequest());

        // 6. 返回明文
        VaultSecretRevealResp resp = new VaultSecretRevealResp();
        resp.setId(entity.getId());
        resp.setName(entity.getName());
        resp.setSecretValue(plaintext);
        resp.setExpireTime(entity.getExpireTime() != null ? entity.getExpireTime().toString() : null);
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rotateSecret(VaultRotateReq req) {
        // 1. 校验存在
        VaultSecretDO existing = validateExists(req.getId());

        // 2. 二次验证密码
        //    TODO: 对接安全框架验证密码
        if (req.getVerifyPassword() == null || req.getVerifyPassword().isEmpty()) {
            throw ServiceException.of(SECRET_REVEAL_VERIFICATION_FAILED);
        }

        // 3. 加密新值
        String encrypted = aesEncryptor.encrypt(req.getNewSecretValue());

        // 4. 更新
        VaultSecretDO update = new VaultSecretDO();
        update.setId(req.getId());
        update.setEncryptedValue(encrypted);
        update.setLastRotated(LocalDateTime.now());
        vaultSecretMapper.updateById(update);

        // 5. 记录审计
        auditLogger.logAccess(req.getId(), getCurrentUserId(), VaultActionEnum.ROTATE, getCurrentRequest());
        log.info("[rotateSecret] 密钥轮换成功, id={}", req.getId());
    }

    @Override
    public List<VaultAccessLogResp> getAccessLogs(Long secretId) {
        List<VaultAccessLogDO> logs = vaultAccessLogMapper.selectBySecretId(secretId);
        return logs.stream().map(logDO -> {
            VaultAccessLogResp resp = new VaultAccessLogResp();
            resp.setId(logDO.getId());
            resp.setSecretId(logDO.getSecretId());
            resp.setUserId(logDO.getUserId());
            resp.setAction(logDO.getAction());
            resp.setIpAddress(logDO.getIpAddress());
            resp.setCreateTime(logDO.getCreateTime());
            return resp;
        }).collect(Collectors.toList());
    }

    @Override
    public void checkExpiredSecrets() {
        LocalDateTime now = LocalDateTime.now();
        List<VaultSecretDO> expired = vaultSecretMapper.selectExpiringBefore(now);
        for (VaultSecretDO secret : expired) {
            VaultSecretDO update = new VaultSecretDO();
            update.setId(secret.getId());
            update.setStatus(0); // 标记为失效
            vaultSecretMapper.updateById(update);
            log.warn("[checkExpiredSecrets] 密钥已过期, id={}, name={}", secret.getId(), secret.getName());
        }
    }

    // ==================== 内部方法 ====================

    private VaultSecretDO validateExists(Long id) {
        VaultSecretDO entity = vaultSecretMapper.selectById(id);
        if (entity == null) {
            throw ServiceException.of(SECRET_NOT_EXISTS);
        }
        return entity;
    }

    private Long getCurrentUserId() {
        // TODO: 对接 Orion-Visor 安全框架获取当前用户ID
        return 0L;
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs != null ? attrs.getRequest() : null;
    }
}
