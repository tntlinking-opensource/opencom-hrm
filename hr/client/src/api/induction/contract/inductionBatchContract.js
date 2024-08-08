import request from '@/utils/request'

export const getInductionBatchContractById = (id) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + id,
    method: 'get'
  })

export const listInductionBatchContractPage = (search) =>
  request({
    url: '/induction/contract/inductionBatchContract/list',
    method: 'post',
    data: search
  })

export const listInductionBatchContractAll = (search) =>
  request({
    url: '/induction/contract/inductionBatchContract/listAll',
    method: 'post',
    data: search
  })

export const saveInductionBatchContract = (inductionBatchContract) =>
  request({
    url: '/induction/contract/inductionBatchContract/save',
    method: 'post',
    data: inductionBatchContract
  })

export const deleteInductionBatchContract = (inductionBatchContract) =>
  request({
    url: '/induction/contract/inductionBatchContract/delete',
    method: 'post',
    data: inductionBatchContract
  })

export const bulkInsertInductionBatchContract = (inductionBatchContracts) =>
  request({
    url: '/induction/contract/inductionBatchContract/bulkInsert',
    method: 'post',
    data: inductionBatchContracts
  })

export const bulkUpdateInductionBatchContract = (inductionBatchContracts) =>
  request({
    url: '/induction/contract/inductionBatchContract/bulkUpdate',
    method: 'post',
    data: inductionBatchContracts
  })

export const bulkDeleteInductionBatchContract = (inductionBatchContracts) =>
  request({
    url: '/induction/contract/inductionBatchContract/bulkDelete',
    method: 'post',
    data: inductionBatchContracts
  })

export const generateById = (id) =>
  request({
    url: '/induction/contract/inductionBatchContract/generate/' + id,
    method: 'get'
  })

export const startById = (id) =>
  request({
    url: '/induction/contract/inductionBatchContract/start/' + id,
    method: 'get'
  })

export const viewContract = (id) =>
  request({
    url: '/induction/contract/inductionBatchContract/viewContract/' + id,
    method: 'get'
  })

export const uploadContract = (id) =>
  request({
    url: '/induction/contract/inductionBatchContract/upload/' + id,
    method: 'get',
    responseType: 'blob'
  })

export const viewReport = (id) =>
  request({
    url: '/induction/contract/inductionBatchContract/report/' + id,
    method: 'get'
  })

export const bulkGenerate = (batchContractList) =>
  request({
    url: '/induction/contract/inductionBatchContract/bulkGenerate',
    method: 'post',
    data: batchContractList
  })

export const bulkStart = (batchContractList) =>
  request({
    url: '/induction/contract/inductionBatchContract/bulkStart',
    method: 'post',
    data: batchContractList
  })

export const getInductionBatchContractByTaskId = (TaskId) =>
  request({
    url: '/induction/contract/inductionBatchContract/task/' + TaskId,
    method: 'get'
  })

export const saveDraftInductionBatchContract = (formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/saveDraft',
    method: 'post',
    data: formData
  })

export const createInductionBatchContract = (processId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const firstCreateContract = (procDefId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + procDefId + '/firstCreate',
    method: 'post',
    data: formData
  })

export const bulkSignCreate = (procDefId, formDataList) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + procDefId + '/bulkSignCreate',
    method: 'post',
    data: formDataList
  })

export const reapplyInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateInductionBatchContract = (taskId, formData) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseInductionBatchContract = (taskId) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/contract/inductionBatchContract/' + taskId + '/listBackActivity',
    method: 'get'
  })

