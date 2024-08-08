<template>
  <van-overlay :show="showDialog" @click="showDialog = false">
    <div class="wrapper">
      <div class="block" @click.stop>
        <van-nav-bar
          title="信息填写流程"
          @click-left="onClickLeft"
          @click-right="onClickRight"
        >
          <template #left>
            <van-icon name="arrow-left" color="#000000" />
          </template>
          <template #right>
            <van-icon name="weapp-nav" color="#000000" /> </template
        ></van-nav-bar>

        <van-field readonly label="基本信息">
          <template #left-icon
            ><van-icon name="user-o" class="left-icon" color="#0d8ddb" />
          </template>
          <template #right-icon v-if="iconType.base"
            ><van-icon name="checked" color="#0d8ddb" />
          </template>
          <template #right-icon v-else><van-icon name="circle" /> </template>
        </van-field>
        <van-field readonly label="银行卡号">
          <template #left-icon
            ><van-icon name="user-o" class="left-icon" color="#0d8ddb" />
          </template>
          <template #right-icon v-if="iconType.bank"
            ><van-icon name="checked" color="#0d8ddb" />
          </template>
          <template #right-icon v-else><van-icon name="circle" /> </template>
        </van-field>
        <van-field readonly label="家庭信息">
          <template #left-icon
            ><van-icon class="left-icon" name="award-o" color="#0d8ddb" />
          </template>
          <template #right-icon v-if="iconType.family"
            ><van-icon name="checked" color="#0d8ddb" />
          </template>
          <template #right-icon v-else><van-icon name="circle" /> </template>
        </van-field>

        <van-field readonly label="紧急联系人">
          <template #left-icon
            ><van-icon name="calendar-o" color="#0d8ddb" class="left-icon" />
          </template>
          <template #right-icon v-if="iconType.urgency"
            ><van-icon name="checked" color="#0d8ddb" />
          </template>
          <template #right-icon v-else><van-icon name="circle" /> </template>
        </van-field>

        <van-field readonly label="附件上传">
          <template #left-icon
            ><van-icon class="left-icon" name="todo-list-o" color="#0d8ddb" />
          </template>
          <template #right-icon v-if="iconType.attachment"
            ><van-icon name="checked" color="#0d8ddb" />
          </template>
          <template #right-icon v-else><van-icon name="circle" /> </template>
        </van-field>
      </div>
    </div>
  </van-overlay>
</template>
<script>
export default {
  props: {
    iconType: {
      type: Object,
      default: {
        base: false,
        bank: false,
        attachment: false,
        urgency: false,
        family: false
      }
    }
  },
  data() {
    return {
      showDialog: false
    };
  },
  methods: {
    init() {
      this.showDialog = true;
    },
    onClickLeft() {
      this.showDialog = false;
      this.$emit("onClickLeft");
    },
    onClickRight() {
      this.showDialog = false;
    }
  }
};
</script>
<style scoped lang="scss">
.wrapper {
  height: 100%;
  width: 100%;
}

.block {
  width: 100%;
  background-color: #fff;
  .left-icon {
    margin-right: 5px;
  }
}
</style>