const state = {
    facTask: {},
    facPolicy:{},
    setFacScrollTop: 0,
  }
  
  const mutations = {
    SET_FACTASK: (state, data) => {
      state.facTask = data
    },
    SET_FACPOLICY: (state, data) => {
      state.facPolicy = data
    },
    SET_FACSCROLLTOP: (state, data) => {
      state.setFacScrollTop = data
    },
  }
  
  const actions = {
    setFacTask({ commit }, data) {
      commit('SET_FACTASK', data)
    },
    setFacPolicy({ commit }, data) {
      commit('SET_FACPOLICY', data)
    },
    setFacScrollTop({ commit }, data) {
      commit('SET_FACSCROLLTOP', data)
    },
  }
  
  export default {
    namespaced: true,
    state,
    mutations,
    actions
  }
  