import request from '@/utils/request'

export const getTaxDetailsInfoById = (id) =>
  request({
    url: '/tax/taxDetailsInfo/' + id,
    method: 'get'
  })

export const listTaxDetailsInfoPage = (search) =>
  request({
    url: '/tax/taxDetailsInfo/list',
    method: 'post',
    data: search
  })

export const listTaxDetailsInfoAll = (search) =>
  request({
    url: '/tax/taxDetailsInfo/listAll',
    method: 'post',
    data: search
  })

export const saveTaxDetailsInfo = (taxDetailsInfo) =>
  request({
    url: '/tax/taxDetailsInfo/save',
    method: 'post',
    data: taxDetailsInfo
  })

export const deleteTaxDetailsInfo = (taxDetailsInfo) =>
  request({
    url: '/tax/taxDetailsInfo/delete',
    method: 'post',
    data: taxDetailsInfo
  })

export const bulkInsertTaxDetailsInfo = (taxDetailsInfos) =>
  request({
    url: '/tax/taxDetailsInfo/bulkInsert',
    method: 'post',
    data: taxDetailsInfos
  })

export const bulkUpdateTaxDetailsInfo = (taxDetailsInfos) =>
  request({
    url: '/tax/taxDetailsInfo/bulkUpdate',
    method: 'post',
    data: taxDetailsInfos
  })

export const bulkDeleteTaxDetailsInfo = (taxDetailsInfos) =>
  request({
    url: '/tax/taxDetailsInfo/bulkDelete',
    method: 'post',
    data: taxDetailsInfos
  })
