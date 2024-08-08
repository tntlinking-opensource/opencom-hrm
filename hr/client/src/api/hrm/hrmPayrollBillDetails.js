import request from '@/utils/request'

export const getHrmPayrollBillDetailsById = (id) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/' + id,
    method: 'get'
  })

export const listHrmPayrollBillDetailsPage = (search) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/list',
    method: 'post',
    data: search
  })

export const listHrmPayrollBillDetailsAll = (search) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/listAll',
    method: 'post',
    data: search
  })

export const saveHrmPayrollBillDetails = (hrmPayrollBillDetails) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/save',
    method: 'post',
    data: hrmPayrollBillDetails
  })
export const checkPassword = (userId,password) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/checkPassword/' + userId + '/' + password,
    method: 'post',
  })

export const deleteHrmPayrollBillDetails = (hrmPayrollBillDetails) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/delete',
    method: 'post',
    data: hrmPayrollBillDetails
  })

export const bulkInsertHrmPayrollBillDetails = (hrmPayrollBillDetailss) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/bulkInsert',
    method: 'post',
    data: hrmPayrollBillDetailss
  })

export const bulkUpdateHrmPayrollBillDetails = (hrmPayrollBillDetailss) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/bulkUpdate',
    method: 'post',
    data: hrmPayrollBillDetailss
  })

export const bulkDeleteHrmPayrollBillDetails = (hrmPayrollBillDetailss) =>
  request({
    url: '/hrm/hrmPayrollBillDetails/bulkDelete',
    method: 'post',
    data: hrmPayrollBillDetailss
  })
