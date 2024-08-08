import request from '@/utils/request'

export const getDcTemplateById = (id) =>
  request({
    url: '/dc/dcTemplate/' + id,
    method: 'get'
  })

export const listDcTemplatePage = (search) =>
  request({
    url: '/dc/dcTemplate/list',
    method: 'post',
    data: search
  })

export const listDcTemplateAll = (search) =>
  request({
    url: '/dc/dcTemplate/listAll',
    method: 'post',
    data: search
  })

export const saveDcTemplate = (formData) =>
  request({
    url: '/dc/dcTemplate/save',
    method: 'post',
    data: formData
  })

export const deleteDcTemplate = (dcTemplate) =>
  request({
    url: '/dc/dcTemplate/delete',
    method: 'post',
    data: dcTemplate
  })

export const bulkInsertDcTemplate = (dcTemplates) =>
  request({
    url: '/dc/dcTemplate/bulkInsert',
    method: 'post',
    data: dcTemplates
  })

export const bulkUpdateDcTemplate = (dcTemplates) =>
  request({
    url: '/dc/dcTemplate/bulkUpdate',
    method: 'post',
    data: dcTemplates
  })

export const bulkDeleteDcTemplate = (dcTemplates) =>
  request({
    url: '/dc/dcTemplate/bulkDelete',
    method: 'post',
    data: dcTemplates
  })

export const listTemplateAndFile = (search) =>
  request({
    url: '/dc/dcTemplate/listTemplateAndFile',
    method: 'post',
    data: search
  })
