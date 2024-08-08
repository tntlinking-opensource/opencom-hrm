<template>
  <div class="entrySuccess">
    <van-nav-bar title="入职信息填写" />
    <div class="container">
      <van-icon name="checked" color="#6699cc" size="160" />
      <span>提交成功,感谢填写!</span>
    </div>
    <van-button round block @click="next" class="back-button"
      >查看入职资料</van-button
    >
    <van-button round block type="info" @click="back" class="back-button"
      >返回首页</van-button
    >
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    back() {
      this.$router.push({
        path: "/main"
      });
    },
    next() {
      this.$router.push({
        path: "/source"
      });
    }
  }
};
</script>

<style scoped lang="scss">
.entrySuccess {
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
