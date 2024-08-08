package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.PropertySet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 属性集DAO接口
 * @author
 * @version
 */
@Mapper
public interface PropertySetDao extends CrudDao<PropertySet> {}
