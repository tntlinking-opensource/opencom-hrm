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
            <el-col :span="12">
              <el-form-item prop="parent.id" label="父级" class="dc-el-form-item_CascaderInput">
                <el-cascader
                  ref="parentCascader"
                  :options="parentOptions"
                  v-model="editFormData.parent.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择父级"
                  :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                  clearable
                  filterable
                  separator="/"
                  class="dc-el-cascader_CascaderInput"
                ></el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="12">
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
            </el-col>
            <el-col :span="12">
              <el-form-item prop="code" label="代码" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.code"
                  :maxLength="64"
                  placeholder="请输入编号"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="url" label="URL" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.url"
                  :maxLength="128"
                  placeholder="请输入URL"
                  clearable
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="canPermission" label="可授权" class="dc-el-form-item_Switch">
                <el-switch
                  v-model="editFormData.canPermission"
                  active-color="#13CC22"
                  active-value="1"
                  inactive-value="0"
                  class="dc-el-switch_Switch"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="isLocked" label="禁用" class="dc-el-form-item_Switch">
                <el-switch
                  v-model="editFormData.isLocked"
                  active-color="#36C619"
                  active-value="1"
                  inactive-value="0"
                  class="dc-el-switch_Switch"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="sort"
                label="排序"
                :rules="[{ required: true, message: '排序不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.sort"
                  :maxLength="21"
                  placeholder="请输入排序"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                prop="properties"
                label="扩展属性"
                :rules="[{ required: true, message: '扩展属性不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_MutilpleInput"
              >
                <el-input
                  v-model="editFormData.properties"
                  type="textarea"
                  placeholder="请输入扩展属性"
                  rows="2"
                  :maxLength="-1"
                  class="dc-el-input_MutilpleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item
                prop="systemCode"
                label="系统标识"
                :rules="[{ required: true, message: '系统标识不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.systemCode"
                  :style="{ width: '100%' }"
                  placeholder="请选择系统标识"
                  clearable
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in selectOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <upload-file
                title="图片地址"
                :action="action"
                :objectId="editFormData.iconId"
                v-model="editFormData.iconIdFile"
                class="dc-upload-file_ElUploadFile"
              ></upload-file>
            </el-col>
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
import { treeRouter } from '@/api/admin/router.js'
import uploadFile from '@/views/components/uploadFile'
/** 根据用户界面配置import组件 结束 */
import { getRouterById, saveRouter } from '@/api/admin/router'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'router-form',
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
        title: '路由'
      },
      dialogTitle: '路由',
      // 选项变量
      // 父级选项
      parentOptions: [],
      // 系统标识选项
      selectOptions: [
        {
          label: '后台系统',
          value: '0'
        },
        {
          label: '企业微信',
          value: '1'
        }
      ],
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
      saveRouter(formData)
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

      for (let idx in editFormData.iconIdFile.uploads) {
        formData.append('iconIdUploads', editFormData.iconIdFile.uploads[idx].raw)
      }
      deletes = deletes.concat(editFormData.iconIdFile.deletes)

      formData.append('entity', JSON.stringify(editFormData))
      formData.append('deleteIds', JSON.stringify(deletes))
      return formData
    },

    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getRouterById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              if (validatenull(form.parent)) {
                form.parent = {
                  id: null
                }
              }
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

    listParentOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 路由sys_router
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1289036439920697347')

      treeRouter(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.parentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listParentOptions()
    },
    onParentChange() {
      let nodes = this.$refs['parentCascader'].getCheckedNodes()
      if (nodes.length > 0) {
        if (nodes[0] && nodes[0].data && nodes[0].data.children) {
          this.editFormData.sort = (nodes[0].data.children.length + 1) * 10000
        } else if (nodes[0]) {
          this.editFormData.sort = 10000
        } else {
          this.editFormData.sort = (this.parentOptions.length + 1) * 10000
        }
      } else {
        this.editFormData.sort = (this.parentOptions.length + 1) * 10000
      }
    },
    initEditData(This) {
      return {
        // 父级
        parent: {
          id: validatenull(This) || validatenull(This.id) ? null : This.id,
          name: validatenull(This) || validatenull(This.name) ? null : This.name,
          code: validatenull(This) || validatenull(This.code) ? null : This.code,
          url: validatenull(This) || validatenull(This.url) ? null : This.url,
          parentId: validatenull(This) || validatenull(This.parentId) ? null : This.parentId,
          ids: validatenull(This) || validatenull(This.ids) ? null : This.ids,
          sort: validatenull(This) || validatenull(This.sort) ? null : This.sort,
          canPermission: validatenull(This) || validatenull(This.canPermission) ? null : This.canPermission,
          isLocked: validatenull(This) || validatenull(This.isLocked) ? null : This.isLocked,
          properties: validatenull(This) || validatenull(This.properties) ? null : This.properties,
          systemCode: validatenull(This) || validatenull(This.systemCode) ? null : This.systemCode,
          iconId: validatenull(This) || validatenull(This.iconId) ? null : This.iconId
        },
        name: '', // 名称
        code: '', // 代码
        url: '', // URL
        canPermission: '1', // 可授权
        isLocked: '0', // 禁用
        sort:
          This && This.children
            ? (This.children.length + 1) * 10000
            : this.parentOptions
            ? (this.parentOptions.length + 1) * 10000
            : 10000,
        properties: '', // 扩展属性
        systemCode: null, // 系统标识
        iconId: null,
        iconIdFile: {
          deletes: [], // 待删除（已上传）的文件列表
          uploads: [] // 待上传的文件列表
        }
      }
    }
  },
  watch: {
    parentOptions(newVal, oldVal) {
      if (newVal != oldVal && this.action == 'add') {
        this.$nextTick(() => {
          this.onParentChange()
        })
      }
    }
  },
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
        this.editFormData = this.initEditData(parent)
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

        this.editFormData.iconId = null

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
