import request from '@/utils/request'

export const getDcTableById = (id) =>
  request({
    url: '/dc/dcTable/' + id,
    method: 'get'
  })

export const listDcTablePage = (search) =>
  request({
    url: '/dc/dcTable/list',
    method: 'post',
    data: search
  })

export const listDcTableAll = (search) =>
  request({
    url: '/dc/dcTable/listAll',
    method: 'post',
    data: search
  })

export const saveDcTable = (dcTable) =>
  request({
    url: '/dc/dcTable/save',
    method: 'post',
    data: dcTable
  })

export const deleteDcTable = (dcTable) =>
  request({
    url: '/dc/dcTable/delete',
    method: 'post',
    data: dcTable
  })

export const bulkInsertDcTable = (dcTables) =>
  request({
    url: '/dc/dcTable/bulkInsert',
    method: 'post',
    data: dcTables
  })

export const bulkUpdateDcTable = (dcTables) =>
  request({
    url: '/dc/dcTable/bulkUpdate',
    method: 'post',
    data: dcTables
  })

export const bulkDeleteDcTable = (dcTables) =>
  request({
    url: '/dc/dcTable/bulkDelete',
    method: 'post',
    data: dcTables
  })

export const listTableColumn = (search) =>
  request({
    url: '/dc/dcTable/listTableColumn',
    method: 'post',
    data: search
  })

export const listTableAndColumn = (search) =>
  request({
    url: '/dc/dcTable/listTableAndColumn',
    method: 'post',
    data: search
  })
