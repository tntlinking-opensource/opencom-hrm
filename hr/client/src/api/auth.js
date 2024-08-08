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