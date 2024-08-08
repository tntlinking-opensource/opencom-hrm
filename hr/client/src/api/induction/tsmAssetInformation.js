import request from '@/utils/request'

export const getTsmAssetInformationById = (id) =>
  request({
    url: '/induction/tsmAssetInformation/' + id,
    method: 'get'
  })

export const listTsmAssetInformationPage = (search) =>
  request({
    url: '/induction/tsmAssetInformation/list',
    method: 'post',
    data: search
  })

export const listTsmAssetInformationAll = (search) =>
  request({
    url: '/induction/tsmAssetInformation/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmAssetInformation = (tsmAssetInformation) =>
  request({
    url: '/induction/tsmAssetInformation/delete',
    method: 'post',
    data: tsmAssetInformation
  })

export const getTsmAssetInformationByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmAssetInformation/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmAssetInformation = (formData) =>
  request({
    url: '/induction/tsmAssetInformation/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmAssetInformation = (processId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmAssetInformation = (taskId, formData) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmAssetInformation = (taskId) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmAssetInformation/' + taskId + '/listBackActivity',
    method: 'get'
  })
