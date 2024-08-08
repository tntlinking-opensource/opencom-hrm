package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.ActionRecycle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志DAO接口
 * @author
 * @version
 */
@Mapper
public interface ActionRecycleDao extends CrudDao<ActionRecycle> {}
