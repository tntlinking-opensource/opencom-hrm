<template>
  <div class="entryBase">
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
      <div class="title">基本信息</div>
      <van-form @submit="next" :disabled="formDisabled">
        <van-field
          v-model="form.name"
          label="姓名"
          required
          :rules="[{ required: true, message: '请填写面试者姓名' }]"
        />
        <van-field
          clickable
          label="性别"
          v-model="form.gender.name"
          required
          readonly
          @click="formDisabled ? null : (showGenderPicker = true)"
          :rules="[{ required: true, message: '请选择性别' }]"
        />
        <van-popup v-model="showGenderPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="genderColumns"
            @confirm="onGenderConfirm"
            @cancel="showGenderPicker = false"
          />
        </van-popup>
        <van-field
          clickable
          v-model="form.ethnicity.name"
          label="民族"
          required
          readonly
          @click="formDisabled ? null : (showNationPicker = true)"
          :rules="[{ required: true, message: '请选择民族' }]"
        />
        <van-popup v-model="showNationPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="nationColumns"
            @confirm="onNationConfirm"
            @cancel="showNationPicker = false"
          />
        </van-popup>
        <van-field
          v-model="form.nationality"
          label="国籍或地区"
          required
          :rules="[{ required: true, message: '请输入有效的国籍或地区' }]"
        />
        <van-field
          v-model="form.idNumber"
          label="身份证号码"
          required
          :rules="[
            { required: true, message: '请输入有效的身份证号码' },
            { pattern: idNumberPattern, message: '请输入正确的身份证号' }
          ]"
        />
        <van-field
          clickable
          @click="formDisabled ? null : changeIdCardExpiryDate()"
          v-model="form.idCardExpiryDate"
          label="证件到期日期"
          required
          readonly
          :rules="[{ required: true, message: '请选择证件到期日期' }]"
        />
        <van-popup v-model="showIdCardExpiryDatePicker" position="bottom">
          <van-datetime-picker
            type="date"
            :min-date="minDate"
            :max-date="maxDate"
            v-model="idCardExpiryDateCurrentDate"
            @confirm="onIdCardExpiryDateConfirm"
            @cancel="showIdCardExpiryDatePicker = false"
          />
        </van-popup>
        <van-field
          clickable
          v-model="form.politicalStatus.name"
          label="政治面貌"
          required
          readonly
          @click="formDisabled ? null : (showPoliticalStatusPicker = true)"
          :rules="[{ required: true, message: '请选择政治面貌' }]"
        />
        <van-popup v-model="showPoliticalStatusPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="politicalStatusColumns"
            @confirm="onPoliticalStatusConfirm"
            @cancel="showPoliticalStatusPicker = false"
          />
        </van-popup>
        <van-field
          clickable
          @click="formDisabled ? null : changeWorkStartDate()"
          v-model="form.workStartDate"
          readonly
          label="开始工作时间"
        />
        <van-popup v-model="showWorkStartDatePicker" position="bottom">
          <van-datetime-picker
            type="date"
            :min-date="minDate"
            :max-date="maxDate"
            v-model="workStartDateCurrentDate"
            @confirm="onWorkStartDatefirm"
            @cancel="showWorkStartDatePicker = false"
          />
        </van-popup>
        <van-field
          clickable
          readonly
          v-model="form.healthStatus.name"
          @click="formDisabled ? null : (showHealthStatusPicker = true)"
          label="健康状况"
          required
          :rules="[{ required: true, message: '请选择健康状况' }]"
        />
        <van-popup v-model="showHealthStatusPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="healthStatusOptions"
            @confirm="onHealthStatusConfirm"
            @cancel="showHealthStatusPicker = false"
          />
        </van-popup>

        <van-field
          clickable
          @click="formDisabled ? null : (showEducationBackgroundPicker = true)"
          style="margin-top:20px;"
          v-model="form.educationBackground.name"
          label="最高学历"
          readonly
          required
          :rules="[{ required: true, message: '请选择最高学历' }]"
        />
        <van-popup v-model="showEducationBackgroundPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="educationBackgroundOptions"
            @confirm="onEducationBackgroundConfirm"
            @cancel="showEducationBackgroundPicker = false"
          />
        </van-popup>

        <van-field
          clickable
          @click="formDisabled ? null : (showAcademicPicker = true)"
          v-model="form.highestDegree.name"
          readonly
          label="最高学位"
        />
        <van-popup v-model="showAcademicPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="academicOptions"
            @confirm="onAcademicConfirm"
            @cancel="showAcademicPicker = false"
          />
        </van-popup>
        <van-field
          style="margin-top:20px;"
          v-model="form.householdRegisterLocation"
          label="户口所在地"
          required
          :rules="[{ required: true, message: '请输入户口所在地' }]"
        />
        <van-field
          v-model="form.householdRegisterType.name"
          label="户口性质"
          clickable
          @click="
            formDisabled ? null : (showHouseholdRegisterTypePicker = true)
          "
          readonly
          required
          :rules="[{ required: true, message: '请选择户口性质' }]"
        />
        <van-popup v-model="showHouseholdRegisterTypePicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="registerOptions"
            @confirm="onHouseholdRegisterTypeConfirm"
            @cancel="showHouseholdRegisterTypePicker = false"
          />
        </van-popup>
        <van-field
          clickable
          v-model="form.maritalStatus.name"
          label="婚姻状况"
          readonly
          required
          @click="showMarriageStatusPicker = true"
          :rules="[{ required: true, message: '请选择婚姻状况' }]"
        />
        <van-popup v-model="showMarriageStatusPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="marriageStatusColumns"
            @confirm="onMarriageStatusConfirm"
            @cancel="showMarriageStatusPicker = false"
          />
        </van-popup>
        <van-field
          v-model="form.residenceAddress"
          label="居住地址"
          required
          :rules="[{ required: true, message: '请输入详情的居住地址' }]"
        />
        <van-field
          v-model="form.phoneNumber"
          label="手机号码"
          required
          :rules="[
            { required: true, message: '请输入正确格式的手机号码' },
            { pattern: phoneNumberPattern, message: '请输入正确的手机号' }
          ]"
        />
        <van-field v-model="form.accumulationFundId" label="公积金账号" />

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
import { getDictTypeById } from "@/api/sys/dictType";
import {
  getTsmInductionEnhanceBasicById,
  saveEnhanceBasic
} from "@/api/induction/tsmInductionBasic.js";
import processDialog from "./components/processDialog";
import { Toast } from "vant";
import { changeFormatter, changeUTC } from "./../interview/components/utils";
export default {
  data() {
    return {
      iconType: {
        base: false,
        bank: false,
        attachment: false,
        urgency: false,
        family: false
      },
      disabled: false,
      formDisabled: false,
      form: {
        name: "",
        gender: {
          name: ""
        },
        ethnicity: {
          name: ""
        },
        nationality: "",
        idNumber: "",

        idCardExpiryDate: "",
        politicalStatus: {
          name: ""
        },
        educationBackground: { name: "", id: "" },
        highestDegree: { name: "", id: "" },
        workStartDate: "",
        healthStatus: { name: "" },
        householdRegisterLocation: "",
        householdRegisterType: { name: "" },
        maritalStatus: { name: "" },
        phoneNumber: "",
        residenceAddress: "",
        accountOwner: "",
        salaryCardId: "",
        bankName: "",
        bankAddress: "",
        bankCity: ""
      },
      genderColumns: [],
      showGenderPicker: false,
      nationColumns: [],
      showNationPicker: false,
      marriageStatusColumns: [],
      showMarriageStatusPicker: false,
      politicalStatusColumns: [],
      showPoliticalStatusPicker: false,
      idNumberPattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
      phoneNumberPattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
      showIdCardExpiryDatePicker: false,
      idCardExpiryDateCurrentDate: null,
      showWorkStartDatePicker: false,
      workStartDateCurrentDate: null,
      showHealthStatusPicker: false,
      // 健康状态选项
      healthStatusOptions: [],
      //最高学历
      educationBackgroundOptions: [],
      showEducationBackgroundPicker: false,
      //最高学位
      academicOptions: [],
      showAcademicPicker: false,
      //户口状态
      registerOptions: [],
      showHouseholdRegisterTypePicker: false,
      inductionId: "",
      minDate: new Date(1970, 0, 1),
      maxDate: new Date(2050, 11, 31)
    };
  },
  components: { processDialog },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
    }
    this.getFormData();
    this.getGenderOptions();
    this.getEthnicityOptions();
    this.getMaritalStatusOptions();
    this.getPoliticalStatusOptions();
    this.getHealthStatusOptionsOptions();
    this.getHighestEducationOptions();
    this.getAcademicOptionsOptions();
    this.getRegisterOptionsOptions();
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
    onGenderConfirm(val) {
      this.form.gender = val ? val : { name: "" };
      this.showGenderPicker = false;
    },
    onNationConfirm(val) {
      this.form.ethnicity = val ? val : { name: "" };
      this.showNationPicker = false;
    },
    onMarriageStatusConfirm(val) {
      this.form.maritalStatus = val ? val : { name: "" };
      this.showMarriageStatusPicker = false;
    },
    onPoliticalStatusConfirm(val) {
      this.form.politicalStatus = val ? val : { name: "" };
      this.showPoliticalStatusPicker = false;
    },
    onHealthStatusConfirm(val) {
      this.form.healthStatus = val ? val : { name: "" };
      this.showHealthStatusPicker = false;
    },
    onEducationBackgroundConfirm(val) {
      this.form.educationBackground = val ? val : { name: "" };
      this.showEducationBackgroundPicker = false;
    },
    onAcademicConfirm(val) {
      this.form.highestDegree = val ? val : { name: "" };
      this.showAcademicPicker = false;
    },
    onHouseholdRegisterTypeConfirm(val) {
      this.form.householdRegisterType = val ? val : { name: "" };
      this.showHouseholdRegisterTypePicker = false;
    },
    changeIdCardExpiryDate() {
      if (this.form.idCardExpiryDate) {
        this.idCardExpiryDateCurrentDate = changeUTC(
          this.form.idCardExpiryDate
        );
      } else {
        this.idCardExpiryDateCurrentDate = null;
      }
      this.showIdCardExpiryDatePicker = true;
    },
    onIdCardExpiryDateConfirm(val) {
      this.form.idCardExpiryDate = changeFormatter(val);
      this.showIdCardExpiryDatePicker = false;
    },
    changeWorkStartDate() {
      if (this.form.workStartDate) {
        this.workStartDateCurrentDate = changeUTC(this.form.workStartDate);
      } else {
        this.workStartDateCurrentDate = null;
      }
      this.showWorkStartDatePicker = true;
    },
    onWorkStartDatefirm(val) {
      this.form.workStartDate = changeFormatter(val);
      this.showWorkStartDatePicker = false;
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
          path: "/bank",
          query: { inductionId: this.inductionId }
        });
      } else {
        this.disabled = true;
        saveEnhanceBasic(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("基本信息保存成功!");
            this.iconType.base = true;
            this.$router.push({
              path: "/bank",
              query: { inductionId: this.inductionId }
            });
          } else {
            Toast.fail("网络错误请重试！");
          }
          this.disabled = false;
        });
      }
    },
    getGenderOptions() {
      getDictTypeById("1495769794452094977").then(res => {
        this.genderColumns = res.data.dictItemList;
      });
    },
    getEthnicityOptions() {
      getDictTypeById("1495769794452094981").then(res => {
        this.nationColumns = res.data.dictItemList;
      });
    },
    getMaritalStatusOptions() {
      getDictTypeById("1495769794452094986").then(res => {
        this.marriageStatusColumns = res.data.dictItemList;
      });
    },

    getPoliticalStatusOptions() {
      getDictTypeById("1495769794452094996").then(res => {
        this.politicalStatusColumns = res.data.dictItemList;
      });
    },
    getHealthStatusOptionsOptions() {
      getDictTypeById("1510239419013955733").then(res => {
        this.healthStatusOptions = res.data.dictItemList;
      });
    },
    getHighestEducationOptions() {
      getDictTypeById("1496815670528286753").then(res => {
        this.educationBackgroundOptions = res.data.dictItemList;
      });
    },
    getAcademicOptionsOptions() {
      getDictTypeById("1510239419013955718").then(res => {
        this.academicOptions = res.data.dictItemList;
      });
    },
    getRegisterOptionsOptions() {
      getDictTypeById("1510239419013955729").then(res => {
        this.registerOptions = res.data.dictItemList;
      });
    }
  }
};
</script>

<style scoped lang="scss">
.entryBase {
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
