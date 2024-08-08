package com.geeke.hrm.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.hrm.entity.HrmPayrollBillDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 薪资查看DAO接口
 * @author
 * @version
 */
@Mapper
public interface HrmPayrollBillDetailsDao extends CrudDao<HrmPayrollBillDetails> {}
