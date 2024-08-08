const _import = require('../_import_' + process.env.NODE_ENV)

  //合同监管
export default [
    {
        path: '/jfDeail',
        name: 'jfDeail',
        props:(route)=>({obj: route.params.item}),
        component: _import('contract/contractTabs/jfDeail'),
        meta: { requireAuth: false, verify: false,  keepAlive: true, title: '合同纠纷详情'}
    },
    {
      path:'/showWord',
      component: _import('contract/contractTabs/preview/wordPreview'),
      meta: { requireAuth: false, verify: false,  keepAlive: true, title: '预览word'}
    }
]