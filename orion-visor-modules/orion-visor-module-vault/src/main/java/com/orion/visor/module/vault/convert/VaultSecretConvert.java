package com.orion.visor.module.vault.convert;

import com.orion.visor.module.vault.dal.dataobject.VaultSecretDO;
import com.orion.visor.module.vault.model.req.VaultSecretCreateReq;
import com.orion.visor.module.vault.model.req.VaultSecretUpdateReq;
import com.orion.visor.module.vault.model.resp.VaultSecretResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 密钥转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface VaultSecretConvert {

    VaultSecretConvert INSTANCE = Mappers.getMapper(VaultSecretConvert.class);

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 创建请求 -> DO (不映射 secretValue，由 Service 层加密后设置)
     */
    default VaultSecretDO convert(VaultSecretCreateReq req) {
        VaultSecretDO entity = new VaultSecretDO();
        entity.setName(req.getName());
        entity.setCategoryCode(req.getCategoryCode());
        entity.setProductLineId(req.getProductLineId());
        entity.setProjectId(req.getProjectId());
        entity.setHostId(req.getHostId());
        entity.setSecretType(req.getSecretType() != null ? req.getSecretType() : "text");
        entity.setDescription(req.getDescription());
        entity.setExpireTime(req.getExpireTime() != null && !req.getExpireTime().isEmpty()
                ? LocalDateTime.parse(req.getExpireTime(), FORMATTER) : null);
        entity.setRotationDays(req.getRotationDays() != null ? req.getRotationDays() : 0);
        entity.setStatus(1);
        return entity;
    }

    /**
     * 更新请求 -> DO (部分更新)
     */
    void convert(VaultSecretUpdateReq req, @MappingTarget VaultSecretDO entity);

    /**
     * DO -> 响应 VO
     */
    default VaultSecretResp convert(VaultSecretDO entity) {
        VaultSecretResp resp = new VaultSecretResp();
        resp.setId(entity.getId());
        resp.setName(entity.getName());
        resp.setCategoryCode(entity.getCategoryCode());
        resp.setProductLineId(entity.getProductLineId());
        resp.setProjectId(entity.getProjectId());
        resp.setHostId(entity.getHostId());
        resp.setSecretType(entity.getSecretType());
        resp.setDescription(entity.getDescription());
        resp.setStatus(entity.getStatus());
        resp.setExpireTime(entity.getExpireTime());
        resp.setLastRotated(entity.getLastRotated());
        resp.setRotationDays(entity.getRotationDays());
        resp.setCreateTime(entity.getCreateTime());
        resp.setUpdateTime(entity.getUpdateTime());
        resp.setExpired(entity.getExpireTime() != null && entity.getExpireTime().isBefore(LocalDateTime.now()));
        return resp;
    }

    /**
     * DO 列表 -> 响应 VO 列表
     */
    List<VaultSecretResp> convertList(List<VaultSecretDO> list);
}
