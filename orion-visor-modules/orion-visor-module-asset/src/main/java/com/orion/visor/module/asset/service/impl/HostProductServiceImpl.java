package com.orion.visor.module.asset.service.impl;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.asset.convert.HostProductConvert;
import com.orion.visor.module.asset.dal.dataobject.HostProductDO;
import com.orion.visor.module.asset.dal.mysql.HostProductMapper;
import com.orion.visor.module.asset.enums.EnvEnum;
import com.orion.visor.module.asset.enums.HostRoleEnum;
import com.orion.visor.module.asset.model.req.HostProductBindReq;
import com.orion.visor.module.asset.model.req.HostProductPageReq;
import com.orion.visor.module.asset.model.resp.HostProductResp;
import com.orion.visor.module.asset.service.HostProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.orion.visor.module.asset.enums.AssetErrorCodeConstants.*;

/**
 * 主机产品线关联 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class HostProductServiceImpl implements HostProductService {

    @Resource
    private HostProductMapper hostProductMapper;

    @Override
    public Long bindHostProduct(HostProductBindReq req) {
        // 1. 校验主机未绑定
        HostProductDO existing = hostProductMapper.selectByHostId(req.getHostId());
        if (existing != null) {
            throw ServiceException.of(HOST_ALREADY_BOUND);
        }
        // 2. 校验角色
        if (req.getRole() != null && !HostRoleEnum.isValid(req.getRole())) {
            throw ServiceException.of(HOST_ROLE_INVALID);
        }
        // 3. 校验环境
        if (req.getEnv() != null && !EnvEnum.isValid(req.getEnv())) {
            throw ServiceException.of(HOST_ENV_INVALID);
        }
        // 4. TODO: 校验产品线/项目是否启用 (跨模块调用 module-product)
        // 5. 转换并插入
        HostProductDO entity = HostProductConvert.INSTANCE.convert(req);
        if (entity.getRole() == null) {
            entity.setRole(HostRoleEnum.OTHER.getCode());
        }
        if (entity.getEnv() == null) {
            entity.setEnv(EnvEnum.PROD.getCode());
        }
        hostProductMapper.insert(entity);
        log.info("[bindHostProduct] 主机绑定成功, hostId={}, productLineId={}", req.getHostId(), req.getProductLineId());
        return entity.getId();
    }

    @Override
    public void updateBindHostProduct(HostProductBindReq req) {
        // 1. 校验存在
        HostProductDO existing = hostProductMapper.selectByHostId(req.getHostId());
        if (existing == null) {
            throw ServiceException.of(HOST_PRODUCT_NOT_EXISTS);
        }
        // 2. 校验角色和环境
        if (req.getRole() != null && !HostRoleEnum.isValid(req.getRole())) {
            throw ServiceException.of(HOST_ROLE_INVALID);
        }
        if (req.getEnv() != null && !EnvEnum.isValid(req.getEnv())) {
            throw ServiceException.of(HOST_ENV_INVALID);
        }
        // 3. 更新
        HostProductDO update = new HostProductDO();
        update.setId(existing.getId());
        update.setProductLineId(req.getProductLineId());
        update.setProjectId(req.getProjectId());
        update.setRole(req.getRole() != null ? req.getRole() : existing.getRole());
        update.setEnv(req.getEnv() != null ? req.getEnv() : existing.getEnv());
        hostProductMapper.updateById(update);
        log.info("[updateBindHostProduct] 主机关联更新成功, hostId={}", req.getHostId());
    }

    @Override
    public void unbindHostProduct(Long hostId) {
        HostProductDO existing = hostProductMapper.selectByHostId(hostId);
        if (existing == null) {
            throw ServiceException.of(HOST_PRODUCT_NOT_EXISTS);
        }
        hostProductMapper.deleteById(existing.getId());
        log.info("[unbindHostProduct] 主机解绑成功, hostId={}", hostId);
    }

    @Override
    public HostProductResp getByHostId(Long hostId) {
        HostProductDO entity = hostProductMapper.selectByHostId(hostId);
        if (entity == null) {
            return null;
        }
        return HostProductConvert.INSTANCE.convert(entity);
    }

    @Override
    public List<HostProductResp> getHostProductPage(HostProductPageReq req) {
        List<HostProductDO> list = hostProductMapper.selectPage(req);
        return HostProductConvert.INSTANCE.convertList(list);
    }

    @Override
    public List<HostProductResp> listByProductLineId(Long productLineId) {
        List<HostProductDO> list = hostProductMapper.selectByProductLineId(productLineId);
        return HostProductConvert.INSTANCE.convertList(list);
    }

    @Override
    public List<HostProductResp> listByProjectId(Long projectId) {
        List<HostProductDO> list = hostProductMapper.selectByProjectId(projectId);
        return HostProductConvert.INSTANCE.convertList(list);
    }
}
