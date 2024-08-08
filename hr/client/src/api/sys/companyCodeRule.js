import request from '@/utils/request'

export const getCompanyCodeRuleById = (id) =>
  request({
    url: '/sys/companyCodeRule/' + id,
    method: 'get'
  })

export const listCompanyCodeRulePage = (search) =>
  request({
    url: '/sys/companyCodeRule/list',
    method: 'post',
    data: search
  })

export const listCompanyCodeRuleAll = (search) =>
  request({
    url: '/sys/companyCodeRule/listAll',
    method: 'post',
    data: search
  })

export const saveCompanyCodeRule = (companyCodeRule) =>
  request({
    url: '/sys/companyCodeRule/save',
    method: 'post',
    data: companyCodeRule
  })

export const deleteCompanyCodeRule = (companyCodeRule) =>
  request({
    url: '/sys/companyCodeRule/delete',
    method: 'post',
    data: companyCodeRule
  })

export const bulkInsertCompanyCodeRule = (companyCodeRules) =>
  request({
    url: '/sys/companyCodeRule/bulkInsert',
    method: 'post',
    data: companyCodeRules
  })

export const bulkUpdateCompanyCodeRule = (companyCodeRules) =>
  request({
    url: '/sys/companyCodeRule/bulkUpdate',
    method: 'post',
    data: companyCodeRules
  })

export const bulkDeleteCompanyCodeRule = (companyCodeRules) =>
  request({
    url: '/sys/companyCodeRule/bulkDelete',
    method: 'post',
    data: companyCodeRules
  })
