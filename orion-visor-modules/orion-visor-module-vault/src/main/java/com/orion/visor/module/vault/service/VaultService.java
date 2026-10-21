package com.orion.visor.module.vault.service;

import com.orion.visor.module.vault.model.req.VaultRevealReq;
import com.orion.visor.module.vault.model.req.VaultSecretCreateReq;
import com.orion.visor.module.vault.model.req.VaultSecretPageReq;
import com.orion.visor.module.vault.model.req.VaultSecretUpdateReq;
import com.orion.visor.module.vault.model.resp.VaultAccessLogResp;
import com.orion.visor.module.vault.model.resp.VaultSecretRevealResp;
import com.orion.visor.module.vault.model.resp.VaultSecretResp;

import java.util.List;

/**
 * 密钥保险箱 Service
 *
 * @author ops-platform
 */
public interface VaultService {

    /**
     * 创建密钥
     */
    Long createSecret(VaultSecretCreateReq req);

    /**
     * 更新密钥
     */
    void updateSecret(VaultSecretUpdateReq req);

    /**
     * 删除密钥
     */
    void deleteSecret(Long id);

    /**
     * 获取密钥详情 (不含明文)
     */
    VaultSecretResp getSecret(Long id);

    /**
     * 分页查询密钥列表
     */
    List<VaultSecretResp> getSecretPage(VaultSecretPageReq req);

    /**
     * 明文查看密钥 (需二次验证)
     */
    VaultSecretRevealResp revealSecret(VaultRevealReq req);

    /**
     * 轮换密钥
     */
    void rotateSecret(Long id, String newSecretValue);

    /**
     * 查询密钥访问日志
     */
    List<VaultAccessLogResp> getAccessLogs(Long secretId);

    /**
     * 检查过期密钥并标记
     */
    void checkExpiredSecrets();
}
