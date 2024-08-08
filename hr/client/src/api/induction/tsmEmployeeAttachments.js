import request from '@/utils/request'

export const getTsmEmployeeAttachmentsById = (id) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + id,
    method: 'get'
  })

export const listTsmEmployeeAttachmentsPage = (search) =>
  request({
    url: '/induction/tsmEmployeeAttachments/list',
    method: 'post',
    data: search
  })

export const listTsmEmployeeAttachmentsAll = (search) =>
  request({
    url: '/induction/tsmEmployeeAttachments/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmEmployeeAttachments = (tsmEmployeeAttachments) =>
  request({
    url: '/induction/tsmEmployeeAttachments/delete',
    method: 'post',
    data: tsmEmployeeAttachments
  })

export const getTsmEmployeeAttachmentsByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmEmployeeAttachments/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmEmployeeAttachments = (formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmEmployeeAttachments = (processId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmEmployeeAttachments = (taskId, formData) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmEmployeeAttachments = (taskId) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmEmployeeAttachments/' + taskId + '/listBackActivity',
    method: 'get'
  })
