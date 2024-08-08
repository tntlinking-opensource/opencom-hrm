package com.geeke.common.service;

import com.geeke.common.dao.DcColumnViewDao;
import com.geeke.common.entity.DcColumnView;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 查询列Service
 * @author
 * @version
 */

@Service("dcColumnViewService")
@Transactional(readOnly = true)
public class DcColumnViewService extends CrudService<DcColumnViewDao, DcColumnView> {}
