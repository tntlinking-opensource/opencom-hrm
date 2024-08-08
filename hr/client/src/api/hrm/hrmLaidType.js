import request from '@/utils/request'

export const getHrmLaidTypeById = (id) =>
  request({
    url: '/hrm/hrmLaidType/' + id,
    method: 'get'
  })

export const listHrmLaidTypePage = (search) =>
  request({
    url: '/hrm/hrmLaidType/list',
    method: 'post',
    data: search
  })

export const listHrmLaidTypeAll = (search) =>
  request({
    url: '/hrm/hrmLaidType/listAll',
    method: 'post',
    data: search
  })

export const saveHrmLaidType = (hrmLaidType) =>
  request({
    url: '/hrm/hrmLaidType/save',
    method: 'post',
    data: hrmLaidType
  })

export const deleteHrmLaidType = (hrmLaidType) =>
  request({
    url: '/hrm/hrmLaidType/delete',
    method: 'post',
    data: hrmLaidType
  })

export const bulkInsertHrmLaidType = (hrmLaidTypes) =>
  request({
    url: '/hrm/hrmLaidType/bulkInsert',
    method: 'post',
    data: hrmLaidTypes
  })

export const bulkUpdateHrmLaidType = (hrmLaidTypes) =>
  request({
    url: '/hrm/hrmLaidType/bulkUpdate',
    method: 'post',
    data: hrmLaidTypes
  })

export const bulkDeleteHrmLaidType = (hrmLaidTypes) =>
  request({
    url: '/hrm/hrmLaidType/bulkDelete',
    method: 'post',
    data: hrmLaidTypes
  })
