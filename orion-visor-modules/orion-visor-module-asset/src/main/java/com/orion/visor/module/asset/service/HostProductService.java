package com.orion.visor.module.asset.service;

import com.orion.visor.module.asset.model.req.HostProductBindReq;
import com.orion.visor.module.asset.model.req.HostProductPageReq;
import com.orion.visor.module.asset.model.resp.HostProductResp;

import java.util.List;

/**
 * 主机产品线关联 Service
 *
 * @author ops-platform
 */
public interface HostProductService {

    /**
     * 绑定主机到产品线/项目
     *
     * @param req 绑定请求
     * @return 关联ID
     */
    Long bindHostProduct(HostProductBindReq req);

    /**
     * 更新绑定关系
     *
     * @param req 绑定请求 (hostId 不变，其余可更新)
     */
    void updateBindHostProduct(HostProductBindReq req);

    /**
     * 解绑主机
     *
     * @param hostId 主机ID
     */
    void unbindHostProduct(Long hostId);

    /**
     * 根据主机ID查询关联
     *
     * @param hostId 主机ID
     * @return 关联信息
     */
    HostProductResp getByHostId(Long hostId);

    /**
     * 分页查询关联列表
     *
     * @param req 查询请求
     * @return 关联列表
     */
    List<HostProductResp> getHostProductPage(HostProductPageReq req);

    /**
     * 根据产品线ID查询主机列表
     *
     * @param productLineId 产品线ID
     * @return 关联列表
     */
    List<HostProductResp> listByProductLineId(Long productLineId);

    /**
     * 根据项目ID查询主机列表
     *
     * @param projectId 项目ID
     * @return 关联列表
     */
    List<HostProductResp> listByProjectId(Long projectId);
}
