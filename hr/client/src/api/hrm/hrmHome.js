import request from '@/utils/request'

export const getHrmHomeByEmployeeNo = (employeeNo) =>
  request({
    url: '/hrm/hrmHome/' + employeeNo,
    method: 'get'
  })

export const getContractReminder = () =>
  request({
    url: '/hrm/hrmHome/contractReminder',
    method: 'get'
  })
