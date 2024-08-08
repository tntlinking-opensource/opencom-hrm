<template>
  <div v-if='isLoading'>
    正在加载页面...
  </div>
</template>

<script>
import { getLoginedToken } from '@/api/auth'
import { getAppRoot } from '@/utils/auth'
import { getLocalRouters, setLocalRouters, setLocalDataPermisions, setLocalPersonalTheme } from '@/utils/auth'

import BaseUI from '@/views/components/baseUI'
import { routerTree, handleFamily } from '@/utils/routerTree'
const _import = require('@/router/_import_' + process.env.NODE_ENV)

import sysMixin from './sysMixin'

// 微应用时，appKey作为路由前缀
const root = getAppRoot()

export default {
  extends: BaseUI,
  mixins: [sysMixin],
  data() {
    return {
    }
  },
  // computed: {
  //  // ...Vuex.mapGetters(['settings'])
  // },
  methods: {
    ...Vuex.mapActions({
      changeSetting: 'settings/changeSetting'
    }),
    getUserData() {
      getLoginedToken().then(responseData => {
        if(responseData.code == 100) {
          this.handleUserData(responseData.data)
        } else {
          this.isError = true
          this.loginError = responseData.msg
        }
      })
        .catch(error => {
          this.outputError(error)
        })

    },
    initIndexRouter() {
      let indexRouter = {
        path: `${root ? root : '/'}`,
        name: '/',
        component: _import('home/index'),
        children: [...this.generateChildRouters()]
      }
      // this.$router.addRoutes([indexRouter])
      this.$router.addRoute('/', indexRouter)
    },
    generateChildRouters() {
      let routers = getLocalRouters()
      let routerTreeArr = routerTree(routers)
      if (!routers) {
        return []
      }

      let childRouters = []
      for(let router of routers) {
        if(router.code) {
          let routerProps = JSON.parse(router.properties)
          let childRouter = {
            path: router.url,
            name: router.code,
            component: _import(router.code + '/index'),
            meta: {
              name: router.name,
              cssClass: routerProps.cssClass,
              routerId: router.id,
              parentId: router.parent.id,
              family: handleFamily(routerTreeArr, item => item.id === router.id),
              linkUrl: routerProps.linkUrl,
              requiresAuth: routerProps.meta.requiresAuth,
              nameFullPath: routerProps.nameFullPath
            }
          }
          childRouters.push(childRouter)
        }
      }
      let redirectRouter = {
        path: 'redirect',
        component: _import('redirect/index')
      }
      childRouters.push(redirectRouter)
      return childRouters
    },
    handleUserData(userData) {
      setLocalRouters(userData.routers)
      setLocalDataPermisions(userData.dataPermisions)

      const personalTheme = userData.personalTheme
      personalTheme.theme = JSON.parse(personalTheme.theme)
      setLocalPersonalTheme(personalTheme)
      this.changeTheme(personalTheme.theme)

      // 初始化首页路由
      this.initIndexRouter();

      const routers = userData.routers; // 当前用户的路由
      if(this.$route.query.redirect){   // 判断有无重定向
        const url = this.removeBlock(JSON.stringify( {url:this.$route.query.redirect.substring(1)}));
        if(routers.includes(url)){      // 判断路由是否包含 重定向路径 判断有无权限
          this.$router.push(this.$route.query.redirect);
        }
      }
      this.$router.push(`${root ? root : '/'}`);
    },
    removeBlock (str) {
      if (str) {
        const reg = /^\{/gi;
        const reg2 = /\}$/gi;
        str = str.replace(reg, '');
        str = str.replace(reg2, '');
        return str
      } else {
        return str
      }
    },
    changeTheme(theme) {
      const settings = [
        'theme',
        'headerColor',
        'sidebarColor',
        'backgroundColor',
        'size',
        'style',
        'showTagsView',
        'fixedHeader',
        'showLogo'
      ];
      this.$ELEMENT.size = theme.size;
      settings.forEach(item => {
        this.handleSetting(item, theme[item])
      })
    },
    handleSetting(key, value) {
      this.changeSetting({ key, value });
    },
  },
  created: function() {
    this.getSysSetting()
    this.getUserData()

  }
}
</script>
