package com.geeke.salaryData.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryData.entity.SalaryArchivesInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 薪资明细DAO接口
 * @author
 * @version
 */
@Mapper
public interface SalaryArchivesInfoDao extends CrudDao<SalaryArchivesInfo> {

    SalaryArchivesInfo getEmp(String empCode);

    List<SalaryArchivesInfo> NewestApplyOnPostDate(String idCard);
}
