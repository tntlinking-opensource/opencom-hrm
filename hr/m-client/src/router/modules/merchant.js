// 招商跟进
const _import = require('../_import_' + process.env.NODE_ENV)
const requireAuth = process.env.REQUIRE_AUTH
export default [
    {
        path: '/merchantFollowUp',
        name: 'merchantFollowUp',
        component: _import('merchant/merchantFollowUp/index'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '招商跟进' },
    },
    {
        path: '/customerDetails',
        name: 'customerDetails',
        component: _import('merchant/merchantFollowUp/customerDetails'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '客户详情' }
    },
    {
        path: '/addFollowUpRecord',
        name: 'addFollowUpRecord',
        component: _import('merchant/merchantFollowUp/addFollowUpRecord'),
        meta: { requireAuth: requireAuth, verify: requireAuth, title: '新增跟进' }
    },
]
