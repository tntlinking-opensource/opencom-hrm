package com.geeke.sys.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.PropertySetDao;
import com.geeke.sys.entity.PropertySet;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 属性集Service
 * @author
 * @version
 */

@Service("propertySetService")
@Transactional(readOnly = true)
public class PropertySetService extends CrudService<PropertySetDao, PropertySet> {}
