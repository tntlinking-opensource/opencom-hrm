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
    <el-container direction="horizontal" class="dc-el-container_ElContainer">
      <el-aside width="200px" height="60px">
        <el-row>
          <list-tree
            ref="listTree"
            border
            v-on:current-change="onChangeCurrentRow"
            title="路由"
            field="name"
            align="left"
            header-align="left"
            show-overflow
            :tree-config="{
              children: 'children',
              indent: 20,
              accordion: false,
              line: false,
              showIcon: true,
              iconOpen: '',
              iconClose: ''
            }"
            :api="{
              baseURL: 'BASE_API',
              method: 'post',
              url: 'admin/router/tree',
              params: [],
              data: 'return {\n        params: [],\n      }',
              reqBefore:
                '\nconfig.headers[&quot;X-Token&quot;] = sessionStorage.getItem(&quot;token&quot;)\n    return config\n',
              reqResponse: 'if (response.data.code == 100) {\n          \n\treturn response.data.data\n}',
              reqError: 'console.error(error)'
            }"
            class="dc-list-tree_ListTree"
          ></list-tree>
        </el-row>
      </el-aside>
      <el-main>
        <el-row>
          <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
            <el-col :span="24">
              <query-tag
                ref="queryTag"
                v-model="queryEditObject"
                :moreParm="moreParm"
                :routerId="$route && $route.meta.routerId"
                :isQueryConditionPanel="isQueryConditionPanel"
                v-on:queryTagPanel="onQueryTagPanel"
                v-on:queryTagClick="onQueryTagClick"
                v-on:search="onSearch"
                class="dc-query-tag_ElConditionTag"
              ></query-tag>
            </el-col>
            <el-col :span="6">
              <div style="text-align: left">
                <el-button-group class="dc-el-button-group_ButtonGroup">
                  <el-tooltip effect="dark" content="搜索" placement="top">
                    <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>
                  </el-tooltip>
                  <el-tooltip effect="dark" content="重置" placement="top">
                    <el-button
                      v-on:click="isQueryConditionPanel ? $refs.conditionPanel.reset() : $refs.queryForm.resetFields()"
                      type="primary"
                      icon="el-icon-refresh-right"
                    ></el-button>
                  </el-tooltip>
                  <el-tooltip effect="dark" content="更多" placement="top">
                    <el-button
                      v-on:click="onQueryConditionPanel('query')"
                      type="primary"
                      icon="el-icon-d-arrow-right"
                    ></el-button>
                  </el-tooltip>
                </el-button-group>
              </div>
            </el-col>
          </el-row>
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
                <el-form-item prop="name" label="名称" class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="queryFormData.name"
                    :maxLength="128"
                    placeholder="请输入名称"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-plus" v-on:click="onFastCreateResource">快速添加</el-button>
            </el-tooltip>
            <el-tooltip disabled class="dc-el-tooltip_Button">
              <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
            </el-tooltip>
          </el-row>
          <ux-grid
            column-key
            ref="table"
            :data="tableData"
            border
            v-on:sort-change="onSortChange"
            v-on:header-dragend="onWidthChange($refs.table)"
            :tree-config="{
              children: '',
              indent: 20,
              accordion: false,
              line: false,
              showIcon: true,
              iconOpen: '',
              iconClose: ''
            }"
            class="dc-ux-grid_QueryTable"
          >
            <ux-table-column
              :field="tableColumns['128'].field"
              :title="tableColumns['128'].title"
              :width="tableColumns['128'].width"
              :visible="tableColumns['128'].visible"
              :params="{ sortId: '128' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['129'].field"
              :title="tableColumns['129'].title"
              :width="tableColumns['129'].width"
              :visible="tableColumns['129'].visible"
              :params="{ sortId: '129' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['130'].field"
              :title="tableColumns['130'].title"
              :width="tableColumns['130'].width"
              :visible="tableColumns['130'].visible"
              :params="{ sortId: '130' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['131'].field"
              :title="tableColumns['131'].title"
              :width="tableColumns['131'].width"
              :visible="tableColumns['131'].visible"
              :params="{ sortId: '131' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['133'].field"
              :title="tableColumns['133'].title"
              :width="tableColumns['133'].width"
              :visible="tableColumns['133'].visible"
              :params="{ sortId: '133' }"
              tree-node
              resizable
              min-width="70px"
              align="center"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((cellValue, row, index) => {
                      switch (cellValue) {
                        case '1':
                          return '<span style=' + 'color:red' + '>√</span>'
                        case '0':
                          return ''
                        default:
                          return ''
                      }
                    })(row.isDefault, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['132'].field"
              :title="tableColumns['132'].title"
              :width="tableColumns['132'].width"
              :visible="tableColumns['132'].visible"
              :params="{ sortId: '132' }"
              tree-node
              resizable
              min-width="70px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['134'].field"
              :title="tableColumns['134'].title"
              :width="tableColumns['134'].width"
              :visible="tableColumns['134'].visible"
              :params="{ sortId: '134' }"
              tree-node
              resizable
              min-width="70px"
              align="center"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
              <template slot-scope="{ row, rowIndex }">
                <div
                  v-html="
                    ((cellValue, row, index) => {
                      switch (cellValue) {
                        case '0':
                          return ''
                        case '1':
                          return '<span style=' + 'color:red' + '>√</span>'
                        default:
                          return ''
                      }
                    })(row.isLocked, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['135'].field"
              :title="tableColumns['135'].title"
              :width="tableColumns['135'].width"
              :visible="tableColumns['135'].visible"
              :params="{ sortId: '135' }"
              tree-node
              resizable
              min-width="120px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['136'].field"
              :title="tableColumns['136'].title"
              :width="tableColumns['136'].width"
              :visible="tableColumns['136'].visible"
              :params="{ sortId: '136' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
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
              :params="{ sortId: '137' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
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
              :params="{ sortId: '138' }"
              tree-node
              resizable
              min-width="160px"
              align="right"
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
              :params="{ sortId: '139' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
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
              :params="{ sortId: '140' }"
              tree-node
              resizable
              min-width="160px"
              align="right"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              :field="tableColumns['141'].field"
              :title="tableColumns['141'].title"
              :width="tableColumns['141'].width"
              :visible="tableColumns['141'].visible"
              :params="{ sortId: '141' }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            ></ux-table-column>
            <ux-table-column
              title="操作"
              :params="{ sortId: '119' }"
              tree-node
              resizable
              width="160px"
              min-width="160px"
              fixed="right"
              align="left"
              header-align="center"
              class="dc-ux-table-column_ElTableOptColumn"
            >
              <template v-slot:header="scope">
                <span>操作</span>
                <el-popover
                  ref="popover"
                  width="10"
                  trigger="click"
                  placement="bottom"
                  offset="10"
                  class="dc-el-popover_ElPopover"
                >
                  <view-columns-select
                    v-model="tableColumns"
                    ref="viewColumnsSelect"
                    v-on:save-column-view="saveColumn($event, 'table')"
                    v-on:show-all-column="showAllColumn($event, 'table')"
                    v-on:show-default-column="showDefaultColumn('table')"
                    class="dc-view-columns-select_ViewColumnsSelect"
                  ></view-columns-select>
                  <template slot="reference">
                    <OperationIcon
                      type="primary"
                      content="自定义列"
                      placement="top"
                      icon-name="el-icon-tickets"
                    ></OperationIcon>
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
                  v-on:click="onEdit(scope)"
                  type="primary"
                  v-show="permission.edit"
                  content="编辑"
                  placement="top"
                  icon-name="el-icon-edit"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <OperationIcon
                  v-on:click="onCopy(scope)"
                  type="primary"
                  v-show="permission.add"
                  content="复制"
                  placement="top"
                  icon-name="el-icon-document"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <OperationIcon
                  v-on:click="onDelete(scope)"
                  type="danger"
                  v-show="permission.remove"
                  content="删除"
                  placement="top"
                  icon-name="el-icon-delete"
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
        </el-row>
      </el-main>
    </el-container>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { listResourcePage, deleteResource, saveResource } from '@/api/admin/resource'

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
      parentTable: {},
      queryFormData: {
        name: '' // 名称
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        128: {
          title: '名称',
          field: 'name',
          visible: true,
          order: 0
        },
        129: {
          title: '代码',
          field: 'code',
          visible: false,
          order: 1
        },
        130: {
          title: 'URL',
          field: 'url',
          visible: true,
          order: 2
        },
        131: {
          title: '权限',
          field: 'permission',
          visible: true,
          order: 3
        },
        133: {
          title: '默认权限',
          field: 'isDefault',
          visible: true,
          order: 4
        },
        132: {
          title: '可以授权',
          field: 'canPermission',
          visible: false,
          order: 5
        },
        134: {
          title: '禁用',
          field: 'isLocked',
          visible: true,
          order: 6
        },
        135: {
          title: '路由.名称',
          field: 'router.name',
          visible: false,
          order: 7
        },
        136: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 8
        },
        137: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 9
        },
        138: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 10
        },
        139: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 11
        },
        140: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 12
        },
        141: {
          title: '扩展属性',
          field: 'properties',
          visible: false,
          order: 13
        },
        119: {
          title: '操作',
          width: '160px',
          visible: true,
          order: 14
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 20,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      resourceDefs: [
        {
          name: '查询',
          permission: 'list'
        },
        {
          name: '查看',
          permission: 'read'
        },
        {
          name: '导出',
          permission: 'export'
        },
        {
          name: '添加',
          permission: 'create'
        },
        {
          name: '删除',
          permission: 'delete'
        },
        {
          name: '更新',
          permission: 'update'
        }
      ],
      searchList: [],
      tableId: '1331145570604408856',
      schemeId: '1331145570604408854'
    }
  },
  computed: {},
  methods: {
    onFastCreateResource() {
      if (validatenull(this.parentTable)) {
        this.$alert('请选择路由', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      for (let item in this.resourceDefs) {
        let resource = {
          name: this.resourceDefs[item].name + this.parentTable.name, // 名称
          code: this.parentTable.code, // 代码
          url: this.parentTable.url, // Url
          permission:
            this.parentTable.code.substr(this.parentTable.code.lastIndexOf('/') + 1) + ':' + this.resourceDefs[item].permission, // 权限
          canPermission: '1', // 可授权
          isDefault: 0, // 是否默认资源
          isLocked: 0, // 禁用
          router: {
            // 父表 路由
            id: this.parentTable.id,
            name: this.parentTable.name
          },
          remarks: '' // 备注信息
        }

        this.setLoad()
        saveResource(resource)
          .then((responseData) => {
            this.resetLoad()
            if (this.loadcount == 0) {
              this.getList()
            }
          })
          .catch((error) => {
            this.outputError(error)
            if (this.loadcount == 0) {
              this.getList()
            }
          })
      }
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []

      if (validatenull(this.parentTable)) {
        this.$alert('请选择左侧列表', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      this.search.params.push({
        columnName: 'router_id',
        queryType: '=',
        value: this.parentTable.id
      })

      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'name',
          queryType: 'like',

          value: !validatenull(this.queryFormData.name) ? this.queryFormData.name : null
        })
      }
      // 数据权限: 资源sys_resource
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listResourcePage(this.search)
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
      this.initOptions(this.queryModel)
      this.setLoad()
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === 'resource:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'resource:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'resource:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'resource:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'resource:delete'
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
      if (validatenull(this.parentTable)) {
        this.$alert('请选择左侧列表', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return
      }
      let row = {
        router: {
          id: this.parentTable.id,

          name: this.parentTable.name
        }
      }
      this.$refs.editForm.$emit('openAddDialog', row)
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
          deleteResource(scope.row)
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

    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != oldCurrentRow) {
        this.parentTable = currentRow
        this.initOptions(this.queryModel)
        this.onSearch()
      }
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
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
