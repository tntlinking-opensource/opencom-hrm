import request from '@/utils/request'

export const getDcClienSchemeById = (id) =>
  request({
    url: '/dc/dcClienScheme/' + id,
    method: 'get'
  })

export const listDcClienSchemePage = (search) =>
  request({
    url: '/dc/dcClienScheme/list',
    method: 'post',
    data: search
  })

export const listDcClienSchemeAll = (search) =>
  request({
    url: '/dc/dcClienScheme/listAll',
    method: 'post',
    data: search
  })

export const saveDcClienScheme = (dcClienScheme) =>
  request({
    url: '/dc/dcClienScheme/save',
    method: 'post',
    data: dcClienScheme
  })

export const deleteDcClienScheme = (dcClienScheme) =>
  request({
    url: '/dc/dcClienScheme/delete',
    method: 'post',
    data: dcClienScheme
  })

export const bulkInsertDcClienScheme = (dcClienSchemes) =>
  request({
    url: '/dc/dcClienScheme/bulkInsert',
    method: 'post',
    data: dcClienSchemes
  })

export const bulkUpdateDcClienScheme = (dcClienSchemes) =>
  request({
    url: '/dc/dcClienScheme/bulkUpdate',
    method: 'post',
    data: dcClienSchemes
  })

export const bulkDeleteDcClienScheme = (dcClienSchemes) =>
  request({
    url: '/dc/dcClienScheme/bulkDelete',
    method: 'post',
    data: dcClienSchemes
  })
