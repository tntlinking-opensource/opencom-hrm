package com.geeke.ten.service;

import com.geeke.common.service.CrudService;
import com.geeke.ten.dao.TenantAdminDao;
import com.geeke.ten.entity.TenantAdmin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 租户管理Service
 * @author
 * @version
 */

@Service("tenantAdminService")
@Transactional(readOnly = true)
public class TenantAdminService extends CrudService<TenantAdminDao, TenantAdmin> {}
