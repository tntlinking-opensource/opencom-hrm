import request from '@/utils/request'

export const getSalaryDataById = (id) =>
  request({
    url: '/salaryData/salaryData/' + id,
    method: 'get'
  })

export const listSalaryDataPage = (search) =>
  request({
    url: '/salaryData/salaryData/list',
    method: 'post',
    data: search
  })

export const listSalaryDataAll = (search) =>
  request({
    url: '/salaryData/salaryData/listAll',
    method: 'post',
    data: search
  })

export const saveSalaryData = (salaryData) =>
  request({
    url: '/salaryData/salaryData/save',
    method: 'post',
    data: salaryData
  })

export const deleteSalaryData = (salaryData) =>
  request({
    url: '/salaryData/salaryData/delete',
    method: 'post',
    data: salaryData
  })

export const bulkInsertSalaryData = (salaryDatas) =>
  request({
    url: '/salaryData/salaryData/bulkInsert',
    method: 'post',
    data: salaryDatas
  })

export const bulkUpdateSalaryData = (salaryDatas) =>
  request({
    url: '/salaryData/salaryData/bulkUpdate',
    method: 'post',
    data: salaryDatas
  })

export const bulkUpdateAllSalaryData = (salaryDatas) =>
  request({
    url: '/salaryData/salaryData/bulkUpdate',
    method: 'post',
    data: salaryDatas
  })

export const bulkDeleteSalaryData = (salaryDatas) =>
  request({
    url: '/salaryData/salaryData/bulkDelete',
    method: 'post',
    data: salaryDatas
  })

export const fileUpload = (form) =>
  request({
    url: '/salaryData/salaryData/file',
    method: 'post',
    data: form
  })
