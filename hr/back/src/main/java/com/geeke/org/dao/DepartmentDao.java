package com.geeke.org.dao;

import com.geeke.common.persistence.TreeDao;
import com.geeke.org.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 部门管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface DepartmentDao extends TreeDao<Department> {}
