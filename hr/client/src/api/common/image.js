import request from '@/utils/request'

export const getImage = () =>
  request({
    url: '/common/image',
    method: 'get',
  })
