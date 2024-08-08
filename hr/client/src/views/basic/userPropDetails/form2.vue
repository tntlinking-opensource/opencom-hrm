<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="50%"
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
            <el-form-item prop="attribute2" label="资料编号：" :rules="[{ required: true, message: '资料编号：不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.attribute2"
                :maxLength="-1"
                placeholder="请输入资料编号"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <el-form-item prop="attribute0" label="资料名称："  class="dc-el-form-item_SingleInput">
              <el-input
                v-model="editFormData.attribute0"
                :maxLength="-1"
                placeholder="请输入资料名称"
                clearable
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
            <upload-file
              v-if="dialogProps.visible"
              title="请上传文件"
              :action="action"
              :limit="1"
              :objectId="editFormData.attribute1"
              :fileFormats="['jpg','png', 'doc', 'docx', 'pdf']"
              :fileSizes="100"
              v-model="editFormData.attribute0File"
              class="dc-upload-file_ElUploadFile"
            ></upload-file>
            <el-form-item prop="used" label="状态" class="dc-el-form-item_Switch">
              <el-switch
                v-model="editFormData.used"
                active-value="1"
                inactive-value="0"
                class="dc-el-switch_Switch"
              ></el-switch>
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
import uploadFile from '@/views/components/uploadFile'
/** 根据用户界面配置import组件 结束 */
import { getUserPropDetailsById, saveForm } from '@/api/basic/userPropDetails'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'userPropDetails-form',
  components: {
    /** 根据用户界面配置组件 开始 */
    uploadFile,
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
        title: '入职资料包配置'
      },
      dialogTitle: '入职资料包配置',
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

      let formData = this.createFormData(this.editFormData)
      saveForm(formData)
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
    

    createFormData(editFormData) {
      let formData = new FormData()
      let deletes = []

      for (let idx in editFormData.attribute0File.uploads) {
        formData.append('attribute0Uploads', editFormData.attribute0File.uploads[idx].raw)
      }
      deletes = deletes.concat(editFormData.attribute0File.deletes)

      formData.append('entity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getUserPropDetailsById(id)
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
        attribute0: null,
        attribute0File: {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        },
        used: '1' // 状态
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

        this.editFormData.attribute0 = null

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
