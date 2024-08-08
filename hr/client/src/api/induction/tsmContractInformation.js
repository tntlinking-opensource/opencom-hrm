import request from '@/utils/request'

export const getTsmContractInformationById = (id) =>
  request({
    url: '/induction/tsmContractInformation/' + id,
    method: 'get'
  })

export const listTsmContractInformationPage = (search) =>
  request({
    url: '/induction/tsmContractInformation/list',
    method: 'post',
    data: search
  })

export const listTsmContractInformationAll = (search) =>
  request({
    url: '/induction/tsmContractInformation/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmContractInformation = (tsmContractInformation) =>
  request({
    url: '/induction/tsmContractInformation/delete',
    method: 'post',
    data: tsmContractInformation
  })

export const getTsmContractInformationByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmContractInformation/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmContractInformation = (formData) =>
  request({
    url: '/induction/tsmContractInformation/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmContractInformation = (processId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmContractInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmContractInformation = (taskId) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmContractInformation/' + taskId + '/listBackActivity',
    method: 'get'
  })
