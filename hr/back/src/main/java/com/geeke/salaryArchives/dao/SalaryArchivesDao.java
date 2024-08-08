package com.geeke.salaryArchives.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryArchives.entity.SalaryArchives;
import org.apache.ibatis.annotations.Mapper;

/**
 * 薪酬档案DAO接口
 * @author
 * @version
 */
@Mapper
public interface SalaryArchivesDao extends CrudDao<SalaryArchives> {}
