package com.orion.visor.module.product.service.impl;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.product.convert.ProductLineConvert;
import com.orion.visor.module.product.dal.dataobject.ProductLineDO;
import com.orion.visor.module.product.dal.mysql.ProductLineMapper;
import com.orion.visor.module.product.enums.ProductStatusEnum;
import com.orion.visor.module.product.model.req.ProductLineCreateReq;
import com.orion.visor.module.product.model.req.ProductLinePageReq;
import com.orion.visor.module.product.model.req.ProductLineUpdateReq;
import com.orion.visor.module.product.model.resp.ProductLineResp;
import com.orion.visor.module.product.service.ProductLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.orion.visor.module.product.enums.ProductErrorCodeConstants.*;

/**
 * 产品线 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class ProductLineServiceImpl implements ProductLineService {

    @Resource
    private ProductLineMapper productLineMapper;

    @Override
    public Long createProductLine(ProductLineCreateReq req) {
        // 1. 校验编码唯一
        validateCodeUnique(req.getCode(), null);
        // 2. 校验状态
        validateStatus(req.getStatus());
        // 3. 转换并插入
        ProductLineDO entity = ProductLineConvert.INSTANCE.convert(req);
        if (entity.getStatus() == null) {
            entity.setStatus(ProductStatusEnum.ENABLED.getStatus());
        }
        productLineMapper.insert(entity);
        log.info("[createProductLine] 产品线创建成功, id={}, code={}", entity.getId(), entity.getCode());
        return entity.getId();
    }

    @Override
    public void updateProductLine(ProductLineUpdateReq req) {
        // 1. 校验存在
        ProductLineDO existing = validateExists(req.getId());
        // 2. 校验状态
        validateStatus(req.getStatus());
        // 3. 更新
        ProductLineDO update = new ProductLineDO();
        ProductLineConvert.INSTANCE.convert(req, update);
        productLineMapper.updateById(update);
        log.info("[updateProductLine] 产品线更新成功, id={}", req.getId());
    }

    @Override
    public void deleteProductLine(Long id) {
        // 1. 校验存在
        validateExists(id);
        // 2. TODO: 校验是否有关联项目，有则不允许删除
        // 3. 逻辑删除
        productLineMapper.deleteById(id);
        log.info("[deleteProductLine] 产品线删除成功, id={}", id);
    }

    @Override
    public ProductLineResp getProductLine(Long id) {
        ProductLineDO entity = validateExists(id);
        return ProductLineConvert.INSTANCE.convert(entity);
    }

    @Override
    public List<ProductLineResp> getProductLinePage(ProductLinePageReq req) {
        List<ProductLineDO> list = productLineMapper.selectPage(req);
        // TODO: 批量查询 ownerName 填充
        return ProductLineConvert.INSTANCE.convertList(list);
    }

    @Override
    public List<ProductLineResp> getEnabledProductLineList() {
        List<ProductLineDO> list = productLineMapper.selectEnabledList();
        return ProductLineConvert.INSTANCE.convertList(list);
    }

    // ==================== 内部校验方法 ====================

    private ProductLineDO validateExists(Long id) {
        ProductLineDO entity = productLineMapper.selectById(id);
        if (entity == null) {
            throw ServiceException.of(PRODUCT_LINE_NOT_EXISTS);
        }
        return entity;
    }

    private void validateCodeUnique(String code, Long excludeId) {
        ProductLineDO existing = productLineMapper.selectByCode(code);
        if (existing != null && !Objects.equals(existing.getId(), excludeId)) {
            throw ServiceException.of(PRODUCT_LINE_CODE_DUPLICATE);
        }
    }

    private void validateStatus(Integer status) {
        if (status != null && !ProductStatusEnum.isValid(status)) {
            throw ServiceException.of(PRODUCT_LINE_STATUS_INVALID);
        }
    }
}
