import Vue from 'vue'
import App from './App'
import router from './router'
// 引入 vant 组件库
import '@/utils/vant'
import '@/utils/permission'
import './utils/global'
// 微前端改造
let vm = null
function render(props = {}) {
  const { name, container } = props;

  // 微应用模式下，使用微应用的名称作为路由base
  router.history.base = name ? `/${name}${router.history.base}` : `${router.history.base}`

  vm = new Vue({
    el: container ? container.querySelector('#app') : '#app',
    router,
    components: { App },
    template: '<App/>'
  })
}

export default vm

// 独立运行时
if (!window.__POWERED_BY_QIANKUN__) {
  render();
}

