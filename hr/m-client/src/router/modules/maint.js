const _import = require('../_import_' + process.env.NODE_ENV)
const requireAuth = process.env.REQUIRE_AUTH
export default [
    {
        path: '/acceptance',
        name: 'acceptance',
        component: _import('maint/acceptance'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '' },
    },
]
