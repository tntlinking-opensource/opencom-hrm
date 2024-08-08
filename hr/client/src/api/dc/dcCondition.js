import request from '@/utils/request'

export const getDcConditionById = (id) =>
    request({
        url: '/dc/dcCondition/' + id,
        method: 'get'
    })

export const listDcConditionPage = (search) =>
    request({
        url: '/dc/dcCondition/list',
        method: 'post',
        data: search
    })

export const listDcConditionAll = (search) =>
    request({
        url: '/dc/dcCondition/listAll',
        method: 'post',
        data: search
    })


export const saveDcCondition = (dcCondition) => 
    request({
        url: '/dc/dcCondition/save',
        method: 'post',
        data: dcCondition
    })
  
export const deleteDcCondition = (dcCondition) =>
    request({
        url: '/dc/dcCondition/delete',
        method: 'post',
        data: dcCondition
    })
    
export const bulkInsertDcCondition = (dcConditions) =>
    request({
        url: '/dc/dcCondition/bulkInsert',
        method: 'post',
        data: dcConditions
    })
    
export const bulkUpdateDcCondition = (dcConditions) =>
    request({
        url: '/dc/dcCondition/bulkUpdate',
        method: 'post',
        data: dcConditions
    })

export const bulkDeleteDcCondition = (dcConditions) =>
    request({
        url: '/dc/dcCondition/bulkDelete',
        method: 'post',
        data: dcConditions
    })
    
export const bulkUpdateSort = (userId, routerId, condition) =>
    request({
      url: '/dc/dcCondition/bulkUpdateSort/' + userId + '/' + routerId,
      method: 'post',
      data: condition
    })