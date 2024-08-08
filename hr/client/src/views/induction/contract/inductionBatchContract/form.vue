<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="90%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-form
        ref="editForm"
        :model="editFormData"
        label-width="100px"
        :disabled="action == 'view'"
        class="dc-el-form_ElEditForm"
      >
        <el-row>
          <el-divider content-position="left">个人信息</el-divider>
          <el-col :span="6">
            <el-form-item prop="techPosition" label="姓名" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.induction.name"
                placeholder="请输入员工姓名"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="techPosition" label="性别" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.induction.gender.name"
                placeholder="请输入员工性别"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              prop="entryTime"
              label="入职时间"
              class="dc-el-form-item_PickerTimeDate"
            >
              <el-date-picker
                v-model="editFormData.entryTime"
                :style="{ width: '100%' }"
                placeholder="请选择入职时间"
                type="datetime"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="techPosition" label="公司邮箱" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.companyEmail"
                placeholder="请输入公司邮箱"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="employeeLevel" label="员工职级" class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.employeeLevel"
                placeholder="请输入员工职级"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="probationMonth.id" label="试用期" class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.probationMonth.name"
                placeholder="请输入试用期"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="employeeNumber" label="工号" class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.employeeNumber"
                placeholder="请输入工号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="department.id" label="部门" class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.department.name"
                placeholder="请输入部门"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item prop="company.id" label="所属公司" class="dc-el-form-item_SelectInput">
              <el-input
                v-model="editFormData.company.name"
                placeholder="请输入所属公司"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-divider content-position="left">劳动合同</el-divider>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="code" label="合同编号" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.code"
                placeholder="请输入合同编号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="theme" label="合同主题" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.theme"
                placeholder="请输入合同主题"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="induction.idNumber" label="身份证号码" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.induction.idNumber"
                placeholder="请输入身份证号码"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="induction.residenceAddress" label="居住地地址" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.induction.residenceAddress"
                placeholder="请输入居住地地址"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="induction.phoneNumber" label="联系方式" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.induction.phoneNumber"
                placeholder="请输入联系方式"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="induction.email" label="个人邮箱" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.induction.email"
                placeholder="请输入个人邮箱"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px"
              prop="contractStartTime"
              label="合同开始日期"
              class="dc-el-form-item_PickerTimeDate"
            >
              <el-date-picker
                v-model="editFormData.contractStartTime"
                :style="{ width: '100%' }"
                placeholder="请选择合同开始时间"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px"
              prop="contractEndTime"
              label="合同结束日期"
              class="dc-el-form-item_PickerTimeDate"
            >
              <el-date-picker
                v-model="editFormData.contractEndTime"
                :style="{ width: '100%' }"
                placeholder="请选择合同结束时间"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px"
              prop="contractEndTime"
              label="试用期开始时间"
              class="dc-el-form-item_PickerTimeDate"
            >
              <el-date-picker
                v-model="editFormData.entryTime"
                :style="{ width: '100%' }"
                placeholder="请选择试用期开始时间"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px"
              prop="probationEndTime"
              label="试用期结束时间"
              class="dc-el-form-item_PickerTimeDate"
            >
              <el-date-picker
                v-model="editFormData.probationEndTime"
                :style="{ width: '100%' }"
                placeholder="请选择试用期结束时间"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="techPosition.id" label="担任职务" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.techPosition.name"
                :maxLength="255"
                placeholder="请输入担任职务"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="bjob" label="从事工作" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.bjob"
                :maxLength="255"
                placeholder="请输入从事工作"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="fullWages" label="全额工资" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.fullWages"
                :maxLength="50"
                placeholder="请输入全额工资（元）"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="probationWages" label="试用期工资" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.probationWages"
                :maxLength="50"
                placeholder="请输入试用期工资（元）"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="basicWages" label="基本工资" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.basicWages"
                :maxLength="50"
                placeholder="请输入基本工资（元）"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="systemReward" label="制度奖" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.systemReward"
                :maxLength="50"
                placeholder="请输入制度奖（元）"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="secrecyReward" label="保密奖" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.secrecyReward"
                :maxLength="50"
                placeholder="请输入保密奖（元）"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="contractSignUnit" label="甲方" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.contractSignUnit"
                :maxLength="255"
                placeholder="请输入甲方"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label-width="120px" prop="reportAddress" label="甲方住所地" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.reportAddress"
                :maxLength="255"
                placeholder="请输入甲方住所地"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="120px" prop="laborLegalmana" label="法定代表人" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.laborLegalmana"
                :maxLength="255"
                placeholder="请输入法定代表人"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">工作协议</el-divider>
        <div style="display: flex;justify-content: center">
          <div>
            <el-row>
              <el-col :span="12">
                <el-form-item label-width="120px" prop="basicPerformance" label="基本绩效" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="editFormData.basicPerformance"
                    :maxLength="50"
                    placeholder="请输入基本绩效（元）"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label-width="120px" prop="probationBasicPerformance" label="试用期基本绩效" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="editFormData.probationBasicPerformance"
                    :maxLength="50"
                    placeholder="请输入试用期基本绩效（元）"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label-width="120px" prop="floatingPerformance" label="浮动绩效" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="editFormData.floatingPerformance"
                    :maxLength="50"
                    placeholder="请输入浮动绩效（元）"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label-width="120px"
                              prop="probationFloatingPerformance"
                              label="试用期浮动绩效"
                              class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.probationFloatingPerformance"
                    :maxLength="50"
                    placeholder="请输入试用期浮动绩效（元）"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { getInductionBatchContractById, saveInductionBatchContract } from '@/api/induction/contract/inductionBatchContract'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'inductionBatchContract-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '合约发起'
      },
      dialogTitle: '合约发起',
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ''
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()

      saveInductionBatchContract(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getInductionBatchContractById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
            resolve(form)
          })
          .catch((error) => {
            this.outputError(error)
          })
      })
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    initOptions(This) {
    },
    initEditData(This) {
      return {
        code: '', // 合同编号
        theme: '', // 合同主题
        contractInformation: {
          id: null,
          entryTime: null
        },
        induction: {
          id: null,
          name: null,
          gender: {
            id: null,
            name: null
          },
          idNumber: null,
          age: null,
          phoneNumber: null,
          residenceAddress: null,
          email: null
        },
        companyEmail: '', // 公司邮箱
        entryTime: '', // 入职时间
        probationMonth: { // 试用期
          id: null,
          name: null
        },
        department: { // 部门
          id: null,
          name: null
        },
        company: { // 公司
          id: null,
          name: null
        },
        employeeNumber: '', // 工号
        employeeLevel: '', // 员工职级
        contractStartTime: '', // 合同开始时间
        contractEndTime: '', // 合同结束时间
        probationEndTime: '', // 试用期结束时间
        techPosition: { // 员工职位
          id: null,
          name: null,
        },
        bjob: '', // 从事工作
        contractSignUnit: '', // 合同签约单位
        laborLegalmana: '', // 合同签约方法人
        reportAddress: '', // 甲方住所地
        fullWages: '', // 全额工资（元）
        probationWages: '', // 试用期工资（元）
        basicWages: '', // 基本工资（元）
        probationBasicWages: '', // 试用期基本工资（元）
        secrecyReward: '', // 保密奖（元）
        systemReward: '', // 制度奖（元）
        basicPerformance: '', // 基本绩效（元）
        floatingPerformance: '', // 浮动绩效（元）
        probationBasicPerformance: '', // 试用期基本绩效（元）
        probationFloatingPerformance: '', // 试用期浮动绩效（元）
        contractDate: '', // 合同生成时间
        contractStartDate: '', // 合同发起时间
        contractStatus: {
          id: null,
          name: null,
          value: null
        },
        signStatus: {
          id: null,
          name: null,
          value: null
        },
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
    })
  }
}
</script>
