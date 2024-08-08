<template>
  <div class="positionData">
    <div class="positionData_header">
      <span class="title">岗位信息</span>
      <span class="timer">此信息保存于：{{ positionFormData.updateDate }}</span>
    </div>
    <div class="positionData_content">
      <el-form
        ref="positionFormData"
        :model="positionFormData"
        label-width="110px"
        class="dc-el-form_ElEditForm"
      >
        <el-row style="border-bottom: 1px dashed #e6e6e6;margin-top:10px;">
          <el-col :span="8">
            <el-form-item
              prop="employeeNumber"
              label="工号"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="positionFormData.employeeNumber"
                :maxLength="50"
                clearable
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="department"
              label="部门"
              class="dc-el-form-item_SelectInput"
            >
              <el-input
                v-model="positionFormData.department.name"
                :maxLength="50"
                placeholder="请输入部门"
                clearable
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="employeeType"
              label="员工类型"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="positionFormData.employeeType.name"
                :maxLength="20"
                placeholder="请选择员工类型"
                clearable
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="employeeRank"
              label="职级"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="positionFormData.employeeRank"
                :maxLength="100"
                disabled
                placeholder="请输入职级"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="technicalPosition"
              label="职位"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="positionFormData.technicalPosition"
                :style="{ width: '100%' }"
                :disabled="positionDisabled"
                placeholder="请选择"
                value-key="id"
                @change="changePosition"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in techPositionOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="margin-top:10px;">
          <el-col :span="8">
            <el-form-item
              prop="entryTime"
              label="入职时间"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="positionFormData.entryTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                :disabled="positionDisabled"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="regularTime"
              label="转正时间"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="positionFormData.regularTime"
                :maxLength="100"
                placeholder="请输入转正时间"
                clearable
                disabled
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="reportToObject"
              label="汇报对象"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="positionFormData.reportToObject"
                :style="{ width: '100%' }"
                placeholder="请选择"
                clearable
                filterable
                :disabled="positionDisabled"
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in userOptions"
                  :key="index"
                  :label="item.name + '+' + item.phone"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="probationPeriod"
              label="试用期"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="positionFormData.probationPeriod"
                :style="{ width: '100%' }"
                placeholder="请选择"
                value-key="id"
                @change="changeProbationMonth"
                :disabled="positionDisabled"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in probationMonthOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              prop="officeAddress"
              label="办公地址"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="positionFormData.officeAddress"
                :maxLength="100"
                :disabled="positionDisabled"
                placeholder="请输入办公地址"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item> </el-col
        ></el-row>
      </el-form>
    </div>
    <el-row style="text-align: center;margin: 10px 0;" v-if="!positionDisabled">
      <el-button icon="el-icon-folder-checked" @click="saveData"
        >保存</el-button
      >
    </el-row>
  </div>
</template>
<script>
import { getDictTypeById } from "@/api/sys/dictType";
import { savePost, getPostInfo } from "@/api/induction/EmployeeInfo.js";
import { listUserAll } from "@/api/admin/user";
export default {
  props: ["rowData", "positionDisabled"],
  data() {
    return {
      positionFormData: {
        employeeNumber: "",
        department: "",
        employeeType: "",
        employeeRank: "",
        technicalPosition: "",
        entryTime: "",
        regularTime: "",
        reportToObject: "",
        probationPeriod: "",
        officeAddress: "",
        updateDate: ""
      },
      probationMonthOptions: [],
      techPositionOptions: [],
      userOptions: []
    };
  },

  mounted() {
    this.getData();
    this.getProbationMonthOptions();
    this.getTechPositionOptions();
    this.getUser();
  },
  methods: {
    getData() {
      getPostInfo(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.positionFormData = Object.assign(
            this.positionFormData,
            JSON.parse(JSON.stringify(res.data[0]))
          );
        }
      });
    },
    //获取人员
    getUser() {
      listUserAll({}).then(res => {
        this.userOptions = res.data || [];
      });
    },
    changeProbationMonth(val) {
      if (this.positionFormData.entryTime) {
        this.positionFormData.regularTime = this.changeTime(
          this.positionFormData.entryTime,
          val.value
        );
      } else {
        this.showMessage({ type: "warning", msg: "请先选择入职时间" });
      }
    },
    changePosition(val) {
      this.positionFormData.employeeRank = val.value;
    },
    saveData() {
      savePost(this.positionFormData).then(res => {
        if (res.code === "100") {
          this.$message({
            type: "success",
            message: "个人信息保存成功"
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
    changeTime(entryTime, val) {
      let d = new Date(entryTime);
      var Month = d.getMonth() + 1;
      var Day = d.getDate();
      var Year = d.getFullYear();
      //试用期截至
      if (val !== "0" && val !== 0) {
        var mydate = new Date(Year, Month + Number(val), Day - 1);
        var Yeara = mydate.getFullYear();
        var Monthb = mydate.getMonth();
        var Datec = mydate.getDate();

        if (Day === 1) {
          if (Monthb === 4 || Monthb === 6 || Monthb === 9 || Monthb === 11) {
            Datec = 30;
          } else {
            Datec = 31;
          }
        }
        if (Monthb === 2 && Datec > 29) {
          if (Yeara % 4 === 0) {
            //判断为闰月
            Datec = 29;
          } else {
            Datec = 28;
          }
        }
        if (Monthb === 0) {
          Monthb = 12;
          Yeara = Yeara - 1;
        }
        let str = `${Yeara}-${Monthb > 9 ? Monthb : "0" + Monthb}-${
          Datec > 9 ? Datec : "0" + Datec
        }`;
        return str;
      } else {
        return entryTime;
      }
    },
    //获取试用期
    getProbationMonthOptions() {
      getDictTypeById("1513455318726787124").then(res => {
        this.probationMonthOptions = res.data.dictItemList;
      });
    },
    //获取技术职务
    getTechPositionOptions() {
      getDictTypeById("1515026469303484430").then(res => {
        this.techPositionOptions = res.data.dictItemList;
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.positionData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  overflow: auto;
  .positionData_header {
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
  .positionData_content {
    min-height: 200px;
    height: auto;
    margin: 0 5px 5px 5px;
    border: 1px solid #e6e6e6;
  }
}
</style>