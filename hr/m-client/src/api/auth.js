import request from '@/utils/request'
import qs from 'qs'

export const getToken = (loginName, password) =>
    request({
        url: '/auth/token',
        method: 'post',
        data: qs.stringify({
            loginName,
            password
        })
    })

export const fetchAccessTokenByRefreshToken = (refreshToken) =>
    request({
        url: `user/toB/fetchAccessTokenByRefreshToken?refreshToken=${refreshToken}`,
        method: 'get'
    })

export const getLoginedToken = () =>
    request({
        url: '/auth/loginedtocken',
        method: 'post'
    })

export const logOut = () =>
    request({
        url: '/auth/logout',
        method: 'post'
    })

export const getWechatSign = (url,flag) =>
    request({
      url: `/weChat/getWechatSign/?url=${url}&flag=${flag}`,
      method: 'get'
    })
export const bindUser = (loginName, openId) =>
  request({
    url: '/weChat/bindUser',
    method: 'post',
    data: qs.stringify({
      loginName,
      openId
    })
  })

// request.get(`/weChat/getUserByToken/${userid}`); 原通过微信获取认证后拿到数据
export const getUserInfo = (userid) =>
  request({
    url: `/weChat/getUserByToken/${userid}`,
    method: 'get'
  })

