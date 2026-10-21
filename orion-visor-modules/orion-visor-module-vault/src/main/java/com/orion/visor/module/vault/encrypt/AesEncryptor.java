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

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    private SecretKeySpec secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    @PostConstruct
    public void init() {
        String key = aesKeyConfig;
        boolean isProd = "prod".equals(activeProfile);

        if (key == null || key.isEmpty()) {
            if (isProd) {
                // 生产环境必须配置密钥，否则启动失败
                throw new IllegalStateException(
                    "生产环境必须配置 orion.visor.vault.aes-key (32字节Base64编码密钥)");
            }
            // 开发环境兜底：自动生成 32 字节随机密钥
            byte[] keyBytes = new byte[32];
            secureRandom.nextBytes(keyBytes);
            key = Base64.getEncoder().encodeToString(keyBytes);
            log.warn("[AesEncryptor] 未配置 orion.visor.vault.aes-key，已自动生成临时密钥 (仅限开发环境!)");
        }

        // 尝试 Base64 解码，解码失败则使用 UTF-8 字节
        byte[] keyBytes;
        try {
            keyBytes = Base64.getDecoder().decode(key);
        } catch (IllegalArgumentException e) {
            // 非 Base64 编码，使用原始字节
            keyBytes = key.getBytes(StandardCharsets.UTF_8);
        }

        // AES-256 要求密钥必须为 32 字节
        // 安全处理：不使用零填充，密钥长度不匹配则抛异常
        if (keyBytes.length != 32) {
            if (isProd) {
                throw new IllegalStateException(
                    "AES密钥必须为32字节(Base64解码后)，当前: " + keyBytes.length + " 字节");
            }
            // 开发环境：使用 SHA-256 派生密钥而非零填充
            try {
                java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
                keyBytes = digest.digest(key.getBytes(StandardCharsets.UTF_8));
                log.warn("[AesEncryptor] 密钥长度不足32字节，已使用SHA-256派生密钥 (仅限开发环境!)");
            } catch (Exception e) {
                throw new IllegalStateException("密钥派生失败", e);
            }
        }

        secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        log.info("[AesEncryptor] AES-256-GCM 加密组件初始化完成, profile={}", activeProfile);
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
