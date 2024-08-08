import request from '@/utils/request'

export const getTsmRecruitById = (id) =>
  request({
    url: '/hrRecruit/tsmRecruit/' + id,
    method: 'get'
  })

export const listTsmRecruitPage = (search) =>
  request({
    url: '/hrRecruit/tsmRecruit/list',
    method: 'post',
    data: search
  })

export const listTsmRecruitAll = (search) =>
  request({
    url: '/hrRecruit/tsmRecruit/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmRecruit = (tsmRecruit) =>
  request({
    url: '/recruit/tsmRecruit/delete',
    method: 'post',
    data: tsmRecruit
  })

export const getTsmRecruitByTaskId = (TaskId) =>
  request({
    url: '/recruit/tsmRecruit/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmRecruit = (formData) =>
  request({
    url: '/recruit/tsmRecruit/saveDraft',
    method: 'post',
    data: formData
  })

export const saveTsmRecruit = (tsmRecruit) =>
  request({
    url: '/hrRecruit/tsmRecruit/save',
    method: 'post',
    data: tsmRecruit
  })

export const createTsmRecruit = (processId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmRecruit = (taskId, formData) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmRecruit = (taskId) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/recruit/tsmRecruit/' + taskId + '/listBackActivity',
    method: 'get'
  })
