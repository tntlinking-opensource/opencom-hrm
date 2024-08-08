import request from '@/utils/request'

export const getSalaryArchivesById = (id) =>
  request({
    url: '/salaryArchives/salaryArchives/' + id,
    method: 'get'
  })

export const listSalaryArchivesPage = (search) =>
  request({
    url: '/salaryArchives/salaryArchives/list',
    method: 'post',
    data: search
  })

export const listSalaryArchivesAll = (search) =>
  request({
    url: '/salaryArchives/salaryArchives/listAll',
    method: 'post',
    data: search
  })

export const saveSalaryArchives = (salaryArchives) =>
  request({
    url: '/salaryArchives/salaryArchives/save',
    method: 'post',
    data: salaryArchives
  })

export const deleteSalaryArchives = (salaryArchives) =>
  request({
    url: '/salaryArchives/salaryArchives/delete',
    method: 'post',
    data: salaryArchives
  })

export const bulkInsertSalaryArchives = (salaryArchivess) =>
  request({
    url: '/salaryArchives/salaryArchives/bulkInsert',
    method: 'post',
    data: salaryArchivess
  })

export const bulkUpdateSalaryArchives = (salaryArchivess) =>
  request({
    url: '/salaryArchives/salaryArchives/bulkUpdate',
    method: 'post',
    data: salaryArchivess
  })

export const bulkDeleteSalaryArchives = (salaryArchivess) =>
  request({
    url: '/salaryArchives/salaryArchives/bulkDelete',
    method: 'post',
    data: salaryArchivess
  })

  export const downloadTemplate = () =>
  request({
    url: '/salaryArchives/salaryArchives/downloadTemplate',
    method: 'get',
    type:'blob',
    responseType: 'arraybuffer'
  })

  export const fileUpload = (form) =>
  request({
    url: '/salaryArchives/salaryArchives/file',
    method: 'post',
    data: form
  })
