import request from '@/utils/request'

export const getSalaryArchivesInfoById = (id) =>
  request({
    url: '/salaryData/salaryArchivesInfo/' + id,
    method: 'get'
  })

export const listSalaryArchivesInfoPage = (search) =>
  request({
    url: '/salaryData/salaryArchivesInfo/list',
    method: 'post',
    data: search
  })

export const listSalaryArchivesInfoAll = (search) =>
  request({
    url: '/salaryData/salaryArchivesInfo/listAll',
    method: 'post',
    data: search
  })

export const saveSalaryArchivesInfo = (salaryArchivesInfo) =>
  request({
    url: '/salaryData/salaryArchivesInfo/save',
    method: 'post',
    data: salaryArchivesInfo
  })

export const deleteSalaryArchivesInfo = (salaryArchivesInfo) =>
  request({
    url: '/salaryData/salaryArchivesInfo/delete',
    method: 'post',
    data: salaryArchivesInfo
  })

export const bulkInsertSalaryArchivesInfo = (salaryArchivesInfos) =>
  request({
    url: '/salaryData/salaryArchivesInfo/bulkInsert',
    method: 'post',
    data: salaryArchivesInfos
  })

export const bulkUpdateSalaryArchivesInfo = (salaryArchivesInfos) =>
  request({
    url: '/salaryData/salaryArchivesInfo/bulkUpdate',
    method: 'post',
    data: salaryArchivesInfos
  })

export const bulkDeleteSalaryArchivesInfo = (salaryArchivesInfos) =>
  request({
    url: '/salaryData/salaryArchivesInfo/bulkDelete',
    method: 'post',
    data: salaryArchivesInfos
  })
