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
        <el-tabs v-model="tabName" type="border-card" tab-position="top" class="dc-el-tabs_ElTabs">
          <el-tab-pane label="操作日志" name="editForm" class="dc-el-tab-pane_ElTabPane">
            <el-form
              ref="editForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-form-item
                  prop="actionType"
                  label="操作类型"
                  :rules="[{ required: true, message: '操作类型不能为空', trigger: 'change' }]"
                  class="dc-el-form-item_SelectInput"
                >
                  <el-select
                    v-model="editFormData.actionType"
                    :style="{ width: '100%' }"
                    placeholder="请选择操作类型"
                    clearable
                    value-key="value"
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in actionTypeOptions"
                      :key="index"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  prop="objectType"
                  label="对象类型"
                  :rules="[{ required: true, message: '对象类型不能为空', trigger: 'change' }]"
                  class="dc-el-form-item_SelectInput"
                >
                  <el-select
                    v-model="editFormData.objectType"
                    :style="{ width: '100%' }"
                    placeholder="请选择对象类型"
                    clearable
                    value-key="id"
                    filterable
                    class="dc-el-select_SelectInput"
                  >
                    <el-option
                      v-for="(item, index) in objectTypeOptions"
                      :key="index"
                      :label="item.name"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  prop="objectId"
                  label="业务对象"
                  :rules="[{ required: true, message: '业务对象不能为空', trigger: 'blur' }]"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.objectId"
                    :maxLength="21"
                    placeholder="请输入业务对象"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item prop="objectName" label="对象名称" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="editFormData.objectName"
                    :maxLength="128"
                    placeholder="请输入对象名称"
                    clearable
                    autocomplete="new-password"
                    class="dc-el-input_SingleInput"
                  ></el-input>
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
              </el-row>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="回收站" name="actionRecycleTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddActionRecycleList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="actionRecycleForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.actionRecycleList"
                  ref="actionRecycleTable"
                  :currentRow="table122CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="tableName"
                    title="表名"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'actionRecycleList.' + rowIndex + '.tableName'"
                        label-width="0px"
                        :rules="[{ required: true, message: '表名不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.tableName"
                          :maxLength="128"
                          placeholder="请输入表名"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'actionRecycleList.' + rowIndex + '.tableName'"
                          label-width="0px"
                          :rules="[{ required: true, message: '表名不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.tableName }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="objectId"
                    title="业务对象"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      <span style="color: #f56c6c">*</span>
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'actionRecycleList.' + rowIndex + '.objectId'"
                        label-width="0px"
                        :rules="[{ required: true, message: '业务对象不能为空', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.objectId"
                          :maxLength="21"
                          placeholder="请输入业务对象"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'actionRecycleList.' + rowIndex + '.objectId'"
                          label-width="0px"
                          :rules="[{ required: true, message: '业务对象不能为空', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.objectId }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="objectName"
                    title="对象名称"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_SingleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'actionRecycleList.' + rowIndex + '.objectName'"
                        label-width="0px"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input
                          v-model="row.objectName"
                          :maxLength="128"
                          placeholder="请输入对象名称"
                          clearable
                          autocomplete="new-password"
                          class="dc-el-input_SingleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'actionRecycleList.' + rowIndex + '.objectName'"
                          label-width="0px"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.objectName }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="remarks"
                    title="备注信息"
                    tree-node
                    resizable
                    min-width="120px"
                    align="left"
                    header-align="center"
                    show-overflow
                    :edit-render="{ autofocus: '.el-input__inner' }"
                    class="dc-ux-table-column_MutilpleInput"
                  >
                    <template v-slot:header="{ column }">
                      {{ column.title }}
                    </template>
                    <template v-slot:edit="{ row, rowIndex }">
                      <el-form-item
                        :prop="'actionRecycleList.' + rowIndex + '.remarks'"
                        label-width="0px"
                        class="dc-el-form-item_MutilpleInput"
                      >
                        <el-input
                          v-model="row.remarks"
                          type="textarea"
                          placeholder="请输入备注信息"
                          rows="2"
                          :maxLength="255"
                          class="dc-el-input_MutilpleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'actionRecycleList.' + rowIndex + '.remarks'"
                          label-width="0px"
                          class="dc-el-form-item_MutilpleInput"
                        >
                          {{ row.remarks }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    title="操作"
                    tree-node
                    resizable
                    width="160px"
                    min-width="80px"
                    fixed="right"
                    align="left"
                    header-align="center"
                    v-if="action != 'view'"
                    class="dc-ux-table-column_ElTableOptColumn"
                  >
                    <template v-slot:header="scope">
                      <span>操作</span>
                    </template>
                    <template slot-scope="scope">
                      <OperationIcon
                        v-on:click="onDeleteChild(editFormData.actionRecycleList, scope.rowIndex)"
                        type="danger"
                        content="删除"
                        placement="top"
                        icon-name="el-icon-delete"
                        class="dc-OperationIcon_IconButton"
                      ></OperationIcon>
                    </template>
                  </ux-table-column>
                </ux-grid>
              </el-row>
            </el-form>
          </el-tab-pane>
        </el-tabs>
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
import { listDictItemAll } from '@/api/sys/dictItem.js'
import { listDcTableAll } from '@/api/dc/dcTable.js'
/** 根据用户界面配置import组件 结束 */
import { getActionById, saveAction } from '@/api/sys/action'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'action-form',
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
        title: '操作日志'
      },
      dialogTitle: '操作日志',
      // 选项变量
      // 操作类型选项
      actionTypeOptions: [],
      // 对象类型选项
      objectTypeOptions: [],
      table122CurrentRow: {}, // 当前行
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

      this.$refs['actionRecycleForm'].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = 'actionRecycleTable'
          validFlag = false
          return false
        }
      })

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

      saveAction(this.editFormData)
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
        getActionById(id)
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

    listActionTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'dict_type_id',
          queryType: '=',
          value: '5000'
        }
      ])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1290867985774919713')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.actionTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listObjectTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 低代码-业务表dc_table
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1295241911749886027')

      listDcTableAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.objectTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listActionTypeOptions()

      this.listObjectTypeOptions()
    },
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        actionType: {
          name: null,
          value: null
        },
        objectType: {
          id: null,
          name: null
        },
        objectId: '', // 业务对象
        objectName: '', // 对象名称
        remarks: '', // 备注信息
        actionRecycleList:[]
      }
    },

    onAddActionRecycleList() {
      this.editFormData.actionRecycleList.push({
        tableName: '', // 表名

        objectId: '', // 业务对象

        objectName: '', // 对象名称

        remarks: '' // 备注信息
      })
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
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
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

        for (var i = 0; i <= this.editFormData.actionRecycleList.length - 1; i++) {
          this.editFormData.actionRecycleList[i].id = null
        }

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
