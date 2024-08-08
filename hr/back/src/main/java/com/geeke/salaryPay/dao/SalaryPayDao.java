package com.geeke.salaryPay.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryPay.entity.SalaryPay;
import org.apache.ibatis.annotations.Mapper;

/**
 * 申请发放模板DAO接口
 * @author
 * @version
 */
@Mapper
public interface SalaryPayDao extends CrudDao<SalaryPay> {}
