<template>
  <div class="relationData">
    <div class="relationData_header">
      <span class="title">联系信息</span>
      <span class="timer">此信息保存于：{{ relationFormData.updateDate }}</span>
    </div>
    <div class="relationData_content">
      <el-divider class="dc-el-divider_ElDivider">个人联系方式</el-divider>
      <el-form
        ref="relationFormData"
        :model="relationFormData"
        label-width="110px"
        class="dc-el-form_ElEditForm"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item
              prop="phoneNumber"
              label="手机号码"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.phoneNumber"
                :maxLength="50"
                clearable
                :disabled="relationDisabled"
                placeholder="请输入手机号"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="companyEmail"
              label="企业邮箱"
              class="dc-el-form-item_SelectInput"
            >
              <el-input
                v-model="relationFormData.companyEmail"
                :maxLength="50"
                placeholder="请输入企业邮箱"
                clearable
                :disabled="relationDisabled"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="qqNumber"
              label="QQ号"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.qqNumber"
                :maxLength="20"
                placeholder="请输入QQ号"
                clearable
                :disabled="relationDisabled"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="email"
              label="个人邮箱"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.email"
                :maxLength="100"
                placeholder="请输入个人邮箱"
                clearable
                :disabled="relationDisabled"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="wechatId"
              label="微信号"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.wechatId"
                :maxLength="100"
                placeholder="请输入微信号"
                :disabled="relationDisabled"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="otherContactInformation"
              label="其他联系方式"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.otherContactInformation"
                :maxLength="100"
                placeholder="请输入其他联系方式"
                clearable
                :disabled="relationDisabled"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="companyPhoneNumber "
              label="公司座机号"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.companyPhoneNumber"
                :maxLength="100"
                :disabled="relationDisabled"
                placeholder="请输入公司座机号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="companyExtensionNumber"
              label="分机号"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.companyExtensionNumber"
                :maxLength="100"
                placeholder="请输入公司分机号"
                clearable
                :disabled="relationDisabled"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider class="dc-el-divider_ElDivider">紧急联系人</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item
              prop="emergencyContactName"
              label="联系人姓名"
              :rules="[
                {
                  required: true,
                  message: '联系人姓名不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.emergencyContactName"
                :maxLength="100"
                :disabled="relationDisabled"
                placeholder="请输入公司分机号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="emergencyContactRelationship"
              :rules="[
                {
                  required: true,
                  message: '联系人关系不能为空',
                  trigger: 'blur'
                }
              ]"
              label="联系人关系"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="relationFormData.emergencyContactRelationship"
                :style="{ width: '100%' }"
                placeholder="请输入请输入联系人关系"
                value-key="id"
                :disabled="relationDisabled"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in shipOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="emergencyContactPhoneNumber"
              label="联系电话"
              :rules="[
                {
                  required: true,
                  message: '联系人电话不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="relationFormData.emergencyContactPhoneNumber"
                :maxLength="100"
                placeholder="请输入联系电话"
                clearable
                :disabled="relationDisabled"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <el-row style="text-align: center;margin: 10px 0;" v-if="!relationDisabled">
      <el-button icon="el-icon-folder-checked" @click="saveData"
        >保存</el-button
      >
    </el-row>
  </div>
</template>
<script>
import { getDictTypeById } from "@/api/sys/dictType";
import { getContacts, saveContacts } from "@/api/induction/EmployeeInfo.js";
export default {
  props: ["rowData", "relationDisabled"],
  data() {
    return {
      relationFormData: {
        phoneNumber: "",
        qqNumber: "",
        email: "",
        wechatId: "",
        otherContactInformation: "",
        companyEmail: "",
        companyPhoneNumber: "",
        companyExtensionNumber: "",
        emergencyContactRelationship: "",
        emergencyContactPhoneNumber: "",
        emergencyContactAddress: "",
        emergencyContactName: "",
        updateDate: ""
      },
      shipOptions: []
    };
  },
  mounted() {
    this.getFormData();
    this.getShipOptions();
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      getContacts(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.relationFormData = Object.assign(
            this.relationFormData,
            JSON.parse(JSON.stringify(res.data))
          );
        }
      });
    },
    saveData() {
      saveContacts(this.relationFormData).then(res => {
        if (res.code === "100") {
          this.$message({
            type: "success",
            message: "个人信息保存成功"
          });
          this.getFormData();
        } else {
          this.$message({
            type: "warning",
            message: "网络错误请重试"
          });
        }
      });
    },

    getShipOptions() {
      getDictTypeById("1529905266810732545").then(res => {
        this.shipOptions = res.data.dictItemList;
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.relationData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  overflow: auto;
  .relationData_header {
    height: 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .title {
      margin-left: 20px;
      font-size: 16px;
      font-weight: bold;
    }
    .timer {
      margin-right: 50px;
    }
  }
  .relationData_content {
    min-height: 200px;
    height: auto;
    margin: 0 5px 5px 5px;
    border: 1px solid #e6e6e6;
  }
}
</style>