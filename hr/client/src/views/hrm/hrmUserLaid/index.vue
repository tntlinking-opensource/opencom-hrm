<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
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
        <el-col :span="6">
          <el-form-item prop="laidtype.id" label="状态" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.status"
              :style="{ width: '100%' }"
              placeholder="请选择查询状态"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in procStatus" :key="index" :label="item.name" :value="item.value"></el-option>
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
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <process-panel
        ref="processPanel"
        :bizAdd="permission.add"
        :tenantId="currentUser.company.id"
        keyPrefix="PROC_123"
        procName="离职审批流程"
        formKey="wf/common/wfForm.vue#hrm/hrmUserLaid/form.vue"
        procTitile="${start_user_name}发起${form.name}审批"
        v-on:v-loading="onVLoading"
        class="dc-process-panel_ProcessPanel"
      ></process-panel>
    </el-row>
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
        :field="tableColumns['179'].field"
        :title="tableColumns['179'].title"
        :width="tableColumns['179'].width"
        :visible="tableColumns['179'].visible"
        :params="{ sortId: '179', summary: false }"
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
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
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
        :field="tableColumns['139'].field"
        :title="tableColumns['139'].title"
        :width="tableColumns['139'].width"
        :visible="tableColumns['139'].visible"
        :params="{ sortId: '139', summary: false }"
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
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
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
        :field="tableColumns['144'].field"
        :title="tableColumns['144'].title"
        :width="tableColumns['144'].width"
        :visible="tableColumns['144'].visible"
        :params="{ sortId: '144', summary: false }"
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
      <ux-table-column
        :field="tableColumns['152'].field"
        :title="tableColumns['152'].title"
        :width="tableColumns['152'].width"
        :visible="tableColumns['152'].visible"
        :params="{ sortId: '152', summary: false }"
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
        :field="tableColumns['153'].field"
        :title="tableColumns['153'].title"
        :width="tableColumns['153'].width"
        :visible="tableColumns['153'].visible"
        :params="{ sortId: '153', summary: false }"
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
        :field="tableColumns['110'].field"
        :title="tableColumns['110'].title"
        :width="tableColumns['110'].width"
        :visible="tableColumns['110'].visible"
        :params="{ sortId: '110', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template slot-scope="{ row, rowIndex }">
          <div
            v-html="showStatus(row,rowIndex)"
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['172'].field"
        :title="tableColumns['172'].title"
        :width="tableColumns['172'].width"
        :visible="tableColumns['172'].visible"
        :params="{ sortId: '172', summary: false }"
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
        :field="tableColumns['173'].field"
        :title="tableColumns['173'].title"
        :width="tableColumns['173'].width"
        :visible="tableColumns['173'].visible"
        :params="{ sortId: '173', summary: false }"
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
        :field="tableColumns['174'].field"
        :title="tableColumns['174'].title"
        :width="tableColumns['174'].width"
        :visible="tableColumns['174'].visible"
        :params="{ sortId: '174', summary: false }"
        tree-node
        resizable
        min-width="160px"
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
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['176'].field"
        :title="tableColumns['176'].title"
        :width="tableColumns['176'].width"
        :visible="tableColumns['176'].visible"
        :params="{ sortId: '176', summary: false }"
        tree-node
        resizable
        min-width="160px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
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
            content="查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            v-show="permission.view"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
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
      :page-sizes="[10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
    <comment ref="commentForm" class="dc-comment_ElImport"></comment>
    <component
      ref="wfForm"
      v-on:save-finished="getList()"
      v-on:after-wfForm-load="afterWfFormload()"
      :is="wfForm"
      class="dc-component_ElImport"
    ></component>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { listHrmUserLaidPage, deleteHrmUserLaid } from '@/api/hrm/hrmUserLaid'

import { listResourcePermission } from '@/api/admin/common/permission'
import { getHistProcInstance } from '@/api/wf/procinst'
import { getProcessByKey, getStartForm } from '@/api/wf/processdefinition'

/** 根据用户界面配置import组件 开始 */
import History from '@/views/components/history'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import { listHrmLaidTypeAll } from '@/api/hrm/hrmLaidType.js'
import processPanel from '@/views/wf/common/processPanel'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
import Comment from '@/views/wf/common/comment'
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import WfMainUI from '@/views/wf/common/wfMainUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: WfMainUI,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    QueryTag,
    QueryConditionPanel,
    processPanel,
    ViewColumnsSelect,
    Comment,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon
  },
  data() {
    return {
      wfForm: null, // 工作流表单
      currentRow: null, // 当前查看申请

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
        laidtype: {
          id: null,
          name: null
        },
        name: '' // 编号
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        179: {
          title: '编号',
          field: 'name',
          visible: true,
          order: 0
        },
        139: {
          title: '离职类型',
          field: 'laidtype.name',
          visible: true,
          order: 1
        },
        135: {
          title: '离职人员',
          field: 'user.name',
          visible: true,
          order: 2
        },
        198: {
          title: '工号',
          field: 'user.empolyno',
          visible: true,
          order: 1,
          width: 150
        },
        132: {
          title: '手机号',
          field: 'user.phone',
          visible: true,
          order: 3
        },
        199: {
          title: '邮箱',
          field: 'user.email',
          width: 150,
          visible: true,
          order: 4
        },
        140: {
          title: '离职主要原因',
          field: 'laidmaincause.value',
          visible: true,
          order: 1
        },
        143: {
          title: '离职原因',
          field: 'laidcause',
          visible: true,
          order: 2
        },
        144: {
          title: '交接人',
          field: 'handinguserid.name',
          visible: true,
          order: 3
        },
        151: {
          title: '离职申请日期',
          field: 'laidapplydate',
          visible: true,
          order: 4
        },
        152: {
          title: '最后工作日',
          field: 'lastworkdate',
          visible: true,
          order: 5
        },
        153: {
          title: '离职办理日期',
          field: 'laiddealdate',
          visible: true,
          order: 6
        },
        110: {
          title: '状态',
          field: 'status',
          visible: true,
          order: 6
        },
        172: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 7
        },
        173: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 8
        },
        174: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 9
        },
        175: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 10
        },
        176: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 11
        },
        114: {
          title: '操作',
          width: '140px',
          isShow: true,
          visible: true,
          order: 12
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 离职类型选项
      laidtypeOptions: [],
      procStatus:[
        {
          'name':'已审批',
          'value':'8044'
        },
        {
          'name':'审批中',
          'value':'8045'
        },
        {
          'name':'已终止',
          'value':'8046'
        },
        {
          'name':'草稿',
          'value':'8047'
        }
      ],
      /** 根据用户界面配置生成data数据 结束 */
      version: 30,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1457254743943433245',
      schemeId: '1457254743943433243'
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = [
        {
          columnName: 'user.id',
          queryType: '=',
          value: this.currentUser.id
        }
      ]
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'proc_status',
          queryType: '=',

          value: !validatenull(this.queryFormData.status) ? this.queryFormData.status : null
        })
      }
      // 数据权限: 离职申请hrm_user_laid
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
    showStatus(row,rowIndex){
      if(row.procStatus.id === '8045'){
        return "审批中";
      }
      if(row.procStatus.id === '8044' && (row.handingstatus === '0' || row.handingstatus === '1')){
        return "交接中";
      }
      if(row.handingstatus === '2' && (row.adminaffairsstatus === '0' || row.adminaffairsstatus === '1')){
        return "行政事务办理中";
      }
      if(row.adminaffairsstatus === '2' && (row.financeaffairsstatus === '0' || row.financeaffairsstatus === '1')){
        return "财务办理中";
      }
      if(row.financeaffairsstatus === '2' && (row.humanaffairsstatus === '0' || row.humanaffairsstatus === '1')){
        return "人事办理中";
      }
      if(row.laidstatus === '1'){
        return "已离职";
      }
      return "";
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
            columnName: 'user.id',
            queryType: '=',
            value: this.currentUser.id
          }
        ]
        // 数据权限: 离职申请hrm_user_laid
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listHrmUserLaidRespData, listPermissionRespData] = await Promise.all([
          listHrmUserLaidPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listHrmUserLaidRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableDataTotal = listHrmUserLaidRespData.data.total
          this.tableData = listHrmUserLaidRespData.data.rows ? listHrmUserLaidRespData.data.rows : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmUserLaid:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmUserLaid:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmUserLaid:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmUserLaid:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmUserLaid:delete'
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
      this.setLoad()
      getHistProcInstance(scope.row.procInst)
        .then((responseData) => {
          if (responseData.hasOwnProperty('processDefinitionId')) {
            let definitionId = responseData.processDefinitionId
            if (definitionId) {
              getStartForm(definitionId).then((startFormResp) => {
                if (startFormResp.hasOwnProperty('key')) {
                  if (startFormResp.key) {
                    this.currentRow = {
                      formKey: startFormResp.key,
                      definitionId: definitionId,
                      row: scope.row
                    }
                    this.loadWfForm(startFormResp.key)
                  } else {
                    this.showMessage({
                      type: 'warning',
                      msg: '流程未配置form，请联系管理员。'
                    })
                  }
                } else {
                  this.showMessage(startFormResp)
                }
                this.resetLoad()
              })
            } else {
              this.showMessage({
                type: 'warning',
                msg: '流程实例没有找到流程定义。'
              })
            }
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 重写dcMain的方法
    onShowHistory(scope) {
      this.$refs.commentForm.$emit('openComment', scope.row.procInst)
    },
    afterWfFormload() {
      this.$refs.wfForm.$emit('openViewBizDialog', this.currentRow.formKey, this.currentRow.definitionId, this.currentRow.row)
    },
    onVLoading(loading) {
      loading ? this.setLoad() : this.resetLoad()
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
    resetField(){
      this.queryFormData = {}
      this.getList();
    },
    listLaidtypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 离职类型hrm_laid_type
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943433140')

      listHrmLaidTypeAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.laidtypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.listLaidtypeOptions()
    }
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
