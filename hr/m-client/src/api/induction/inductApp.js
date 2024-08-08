import request from '@/utils/request'

export const saveDraftTsmInduction = (formData) =>
  request({
    url: '/induction/app/home/saveDraft',
    method: 'post',
    data: formData
  })

export const saveInductionBasic = (formData) =>
  request({
    url: '/induction/app/saveInductionBasic',
    method: 'post',
    data: formData
  })
export const saveOnboardingHome = (formData) =>
  request({
    url: '/induction/app/saveOnboardingHome',
    method: 'post',
    data: formData
  })

export const saveInductionEducational = (formData) =>
  request({
    url: '/induction/app/saveInductionEducational',
    method: 'post',
    data: formData
  })

export const saveInductionWork = (formData) =>
  request({
    url: '/induction/app/saveInductionWork',
    method: 'post',
    data: formData
  })

export const saveInductionPersonalSkills = (formData) =>
  request({
    url: '/induction/app/saveInductionPersonalSkills',
    method: 'post',
    data: formData
  })

export const saveInductionQualification = (formData) =>
  request({
    url: '/induction/app/saveInductionQualification',
    method: 'post',
    data: formData
  })
  export const listUserPropDetailsAll = (search) =>
  request({
    url: '/basic/userPropDetails/listAll',
    method: 'post',
    data: search
  })


export const uploadMany = (formData,inductionId) =>
  request({
    url: '/induction/app/upload/many/' + inductionId,
    method: 'post',
    data: formData
  })
export const downByInductionId = (inductionId) =>
  request({
    url: '/induction/app/downByInductionId/' + inductionId,
    method: 'get',
  })
export const downByEmployeeNo = (employeeNo) =>
  request({
    url: '/induction/app/downByEmployeeNo/' + employeeNo,
    method: 'get',
  })

export const deleteFile = (fileBase64Id) =>
  request({
    url: '/induction/app/deleteFIle/' + fileBase64Id,
    method: 'get',
  })

export const getSysFileById = (id) =>
  request({
      url: '/sys/sysFile/' + id,
      method: 'get'
  })

export const listTsmRecruitAll = (search) =>
  request({
    url: '/recruit/tsmRecruit/listAll',
    method: 'post',
    data: search
  })
export const dataSubmission = (inductionId) =>
  request({
    url: '/induction/app/dataSubmission/' + inductionId,
    method: 'post'
  })
