import request from '@/utils/request'

export const saveInterviewResult = (result,tsmInductionBasic) =>
  request({
    url: '/induction/pc/other/saveInterviewResult/' + result,
    method: 'post',
    data: tsmInductionBasic
  })

export const saveReportingType = (inductionId,dictItem) =>
  request({
    url: '/induction/pc/other/saveReportingType/' + inductionId,
    method: 'post',
    data: dictItem
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


