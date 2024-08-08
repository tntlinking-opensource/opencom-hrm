<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      :confirmOptions="confirmOptions"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <el-dialog title="导入" :visible.sync="dialogVisible" width="30%">
      <div style="display: flex;margin-bottom: 20px;"><span>报税年月：</span>
        <el-date-picker v-model="taxYear" type="month" value-format='yyyyMM'></el-date-picker>
      </div>
      <div style="display: flex;margin-bottom: 20px;"><span>账单年月：</span>
        <el-date-picker v-model="billYear" type="month" value-format='yyyyMM'></el-date-picker>
      </div>
      <div style="display: flex;"><span>选择文件：</span>
        <el-button @click="importSalary" class="dc-el-button_Button" type="primary" icon="el-icon-upload2">上传
        </el-button>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="billYearMonth" label="账单年月：" class="dc-el-form-item_SelectInput">
            <el-date-picker v-model="queryFormData.billYearMonth" :style="{ width: '100%' }" placeholder="请选择"
                            type="month" format="yyyyMM" value-format="yyyyMM"
                            class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="saBatchCode" label="批次号：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.saBatchCode"
              :maxLength="100"
              placeholder="请输入薪资批次号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="auditState" label="账单确认：" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.auditState"
              :style="{ width: '100%' }"
              placeholder="请选择账单状态：已确认；未确认"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in auditStateOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="2">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-search" v-on:click="onSearch" class="dc-el-button_Button">
              查询
            </el-button>
          </el-tooltip>
        </el-col>
        <el-col :span="2">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-refresh" v-on:click="resetSalary" class="dc-el-button_Button">重置
            </el-button>
          </el-tooltip>
        </el-col>
        <el-col :span="24">
          <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
          </el-tooltip>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-upload2" v-on:click="uploadSalary" class="dc-el-button_Button">导入
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="exportSalaryData" class="dc-el-button_Button">导出
        </el-button>
      </el-tooltip>
      <!--      <el-tooltip disabled class="dc-el-tooltip_Button">-->
      <!--        <el-button type="primary" icon="el-icon-edit-outline" v-on:click="editSalary()" class="dc-el-button_Button">-->
      <!--          编辑明细-->
      <!--        </el-button>-->
      <!--      </el-tooltip>-->
      <!--      <el-tooltip disabled class="dc-el-tooltip_Button">-->
      <!--        <el-button type="primary" icon="el-icon-search" v-on:click="selectSalary" class="dc-el-button_Button">-->
      <!--          查看明细-->
      <!--        </el-button>-->
      <!--      </el-tooltip>-->
      <!--      <el-tooltip disabled class="dc-el-tooltip_Button">-->
      <!--        <el-button type="primary" icon="el-icon-circle-close" v-on:click="deleteSalary" class="dc-el-button_Button">-->
      <!--          删除-->
      <!--        </el-button>-->
      <!--      </el-tooltip>-->
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-s-operation" v-on:click="generateDistributionData"
                   class="dc-el-button_Button">
          生成发放数据
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" v-on:click="downloadTempSalary" class="dc-el-button_Button">
          下载模板
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-success" v-on:click="billConfirmation" class="dc-el-button_Button">
          账单确认
        </el-button>
      </el-tooltip>
      <el-dialog :visible.sync="dialogView" class="uploadDialog"></el-dialog>
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      @select="selectChoose"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column align="center" header-align="center" type="checkbox" width="55" fixed="left" renderable="isSelected">
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['144'].field"
        :title="tableColumns['144'].title"
        :width="tableColumns['144'].width"
        :visible="tableColumns['144'].visible"
        :params="{ sortId: '144', summary: false }"
        tree-node
        resizable
        min-width="90px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['156'].field"
        :title="tableColumns['156'].title"
        :width="tableColumns['156'].width"
        :visible="tableColumns['156'].visible"
        :params="{ sortId: '156', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['155'].field"
        :title="tableColumns['155'].title"
        :width="tableColumns['155'].width"
        :visible="tableColumns['155'].visible"
        :params="{ sortId: '155', summary: false }"
        tree-node
        resizable
        min-width="90px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['138'].field"
        :title="tableColumns['138'].title"
        :width="tableColumns['138'].width"
        :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }"
        tree-node
        resizable
        min-width="150px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['139'].field"
        :title="tableColumns['139'].title"
        :width="tableColumns['139'].width"
        :visible="tableColumns['139'].visible"
        :params="{ sortId: '139', summary: false }"
        tree-node
        resizable
        min-width="90px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['174'].field"
        :title="tableColumns['174'].title"
        :width="tableColumns['174'].width"
        :visible="tableColumns['174'].visible"
        :params="{ sortId: '174', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['175'].field"
        :title="tableColumns['175'].title"
        :width="tableColumns['175'].width"
        :visible="tableColumns['175'].visible"
        :params="{ sortId: '175', summary: false }"
        tree-node
        resizable
        min-width="150px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column title="操作" :params="{ sortId: '114' }" tree-node resizable width="140px" min-width="140px"
                       fixed="right" align="center" header-align="center" class="dc-ux-table-column_ElTableOptColumn">
        <template v-slot:header="scope">
          <span>操作</span>
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10"
                      class="dc-el-popover_ElPopover">
            <view-columns-select v-model="tableColumns" ref="viewColumnsSelect"
                                 v-on:save-column-view="saveColumn($event, 'table')"
                                 v-on:show-all-column="showAllColumn($event, 'table')"
                                 v-on:show-default-column="showDefaultColumn('table')"
                                 class="dc-view-columns-select_ViewColumnsSelect"></view-columns-select>
            <template slot="reference">
              <OperationIcon type="primary" content="自定义列" placement="top" icon-name="el-icon-tickets"></OperationIcon>
            </template>
          </el-popover>
          <OperationIcon v-on:click="onExport(tableData)" type="primary" content="导出excel文件" placement="top"
                         icon-name="el-icon-download" class="dc-OperationIcon_IconButton"></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon v-on:click="onView(scope)" type="info" content="查看" placement="top" icon-name="el-icon-view"
                         class="dc-OperationIcon_IconButton"></OperationIcon>
          <OperationIcon v-on:click="editSalary(scope)" type="primary" content="编辑" placement="top"
                         icon-name="el-icon-edit"
                         class="dc-OperationIcon_IconButton"></OperationIcon>
          <OperationIcon v-on:click="onDelete(scope)" type="danger" content="删除" placement="top"
                         icon-name="el-icon-delete" class="dc-OperationIcon_IconButton"></OperationIcon>
        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination
      :total="tableDataTotal"
      :page-size="search.limit"
      background
      :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
  </el-row>
</template>
<script>
  import {validatenull} from '@/utils/validate'

  import {
    listSalaryBatchPage,
    deleteSalaryBatch,
    fileSalaryUpload,
    downloadTemplateSalary,
    salaryConfirmFun,
    isSalaryConfirmFun,
    exportSalaryData
  } from '@/api/salaryBatch/salaryBatch'

  import {listResourcePermission} from '@/api/admin/common/permission'

  import {getDictTypeById} from "@/api/sys/dictType";
  /** 根据用户界面配置import组件 开始 */
  import History from '@/views/components/history'
  import EditForm from './form'
  import upForm from './form'
  import QueryTag from '@/views/components/queryTag'
  import QueryConditionPanel from '@/views/components/queryConditionPanel'
  import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
  /** 根据用户界面配置import组件 结束 */
  import metadata from './metadata'
  import ExportExcelButton from '@/components/ExportExcelButton'
  import DcMain from '@/views/components/dcMain'
  import OperationIcon from '@/components/OperationIcon'

  export default {
    extends: DcMain,
    components: {
      /** 根据用户界面配置组件 开始 */
      History,
      EditForm,
      upForm,
      QueryTag,
      QueryConditionPanel,
      ViewColumnsSelect,
      /** 根据用户界面配置组件 结束 */
      ExportExcelButton,
      OperationIcon
    },
    data() {
      return {
        permission: {
          view: false,
          add: false,
          edit: false,
          remove: false,
          export: false
        },
        metadata,
        billYear: '',
        taxYear: '',
        dialogVisible: false,
        dialogView: false,
        /** 根据用户界面配置生成data数据 开始 */
        queryFormData: {
          saBatchCode: '', // 批次号
          auditState: '', // 账单确认
          billYearMonth: '' //账单年月
        },
        tableData: [],
        // 查询表格列头
        tableColumns: {

          131: {
            title: '批次号',
            field: 'saBatchCode',
            visible: true,
            order: 0
          },
          144: {
            title: '账单年月 ',
            field: 'billYearMonth',
            visible: true,
            order: 1
          },
          156: {
            title: '账单金额',
            field: 'totalAmount',
            visible: true,
            order: 2
          },
          155: {
            title: '薪资人数',
            field: 'totalDecimal',
            visible: true,
            order: 3
          },
          137: {
            title: '账单生成人',
            field: 'applyPayUserId',
            visible: true,
            order: 4
          },
          138: {
            title: '账单生成时间',
            field: 'applyPayTime',
            visible: true,
            order: 5
          },
          139: {
            title: '账单归属',
            field: 'applyPayUserId',
            visible: true,
            order: 5
          },
          174: {
            title: '是否已确认',
            field: 'isVerifyName',
            visible: true,
            order: 6
          },
          175: {
            title: '确认时间',
            field: 'verifyTime',
            visible: true,
            order: 7
          },
          114: {
            title: '操作',
            width: '140px',
            visible: true,
            order: 8
          }
        },
        // 分页属性
        tableDataPage: 1,
        tableDataTotal: 0,
        // 选项变量
        // 账单确认选项
        auditStateOptions: [
          {
            label: '已确认',
            value: '1'
          },
          {
            label: '未确认',
            value: '2'
          }
        ],
        /** 根据用户界面配置生成data数据 结束 */
        version: 6,
        search: {
          // 查询条件   业务表设置的筛选条件
          params: [],

          offset: 0,
          limit: 10,
          columnName: '', // 排序字段名
          order: '' // 排序
        },
        searchList: [],
        statusOptions: [],
        confirmOptions: [],
        tableId: '1683007003934706055',
        schemeId: '1683007003934706053',
      }
    },
    computed: {},
    methods: {
      selectChoose(selection, row) {
        // 清除 所有勾选项
        this.$refs.table.clearSelection()
        // 当表格数据都没有被勾选的时候 就返回
        // 主要用于将当前勾选的表格状态清除
        if (selection.length == 0) return
        this.$refs.table.toggleRowSelection([
          {
            row: row,
            selected: true
          }
        ])
      },
      //导出
      exportSalaryData() {
        let data = this.$refs.table.getCheckboxRecords()
        if (data.length == 0){
          this.$message.warning('请先选择一条数据！')
          return
        }
        this.setLoad()
        exportSalaryData(data[0]).then(resp => {
          const blob = new Blob([resp], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
          const link = document.createElement('a');
          link.href = URL.createObjectURL(blob);
          link.download = '薪资明细.xlsx'
          link.click();
          URL.revokeObjectURL(link.href);
          this.showMessage({
            type: 'success',
            msg: '导出成功'
          })
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      },
      //账单确认
      billConfirmation: function () {
        let data = this.$refs.table.getCheckboxRecords()
        if (data.length == 0){
          this.$message.warning('请先选择一条数据！')
          return
        }
        this.setLoad()
        salaryConfirmFun(data[0]).then(resp => {
          if (resp.code == 400) {
            this.showMessage({type: 'warning', msg: resp.msg + '，数据确认失败！'})
          } else {
            this.getList()
            this.showMessage({
              type: 'success',
              msg: '操作成功！'
            })
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      },
      uploadSalary: function () {
        this.dialogVisible = true
      },
      // 导入
      importSalary: function () {
        if (!this.taxYear) {
          this.$message.warning('请先选择报税年月！')
          return
        }
        if (!this.billYear) {
          this.$message.warning('请先选择账单年月！')
          return
        }
        const fileInput = document.createElement('input');
        fileInput.style = 'display:none'
        fileInput.setAttribute("type", "file");
        fileInput.setAttribute("accept", ".xls,.xlsx");
        fileInput.click()
        fileInput.addEventListener('change', (e) => this.fileToData(e))
      },
      fileToData(e) {
        let formData = new FormData()
        formData.append('file', e.target.files[0])
        formData.append('taxYear', this.taxYear)
        formData.append('billYear', this.billYear)
        fileSalaryUpload(formData).then(resp => {
          if (resp.code == 400) {
            this.showMessage({type: 'warning', msg: resp.msg})
          } else {
            this.showMessage({
              type: 'success',
              msg: '导入成功！'
            })
          }
          this.dialogVisible = false
          this.getList();
        }).catch((error) => {
          this.outputError(error)
        })
      },
      //下载模板
      downloadTempSalary() {
        // this.dialogView = true
        this.setLoad()
        downloadTemplateSalary().then(resp => {
          const blob = new Blob([resp], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'})
          const link = document.createElement('a');
          link.href = URL.createObjectURL(blob);
          link.download = '薪资计算导入模版.xlsx'
          link.click();
          URL.revokeObjectURL(link.href);
          // this.dialogView = false
          this.resetLoad()
        })
      },
      editSalary(scope) {
        isSalaryConfirmFun(scope.row).then(resp => {
          if (resp.code == 400) {
            this.showMessage({type: 'warning', msg: resp.msg})
          } else {
            // this.getList()
            // this.showMessage({
            //   type: 'success',
            //   msg: '操作成功！'
            // })
            this.$refs.editForm.$emit('openEditDialog', scope.row.id)
          }
          // this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })

      },
      //重置
      resetSalary() {
        this.queryFormData = {
          employeeNo: '',
          phoneNumber: '',
          employeeStatus: '',
          deptId: ''
        }
        this.onSearch()
      },
      getList() {
        this.setLoad()
        /* 查询参数 和数据权限 */
        this.search.params = []
        if (this.isQueryConditionPanel) {
          this.search.params = this.search.params.concat(this.compositeCondition())
        } else {
          this.search.params.push({
            columnName: 'SA_BATCH_CODE',
            queryType: 'like',
            value: !validatenull(this.queryFormData.saBatchCode) ? this.queryFormData.saBatchCode : null
          })

          this.search.params.push({
            columnName: 'AUDIT_STATE',
            queryType: '=',
            value: !validatenull(this.queryFormData.auditState) ? this.queryFormData.auditState : null
          })

          this.search.params.push({
            columnName: 'BILL_YEAR_MONTH',
            queryType: '=',
            value: !validatenull(this.queryFormData.billYearMonth) ? this.queryFormData.billYearMonth : null
          })
        }
        // 数据权限: 员工薪资发放批次表salary_batch
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        getDictTypeById("1730610205251371011").then(res => {
          this.confirmOptions = res.data.dictItemList;
          listSalaryBatchPage(this.search)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.tableDataTotal = responseData.data.total
                let datas = responseData.data.rows ? responseData.data.rows : []
                datas.map(item => {
                  let obj = this.confirmOptions.find(it => it.value === item.isVerify)
                  item.isVerifyName = obj ? obj.name : item.isVerify
                  return item
                })
                this.tableData = JSON.parse(JSON.stringify(datas))
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        });
      },
      onSearch() {
        if (this.isQueryConditionPanel) {
          this.search.offset = 0
          this.tableDataPage = 1
          this.getList()
        } else {
          this.$refs['queryForm'].validate((valid) => {
            if (valid) {
              this.search.offset = 0
              this.tableDataPage = 1
              this.getList()
            } else {
              return false
            }
          })
        }
      },
      getConfirm() {
        getDictTypeById("1730610205251371011").then(res => {
          this.confirmOptions = res.data.dictItemList;
        });
      },
      onSizeChange(val) {
        this.tableDataPage = 1
        this.search.limit = val
        this.search.offset = (this.tableDataPage - 1) * val
        this.getList()
      },
      onCurrentChange(val) {
        this.search.offset = (val - 1) * this.search.limit
        this.tableDataPage = val
        this.getList()
      },
      onDialogCloseSalary() {
        this.dialogProps.visible = false
      },
      onDialogOpenSalary() {
        this.$nextTick(() => {
          this.$refs['upForm'].clearValidate()
        })
      },
      async pageInit() {
        this.setLoad()
        this.initOptions(this.queryModel)
        this.onSearch()
        listResourcePermission(this.$route.meta.routerId)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.permission.view = responseData.data.find((item) => {
                return item.permission === 'salaryBatch:read'
              })
              this.permission.export = responseData.data.find((item) => {
                return item.permission === 'salaryBatch:export'
              })

              this.permission.add = responseData.data.find((item) => {
                return item.permission === 'salaryBatch:create'
              })
              this.permission.edit = responseData.data.find((item) => {
                return item.permission === 'salaryBatch:update'
              })
              this.permission.remove = responseData.data.find((item) => {
                return item.permission === 'salaryBatch:delete'
              })
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      onView(scope) {
        this.$refs.editForm.$emit('openViewDialog', scope.row.id)
      },

      onCreate() {
        this.$refs.editForm.$emit('openAddDialog')
      },
      onEdit(scope) {
        this.$refs.editForm.$emit('openEditDialog', scope.row.id)
      },
      onCopy(scope) {
        this.$refs.editForm.$emit('openCopyDialog', scope.row.id)
      },
      onDelete(scope) {
        this.$confirm('确定删除吗？', '确认', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.setLoad()
            deleteSalaryBatch(scope.row)
              .then((responseData) => {
                if (responseData.code == 100) {
                  this.getList()
                  this.showMessage({
                    type: 'success',
                    msg: '删除成功'
                  })
                } else {
                  this.showMessage(responseData)
                }
                this.resetLoad()
              })
              .catch((error) => {
                this.outputError(error)
              })
          })
          .catch(() => {
          })
      },
      onSortChange(orderby) {
        if (validatenull(orderby.prop)) {
          this.search.columnName = ''
          this.search.order = ''
        } else {
          this.search.columnName = orderby.prop
          this.search.order = orderby.order
        }
        this.getList()
      },

      // 初始化自定义类型选择框选项变量
      initOptions(This) {
      }
    },
    watch: {},
    created() {
      this.getConfirm()
    },
    mounted() {
      this.pageInit()
      this.columnDrop(this.$refs.table)
    }
  }
</script>
