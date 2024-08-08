import request from '@/utils/request'

export const getSalaryBatchById = (id) =>
  request({
    url: '/salaryPay/salaryBatch/' + id,
    method: 'get'
  })

export const listSalaryBatchPage = (search) =>
  request({
    url: '/salaryPay/salaryBatch/list',
    method: 'post',
    data: search
  })

export const listSalaryBatchAll = (search) =>
  request({
    url: '/salaryPay/salaryBatch/listAll',
    method: 'post',
    data: search
  })

export const saveSalaryBatch = (salaryBatch) =>
  request({
    url: '/salaryPay/salaryBatch/save',
    method: 'post',
    data: salaryBatch
  })

export const deleteSalaryBatch = (salaryBatch) =>
  request({
    url: '/salaryPay/salaryBatch/delete',
    method: 'post',
    data: salaryBatch
  })

export const bulkInsertSalaryBatch = (salaryBatchs) =>
  request({
    url: '/salaryPay/salaryBatch/bulkInsert',
    method: 'post',
    data: salaryBatchs
  })

export const bulkUpdateSalaryBatch = (salaryBatchs) =>
  request({
    url: '/salaryPay/salaryBatch/bulkUpdate',
    method: 'post',
    data: salaryBatchs
  })

export const bulkDeleteSalaryBatch = (salaryBatchs) =>
  request({
    url: '/salaryPay/salaryBatch/bulkDelete',
    method: 'post',
    data: salaryBatchs
  })
