import request from '@/utils/request'

export const getTsmInductionBasicById = (id) =>
  request({
    url: '/induction/tsmInductionBasic/' + id,
    method: 'get'
  })

export const getTsmInductionEnhanceBasicById = (id) =>
  request({
    url: '/induction/tsmInductionBasic/enhanceBasic/' + id,
    method: 'get'
  })

export const getInfoCompletionBasicById = (id) =>
  request({
    url: '/induction/tsmInductionBasic/infoCompletion/' + id,
    method: 'get'
  })
export const getFlowInitById = (id) =>
  request({
    url: '/induction/tsmInductionBasic/flowInit/' + id,
    method: 'get'
  })

export const listTsmInductionBasicPage = (search) =>
  request({
    url: '/induction/tsmInductionBasic/list',
    method: 'post',
    data: search
  })
//首页条件搜索
export const listInductionHomeSearch = (formData) =>
  request({
    url: '/induction/tsmInductionBasic/homeSearch',
    method: 'post',
    data: formData
  })

export const listTsmInductionBasicAll = (search) =>
  request({
    url: '/induction/tsmInductionBasic/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmInductionBasic = (tsmInductionBasic) =>
  request({
    url: '/induction/tsmInductionBasic/delete',
    method: 'post',
    data: tsmInductionBasic
  })

export const getTsmInductionBasicByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmInductionBasic/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmInductionBasic = (formData) =>
  request({
    url: '/induction/tsmInductionBasic/saveDraft',
    method: 'post',
    data: formData
  })
export const saveEnhanceBasic = (formData) =>
  request({
    url: '/induction/tsmInductionBasic/saveEnhanceBasic',
    method: 'post',
    data: formData
  })

export const saveInfoCompletion = (formData) =>
  request({
    url: '/induction/tsmInductionBasic/saveInfoCompletion',
    method: 'post',
    data: formData
  })

export const createTsmInductionBasic = (processId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmInductionBasic = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmInductionBasic = (taskId) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmInductionBasic/' + taskId + '/listBackActivity',
    method: 'get'
  })
