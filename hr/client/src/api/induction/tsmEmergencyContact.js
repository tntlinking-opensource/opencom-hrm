import request from '@/utils/request'

export const getTsmEmergencyContactById = (id) =>
  request({
    url: '/induction/tsmEmergencyContact/' + id,
    method: 'get'
  })

export const listTsmEmergencyContactPage = (search) =>
  request({
    url: '/induction/tsmEmergencyContact/list',
    method: 'post',
    data: search
  })

export const listTsmEmergencyContactAll = (search) =>
  request({
    url: '/induction/tsmEmergencyContact/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmEmergencyContact = (tsmEmergencyContact) =>
  request({
    url: '/induction/tsmEmergencyContact/delete',
    method: 'post',
    data: tsmEmergencyContact
  })

export const getTsmEmergencyContactByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmEmergencyContact/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmEmergencyContact = (formData) =>
  request({
    url: '/induction/tsmEmergencyContact/saveDraft',
    method: 'post',
    data: formData
  })
export const saveTsmEmergencyContact = (formData) =>
  request({
    url: '/induction/tsmEmergencyContact/save',
    method: 'post',
    data: formData
  })
export const createTsmEmergencyContact = (processId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmEmergencyContact = (taskId, formData) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmEmergencyContact = (taskId) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmEmergencyContact/' + taskId + '/listBackActivity',
    method: 'get'
  })
