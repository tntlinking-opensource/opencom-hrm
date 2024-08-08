import request from '@/utils/request'

export const getTsmPostById = (id) =>
  request({
    url: '/induction/tsmPost/' + id,
    method: 'get'
  })

export const listTsmPostPage = (search) =>
  request({
    url: '/induction/tsmPost/list',
    method: 'post',
    data: search
  })

export const listTsmPostAll = (search) =>
  request({
    url: '/induction/tsmPost/listAll',
    method: 'post',
    data: search
  })

export const deleteTsmPost = (tsmPost) =>
  request({
    url: '/induction/tsmPost/delete',
    method: 'post',
    data: tsmPost
  })

export const getTsmPostByTaskId = (TaskId) =>
  request({
    url: '/induction/tsmPost/task/' + TaskId,
    method: 'get'
  })
export const saveDraftTsmPost = (formData) =>
  request({
    url: '/induction/tsmPost/saveDraft',
    method: 'post',
    data: formData
  })
export const createTsmPost = (processId, formData) =>
  request({
    url: '/induction/tsmPost/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateTsmPost = (taskId, formData) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseTsmPost = (taskId) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/induction/tsmPost/' + taskId + '/listBackActivity',
    method: 'get'
  })
