import request from '@/utils/request'

export const getTsmQualificationById = (id) =>
  request({
    url: '/induction/tsmQualification/' + id,
    method: 'get'
  })

export const listTsmQualificationPage = (search) =>
  request({
    url: '/induction/tsmQualification/list',
    method: 'post',
    data: search
  })

export const listTsmQualificationAll = (search) =>
  request({
    url: '/induction/tsmQualification/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmQualification = (tsmQualification) =>
  request({
    url: '/induction/tsmQualification/delete',
    method: 'post',
    data: tsmQualification
  })

export const getTsmQualificationByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmQualification/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmQualification = (formData) =>
  request({
    url: '/induction/tsmQualification/saveDraft',
    method: 'post',
    data: formData
  })
export const saveTsmQualification = (formData) =>
  request({
    url: '/induction/tsmQualification/save',
    method: 'post',
    data: formData
  })
export const createTsmQualification = (processId, formData) =>
  request({
    url: '/induction/tsmQualification/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmQualification = (taskId, formData) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmQualification = (taskId) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmQualification/' + taskId + '/listBackActivity',
    method: 'get'
  })
