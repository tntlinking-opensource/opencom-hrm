<template>
  <div class="base">
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
      <div class="title">基本信息</div>
      <van-form @submit="next" :disabled="formDisabled">
        <van-field
          v-model="form.name"
          label="姓名"
          required
          :rules="[
            { required: true, message: '请填写面试者姓名' },
            { pattern: namePattern, message: '请输入正确的名字' }
          ]"
        />
        <van-field
          label="性别"
          required
          readonly
          v-model="form.gender.name"
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
          readonly
          required
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
          v-model="form.idNumber"
          label="身份证号码"
          required
          :rules="[
            { required: true, message: '请输入有效的身份证号码' },
            { pattern: idNumberPattern, message: '请输入正确的身份证号' }
          ]"
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
        <van-field
          v-model="form.email"
          label="个人邮箱"
          required
          :rules="[
            { required: true, message: '请输入个人邮箱' },
            { pattern: emailPattern, message: '请输入正确的邮箱' }
          ]"
        />
        <van-field
          v-model="form.nativePlace"
          label="籍贯"
          required
          :rules="[{ required: true, message: '请输入籍贯,例:XX省XX市(县)' }]"
        />
        <van-field
          v-model="form.residenceAddress"
          label="居住地址"
          required
          :rules="[{ required: true, message: '请输入详情的居住地址' }]"
        />
        <van-field
          clickable
          v-model="form.maritalStatus.name"
          label="婚姻状况"
          required
          readonly
          @click="formDisabled ? null : (showMarriageStatusPicker = true)"
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
          clickable
          v-if="form.gender.value === '0'"
          v-model="form.childbirthStatus.name"
          label="生育状况"
          required
          readonly
          @click="formDisabled ? null : (showFertilizerStatusPicker = true)"
          :rules="[{ required: true, message: '请选择生育状况' }]"
        />
        <van-popup v-model="showFertilizerStatusPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="fertilizerStatusColumns"
            @confirm="onFertilizerStatusConfirm"
            @cancel="showFertilizerStatusPicker = false"
          />
        </van-popup>
        <van-field
          clickable
          v-model="form.politicalStatus.name"
          label="政治面貌"
          readonly
          required
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
          style="margin-top:20px"
          clickable
          readonly
          v-model="form.jobPosition.postName"
          label="招聘岗位"
          required
          @click="formDisabled ? null : (showRecruitmentNeedsPicker = true)"
          :rules="[{ required: true, message: '请选择招聘岗位' }]"
        />
        <van-popup v-model="showRecruitmentNeedsPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="postName"
            :columns="recruitmentNeedsColumns"
            @confirm="onRecruitmentNeedsConfirm"
            @cancel="showRecruitmentNeedsPicker = false"
          />
        </van-popup>
        <van-field
          v-model="form.companyName"
          label="应聘公司"
          required
          disabled
          :rules="[{ required: true, message: '请输入应聘公司' }]"
        />
        <van-field
          v-model="form.expectedSalary"
          label="期望月薪"
          required
          :rules="[{ required: true, message: '请输入期望月薪(税前)' },
                  {pattern: expectedSalaryPattern, message: '只能输入数字' }
          ]"
        />
        <div style="padding:16px 0">
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
  import {getInfoCompletionBasicById} from "@/api/induction/tsmInductionBasic";
  import {listTsmRecruitAll, saveInductionBasic} from "@/api/induction/inductApp";
  import {getDictTypeById} from "@/api/sys/dictType";
  import {Toast} from "vant";

  export default {
  data() {
    return {
      iconType: {
        base: false,
        certificate: false,
        personal: false,
        work: false,
        education: false
      },
      disabled: false,
      form: {
        name: "",
        gender: {
          name: ""
        },
        ethnicity: {
          name: ""
        },
        idNumber: "",
        phoneNumber: "",
        email: "",
        nativePlace: "",
        residenceAddress: "",
        maritalStatus: { name: "" },
        childbirthStatus: { name: "" },
        politicalStatus: {
          name: ""
        },
        jobPosition: {
          postName: ""
        },
        companyName: "",
        expectedSalary: ""
      },
      genderColumns: [],
      showGenderPicker: false,
      nationColumns: [],
      showNationPicker: false,
      marriageStatusColumns: [],
      showMarriageStatusPicker: false,
      fertilizerStatusColumns: [],
      showFertilizerStatusPicker: false,
      politicalStatusColumns: [],
      showPoliticalStatusPicker: false,
      recruitmentNeedsColumns: [],
      showRecruitmentNeedsPicker: false,
      genderOptions: [],
      infoId: "",
      postId: "",
      idNumberPattern: /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
      phoneNumberPattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
      emailPattern: /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/,
      inductionId: "",
      namePattern: /^[\u4e00-\u9fa5]+$/,
      expectedSalaryPattern: /^[0-9]+$/,
      formDisabled: false,
      order: ""
    };
  },
  components: {
    processDialog
  },
  watch: {
    $route(val) {
      if (val.query && val.query["inductionId"]) {
        this.inductionId = val.query["inductionId"];
        this.getFormData();
      }
    }
  },
  mounted() {
    this.getGenderOptions();
    this.getEthnicityOptions();
    this.getMaritalStatusOptions();
    this.getChildbirthStatusOptions();
    this.getPoliticalStatusOptions();
    this.getJobPositionOptions();
    if (this.$route.query && this.$route.query["companyName"]) {
      this.form.companyName = this.$route.query["companyName"];
    }
    if (this.$route.query && this.$route.query["order"]) {
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
  methods: {
    getFormData() {
      this.disabled = true;
      getInfoCompletionBasicById(this.inductionId).then(res => {
        if (res.code === "100") {
          this.form = Object.assign(
            this.form,
            JSON.parse(JSON.stringify(res.data))
          );
          if (res.data.dataValidation && res.data.dataValidation === "1") {
            this.formDisabled = true;
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
    onFertilizerStatusConfirm(val) {
      this.form.childbirthStatus = val ? val : { name: "" };
      this.showFertilizerStatusPicker = false;
    },
    onPoliticalStatusConfirm(val) {
      this.form.politicalStatus = val ? val : { name: "" };
      this.showPoliticalStatusPicker = false;
    },
    onRecruitmentNeedsConfirm(val) {
      this.form.jobPosition = val ? val : { name: "" };
      this.showRecruitmentNeedsPicker = false;
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
          path: "/education",
          query: { inductionId: this.inductionId, order: this.order }
        });
      } else {
        this.disabled = true;
        if (this.inductionId) {
          this.form.inductionId = this.inductionId;
        }
        if (this.postId) {
          this.form.postId = this.postId;
        }
        if (this.infoId) {
          this.form.infoId = this.infoId;
        }
        saveInductionBasic(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("基本信息保存成功!");
            this.iconType.base = true;
            this.$router.push({
              path: "/education",
              query: { inductionId: res.data.id, order: this.order }
            });
            this.inductionId = res.data.id;
            this.infoId = res.data.tsmInductionInfoList[0].id;
            this.postId = res.data.tsmPostList[0].id;
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
    getChildbirthStatusOptions() {
      getDictTypeById("1495769794452094992").then(res => {
        this.fertilizerStatusColumns = res.data.dictItemList;
      });
    },
    getPoliticalStatusOptions() {
      getDictTypeById("1495769794452094996").then(res => {
        this.politicalStatusColumns = res.data.dictItemList;
      });
    },
    getJobPositionOptions() {
      let search = {
        columnName: "",
        order: "",
        params: []
      };
      listTsmRecruitAll(search).then(res => {
        if (res.code === "100") {
          this.recruitmentNeedsColumns = res.data.filter(
            item => item.staffNum !== "0" && item.actStatus === "8044"
          );
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.base {
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
