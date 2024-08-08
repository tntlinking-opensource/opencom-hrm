import request from '@/utils/request'

export const saveInterviewResult = (result,tsmInductionBasic) =>
  request({
    url: '/induction/pc/other/saveInterviewResult/' + result,
    method: 'post',
    data: tsmInductionBasic
  })

export const saveReportingType = (inductionId) =>
  request({
    url: '/induction/pc/other/saveReportingType/' + inductionId,
    method: 'get'
  })

export const saveDataValidation = (inductionId,result) =>
  request({
    url: '/induction/pc/other/saveDataValidation/' + inductionId + '/' + result,
    method: 'post',
  })

export const checkIdCardLast = (inductionId,idCardLast) =>
  request({
    url: '/induction/pc/other/checkIdCardLast/' + inductionId + '/' + idCardLast,
    method: 'get',
  })

export const archive = (inductionId) =>
  request({
    url: '/induction/pc/other/archive/' + inductionId,
    method: 'get',
  })
export const downByInductionId = (inductionId) =>
  request({
    url: '/induction/app/downByInductionId/' + inductionId,
    method: 'get',
  })
export const downByEmployeeNo = (employeeNo) =>
  request({
    url: '/induction/app/downByEmployeeNo/' + employeeNo,
    method: 'get',
  })

