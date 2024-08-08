import main from '@/views/main/index'
import base from '@/views/interview/base'
import education from '@/views/interview/education'
import work from '@/views/interview/work'
import personal from '@/views/interview/personal'
import certificate from '@/views/interview/certificate'
import success from '@/views/interview/success'
import entryBase from '@/views/entrySource/entryBase'
import bank from '@/views/entrySource/bank'
import family from '@/views/entrySource/family'
import urgency from '@/views/entrySource/urgency'
import attachment from '@/views/entrySource/attachment'
import entrySuccess from '@/views/entrySource/entrySuccess'
import source from '@/views/entry/source'
import { getAppRoot } from '@/utils/auth'
Vue.use(VueRouter)

// 微应用时，appKey作为路由前缀
const root = getAppRoot()

const router = new VueRouter({
  routes: [
    {
      path: '/main',
      name: 'main',
      component: main,
      meta: {  }
    },
    {
      path: '/base',
      name: 'base',
      component: base,
      meta: {  }
    },
    {
      path: '/education',
      name: 'education',
      component: education,
      meta: {  }
    },
    {
      path: '/work',
      name: 'work',
      component: work,
      meta: {  }
    },
    {
      path: '/personal',
      name: 'personal',
      component: personal,
      meta: {  }
    },
    {
      path: '/certificate',
      name: 'certificate',
      component: certificate,
      meta: {  }
    },
    {
      path: '/success',
      name: 'success',
      component: success,
      meta: {  }
    },
    {
      path: '/entryBase',
      name: 'entryBase',
      component: entryBase,
      meta: {  }
    },
    {
      path: '/bank',
      name: 'bank',
      component: bank,
      meta: {  }
    },
    {
      path: '/family',
      name: 'family',
      component: family,
      meta: {  }
    },
    {
      path: '/urgency',
      name: 'urgency',
      component: urgency,
      meta: {  }
    },
    {
      path: '/attachment',
      name: 'attachment',
      component: attachment,
      meta: {  }
    },
    {
      path: '/entrySuccess',
      name: 'entrySuccess',
      component: entrySuccess,
      meta: {  }
    },
    {
      path: '/source',
      name: 'source',
      component: source,
      meta: {  }
    },
    
  ]
})

export default router
