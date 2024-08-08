<template>
  <div class="success">
    <van-nav-bar title="面试信息填写" />
    <div class="container">
      <van-icon name="checked" color="#6699cc" size="160" />
      <span>提交成功,感谢填写!</span>
    </div>
    <van-button round block type="info" @click="back" class="back-button"
      >返回首页</van-button
    >
  </div>
</template>
<script>
export default {
  data() {
    return {
      inductionId: ""
    };
  },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
    }
  },
  methods: {
    back() {
      this.$router.push({
        path: "/main",
        query: { inductionId: this.inductionId }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.success {
  height: 100vh;
  .container {
    width: 90%;
    margin: 5% 5%;
    height: 60%;
    background: #ffffff;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-evenly;
  }
  .back-button {
    width: 90%;
    margin: 15px 5%;
  }
}
</style>
