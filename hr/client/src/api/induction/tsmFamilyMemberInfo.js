import request from '@/utils/request'

export const getTsmFamilyMemberInfoById = (id) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + id,
    method: 'get'
  })

export const listTsmFamilyMemberInfoPage = (search) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/list',
    method: 'post',
    data: search
  })

export const listTsmFamilyMemberInfoAll = (search) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmFamilyMemberInfo = (tsmFamilyMemberInfo) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/delete',
    method: 'post',
    data: tsmFamilyMemberInfo
  })

export const getTsmFamilyMemberInfoByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmFamilyMemberInfo = (formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/saveDraft',
    method: 'post',
    data: formData
  })
export const saveTsmFamilyMemberInfo = (formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/save',
    method: 'post',
    data: formData
  })
export const createTsmFamilyMemberInfo = (processId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmFamilyMemberInfo = (taskId, formData) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmFamilyMemberInfo = (taskId) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmFamilyMemberInfo/' + taskId + '/listBackActivity',
    method: 'get'
  })
