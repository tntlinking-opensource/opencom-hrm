import request from '@/utils/request'

export const getFirstQrCode = () =>
  request({
    url: '/induction/app/first/qrCode/',
    method: 'get'
  })

export const getSecondQrCode = (inductionId) =>
  request({
    url: '/induction/app/second/qrCode/' + inductionId  ,
    method: 'get'
  })
