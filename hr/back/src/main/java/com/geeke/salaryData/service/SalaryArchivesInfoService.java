package com.geeke.salaryData.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.salaryData.dao.SalaryArchivesInfoDao;
import com.geeke.salaryData.entity.SalaryArchivesInfo;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 薪资明细Service
 * @author
 * @version
 */

@Service("salaryArchivesInfoService")
@Transactional(readOnly = true)
public class SalaryArchivesInfoService extends CrudService<SalaryArchivesInfoDao, SalaryArchivesInfo> {
    @Autowired
    private SalaryArchivesInfoDao salaryArchivesInfoDao;
    /**
     * 获取员工档案信息
     * @param empCode
     * @return
     */
    public SalaryArchivesInfo getEmp(String empCode) {
        return salaryArchivesInfoDao.getEmp(empCode);
    }
}
