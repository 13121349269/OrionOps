package com.orion.visor.module.vault.dal.mysql;

import com.orion.visor.framework.mybatis.core.mapper.BaseMapperX;
import com.orion.visor.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.orion.visor.module.vault.dal.dataobject.VaultCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 密钥分类 Mapper
 *
 * @author ops-platform
 */
@Mapper
public interface VaultCategoryMapper extends BaseMapperX<VaultCategoryDO> {

    /**
     * 根据编码查询
     */
    default VaultCategoryDO selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<VaultCategoryDO>()
                .eqIfPresent(VaultCategoryDO::getCode, code));
    }

    /**
     * 查询全部分类 (按排序)
     */
    default List<VaultCategoryDO> selectAll() {
        return selectList(new LambdaQueryWrapperX<VaultCategoryDO>()
                .orderByAsc(VaultCategoryDO::getSort));
    }
}
