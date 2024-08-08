import request from '@/utils/request'

export const getSalaryBatchById = (data) =>
  request({
    url: '/salaryData/salaryData/listSalaryData',
    method: 'post',
    data: data
  })

export const listSalaryBatchPage = (search) =>
  request({
    url: '/salaryBatch/salaryBatch/list',
    method: 'post',
    data: search
  })

export const listSalaryBatchAll = (search) =>
  request({
    url: '/salaryBatch/salaryBatch/listAll',
    method: 'post',
    data: search
  })

export const saveSalaryBatch = (salaryBatch) =>
  request({
    url: '/salaryBatch/salaryBatch/save',
    method: 'post',
    data: salaryBatch
  })

export const deleteSalaryBatch = (salaryBatch) =>
  request({
    url: '/salaryBatch/salaryBatch/delete',
    method: 'post',
    data: salaryBatch
  })

export const bulkInsertSalaryBatch = (salaryBatchs) =>
  request({
    url: '/salaryBatch/salaryBatch/bulkInsert',
    method: 'post',
    data: salaryBatchs
  })

export const bulkUpdateSalaryBatch = (salaryBatchs) =>
  request({
    url: '/salaryBatch/salaryBatch/bulkUpdate',
    method: 'post',
    data: salaryBatchs
  })

export const bulkDeleteSalaryBatch = (salaryBatchs) =>
  request({
    url: '/salaryBatch/salaryBatch/bulkDelete',
    method: 'post',
    data: salaryBatchs
  })

export const fileSalaryUpload = (form) =>
  request({
    url: '/salaryData/salaryData/file',
    method: 'post',
    data: form
  })

export const downloadTemplateSalary = () =>
  request({
    url: '/salaryBatch/salaryBatch/downloadTemplate',
    method: 'get',
    type:'blob',
    responseType: 'arraybuffer'
  })

export const listSalaryDataDetailsInfoPage = (search) =>
  request({
    url: '/salaryData/salaryData/list',
    method: 'post',
    data: search
  })

export const getBillingFun = (salaryData) =>
  request({
    url: '/salaryData/salaryData/getBilling',
    method: 'post',
    data: salaryData
  })

export const getAllBillingFun = (data) =>
  request({
    url: '/salaryData/salaryData/getAllBilling',
    method: 'post',
    data: data
  })

export const getSpecialAddDeductFun = (salaryData) =>
  request({
    url: '/salaryData/salaryData/getSpecialAddDeduct',
    method: 'post',
    data: salaryData
  })

export const getAllSpecialAddDeductFun = (data) =>
  request({
    url: '/salaryData/salaryData/getAllSpecialAddDeduct',
    method: 'post',
    data: data
  })

export const salaryCalculateFun = (salaryData) =>
  request({
    url: '/salaryData/salaryData/salaryCalculate',
    method: 'post',
    data: salaryData
  })

export const salaryConfirmFun = (salaryBatch) =>
  request({
    url: '/salaryBatch/salaryBatch/salaryConfirm',
    method: 'post',
    data: salaryBatch
  })
export const isSalaryConfirmFun = (salaryBatch) =>
  request({
    url: '/salaryBatch/salaryBatch/isSalaryConfirm',
    method: 'post',
    data: salaryBatch
  })
export const exportSalaryData = (salaryBatch) =>
  request({
    url: '/salaryBatch/salaryBatch/exportSalaryDataByBatch',
    method: 'post',
    type: 'blob',
    responseType: 'arraybuffer',
    data: salaryBatch
  })
export const allSalaryCalculateFun = (data) =>
  request({
    url: '/salaryData/salaryData/allSalaryCalculate',
    method: 'post',
    data: data
  })
