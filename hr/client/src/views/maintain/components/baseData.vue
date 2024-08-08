<template>
  <div class="baseData">
    <div class="baseData_header">
      <span class="title">基本信息</span>
      <span class="timer">此信息保存于：{{ baseFormData.updateDate }}</span>
    </div>
    <div class="baseData_content">
      <el-row class="content_row">
        <el-col :span="8">
          <div class="image">
            <el-upload
              class="avatar-uploader"
              accept=".jpg,.jpeg,.png,.ico"
              :before-upload="beforeAvatarUpload"
              :show-file-list="false"
              action=""
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </el-col>
        <el-col :span="16" style="margin-top:10px;">
          <el-form
            ref="baseFormData"
            :model="baseFormData"
            label-width="110px"
            class="dc-el-form_ElEditForm"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item
                  prop="name"
                  label="姓名"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="baseFormData.name"
                    :maxLength="50"
                    disabled
                    placeholder="请输入姓名"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="gender"
                  label="性别"
                  class="dc-el-form-item_SelectInput"
                >
                  <el-input
                    v-model="baseFormData.gender.name"
                    :maxLength="50"
                    placeholder="请输入性别"
                    disabled
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="ethnicity"
                  label="民族"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-select
                    v-model="baseFormData.ethnicity"
                    :style="{ width: '100%' }"
                    value-key="id"
                    placeholder="请选择"
                    filterable
                    clearable
                    :disabled="baseDisabled"
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in ethnicityOptions"
                      :key="index"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="idNumber"
                  label="身份证号"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="baseFormData.idNumber"
                    :maxLength="100"
                    placeholder="请输入身份证号"
                    clearable
                    disabled
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="birthDate"
                  label="出生年月"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="baseFormData.birthDate"
                    :maxLength="100"
                    disabled
                    placeholder="请输入出生年月"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="age"
                  label="年龄"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="baseFormData.age"
                    :maxLength="100"
                    disabled
                    placeholder="请输入年龄"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="politicalStatus"
                  label="政治面貌"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-select
                    v-model="baseFormData.politicalStatus"
                    :style="{ width: '100%' }"
                    placeholder="请选择政治面貌"
                    value-key="id"
                    filterable
                    clearable
                    :disabled="baseDisabled"
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in politicalStatusOptions"
                      :key="index"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item
                  prop="phoneNumber"
                  label="手机号"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="baseFormData.phoneNumber"
                    :maxLength="100"
                    disabled
                    placeholder="请输入手机号"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col> </el-row></el-form
        ></el-col>
      </el-row>
      <el-row style="margin-top:10px;">
        <el-form
          ref="baseFormData"
          :model="baseFormData"
          label-width="110px"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="8">
              <el-form-item
                prop="nativePlace"
                label="籍贯"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="baseFormData.nativePlace"
                  :maxLength="50"
                  placeholder="请输入籍贯"
                  :disabled="baseDisabled"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="residenceAddress"
                label="户籍所在地"
                class="dc-el-form-item_SelectInput"
              >
                <el-input
                  v-model="baseFormData.residenceAddress"
                  :maxLength="20"
                  placeholder="请输入户籍所在地"
                  :disabled="baseDisabled"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="nationality"
                label="所在地区"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="baseFormData.nationality"
                  :maxLength="20"
                  placeholder="请输入所在地区"
                  :disabled="baseDisabled"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="zodiac"
                label="属相"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="baseFormData.zodiac"
                  :maxLength="100"
                  placeholder="请输入个人属相"
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="bloodType"
                label="血型"
                class="dc-el-form-item_SingleInput"
              >
                <el-select
                  v-model="baseFormData.bloodType"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  filterable
                  clearable
                  :disabled="baseDisabled"
                  value-key="id"
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in bloodTypeOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="horoscope"
                label="星座"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="baseFormData.horoscope"
                  :maxLength="100"
                  placeholder="请输入星座"
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="maritalStatus"
                label="婚姻状况"
                class="dc-el-form-item_SingleInput"
              >
                <el-select
                  v-model="baseFormData.maritalStatus"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  filterable
                  clearable
                  :disabled="baseDisabled"
                  value-key="id"
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in maritalStatusOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="childbirthStatus"
                label="生育状况"
                class="dc-el-form-item_SingleInput"
              >
                <el-select
                  v-model="baseFormData.childbirthStatus"
                  :style="{ width: '100%' }"
                  placeholder="请选择生育状态"
                  :disabled="baseDisabled"
                  value-key="id"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in childbirthStatusOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="workStartDate"
                label="参加工作时间"
                class="dc-el-form-item_SingleInput"
              >
                <el-date-picker
                  v-model="baseFormData.workStartDate"
                  :style="{ width: '100%' }"
                  placeholder="请选择"
                  :disabled="baseDisabled"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerMonth"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="workYearNum"
                label="工作年限"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="baseFormData.workYearNum"
                  :maxLength="100"
                  placeholder="请输入工作年限"
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col> </el-row></el-form
      ></el-row>
    </div>
    <el-row style="text-align: center;margin: 10px 0;" v-if="!baseDisabled">
      <el-button icon="el-icon-folder-checked" @click="saveData"
        >保存</el-button
      >
    </el-row>
  </div>
</template>
<script>
import { getDictTypeById } from "@/api/sys/dictType";
import {
  getInfo,
  saveBasicInfo,
  headSculptureUpload,
  headSculptureDown
} from "@/api/induction/EmployeeInfo.js";

export default {
  props: ["rowData", "baseDisabled"],
  data() {
    return {
      baseFormData: {
        name: "", // 姓名
        gender: {
          id: null,
          name: ""
        },
        phoneNumber: "", // 手机号码
        email: "", // 个人邮箱
        idNumber: "", // 身份证号码
        birthDate: "",
        ethnicity: {
          id: null,
          name: ""
        },
        age: "",
        politicalStatus: "",
        nativePlace: "", // 籍贯
        residenceAddress: "",
        nationality: "",
        zodiac: "",
        bloodType: "",
        horoscope: "",
        maritalStatus: "",
        childbirthStatus: "",
        workStartDate: "",
        workYearNum: "",
        baseFormData: ""
      },
      // 民族选项
      ethnicityOptions: [],
      //婚姻选项
      maritalStatusOptions: [],
      // 政治面貌选项
      politicalStatusOptions: [],
      // 生育状态选项
      childbirthStatusOptions: [],
      bloodTypeOptions: [],
      imageUrl: ""
    };
  },
  watch: {
    "rowData.id"() {
      this.getData();
      this.getImage();
    }
  },
  mounted() {
    this.initData();
  },
  methods: {
    getData() {
      getInfo(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.baseFormData = Object.assign(
            this.baseFormData,
            JSON.parse(JSON.stringify(res.data))
          );
        }
      });
    },
    getImage() {
      headSculptureDown(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          if (res.data.length > 0) {
            let obj = res.data[0];
            let fileByte = obj.sysFileContentList[0].fileByte;
            let arr = obj.fileType.split(".");
            let type = "";
            if (arr[1] == "ico") {
              type = "x-icon";
            } else {
              type = arr[1];
            }
            let arrI = fileByte.split("base64");
            this.imageUrl = `data:image/${type};base64,` + arrI[1];
          }
        }
      });
    },

    beforeAvatarUpload(file) {
      let form = new FormData();
      form.append("file", file);
      form.append("name", file.name);
      headSculptureUpload(form, this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.$message({ type: "success", message: "头像保存成功" });
          this.getImage();
        } else {
          this.$message({
            type: "warning",
            message: "网络错误请重试"
          });
        }
      });
    },

    saveData() {
      saveBasicInfo(this.baseFormData).then(res => {
        if (res.code === "100") {
          this.$message({
            type: "success",
            message: "基本信息保存成功"
          });
          this.getData();
        } else {
          this.$message({
            type: "warning",
            message: "网络错误请重试"
          });
        }
      });
    },
    initData() {
      this.getData();
      this.getEthnicityOptions();
      this.getMaritalStatusOptions();
      this.getChildbirthStatusOptions();
      this.getPoliticalStatusOptions();
      this.getBloodTypeOptions();
      this.getImage();
    },
    getEthnicityOptions() {
      getDictTypeById("1495769794452094981").then(res => {
        this.ethnicityOptions = res.data.dictItemList;
      });
    },
    getMaritalStatusOptions() {
      getDictTypeById("1495769794452094986").then(res => {
        this.maritalStatusOptions = res.data.dictItemList;
      });
    },
    getChildbirthStatusOptions() {
      getDictTypeById("1495769794452094992").then(res => {
        this.childbirthStatusOptions = res.data.dictItemList;
      });
    },
    getPoliticalStatusOptions() {
      getDictTypeById("1495769794452094996").then(res => {
        this.politicalStatusOptions = res.data.dictItemList;
      });
    },
    getBloodTypeOptions() {
      getDictTypeById("1538737386019995702").then(res => {
        this.bloodTypeOptions = res.data.dictItemList;
      });
    }
  }
};
</script>
<style lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #999999;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 180px !important;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<style lang="scss" scoped>
.baseData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  overflow: auto;
  .baseData_header {
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
  .baseData_content {
    min-height: 200px;
    height: auto;
    margin: 0 5px 5px 5px;
    border: 1px solid #e6e6e6;
    .content_row {
      border-bottom: 1px dashed #e6e6e6;
      .image {
        width: 100%;
        text-align: center;

        img {
          width: 250px;
          height: 180px;
          margin-top: 10px;
        }
      }
    }
  }
}
</style>