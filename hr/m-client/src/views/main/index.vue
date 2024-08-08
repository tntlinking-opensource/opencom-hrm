<template>
  <div class="main">
    <van-nav-bar title="入职助手" />
    <div class="company">{{ this.companyName }}</div>
    <div class="card-droup">
      <div class="card-column" @click="interview">
        <div class="card">
          <img src="~@/assets/img/interview.png" class="image" />

          <span class="card-title">面试信息填写</span>
          <van-icon name="checked" class="card-icon" color="#0d8ddb" />
        </div>
      </div>
      <div class="card-column" @click="entrySource">
        <div :class="{ card: true, 'card-disabled': disabled }">
          <img src="~@/assets/img/entry_resource.png" class="image" />

          <span class="card-title">入职资料填写</span>
          <van-icon
            name="warning-o"
            class="card--disabled-icon"
            color="#ccc"
            v-if="disabled"
          />
          <van-icon name="checked" class="card-icon" v-else color="#0d8ddb" />
        </div>
      </div>
      <div class="card-column" @click="entry">
        <div :class="{ card: true, 'card-disabled': disabled }">
          <img src="~@/assets/img/entry.png" class="image" />
          <span class="card-title">入职资料</span>
          <van-icon
            name="warning-o"
            color="#ccc"
            class="card--disabled-icon"
            v-if="disabled"
          />
          <van-icon name="checked" class="card-icon" v-else color="#0d8ddb" />
        </div>
      </div>
    </div>
    <van-popup
      v-model="showPopup"
      :close-on-click-overlay="false"
      style="width: 80%;border-radius: 10px;"
      ><div class="idNumber">
        <div style="font-size:14px">请输入身份证后四位</div>
        <van-field
          v-model="idNumber"
          placeholder="请输入身份证后四位"
          class="idNumber-input"
        />
        <div class="idNumber-button">
          <van-button style="width:50%;height: 40px;" @click="showPopup = false"
            >取消</van-button
          >
          <van-button
            style="width:50%;height: 40px;color:blue;"
            @click="checkIdNumber"
            >确认</van-button
          >
        </div>
      </div>
    </van-popup>
  </div>
</template>
<script>
import { Toast } from "vant";
import { Notify } from "vant";
import { checkIdCardLast } from "@/api/induction/tsmInductionPcOther";
export default {
  data() {
    return {
      disabled: true,
      showPopup: false,
      idNumber: "",
      inductionId: "",
      companyName: "",
      isCheck: false,
      order: ""
    };
  },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
    }
    if (this.$route.query && this.$route.query["companyName"]) {
      this.companyName = this.$route.query["companyName"];
    }
    if (this.$route.query && this.$route.query["order"]) {
      this.order = this.$route.query["order"];
    }
    //说明是第二次扫码
    if (this.order === "2") {
      this.disabled = false;
      this.showPopup = true;
    }
  },
  watch: {
    $route: {
      handler(newVal, oldVal) {
        if (newVal.query && newVal.query["inductionId"]) {
          this.inductionId = newVal.query["inductionId"];
        }
        if (oldVal.query && oldVal.query["inductionId"]) {
          this.inductionId = oldVal.query["inductionId"];
        }
      },
      deep: true
    }
  },
  methods: {
    interview() {
      if (this.order === "2") {
        if (this.isCheck) {
          this.$router.push({
            path: "/base",
            query: {
              inductionId: this.inductionId,
              companyName: this.companyName,
              order: this.order
            }
          });
        } else {
          this.showPopup = true;
        }
      } else {
        this.$router.push({
          path: "/base",
          query: {
            inductionId: this.inductionId,
            companyName: this.companyName
          }
        });
      }
    },
    entrySource() {
      if (this.disabled) {
        Toast.fail("当前阶段无法操作");
      } else {
        if (this.isCheck) {
          this.$router.push({
            path: "/entryBase",
            query: { inductionId: this.inductionId }
          });
        } else {
          this.showPopup = true;
        }
      }
    },
    entry() {
      if (this.disabled) {
        Toast.fail("当前阶段无法操作");
      } else {
        if (this.isCheck) {
          this.$router.push({ path: "/source" });
        } else {
          this.showPopup = true;
        }
      }
    },
    checkIdNumber() {
      if (this.idNumber) {
        checkIdCardLast(this.inductionId, this.idNumber).then(res => {
          if (res.code === "100") {
            this.showPopup = false;
            this.isCheck = true;
          } else {
            Notify({ type: "warning", message: "权限不足" });
          }
        });
      } else {
        Notify({ type: "warning", message: "请输入身份证后四位" });
      }
    }
  }
};
</script>

<style scoped lang="scss">
.main {
  height: 100vh;
  .idNumber {
    height: 120px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .idNumber-button {
      border-top: 1px solid #eeeaea;
      width: 100%;
      display: flex;
      place-content: space-between;
      height: 27px;
    }
  }
  .company {
    height: 5%;
    line-height: 2.5rem;
    margin-left: 5%;
    font-size: 0.9rem;
  }
  .card-droup {
    display: flex;
    height: 60%;
    flex-direction: column;
    align-items: center;
    justify-content: space-evenly;
    .card-column {
      width: 90%;
      height: 30%;
      background: #ffffff;
      box-shadow: 3px 3px 6px #808080;
      border-radius: 0.9rem;
      position: relative;

      .card {
        display: flex;
        align-items: center;
        height: 100%;
        justify-content: space-around;
        .image {
          width: 8rem;
          height: 6rem;
        }
        .card-title {
          text-align: center;
        }
        .card-icon,
        .card--disabled-icon {
          position: relative;
          top: -30%;
          font-size: 1.5rem;
        }
        &.card-disabled {
          opacity: 0.5;
        }
      }
    }
  }
}
</style>
<style  lang="scss">
.main {
  .idNumber {
    .idNumber-input {
      .van-field__control {
        text-align: center;
      }
    }
  }
}
</style>