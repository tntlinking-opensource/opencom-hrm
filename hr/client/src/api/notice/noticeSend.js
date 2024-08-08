import request from '@/utils/request'

export const getNoticeSendById = (id) =>
  request({
    url: '/notice/noticeSend/' + id,
    method: 'get'
  })

export const listNoticeSendPage = (search) =>
  request({
    url: '/notice/noticeSend/list',
    method: 'post',
    data: search
  })

export const listNoticeSendAll = (search) =>
  request({
    url: '/notice/noticeSend/listAll',
    method: 'post',
    data: search
  })

export const saveNoticeSend = (noticeSend) =>
  request({
    url: '/notice/noticeSend/save',
    method: 'post',
    data: noticeSend
  })

export const deleteNoticeSend = (noticeSend) =>
  request({
    url: '/notice/noticeSend/delete',
    method: 'post',
    data: noticeSend
  })

export const bulkInsertNoticeSend = (noticeSends) =>
  request({
    url: '/notice/noticeSend/bulkInsert',
    method: 'post',
    data: noticeSends
  })

export const bulkUpdateNoticeSend = (noticeSends) =>
  request({
    url: '/notice/noticeSend/bulkUpdate',
    method: 'post',
    data: noticeSends
  })

export const bulkDeleteNoticeSend = (noticeSends) =>
  request({
    url: '/notice/noticeSend/bulkDelete',
    method: 'post',
    data: noticeSends
  })


export const getCompanyTree = (code) =>
  request({
    url: '/notice/noticeSend/tree/' + code,
    method: 'get'
  });
