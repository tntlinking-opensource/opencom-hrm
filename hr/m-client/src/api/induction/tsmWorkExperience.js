import request from '@/utils/request'

export const getTsmWorkExperienceById = (id) =>
  request({
    url: '/induction/tsmWorkExperience/' + id,
    method: 'get'
  })

export const listTsmWorkExperiencePage = (search) =>
  request({
    url: '/induction/tsmWorkExperience/list',
    method: 'post',
    data: search
  })

export const listTsmWorkExperienceAll = (search) =>
  request({
    url: '/induction/tsmWorkExperience/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmWorkExperience = (tsmWorkExperience) =>
  request({
    url: '/induction/tsmWorkExperience/delete',
    method: 'post',
    data: tsmWorkExperience
  })

export const getTsmWorkExperienceByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmWorkExperience/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmWorkExperience = (formData) =>
  request({
    url: '/induction/tsmWorkExperience/saveDraft',
    method: 'post',
    data: formData
  })
export const saveTsmWorkExperience = (formData) =>
  request({
    url: '/induction/tsmWorkExperience/save',
    method: 'post',
    data: formData
  })

export const createTsmWorkExperience = (processId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmWorkExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmWorkExperience = (taskId) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmWorkExperience/' + taskId + '/listBackActivity',
    method: 'get'
  })
