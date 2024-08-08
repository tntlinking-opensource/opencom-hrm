package com.geeke.basic.dao;

import com.geeke.basic.entity.UserPropList;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 基础配置DAO接口
 * @author
 * @version
 */
@Mapper
public interface UserPropListDao extends CrudDao<UserPropList> {}
