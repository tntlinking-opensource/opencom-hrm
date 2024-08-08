import request from '@/utils/request'

export const getHrmLaidValueById = (id) =>
  request({
    url: '/hrm/hrmLaidValue/' + id,
    method: 'get'
  })

export const listHrmLaidValuePage = (search) =>
  request({
    url: '/hrm/hrmLaidValue/list',
    method: 'post',
    data: search
  })

export const listHrmLaidValueAll = (search) =>
  request({
    url: '/hrm/hrmLaidValue/listAll',
    method: 'post',
    data: search
  })

export const saveHrmLaidValue = (hrmLaidValue) =>
  request({
    url: '/hrm/hrmLaidValue/save',
    method: 'post',
    data: hrmLaidValue
  })

export const deleteHrmLaidValue = (hrmLaidValue) =>
  request({
    url: '/hrm/hrmLaidValue/delete',
    method: 'post',
    data: hrmLaidValue
  })

export const bulkInsertHrmLaidValue = (hrmLaidValues) =>
  request({
    url: '/hrm/hrmLaidValue/bulkInsert',
    method: 'post',
    data: hrmLaidValues
  })

export const bulkUpdateHrmLaidValue = (hrmLaidValues) =>
  request({
    url: '/hrm/hrmLaidValue/bulkUpdate',
    method: 'post',
    data: hrmLaidValues
  })

export const bulkDeleteHrmLaidValue = (hrmLaidValues) =>
  request({
    url: '/hrm/hrmLaidValue/bulkDelete',
    method: 'post',
    data: hrmLaidValues
  })
