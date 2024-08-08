import request from '@/utils/request'

export const getDepartmentById = (id) =>
  request({
    url: '/org/department/' + id,
    method: 'get'
  })

export const listDepartmentPage = (search) =>
  request({
    url: '/org/department/list',
    method: 'post',
    data: search
  })

export const listDepartmentAll = (search) =>
  request({
    url: '/org/department/listAll',
    method: 'post',
    data: search
  })
export const listAllByCompany = (search) =>
  request({
    url: '/org/department/listAllByCompany',
    method: 'post',
    data: search
  })

export const treeDepartment = (search) =>
  request({
    url: '/org/department/tree',
    method: 'post',
    data: search
  })

export const saveDepartment = (department) =>
  request({
    url: '/org/department/save',
    method: 'post',
    data: department
  })

export const deleteDepartment = (department) =>
  request({
    url: '/org/department/delete',
    method: 'post',
    data: department
  })

export const bulkInsertDepartment = (departments) =>
  request({
    url: '/org/department/bulkInsert',
    method: 'post',
    data: departments
  })

export const bulkUpdateDepartment = (departments) =>
  request({
    url: '/org/department/bulkUpdate',
    method: 'post',
    data: departments
  })

export const bulkDeleteDepartment = (departments) =>
  request({
    url: '/org/department/bulkDelete',
    method: 'post',
    data: departments
  })
