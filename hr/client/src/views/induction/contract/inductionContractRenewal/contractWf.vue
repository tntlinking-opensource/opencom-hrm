<template>
  <el-row class="dc-container" v-loading="loading">
    <iframe :src="pageUrl" frameborder="no" style="width: 100%;height: 600px" scrolling="auto" />
  </el-row>
</template>
<script>
import BizForm from '@/views/wf/common/bizForm'
import {
  createInductionContractRenewal,
  reapplyInductionContractRenewal,
  getInductionContractRenewalByTaskId,
  agreeInductionContractRenewal,
  forwardInductionContractRenewal,
  commissionInductionContractRenewal,
  proposeInductionContractRenewal,
  backInductionContractRenewal,
  rejectInductionContractRenewal,
  terminateInductionContractRenewal,
  reverseInductionContractRenewal,
  listBackActivity,
  saveDraftInductionContractRenewal,
  viewReport
} from '@/api/induction/contract/inductionContractRenewal'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BizForm,
  name: 'inductionBatchContract-form',
  components: {
    OperationIcon
  },
  data() {
    return {
      editFormData: this.initEditData(),
      dialogProps: {
        visible: false,
        title: ''
      },
      pageUrl: '',
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
    loadModelData(taskId) {
      this.setLoad()
      getInductionContractRenewalByTaskId(taskId).then((responseData) => {
        if (responseData.code == 100) {
          this.editFormData = {
            ...this.initEditData(),
            ...responseData.data
          }
          this.getReportUrl()
          this.$emit('after-load-data', this.editFormData) // 表单数据对象传给父节点
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      })
      .catch((error) => {
        this.outputError(error)
      })
    },
    // 保存草稿，files附件列表, draftModel 草稿对象
    doSaveDraft(draftModel, files) {
      if (!draftModel) {
        // 不是从草稿打开创建，保存时更新原草稿
        draftModel = {
          procdefId: this.procDef.id,
          procdefName: this.procDef.theme,
          name: this.procDef.name + '-' + this.editFormData.theme
        }
      }
      this.setLoad()
      let formData = this.createWfData(this.editFormData, files)
      formData.append('strDraft', JSON.stringify(draftModel))
      saveDraftInductionContractRenewal(formData)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 创建一个新流程 process流程 actNodes是提交的用户任务节点及其审批用户
    doCreate(files, actNodes) {
      this.setLoad()
      this.editFormData.actNodes = actNodes // 选择节点及审批用户
      let formData = this.createWfData(this.editFormData, files)
      createInductionContractRenewal(this.procDef.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 重新发起申请 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doReApply(comment, files, actNodes) {
      this.setLoad()
      this.editFormData.actNodes = actNodes // 选择节点及审批用户
      this.editFormData.taskComement = comment // 意见
      let formData = this.createWfData(this.editFormData, files)
      reapplyInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      })
      .catch((error) => {
        this.outputError(error)
      })
    },
    // 同意 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doAgree(comment, files, actNodes) {
      this.setLoad()
      this.editFormData.actNodes = actNodes // 选择节点及审批用户
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      agreeInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 转派任务
    doForward(comment, files, actNodes) {
      this.setLoad()
      this.editFormData.actNodes = actNodes // 选择节点及审批用户
      this.editFormData.taskComement = comment // 意见
      let formData = this.createWfData(this.editFormData, files)
      forwardInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 委派任务
    doCommission(comment, files, actNodes) {
      this.setLoad()
      this.editFormData.actNodes = actNodes // 选择节点及审批用户
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      commissionInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 拟办（办理委派过来的任务）
    doPropose(comment, files) {
      this.setLoad()
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      proposeInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 拒绝
    doReject(comment, files) {
      this.setLoad()
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      rejectInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 退回 comment 意见, actNodes退回节点和用户信息
    doBack(comment, files, actNodes) {
      this.setLoad()
      this.editFormData.actNodes = actNodes // 选择节点及审批用户
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      backInductionContractRenewal(this.task.id, formData).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 终止流程 comment 意见, actNodes退回节点和用户信息
    doTerminate(comment, files) {
      this.setLoad()
      this.editFormData.taskComement = comment // 意见
      let formData = this.createWfData(this.editFormData, files)
      terminateInductionContractRenewal(this.task.id, formData).then((responseData) => {
        if (responseData.code == 100) {
          this.dialogProps.visible = false
          this.$emit('save-finished')
        }
        this.showMessage(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    // 撤回任务
    doReverse(task) {
      this.setLoad()
      reverseInductionContractRenewal(task.id).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    doListActivity() {
      this.setLoad()
      listBackActivity(this.task.id).then((responseData) => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    getReportUrl() { // 存证报告
      this.setLoad()
      viewReport(this.editFormData.id).then((responseData) => {
        if (responseData.code == 100) {
          this.pageUrl = responseData.data
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    initEditData(This) {
      return {
        code: '', // 合同编号
        theme: '', // 合同主题
        name: '', // 姓名
        gender: {
          id: null,
          name: null
        },
        phone: '', // 手机号
        idNumber: '', // 身份证号码
        email: '', // 邮箱
        address: '', // 居住地地址
        entryTime: '', // 入职时间
        probationMonth: {
          id: null,
          name: null
        },
        employeeLevel: '', // 员工职级
        employeeNumber: '', // 工号
        techPosition: {
          id: null,
          name: null,
          value: null
        }, // 员工职位
        bjob: '', // 从事工作
        contractStartTime: '', // 合同开始时间
        contractEndTime: '', // 合同结束时间
        probationEndTime: '', // 试用期结束时间
        company: {
          id: null,
          name: null,
          code: null
        },
        department: {
          id: null,
          name: null,
          code: null
        },
        companyEmail: '', // 公司邮箱
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
          name: null,
          value: null
        },
        signStatus: {
          name: null,
          value: null,
          id: null
        },
        expirationDate: '', // 到期日期
        endDate: '', // 截止日期
        arriveName: '', // 送达人
        arrivePhone: '', // 送达人联系方式
        signDate: '', // 签字时间
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.dialogProps.visible = true
      this.tabIndex = '1'
      if (this.wfAction == 'create') {
        // 从草稿创建
        if (this.bizData) {
          this.editFormData = {
            ...this.initEditData(),
            ...this.bizData
          }
        }
      } else if (this.wfAction == 'approve') {
        // 重新申请
        let reApply =
          this.task.assignee == this.task.owner &&
          this.task.assignee == this.task.extInfo.applicantId &&
          this.task.taskDefinitionKey == this.task.extInfo.appActId
        if (!reApply) {
          this.action = 'view'
        }
        this.loadModelData(this.task.id)
      } else if (this.wfAction == 'viewBiz') {
        this.action = 'view'
        this.editFormData = {
          ...this.initEditData(),
          ...this.bizData
        }
      } else if (this.wfAction == 'viewTask') {
        this.action = 'view'
        this.loadModelData(this.task.id)
      } else {
        console.err('=============== 业务审批单中，实现的：' + this.wfAction)
      }
    })
  }
}
</script>
