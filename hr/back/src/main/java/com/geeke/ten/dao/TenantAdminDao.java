package com.geeke.ten.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.ten.entity.TenantAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 租户管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface TenantAdminDao extends CrudDao<TenantAdmin> {
    /**
     * 修改 密码
     */
    public int updateLoginPassword(@Param("id") String id, @Param("pass") String pass);
}
