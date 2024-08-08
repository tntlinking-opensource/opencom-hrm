<template>
  <div class="certificate">
    <van-nav-bar
      title="面试信息填写"
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #left>
        <van-icon name="arrow-left" color="#000000" />
      </template>
      <template #right> <van-icon name="weapp-nav" color="#000000" /> </template
    ></van-nav-bar>
    <div class="container">
      <div class="title">资格证书</div>
      <van-form :disabled="formDisabled">
        <div v-for="(item, index) in form" :key="index">
          <div :class="{ addForm: index > 0 }">
            <van-cell
              title=""
              v-if="index > 0"
              style="border-top-left-radius: 5px;border-top-right-radius: 5px;"
            >
              <template #right-icon>
                <van-icon
                  name="delete-o"
                  color="#999999"
                  size="18"
                  @click="formDisabled ? null : deleteData(index, item)"
                />
              </template>
            </van-cell>
            <van-field v-model="form[index].certificateName" label="证书名称" />
            <van-field v-model="form[index].certificateNum" label="证书编号" />
            <van-field
              clickable
              label="发证日期"
              readonly
              v-model="form[index].certificateDate"
              @click="formDisabled ? null : changeCertificateDateIndex(index)"
            />
          </div>
        </div>
        <van-popup v-model="showCertificateDatePicker" position="bottom">
          <van-datetime-picker
            type="date"
            v-model="certificateDate"
            :min-date="minDate"
            :max-date="maxDate"
            @confirm="onCertificateDateConfirm"
            @cancel="showCertificateDatePicker = false"
          />
        </van-popup>
        <div style="padding:15px 0 ">
          <van-button round block @click="addData" :disabled="formDisabled"
            >新增</van-button
          >
          <van-button
            round
            block
            type="info"
            native-type="submit"
            @click="next"
            :disabled="disabled"
            style="margin:15px 0;"
            >提交</van-button
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
import { getInfoCompletionBasicById } from "@/api/induction/tsmInductionBasic";
import { saveInductionQualification } from "@/api/induction/inductApp";
import processDialog from "./components/processDialog";
import { changeFormatter, changeUTC } from "./components/utils";
import { Toast } from "vant";
import { Dialog } from "vant";
import { deleteTsmQualification } from "@/api/induction/tsmQualification.js";
export default {
  data() {
    return {
      iconType: {
        base: true,
        certificate: false,
        personal: true,
        work: true,
        education: true
      },
      disabled: false,
      formDisabled: false,
      form: [
        {
          certificateName: "", // 证书名称
          certificateNum: "", // 证书编号
          certificateDate: "" // 发证时间
        }
      ],

      showCertificateDatePicker: false,
      certificateDateIndex: 0,
      certificateDate: null,

      inductionId: "",
      minDate: new Date(1970, 0, 1),
      maxDate: new Date(2050, 11, 31)
    };
  },
  components: {
    processDialog
  },
  mounted() {
    if (this.$route.order && this.$route.query["order"]) {
      this.order = this.$route.query["order"];
    }
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
      this.getFormData();
    }
    if (this.order === "2") {
      this.iconType = {
        base: true,
        certificate: true,
        personal: true,
        work: true,
        education: true
      };
    }
  },
  watch: {
    $route(val) {
      if (val.query && val.query["inductionId"]) {
        this.inductionId = val.query["inductionId"];
        this.getFormData();
      }
    }
  },
  methods: {
    getFormData() {
      this.disabled = true;
      getInfoCompletionBasicById(this.inductionId).then(res => {
        if (res.code === "100") {
          this.form = res.data.tsmQualificationList;
          if (this.form.length === 0) {
            this.addData();
          }
          if (res.data.dataValidation && res.data.dataValidation === "1") {
            this.formDisabled = true;
          }
        }
        this.disabled = false;
      });
    },
    changeCertificateDateIndex(index) {
      this.certificateDateIndex = index;
      if (this.form[index].certificateDate) {
        this.certificateDate = changeUTC(this.form[index].certificateDate);
      } else {
        this.certificateDate = null;
      }
      this.showCertificateDatePicker = true;
    },
    onCertificateDateConfirm(val) {
      this.form[this.certificateDateIndex].certificateDate = changeFormatter(
        val
      );
      this.showCertificateDatePicker = false;
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
          path: "/success",
          query: { inductionId: this.inductionId }
        });
      } else {
        this.disabled = true;
        this.form.map(item => {
          item.inductionId = this.inductionId;
          return item;
        });
        saveInductionQualification(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("资格证书保存成功!");
            this.iconType.certificate = true;
            this.$router.push({
              path: "/success",
              query: { inductionId: this.inductionId }
            });
          } else {
            Toast.fail("网络错误请重试！");
          }
          this.disabled = false;
        });
      }
    },
    deleteData(index, item) {
      if (item.certificateName || item.certificateNum || item.certificateDate) {
        Dialog.confirm({
          message: "已经有填写内容，确认删除？"
        })
          .then(() => {
            if (item["id"]) {
              deleteTsmQualification(item).then(res => {
                if (res && res.code === "100") {
                  Toast.success("该条数据已删除!");
                  this.getFormData();
                } else {
                  Toast.fail("网络错误请重试！");
                }
              });
            } else {
              this.form.splice(index, 1);
            }
          })
          .catch(() => {});
      } else {
        this.form.splice(index, 1);
      }
    },
    addData() {
      this.form.push({
        certificateName: "", // 证书名称
        certificateNum: "", // 证书编号
        certificateDate: "" // 发证时间
      });
    }
  }
};
</script>

<style scoped lang="scss">
.certificate {
  height: 100vh;
  .container {
    margin: 2% 5%;
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
    .addForm {
      margin-top: 15px;
    }
  }
}
</style>
