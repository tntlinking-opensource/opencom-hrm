<template>
  <van-row class="dm-container">
    <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" width="70%" :fullscreen="false"
      :title="dialogProps.title" :visible.sync="dialogProps.visible" :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog">
      <el-row>

        <el-form ref="editForm" :model="editFormData" label-width="100px" :disabled="action == 'view'"
          class="dc-el-form_ElEditForm">
          <el-row>
            <el-col :span="6"> <el-form-item prop="taxName" label="税率表名称" :rules="[
              { required: true, message: '税率表名称不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.taxName" :maxLength="255" placeholder="请输入税率表名称" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item></el-col>

            <el-col :span="6"> <el-form-item prop="expDeduction" :rules="[
              { required: true, message: '费用扣除不能为空', trigger: 'blur' }
            ]" label="费用扣除" class="dc-el-form-item_SingleInput">
                <number-input v-model="editFormData.expDeduction" :precision="2" :maxLength="18" :min="0"
                  placeholder="请输入费用扣除" clearable autocomplete="new-password"
                  class="dc-el-input_SingleInput"></number-input>
              </el-form-item></el-col>
            <el-col :span="6"> <el-form-item prop="nationalTariff" label="固定税率" class="dc-el-form-item_SingleInput"
                :rules="[
                  { required: true, message: '固定税率不能为空', trigger: 'blur' }
                ]">
                <number-input v-model="editFormData.nationalTariff" :maxLength="18" placeholder="请输入固定税率" clearable
                  :min="0" autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
              </el-form-item></el-col>
          </el-row>
        </el-form>

        <el-row v-if="action != 'view'" gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-plus" v-on:click="onAddTaxDetailsInfoList"
              style="margin-bottom: 10px;" class="dc-el-button_Button"></el-button>
          </el-tooltip>
        </el-row>
        <el-form ref="taxDetailsInfoForm" :model="editFormData" label-width="100px" :disabled="action == 'view'"
          class="dc-el-form_ElEditForm">
          <el-row>
            <ux-grid :data="editFormData.taxDetailsInfoList" ref="taxDetailsInfoTable" :currentRow="table124CurrentRow"
              height="300px" border :edit-config="{ trigger: 'click', mode: 'cell' }" class="dc-ux-grid_EditTable">
              <ux-table-column field="rateLevel" title="速率级次" tree-node resizable min-width="60px" align="left"
                header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
                class="dc-ux-table-column_SingleInput">
                <template v-slot:header="{ column }">
                  <span style="color: #f56c6c">*</span>
                  {{ column.title }}
                </template>

                <template v-slot:edit="{ row, rowIndex }">
                  <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.rateLevel'" label-width="0px"
                    class="dc-el-form-item_SingleInput">
                    <number-input v-model="row.rateLevel" :maxLength="18" placeholder="请输入速率级次" clearable :min="0"
                      autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
                  </el-form-item>
                </template>
                <template v-slot="{ row, rowIndex }">
                  <span class="my-input-sc">
                    <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.rateLevel'" label-width="0px"
                      class="dc-el-form-item_SingleInput">
                      {{ row.rateLevel }}
                    </el-form-item>
                  </span>
                </template>
              </ux-table-column>
              <ux-table-column field="amountDown" title="纳税级距下限" tree-node resizable min-width="120px" align="left"
                header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
                class="dc-ux-table-column_SingleInput">
                <template v-slot:header="{ column }">
                  <span style="color: #f56c6c">*</span>
                  {{ column.title }}
                </template>
                <template v-slot:edit="{ row, rowIndex }">
                  <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.amountDown'" label-width="0px"
                    class="dc-el-form-item_SingleInput">
                    <number-input v-model="row.amountDown" :precision="2" :min="0" :maxLength="18" placeholder="请输入纳税级距下限"
                      clearable autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
                  </el-form-item>
                </template>
                <template v-slot="{ row, rowIndex }">
                  <span class="my-input-sc">
                    <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.amountDown'" label-width="0px"
                      class="dc-el-form-item_SingleInput">
                      {{ row.amountDown | getFloat }}
                    </el-form-item>
                  </span>
                </template>
              </ux-table-column>
              <ux-table-column field="amountUp" title="纳税级距上限" tree-node resizable min-width="120px" align="left"
                header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
                class="dc-ux-table-column_SingleInput">
                <template v-slot:header="{ column }">
                  <span style="color: #f56c6c">*</span>
                  {{ column.title }}
                </template>
                <template v-slot:edit="{ row, rowIndex }">
                  <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.amountUp'" label-width="0px"
                    class="dc-el-form-item_SingleInput">
                    <number-input v-model="row.amountUp" :precision="2" :maxLength="18" placeholder="请输入纳税级距上限" :min="0"
                      clearable autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
                  </el-form-item>
                </template>
                <template v-slot="{ row, rowIndex }">
                  <span class="my-input-sc">
                    <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.amountUp'" label-width="0px"
                      class="dc-el-form-item_SingleInput">
                      {{ row.amountUp | getFloat }}
                    </el-form-item>
                  </span>
                </template>
              </ux-table-column>
              <ux-table-column field="rate" title="税率(%)" tree-node resizable min-width="120px" align="left"
                header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
                class="dc-ux-table-column_SingleInput">
                <template v-slot:header="{ column }">
                  <span style="color: #f56c6c">*</span>
                  {{ column.title }}
                </template>
                <template v-slot:edit="{ row, rowIndex }">
                  <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.rate'" label-width="0px"
                    class="dc-el-form-item_SingleInput">
                    <number-input v-model="row.rate" :maxLength="2" placeholder="请输入税率(%)" clearable :min="0"
                      autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
                  </el-form-item>
                </template>
                <template v-slot="{ row, rowIndex }">
                  <span class="my-input-sc">
                    <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.rate'" label-width="0px"
                      class="dc-el-form-item_SingleInput">
                      {{ row.rate }}
                    </el-form-item>
                  </span>
                </template>
              </ux-table-column>
              <ux-table-column field="speedDeductAmount" title="速算扣除数" tree-node resizable min-width="120px" align="left"
                header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
                class="dc-ux-table-column_SingleInput">
                <template v-slot:header="{ column }">
                  <span style="color: #f56c6c">*</span>
                  {{ column.title }}
                </template>
                <template v-slot:edit="{ row, rowIndex }">
                  <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.speedDeductAmount'" label-width="0px"
                    class="dc-el-form-item_SingleInput">
                    <number-input v-model="row.speedDeductAmount" :precision="2" :maxLength="18" placeholder="请输入数算扣除数"
                      clearable :min="0" autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
                  </el-form-item>
                </template>
                <template v-slot="{ row, rowIndex }">
                  <span class="my-input-sc">
                    <el-form-item :prop="'taxDetailsInfoList.' + rowIndex + '.speedDeductAmount'" label-width="0px"
                      class="dc-el-form-item_SingleInput">
                      {{ row.speedDeductAmount | getFloat }}
                    </el-form-item>
                  </span>
                </template>
              </ux-table-column>

              <ux-table-column title="操作" tree-node resizable width="100px" fixed="right" align="center"
                header-align="center" v-if="action != 'view'" class="dc-ux-table-column_ElTableOptColumn">
                <template v-slot:header="scope">
                  <span>操作</span>
                </template>
                <template slot-scope="scope">
                  <OperationIcon v-on:click="onDeleteChild(editFormData.taxDetailsInfoList, scope.rowIndex)" type="danger"
                    content="删除" placement="top" icon-name="el-icon-delete" class="dc-OperationIcon_IconButton">
                  </OperationIcon>
                </template>
              </ux-table-column>
            </ux-grid>
          </el-row>
        </el-form>
        <el-pagination v-if="action === 'view'" :total="tableDataTotal" :page-size="search.limit" background
          :current-page="tableDataPage" :page-sizes="[10, 20, 30, 40, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper" v-on:size-change="onSizeChange"
          v-on:current-change="onCurrentChange" class="dc-el-pagination_ElPagination"></el-pagination>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </van-row>
</template>
<script>

/** 根据用户界面配置import组件 结束 */
import { getTaxTypeInfoById, saveTaxTypeInfo } from '@/api/tax/taxTypeInfo'
import { listTaxDetailsInfoPage } from '@/api/tax/taxDetailsInfo'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'taxTypeInfo-form',
  components: {
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: ''
      },
      dialogTitle: '工资薪金税率表',
      // 选项变量
      table124CurrentRow: {}, // 当前行
      tabName: 'editForm', // tab标签页
      // 窗口操作类型 view/edit/add
      action: '',
      tableForm: {},
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      rowId: '',
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
    }
  },
  filters: {
    getFloat(val) {
      if (val) {
        return val.toFixed(2)
      }
      if (val === 0) {
        return '0.00'
      }
      return null
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        }
      })

    },
    doSave() {

      if (this.editFormData.taxDetailsInfoList.length > 0) {
        let arr = JSON.parse(JSON.stringify(this.editFormData.taxDetailsInfoList))
        let isEmpty = false, isValid = true
        arr.forEach(item => {
          if (!item.rateLevel || !item.rate) {
            isEmpty = true
          }
          if ((!item.speedDeductAmount && item.speedDeductAmount !== 0) || (!item.amountDown && item.amountDown !== 0) || (!item.amountUp && item.amountUp !== 0)) {
            isValid = false
          }
        })

        if (!isEmpty && isValid) {
          this.setLoad()
          saveTaxTypeInfo(this.editFormData)
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
        } else {
          if (isEmpty) {
            this.$message({
              type: 'warning',
              message: '不是数字或精度有误！'
            })
          } else if (!isValid) {
            this.$message({
              type: 'warning',
              message: '请填写速算扣除数/纳税级距上限/纳税级距下限！'
            })
          }
        }
      } else {
        this.$message({
          type: 'warning',
          message: '请填写税率表详细信息！'
        })
      }
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getFormList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getFormList()
    },
    getFormList() {
      this.setLoad()

      return new Promise((resolve) => {
        this.search.params.push(
          {
            "columnName": "tax_type_info_id",
            "queryType": "=",
            "value": this.rowId
          },
        )
        listTaxDetailsInfoPage(this.search)
          .then((responseData) => {
            let form = this.tableForm
            if (responseData.code == 100) {
              form.taxDetailsInfoList = responseData.data.rows
              this.tableDataTotal = responseData.data.total
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
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getTaxTypeInfoById(id)
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
    onDeleteChild(tableData, index) {
      this.$confirm('确定删除所选择的数据记录？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 根据下标删除子表数据
        tableData.splice(index, 1)
      })

    },
    initEditData(This) {
      return {
        taxName: '', // 税率表名称
        taxType: '1', // 类型
        nationalTariff: '', // 固定税率
        expDeduction: '', // 费用扣除
        monthAvg: '', // 月平均金额
        monthTaxIncome: '', // 月纳税所得额
        deductRate: '', // 扣除费率
        taxDetailsInfoList: []
      }
    },

    onAddTaxDetailsInfoList() {
      this.editFormData.taxDetailsInfoList.push({
        rateLevel: '', // 速率级次
        amountDown: '', // 纳税级距下限
        amountUp: '', // 纳税级距上限
        rate: '', // 税率
        speedDeductAmount: '', // 数算扣除数
        monthTaxAmount: '', // 月纳税所得额
        freeTaxAmount: '' // 免税所得额
      })
    }
  },

  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id, row) {
        this.action = 'view'
        this.tableForm = row
        this.rowId = id
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.search = {
          params: [],
          offset: 0,
          limit: 10,
          columnName: '', // 排序字段名
          order: '' // 排序
        },
          this.editFormData = {
            ...this.initEditData(),
            ...(await this.getFormList())
          }
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }

        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
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
