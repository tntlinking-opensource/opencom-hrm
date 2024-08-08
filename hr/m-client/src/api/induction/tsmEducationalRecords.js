import request from '@/utils/request'

export const getTsmEducationalRecordsById = (id) =>
  request({
    url: '/induction/tsmEducationalRecords/' + id,
    method: 'get'
  })

export const listTsmEducationalRecordsPage = (search) =>
  request({
    url: '/induction/tsmEducationalRecords/list',
    method: 'post',
    data: search
  })

export const listTsmEducationalRecordsAll = (search) =>
  request({
    url: '/induction/tsmEducationalRecords/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmEducationalRecords = (tsmEducationalRecords) =>
  request({
    url: '/induction/tsmEducationalRecords/delete',
    method: 'post',
    data: tsmEducationalRecords
  })

export const getTsmEducationalRecordsByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmEducationalRecords/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmEducationalRecords = (formData) =>
  request({
    url: '/induction/tsmEducationalRecords/saveDraft',
    method: 'post',
    data: formData
  })
export const saveTsmEducationalRecords = (formData) =>
  request({
    url: '/induction/tsmEducationalRecords/save',
    method: 'post',
    data: formData
  })
export const createTsmEducationalRecords = (processId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmEducationalRecords = (taskId, formData) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmEducationalRecords = (taskId) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmEducationalRecords/' + taskId + '/listBackActivity',
    method: 'get'
  })
