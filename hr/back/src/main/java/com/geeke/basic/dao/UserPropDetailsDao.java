package com.geeke.basic.dao;

import com.geeke.basic.entity.UserPropDetails;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 基础配置项DAO接口
 * @author
 * @version
 */
@Mapper
public interface UserPropDetailsDao extends CrudDao<UserPropDetails> {}
