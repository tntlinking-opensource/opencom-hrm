package com.geeke.hrm.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.hrm.dao.HrmPayrollBillDetailsDao;
import com.geeke.hrm.entity.HrmPayrollBillDetails;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 薪资查看Service
 * @author
 * @version
 */

@Service("hrmPayrollBillDetailsService")
@Transactional(readOnly = true)
public class HrmPayrollBillDetailsService extends CrudService<HrmPayrollBillDetailsDao, HrmPayrollBillDetails> {}
