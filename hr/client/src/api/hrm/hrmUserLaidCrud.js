import request from '@/utils/request'

export const getHrmUserLaidById = (id) =>
  request({
    url: '/hrm/hrmUserCrudLaid/' + id,
    method: 'get'
  })

export const listHrmUserLaidPage = (search) =>
  request({
    url: '/hrm/hrmUserCrudLaid/list',
    method: 'post',
    data: search
  })

export const listHrmUserLaidAll = (search) =>
  request({
    url: '/hrm/hrmUserCrudLaid/listAll',
    method: 'post',
    data: search
  })

export const saveHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/save',
    method: 'post',
    data: hrmUserLaid
  })
export const handoverHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/handover',
    method: 'post',
    data: hrmUserLaid
  })

export const processingHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/processing',
    method: 'post',
    data: hrmUserLaid
  })
export const financeHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/finance',
    method: 'post',
    data: hrmUserLaid
  })

export const docLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/doc/laid',
    method: 'post',
    data: hrmUserLaid,
    type:'blob',
    responseType: 'arraybuffer'
  })

export const docLaidApply = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/doc/laidApply',
    method: 'post',
    data: hrmUserLaid,
    type:'blob',
    responseType: 'arraybuffer'
  })

export const resignationHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/resignation',
    method: 'post',
    data: hrmUserLaid
  })
export const laidHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/laid',
    method: 'post',
    data: hrmUserLaid
  })

export const deleteHrmUserLaid = (hrmUserLaid) =>
  request({
    url: '/hrm/hrmUserCrudLaid/delete',
    method: 'post',
    data: hrmUserLaid
  })

export const bulkInsertHrmUserLaid = (hrmUserLaids) =>
  request({
    url: '/hrm/hrmUserCrudLaid/bulkInsert',
    method: 'post',
    data: hrmUserLaids
  })

export const bulkUpdateHrmUserLaid = (hrmUserLaids) =>
  request({
    url: '/hrm/hrmUserCrudLaid/bulkUpdate',
    method: 'post',
    data: hrmUserLaids
  })

export const bulkDeleteHrmUserLaid = (hrmUserLaids) =>
  request({
    url: '/hrm/hrmUserCrudLaid/bulkDelete',
    method: 'post',
    data: hrmUserLaids
  })
