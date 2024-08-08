import request from '@/utils/request'

export const sendMail = (formData) =>
  request({
    url: '/notice/mail/sendEmail',
    method: 'post',
    data: formData
  })
