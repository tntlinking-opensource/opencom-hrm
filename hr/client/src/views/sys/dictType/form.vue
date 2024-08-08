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
          <el-tab-pane label="基本信息" name="editForm" class="dc-el-tab-pane_ElTabPane">
            <el-form
              ref="editForm"
              :model="editFormData"
              label-width="110px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <el-form-item
                  prop="code"
                  label="字典类型编码"
                  :rules="[{ required: true, message: '字典类型编码不能为空', trigger: 'blur' }]"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.code"
                    :maxLength="64"
                    placeholder="请输入字典类型编码"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  prop="name"
                  label="字典类型名"
                  :rules="[{ required: true, message: '字典类型名不能为空', trigger: 'blur' }]"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="editFormData.name"
                    :maxLength="128"
                    placeholder="请输入字典类型名"
                    clearable
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
                <el-form-item
                  prop="isSystem"
                  label="系统级"
                  :rules="[{ required: true, message: '系统级不能为空', trigger: 'change' }]"
                  class="dc-el-form-item_Switch"
                >
                  <el-switch
                    v-model="editFormData.isSystem"
                    active-color="#1EFF00"
                    active-value="1"
                    inactive-value="0"
                    class="dc-el-switch_Switch"
                  ></el-switch>
                </el-form-item>
              </el-row>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="字典项" name="dictItemTable" class="dc-el-tab-pane_ElTabPane">
            <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
              <el-tooltip disabled class="dc-el-tooltip_Button">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  v-on:click="onAddDictItemList"
                  class="dc-el-button_Button"
                ></el-button>
              </el-tooltip>
            </el-row>
            <el-form
              ref="dictItemForm"
              :model="editFormData"
              label-width="100px"
              :disabled="action == 'view'"
              class="dc-el-form_ElEditForm"
            >
              <el-row>
                <ux-grid
                  :data="editFormData.dictItemList"
                  ref="dictItemTable"
                  :currentRow="table118CurrentRow"
                  height="200px"
                  border
                  :edit-config="{ trigger: 'click', mode: 'cell' }"
                  class="dc-ux-grid_EditTable"
                >
                  <ux-table-column
                    field="name"
                    title="名称"
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
                        :prop="'dictItemList.' + rowIndex + '.name'"
                        label-width="0px"
                        :rules="[{ required: true, message: '', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input v-model="row.name" :maxLength="128" clearable class="dc-el-input_SingleInput"></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'dictItemList.' + rowIndex + '.name'"
                          label-width="0px"
                          :rules="[{ required: true, message: '', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.name }}
                        </el-form-item>
                      </span>
                    </template>
                  </ux-table-column>
                  <ux-table-column
                    field="value"
                    title="值"
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
                        :prop="'dictItemList.' + rowIndex + '.value'"
                        label-width="0px"
                        :rules="[{ required: true, message: '', trigger: 'blur' }]"
                        class="dc-el-form-item_SingleInput"
                      >
                        <el-input v-model="row.value" :maxLength="128" clearable class="dc-el-input_SingleInput"></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'dictItemList.' + rowIndex + '.value'"
                          label-width="0px"
                          :rules="[{ required: true, message: '', trigger: 'blur' }]"
                          class="dc-el-form-item_SingleInput"
                        >
                          {{ row.value }}
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
                        :prop="'dictItemList.' + rowIndex + '.remarks'"
                        label-width="0px"
                        class="dc-el-form-item_MutilpleInput"
                      >
                        <el-input
                          v-model="row.remarks"
                          type="textarea"
                          rows="2"
                          :maxLength="255"
                          class="dc-el-input_MutilpleInput"
                        ></el-input>
                      </el-form-item>
                    </template>
                    <template v-slot="{ row, rowIndex }">
                      <span class="my-input-sc">
                        <el-form-item
                          :prop="'dictItemList.' + rowIndex + '.remarks'"
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
                        v-on:click="onDeleteChild(editFormData.dictItemList, scope.rowIndex)"
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

/** 根据用户界面配置import组件 结束 */
import { getDictTypeById, saveDictType } from '@/api/sys/dictType'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'dictType-form',
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
        title: '字典类型'
      },
      dialogTitle: '字典类型',
      // 选项变量
      table118CurrentRow: {}, // 当前行
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

      this.$refs['dictItemForm'].validate((valid) => {
        if (valid) {
        } else {
          this.tabName = 'dictItemTable'
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

      saveDictType(this.editFormData)
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
        getDictTypeById(id)
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
    onDeleteChild(tableData, index) {
      // 根据下标删除子表数据
      tableData.splice(index, 1)
    },
    initEditData(This) {
      return {
        code: '', // 字典类型编码
        name: '', // 字典类型名
        remarks: '', // 备注信息
        isSystem: '0', // 系统级
        dictItemList: []
      }
    },

    onAddDictItemList() {
      this.editFormData.dictItemList.push({
        name: '', // 名称

        value: '', // 值

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

        for (var i = 0; i <= this.editFormData.dictItemList.length - 1; i++) {
          this.editFormData.dictItemList[i].id = null
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
