import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import property from './modules/property'
import fac from './modules/fac'

// 导入模块

const state = {}
const actions = {}
const mutations = {}

Vue.use(Vuex)
export default new Vuex.Store({
  modules: {
    property,
    fac
  },
  state,
  actions,
  mutations,
  getters
})
