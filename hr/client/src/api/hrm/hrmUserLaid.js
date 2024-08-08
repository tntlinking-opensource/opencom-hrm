import request from '@/utils/request'

export const getHrmUserLaidById = (id) =>
  request({
    url: '/hrm/hrmUserLaid/' + id,
    method: 'get'
  })

export const listHrmUserLaidPage = (search) =>
  request({
    url: '/hrm/hrmUserLaid/list',
    method: 'post',
    data: search
  })

export const listHrmUserLaidAll = (search) =>
  request({
    url: '/hrm/hrmUserLaid/listAll',
    method: 'post',
    data: search
  })

export const deleteHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserLaid/delete',
    method: 'post',
    data: hrmUserLaid
  })

export const getHrmUserLaidByTaskId = (TaskId) =>
  request({
    url: '/hrm/hrmUserLaid/task/' + TaskId,
    method: 'get'
  })
export const saveDraftHrmUserLaid = (formData) =>
  request({
    url: '/hrm/hrmUserLaid/saveDraft',
    method: 'post',
    data: formData
  })
export const createHrmUserLaid = (processId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + processId + '/create',
    method: 'post',
    data: formData
  })

export const reapplyHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/reapply',
    method: 'post',
    data: formData
  })

export const agreeHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/agree',
    method: 'post',
    data: formData
  })

export const forwardHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/forward',
    method: 'post',
    data: formData
  })

export const commissionHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/commission',
    method: 'post',
    data: formData
  })

export const proposeHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/propose',
    method: 'post',
    data: formData
  })

export const backHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/back',
    method: 'post',
    data: formData
  })
export const rejectHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/reject',
    method: 'post',
    data: formData
  })

export const terminateHrmUserLaid = (taskId, formData) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/terminate',
    method: 'post',
    data: formData
  })

export const reverseHrmUserLaid = (taskId) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/reverse',
    method: 'post'
  })

export const listBackActivity = (taskId) =>
  request({
    url: '/hrm/hrmUserLaid/' + taskId + '/listBackActivity',
    method: 'get'
  })
