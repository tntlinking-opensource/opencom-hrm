package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.Action;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志DAO接口
 * @author
 * @version
 */
@Mapper
public interface ActionDao extends CrudDao<Action> {}
