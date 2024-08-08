import request from '@/utils/request'

export const getDcTableColumnById = (id) =>
  request({
    url: '/dc/dcTableColumn/' + id,
    method: 'get'
  })

export const listDcTableColumnPage = (search) =>
  request({
    url: '/dc/dcTableColumn/list',
    method: 'post',
    data: search
  })

export const listDcTableColumnAll = (search) =>
  request({
    url: '/dc/dcTableColumn/listAll',
    method: 'post',
    data: search
  })

export const saveDcTableColumn = (dcTableColumn) =>
  request({
    url: '/dc/dcTableColumn/save',
    method: 'post',
    data: dcTableColumn
  })

export const deleteDcTableColumn = (dcTableColumn) =>
  request({
    url: '/dc/dcTableColumn/delete',
    method: 'post',
    data: dcTableColumn
  })

export const bulkInsertDcTableColumn = (dcTableColumns) =>
  request({
    url: '/dc/dcTableColumn/bulkInsert',
    method: 'post',
    data: dcTableColumns
  })

export const bulkUpdateDcTableColumn = (dcTableColumns) =>
  request({
    url: '/dc/dcTableColumn/bulkUpdate',
    method: 'post',
    data: dcTableColumns
  })

export const bulkDeleteDcTableColumn = (dcTableColumns) =>
  request({
    url: '/dc/dcTableColumn/bulkDelete',
    method: 'post',
    data: dcTableColumns
  })
