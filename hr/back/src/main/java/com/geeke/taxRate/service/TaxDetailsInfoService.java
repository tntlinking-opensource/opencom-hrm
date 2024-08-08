package com.geeke.taxRate.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.taxRate.dao.TaxDetailsInfoDao;
import com.geeke.taxRate.entity.TaxDetailsInfo;
import com.geeke.taxRate.dao.TaxDetailsInfoDao;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 税率类型Service
 * @author
 * @version
 */

@Service("taxDetailsInfoService")
@Transactional(readOnly = true)
public class TaxDetailsInfoService extends CrudService<TaxDetailsInfoDao, TaxDetailsInfo> {}
