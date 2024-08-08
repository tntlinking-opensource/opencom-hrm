package com.geeke.ten.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.ten.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 租户管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TenantDao extends CrudDao<Tenant> {}
