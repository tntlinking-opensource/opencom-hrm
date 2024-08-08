import request from '@/utils/request'

export const getDcColumnViewById = (id) =>
  request({
    url: '/common/dcColumnView/' + id,
    method: 'get'
  })

export const listDcColumnViewPage = (search) =>
  request({
    url: '/common/dcColumnView/list',
    method: 'post',
    data: search
  })

export const listDcColumnViewAll = (search) =>
  request({
    url: '/common/dcColumnView/listAll',
    method: 'post',
    data: search
  })

export const saveDcColumnView = (dcColumnView) =>
  request({
    url: '/common/dcColumnView/save',
    method: 'post',
    data: dcColumnView
  })

export const deleteDcColumnView = (dcColumnView) =>
  request({
    url: '/common/dcColumnView/delete',
    method: 'post',
    data: dcColumnView
  })

export const bulkInsertDcColumnView = (dcColumnViews) =>
  request({
    url: '/common/dcColumnView/bulkInsert',
    method: 'post',
    data: dcColumnViews
  })

export const bulkUpdateDcColumnView = (dcColumnViews) =>
  request({
    url: '/common/dcColumnView/bulkUpdate',
    method: 'post',
    data: dcColumnViews
  })

export const bulkDeleteDcColumnView = (dcColumnViews) =>
  request({
    url: '/common/dcColumnView/bulkDelete',
    method: 'post',
    data: dcColumnViews
  })
