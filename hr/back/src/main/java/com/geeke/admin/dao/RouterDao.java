package com.geeke.admin.dao;

import com.geeke.admin.entity.Router;
import com.geeke.common.persistence.TreeDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 路由管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface RouterDao extends TreeDao<Router> {
    // 修改加密字段

}
