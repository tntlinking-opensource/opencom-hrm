package com.geeke.accumulation.dao;

import com.geeke.accumulation.entity.PayCityConfig;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 缴费城市DAO接口
 * @author
 * @version
 */
@Mapper
public interface PayCityConfigDao extends CrudDao<PayCityConfig> {
}
