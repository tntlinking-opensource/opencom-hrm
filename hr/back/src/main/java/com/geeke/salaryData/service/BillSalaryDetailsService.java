package com.geeke.salaryData.service;


import com.geeke.common.service.CrudService;
import com.geeke.salaryData.dao.BillSalaryDetailsDao;
import com.geeke.salaryData.entity.BillSalaryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 生成账单Service
 * @author
 * @version
 */

@Service("billSalaryDetailsService")
@Transactional(readOnly = true)
public class BillSalaryDetailsService extends CrudService<BillSalaryDetailsDao, BillSalaryDetails> {
    @Autowired
    private BillSalaryDetailsDao billSalaryDetailsDao;


    public List<BillSalaryDetails> salaryCountBill(BillSalaryDetails billSalaryDetails) {
        return billSalaryDetailsDao.salaryCountBill(billSalaryDetails);
    }
}
