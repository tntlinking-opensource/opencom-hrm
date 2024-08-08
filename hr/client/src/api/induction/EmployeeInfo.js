import request from '@/utils/request'
//基本信息详情
export const getInfo = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/' + employeeNo,
    method: 'get',
  })
export const saveBasicInfo = (formData) =>
  request({
    url: '/induction/employeeInfo/basic/save',
    method: 'post',
    data: formData
  })
//岗位信息
export const getPostInfo = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/post/' + employeeNo,
    method: 'get',
  })

export const savePost= (formData) =>
  request({
    url: '/induction/employeeInfo/post/save',
    method: 'post',
    data: formData
  })
//工作经验
export const getWorkExperience = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/workExperience/' + employeeNo,
    method: 'get',
  })
export const saveWorkExperience= (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/workExperience/save/' + employeeNo,
    method: 'post',
    data: formData
  })
//项目经验
export const getProjectExperience = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/projectExperience/' + employeeNo,
    method: 'get',
  })
export const saveProjectExperience= (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/projectExperience/save/' + employeeNo,
    method: 'post',
    data: formData
  })
export const deleteProjectExperience = (formData) =>
  request({
    url: '/induction/tsmProjectExperience/delete',
    method: 'post',
    data: formData
  })
//培训经历
export const getTrainingExperience = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/trainingExperience/' + employeeNo,
    method: 'get',
  })
export const saveTrainingExperience= (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/trainingExperience/save/' + employeeNo,
    method: 'post',
    data: formData
  })
export const deleteTrainingExperience = (formData) =>
  request({
    url: '/induction/tsmTrainingExperience/delete',
    method: 'post',
    data: formData
  })
//证书
export const getQualification = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/qualification/' + employeeNo,
    method: 'get',
  })
export const saveQualification= (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/qualification/save/' + employeeNo,
    method: 'post',
    data: formData
  })
//教育经历
export const getEducationalRecords = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/educationalRecords/' + employeeNo,
    method: 'get',
  })
export const saveEducationalRecords= (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/educationalRecords/save/' + employeeNo,
    method: 'post',
    data: formData
  })
//个人技能
export const getPersonalSkills = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/personalSkills/' + employeeNo,
    method: 'get',
  })
export const savePersonalSkills= (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/personalSkills/save/' + employeeNo,
    method: 'post',
    data: formData
  })
//联系人信息
export const getContacts = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/contacts/' + employeeNo,
    method: 'get',
  })
export const saveContacts = (formData) =>
  request({
    url: '/induction/employeeInfo/contacts/save',
    method: 'post',
    data: formData
  })

//家庭信息
export const getFamilyMemberInfo = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/otherInfo/familyMemberInfo/' + employeeNo,
    method: 'get',
  })
export const saveTsmFamilyMemberInfo = (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/familyMemberInfo/save/' + employeeNo,
    method: 'post',
    data: formData
  })

//合同信息
export const getTsmContractInformation = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/otherInfo/contractInformation/' + employeeNo,
    method: 'get',
  })
export const saveTsmContactInformation = (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/contractInformation/save/' + employeeNo,
    method: 'post',
    data: formData
  })
//卡片信息
export const getCard = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/otherInfo/card/' + employeeNo,
    method: 'get',
  })
export const saveTsmCard = (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/card/save/' + employeeNo,
    method: 'post',
    data: formData
  })
export const deleteCard = (formData) =>
  request({
    url: '/induction/tsmCard/delete',
    method: 'post',
    data: formData
  })
//资产信息
export const getAssetInformation = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/otherInfo/assetInformation/' + employeeNo,
    method: 'get',
  })
export const saveTsmAssetInformation = (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/assetInformation/save/' + employeeNo,
    method: 'post',
    data: formData
  })
export const deleteAssetInformation = (formData) =>
  request({
    url: '/induction/tsmAssetInformation/delete',
    method: 'post',
    data: formData
  })
export const resumeTranslation = (employeeNo,resumeType) =>
  request({
    url: '/induction/pc/other/resume/'+ resumeType +"/"+ employeeNo,
    method: 'get',
    type: 'blob',
    responseType: 'arraybuffer'
  })
export const headSculptureUpload = (formData,employeeNo) =>
  request({
    url: '/induction/employeeInfo/headSculpture/upload/' + employeeNo,
    method: 'post',
    data: formData
  })
export const headSculptureDown = (employeeNo) =>
  request({
    url: '/induction/employeeInfo/headSculpture/down/' + employeeNo,
    method: 'get'
  })

