package com.orion.visor.module.container.convert;

import com.orion.visor.module.container.dal.dataobject.DockerHostDO;
import com.orion.visor.module.container.model.req.DockerHostCreateReq;
import com.orion.visor.module.container.model.req.DockerHostUpdateReq;
import com.orion.visor.module.container.model.resp.DockerHostResp;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Docker主机转换器 (MapStruct)
 *
 * @author ops-platform
 */
@Mapper
public interface DockerHostConvert {

    DockerHostConvert INSTANCE = Mappers.getMapper(DockerHostConvert.class);

    DockerHostDO convert(DockerHostCreateReq req);

    void convert(DockerHostUpdateReq req, @MappingTarget DockerHostDO entity);

    DockerHostResp convert(DockerHostDO entity);

    List<DockerHostResp> convertList(List<DockerHostDO> list);
}
