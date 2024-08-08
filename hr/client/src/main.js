// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import '@/icons'
// import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import './public-path'

// import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css'
import './assets/element-variables.scss'
import * as filters from './utils/filters'

import NumberInput from './components/NumberInput'
Vue.use(NumberInput)

import './utils/global'
import VueCropper from 'vue-cropper'
Vue.use(VueCropper)

Vue.use(ELEMENT, {
  size: store.getters.settings.size,
})
Vue.config.productionTip = false

/* 注入全局filter*/
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

// 报表
import i18n from '@/views/report/lang'
Vue.use(window['vue-codegenerator'], {env: process.env})

let vm = null


function render(props = {}) {
  const { name, container } = props;
  
  // 微应用模式下，使用微应用的名称作为路由base
  router.history.base = name ? `/${name}${router.history.base}` : `${router.history.base}`

  vm = new Vue({
    el: container ? container.querySelector('#app') : '#app',
    i18n,
    store,
    router,
    template: '<App/>',
    components: { App }
  })
}

export default vm


// 独立运行时
if (!window.__POWERED_BY_QIANKUN__) {
  render();
}

// 微应用必须暴露三个接口：bootstrap、mount、unmount
export async function bootstrap(props) {
  console.log('[vue] vue app bootstraped');
  console.log("我应该只调用一次才对");
}
export async function mount(props) {
  // 每次进入都会调用
  console.log("我是微应用 a");
  console.log("我应该每次都调用才对");
  console.log('[vue] props from main framework', props);
  render(props);
  
  console.log('client ---------')
  console.log(vm);
  
}
export async function unmount(props) {
  vm.$destroy();
  vm.$el.innerHTML = '';
  vm = null;
  delete window.Vue
}

