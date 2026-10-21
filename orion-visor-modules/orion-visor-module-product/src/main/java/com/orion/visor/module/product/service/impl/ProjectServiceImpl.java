package com.orion.visor.module.product.service.impl;

import com.orion.visor.framework.common.exception.ServiceException;
import com.orion.visor.module.product.convert.ProjectConvert;
import com.orion.visor.module.product.dal.dataobject.ProductLineDO;
import com.orion.visor.module.product.dal.dataobject.ProjectDO;
import com.orion.visor.module.product.dal.mysql.ProductLineMapper;
import com.orion.visor.module.product.dal.mysql.ProjectMapper;
import com.orion.visor.module.product.enums.ProductStatusEnum;
import com.orion.visor.module.product.model.req.ProjectCreateReq;
import com.orion.visor.module.product.model.req.ProjectPageReq;
import com.orion.visor.module.product.model.req.ProjectUpdateReq;
import com.orion.visor.module.product.model.resp.ProjectResp;
import com.orion.visor.module.product.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.orion.visor.module.product.enums.ProductErrorCodeConstants.*;

/**
 * 项目 Service 实现
 *
 * @author ops-platform
 */
@Slf4j
@Service
@Validated
public class ProjectServiceImpl implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProductLineMapper productLineMapper;

    @Override
    public Long createProject(ProjectCreateReq req) {
        // 1. 校验产品线存在且启用
        ProductLineDO productLine = validateProductLineExists(req.getProductLineId());
        if (!ProductStatusEnum.ENABLED.getStatus().equals(productLine.getStatus())) {
            throw ServiceException.of(PROJECT_PRODUCT_LINE_DISABLED);
        }
        // 2. 校验同一产品线下编码唯一
        validateCodeUnique(req.getProductLineId(), req.getCode(), null);
        // 3. 校验状态
        validateStatus(req.getStatus());
        // 4. 转换并插入
        ProjectDO entity = ProjectConvert.INSTANCE.convert(req);
        if (entity.getStatus() == null) {
            entity.setStatus(ProductStatusEnum.ENABLED.getStatus());
        }
        projectMapper.insert(entity);
        log.info("[createProject] 项目创建成功, id={}, lineId={}, code={}",
                entity.getId(), entity.getProductLineId(), entity.getCode());
        return entity.getId();
    }

    @Override
    public void updateProject(ProjectUpdateReq req) {
        // 1. 校验存在
        validateExists(req.getId());
        // 2. 校验状态
        validateStatus(req.getStatus());
        // 3. 更新
        ProjectDO update = new ProjectDO();
        ProjectConvert.INSTANCE.convert(req, update);
        projectMapper.updateById(update);
        log.info("[updateProject] 项目更新成功, id={}", req.getId());
    }

    @Override
    public void deleteProject(Long id) {
        validateExists(id);
        projectMapper.deleteById(id);
        log.info("[deleteProject] 项目删除成功, id={}", id);
    }

    @Override
    public ProjectResp getProject(Long id) {
        ProjectDO entity = validateExists(id);
        ProjectResp resp = ProjectConvert.INSTANCE.convert(entity);
        // 填充产品线名称
        ProductLineDO line = productLineMapper.selectById(entity.getProductLineId());
        if (line != null) {
            resp.setProductLineName(line.getName());
        }
        return resp;
    }

    @Override
    public List<ProjectResp> getProjectPage(ProjectPageReq req) {
        List<ProjectDO> list = projectMapper.selectPage(req);
        List<ProjectResp> respList = ProjectConvert.INSTANCE.convertList(list);
        // TODO: 批量查询产品线名称和 ownerName 填充
        return respList;
    }

    @Override
    public List<ProjectResp> getProjectListByProductLine(Long productLineId) {
        List<ProjectDO> list = projectMapper.selectByProductLineId(productLineId);
        return ProjectConvert.INSTANCE.convertList(list);
    }

    // ==================== 内部校验方法 ====================

    private ProjectDO validateExists(Long id) {
        ProjectDO entity = projectMapper.selectById(id);
        if (entity == null) {
            throw ServiceException.of(PROJECT_NOT_EXISTS);
        }
        return entity;
    }

    private ProductLineDO validateProductLineExists(Long productLineId) {
        ProductLineDO line = productLineMapper.selectById(productLineId);
        if (line == null) {
            throw ServiceException.of(PRODUCT_LINE_NOT_EXISTS);
        }
        return line;
    }

    private void validateCodeUnique(Long productLineId, String code, Long excludeId) {
        ProjectDO existing = projectMapper.selectByLineAndCode(productLineId, code);
        if (existing != null && !Objects.equals(existing.getId(), excludeId)) {
            throw ServiceException.of(PROJECT_CODE_DUPLICATE);
        }
    }

    private void validateStatus(Integer status) {
        if (status != null && !ProductStatusEnum.isValid(status)) {
            throw ServiceException.of(PROJECT_STATUS_INVALID);
        }
    }
}
