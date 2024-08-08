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
            <el-form-item
              prop="name"
              label="名称"
              :rules="[{ required: true, message: '名称不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.name"
                :maxLength="128"
                placeholder="请输入名称"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="code"
              label="代码"
              :rules="[{ required: true, message: '代码不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.code"
                :maxLength="64"
                placeholder="请输入代码"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="url"
              label="URL"
              :rules="[{ required: true, message: 'URL不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.url"
                :maxLength="128"
                placeholder="请输入URL"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item
              prop="permission"
              label="权限"
              :rules="[{ required: true, message: '权限不能为空', trigger: 'blur' }]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="editFormData.permission"
                :maxLength="32"
                placeholder="请输入权限"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item prop="canPermission" label="可授权" class="dc-el-form-item_Switch">
              <el-switch
                v-model="editFormData.canPermission"
                active-value="1"
                inactive-value="0"
                class="dc-el-switch_Switch"
              ></el-switch>
            </el-form-item>
            <el-form-item prop="isDefault" label="默认权限" class="dc-el-form-item_Switch">
              <el-switch
                v-model="editFormData.isDefault"
                active-value="1"
                inactive-value="0"
                class="dc-el-switch_Switch"
              ></el-switch>
            </el-form-item>
            <el-form-item prop="isLocked" label="禁用" class="dc-el-form-item_Switch">
              <el-switch
                v-model="editFormData.isLocked"
                active-value="1"
                inactive-value="0"
                class="dc-el-switch_Switch"
              ></el-switch>
            </el-form-item>
            <el-form-item prop="remarks" label="备注信息" class="dc-el-form-item_MutilpleInput">
              <el-input
                v-model="editFormData.remarks"
                type="textarea"
                placeholder="请输入备注信息"
                rows="2"
                :maxLength="255"
                class="dc-el-input_MutilpleInput"
              ></el-input>
            </el-form-item>
            <el-form-item prop="properties" label="扩展属性" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.properties"
                :maxLength="1024"
                placeholder="请输入扩展属性"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </el-row>
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
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getResourceById, saveResource } from '@/api/admin/resource'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'resource-form',
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
        title: '资源管理'
      },
      dialogTitle: '资源管理',
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

      saveResource(this.editFormData)
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
        getResourceById(id)
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
        name: '', // 名称
        code: 'This.router.code', // 代码
        url: 'This.router.url', // URL
        permission: '', // 权限
        canPermission: '1', // 可授权
        isDefault: '0', // 默认权限
        isLocked: '0', // 禁用
        remarks: '', // 备注信息
        properties: '' // 扩展属性
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
      this.$on('openAddDialog', function (parent) {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...parent
        }
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
