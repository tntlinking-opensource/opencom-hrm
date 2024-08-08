import request from '@/utils/request'

export const getPersonalThemeById = (id) =>
  request({
    url: '/sys/personalTheme/' + id,
    method: 'get'
  })

export const listPersonalThemePage = (search) =>
  request({
    url: '/sys/personalTheme/list',
    method: 'post',
    data: search
  })

export const listPersonalThemeAll = (search) =>
  request({
    url: '/sys/personalTheme/listAll',
    method: 'post',
    data: search
  })

export const savePersonalTheme = (personalTheme) =>
  request({
    url: '/sys/personalTheme/save',
    method: 'post',
    data: personalTheme
  })

export const deletePersonalTheme = (personalTheme) =>
  request({
    url: '/sys/personalTheme/delete',
    method: 'post',
    data: personalTheme
  })

export const bulkInsertPersonalTheme = (personalThemes) =>
  request({
    url: '/sys/personalTheme/bulkInsert',
    method: 'post',
    data: personalThemes
  })

export const bulkUpdatePersonalTheme = (personalThemes) =>
  request({
    url: '/sys/personalTheme/bulkUpdate',
    method: 'post',
    data: personalThemes
  })

export const bulkDeletePersonalTheme = (personalThemes) =>
  request({
    url: '/sys/personalTheme/bulkDelete',
    method: 'post',
    data: personalThemes
  })
