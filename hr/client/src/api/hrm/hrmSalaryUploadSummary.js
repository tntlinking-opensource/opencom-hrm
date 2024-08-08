import request from '@/utils/request'

export const getHrmSalaryUploadSummaryById = (id) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/' + id,
    method: 'get'
  })
export const downloadTemplate = () =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/downloadTemplate',
    method: 'get',
    type:'blob',
    responseType: 'arraybuffer'
  })

export const listHrmSalaryUploadSummaryPage = (search) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/list',
    method: 'post',
    data: search
  })

export const listHrmSalaryUploadSummaryAll = (search) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/listAll',
    method: 'post',
    data: search
  })

export const saveHrmSalaryUploadSummary = (hrmSalaryUploadSummary) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/save',
    method: 'post',
    data: hrmSalaryUploadSummary
  })
export const fileUpload = (form) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/file',
    method: 'post',
    data: form
  })

export const deleteHrmSalaryUploadSummary = (hrmSalaryUploadSummary) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/delete',
    method: 'post',
    data: hrmSalaryUploadSummary
  })

export const bulkInsertHrmSalaryUploadSummary = (hrmSalaryUploadSummarys) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/bulkInsert',
    method: 'post',
    data: hrmSalaryUploadSummarys
  })

export const bulkUpdateHrmSalaryUploadSummary = (hrmSalaryUploadSummarys) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/bulkUpdate',
    method: 'post',
    data: hrmSalaryUploadSummarys
  })

export const bulkDeleteHrmSalaryUploadSummary = (hrmSalaryUploadSummarys) =>
  request({
    url: '/hrm/hrmSalaryUploadSummary/bulkDelete',
    method: 'post',
    data: hrmSalaryUploadSummarys
  })
