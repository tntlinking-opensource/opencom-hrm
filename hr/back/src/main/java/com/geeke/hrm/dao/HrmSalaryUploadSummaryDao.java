package com.geeke.hrm.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.hrm.entity.HrmSalaryUploadSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 工资上传DAO接口
 * @author
 * @version
 */
@Mapper
public interface HrmSalaryUploadSummaryDao extends CrudDao<HrmSalaryUploadSummary> {}
