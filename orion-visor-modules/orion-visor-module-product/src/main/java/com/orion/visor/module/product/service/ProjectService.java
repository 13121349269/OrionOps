package com.orion.visor.module.product.service;

import com.orion.visor.module.product.model.req.ProjectCreateReq;
import com.orion.visor.module.product.model.req.ProjectPageReq;
import com.orion.visor.module.product.model.req.ProjectUpdateReq;
import com.orion.visor.module.product.model.resp.ProjectResp;

import java.util.List;

/**
 * 项目 Service
 *
 * @author ops-platform
 */
public interface ProjectService {

    /**
     * 创建项目
     */
    Long createProject(ProjectCreateReq req);

    /**
     * 更新项目
     */
    void updateProject(ProjectUpdateReq req);

    /**
     * 删除项目
     */
    void deleteProject(Long id);

    /**
     * 获取项目详情
     */
    ProjectResp getProject(Long id);

    /**
     * 分页查询项目列表
     */
    List<ProjectResp> getProjectPage(ProjectPageReq req);

    /**
     * 根据产品线ID查询项目列表 (下拉框用)
     */
    List<ProjectResp> getProjectListByProductLine(Long productLineId);
}
