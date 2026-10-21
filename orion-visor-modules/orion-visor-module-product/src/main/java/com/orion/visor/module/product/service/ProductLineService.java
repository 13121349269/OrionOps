package com.orion.visor.module.product.service;

import com.orion.visor.module.product.model.req.ProductLineCreateReq;
import com.orion.visor.module.product.model.req.ProductLinePageReq;
import com.orion.visor.module.product.model.req.ProductLineUpdateReq;
import com.orion.visor.module.product.model.resp.ProductLineResp;

import java.util.List;

/**
 * 产品线 Service
 *
 * @author ops-platform
 */
public interface ProductLineService {

    /**
     * 创建产品线
     *
     * @param req 创建请求
     * @return 产品线ID
     */
    Long createProductLine(ProductLineCreateReq req);

    /**
     * 更新产品线
     *
     * @param req 更新请求
     */
    void updateProductLine(ProductLineUpdateReq req);

    /**
     * 删除产品线
     *
     * @param id 产品线ID
     */
    void deleteProductLine(Long id);

    /**
     * 获取产品线详情
     *
     * @param id 产品线ID
     * @return 产品线响应
     */
    ProductLineResp getProductLine(Long id);

    /**
     * 分页查询产品线列表
     *
     * @param req 查询请求
     * @return 产品线列表
     */
    List<ProductLineResp> getProductLinePage(ProductLinePageReq req);

    /**
     * 获取所有启用的产品线 (下拉框用)
     *
     * @return 启用的产品线列表
     */
    List<ProductLineResp> getEnabledProductLineList();
}
