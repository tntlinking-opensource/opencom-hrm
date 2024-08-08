import request from '@/utils/request'

export const getTsmCardById = (id) =>
  request({
    url: '/induction/tsmCard/' + id,
    method: 'get'
  })

export const listTsmCardPage = (search) =>
  request({
    url: '/induction/tsmCard/list',
    method: 'post',
    data: search
  })

export const listTsmCardAll = (search) =>
  request({
    url: '/induction/tsmCard/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmCard = (tsmCard) =>
  request({
    url: '/induction/tsmCard/delete',
    method: 'post',
    data: tsmCard
  })

export const getTsmCardByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmCard/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmCard = (formData) =>
  request({
    url: '/induction/tsmCard/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmCard = (processId, formData) =>
  request({
    url: '/induction/tsmCard/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmCard = (taskId, formData) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmCard = (taskId) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmCard/' + taskId + '/listBackActivity',
    method: 'get'
  })
