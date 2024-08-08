import { listSysSetingAll } from '@/api/sys/sysSeting.js'
import { setLocalSysSetting } from '@/utils/auth'

let sysMixin = {
  data() {
    return {
      baseApi: process.env.BASE_API,
      sysData: {
        sysName: '',      // 系统名称
        sysAbbrname: '',  // 系统简称
        loginBgcolor: "", // 登录页背景色
        loginLayout: 'simple-center',
        projectLogo: '',  // 项目logo
        loginLogo: '',    // 登录框logo
        sysLogo: '',      // 系统主页logo
        favicon: '',      // 网页顶部 favicon 图标
        loginBg: '',      // 登录页背景图
        loginGraph: ''    // 登录页配图
      },
    }
  },
  methods: {
    getSysSetting() {
      let parmas = {}
      listSysSetingAll(parmas).then(response => {
        if (response.code == 100) {
          let result = response.data[0];
          this.sysData.sysName = result.sysName
          this.sysData.sysAbbrname = result.sysAbbrname
          this.sysData.loginBgcolor = result.loginBgcolor
          this.sysData.loginLayout = result.loginLayout
          this.sysData.projectLogo = `${this.baseApi}/sys/sysSeting/getFile/1`
          this.sysData.loginLogo = `${this.baseApi}/sys/sysSeting/getFile/2`
          this.sysData.sysLogo = `${this.baseApi}/sys/sysSeting/getFile/3`
          this.sysData.favicon = `${this.baseApi}/sys/sysSeting/getFile/4`
          this.sysData.loginBg = `${this.baseApi}/sys/sysSeting/getFile/5`
          this.sysData.loginGraph = `${this.baseApi}/sys/sysSeting/getFile/6`
          setLocalSysSetting(this.sysData)

          this.changeFavicon(this.sysData.favicon)
          this.changeWebTitle(this.sysData.sysAbbrname)
        }
        this.isLoaded = true
      }).catch(error => {
        this.isLoaded = true
      })
    },
    changeFavicon(icon) {
      let link = document.querySelector("link[rel*='Icon']") || document.createElement('link');
      link.type = 'image/x-icon';
      link.rel = 'Shortcut Icon';
      link.href = icon;
      document.getElementsByTagName('head')[0].appendChild(link);
    },
    changeWebTitle(title) {
      document.title = title
    },
  }
}

export default sysMixin
