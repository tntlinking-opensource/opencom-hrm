import request from '@/utils/request'

export const getTsmPersonalSkillsById = (id) =>
  request({
    url: '/induction/tsmPersonalSkills/' + id,
    method: 'get'
  })

export const listTsmPersonalSkillsPage = (search) =>
  request({
    url: '/induction/tsmPersonalSkills/list',
    method: 'post',
    data: search
  })

export const listTsmPersonalSkillsAll = (search) =>
  request({
    url: '/induction/tsmPersonalSkills/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmPersonalSkills = (tsmPersonalSkills) =>
  request({
    url: '/induction/tsmPersonalSkills/delete',
    method: 'post',
    data: tsmPersonalSkills
  })

export const getTsmPersonalSkillsByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmPersonalSkills/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmPersonalSkills = (formData) =>
  request({
    url: '/induction/tsmPersonalSkills/saveDraft',
    method: 'post',
    data: formData
  })
export const saveTsmPersonalSkills = (formData) =>
  request({
    url: '/induction/tsmPersonalSkills/save',
    method: 'post',
    data: formData
  })
export const createTsmPersonalSkills = (processId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmPersonalSkills = (taskId, formData) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmPersonalSkills = (taskId) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmPersonalSkills/' + taskId + '/listBackActivity',
    method: 'get'
  })
