package com.geeke.admin.dao;

import com.geeke.admin.entity.Resource;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资源管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface ResourceDao extends CrudDao<Resource> {}
