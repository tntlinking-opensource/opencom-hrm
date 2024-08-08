package com.geeke.hrm.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.hrm.dao.HrmSalaryUploadSummaryDao;
import com.geeke.hrm.entity.HrmSalaryUploadSummary;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工资上传Service
 * @author
 * @version
 */

@Service("hrmSalaryUploadSummaryService")
@Transactional(readOnly = true)
public class HrmSalaryUploadSummaryService extends CrudService<HrmSalaryUploadSummaryDao, HrmSalaryUploadSummary> {

    @Override
    public HrmSalaryUploadSummary save(HrmSalaryUploadSummary entity) {
        Map<String, String> colMaps = Maps.newHashMap();
        colMaps.clear();
        colMaps.put("salary_year_and_month", "salaryYearAndMonth");
        if (exists(dao, entity, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "当前上传的年月薪资信息已存在!"));
        }
        HrmSalaryUploadSummary hrmSalaryUploadSummaryTemp = super.save(entity);
        return hrmSalaryUploadSummaryTemp;
    }

    @Override
    public int delete(HrmSalaryUploadSummary entity) {
        return dao.delete(entity);
    }
}
