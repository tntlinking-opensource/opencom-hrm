package com.geeke.dc.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.dc.entity.DcCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 查询条件DAO接口
 * @author
 * @version
 */
@Mapper
public interface DcConditionDao extends CrudDao<DcCondition> {}
