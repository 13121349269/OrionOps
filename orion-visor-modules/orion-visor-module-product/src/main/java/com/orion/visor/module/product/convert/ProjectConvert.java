package com.orion.visor.module.product.convert;

import com.orion.visor.module.product.dal.dataobject.ProjectDO;
import com.orion.visor.module.product.model.req.ProjectCreateReq;
import com.orion.visor.module.product.model.req.ProjectUpdateReq;
import com.orion.visor.module.product.model.resp.ProjectResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 项目对象转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface ProjectConvert {

    ProjectConvert INSTANCE = Mappers.getMapper(ProjectConvert.class);

    /**
     * 创建请求 -> DO
     */
    ProjectDO convert(ProjectCreateReq req);

    /**
     * 更新请求 -> DO (部分更新)
     */
    void convert(ProjectUpdateReq req, @MappingTarget ProjectDO entity);

    /**
     * DO -> 响应 VO
     */
    ProjectResp convert(ProjectDO entity);

    /**
     * DO 列表 -> 响应 VO 列表
     */
    List<ProjectResp> convertList(List<ProjectDO> list);
}
