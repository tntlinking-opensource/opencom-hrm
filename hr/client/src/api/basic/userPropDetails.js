import request from '@/utils/request'

export const getUserPropDetailsById = (id) =>
  request({
    url: '/basic/userPropDetails/' + id,
    method: 'get'
  })

export const listUserPropDetailsPage = (search) =>
  request({
    url: '/basic/userPropDetails/list',
    method: 'post',
    data: search
  })

export const listUserPropDetailsAll = (search) =>
  request({
    url: '/basic/userPropDetails/listAll',
    method: 'post',
    data: search
  })

export const saveUserPropDetails = (userPropDetails) =>
  request({
    url: '/basic/userPropDetails/save',
    method: 'post',
    data: userPropDetails
  })

export const saveForm = (formData) =>
  request({
    url: '/basic/userPropDetails/file/save',
    method: 'post',
    data: formData
  })

export const deleteUserPropDetails = (userPropDetails) =>
  request({
    url: '/basic/userPropDetails/delete',
    method: 'post',
    data: userPropDetails
  })

export const bulkInsertUserPropDetails = (userPropDetailss) =>
  request({
    url: '/basic/userPropDetails/bulkInsert',
    method: 'post',
    data: userPropDetailss
  })

export const bulkUpdateUserPropDetails = (userPropDetailss) =>
  request({
    url: '/basic/userPropDetails/bulkUpdate',
    method: 'post',
    data: userPropDetailss
  })

export const bulkDeleteUserPropDetails = (userPropDetailss) =>
  request({
    url: '/basic/userPropDetails/bulkDelete',
    method: 'post',
    data: userPropDetailss
  })
