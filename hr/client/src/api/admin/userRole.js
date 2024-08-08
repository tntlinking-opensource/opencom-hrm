import request from '@/utils/request'

export const getUserRoleById = (id) =>
  request({
    url: '/admin/userRole/' + id,
    method: 'get'
  })

export const listUserRolePage = (search) =>
  request({
    url: '/admin/userRole/list',
    method: 'post',
    data: search
  })

export const listUserRoleAll = (search) =>
  request({
    url: '/admin/userRole/listAll',
    method: 'post',
    data: search
  })

export const saveUserRole = (userRole) =>
  request({
    url: '/admin/userRole/save',
    method: 'post',
    data: userRole
  })

export const deleteUserRole = (userRole) =>
  request({
    url: '/admin/userRole/delete',
    method: 'post',
    data: userRole
  })

export const bulkInsertUserRole = (userRoles) =>
  request({
    url: '/admin/userRole/bulkInsert',
    method: 'post',
    data: userRoles
  })

export const bulkUpdateUserRole = (userRoles) =>
  request({
    url: '/admin/userRole/bulkUpdate',
    method: 'post',
    data: userRoles
  })

export const bulkDeleteUserRole = (userRoles) =>
  request({
    url: '/admin/userRole/bulkDelete',
    method: 'post',
    data: userRoles
  })
