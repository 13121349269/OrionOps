package com.orion.visor.module.vault.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.vault.dal.dataobject.VaultAccessLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 密钥访问日志 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface VaultAccessLogMapper extends BaseMapperX<VaultAccessLogDO> {

    /**
     * 根据密钥ID查询日志
     */
    default List<VaultAccessLogDO> selectBySecretId(Long secretId) {
        return selectList(new LambdaQueryWrapperX<VaultAccessLogDO>()
                .eqIfPresent(VaultAccessLogDO::getSecretId, secretId)
                .orderByDesc(VaultAccessLogDO::getCreateTime));
    }

    /**
     * 根据用户ID查询日志
     */
    default List<VaultAccessLogDO> selectByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<VaultAccessLogDO>()
                .eqIfPresent(VaultAccessLogDO::getUserId, userId)
                .orderByDesc(VaultAccessLogDO::getCreateTime));
    }
}
