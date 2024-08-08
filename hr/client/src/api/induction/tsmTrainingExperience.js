import request from '@/utils/request'

export const getTsmTrainingExperienceById = (id) =>
  request({
    url: '/induction/tsmTrainingExperience/' + id,
    method: 'get'
  })

export const listTsmTrainingExperiencePage = (search) =>
  request({
    url: '/induction/tsmTrainingExperience/list',
    method: 'post',
    data: search
  })

export const listTsmTrainingExperienceAll = (search) =>
  request({
    url: '/induction/tsmTrainingExperience/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmTrainingExperience = (tsmTrainingExperience) =>
  request({
    url: '/induction/tsmTrainingExperience/delete',
    method: 'post',
    data: tsmTrainingExperience
  })

export const getTsmTrainingExperienceByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmTrainingExperience/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmTrainingExperience = (formData) =>
  request({
    url: '/induction/tsmTrainingExperience/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmTrainingExperience = (processId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmTrainingExperience = (taskId, formData) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmTrainingExperience = (taskId) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmTrainingExperience/' + taskId + '/listBackActivity',
    method: 'get'
  })
