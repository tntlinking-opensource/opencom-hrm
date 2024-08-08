package com.geeke.salaryData.dao;

import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryData.entity.SalaryData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 薪资明细DAO接口
 * @author
 * @version
 */
@Mapper
public interface SalaryDataDao extends CrudDao<SalaryData> {

    List<SalaryData> salaryDataByempAndYear(String employeeNo, String year, String year1);

    List<SalaryData> getListByEmpidAndTaxMonth(String employeeNo, String taxMonth);

    List<SalaryData> listSalaryPage(String employeeName,String idCardNo,String saBatchId, int offset, int limit, String order);
}
