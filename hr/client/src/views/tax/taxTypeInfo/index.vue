<template>
  <van-row class="dm-container">
    <edit-form ref="editForm" v-on:save-finished="getList" v-on:before-load="setLoad"
      v-on:after-load="resetLoad"></edit-form>
    <el-form ref="queryForm" :model="queryFormData" style="padding-top: 20px;" label-width="100px"
      class="dc-el-form_ElQueryForm">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="postName" label="税率表名称:" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.taxName" :maxLength="64" placeholder="税率表名称" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="actStatus" label="费用扣除>=:" class="dc-el-form-item_SelectInput">
            <number-input v-model="queryFormData.minAmount" :maxLength="64" placeholder="费用扣除>=" clearable :min="0"
              class="dc-el-input_SingleInput"></number-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="recruitStatus" label="费用扣除<=:" class="dc-el-form-item_SelectInput">
            <number-input v-model="queryFormData.maxAmount" :maxLength="64" placeholder="费用扣除<=" clearable :min="0"
              class="dc-el-input_SingleInput"></number-input>
          </el-form-item>
        </el-col>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button @click="getList" style="margin-left:20px" icon="el-icon-search"
            class="dc-el-button_Button">查询</el-button>
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button @click="reset" icon="el-icon-refresh" class="dc-el-button_Button">重置</el-button>
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button @click="onCreate" class="dc-el-button_Button" type="primary" icon="el-icon-plus">新增</el-button>
        </el-tooltip>
      </el-row>
    </el-form>
    <ux-grid column-key ref="table" :data="tableData" border v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable">
      <ux-table-column :field="tableColumns['118'].field" :title="tableColumns['118'].title"
        :width="tableColumns['118'].width" :visible="tableColumns['118'].visible"
        :params="{ sortId: '118', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['121'].field" :title="tableColumns['121'].title"
        :width="tableColumns['121'].width" :visible="tableColumns['121'].visible"
        :params="{ sortId: '121', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['120'].field" :title="tableColumns['120'].title"
        :width="tableColumns['120'].width" :visible="tableColumns['120'].visible"
        :params="{ sortId: '120', summary: false }" tree-node resizable min-width="180px" align="left"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>



      <ux-table-column title="操作" :params="{ sortId: '113' }" tree-node resizable width="160px" min-width="160px"
        fixed="right" align="left" header-align="center" class="dc-ux-table-column_ElTableOptColumn">
        <template v-slot:header="scope">
          <span>操作</span>
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="0"
            class="dc-el-popover_ElPopover">
            <view-columns-select v-model="tableColumns" ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')" v-on:show-all-column="showAllColumn($event, 'table')"
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
          <OperationIcon v-on:click="onEdit(scope)" type="primary" content="编辑" placement="top" icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"></OperationIcon>

          <OperationIcon v-on:click="onDelete(scope)" type="danger" content="删除" placement="top"
            icon-name="el-icon-delete" class="dc-OperationIcon_IconButton"></OperationIcon>

        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination :total="tableDataTotal" :page-size="search.limit" background :current-page="tableDataPage"
      :page-sizes="[10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"></el-pagination>
  </van-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { listTaxTypeInfoPage, deleteTaxTypeInfo } from '@/api/tax/taxTypeInfo'

import EditForm from './form'

import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    EditForm,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        taxName: '',
        minAmount: null,
        maxAmount: null
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        118: {
          title: '税率表名称',
          field: 'taxName',
          visible: true,
          order: 0
        },

        120: {
          title: '固定税率',
          field: 'nationalTariff',
          visible: true,
          order: 2
        },
        121: {
          title: '费用扣除',
          field: 'expDeduction',
          visible: true,
          order: 3
        },
        113: {
          title: '操作',
          width: '160px',
          visible: true,
          order: 11
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      tableId: "1457254743943430852",
    }
  },
  methods: {
    getList() {
      if (this.queryFormData.maxAmount && this.queryFormData.minAmount && this.queryFormData.minAmount > this.queryFormData.maxAmount) {
        this.$message({
          type: 'warning',
          message: '请填写正确的费用扣除范围！'
        })
      } else {
        this.setLoad()
        /* 查询参数 和数据权限 */
        this.search.params = [

          {
            "columnName": "tax_type",
            "queryType": "=",
            "value": "1"
          },
        ]
        if (this.queryFormData.taxName) {
          this.search.params.push(

            {
              "columnName": "tax_name",
              "queryType": "like",
              "value": this.queryFormData.taxName
            },
          )
        }
        if (this.queryFormData.maxAmount) {
          this.search.params.push(

            {
              "columnName": "exp_deduction",
              "queryType": "<=",
              "value": this.queryFormData.maxAmount
            },
          )
        }
        if (this.queryFormData.minAmount) {
          this.search.params.push(

            {
              "columnName": "exp_deduction",
              "queryType": ">=",
              "value": this.queryFormData.minAmount
            },
          )
        }
        listTaxTypeInfoPage(this.search)
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
      }

    },
    onSearch() {
      this.search.offset = 0
      this.tableDataPage = 1
      this.getList()
    },
    reset() {
      this.queryFormData = {
        taxName: '',
        minAmount: null,
        maxAmount: null
      }
      this.getList()
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
      this.onSearch()
    },
    onView(scope) {
      this.$refs.editForm.$emit('openViewDialog', scope.row.id, scope.row)
    },

    onCreate() {
      this.$refs.editForm.$emit('openAddDialog')
    },
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope.row.id)
    },
    onDelete(scope) {
      this.$confirm('确定删除所选择的数据记录？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.setLoad()
          deleteTaxTypeInfo(scope.row)
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
        .catch(() => { })
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
  },
  mounted() {
    this.pageInit()
    this.columnDrop(this.$refs.table);
  }
}
</script>
