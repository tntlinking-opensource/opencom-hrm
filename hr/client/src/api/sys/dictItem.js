import request from '@/utils/request'

export const getDictItemById = (id) =>
  request({
    url: '/sys/dictItem/' + id,
    method: 'get'
  })

export const listDictItemPage = (search) =>
  request({
    url: '/sys/dictItem/list',
    method: 'post',
    data: search
  })

export const listDictItemAll = (search) =>
  request({
    url: '/sys/dictItem/listAll',
    method: 'post',
    data: search
  })

export const saveDictItem = (dictItem) =>
  request({
    url: '/sys/dictItem/save',
    method: 'post',
    data: dictItem
  })

export const deleteDictItem = (dictItem) =>
  request({
    url: '/sys/dictItem/delete',
    method: 'post',
    data: dictItem
  })

export const bulkInsertDictItem = (dictItems) =>
  request({
    url: '/sys/dictItem/bulkInsert',
    method: 'post',
    data: dictItems
  })

export const bulkUpdateDictItem = (dictItems) =>
  request({
    url: '/sys/dictItem/bulkUpdate',
    method: 'post',
    data: dictItems
  })

export const bulkDeleteDictItem = (dictItems) =>
  request({
    url: '/sys/dictItem/bulkDelete',
    method: 'post',
    data: dictItems
  })
