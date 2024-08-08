import request from '@/utils/request'

export const getActionRecycleById = (id) =>
  request({
    url: '/sys/actionRecycle/' + id,
    method: 'get'
  })

export const listActionRecyclePage = (search) =>
  request({
    url: '/sys/actionRecycle/list',
    method: 'post',
    data: search
  })

export const listActionRecycleAll = (search) =>
  request({
    url: '/sys/actionRecycle/listAll',
    method: 'post',
    data: search
  })

export const saveActionRecycle = (actionRecycle) =>
  request({
    url: '/sys/actionRecycle/save',
    method: 'post',
    data: actionRecycle
  })

export const deleteActionRecycle = (actionRecycle) =>
  request({
    url: '/sys/actionRecycle/delete',
    method: 'post',
    data: actionRecycle
  })

export const bulkInsertActionRecycle = (actionRecycles) =>
  request({
    url: '/sys/actionRecycle/bulkInsert',
    method: 'post',
    data: actionRecycles
  })

export const bulkUpdateActionRecycle = (actionRecycles) =>
  request({
    url: '/sys/actionRecycle/bulkUpdate',
    method: 'post',
    data: actionRecycles
  })

export const bulkDeleteActionRecycle = (actionRecycles) =>
  request({
    url: '/sys/actionRecycle/bulkDelete',
    method: 'post',
    data: actionRecycles
  })
