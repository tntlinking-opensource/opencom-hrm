<template>
  <div class="education">
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
      <div class="title">教育经历</div>
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
              v-model="form[index].graduatedSchool"
              label="毕业学校"
              required
              :rules="[{ required: true, message: '请填写毕业学校' }]"
            />
            <van-field
              v-model="form[index].major"
              label="专业"
              required
              :rules="[{ required: true, message: '请填写专业' }]"
            />
            <van-field
              clickable
              label="最高学历"
              v-model="form[index].highestEducation.name"
              required
              readonly
              @click="formDisabled ? null : changeHighestEducationIndex(index)"
              :rules="[{ required: true, message: '请选择最高学历' }]"
            />

            <van-field
              clickable
              label="教育方式"
              v-model="form[index].educationMethod.name"
              required
              readonly
              @click="formDisabled ? null : changeEducationMethodIndex(index)"
              :rules="[{ required: true, message: '请选择教育方式' }]"
            />
            <van-field
              clickable
              label="入学时间"
              v-model="form[index].admissionTime"
              required
              readonly
              @click="formDisabled ? null : changeadmissionTimeIndex(index)"
              :rules="[{ required: true, message: '请选择入学时间' }]"
            />

            <van-field
              clickable
              label="毕业时间"
              v-model="form[index].graduationTime"
              required
              readonly
              @click="formDisabled ? null : changeGraduationTimeIndex(index)"
              :rules="[{ required: true, message: '请选择毕业时间' }]"
            />
            <van-field v-model="form[index].diplomaNumber" label="毕业证编号" />
            <van-field
              v-model="form[index].degreeCertificateNumber"
              label="学位证编号"
            />
          </div>
        </div>
        <van-popup v-model="showHighestEducationPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="highestEducationColumns"
            @confirm="onHighestEducatioConfirm"
            @cancel="showHighestEducationPicker = false"
          />
        </van-popup>
        <van-popup v-model="showEducationMethodPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="educationMethodColumns"
            @confirm="onEducationMethodConfirm"
            @cancel="showEducationMethodPicker = false"
          />
        </van-popup>
        <van-popup v-model="showAdmissionTimePicker" position="bottom">
          <van-datetime-picker
            type="date"
            :min-date="minDate"
            :max-date="maxDate"
            v-model="admissionTimeCurrentDate"
            @confirm="onAdmissionTimeConfirm"
            @cancel="showAdmissionTimePicker = false"
          />
        </van-popup>
        <van-popup v-model="showGraduationTimePicker" position="bottom">
          <van-datetime-picker
            :min-date="minDate"
            :max-date="maxDate"
            type="date"
            v-model="graduationTimeCurrentDate"
            @confirm="onGraduationTimeConfirm"
            @cancel="showGraduationTimePicker = false"
          />
        </van-popup>
        <div style="padding:80px 0 15px 0">
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
import { changeFormatter, changeUTC } from "./components/utils";
import processDialog from "./components/processDialog";
import { getInfoCompletionBasicById } from "@/api/induction/tsmInductionBasic";
import { saveInductionEducational } from "@/api/induction/inductApp";
import { getDictTypeById } from "@/api/sys/dictType";
import { deleteTsmEducationalRecords } from "@/api/induction/tsmEducationalRecords.js";
import { Notify } from "vant";
import { Toast } from "vant";
import { Dialog } from "vant";
export default {
  data() {
    return {
      iconType: {
        base: true,
        certificate: false,
        personal: false,
        work: false,
        education: false
      },
      disabled: false,
      formDisabled: false,
      form: [
        {
          graduatedSchool: "",
          major: "",
          highestEducation: { name: "" },
          educationMethod: { name: "" },
          admissionTime: "",
          graduationTime: "",
          diplomaNumber: "",
          degreeCertificateNumber: ""
        }
      ],

      highestEducationColumns: [],
      showHighestEducationPicker: false,
      highestEducationIndex: 0,
      educationMethodColumns: [],
      showEducationMethodPicker: false,
      educationMethodIndex: 0,
      showAdmissionTimePicker: false,
      admissionTimeIndex: 0,
      admissionTimeCurrentDate: null,
      showGraduationTimePicker: false,
      graduationTimeIndex: 0,
      graduationTimeCurrentDate: null,

      inductionId: "",
      minDate: new Date(1970, 0, 1),
      maxDate: new Date(2050, 11, 31),
      order: ""
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
    this.getHighestEducationOptions();
    this.getEducationMethodOptions();
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
          this.form = res.data.tsmEducationalRecordsList;
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
    changeHighestEducationIndex(index) {
      this.highestEducationIndex = index;
      this.showHighestEducationPicker = true;
    },
    changeEducationMethodIndex(index) {
      this.educationMethodIndex = index;
      this.showEducationMethodPicker = true;
    },
    onHighestEducatioConfirm(val) {
      this.form[this.highestEducationIndex].highestEducation = val;
      this.showHighestEducationPicker = false;
    },
    onEducationMethodConfirm(val) {
      this.form[this.educationMethodIndex].educationMethod = val;
      this.showEducationMethodPicker = false;
    },
    changeadmissionTimeIndex(index) {
      this.admissionTimeIndex = index;
      if (this.form[index].admissionTime) {
        this.admissionTimeCurrentDate = changeUTC(
          this.form[index].admissionTime
        );
      } else {
        this.admissionTimeCurrentDate = null;
      }
      this.showAdmissionTimePicker = true;
    },
    onAdmissionTimeConfirm(val) {
      let graduationTime = changeUTC(
        this.form[this.admissionTimeIndex].graduationTime
      );
      if (graduationTime && graduationTime < val) {
        //此时表示入学时间比毕业时间晚
        Notify({ type: "warning", message: "入学时间应不高于毕业时间" });
      } else {
        this.form[this.admissionTimeIndex].admissionTime = changeFormatter(val);
        this.showAdmissionTimePicker = false;
      }
    },
    changeGraduationTimeIndex(index) {
      this.graduationTimeIndex = index;
      if (this.form[index].graduationTime) {
        this.graduationTimeCurrentDate = changeUTC(
          this.form[index].graduationTime
        );
      } else {
        this.graduationTimeCurrentDate = null;
      }
      this.showGraduationTimePicker = true;
    },

    onGraduationTimeConfirm(val) {
      if (!this.form[this.graduationTimeIndex].admissionTime) {
        Notify({ type: "warning", message: "请先选择入学时间" });
      } else {
        let admissionTime = changeUTC(
          this.form[this.graduationTimeIndex].admissionTime
        );

        if (admissionTime > val) {
          //此时表示入学时间比毕业时间晚
          Notify({ type: "warning", message: "毕业时间应不低于入学时间" });
        } else {
          this.form[this.graduationTimeIndex].graduationTime = changeFormatter(
            val
          );
          this.showGraduationTimePicker = false;
        }
      }
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
          path: "/work",
          query: { inductionId: this.inductionId, order: this.order }
        });
      } else {
        this.disabled = true;
        this.form.map(item => {
          item.inductionId = this.inductionId;
          return item;
        });
        saveInductionEducational(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("教育经历保存成功!");
            this.iconType.education = true;
            this.$router.push({
              path: "/work",
              query: { inductionId: this.inductionId, order: this.order }
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
        item.graduatedSchool ||
        item.major ||
        item.highestEducation.name ||
        item.educationMethod.name ||
        item.admissionTime ||
        item.graduationTime ||
        item.diplomaNumber ||
        item.degreeCertificateNumber
      ) {
        Dialog.confirm({
          message: "已经有填写内容，确认删除？"
        })
          .then(() => {
            if (item["id"]) {
              deleteTsmEducationalRecords(item).then(res => {
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
      if (this.form.length > 5) {
        Notify({ type: "primary", message: "最多只能新增5条数据" });
      } else {
        this.form.push({
          graduatedSchool: "",
          major: "",
          highestEducation: { name: "" },
          educationMethod: { name: "" },
          admissionTime: "",
          graduationTime: "",
          diplomaNumber: "",
          degreeCertificateNumber: "",
          inductionId: this.inductionId
        });
      }
    },
    getHighestEducationOptions() {
      getDictTypeById("1496815670528286753").then(res => {
        this.highestEducationColumns = res.data.dictItemList;
      });
    },
    getEducationMethodOptions() {
      getDictTypeById("1496815670528286761").then(res => {
        this.educationMethodColumns = res.data.dictItemList;
      });
    }
  }
};
</script>

<style scoped lang="scss">
.education {
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
