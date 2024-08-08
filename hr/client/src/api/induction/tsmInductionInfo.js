import request from '@/utils/request'

export const getTsmInductionInfoById = (id) =>
  request({
    url: '/induction/tsmInductionInfo/' + id,
    method: 'get'
  })

export const listTsmInductionInfoPage = (search) =>
  request({
    url: '/induction/tsmInductionInfo/list',
    method: 'post',
    data: search
  })

export const listTsmInductionInfoAll = (search) =>
  request({
    url: '/induction/tsmInductionInfo/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmInductionInfo = (tsmInductionInfo) =>
  request({
    url: '/induction/tsmInductionInfo/delete',
    method: 'post',
    data: tsmInductionInfo
  })

export const getTsmInductionInfoByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmInductionInfo/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmInductionInfo = (formData) =>
  request({
    url: '/induction/tsmInductionInfo/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmInductionInfo = (processId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmInductionInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmInductionInfo = (taskId) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmInductionInfo/' + taskId + '/listBackActivity',
    method: 'get'
  })
