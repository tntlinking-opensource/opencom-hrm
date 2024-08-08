<template>
  <van-row class="dm-container">

    <edit-form ref="editForm" v-on:save-finished="getList" v-on:before-load="setLoad"
      v-on:after-load="resetLoad"></edit-form>

    <el-form ref="queryForm" :model="queryFormData" label-width="100px" style="padding-top: 20px;"
      class="dc-el-form_ElQueryForm">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="billYearMonth" label="账单年月:" class="dc-el-form-item_SelectInput">
            <el-date-picker v-model="queryFormData.billYearMonth" :style="{ width: '100%' }" placeholder="请选择"
              type="month" format="yyyyMM" value-format="yyyyMM"
              class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="batchNo" label="批次号:" class="dc-el-form-item_SelectInput">
            <el-input v-model="queryFormData.batchNo" :maxLength="64" placeholder="请输入" clearable
              autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="createBy" label="操作人:" class="dc-el-form-item_SelectInput">
            <el-input v-model="queryFormData.createBy" :maxLength="64" placeholder="请输入" clearable
              autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24" style="margin-bottom: 10px;"> <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="getList" style="margin-left:20px" icon="el-icon-search"
              class="dc-el-button_Button">查询</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="reset" icon="el-icon-refresh" class="dc-el-button_Button">重置</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="getBillClick" icon="el-icon-folder-checked" type="primary"
              class="dc-el-button_Button">生成账单</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" @click="setBillClick" icon="el-icon-edit"
              class="dc-el-button_Button">账单配置</el-button>
          </el-tooltip>
        </el-col></el-row>
    </el-form>
    <ux-grid column-key ref="table" :data="tableData" border v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable">
      <ux-table-column :field="tableColumns['129'].field" :title="tableColumns['129'].title"
        :width="tableColumns['129'].width" :visible="tableColumns['129'].visible" min-width="170px"
        :params="{ sortId: '129', summary: false }" tree-node resizable align="center" header-align="center" sortable
        show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['130'].field" :title="tableColumns['130'].title"
        :width="tableColumns['130'].width" :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['131'].field" :title="tableColumns['131'].title"
        :width="tableColumns['131'].width" :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['132'].field" :title="tableColumns['132'].title"
        :width="tableColumns['132'].width" :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }" tree-node resizable min-width="50px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['133'].field" :title="tableColumns['133'].title"
        :width="tableColumns['133'].width" :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }" tree-node resizable min-width="150px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['134'].field" :title="tableColumns['134'].title"
        :width="tableColumns['134'].width" :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['135'].field" :title="tableColumns['135'].title"
        :width="tableColumns['135'].width" :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column title="操作" :params="{ sortId: '114' }" tree-node resizable min-width="140px" fixed="right"
        align="center" header-align="center" class="dc-ux-table-column_ElTableOptColumn">
        <template v-slot:header="scope">
          <span>操作</span>
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10"
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

          <OperationIcon type="danger" content="删除" placement="top" @click="onDelete(scope)" icon-name="el-icon-delete"
            class="dc-OperationIcon_IconButton"></OperationIcon>

          <OperationIcon @click="onCreate(scope)" type="info" content="查看明细" placement="top" icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"></OperationIcon>
        </template>
      </ux-table-column>
    </ux-grid>
    <el-pagination :total="tableDataTotal" :page-size="search.limit" background :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"></el-pagination>
    <getBill ref="getBill" v-on:save-finished="getList"></getBill>
    <setBill ref="setBill"></setBill>
  </van-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
import {
  downloadTemplate,
  fileUpload
} from '@/api/salaryArchives/salaryArchives'
import { listDepartmentAll } from '@/api/org/department.js'
import EditForm from "./form"
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect"
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import { getDictTypeById } from "@/api/sys/dictType";
import { listBill, deleteBillDetails } from "@/api/socialSecurityFund/socialSecurityFund";
import getBill from './components/getBill'
import setBill from './components/setBill'
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    EditForm,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    getBill,
    setBill
  },
  data() {
    return {
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        billYearMonth: '',
        batchNo: '',
        createBy: '',
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        129: {
          title: '批次号',
          field: 'batchNo',
          visible: true,
          order: 0
        },
        130: {
          title: '账单年月',
          field: 'billYearMonth',
          visible: true,
          order: 1
        },
        131: {
          title: '账单金额',
          field: 'billAmount',
          visible: true,
          order: 2
        },
        132: {
          title: '账单人数',
          field: 'billPeopleNumber',
          visible: true,
          order: 3
        },
        133: {
          title: '生成时间',
          field: 'createDate',
          visible: true,
          order: 4
        },

        134: {
          title: '生成人',
          field: 'createBy',
          visible: true,
          order: 5
        },
        135: {
          title: '版本号',
          field: 'version',
          visible: true,
          order: 6
        },
        114: {
          title: '操作',
          width: '140px',
          visible: true,
          order: 30
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      /** 根据用户界面配置生成data数据 结束 */
      version: 3,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1641034040897357722',
      schemeId: '1641034040897357709',
      statusOptions: [],
      departmentOptions: [],
      sexOptions: [],
      userTypeOptions: [],
      probationOptions: []
    }
  },
  computed: {
  },
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.queryFormData.createBy) {
        this.search.params.push({
          "columnName": "create_by",
          "queryType": "like",
          "value": `%${this.queryFormData.createBy}%`
        },)
      }
      if (this.queryFormData.batchNo) {
        this.search.params.push({
          "columnName": "batch_no",
          "queryType": "like",
          "value": `%${this.queryFormData.batchNo}%`
        },)
      }
      if (this.queryFormData.billYearMonth) {
        this.search.params.push({
          "columnName": "bill_year_month",
          "queryType": "=",
          "value": this.queryFormData.billYearMonth
        })
      }
      listBill(this.search).then(responseData => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          this.tableData = responseData.data.rows ? responseData.data.rows : []
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    getBillClick() {
      this.$refs.getBill.init()
    },
    setBillClick() {
      this.$refs.setBill.init()
    },
    onSearch() {
      this.search.offset = 0
      this.tableDataPage = 1
      this.getList()
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val;
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    async pageInit() {
      this.initOptions(this.queryModel)
      setTimeout(() => {
        this.onSearch()
      })
    },
    onCreate(scope) {
      this.$refs.editForm.$emit('openAddDialog', scope)
    },

    //重置
    reset() {
      this.queryFormData = {
        billYearMonth: '',
        batchNo: '',
        createBy: '',
      }
      this.onSearch()
    },
    onDelete(scope) {
      this.$confirm('确定删除吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        deleteBillDetails(scope.row).then(responseData => {
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
        }).catch(error => {
          this.outputError(error)
        })
      }).catch(() => { })
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
          value: currentUser.company.id == '0' ? '' : currentUser.company.id
        }
      ])
      // 数据权限: 部门org_department
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000014')

      listDepartmentAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    // 下载模板
    downloadTemp() {
      downloadTemplate().then(resp => {
        const blob = new Blob([resp], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = '薪酬档案信息.xlsx'
        link.click();
        URL.revokeObjectURL(link.href);
      })
    },
  },
  watch: {
  },
  mounted() {
    this.pageInit()
    this.columnDrop(this.$refs.table);
  }
}
</script>
