<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="70%"
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
          label-width="110px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item
                prop="department.id"
                label="部门"
                :rules="[{ required: true, message: '部门不能为空', trigger: 'change' }]"
                class="dc-el-form-item_CascaderInput"
              >
                <el-cascader
                  ref="cascader106"
                  :options="departmentOptions"
                  v-model="editFormData.department.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择部门"
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
                label="用户名"
                :rules="[{ required: true, message: '用户名不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.name"
                  :maxLength="128"
                  placeholder="请输入用户名"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="loginName"
                label="登录名"
                :rules="[{ required: true, message: '登录名不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.loginName"
                  :maxLength="10"
                  placeholder="请输入登录名"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="loginPasswordUpdate" label="修改密码" v-show="action == 'edit'" class="dc-el-form-item_Switch">
                <el-switch
                  v-model="editFormData.loginPasswordUpdate"
                  active-value
                  :inactive-value="false"
                  class="dc-el-switch_Switch"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="loginPassword"
                label="密码"
                v-if="action != 'view' && editFormData.loginPasswordUpdate == true"
                :rules="[{ required: true, message: '密码不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.loginPassword"
                  :maxLength="-1"
                  placeholder="请输入密码"
                  clearable
                  show-password
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="loginPasswordConfirm"
                label="确认密码"
                v-if="action != 'view' && editFormData.loginPasswordUpdate == true"
                :rules="[
                  { required: true, message: '确认密码不能为空', trigger: 'blur' },
                  { pattern: new RegExp(`^${editFormData.loginPassword}$`), message: '两次输入密码不一致!', trigger: 'blur' }
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.loginPasswordConfirm"
                  :maxLength="-1"
                  placeholder="请输入确认密码"
                  clearable
                  show-password
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="isLocked" label="禁用" class="dc-el-form-item_Switch">
                <el-switch
                  v-model="editFormData.isLocked"
                  active-value="1"
                  inactive-value="0"
                  class="dc-el-switch_Switch"
                ></el-switch>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="phone"
                label="手机号"
                :rules="[
                  { required: true, message: '手机号不能为空', trigger: 'blur' },
                  {
                    pattern: new RegExp(/^1[3|4|5|6|7|8|9][0-9]\d{8}$/),
                    message: '手机号码格式如:138xxxx8888',
                    trigger: 'blur'
                  }
                ]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.phone"
                  :maxLength="20"
                  placeholder="请输入手机号"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="email"
                label="邮箱"
                :rules="[{ required: true, message: '邮箱不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.email"
                  :maxLength="36"
                  placeholder="请输入邮箱"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="empolyno"
                label="工号"
                :rules="[{ required: true, message: '工号不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.empolyno"
                  :maxLength="100"
                  placeholder="请输入工号"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="ranks.id"
                label="职级"
                :rules="[{ required: true, message: '职级不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.ranks"
                  :style="{ width: '100%' }"
                  placeholder="请选择职级"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option v-for="(item, index) in rankOptions" :key="index" :label="item.name" :value="item"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="post.id"
                label="技术职务"
                :rules="[{ required: true, message: '技术职务不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.post"
                  :style="{ width: '100%' }"
                  placeholder="请选择技术职务"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option v-for="(item, index) in postOptions" :key="index" :label="item.name" :value="item"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="24">
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
            </el-col>
            <el-col :span="24">
              <el-form-item prop="userRoleList" label="用户角色" class="dc-el-form-item_ObjectSelect">
                <el-select
                  v-model="editFormData.userRoleList"
                  :style="{ width: '100%' }"
                  placeholder="请选择角色标识"
                  clearable
                  value-key="role.id"
                  filterable
                  multiple
                  class="dc-el-select_ObjectSelect"
                >
                  <el-option
                    v-for="(item, index) in userRoleOptions"
                    :key="index"
                    :label="item.name"
                    :value="{ id: '', role: item }"
                  ></el-option>
                </el-select>
              </el-form-item>
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
import { treeDepartment } from '@/api/org/department.js'
import { listRoleAll } from '@/api/admin/role.js'
/** 根据用户界面配置import组件 结束 */
import { getUserById, saveUser } from '@/api/admin/user'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'user-form',
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
        title: '用户'
      },
      dialogTitle: '用户',
      // 选项变量
      // 部门选项
      departmentOptions: [],
      // 职级选项
      rankOptions: [],
      // 技术职务选项
      postOptions: [],
      tabName: 'editForm', // tab标签页
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
      let validFlag = true

      this.$refs['editForm'].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = 'editForm'
          validFlag = false
          return false
        }
      })
      if (validFlag) {
        this.doSave()
      }
    },
    doSave() {
      this.setLoad()

      saveUser(this.editFormData)
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
        getUserById(id)
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

    listDepartmentOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'company_id',
          queryType: '=',
          value: this.editFormData.company.id
        }
      ])

      // 数据权限: 部门org_department
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1290602453716598804')

      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    listRankOptions() {
      let search_List = {
        params: [
          {
            columnName: 'dict_type_id',
            queryType: '=',
            value: '1526849449118580737'
          }
        ]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.rankOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listPostOptions() {
      let search_List = {
        params: [
          {
            columnName: 'dict_type_id',
            queryType: '=',
            value: '1526849449118580752'
          }
        ]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.postOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    listUserRoleOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'company_id',
          queryType: '=',
          value: this.editFormData.company.id
        }
      ])
      listRoleAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.userRoleOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listDepartmentOptions()
      this.listRankOptions()

      this.listPostOptions()
      this.listUserRoleOptions()
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        // 部门
        department: {
          id: null,
          name: null,
          code: null,
          ids: null
        },
        name: '', // 用户名
        loginName: '', // 登录名
        loginPassword: '', // 密码
        loginPassword: '', // 确认密码
        isLocked: '0', // 禁用
        phone: '', // 手机号
        email: '', // 邮箱
        empolyno: '', // 工号
        rank: {
          id: null,
          name: null,
          value: null
        },
        post: {
          id: null,
          name: null,
          value: null
        },
        remarks: '', // 备注信息
        userRoleList: [], // 用户角色
        loginPasswordUpdate: this.dialogProps && this.action == 'add' ? true : false,        // 是否修改密码
      }
    }
  },
  watch: {
    tabName(val, oldVal) {
      this.$nextTick(() => {
        if (this.$refs[val] && this.$refs[val].doLayout) {
          this.$refs[val].doLayout()
        }
      })
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
        this.tabName = 'editForm'
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
        this.tabName = 'editForm'
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
        this.tabName = 'editForm'
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
        this.tabName = 'editForm'
        this.editFormData.id = null //把id设置为空，添加一个新的

        for (var i = 0; i <= this.editFormData.userRoleList.length - 1; i++) {
          this.editFormData.userRoleList[i].id = null
        }

        //TODO 修改加密字段
        this.editFormData.loginPasswordUpdate = true // 允许修改密码

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
<style>
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
</style>
