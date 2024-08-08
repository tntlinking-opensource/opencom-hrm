import request from '@/utils/request'

export const getPayCityById = (id) =>
  request({
    url: '/socialSecurityFund/payCity/' + id,
    method: 'get'
  })

export const listPayCityPage = (search) =>
  request({
    url: '/payCity/list',
    method: 'post',
    data: search
  })
export const listDefultConfig = (search) =>
  request({
    url: 'defaultPayConfig/list',
    method: 'post',
    data: search
  })
export const saveDefultConfig = (search) =>
  request({
    url: '/defaultPayConfig/save',
    method: 'post',
    data: search
  })
export const deleteDefultConfig = (search) =>
  request({
    url: '/defaultPayConfig/bulkDelete',
    method: 'post',
    data: search
  })
export const updateDefultConfig = (search) =>
  request({
    url: '/defaultPayConfig/bulkUpdate',
    method: 'post',
    data: search
  })
export const listPayCityAll = (search) =>
  request({
    url: '/socialSecurityFund/payCity/listAll',
    method: 'post',
    data: search
  })

export const savePayCity = (payCity) =>
  request({
    url: '/payCity/save',
    method: 'post',
    data: payCity
  })

export const deletePayCity = (payCity) =>
  request({
    url: '/payCity/delete',
    method: 'post',
    data: payCity
  })

export const bulkInsertPayCity = (payCitys) =>
  request({
    url: '/socialSecurityFund/payCity/bulkInsert',
    method: 'post',
    data: payCitys
  })

export const bulkUpdatePayCity = (payCitys) =>
  request({
    url: '/socialSecurityFund/payCity/bulkUpdate',
    method: 'post',
    data: payCitys
  })

export const bulkDeletePayCity = (payCitys) =>
  request({
    url: '/socialSecurityFund/payCity/bulkDelete',
    method: 'post',
    data: payCitys
  })
