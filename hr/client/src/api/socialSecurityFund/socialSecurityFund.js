import request from '@/utils/request'

export const getSocialSecurityFundById = (id) =>
  request({
    url: '/socialSecurityFund/' + id,
    method: 'get'
  })

export const listSocialSecurityFundPage = (search) =>
  request({
    url: '/socialSecurityFund/list',
    method: 'post',
    data: search
  })

export const listSocialSecurityFundAll = (search) =>
  request({
    url: '/socialSecurityFund/listAll',
    method: 'post',
    data: search
  })
export const listBill = (search) =>
  request({
    url: '/billDetails/list',
    method: 'post',
    data: search
  })
  export const initAllEmployee = (search) =>
  request({
    url: '/socialSecurityFund/initAllEmployee',
    method: 'post',
    data: search
  })
  export const initEmployee = (search) =>
  request({
    url: '/socialSecurityFund/initEmployee',
    method: 'post',
    data: search
  })

  
export const generateBillDetails = (search) =>
  request({
    url: '/billDetails/generateBillDetails',
    method: 'post',
    data: search
  })

export const saveSocialSecurityFund = (socialSecurityFund) =>
  request({
    url: '/socialSecurityFund/save',
    method: 'post',
    data: socialSecurityFund
  })

export const deleteSocialSecurityFund = (socialSecurityFund) =>
  request({
    url: '/socialSecurityFund/delete',
    method: 'post',
    data: socialSecurityFund
  })
  export const deleteBillDetails = (socialSecurityFund) =>
  request({
    url: '/billDetails/deleteByBatchNo',
    method: 'post',
    data: socialSecurityFund
  })

  
export const bulkInsertSocialSecurityFund = (socialSecurityFunds) =>
  request({
    url: '/socialSecurityFund/bulkInsert',
    method: 'post',
    data: socialSecurityFunds
  })

export const bulkUpdateSocialSecurityFund = (socialSecurityFunds) =>
  request({
    url: '/socialSecurityFund/bulkUpdate',
    method: 'post',
    data: socialSecurityFunds
  })

export const bulkDeleteSocialSecurityFund = (socialSecurityFunds) =>
  request({
    url: '/socialSecurityFund/bulkDelete',
    method: 'post',
    data: socialSecurityFunds
  })

export const downloadTemplate = () =>
  request({
    url: '/socialSecurityFund/template',
    method: 'get',
    type: 'blob',
    responseType: 'arraybuffer'
  })

export const fileUpload = (form) =>
  request({
    url: '/socialSecurityFund/import',
    method: 'post',
    data: form
  })

  export const getBillDetailsList = (form) =>
  request({
    url: '/billDetails/getBillDetailsList',
    method: 'post',
    data: form
  })

  export const saveBillDetails = (billDetails) =>
  request({
    url: '/billDetails/save',
    method: 'post',
    data: billDetails
  })

  export const listAllBill = (search) =>
  request({
    url: '/billDetails/listAll',
    method: 'post',
    data: search
  })

  export const exportBillDetails = (billDetails) =>
  request({
    url: '/billDetails/exportBillDetails',
    method: 'post',
    type: 'blob',
    responseType: 'arraybuffer',
    data: billDetails
  })