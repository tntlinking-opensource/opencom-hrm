/**
 * @desc 守卫路由信息，后续可根据实际情况修改
 */
import router from '../router'
import { Notify } from 'vant'
import { getLocalToken, getLocalRouters, getAppRoot } from '@/utils/auth'
const _import = require('../router/_import_' + process.env.NODE_ENV)

// 微应用时，appKey作为路由前缀
const root = getAppRoot()

// 构造路由
initRouters()

function initRouters() {
  let routers = getLocalRouters()
  if (routers && routers.length > 0) {
    let indexRouter = { // 根路由
      path: `${root ? root : '/'}`,
      name: '/',
      redirect: '/home',
      component: _import('layout/index'),
      meta: { requireAuth: true, verify: true },
      children: [
        // 一层菜单路由
        ...generateRouters().menuRouters
      ]
    }
    router.addRoutes([indexRouter, ...generateRouters().childRouters])
  }
}
function generateRouters() {
  
  let routers = getLocalRouters()
  let menuRouters = []
  let childRouters = []
  if (!routers) {
    return { menuRouters, childRouters }
  }
  for (let router of routers) {
    if (router.code) { // 非目录
      let routerProps = JSON.parse(router.properties)
      if (router.parent.id == 0) {
        let menuRouter = {
          path: router.url,
          name: router.code,
          component: _import(routerProps.path),
          meta: {
            name: router.name,
            cssClass: routerProps.cssClass,
            routerId: router.id,
            parentId: router.parent.id,
            requireAuth: process.env.REQUIRE_AUTH,
            verify: process.env.REQUIRE_AUTH,
            keepAlive: routerProps.keepAlive
          }
        }
        menuRouters.push(menuRouter)
      } else {
        let childRouter = {
          path: router.url,
          name: router.code,
          component: _import(routerProps.path),
          meta: {
            name: router.name,
            cssClass: routerProps.cssClass,
            routerId: router.id,
            parentId: router.parent.id,
            requireAuth: process.env.REQUIRE_AUTH,
            verify: process.env.REQUIRE_AUTH,
            keepAlive: routerProps.keepAlive
          }
        }
        childRouters.push(childRouter)
      }
    }
  }

  return { menuRouters, childRouters }
}

router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    next('/main')
  } else {
    next()
  }
})

router.afterEach((to, from) => {})
