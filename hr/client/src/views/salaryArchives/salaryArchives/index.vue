<template>
  <van-row class="dm-container">

    <edit-form ref="editForm" v-on:save-finished="getList" v-on:before-load="setLoad"
      :departmentOptions="departmentOptions" :statusOptions="statusOptions" v-on:after-load="resetLoad"
      :userTypeOptions="userTypeOptions" :sexOptions="sexOptions" :probationOptions="probationOptions"></edit-form>

    <el-form ref="queryForm" :model="queryFormData" label-width="100px" style="padding-top: 20px;"
      class="dc-el-form_ElQueryForm">
      <el-row> <el-col :span="6">
          <el-form-item prop="postName" label="员工工号:" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.employeeNo" :maxLength="64" placeholder="员工工号" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="actStatus" label="手机号:" class="dc-el-form-item_SelectInput">
            <el-input v-model="queryFormData.phoneNumber" :maxLength="64" placeholder="手机号" clearable :min="0"
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="recruitStatus" label="员工状态:" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.employeeStatus" :style="{ width: '100%' }" placeholder="请选择" clearable
              filterable class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in statusOptions" :key="index" :label="item.name"
                :value="item.value"></el-option>
            </el-select>

          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="recruitStatus" label="所属部门:" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.deptId" :style="{ width: '100%' }" placeholder="请选择" clearable filterable
              class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in departmentOptions" :key="index" :label="item.name"
                :value="item.id"></el-option>
            </el-select>
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
            <el-button @click="onCreate" class="dc-el-button_Button" type="primary" icon="el-icon-plus">新增</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="importData" class="dc-el-button_Button" type="primary"
              icon="el-icon-upload2">导入</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="downloadTemp" class="dc-el-button_Button" type="primary"
              icon="el-icon-download">模板下载</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="AllInitialize" class="dc-el-button_Button" type="primary"
              icon="el-icon-circle-check">全量初始化</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="partInitialize" class="dc-el-button_Button" type="primary"
              icon="el-icon-check">部分初始化</el-button>
          </el-tooltip>
        </el-col></el-row>
    </el-form>
    <ux-grid column-key ref="table" :data="tableData" border v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)" @selection-change="handleSelectionChange"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable">
      <ux-table-column align="center" header-align="center" type="checkbox" width="55" fixed="left">
      </ux-table-column>
      <ux-table-column :field="tableColumns['129'].field" :title="tableColumns['129'].title" fixed="left"
        :width="tableColumns['129'].width" :visible="tableColumns['129'].visible" min-width="100px"
        :params="{ sortId: '129', summary: false }" tree-node resizable align="center" header-align="center" sortable
        show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['130'].field" :title="tableColumns['130'].title" fixed="left"
        :width="tableColumns['130'].width" :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['131'].field" :title="tableColumns['131'].title" fixed="left"
        :width="tableColumns['131'].width" :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['132'].field" :title="tableColumns['132'].title" fixed="left"
        :width="tableColumns['132'].width" :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['133'].field" :title="tableColumns['133'].title"
        :width="tableColumns['133'].width" :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['134'].field" :title="tableColumns['134'].title"
        :width="tableColumns['134'].width" :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['135'].field" :title="tableColumns['135'].title"
        :width="tableColumns['135'].width" :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['136'].field" :title="tableColumns['136'].title"
        :width="tableColumns['136'].width" :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['137'].field" :title="tableColumns['137'].title"
        :width="tableColumns['137'].width" :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['138'].field" :title="tableColumns['138'].title"
        :width="tableColumns['138'].width" :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['139'].field" :title="tableColumns['139'].title"
        :width="tableColumns['139'].width" :visible="tableColumns['139'].visible"
        :params="{ sortId: '139', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['140'].field" :title="tableColumns['140'].title"
        :width="tableColumns['140'].width" :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['141'].field" :title="tableColumns['141'].title"
        :width="tableColumns['141'].width" :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['142'].field" :title="tableColumns['142'].title"
        :width="tableColumns['142'].width" :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }" tree-node resizable min-width="140px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['143'].field" :title="tableColumns['143'].title"
        :width="tableColumns['143'].width" :visible="tableColumns['143'].visible"
        :params="{ sortId: '143', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['144'].field" :title="tableColumns['144'].title"
        :width="tableColumns['144'].width" :visible="tableColumns['144'].visible"
        :params="{ sortId: '144', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['145'].field" :title="tableColumns['145'].title"
        :width="tableColumns['145'].width" :visible="tableColumns['145'].visible"
        :params="{ sortId: '145', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['146'].field" :title="tableColumns['146'].title"
        :width="tableColumns['146'].width" :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['147'].field" :title="tableColumns['147'].title"
        :width="tableColumns['147'].width" :visible="tableColumns['147'].visible"
        :params="{ sortId: '147', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['148'].field" :title="tableColumns['148'].title"
        :width="tableColumns['148'].width" :visible="tableColumns['148'].visible"
        :params="{ sortId: '148', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['149'].field" :title="tableColumns['149'].title"
        :width="tableColumns['149'].width" :visible="tableColumns['149'].visible"
        :params="{ sortId: '149', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['150'].field" :title="tableColumns['150'].title"
        :width="tableColumns['150'].width" :visible="tableColumns['150'].visible"
        :params="{ sortId: '150', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['151'].field" :title="tableColumns['151'].title"
        :width="tableColumns['151'].width" :visible="tableColumns['151'].visible"
        :params="{ sortId: '151', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['152'].field" :title="tableColumns['152'].title"
        :width="tableColumns['152'].width" :visible="tableColumns['152'].visible"
        :params="{ sortId: '152', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['153'].field" :title="tableColumns['153'].title"
        :width="tableColumns['153'].width" :visible="tableColumns['153'].visible"
        :params="{ sortId: '153', summary: false }" tree-node resizable min-width="180px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>

      <ux-table-column title="操作" :params="{ sortId: '114' }" tree-node resizable width="140px" min-width="140px"
        fixed="right" align="center" header-align="center" class="dc-ux-table-column_ElTableOptColumn">
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
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"></el-pagination>

    <el-dialog :visible.sync="dialogVisible" class="uploadDialog"> </el-dialog>
  </van-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
import {
  listSalaryArchivesPage,
  deleteSalaryArchives,
  downloadTemplate,
  fileUpload
} from '@/api/salaryArchives/salaryArchives'
import { listDepartmentAll } from '@/api/org/department.js'
import {
  listResourcePermission
} from '@/api/admin/common/permission'
import EditForm from "./form"
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect"
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import { getDictTypeById } from "@/api/sys/dictType";
import { initAllEmployee, initEmployee } from "@/api/socialSecurityFund/socialSecurityFund";
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
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        employeeNo: '',
        phoneNumber: '',
        employeeStatus: '',
        deptId: ''
      },
      dialogVisible: false,
      tableData: [],
      // 查询表格列头
      tableColumns: {
        129: {
          title: '员工工号',
          field: 'employeeNo',
          visible: true,
          order: 0
        },
        130: {
          title: '员工姓名',
          field: 'employeeName',
          visible: true,
          order: 1
        },
        131: {
          title: '证件号码',
          field: 'idCardNo',
          visible: true,
          order: 2
        },
        132: {
          title: '性别',
          field: 'sexName',
          visible: true,
          order: 3
        },
        133: {
          title: '国籍',
          field: 'nationality',
          visible: true,
          order: 4
        },

        134: {
          title: '出生日期',
          field: 'birthDate',
          visible: true,
          order: 5
        },
        135: {
          title: '手机号',
          field: 'phoneNumber',
          visible: true,
          order: 6
        },
        136: {
          title: '所属部门',
          field: 'deptName',
          visible: true,
          order: 7
        },
        137: {
          title: '员工状态',
          field: 'employeeStatusName',
          visible: true,
          order: 8
        },
        138: {
          title: '员工类型',
          field: 'typeName',
          visible: true,
          order: 9
        },
        139: {
          title: '员工归属',
          field: 'employeeBelongs',
          visible: true,
          order: 10
        },
        140: {
          title: '岗位',
          field: 'postName',
          visible: true,
          order: 11
        },
        141: {
          title: '银行类型',
          field: 'bankCardType',
          visible: true,
          order: 12
        },
        142: {
          title: '开户行',
          field: 'bankName',
          visible: true,
          order: 13
        },
        143: {
          title: '银行卡号',
          field: 'bankNo',
          visible: true,
          order: 14
        },
        144: {
          title: '开户姓名',
          field: 'bankUserName',
          visible: true,
          order: 15
        },
        145: {
          title: '账号市区名',
          field: 'bankCityName',
          visible: true,
          order: 16
        },
        146: {
          title: '入职时间',
          field: 'entryTime',
          visible: true,
          order: 17
        },
        147: {
          title: '试用期',
          field: 'probationMonthName',
          visible: true,
          order: 18
        },
        148: {
          title: '试用期结束时间',
          field: 'probationEndTime',
          visible: true,
          order: 19
        },
        149: {
          title: '离职时间',
          field: 'leaveTime',
          visible: true,
          order: 20
        },
        150: {
          title: '工资薪金税率表',
          field: 'salaryRateTableName',
          visible: true,
          order: 22
        },
        151: {
          title: '年终奖税率表',
          field: 'yearEndRateTableName',
          visible: true,
          order: 23
        },
        152: {
          title: '劳务报酬税',
          field: 'rewardRateTableName',
          visible: true,
          order: 24
        },
        153: {
          title: '离职补偿税率表',
          field: 'leaveCompensationRateTableName',
          visible: true,
          order: 25
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
      version: 1,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1641034040897357711',
      schemeId: '1641034040897357709',
      statusOptions: [],
      departmentOptions: [],
      sexOptions: [],
      userTypeOptions: [],
      probationOptions: [],
      selectData: []
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.queryFormData.employeeNo) {
        this.search.params.push({
          "columnName": "employee_no",
          "queryType": "like",
          "value": `%${this.queryFormData.employeeNo}%`
        },)
      }
      if (this.queryFormData.phoneNumber) {
        this.search.params.push({
          "columnName": "employee_name",
          "queryType": "like",
          "value": `%${this.queryFormData.phoneNumber}%`
        },)
      }
      if (this.queryFormData.employeeStatus) {
        this.search.params.push({
          "columnName": "employee_status",
          "queryType": "=",
          "value": this.queryFormData.employeeStatus
        })
      }
      if (this.queryFormData.deptId) {
        this.search.params.push({
          "columnName": "dept_id",
          "queryType": "=",
          "value": this.queryFormData.deptId
        })
      }
      listSalaryArchivesPage(this.search).then(responseData => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          let arr = responseData.data.rows ? responseData.data.rows : []
          arr.map(item => {
            let obj = this.statusOptions.find(it => it.value === item.employeeStatus)
            item.employeeStatusName = obj ? obj.name : item.employeeStatus
            let obj1 = this.sexOptions.find(it => it.value === item.sex)
            item.sexName = obj1 ? obj1.name : item.sex
            let obj2 = this.userTypeOptions.find(it => it.value === item.employeeType)
            item.typeName = obj2 ? obj2.name : item.employeeType
            let obj3 = this.probationOptions.find(it => it.value === item.probationMonth)
            item.probationMonthName = obj3 ? obj3.name : item.probationMonth
            return item
          })
          this.tableData = JSON.parse(JSON.stringify(arr))
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    handleSelectionChange(val) { this.selectData = val },
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
    onView(scope) {
      this.$refs.editForm.$emit('openViewDialog', scope.row.id)
    },

    onCreate() {
      this.$refs.editForm.$emit('openAddDialog')
    },
    onEdit(scope) {
      this.$refs.editForm.$emit('openEditDialog', scope.row.id)
    },
    //重置
    reset() {
      this.queryFormData = {
        employeeNo: '',
        phoneNumber: '',
        employeeStatus: '',
        deptId: ''
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
        deleteSalaryArchives(scope.row).then(responseData => {
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
      this.dialogVisible = true
      downloadTemplate().then(resp => {
        const blob = new Blob([resp], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = '薪酬档案信息.xlsx'
        link.click();
        URL.revokeObjectURL(link.href);
        this.dialogVisible = false
      })
    },
    // 导入
    importData: function () {
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
      fileUpload(formData).then(resp => {
        if (resp.code == 50001) {
          this.showMessage({ type: 'warning', msg: resp.msg + ',其余数据导入成功！' })
        } else {
          this.showMessage({
            type: 'success',
            msg: '导入成功'
          })
        }

        this.getList();
      }).catch((error) => {
        this.outputError(error)
      })
    },
    AllInitialize() {
      this.dialogVisible = true
      initAllEmployee().then((responseData) => {
        if (responseData.code == 100) {
          this.$message.success('初始化数据成功！')
        } else {
          this.showMessage(responseData)
        }
        this.dialogVisible = false
      })
    },
    partInitialize() {

      if (this.selectData.length === 0) {
        this.$message.warning('请先选择初始化的数据！')
      } else {
        this.dialogVisible = true
        let arr = this.selectData.map(item => item.employeeNo)
        let params = {
          "employeeNos": arr,
          "needVerify": false
        }
        initEmployee(params).then((responseData) => {
          if (responseData.code == 100) {
            this.$message.success('初始化数据成功！')
          } else {
            this.showMessage(responseData)
          }
          this.dialogVisible = false
        })
      }
    },
    //获取员工状态
    getStatus() {
      getDictTypeById("1513455318726787117").then(res => {
        this.statusOptions = res.data.dictItemList;
      });
    },
    getSex() {
      getDictTypeById("1495769794452094977").then(res => {
        this.sexOptions = res.data.dictItemList;
      });
    },

    getUserType() {
      getDictTypeById("1660844079343558660").then(res => {
        this.userTypeOptions = res.data.dictItemList;
      });
    },
    getProbation() {
      getDictTypeById("1513455318726787124").then(res => {
        this.probationOptions = res.data.dictItemList;
      });
    }
  },
  watch: {
  },
  mounted() {
    this.pageInit()
    this.getStatus()
    this.listDepartmentOptions()
    this.getSex()

    this.getUserType()
    this.getProbation()
    this.columnDrop(this.$refs.table);
  }
}
</script>
<style>
.uploadDialog .el-dialog {
  display: none;
}
</style>
