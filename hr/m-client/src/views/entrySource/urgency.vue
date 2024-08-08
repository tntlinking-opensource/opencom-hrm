<template>
  <div class="urgency">
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
      <div class="title">紧急联系人</div>
      <van-form @submit="next" :disabled="formDisabled">
        <div v-for="(item, index) in form" :key="index">
          <div :class="{ addForm: index > 0 }">
            <van-cell
              title=""
              v-if="index > 0"
              @click="formDisabled ? null : deleteData(index, item)"
              style="border-top-left-radius: 5px;border-top-right-radius: 5px;"
            >
              <template #right-icon>
                <van-icon name="delete-o" color="#999999" size="18" />
              </template>
            </van-cell>
            <van-field
              v-model="form[index].name"
              label="联系人姓名"
              required
              :rules="[{ required: true, message: '请填写联系人姓名' }]"
            />
            <van-field
              v-model="form[index].relationship.name"
              label="联系人关系"
              required
              readonly
              @click="formDisabled ? null : changeSkip(index)"
              :rules="[{ required: true, message: '请填写联系人关系' }]"
            />
            <van-popup v-model="showShipPicker" position="bottom">
              <van-picker
                show-toolbar
                value-key="name"
                :columns="shipOptions"
                @confirm="onShipConfirm"
                @cancel="showShipPicker = false"
              />
            </van-popup>
            <van-field
              label="联系人电话"
              v-model="form[index].phoneNumber"
              required
              :rules="[
                { required: true, message: '请选择联系人电话' },
                { pattern: phoneNumberPattern, message: '请输入正确的电话号' }
              ]"
            />
            <van-field
              label="联系地址"
              v-model="form[index].address"
              required
              :rules="[{ required: true, message: '请输入联系地址' }]"
            />
          </div>
        </div>

        <div style="padding:80px 0 15px 0">
          <van-button
            round
            :loading="disabled ? true : false"
            block
            type="info"
            native-type="submit"
            :disabled="disabled"
            >下一步</van-button
          >
        </div>
      </van-form>
      <van-button
        round
        block
        @click="addData"
        style="position: relative;top: -120px;"
        :disabled="formDisabled"
        >新增</van-button
      >
    </div>
    <processDialog
      ref="processDialog"
      @onClickLeft="onClickLeft"
      :iconType="iconType"
    ></processDialog>
  </div>
</template>
<script>
import processDialog from "./components/processDialog";
import { getTsmInductionEnhanceBasicById } from "@/api/induction/tsmInductionBasic.js";
import {
  saveTsmEmergencyContact,
  deleteTsmEmergencyContact
} from "@/api/induction/tsmEmergencyContact.js";
import { getDictTypeById } from "@/api/sys/dictType";
import { Toast } from "vant";
import { Dialog } from "vant";
export default {
  data() {
    return {
      iconType: {
        base: true,
        bank: true,
        attachment: false,
        urgency: false,
        family: true
      },
      disabled: false,
      formDisabled: false,
      form: [
        {
          name: "",
          relationship: { id: "", name: "" },
          phoneNumber: "",
          address: ""
        }
      ],
      inductionId: "",
      shipOptions: [],
      skipIndex: 0,
      showShipPicker: false,
      phoneNumberPattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    };
  },
  components: { processDialog },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
    }
    this.getFormData();
    this.getShipOptions();
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
    //获取表格表单数据
    getFormData() {
      this.disabled = true;
      getTsmInductionEnhanceBasicById(this.inductionId).then(res => {
        if (res.code === "100") {
          this.form = res.data.tsmEmergencyContactList;
          if (this.form.length === 0) {
            this.addData();
          }
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
    getShipOptions() {
      getDictTypeById("1529905266810732545").then(res => {
        this.shipOptions = res.data.dictItemList;
      });
    },
    changeSkip(index) {
      this.skipIndex = index;
      this.showShipPicker = true;
    },
    onShipConfirm(val) {
      this.form[this.skipIndex].relationship = val;
      this.showShipPicker = false;
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
          path: "/attachment",
          query: { inductionId: this.inductionId }
        });
      } else {
        this.disabled = true;
        this.form.map(item => {
          item.inductionId = this.inductionId;
          return item;
        });
        saveTsmEmergencyContact(this.form).then(res => {
          if (res && res.code === "100") {
            this.iconType.urgency = true;
            Toast.success("紧急联系人保存成功!");
            this.$router.push({
              path: "/attachment",
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
      if (
        item.name ||
        item.relationship.name ||
        item.phoneNumber ||
        item.address
      ) {
        Dialog.confirm({
          message: "已经有填写内容，确认删除？"
        })
          .then(() => {
            if (item["id"]) {
              deleteTsmEmergencyContact(item).then(res => {
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
        name: "",
        relationship: { id: "", name: "" },
        phoneNumber: "",
        address: "",
        inductionId: this.inductionId
      });
    }
  }
};
</script>

<style scoped lang="scss">
.urgency {
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
