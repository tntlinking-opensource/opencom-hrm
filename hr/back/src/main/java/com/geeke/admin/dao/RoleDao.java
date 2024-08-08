package com.geeke.admin.dao;

import com.geeke.admin.entity.Role;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色DAO接口
 * @author
 * @version
 */
@Mapper
public interface RoleDao extends CrudDao<Role> {}
