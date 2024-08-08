import request from '@/utils/request'

export const getTenantById = (id) =>
  request({
    url: '/ten/tenant/' + id,
    method: 'get'
  })

export const listTenantPage = (search) =>
  request({
    url: '/ten/tenant/list',
    method: 'post',
    data: search
  })

export const listTenantAll = (search) =>
  request({
    url: '/ten/tenant/listAll',
    method: 'post',
    data: search
  })

export const saveTenant = (tenant) =>
  request({
    url: '/ten/tenant/save',
    method: 'post',
    data: tenant
  })

export const deleteTenant = (tenant) =>
  request({
    url: '/ten/tenant/delete',
    method: 'post',
    data: tenant
  })

export const bulkInsertTenant = (tenants) =>
  request({
    url: '/ten/tenant/bulkInsert',
    method: 'post',
    data: tenants
  })

export const bulkUpdateTenant = (tenants) =>
  request({
    url: '/ten/tenant/bulkUpdate',
    method: 'post',
    data: tenants
  })

export const bulkDeleteTenant = (tenants) =>
  request({
    url: '/ten/tenant/bulkDelete',
    method: 'post',
    data: tenants
  })
