package com.geeke.org.dao;

import com.geeke.common.persistence.TreeDao;
import com.geeke.org.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公司管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface CompanyDao extends TreeDao<Company> {}
