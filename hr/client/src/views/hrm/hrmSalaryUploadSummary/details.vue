<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="name" label="姓名" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.name"
              :maxLength="64"
              placeholder="请输入姓名"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="deptName" label="部门" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.deptName"
              :maxLength="64"
              placeholder="请输入部门"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
          <el-col :span="12">
            <div style="text-align: right">
              <el-button-group class="dc-el-button-group_ButtonGroup">
                <el-tooltip effect="dark" content="搜索" placement="top">
                  <el-button style="margin-right: 20px" v-on:click="onSearch" type="primary" icon="el-icon-search">搜索</el-button>
                </el-tooltip>
                <el-tooltip effect="dark" content="重置" placement="top">
                  <el-button
                    v-on:click="reset"
                    type="primary"
                    icon="el-icon-refresh-right"
                  >重置</el-button>
                </el-tooltip>
              </el-button-group>
            </div>
          </el-col>
        </el-row>
      </el-row>
    </el-form>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        fixed="left"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        fixed="left"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['133'].field"
        :title="tableColumns['133'].title"
        :width="tableColumns['133'].width"
        :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        fixed="left"
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
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        fixed="left"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['141'].field"
        :title="tableColumns['141'].title"
        :width="tableColumns['141'].width"
        :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }"
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
        :field="tableColumns['142'].field"
        :title="tableColumns['142'].title"
        :width="tableColumns['142'].width"
        :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }"
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
        :field="tableColumns['143'].field"
        :title="tableColumns['143'].title"
        :width="tableColumns['143'].width"
        :visible="tableColumns['143'].visible"
        :params="{ sortId: '143', summary: false }"
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
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['145'].field"
        :title="tableColumns['145'].title"
        :width="tableColumns['145'].width"
        :visible="tableColumns['145'].visible"
        :params="{ sortId: '145', summary: false }"
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
        :field="tableColumns['146'].field"
        :title="tableColumns['146'].title"
        :width="tableColumns['146'].width"
        :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }"
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
        :field="tableColumns['147'].field"
        :title="tableColumns['147'].title"
        :width="tableColumns['147'].width"
        :visible="tableColumns['147'].visible"
        :params="{ sortId: '147', summary: false }"
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
        :field="tableColumns['148'].field"
        :title="tableColumns['148'].title"
        :width="tableColumns['148'].width"
        :visible="tableColumns['148'].visible"
        :params="{ sortId: '148', summary: false }"
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
        :field="tableColumns['157'].field"
        :title="tableColumns['157'].title"
        :width="tableColumns['157'].width"
        :visible="tableColumns['157'].visible"
        :params="{ sortId: '157', summary: false }"
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
        :field="tableColumns['152'].field"
        :title="tableColumns['152'].title"
        :width="tableColumns['152'].width"
        :visible="tableColumns['152'].visible"
        :params="{ sortId: '152', summary: false }"
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
        :field="tableColumns['150'].field"
        :title="tableColumns['150'].title"
        :width="tableColumns['150'].width"
        :visible="tableColumns['150'].visible"
        :params="{ sortId: '150', summary: false }"
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
        :field="tableColumns['149'].field"
        :title="tableColumns['149'].title"
        :width="tableColumns['149'].width"
        :visible="tableColumns['149'].visible"
        :params="{ sortId: '149', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['151'].field"
        :title="tableColumns['151'].title"
        :width="tableColumns['151'].width"
        :visible="tableColumns['151'].visible"
        :params="{ sortId: '151', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
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
import { validatenull } from '@/utils/validate'

import { listHrmPayrollBillDetailsPage, deleteHrmPayrollBillDetails } from '@/api/hrm/hrmPayrollBillDetails'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
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
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon
  },
  props:{
    summaryTableId:{
      type: String,
      require:true,
    }
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

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        salaryYearAndMonth: '' // 薪资年月
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        132: {
          title: '薪资年月',
          field: 'salaryYearAndMonth',
          visible: true,
          width: 100,
          order: 0
        },
        140: {
          title: '部门名称',
          field: 'deptName',
          visible: true,
          width: 100,
          order: 1
        },
        133: {
          title: '工号',
          field: 'empolyno',
          visible: true,
          width: 100,
          order: 2
        },
        139: {
          title: '姓名',
          field: 'name',
          visible: true,
          width: 100,
          order: 3
        },
        141: {
          title: '全额工资',
          field: 'totalSalary',
          visible: true,
          order: 4
        },
        142: {
          title: '奖金合计',
          field: 'totalBonus',
          visible: true,
          order: 5
        },
        143: {
          title: '补扣款合计',
          field: 'totalAllowanceAndDeduction',
          visible: true,
          order: 6
        },
        144: {
          title: '社保小计(个人)',
          field: 'personalSocialInsurance',
          visible: true,
          order: 7
        },
        145: {
          title: '公积金小计(个人)',
          field: 'personalAccumulationFund',
          visible: true,
          order: 8
        },
        146: {
          title: '计税工资',
          field: 'salaryNeedTax',
          visible: true,
          order: 9
        },
        147: {
          title: '个人税款',
          field: 'personalTax',
          visible: true,
          order: 10
        },
        148: {
          title: '税后项合计',
          field: 'totalAfterTax',
          visible: true,
          order: 11
        },
        157: {
          title: '所得税',
          field: 'salaryTax',
          visible: true,
          order: 12
        },
        152: {
          title: '实发工资',
          field: 'netSalary',
          visible: true,
          order: 13
        },
        150: {
          title: '实发工资2',
          field: 'netSalary2',
          visible: true,
          order: 14
        },
        149: {
          title: '备注1',
          field: 'remark',
          visible: true,
          order: 15
        },
        151: {
          title: '备注2',
          field: 'remark2',
          visible: true,
          order: 16
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 2,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1457254743943434067',
      schemeId: '1457254743943434065'
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      console.log(this.summaryTableId)
      /* 查询参数 和数据权限 */
      this.search.params = [
        {
          columnName: 'summary_table_id',
          queryType: '=',
          value: this.summaryTableId
        }
      ]
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'name',
          queryType: 'like',
          value: !validatenull(this.queryFormData.name) ? this.queryFormData.name : null
        })
        this.search.params.push({
          columnName: 'dept_name',
          queryType: 'like',
          value: !validatenull(this.queryFormData.deptName) ? this.queryFormData.deptName : null
        })
      }
      // 数据权限: 薪资条账单明细表hrm_payroll_bill_details
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listHrmPayrollBillDetailsPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total
            this.tableData = responseData.data.rows ? responseData.data.rows : []
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    reset(){
      this.queryFormData = {}
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
    async pageInit() {
      this.setLoad()
      console.log(this.summaryTableId)
      try {
        this.initOptions(this.queryModel)
        this.search.params = [
          {
            columnName: 'summary_table_id',
            queryType: '=',
            value: this.summaryTableId
          }
        ]
        // 数据权限: 薪资条账单明细表hrm_payroll_bill_details
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listHrmPayrollBillDetailsRespData, listPermissionRespData] = await Promise.all([
          listHrmPayrollBillDetailsPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listHrmPayrollBillDetailsRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableDataTotal = listHrmPayrollBillDetailsRespData.data.total
          this.tableData = listHrmPayrollBillDetailsRespData.data.rows ? listHrmPayrollBillDetailsRespData.data.rows : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmPayrollBillDetails:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmPayrollBillDetails:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmPayrollBillDetails:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmPayrollBillDetails:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmPayrollBillDetails:delete'
          })
        } else {
          this.showMessage(listPermissionRespData.code != 100 ? listPermissionRespData : listHrmPayrollBillDetailsRespData)
        }
        this.resetLoad()
      } catch (error) {
        this.outputError(error)
      }
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
          deleteHrmPayrollBillDetails(scope.row)
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
        .catch(() => {})
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
    initOptions(This) {}
  },
  watch: {
    summaryTableId(summaryTableId){
      this.getList();
    },
    deep:true,
    immediate: true
  },
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
