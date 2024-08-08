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
        <div style="text-align: right">
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
              <el-button v-on:click="onQueryConditionPanel('query')" type="primary" icon="el-icon-d-arrow-right"></el-button>
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
        <el-col :span="6">
          <el-form-item prop="systemCode" label="系统标识" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.systemCode"
              :style="{ width: '100%' }"
              placeholder="请选择系统标识"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in systemCodeOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
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
        children: 'children',
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
        :field="tableColumns['120'].field"
        :title="tableColumns['120'].title"
        :width="tableColumns['120'].width"
        :visible="tableColumns['120'].visible"
        :params="{ sortId: '120' }"
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
        :field="tableColumns['125'].field"
        :title="tableColumns['125'].title"
        :width="tableColumns['125'].width"
        :visible="tableColumns['125'].visible"
        :params="{ sortId: '125' }"
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
        :field="tableColumns['118'].field"
        :title="tableColumns['118'].title"
        :width="tableColumns['118'].width"
        :visible="tableColumns['118'].visible"
        :params="{ sortId: '118' }"
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
        :field="tableColumns['119'].field"
        :title="tableColumns['119'].title"
        :width="tableColumns['119'].width"
        :visible="tableColumns['119'].visible"
        :params="{ sortId: '119' }"
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
        :field="tableColumns['127'].field"
        :title="tableColumns['127'].title"
        :width="tableColumns['127'].width"
        :visible="tableColumns['127'].visible"
        :params="{ sortId: '127' }"
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
              ((cellValue, row, index) => {
                switch (cellValue) {
                  case '1':
                    return '<span style=' + 'color:red' + '>√</span>'
                  case '0':
                    return ''
                  default:
                    return ''
                }
              })(row.canPermission, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['121'].field"
        :title="tableColumns['121'].title"
        :width="tableColumns['121'].width"
        :visible="tableColumns['121'].visible"
        :params="{ sortId: '121' }"
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
        :field="tableColumns['122'].field"
        :title="tableColumns['122'].title"
        :width="tableColumns['122'].width"
        :visible="tableColumns['122'].visible"
        :params="{ sortId: '122' }"
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
        :field="tableColumns['123'].field"
        :title="tableColumns['123'].title"
        :width="tableColumns['123'].width"
        :visible="tableColumns['123'].visible"
        :params="{ sortId: '123' }"
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
        :field="tableColumns['124'].field"
        :title="tableColumns['124'].title"
        :width="tableColumns['124'].width"
        :visible="tableColumns['124'].visible"
        :params="{ sortId: '124' }"
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
        :field="tableColumns['126'].field"
        :title="tableColumns['126'].title"
        :width="tableColumns['126'].width"
        :visible="tableColumns['126'].visible"
        :params="{ sortId: '126' }"
        tree-node
        resizable
        min-width="180px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['128'].field"
        :title="tableColumns['128'].title"
        :width="tableColumns['128'].width"
        :visible="tableColumns['128'].visible"
        :params="{ sortId: '128' }"
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
              ((cellValue, row, index) => {
                switch (cellValue) {
                  case '1':
                    return '<span style=' + 'color:red' + '>√</span>'
                  case '0':
                    return ''
                  default:
                    return ''
                }
              })(row.isLocked, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
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
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132' }"
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
        :field="tableColumns['133'].field"
        :title="tableColumns['133'].title"
        :width="tableColumns['133'].width"
        :visible="tableColumns['133'].visible"
        :params="{ sortId: '133' }"
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
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134' }"
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
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
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
                    return '后台系统'
                  case '1':
                    return '企业微信'
                  default:
                    return ''
                }
              })(row.systemCode, row, rowIndex)
            "
          ></div>
        </template>
      </ux-table-column>
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
        title="操作"
        :params="{ sortId: '112' }"
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
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="0" class="dc-el-popover_ElPopover">
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
            v-on:click="onCreate(scope)"
            type="primary"
            v-show="permission.add"
            content="添加下级"
            placement="top"
            icon-name="el-icon-circle-plus-outline"
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
            v-show="permission.remove && (!scope.row.children || scope.row.children.length <= 0)"
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
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { treeRouter, deleteRouter } from '@/api/admin/router'

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
      queryFormData: {
        name: '', // 名称
        systemCode: null // 系统标识
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        120: {
          title: '父级',
          field: 'parent.name',
          visible: false,
          order: 0
        },
        125: {
          title: '名称',
          field: 'name',
          visible: true,
          order: 1
        },
        118: {
          title: '代码',
          field: 'code',
          visible: true,
          order: 2
        },
        119: {
          title: 'URL',
          field: 'url',
          visible: true,
          order: 3
        },
        127: {
          title: '可授权',
          field: 'canPermission',
          visible: true,
          order: 4
        },
        121: {
          title: '父级.URL',
          field: 'parent.url',
          visible: false,
          order: 5
        },
        122: {
          title: '父级.编号',
          field: 'parent.code',
          visible: false,
          order: 6
        },
        123: {
          title: '父级.名称',
          field: 'parent.name',
          visible: false,
          order: 7
        },
        124: {
          title: '全标识',
          field: 'ids',
          visible: false,
          order: 8
        },
        126: {
          title: '显示序号',
          field: 'sort',
          visible: true,
          order: 9
        },
        128: {
          title: '禁用',
          field: 'isLocked',
          visible: true,
          order: 10
        },
        129: {
          title: '扩展属性',
          field: 'properties',
          visible: true,
          order: 11
        },
        130: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 12
        },
        131: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 13
        },
        132: {
          title: '创建时间',
          field: 'createDate',
          visible: false,
          order: 14
        },
        133: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 15
        },
        134: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 16
        },
        135: {
          title: '系统标识',
          field: 'systemCode',
          visible: true,
          order: 17
        },
        136: {
          title: '图片地址',
          field: 'iconId',
          visible: true,
          order: 18
        },
        112: {
          title: '操作',
          width: '160px',
          visible: true,
          order: 19
        }
      },

      // 选项变量
      // 系统标识选项
      systemCodeOptions: [
        {
          label: '后台系统',
          value: '0'
        },
        {
          label: '企业微信',
          value: '1'
        }
      ],
      /** 根据用户界面配置生成data数据 结束 */
      version: 62,

      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],

      tableId: '1289036439920697347',
      schemeId: '1289036439920697345'
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'name',
          queryType: 'like',

          value: !validatenull(this.queryFormData.name) ? this.queryFormData.name : null
        })

        this.search.params.push({
          columnName: 'system_code',
          queryType: '=',

          value: !validatenull(this.queryFormData.systemCode) ? this.queryFormData.systemCode : null
        })
      }
      // 数据权限: 路由sys_router
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      treeRouter(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableData = responseData.data ? responseData.data : []
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
        this.getList()
      } else {
        this.$refs['queryForm'].validate((valid) => {
          if (valid) {
            this.getList()
          } else {
            return false
          }
        })
      }
    },
    async pageInit() {
      this.setLoad()
      try {
        this.initOptions(this.queryModel)
        this.search.params = []
        // 数据权限: 路由sys_router
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listRouterRespData, listPermissionRespData] = await Promise.all([
          treeRouter(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listRouterRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableData = listRouterRespData.data ? listRouterRespData.data : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'router:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'router:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'router:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'router:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'router:delete'
          })
        } else {
          this.showMessage(listPermissionRespData.code != 100 ? listPermissionRespData : listRouterRespData)
        }
        this.resetLoad()
      } catch (error) {
        this.outputError(error)
      }
    },
    onView(scope) {
      this.$refs.editForm.$emit('openViewDialog', scope.row.id)
    },

    onCreate(scope) {
      this.$refs.editForm.$emit('openAddDialog', scope.row)
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
          deleteRouter(scope.row)
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
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
