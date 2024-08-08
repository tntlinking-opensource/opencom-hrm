package com.geeke.hrm.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.hrm.entity.HrmLaidType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 离职类型DAO接口
 * @author
 * @version
 */
@Mapper
public interface HrmLaidTypeDao extends CrudDao<HrmLaidType> {}
