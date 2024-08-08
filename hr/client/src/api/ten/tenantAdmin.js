import request from '@/utils/request'

export const getTenantAdminById = (id) =>
  request({
    url: '/ten/tenantAdmin/' + id,
    method: 'get'
  })

export const listTenantAdminPage = (search) =>
  request({
    url: '/ten/tenantAdmin/list',
    method: 'post',
    data: search
  })

export const listTenantAdminAll = (search) =>
  request({
    url: '/ten/tenantAdmin/listAll',
    method: 'post',
    data: search
  })

export const saveTenantAdmin = (tenantAdmin) =>
  request({
    url: '/ten/tenantAdmin/save',
    method: 'post',
    data: tenantAdmin
  })

export const deleteTenantAdmin = (tenantAdmin) =>
  request({
    url: '/ten/tenantAdmin/delete',
    method: 'post',
    data: tenantAdmin
  })

export const bulkInsertTenantAdmin = (tenantAdmins) =>
  request({
    url: '/ten/tenantAdmin/bulkInsert',
    method: 'post',
    data: tenantAdmins
  })

export const bulkUpdateTenantAdmin = (tenantAdmins) =>
  request({
    url: '/ten/tenantAdmin/bulkUpdate',
    method: 'post',
    data: tenantAdmins
  })

export const bulkDeleteTenantAdmin = (tenantAdmins) =>
  request({
    url: '/ten/tenantAdmin/bulkDelete',
    method: 'post',
    data: tenantAdmins
  })
