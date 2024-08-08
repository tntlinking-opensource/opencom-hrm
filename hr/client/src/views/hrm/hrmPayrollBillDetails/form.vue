<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="30%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="20">
              <el-form-item prop="empolyno" label="密码" :rules="[{ required: true, message: '密码不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.empolyno"
                  :maxLength="100"
                  placeholder="请输入登录密码"
                  clearable
                  show-password
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="checkPassword" type="primary" v-show="action != 'view'">确定</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { listHrmSalaryUploadSummaryAll } from '@/api/hrm/hrmSalaryUploadSummary.js'
import { listUserAll } from '@/api/admin/user.js'
/** 根据用户界面配置import组件 结束 */
import { getHrmPayrollBillDetailsById, checkPassword,saveHrmPayrollBillDetails } from '@/api/hrm/hrmPayrollBillDetails'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'hrmPayrollBillDetails-form',
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
        title: '薪资查看'
      },
      dialogTitle: '薪资查看',
      // 选项变量
      // 汇总表id选项
      summaryOptions: [],
      // 用户选项
      userOptions: [],
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
    checkPassword() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          checkPassword(currentUser.id,this.editFormData.empolyno).then(resp => {
            if(resp.code == 100){
                if(resp.data){
                  this.dialogProps.visible = false
                  this.$emit('save-finished')
                }else{
                  this.showMessage({
                    type: 'waring',
                    msg: '密码错误!'
                  })
                }
            }
            this.resetLoad()
          })
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()

      saveHrmPayrollBillDetails(this.editFormData)
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
        getHrmPayrollBillDetailsById(id)
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

    listSummaryOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 薪资条账单汇总表hrm_salary_upload_summary
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943434047')

      listHrmSalaryUploadSummaryAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.summaryOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listUserOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943433829')

      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.userOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listSummaryOptions()

      this.listUserOptions()
    },
    initEditData(This) {
      return {
        summary: {
          id: null,
          name: null
        },
        salaryYearAndMonth: '', // 账单年月
        empolyno: '', // 工号
        user: {
          id: null,
          name: null,
          departmentId: null,
          companyId: null,
          empolyno: null,
          loginPassword: null
        },
        name: '', // 姓名
        deptName: '', // 部门名称
        totalSalary: null, // 全额工资
        totalBonus: null, // 奖金合计
        totalAllowanceAndDeduction: null, // 补扣款合计
        personalSocialInsurance: null, // 社保小计(个人)
        personalAccumulationFund: null, // 公积金小计(个人)
        salaryNeedTax: null, // 计税工资
        personalTax: null, // 个人税款
        totalAfterTax: null, // 税后项合计
        remark: '', // 补充信息
        netSalary2: null, // 实发工资2
        remark2: '', // 补充信息2
        netSalary: null, // 实发工资
        tenantId: '', // tenant_id
        stateType: '', // 是否启用：0-启用，1-禁用
        creator: '', // 创建人工号
        updator: '', // 更新人工号
        salaryTax: null, // 所得税
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })
      this.$on('openCopyDialog', async function (id) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.editFormData.id = null //把id设置为空，添加一个新的

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
