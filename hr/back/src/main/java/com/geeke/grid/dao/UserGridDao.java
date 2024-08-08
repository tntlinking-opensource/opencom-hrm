package com.geeke.grid.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.grid.entity.UserGrid;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户自定义布局DAO接口
 * @author
 * @version
 */
@Mapper
public interface UserGridDao extends CrudDao<UserGrid> {}
