import request from '@/utils/request'

export const getDcSchemeById = (id) =>
  request({
    url: '/dc/dcScheme/' + id,
    method: 'get'
  })

export const listDcSchemePage = (search) =>
  request({
    url: '/dc/dcScheme/list',
    method: 'post',
    data: search
  })

export const listDcSchemeAll = (search) =>
  request({
    url: '/dc/dcScheme/listAll',
    method: 'post',
    data: search
  })

export const saveDcScheme = (dcScheme) =>
  request({
    url: '/dc/dcScheme/save',
    method: 'post',
    data: dcScheme
  })

export const deleteDcScheme = (dcScheme) =>
  request({
    url: '/dc/dcScheme/delete',
    method: 'post',
    data: dcScheme
  })

export const bulkInsertDcScheme = (dcSchemes) =>
  request({
    url: '/dc/dcScheme/bulkInsert',
    method: 'post',
    data: dcSchemes
  })

export const bulkUpdateDcScheme = (dcSchemes) =>
  request({
    url: '/dc/dcScheme/bulkUpdate',
    method: 'post',
    data: dcSchemes
  })

export const bulkDeleteDcScheme = (dcSchemes) =>
  request({
    url: '/dc/dcScheme/bulkDelete',
    method: 'post',
    data: dcSchemes
  })
