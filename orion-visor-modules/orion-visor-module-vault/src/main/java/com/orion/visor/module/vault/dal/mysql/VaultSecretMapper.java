package com.orion.visor.module.vault.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.vault.dal.dataobject.VaultSecretDO;
import com.orion.visor.module.vault.model.req.VaultSecretPageReq;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 密钥条目 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface VaultSecretMapper extends BaseMapperX<VaultSecretDO> {

    /**
     * 根据名称查询
     */
    default VaultSecretDO selectByName(String name) {
        return selectOne(new LambdaQueryWrapperX<VaultSecretDO>()
                .eqIfPresent(VaultSecretDO::getName, name));
    }

    /**
     * 分页查询
     */
    default List<VaultSecretDO> selectPage(VaultSecretPageReq req) {
        return selectList(new LambdaQueryWrapperX<VaultSecretDO>()
                .likeIfPresent(VaultSecretDO::getName, req.getName())
                .eqIfPresent(VaultSecretDO::getCategoryCode, req.getCategoryCode())
                .eqIfPresent(VaultSecretDO::getProductLineId, req.getProductLineId())
                .eqIfPresent(VaultSecretDO::getProjectId, req.getProjectId())
                .eqIfPresent(VaultSecretDO::getHostId, req.getHostId())
                .eqIfPresent(VaultSecretDO::getStatus, req.getStatus())
                .eqIfPresent(VaultSecretDO::getSecretType, req.getSecretType())
                .orderByDesc(VaultSecretDO::getCreateTime));
    }

    /**
     * 查询即将过期的密钥
     */
    default List<VaultSecretDO> selectExpiringBefore(LocalDateTime deadline) {
        return selectList(new LambdaQueryWrapperX<VaultSecretDO>()
                .isNotNull(VaultSecretDO::getExpireTime)
                .le(VaultSecretDO::getExpireTime, deadline)
                .eq(VaultSecretDO::getStatus, 1));
    }

    /**
     * 查询需要轮换的密钥
     */
    default List<VaultSecretDO> selectNeedRotation(LocalDateTime deadline) {
        return selectList(new LambdaQueryWrapperX<VaultSecretDO>()
                .gt(VaultSecretDO::getRotationDays, 0)
                .isNotNull(VaultSecretDO::getLastRotated)
                .le(VaultSecretDO::getLastRotated, deadline)
                .eq(VaultSecretDO::getStatus, 1));
    }
}
