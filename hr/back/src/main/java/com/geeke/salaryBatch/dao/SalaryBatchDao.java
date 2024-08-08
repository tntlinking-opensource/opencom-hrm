package com.geeke.salaryBatch.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryBatch.entity.SalaryBatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 薪资计算DAO接口
 * @author
 * @version
 */
@Mapper
public interface SalaryBatchDao extends CrudDao<SalaryBatch> {}
