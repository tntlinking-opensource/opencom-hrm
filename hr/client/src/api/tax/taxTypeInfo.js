import request from '@/utils/request'

export const getTaxTypeInfoById = (id) =>
  request({
    url: '/tax/taxTypeInfo/' + id,
    method: 'get'
  })

export const listTaxTypeInfoPage = (search) =>
  request({
    url: '/tax/taxTypeInfo/list',
    method: 'post',
    data: search
  })

export const listTaxTypeInfoAll = (search) =>
  request({
    url: '/tax/taxTypeInfo/listAll',
    method: 'post',
    data: search
  })

export const saveTaxTypeInfo = (taxTypeInfo) =>
  request({
    url: '/tax/taxTypeInfo/save',
    method: 'post',
    data: taxTypeInfo
  })

export const deleteTaxTypeInfo = (taxTypeInfo) =>
  request({
    url: '/tax/taxTypeInfo/delete',
    method: 'post',
    data: taxTypeInfo
  })

export const bulkInsertTaxTypeInfo = (taxTypeInfos) =>
  request({
    url: '/tax/taxTypeInfo/bulkInsert',
    method: 'post',
    data: taxTypeInfos
  })

export const bulkUpdateTaxTypeInfo = (taxTypeInfos) =>
  request({
    url: '/tax/taxTypeInfo/bulkUpdate',
    method: 'post',
    data: taxTypeInfos
  })

export const bulkDeleteTaxTypeInfo = (taxTypeInfos) =>
  request({
    url: '/tax/taxTypeInfo/bulkDelete',
    method: 'post',
    data: taxTypeInfos
  })
