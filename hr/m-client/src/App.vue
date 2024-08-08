<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="isRouterAlive"></router-view>
    </keep-alive>
  </div>
</template>

<script>
import { getLocalCurrentUser, setLocalToken } from "@/utils/auth";
// 传给报表组件的后台服务地址
window.REPORT_SERVER_URL = process.env.REPORT_SERVER_URL;
export default {
  name: "App",
  provide() {
    return {
      reload: this.reload
    };
  },
  data() {
    return {
      isRouterAlive: true
    };
  },
  methods: {
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    }
  },

  beforeCreate() {
    let token =
      "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE2ODQyODk0ODEsInN1YiI6IntcInRlbmFudF9pZFwiOlwibWFzdGVyXCIsXCJob3N0XCI6XCIwOjA6MDowOjA6MDowOjFcIixcInNlc3Npb25JZFwiOlwiYWI0MzY2NjctMjBiZC00Nzk3LWFkNzktYWI4OTc3ZmUyMDFmXCIsXCJ1c2VySWRcIjpcIjE0NzE5NTQzMzgyMzM1MDc4OTJcIn0ifQ.Tl3ADBhLOWB0I2zgqI0iXvG_ikDd1oPv0fETCAKYeAQ";
    if (this.$route.query && this.$route.query["X-Token"]) {
      token = this.$route.query["X-Token"];
    }
    // 当前登录用户放进全局参数中
    setLocalToken(token);
    window.currentUser = getLocalCurrentUser();
  }
};
</script>
<style lang="scss">
</style>
