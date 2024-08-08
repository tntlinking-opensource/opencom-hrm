<template>
  <div class="work">
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
      <div class="title">工作经历</div>
      <van-form :disabled="formDisabled" @submit="next">
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
            <van-field v-model="form[index].workUnit" label="工作单位" />
            <van-field v-model="form[index].position" label="职位" />
            <van-field
              clickable
              label="开始时间"
              readonly
              v-model="form[index].startTime"
              @click="formDisabled ? null : changeStartTimeIndex(index)"
            />

            <van-field
              clickable
              label="结束时间"
              readonly
              v-model="form[index].endTime"
              @click="formDisabled ? null : changeEndTimeIndex(index)"
            />
            <van-field v-model="form[index].resignReason" label="离职原因" />
            <van-field v-model="form[index].referenceName" label="证明人" />
            <van-field
              v-model="form[index].referencePhone"
              label="证明人电话"
              :rules="[
                {
                  pattern: form[index].referencePhone ? phoneNumberPattern : '',
                  message: '请输入正确的电话号码'
                }
              ]"
            />
          </div>
        </div>
        <van-popup v-model="showStartTimePicker" position="bottom">
          <van-datetime-picker
            type="date"
            :min-date="minDate"
            :max-date="maxDate"
            v-model="startTimeCurrentDate"
            @confirm="onStartTimeConfirm"
            @cancel="showStartTimePicker = false"
          />
        </van-popup>
        <van-popup v-model="showEndTimePicker" position="bottom">
          <van-datetime-picker
            type="date"
            :min-date="minDate"
            :max-date="maxDate"
            v-model="endTimeCurrentDate"
            @confirm="onEndTimeConfirm"
            @cancel="showEndTimePicker = false"
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
import processDialog from "./components/processDialog";
import { changeFormatter, changeUTC } from "./components/utils";
import { saveInductionWork } from "@/api/induction/inductApp";
import { deleteTsmWorkExperience } from "@/api/induction/tsmWorkExperience.js";
import { getInfoCompletionBasicById } from "@/api/induction/tsmInductionBasic";
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
        education: true
      },
      disabled: false,
      formDisabled: false,
      form: [
        {
          workUnit: "",
          position: "",
          startTime: "",
          endTime: "",
          resignReason: "",
          referenceName: "",
          referencePhone: ""
        }
      ],
      showStartTimePicker: false,
      startTimeIndex: 0,
      showEndTimePicker: false,
      endTimeIndex: 0,
      startTimeCurrentDate: null,
      endTimeCurrentDate: null,
      order: "",
      inductionId: "",
      minDate: new Date(1970, 0, 1),
      maxDate: new Date(2050, 11, 31),
      phoneNumberPattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    };
  },
  components: {
    processDialog
  },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
      this.getFormData();
    }
    if (this.$route.order && this.$route.query["order"]) {
      this.order = this.$route.query["order"];
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
          this.form = res.data.tsmWorkExperienceList;
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
    changeStartTimeIndex(index) {
      this.startTimeIndex = index;
      if (this.form[index].startTime) {
        this.startTimeCurrentDate = changeUTC(this.form[index].startTime);
      } else {
        this.startTimeCurrentDate = null;
      }
      this.showStartTimePicker = true;
    },
    onStartTimeConfirm(val) {
      let endTime = changeUTC(this.form[this.startTimeIndex].endTime);
      if (endTime && endTime < val) {
        //此时表示开始时间比结束时间晚
        Notify({ type: "warning", message: "开始时间不高于结束时间" });
      } else {
        this.form[this.startTimeIndex].startTime = changeFormatter(val);
        this.showStartTimePicker = false;
      }
    },
    changeEndTimeIndex(index) {
      this.endTimeIndex = index;
      if (this.form[index].endTime) {
        this.endTimeCurrentDate = changeUTC(this.form[index].endTime);
      } else {
        this.endTimeCurrentDate = null;
      }
      this.showEndTimePicker = true;
    },

    onEndTimeConfirm(val) {
      if (!this.form[this.endTimeIndex].startTime) {
        Notify({ type: "warning", message: "请先选择开始时间" });
      } else {
        let startTime = changeUTC(this.form[this.endTimeIndex].startTime);

        if (startTime > val) {
          //此时表示开始时间比结束时间晚
          Notify({ type: "warning", message: "结束时间应不低于开始时间" });
        } else {
          this.form[this.endTimeIndex].endTime = changeFormatter(val);
          this.showEndTimePicker = false;
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
          path: "/personal",
          query: { inductionId: this.inductionId, order: this.order }
        });
      } else {
        this.disabled = true;
        this.form.map(item => {
          item.inductionId = this.inductionId;
          return item;
        });
        saveInductionWork(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("工作经历保存成功!");
            this.iconType.work = true;
            this.$router.push({
              path: "/personal",
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
        item.workUnit ||
        item.position ||
        item.startTime ||
        item.endTime ||
        item.resignReason ||
        item.referenceName ||
        item.referencePhone
      ) {
        Dialog.confirm({
          message: "已经有填写内容，确认删除？"
        })
          .then(() => {
            if (item["id"]) {
              deleteTsmWorkExperience(item).then(res => {
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
        workUnit: "",
        position: "",
        startTime: "",
        endTime: "",
        resignReason: "",
        referenceName: "",
        referencePhone: ""
      });
    }
  }
};
</script>

<style scoped lang="scss">
.work {
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
