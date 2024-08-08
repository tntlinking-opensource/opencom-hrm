<template>
  <el-row class="dc-container inductionAppForm" v-loading="loading">
    <el-form
      ref="userFormData"
      :model="userFormData"
      label-width="120px"
      class="dc-el-form_ElEditForm"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="应聘者:" class="dc-el-form-item_SelectInput">
            {{ userFormData.name }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="应聘职位:" class="dc-el-form-item_SingleInput">
            {{ userFormData.jobPosition.postName }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="联系电话:" class="dc-el-form-item_SelectInput">
            {{ userFormData.phoneNumber }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider class="dc-el-divider_ElDivider">主要信息</el-divider>
      <el-row>
        <el-col :span="24">
          <el-form-item label="电脑配置:" class="dc-el-form-item_SelectInput">
            <el-radio-group v-model="userFormData.computerCondition.id">
              <el-radio
                :label="item.id"
                v-for="item in computerConditionOptions"
                :key="item.id"
                >{{ item.name }}</el-radio
              >
            </el-radio-group>
            <span
              v-if="userFormData.computerCondition.id === '1513455318726787145'"
              >：{{ userFormData.computerSpecialRemarks }}</span
            >
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="人员类别:" class="dc-el-form-item_SelectInput">
            <el-radio-group v-model="userFormData.employeeType.id">
              <el-radio
                :label="item.id"
                v-for="item in employeeTypeOptions"
                :key="item.id"
                >{{ item.name }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="入职时间:" class="dc-el-form-item_SelectInput">
            {{ userFormData.entryTime }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所属部门:" class="dc-el-form-item_SingleInput">
            {{ userFormData.department.name }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="个人邮箱:" class="dc-el-form-item_SingleInput">
            {{ userFormData.email }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="试用期薪资:" class="dc-el-form-item_SelectInput">
            {{ userFormData.probationWages }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="转正薪资:" class="dc-el-form-item_SelectInput">
            {{ userFormData.fullWages }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="职级:" class="dc-el-form-item_SelectInput">
            {{ userFormData.employeeLevel }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="职位:" class="dc-el-form-item_SelectInput">
            {{ userFormData.techPosition.name }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="劳动合同:"
            class="dc-el-form-item_PickerTimeDate"
          >
            <span style="font-weight: bolder;"> 试用期：</span
            >{{ userFormData.probationMonth.name }}
            <span style="font-weight: bolder;margin-left:10px">
              合同开始时间：</span
            >{{ userFormData.contractStartTime }}
            <br />
            <el-radio label="standard" v-model="contractMonths"
              >标准合同期限(3年期合同)</el-radio
            >
            <el-radio
              label="normal"
              v-model="contractMonths"
              style="margin-right: 0;"
              >合同期限：</el-radio
            >至
            <span
              v-if="contractMonths === 'standard'"
              style="width: 100px;display: inline-block;border-bottom: 1px solid;"
            ></span>
            <span
              style="border-bottom: 1px solid;"
              v-if="contractMonths === 'normal'"
              >{{ userFormData.contractEndTime }}</span
            >
            结束
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="工作城市:" class="dc-el-form-item_SelectInput">
            <el-radio-group v-model="workCity">
              <el-radio label="shanghai">上海</el-radio>
              <el-radio label="other">其他：</el-radio>
            </el-radio-group>
            <span v-if="workCity === 'other'">{{ userFormData.workCity }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            label="招聘方式:"
            class="dc-el-form-item_PickerTimeDate"
          >
            <el-radio-group v-model="userFormData.recruitmentMode.id">
              <el-radio
                :label="item.id"
                v-for="item in recruitmentModeOptions"
                :key="item.id"
                >{{ item.name }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider class="dc-el-divider_ElDivider">其余信息</el-divider>
      <el-col :span="6">
        <el-form-item
          label="试用期结束时间:"
          class="dc-el-form-item_SelectInput"
        >
          {{ userFormData.probationEndTime }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="合同签约单位:" class="dc-el-form-item_SingleInput">
          {{ userFormData.contractSignUnit }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="合同期(月):" class="dc-el-form-item_SelectInput">
          {{ userFormData.contractMonths }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="所属公司:" class="dc-el-form-item_SelectInput">
          {{ userFormData.companyName }}</el-form-item
        >
      </el-col>
      <el-col :span="6">
        <el-form-item label="人事区域:" class="dc-el-form-item_SelectInput">
          {{ userFormData.personnelArea }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="工号:" class="dc-el-form-item_SelectInput">
          {{ userFormData.employeeNumber }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="公司邮箱:" class="dc-el-form-item_SingleInput">
          {{ userFormData.companyEmail }}</el-form-item
        >
      </el-col>
      <el-col :span="6">
        <el-form-item label="员工性质:" class="dc-el-form-item_PickerTimeDate">
          {{ userFormData.employeeNature.name }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="工时类别:" class="dc-el-form-item_NumberInput">
          {{ userFormData.workingHoursCategory.name }}</el-form-item
        >
      </el-col>
      <el-col :span="6">
        <el-form-item label="员工状态:" class="dc-el-form-item_PickerTimeDate">
          {{ userFormData.employeeStatus.name }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="入职类型:" class="dc-el-form-item_SingleInput">
          {{ userFormData.entryType.name }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="毕业学校:" class="dc-el-form-item_SingleInput">
          {{ userFormData.tsmEducationalRecordsList[0].graduatedSchool }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="最高学历:" class="dc-el-form-item_SingleInput">
          {{ userFormData.tsmEducationalRecordsList[0].highestEducation.name }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="毕业时间:" class="dc-el-form-item_SingleInput">
          {{ userFormData.tsmEducationalRecordsList[0].graduationTime }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="员工性别:" class="dc-el-form-item_SingleInput">
          {{ userFormData.gender.name }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="出生日期:" class="dc-el-form-item_SingleInput">
          {{ userFormData.birthDate }}</el-form-item
        >
      </el-col>
      <el-col :span="6">
        <el-form-item label="身份证号:" class="dc-el-form-item_SingleInput">
          {{ userFormData.idNumber }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="报道日期:" class="dc-el-form-item_SingleInput">
          {{ userFormData.reportTime }}
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="居住地址:" class="dc-el-form-item_SingleInput">
          {{ userFormData.residenceAddress }}
        </el-form-item>
      </el-col>
    </el-form>
  </el-row>
</template>
<script>
/** 根据用户界面配置import组件 结束 */
import { getDictTypeById } from "@/api/sys/dictType";
import OperationIcon from "@/components/OperationIcon";
import {
  getFlowInitById,
  createTsmInductionBasic,
  getTsmInductionBasicByTaskId,
  agreeTsmInductionBasic,
  reapplyTsmInductionBasic,
  forwardTsmInductionBasic,
  commissionTsmInductionBasic,
  proposeTsmInductionBasic,
  backTsmInductionBasic,
  rejectTsmInductionBasic,
  terminateTsmInductionBasic,
  reverseTsmInductionBasic,
  listBackActivity
} from "@/api/induction/tsmInductionBasic";

import BizForm from "@/views/wf/common/bizForm";
export default {
  extends: BizForm,
  components: {
    OperationIcon
  },
  props: ["options"],
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      userFormData: {
        name: "", // 姓名
        jobPosition: {
          id: null,
          postName: null
        },
        phoneNumber: "", // 手机号码
        computerCondition: {
          id: "",
          name: null
        },
        computerSpecialRemarks: "",
        employeeType: {
          id: "",
          name: null
        },
        entryTime: "", // 入职时间
        email: "", // 个人邮箱
        department: {
          id: "",
          name: null
        },
        probationWages: "",
        fullWages: "",
        employeeLevel: "",
        techPosition: {
          id: "",
          name: null
        },
        recruitmentMode: {
          id: "",
          name: null
        },
        contractSignUnit: "",
        contractMonths: "",
        companyName: "",
        personnelArea: "",
        employeeNumber: "",
        companyEmail: "",
        employeeNature: {
          id: "",
          name: null
        },
        workingHoursCategory: { id: "", name: null },
        employeeStatus: { id: "", name: null },
        probationMonth: { id: "", name: null },
        entryType: {
          id: "",
          name: null
        },
        gender: {
          id: "",
          name: null
        },
        birthDate: "",
        idNumber: "", // 身份证号码
        reportTime: "",
        residenceAddress: "", // 居住地址
        workCity: "",
        contractEndTime: "",
        tsmEducationalRecordsList: [
          {
            graduatedSchool: "",
            highestEducation: {
              id: "",
              name: ""
            },
            graduationTime: ""
          }
        ]
      },
      computerConditionOptions: [],
      employeeTypeOptions: [],
      recruitmentModeOptions: [],
      selectOptions: [],
      rowData: {},
      workCity: "",
      contractMonths: "standard"
    };
  },

  computed: {},
  methods: {
    getDic() {
      // 初始化自定义类型选择框选项变量
      this.getComputerConditionOptions();
      this.getEmployeeTypeOptions();
      this.getRecruitmentModeOptions();
    },
    loadModelData(taskId) {
      this.setLoad();
      getTsmInductionBasicByTaskId(taskId).then(res => {
        if (res.code === "100") {
          this.userFormData = Object.assign(this.userFormData, res.data);
          if (this.userFormData.workCity === "上海") {
            this.workCity = "shanghai";
          } else {
            this.workCity = "other";
          }
          if (
            this.userFormData.contractMonths === 36 ||
            this.userFormData.contractMonths === "36"
          ) {
            this.contractMonths = "standard";
          } else {
            this.contractMonths = "normal";
          }
        }
        this.resetLoad();
      });
    },
    //获取表格数据
    getUserData() {
      this.setLoad();
      getFlowInitById(this.options ? this.options.id : this.bizData.id).then(
        res => {
          if (res.code === "100") {
            this.userFormData = Object.assign(this.userFormData, res.data);
            if (this.userFormData.workCity === "上海") {
              this.workCity = "shanghai";
            } else {
              this.workCity = "other";
            }
            if (
              this.userFormData.contractMonths === 36 ||
              this.userFormData.contractMonths === "36"
            ) {
              this.contractMonths = "standard";
            } else {
              this.contractMonths = "normal";
            }
          }
          this.resetLoad();
        }
      );
    },
    doSaveDraft() {
      this.$message("该页面暂不需要草稿功能");
    },
    //获取电脑情况
    getComputerConditionOptions() {
      getDictTypeById("1513455318726787139").then(res => {
        this.computerConditionOptions = res.data.dictItemList;
      });
    },
    //获取员工类型
    getEmployeeTypeOptions() {
      getDictTypeById("1513227015445110975").then(res => {
        this.employeeTypeOptions = res.data.dictItemList;
      });
    },
    //获取招聘方式
    getRecruitmentModeOptions() {
      getDictTypeById("1513455318726787147").then(res => {
        this.recruitmentModeOptions = res.data.dictItemList;
      });
    },
    doCreate(files, actNodes) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.actNodes = actNodes; // 选择节点及审批用户
      let formData = this.createWfData(this.userFormData, files);
      createTsmInductionBasic(this.procDef.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    doReApply(comment, files, actNodes) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.actNodes = actNodes; // 选择节点及审批用户
      this.userFormData.taskComement = comment; // 意见

      let formData = this.createWfData(this.userFormData, files);
      reapplyTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 同意 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doAgree(comment, files, actNodes) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.actNodes = actNodes; // 选择节点及审批用户
      this.userFormData.taskComement = comment; // 意见

      let formData = this.createWfData(this.userFormData, files);
      agreeTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 转派任务
    doForward(comment, files, actNodes) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.actNodes = actNodes; // 选择节点及审批用户
      this.userFormData.taskComement = comment; // 意见

      let formData = this.createWfData(this.userFormData, files);
      forwardTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 委派任务
    doCommission(comment, files, actNodes) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.actNodes = actNodes; // 选择节点及审批用户
      this.userFormData.taskComement = comment; // 意见

      let formData = this.createWfData(this.userFormData, files);
      commissionTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 拟办（办理委派过来的任务）
    doPropose(comment, files) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.taskComement = comment; // 意见
      let formData = this.createWfData(this.userFormData, files);
      proposeTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 拒绝
    doReject(comment, files) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.taskComement = comment; // 意见
      let formData = this.createWfData(this.userFormData, files);
      rejectTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 退回 comment 意见, actNodes退回节点和用户信息
    doBack(comment, files, actNodes) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.actNodes = actNodes; // 选择节点及审批用户
      this.userFormData.taskComement = comment; // 意见
      let formData = this.createWfData(this.userFormData, files);
      backTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 终止流程 comment 意见, actNodes退回节点和用户信息
    doTerminate(comment, files) {
      if (!files) {
        files = [];
      }
      this.setLoad();
      this.userFormData.taskComement = comment; // 意见

      let formData = this.createWfData(this.userFormData, files);
      terminateTsmInductionBasic(this.task.id, formData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.$emit("save-finished");
          }
          this.showMessage(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 撤回任务
    doReverse(task) {
      this.setLoad();
      reverseTsmInductionBasic(this.task.id)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    doListActivity() {
      this.setLoad();
      listBackActivity(this.task.id)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    }
  },
  watch: {},
  mounted() {
    this.getDic();
    if (this.wfAction == "approve") {
      // 重新申请
      let reApply =
        this.task.assignee == this.task.owner &&
        this.task.assignee == this.task.extInfo.applicantId &&
        this.task.taskDefinitionKey == this.task.extInfo.appActId;
      this.loadModelData(this.task.id);
    } else {
      this.getUserData();
    }
  }
};
</script>
<style lang="scss">
.inductionAppForm {
  padding: 0 15px;
  .my-input-sc {
    display: inline-block;
    height: 30px;
    line-height: 30px;
    -webkit-appearance: none;
    background-color: #ffffff;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    font-size: inherit;
    outline: none;
    padding: 0 15px;
    transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
    width: 100%;
  }
  .dc-el-form_ElEditForm {
    pointer-events: none;
  }
}
</style>
