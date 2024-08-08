package com.geeke.accumulation.dao;

import com.geeke.accumulation.entity.DefaultPayConfig;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 默认配置DAO接口
 * @author
 * @version
 */
@Mapper
public interface DefaultPayConfigDao extends CrudDao<DefaultPayConfig> {
    int deleteByPayCityId(@Param("param") DefaultPayConfig defaultPayConfig);
}
