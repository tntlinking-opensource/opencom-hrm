import request from '@/utils/request'

export const getUserPropListById = (id) =>
  request({
    url: '/basic/userPropList/' + id,
    method: 'get'
  })

export const listUserPropListPage = (search) =>
  request({
    url: '/basic/userPropList/list',
    method: 'post',
    data: search
  })

export const listUserPropListAll = (search) =>
  request({
    url: '/basic/userPropList/listAll',
    method: 'post',
    data: search
  })

export const saveUserPropList = (userPropList) =>
  request({
    url: '/basic/userPropList/save',
    method: 'post',
    data: userPropList
  })

export const deleteUserPropList = (userPropList) =>
  request({
    url: '/basic/userPropList/delete',
    method: 'post',
    data: userPropList
  })

export const bulkInsertUserPropList = (userPropLists) =>
  request({
    url: '/basic/userPropList/bulkInsert',
    method: 'post',
    data: userPropLists
  })

export const bulkUpdateUserPropList = (userPropLists) =>
  request({
    url: '/basic/userPropList/bulkUpdate',
    method: 'post',
    data: userPropLists
  })

export const bulkDeleteUserPropList = (userPropLists) =>
  request({
    url: '/basic/userPropList/bulkDelete',
    method: 'post',
    data: userPropLists
  })
