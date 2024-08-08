package com.geeke.salaryData.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryData.entity.BillSalaryDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生成账单DAO接口
 * @author
 * @version
 */
@Mapper
public interface BillSalaryDetailsDao extends CrudDao<BillSalaryDetails> {
    /**
     * 薪资计算获取社保账单数据
     * @param billSalaryDetails
     * @return
     */
    List<BillSalaryDetails> salaryCountBill(@Param("ob") BillSalaryDetails billSalaryDetails);
}
