/**
 * @desc 企业微信授权获取 code
 * @param {string} curHref，href - 当前页面的地址
 */
import axios from 'axios'
import { Notify, Dialog } from 'vant';
const service = axios.create({
  baseURL: process.env.BASE_API,
  // timeout: 10000 // request timeout
  headers: {
      'Content-Type': "application/json;charset=utf-8",
      //'x-Token': 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE2MzYzNTYyNzYsInN1YiI6IntcImhvc3RcIjpcIjEyNy4wLjAuMVwiLFwic2Vzc2lvbklkXCI6XCIyZDA0ODM4Zi0wNmM4LTRkMGEtOTRmYy1mZmIyNDhjNmI2ZjdcIixcInVzZXJJZFwiOlwiNjY3MzMxMDI0NjUyNzMwMzY5XCJ9In0.83cXOWr8qoNyTEj-5COZ7auzTyRfO3eoSX5mlWzbins'
  }
})
import { setLocalWeChatUser } from "./uToken";
let getAuthorizeCode = function(href) {
  return new Promise((resolve, reject) => {
    service.get(`/weChat/getAuthorizeUrl/?url=${href}`).then(res => {
      console.log(res.data)
      if (res.data.code == 100) {
        window.location.href = res.data.data;
        resolve()
      }
    })
  })
}
let authorize = function(curHref) {
  let str = window.location.href;
  return new Promise((resolve, reject) => {
    if (str.indexOf("code") !== -1) {
      let code = str.match(/code=(\S*)&state=/)[1]; // 截取中间的code
      service.get(`/weChat/authorize/${code}`).then(res => {
        if (res.data.code == 100) {
          setLocalWeChatUser(res.data.data.weChatUser)
          resolve(true)
        }else {
          //window.location.href = '#/bindingInfo'
          Dialog.alert({
            title: '提示',
            message: JSON.stringify(res.data.msg),
          }).then(() => {
            
          });
          resolve(false)
        }
      })
    } else {
        getAuthorizeCode(curHref)
    }
  })
}

export default authorize
