import request from '@/utils/request'

export const getInductionContractRenewalById = (id) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + id,
    method: 'get'
  })

export const listInductionContractRenewalPage = (search) =>
  request({
    url: '/induction/contract/inductionContractRenewal/list',
    method: 'post',
    data: search
  })

export const listInductionContractRenewalAll = (search) =>
  request({
    url: '/induction/contract/inductionContractRenewal/listAll',
    method: 'post',
    data: search
  })

export const saveInductionContractRenewal = (formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/save',
    method: 'post',
    data: formData
  })

export const deleteInductionContractRenewal = (inductionContractRenewal) =>
  request({
    url: '/induction/contract/inductionContractRenewal/delete',
    method: 'post',
    data: inductionContractRenewal
  })

export const generateById = (id) =>
  request({
    url: '/induction/contract/inductionContractRenewal/generate/' + id,
    method: 'get'
  })

export const startById = (id) =>
  request({
    url: '/induction/contract/inductionContractRenewal/start/' + id,
    method: 'get'
  })

export const viewReport = (id) =>
  request({
    url: '/induction/contract/inductionContractRenewal/report/' + id,
    method: 'get'
  })

export const viewContract = (id) =>
  request({
    url: '/induction/contract/inductionContractRenewal/viewContract/' + id,
    method: 'get'
  })

export const uploadContract = (id) =>
  request({
    url: '/induction/contract/inductionContractRenewal/upload/' + id,
    method: 'get',
    responseType: 'blob'
  })

export const bulkGenerate = (renewalContractList) =>
  request({
    url: '/induction/contract/inductionContractRenewal/bulkGenerate',
    method: 'post',
    data: renewalContractList
  })

export const bulkStart = (renewalContractList) =>
  request({
    url: '/induction/contract/inductionContractRenewal/bulkStart',
    method: 'post',
    data: renewalContractList
  })

export const bulkInsertInductionContractRenewal = (inductionBatchContractRenewals) =>
  request({
    url: '/induction/contract/inductionContractRenewal/bulkInsert',
    method: 'post',
    data: inductionBatchContractRenewals
  })

export const firstCreateContract = (procDefId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + procDefId + '/firstCreate',
    method: 'post',
    data: formData
  })

export const bulkSignCreate = (procDefId, formDataList) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + procDefId + '/bulkSignCreate',
    method: 'post',
    data: formDataList
  })

export const getInductionContractRenewalByTaskId = (TaskId) =>
  request({
    url: '/induction/contract/inductionContractRenewal/task/' + TaskId,
    method: 'get'
  })
export const saveDraftInductionContractRenewal = (formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/saveDraft',
    method: 'post',
    data: formData
  })
export const createInductionContractRenewal = (processId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateInductionContractRenewal = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseInductionContractRenewal = (taskId) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/contract/inductionContractRenewal/' + taskId + '/listBackActivity',
    method: 'get'
  })
