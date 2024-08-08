import request from '@/utils/request'

export const getTsmProjectExperienceById = (id) =>
  request({
    url: '/induction/tsmProjectExperience/' + id,
    method: 'get'
  })

export const listTsmProjectExperiencePage = (search) =>
  request({
    url: '/induction/tsmProjectExperience/list',
    method: 'post',
    data: search
  })

export const listTsmProjectExperienceAll = (search) =>
  request({
    url: '/induction/tsmProjectExperience/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmProjectExperience = (tsmProjectExperience) =>
  request({
    url: '/induction/tsmProjectExperience/delete',
    method: 'post',
    data: tsmProjectExperience
  })

export const getTsmProjectExperienceByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmProjectExperience/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmProjectExperience = (formData) =>
  request({
    url: '/induction/tsmProjectExperience/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmProjectExperience = (processId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmProjectExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmProjectExperience = (taskId) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmProjectExperience/' + taskId + '/listBackActivity',
    method: 'get'
  })
