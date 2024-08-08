// 设备维保
const _import = require('../_import_' + process.env.NODE_ENV)
export default [
   {
    path: '/facMaintainTask',
    name: 'facMaintainTask',
    component: _import('fac/facMaintainTask/index'),
    meta: { requireAuth: true, verify: true,  keepAlive: true, title: '任务列表'},
   },
    {
      path: '/facTaskPop',
      name: 'facTaskPop',
      component: _import('fac/facMaintainTask/facTaskPop'),
      meta: {requireAuth: true, verify: true, title: '任务详情'}
    },
    {
      path: '/repairOrderPop',
      name: 'repairOrderPop',
      component: _import('fac/facMaintainTask/repairOrderPop'),
      meta: {requireAuth: true, verify: true, title: '设备维保单'}
    }
]