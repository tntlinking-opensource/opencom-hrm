package com.geeke.admin.dao;

import com.geeke.admin.entity.User;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface UserDao extends CrudDao<User> {
    // 修改加密字段

    /**
     * 修改 密码
     */
    public int updateLoginPassword(@Param("id") String id, @Param("pass") String pass);
}
