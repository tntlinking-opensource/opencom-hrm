// 巡更巡检
const _import = require('../_import_' + process.env.NODE_ENV)
export default [
   {
    path: '/propInspectTask',
    name: 'propInspectTask',
    component: _import('property/propInspectTask/index'),
    meta: { requireAuth: true, verify: true,  keepAlive: true, title: '任务列表'},
   },
    {
      path: '/taskPop',
      name: 'taskPop',
      component: _import('property/propInspectTask/taskPop'),
      meta: {requireAuth: true, verify: true, title: '任务详情'}
    },
    {
      path: '/linePop',
      name: 'linePop',
      component: _import('property/propInspectTask/linePop'),
      meta: {requireAuth: true, verify: true, title: '巡检路线'}
    },
    {
      path: '/pointPop',
      name: 'pointPop',
      component: _import('property/propInspectTask/pointPop'),
      meta: {requireAuth: true, verify: true, title: '巡检点详情'}
    },
    {
      path: '/photoUploadPop',
      name: 'photoUploadPop',
      component: _import('property/propInspectTask/photoUploadPop'),
      meta: {requireAuth: true, verify: true, title: '巡检拍照'}
    },
    {
      path: '/facPop',
      name: 'facPop',
      component: _import('property/propInspectTask/linePop'),
      meta: {requireAuth: true, verify: true, title: '巡检设备'}
    },
    {
      path: '/facDetailPop',
      name: 'facDetailPop',
      component: _import('property/propInspectTask/pointPop'),
      meta: {requireAuth: true, verify: true, title: '设备详情'}
    }
]