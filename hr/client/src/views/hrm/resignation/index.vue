<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <laid-form
      ref="laidForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></laid-form>
    <query-condition-panel
      v-if="isQueryConditionPanel"
      ref="conditionPanel"
      :conditionColumns="
        metadata.find((item) => {
          return item.type == 'main'
        }).conditionPanel
      "
      v-model="moreParm"
      :routerId="$route.meta.routerId"
      class="dc-query-condition-panel_ElConditionPanel"
    ></query-condition-panel>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item prop="user.name" label="姓名" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.user.name"
              :maxLength="64"
              placeholder="请输入姓名"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="name" label="工号" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.name"
              :maxLength="64"
              placeholder="请输入工号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item prop="lastworkdate" label="最后工作日" class="dc-el-form-item_PickerTimeDate">
            <el-date-picker
              v-model="queryFormData.lastworkdate"
              :style="{ width: '100%' }"
              placeholder="请选择最后工作日"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="handingstatus" label="交接状态" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.handingstatus"
              :style="{ width: '100%' }"
              placeholder="请选择交接状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in handingstatusOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item prop="financeaffairsstatus" label="财务事务状态" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.financeaffairsstatus"
              :style="{ width: '100%' }"
              placeholder="请选择财务事务办理"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in financeaffairsstatusOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="laidstatus" label="离职状态" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.laidstatus"
              :style="{ width: '100%' }"
              placeholder="请选择离职状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in laidstatusOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
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
                    v-on:click="resetField"
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
        :field="tableColumns['221'].field"
        :title="tableColumns['221'].title"
        :width="tableColumns['221'].width"
        :visible="tableColumns['221'].visible"
        :params="{ sortId: '221', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['198'].field"
        :title="tableColumns['198'].title"
        :width="tableColumns['198'].width"
        :visible="tableColumns['198'].visible"
        :params="{ sortId: '198', summary: false }"
        tree-node
        resizable
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['200'].field"
        :title="tableColumns['200'].title"
        :width="tableColumns['200'].width"
        :visible="tableColumns['200'].visible"
        :params="{ sortId: '200', summary: false }"
        tree-node
        resizable
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['222'].field"
        :title="tableColumns['222'].title"
        :width="tableColumns['222'].width"
        :visible="tableColumns['222'].visible"
        :params="{ sortId: '222', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
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
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['215'].field"
        :title="tableColumns['215'].title"
        :width="tableColumns['215'].width"
        :visible="tableColumns['215'].visible"
        :params="{ sortId: '215', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '8044':
                    return '已审批'
                  case '8045':
                    return '审批中'
                  case '8046':
                    return '已终止'
                  case '8047':
                    return '草稿'
                  default:
                    return ''
                }
              })(row.procStatus.id, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['160'].field"
        :title="tableColumns['160'].title"
        :width="tableColumns['160'].width"
        :visible="tableColumns['160'].visible"
        :params="{ sortId: '160', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '0':
                    return '交接中'
                  case '1':
                    return '交接完成'
                  default:
                    return ''
                }
              })(row.handingstatus, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['201'].field"
        :title="tableColumns['201'].title"
        :width="tableColumns['201'].width"
        :visible="tableColumns['201'].visible"
        :params="{ sortId: '201', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '0':
                    return '未办理'
                  case '1':
                    return '办理中'
                  case '2':
                    return '已办理'
                  default:
                    return ''
                }
              })(row.adminaffairsstatus, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['216'].field"
        :title="tableColumns['216'].title"
        :width="tableColumns['216'].width"
        :visible="tableColumns['216'].visible"
        :params="{ sortId: '216', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '0':
                    return '未办理'
                  case '1':
                    return '办理中'
                  case '2':
                    return '已办理'
                  default:
                    return ''
                }
              })(row.financeaffairsstatus, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['223'].field"
        :title="tableColumns['223'].title"
        :width="tableColumns['223'].width"
        :visible="tableColumns['223'].visible"
        :params="{ sortId: '223', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '0':
                    return '未办理'
                  case '1':
                    return '办理中'
                  case '2':
                    return '已办理'
                  default:
                    return ''
                }
              })(row.humanaffairsstatus, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['202'].field"
        :title="tableColumns['202'].title"
        :width="tableColumns['202'].width"
        :visible="tableColumns['202'].visible"
        :params="{ sortId: '202', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="
              ((value, row, rowIndex) => {
                switch (value) {
                  case '0':
                    return '办理中'
                  case '1':
                    return '已离职'
                  default:
                    return ''
                }
              })(row.laidstatus, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '114' }"
        tree-node
        resizable
        width="140px"
        min-width="140px"
        fixed="right"
        align="center"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template v-slot:header="scope">
          <span>操作</span>
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10" class="dc-el-popover_ElPopover">
            <view-columns-select
              v-model="tableColumns"
              ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')"
              v-on:show-all-column="showAllColumn($event, 'table')"
              v-on:show-default-column="showDefaultColumn('table')"
              class="dc-view-columns-select_ViewColumnsSelect"
            ></view-columns-select>
            <template slot="reference">
              <OperationIcon type="primary" content="自定义列" placement="top" icon-name="el-icon-tickets"></OperationIcon>
            </template>
          </el-popover>
          <OperationIcon
            v-on:click="onExport(tableData)"
            type="primary"
            v-show="permission.export"
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon
            v-on:click="onView(scope)"
            type="info"
            v-show="permission.view"
            content="人事办理详情"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="permission.edit && scope.row.humanaffairsstatus === '1'"
            content="人事办理确认"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onLaid(scope)"
            type="primary"
            v-show="permission.laid && scope.row.laidstatus === '0'"
            content="确认离职"
            placement="top"
            icon-name="el-icon-circle-check"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="docLaid(scope)"
            type="primary"
            v-show="permission.view"
            content="查看离职详情单"
            placement="top"
            icon-name="el-icon-document"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="docLaidApply(scope)"
            type="primary"
            v-show="permission.view"
            content="查看离职申请单"
            placement="top"
            icon-name="el-icon-document-copy"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
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
import { validatenull } from '@/utils/validate'

import { listHrmUserLaidPage, deleteHrmUserLaid,docLaid,docLaidApply } from '@/api/hrm/hrmUserLaidCrud'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import EditForm from './form'
import LaidForm from "./laidForm.vue";
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import { listUserAll } from '@/api/admin/user.js'
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
    LaidForm,
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
        export: false,
        laid: false
      },
      metadata,

      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        user: {
          id: null,
          name: null,
          departmentId: null,
          companyId: null,
          email: null,
          phone: null
        },
        name: '', // 工号
        lastworkdate: '', // 最后工作日
        handingstatus: null, // 交接状态
        financeaffairsstatus: null, // 财务事务状态
        laidstatus: null // 离职状态
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        221: {
          title: '表单号',
          field: 'name',
          visible: true,
          order: 0
        },
        135: {
          title: '姓名',
          field: 'user.name',
          visible: true,
          order: 1
        },
        198: {
          title: '工号',
          field: 'user.empolyno',
          visible: true,
          width:150,
          order: 2
        },
        134: {
          title: '部门',
          field: 'user.department.name',
          visible: true,
          order: 3
        },
        200: {
          title: '技术职务',
          field: 'user.post.name',
          visible: true,
          width:150,
          order: 4
        },
        222: {
          title: '交接人名称',
          field: 'handingname',
          visible: true,
          order: 5
        },
        143: {
          title: '最后工作日',
          field: 'lastworkdate',
          visible: true,
          order: 6
        },
        215: {
          title: '审批状态',
          field: 'procStatus',
          visible: true,
          order: 7
        },
        160: {
          title: '交接状态',
          field: 'handingstatus',
          visible: true,
          order: 8
        },
        201: {
          title: '行政事务状态',
          field: 'adminaffairsstatus',
          visible: true,
          order: 9
        },
        216: {
          title: '财务事务状态',
          field: 'financeaffairsstatus',
          visible: true,
          order: 10
        },
        223: {
          title: '人事办理状态',
          field: 'humanaffairsstatus',
          visible: true,
          order: 11
        },
        202: {
          title: '离职状态',
          field: 'laidstatus',
          visible: true,
          order: 12
        },
        114: {
          title: '操作',
          width: '140px',
          isShow: true,
          visible: true,
          order: 13
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 姓名选项
      userOptions: [],
      // 交接状态选项
      handingstatusOptions: [
        {
          label: '交接中',
          value: '0'
        },
        {
          label: '已确认',
          value: '1'
        }
      ],
      // 财务事务状态选项
      financeaffairsstatusOptions: [
        {
          label: '未办理',
          value: '0'
        },
        {
          label: '办理中',
          value: '1'
        },
        {
          label: '已办理',
          value: '2'
        }
      ],
      // 离职状态选项
      laidstatusOptions: [
        {
          label: '办理中',
          value: '0'
        },
        {
          label: '已离职',
          value: '1'
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
      tableId: '1457254743943433612',
      schemeId: '1457254743943433610'
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = [
        {
          columnName: 'financeaffairsstatus',
          queryType: '=',
          value: '2'
        }
      ]
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'user_id',
          queryType: '=',

          value: !validatenull(this.queryFormData.user.id) ? this.queryFormData.user.id : null
        })

        this.search.params.push({
          columnName: 'user.empolyno',
          queryType: 'like',

          value: !validatenull(this.queryFormData.name) ? this.queryFormData.name : null
        })

        this.search.params.push({
          columnName: 'lastWorkDate',
          queryType: '=',

          value: !validatenull(this.queryFormData.lastworkdate) ? this.queryFormData.lastworkdate : null
        })

        this.search.params.push({
          columnName: 'handingStatus',
          queryType: '=',

          value: !validatenull(this.queryFormData.handingstatus) ? this.queryFormData.handingstatus : null
        })

        this.search.params.push({
          columnName: 'financeAffairsStatus',
          queryType: '=',

          value: !validatenull(this.queryFormData.financeaffairsstatus) ? this.queryFormData.financeaffairsstatus : null
        })

        this.search.params.push({
          columnName: 'laidStatus',
          queryType: '=',

          value: !validatenull(this.queryFormData.laidstatus) ? this.queryFormData.laidstatus : null
        })
      }
      // 数据权限: 离职申请hrm_user_laid1
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listHrmUserLaidPage(this.search)
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
    resetField(){
      this.queryFormData={
        name:'',
        user:{},
        lastworkdate:'',
        handingstatus:'',
        financeaffairsstatus:'',
        laidstatus: ''
      }
      this.getList()
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
      try {
        this.initOptions(this.queryModel)
        this.search.params = [
          {
            columnName: 'financeaffairsstatus',
            queryType: '=',
            value: '2'
          }
        ]
        // 数据权限: 离职申请hrm_user_laid1
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listHrmUserLaidRespData, listPermissionRespData] = await Promise.all([
          listHrmUserLaidPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listHrmUserLaidRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableDataTotal = listHrmUserLaidRespData.data.total
          this.tableData = listHrmUserLaidRespData.data.rows ? listHrmUserLaidRespData.data.rows : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'resignation:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'resignation:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'resignation:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'resignation:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'resignation:delete'
          })
          this.permission.laid = listPermissionRespData.data.find((item) => {
            return item.permission === 'resignation:laid'
          })
        } else {
          this.showMessage(listPermissionRespData.code != 100 ? listPermissionRespData : listHrmUserLaidRespData)
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
    docLaid(scope){
      docLaid(scope.row).then(responseData => {
        this.downloadPdf(responseData);
      })
    },
    docLaidApply(scope){
      docLaidApply(scope.row).then(responseData => {
        this.downloadPdf(responseData);
      })
    },
    downloadPdf(content){
      const blob = new Blob([content] ,{ type: 'application/pdf;charset-UTF-8' });
      const url = window.URL.createObjectURL(blob);
      window.open(url)
    },
    onLaid(scope) {
      this.$refs.laidForm.$emit('openEditDialog', scope.row.id)
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
          deleteHrmUserLaid(scope.row)
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

    listUserOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000002')

      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.userOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.listUserOptions()
    }
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
