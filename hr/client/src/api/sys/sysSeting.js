import request from '@/utils/request'

export const getSysSetingById = (id) =>
  request({
    url: '/sys/sysSeting/' + id,
    method: 'get'
  })

export const listSysSetingPage = (search) =>
  request({
    url: '/sys/sysSeting/list',
    method: 'post',
    data: search
  })

export const listSysSetingAll = (search) =>
  request({
    url: '/sys/sysSeting/listAll',
    method: 'post',
    data: search
  })

export const saveSysSeting = (sysSeting) =>
  request({
    url: '/sys/sysSeting/save',
    method: 'post',
    data: sysSeting
  })

export const deleteSysSeting = (sysSeting) =>
  request({
    url: '/sys/sysSeting/delete',
    method: 'post',
    data: sysSeting
  })

export const bulkInsertSysSeting = (sysSetings) =>
  request({
    url: '/sys/sysSeting/bulkInsert',
    method: 'post',
    data: sysSetings
  })

export const bulkUpdateSysSeting = (sysSetings) =>
  request({
    url: '/sys/sysSeting/bulkUpdate',
    method: 'post',
    data: sysSetings
  })

export const bulkDeleteSysSeting = (sysSetings) =>
  request({
    url: '/sys/sysSeting/bulkDelete',
    method: 'post',
    data: sysSetings
  })

//根据图片id回显图片
export const getFileById = (id) =>
  request({
    url: '/sys/sysSeting/getFile/' + id,
    method: 'get'
  })
