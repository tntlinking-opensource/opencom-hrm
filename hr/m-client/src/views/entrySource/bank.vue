<template>
  <div class="bank">
    <van-nav-bar
      title="入职信息填写"
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #left>
        <van-icon name="arrow-left" color="#000000" />
      </template>
      <template #right> <van-icon name="weapp-nav" color="#000000" /> </template
    ></van-nav-bar>
    <div class="container">
      <div class="title">银行卡号</div>
      <van-form @submit="next" :disabled="formDisabled">
        <van-field
          v-model="form.accountOwner"
          label="持卡人姓名"
          required
          :rules="[
            {
              required: true,
              message: '请填写持卡人姓名'
            }
          ]"
        />

        <van-field
          v-model="form.cardId"
          label="银行卡号"
          required
          :rules="[
            { required: true, message: '请输入银行卡号' },
            { pattern: cardPattern, message: '请输入正确的银行卡号' }
          ]"
        />
        <van-field
          v-model="form.bankName"
          label="银行名称"
          required
          :rules="[{ required: true, message: '请输入银行名称' }]"
        />

        <van-field
          v-model="form.bankAddress"
          label="开户行"
          required
          :rules="[{ required: true, message: '请输入开户行' }]"
        />

        <van-field
          v-model="form.bankCity"
          label="开户城市"
          required
          :rules="[{ required: true, message: '请输入开户城市' }]"
        />

        <div style="padding:16px 0">
          <van-button
            round
            block
            type="info"
            :loading="disabled ? true : false"
            native-type="submit"
            :disabled="disabled"
            >下一步</van-button
          >
        </div>
      </van-form>
    </div>
    <processDialog
      ref="processDialog"
      @onClickLeft="onClickLeft"
      :iconType="iconType"
    ></processDialog>
  </div>
</template>
<script>
  import {getTsmInductionEnhanceBasicById, saveEnhanceBasic} from "@/api/induction/tsmInductionBasic.js";
  import processDialog from "./components/processDialog";
  import {Toast} from "vant";

  import getBankcardinfo from "bankcardinfo";

  export default {
  data() {
    return {
      iconType: {
        base: true,
        bank: false,
        attachment: false,
        urgency: false,
        family: false
      },
      disabled: false,
      form: {
        accountOwner: "",
        salaryCardId: "",
        bankName: "",
        bankAddress: "",
        bankCity: ""
      },
      inductionId: "",
      formDisabled: false,
      cardPattern: /^([1-9]{1})(\d{15}|\d{16}|\d{18})$/
    };
  },
  components: { processDialog },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
    }
    this.getFormData();
  },
  watch: {
    "form.cardId"(val) {
      this.getBank(val);
    }
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      this.disabled = true;
      getTsmInductionEnhanceBasicById(this.inductionId).then(res => {
        if (res.code === "100") {
          this.form = Object.assign(
            this.form,
            JSON.parse(JSON.stringify(res.data))
          );
          if (res.data.dataValidation && res.data.dataValidation === "1") {
            this.formDisabled = true;
            this.iconType = {
              base: true,
              bank: true,
              attachment: true,
              urgency: true,
              family: true
            };
          }
        }
        this.disabled = false;
      });
    },
    getBank(val) {
      getBankcardinfo.getBankBin(val, (err, data) => {
        if (data) {
          this.form.bankName = data.bankName;
        } else {
          this.form.bankName = "";
        }
      });
    },

    onClickLeft() {
      window.history.go(-1);
    },
    onClickRight() {
      this.$refs.processDialog.init();
    },
    next() {
      if (this.formDisabled) {
        this.$router.push({
          path: "/family",
          query: { inductionId: this.inductionId }
        });
      } else {
        this.disabled = true;
        saveEnhanceBasic(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("银行信息保存成功!");
            this.iconType.bank = true;
            this.$router.push({
              path: "/family",
              query: { inductionId: this.inductionId }
            });
          } else {
            Toast.fail("网络错误请重试！");
          }
          this.disabled = false;
        });
      }
    }
  }
};
</script>

<style scoped lang="scss">
.bank {
  height: 100vh;
  .container {
    margin: 2% 5%;
  }
  .title {
    width: 114px;
    height: 42px;
    line-height: 42px;
    background-color: #fff;
    text-align: center;
    color: #0d8ddb;
    border-top-left-radius: 13px;
    border-top-right-radius: 13px;
  }
}
</style>
