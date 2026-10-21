package com.orion.visor.module.vault.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 密钥访问日志 DO
 *
 * @author ops-platform
 */
@Data
@TableName("ops_vault_access_log")
public class VaultAccessLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 密钥ID
     */
    private Long secretId;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作 (view/reveal/rotate/delete/export)
     */
    private String action;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;
}
