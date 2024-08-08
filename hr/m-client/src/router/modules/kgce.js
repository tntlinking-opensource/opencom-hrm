const _import = require('../_import_' + process.env.NODE_ENV)
const requireAuth = process.env.REQUIRE_AUTH
export default [
    {
        path: '/meterReadingInput',
        name: 'meterReadingInput',
        component: _import('kgce/meterReading/meterReadingInput'),
        meta: { requireAuth: requireAuth, verify: requireAuth, keepAlive: true, title: '抄表' },
    },
    {
        path: '/meterViewPage',
        name: 'meterViewPage',
        component: _import('kgce/meterReading/meterViewPage'),
        meta: { requireAuth: requireAuth, verify: requireAuth, keepAlive: true, title: '查看页面' },
    },
]
