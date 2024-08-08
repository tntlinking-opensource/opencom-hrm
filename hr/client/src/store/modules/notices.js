const state = {
  noticeTotal:0
};

const actions = {
  SetNoticeTotal({ commit }, data) {
    commit('Set_NoticeTotal', data)
  },
};

const mutations = {
  Set_NoticeTotal(state, data){
    state.noticeTotal = data;
  }
};

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
