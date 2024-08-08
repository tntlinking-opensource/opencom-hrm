package com.geeke.salaryData.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.salaryData.entity.SalaryTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 薪资项目模板DAO接口
 * @author
 * @version
 */
@Mapper
public interface SalaryTempDao extends CrudDao<SalaryTemp> {

    List<SalaryTemp> getSalaryItemA1();

    List<SalaryTemp> getSalaryItemA7();

    List<SalaryTemp> getSalaryItemAll();
}
