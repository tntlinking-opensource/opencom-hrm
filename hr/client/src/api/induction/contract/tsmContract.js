import request from '@/utils/request'

export const getTsmContractById = (id) =>
  request({
    url: '/induction/contract/tsmContract/' + id,
    method: 'get'
  })

export const listTsmContractPage = (search) =>
  request({
    url: '/induction/contract/tsmContract/list',
    method: 'post',
    data: search
  })

export const listTsmContractAll = (search) =>
  request({
    url: '/induction/contract/tsmContract/listAll',
    method: 'post',
    data: search
  })

export const saveTsmContract = (tsmContract) =>
  request({
    url: '/induction/contract/tsmContract/save',
    method: 'post',
    data: tsmContract
  })

export const deleteTsmContract = (tsmContract) =>
  request({
    url: '/induction/contract/tsmContract/delete',
    method: 'post',
    data: tsmContract
  })

export const bulkInsertTsmContract = (tsmContracts) =>
  request({
    url: '/induction/contract/tsmContract/bulkInsert',
    method: 'post',
    data: tsmContracts
  })

export const bulkUpdateTsmContract = (tsmContracts) =>
  request({
    url: '/induction/contract/tsmContract/bulkUpdate',
    method: 'post',
    data: tsmContracts
  })

export const bulkDeleteTsmContract = (tsmContracts) =>
  request({
    url: '/induction/contract/tsmContract/bulkDelete',
    method: 'post',
    data: tsmContracts
  })
