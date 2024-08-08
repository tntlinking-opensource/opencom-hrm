package com.geeke.salaryPay.service;

import com.geeke.common.service.CrudService;
import com.geeke.salaryPay.dao.SalaryPayDao;
import com.geeke.salaryPay.entity.SalaryPay;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 申请发放模板Service
 * @author
 * @version
 */

@Service("salaryPayService")
@Transactional(readOnly = true)
public class SalaryPayService extends CrudService<SalaryPayDao, SalaryPay> {}
