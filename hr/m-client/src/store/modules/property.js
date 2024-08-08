const state = {
  propInspectTask: {},
  propInspectLine: {},
  propInspectPoint: {},
  propInspectTaskSite: {},
  setPropertyScrollTop: 0,
  propInspectType: null,
}

const mutations = {
  SET_PROPINSPECETASK: (state, data) => {
    state.propInspectTask = data
  },
  SET_PROPINSPECELINE: (state, data) => {
    state.propInspectLine = data
  },
  SET_PROPINSPECEPOINT:(state, data) => {
    state.propInspectPoint = data
  },
  SET_PROPINSPECETASKSITE: (state, data) => {
    state.propInspectTaskSite = data
  },
  SET_PROPERTYSCROLLTOP: (state, data) => {
    state.setPropertyScrollTop = data
  },
  SET_PROPINSPECTTYPE:(state, data) => {
    state.propInspectType = data
  },
}

const actions = {
  setPropInspectTask({ commit }, data) {
    commit('SET_PROPINSPECETASK', data)
  },
  setPropInspectLine({ commit }, data) {
    commit('SET_PROPINSPECELINE', data)
  },
  setPropInspectPoint({ commit }, data) {
    commit('SET_PROPINSPECEPOINT', data)
  },
  setpropInspectTaskSite({ commit }, data) {
    commit('SET_PROPINSPECETASKSITE', data)
  },
  setPropertyScrollTop({ commit }, data) {
    commit('SET_PROPERTYSCROLLTOP', data)
  },
  setPropInspectType({ commit }, data) {
    commit('SET_PROPINSPECTTYPE', data)
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
