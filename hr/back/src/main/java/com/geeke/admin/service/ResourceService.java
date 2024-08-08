package com.geeke.admin.service;

import com.geeke.admin.dao.ResourceDao;
import com.geeke.admin.entity.Resource;
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
 * 资源管理Service
 * @author
 * @version
 */

@Service("resourceService")
@Transactional(readOnly = true)
public class ResourceService extends CrudService<ResourceDao, Resource> {}
