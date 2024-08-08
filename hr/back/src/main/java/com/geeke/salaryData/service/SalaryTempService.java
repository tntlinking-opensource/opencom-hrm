package com.geeke.salaryData.service;

import com.geeke.common.service.CrudService;
import com.geeke.salaryData.dao.SalaryTempDao;
import com.geeke.salaryData.entity.SalaryTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 薪资项目模板Service
 * @author
 * @version
 */

@Service("salaryTempService")
@Transactional(readOnly = true)
public class SalaryTempService extends CrudService<SalaryTempDao, SalaryTemp> {
    @Autowired
    private SalaryTempDao salaryTempDao;
    public List<SalaryTemp> getSalaryItemA1() {
        return salaryTempDao.getSalaryItemA1();
    }

    public List<SalaryTemp> getSalaryItemA7() {
        return salaryTempDao.getSalaryItemA7();
    }

    public List<SalaryTemp> slistAll() {
        return salaryTempDao.getSalaryItemAll();
    }
}
