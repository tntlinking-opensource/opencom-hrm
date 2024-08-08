<template>
  <div>
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
            label-width="150px"
            :disabled="action == 'view'"
            class="dc-el-form_ElEditForm"
          >
            <el-row>
              <el-form-item
                prop="name"
                label="任务名称"
                :rules="[{ required: true, message: '任务名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.name"
                  :maxLength="128"
                  placeholder="请输入任务名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
              <el-form-item
                prop="javaClass"
                label="定时任务类全路径"
                :rules="[{ required: true, message: '定时任务类全路径不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.javaClass"
                  :maxLength="150"
                  placeholder="请输入定时任务类全路径"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
              <el-form-item
                prop="javaMethod"
                label="定时任务方法名称"
                :rules="[{ required: true, message: '定时任务方法名称不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.javaMethod"
                  :maxLength="30"
                  placeholder="请输入定时任务方法名称"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
              <el-form-item prop="params" label="定时任务方法入参" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.params"
                  :maxLength="100"
                  placeholder="请输入定时任务方法入参"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
              <el-form-item
                prop="cron"
                label="任务表达式"
                :rules="[{ required: true, message: '任务表达式不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.cron"
                  :maxLength="128"
                  placeholder="请输入任务表达式"
                  clearable
                  class="dc-el-input_SingleInput"
                >
                  <template slot="append">
                    <el-button type="primary" @click="handleShowCron">
                      生成表达式
                      <i class="el-icon-time"></i>
                    </el-button>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item prop="status" label="任务状态" class="dc-el-form-item_Switch">
                <el-switch
                  v-model="editFormData.status"
                  active-color="#4DFF00"
                  active-value="1"
                  inactive-value="0"
                  class="dc-el-switch_Switch"
                ></el-switch>
              </el-form-item>
              <el-form-item prop="isLock" label="是否禁用" class="dc-el-form-item_Switch">
                <el-switch
                  v-model="editFormData.isLock"
                  active-color="#63C111"
                  active-value="1"
                  inactive-value="0"
                  class="dc-el-switch_Switch"
                ></el-switch>
              </el-form-item>
              <el-form-item prop="remarks" label="备注" class="dc-el-form-item_MutilpleInput">
                <el-input
                  v-model="editFormData.remarks"
                  type="textarea"
                  placeholder="请输入备注"
                  rows="2"
                  :maxLength="255"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-row>
          </el-form>
        </el-row>
        <span slot="footer" class="dialog-footer">
          <el-dropdown v-if='action == "edit"' split-button type='warning' v-on:click="startTask('editForm')">
              启动任务
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button  type='text' :plain='true' @click='restartTask("editForm")'>保存并启动</el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button v-on:click="endTask('editForm')" type="primary" v-show="action == 'edit'">暂停</el-button>

          <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
          <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
          <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
        </span>
      </el-dialog>
    </el-row>

    <div>
      <el-dialog title="Cron表达式生成器" width="80%" :visible.sync="openCron" append-to-body destroy-on-close class="scrollbar">
        <crontab @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
      </el-dialog>
    </div>

  </div>

</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getScheduleJobById, saveScheduleJob, startTask, restartTask, endTask } from '@/api/schedule/scheduleJob'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import Crontab from '@/components/Crontab'

export default {
  extends: BaseUI,
  name: 'scheduleJob-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon,
    Crontab
  },
  data() {
    return {
      openCron: false,
      // 传入的表达式
      expression: '',
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '定时任务'
      },
      dialogTitle: '定时任务',
      // 选项变量

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
    //取消任务
    endTask(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.editFormData.status == '已暂停') {
            this.$message.warning('当前任务暂停状态不可重复暂停')
            return false
          }
          this.setLoad()
          endTask(this.editFormData)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.dialogProps.visible = false
                this.$emit('save-finished')
                this.$message.success(responseData.data)
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        } else {
          return false
        }
      })
    },

    //保存并重启任务
    restartTask(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.setLoad()
          saveScheduleJob(this.editFormData)
            .then((responseData) => {
              if (responseData.code == 100) {
                //任务重启
                restartTask(this.editFormData)
                  .then((responseData) => {
                    if (responseData.code == 100) {
                      this.dialogProps.visible = false
                      this.$emit('save-finished')
                      this.$message.success(responseData.data)
                    } else {
                      this.showMessage(responseData)
                    }
                    this.resetLoad()
                  })
                  .catch((error) => {
                    this.outputError(error)
                  })
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        } else {
          return false
        }
      })
    },

    //启动定时任务
    startTask(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.editFormData.status == '执行中') {
            this.$message.warning('当前任务执行状态不可重复启动')
            return false
          }
          this.setLoad()
          startTask(this.editFormData)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.dialogProps.visible = false
                this.$emit('save-finished')
                this.$message.success(responseData.data)
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        } else {
          return false
        }
      })
    },

    /** 确定后回传值 */
    crontabFill(value) {
      this.editFormData.cron = value
    },

    /** cron表达式按钮操作 */
    handleShowCron() {
      this.expression = this.editFormData.cron
      this.openCron = true
    },

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

      saveScheduleJob(this.editFormData)
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
        getScheduleJobById(id)
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
      // 初始化自定义类型选择框选项变量
    },
    initEditData(This) {
      return {
        name: '', // 任务名称
        javaClass: '', // 定时任务类全路径
        javaMethod: '', // 定时任务方法名称
        params: '', // 定时任务方法入参
        cron: '', // 任务表达式
        status: '0', // 任务状态
        isLock: '0', // 是否禁用
        remarks: '' // 备注
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
