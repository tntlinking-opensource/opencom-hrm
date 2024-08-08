// 设备维保
const _import = require('../_import_' + process.env.NODE_ENV)
const requireAuth = process.env.REQUIRE_AUTH
export default [
    {
        path: '/assetManagement',
        name: 'assetManagement',
        component: _import('assetsManage/assetManagement/index'),
        meta: { requireAuth: requireAuth, verify: requireAuth, keepAlive: true, title: '资产管理' },
    },
    {
        path: '/parkArchives',
        name: 'parkArchives',
        component: _import('assetsManage/assetManagement/parkArchives'),
        meta: { requireAuth: requireAuth, verify: requireAuth, keepAlive: true, title: '园区档案' }
    },
    {
        path: '/roomDetails',
        name: 'roomDetails',
        component: _import('assetsManage/assetManagement/roomDetails'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '房间详情' }
    },
    {
        path: '/otherAssetFiles',
        name: 'otherAssetFiles',
        component: _import('assetsManage/assetManagement/otherAssetFiles'),
        meta: { requireAuth: requireAuth, verify: requireAuth, keepAlive: true, title: '其他资产档案' }
    },
    {
        path: '/genReDetails',
        name: 'genReDetails',
        component: _import('assetsManage/assetManagement/genReDetails'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '通用资源详情' }
    },
]
