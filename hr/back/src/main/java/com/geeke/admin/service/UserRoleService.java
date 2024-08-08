package com.geeke.admin.service;

import com.geeke.admin.dao.UserRoleDao;
import com.geeke.admin.entity.UserRole;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理Service
 * @author
 * @version
 */

@Service("userRoleService")
@Transactional(readOnly = true)
public class UserRoleService extends CrudService<UserRoleDao, UserRole> {}
