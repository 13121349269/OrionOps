package com.orion.visor.module.vault.encrypt;

import com.orion.visor.framework.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import static com.orion.visor.module.vault.enums.VaultErrorCodeConstants.*;

/**
 * AES-256-GCM 加密工具
 *
 * <p>使用复用 Orion-Visor starter-cipher 的加密密钥，通过配置项注入。
 * 如未配置则自动生成一个临时密钥（仅用于开发环境）。</p>
 *
 * @author ops-platform
 */
@Slf4j
@Component
public class AesEncryptor {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    @Value("${orion.visor.vault.aes-key:}")
    private String aesKeyConfig;

    private SecretKeySpec secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    @PostConstruct
    public void init() {
        String key = aesKeyConfig;
        if (key == null || key.isEmpty()) {
            // 开发环境兜底：自动生成 32 字节密钥
            byte[] keyBytes = new byte[32];
            secureRandom.nextBytes(keyBytes);
            key = Base64.getEncoder().encodeToString(keyBytes);
            log.warn("[AesEncryptor] 未配置 orion.visor.vault.aes-key，已自动生成临时密钥 (仅限开发环境!)");
        }
        // 确保密钥长度为 32 字节 (AES-256)
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            byte[] padded = new byte[32];
            System.arraycopy(keyBytes, 0, padded, 0, Math.min(keyBytes.length, 32));
            keyBytes = padded;
        } else if (keyBytes.length > 32) {
            byte[] trimmed = new byte[32];
            System.arraycopy(keyBytes, 0, trimmed, 0, 32);
            keyBytes = trimmed;
        }
        secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        log.info("[AesEncryptor] AES-256-GCM 加密组件初始化完成");
    }

    /**
     * 加密明文
     *
     * @param plaintext 明文
     * @return Base64 编码的加密结果 (IV + 密文)
     */
    public String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            // 拼接 IV + 密文
            byte[] combined = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            log.error("[encrypt] 加密失败", e);
            throw ServiceException.of(SECRET_ENCRYPT_FAILED);
        }
    }

    /**
     * 解密
     *
     * @param encrypted Base64 编码的加密结果
     * @return 明文
     */
    public String decrypt(String encrypted) {
        try {
            byte[] combined = Base64.getDecoder().decode(encrypted);
            byte[] iv = new byte[IV_LENGTH];
            byte[] ciphertext = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
            System.arraycopy(combined, IV_LENGTH, ciphertext, 0, ciphertext.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            byte[] plaintext = cipher.doFinal(ciphertext);

            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[decrypt] 解密失败", e);
            throw ServiceException.of(SECRET_DECRYPT_FAILED);
        }
    }
}
