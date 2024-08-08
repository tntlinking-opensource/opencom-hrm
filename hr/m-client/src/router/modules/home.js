// 首页 个人信息
const _import = require('../_import_' + process.env.NODE_ENV)

const Layout = _import('layout/index')
const NewHome = _import('layout/home/newIndex')
const requireAuth = process.env.REQUIRE_AUTH

export default [
  {
    path: '/',
    redirect: '/home',
    component: Layout,
    children: [
      {
        path: '/home',
        name: 'home',
        component: NewHome,
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '首页' }
      },
      {
        path: '/mytask',
        name: 'mytask',
        component: _import('layout/mytask/index'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '我的待办' }
      },
      {
        path: '/myapproved',
        name: 'myapproved',
        component: _import('layout/myapproved/index'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '我已审批' }
      },
      {
        path: '/myapplied',
        name: 'myapplied',
        component: _import('layout/myapplied/index'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '我的申请' }
      },
      {
        path: '/user',
        name: 'user',
        component: _import('mine/myInfo/index'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '我的' }
      }
    ]
  },
  {
    path: '/settings',
    name: 'settings',
    component: _import('mine/settings/index'),
    meta: { requireAuth: requireAuth, verify: requireAuth, title: '设置' }
  }
]
