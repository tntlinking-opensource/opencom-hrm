<template>
  <el-row class="dc-container">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form ref="editForm" v-on:save-finished="getList" :permission="permission"></edit-form>
    <el-row gutter="0" type="flex" justify="start" align="top">
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
        ></query-tag>
      </el-col>
      <el-col :span="6">
        <div style="text-align: right">
          <el-button-group>
            <el-tooltip effect="dark" content="搜索" placement="top">
              <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="重置" placement="top">
              <el-button v-on:click="$refs.queryForm.resetFields()" type="primary" icon="el-icon-refresh-right"></el-button>
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
      v-model="moreParm"
      :routerId="$route.meta.routerId"
      :conditionColumns="conditionColumns159"
    ></query-condition-panel>
    <el-form ref="queryForm" :model="queryFormData" size="mini" label-width="100px" v-if="!isQueryConditionPanel">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="name" label="名称">
            <el-input v-model="queryFormData.name" :maxLength="128" placeholder="请输入名称" clearable></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="end" align="top">
      <el-tooltip v-show="permission.add" disabled>
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
      :tree-config="{ children: '', indent: 0, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
    >
      <ux-table-column
        :field="tableColumns['163'].field"
        :title="tableColumns['163'].title"
        :width="tableColumns['163'].width"
        :visible="tableColumns['163'].visible"
        :params="{ sortId: '163' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['164'].field"
        :title="tableColumns['164'].title"
        :width="tableColumns['164'].width"
        :visible="tableColumns['164'].visible"
        :params="{ sortId: '164' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['165'].field"
        :title="tableColumns['165'].title"
        :width="tableColumns['165'].width"
        :visible="tableColumns['165'].visible"
        :params="{ sortId: '165' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['166'].field"
        :title="tableColumns['166'].title"
        :width="tableColumns['166'].width"
        :visible="tableColumns['166'].visible"
        :params="{ sortId: '166' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['167'].field"
        :title="tableColumns['167'].title"
        :width="tableColumns['167'].width"
        :visible="tableColumns['167'].visible"
        :params="{ sortId: '167' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['168'].field"
        :title="tableColumns['168'].title"
        :width="tableColumns['168'].width"
        :visible="tableColumns['168'].visible"
        :params="{ sortId: '168' }"
        tree-node
        resizable
        min-width="160px"
        align="right"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['169'].field"
        :title="tableColumns['169'].title"
        :width="tableColumns['169'].width"
        :visible="tableColumns['169'].visible"
        :params="{ sortId: '169' }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['170'].field"
        :title="tableColumns['170'].title"
        :width="tableColumns['170'].width"
        :visible="tableColumns['170'].visible"
        :params="{ sortId: '170' }"
        tree-node
        resizable
        min-width="160px"
        align="right"
        header-align="center"
        sortable
      ></ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '148' }"
        tree-node
        resizable
        min-width="150px"
        fixed="right"
        align="left"
        header-align="center"
      >
        <template slot="header" slot-scope="scope">
          <span>操作</span>
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10">
            <view-columns-select
              v-model="tableColumns"
              ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')"
              v-on:show-all-column="showAllColumn($event, 'table')"
              v-on:show-default-column="showDefaultColumn('table')"
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
          ></OperationIcon>
          <OperationIcon
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="permission.edit"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onCopy(scope)"
            type="primary"
            v-show="permission.add"
            content="复制"
            placement="top"
            icon-name="el-icon-document"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            v-show="permission.view"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
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
    ></el-pagination>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import { listPropertySetPage, deletePropertySet } from '@/api/sys/propertySet'

import { listResourcePermission } from '@/api/admin/common/permission'

/** 根据用户界面配置import组件 开始 */

import History from '@/views/components/history'
import EditForm from './form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */

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

      /** 根据用户界面配置生成data数据 开始 */

      conditionColumns159: [
        {
          name: 'id',
          comments: '标识',
          tag: 'el-input',
          queryType: 'like',
          operations: [
            { value: 'like', label: '包含' },
            { value: 'not like', label: '不包含' },
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' }
          ],
          attribute: {}
        },

        {
          name: 'name',
          comments: '名称',
          tag: 'el-input',
          queryType: 'like',
          operations: [
            { value: 'like', label: '包含' },
            { value: 'not like', label: '不包含' },
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' }
          ],
          attribute: {}
        },

        {
          name: 'properties_def',
          comments: '属性定义',
          tag: 'el-input',
          queryType: 'like',
          operations: [
            { value: 'like', label: '包含' },
            { value: 'not like', label: '不包含' },
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' }
          ],
          attribute: {}
        },

        {
          name: 'remarks',
          comments: '备注信息',
          tag: 'el-input',
          queryType: 'like',
          operations: [
            { value: 'like', label: '包含' },
            { value: 'not like', label: '不包含' },
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' }
          ],
          attribute: {}
        },

        {
          name: 'create_by',
          comments: '创建者',
          tag: 'el-input',
          queryType: 'like',
          operations: [
            { value: 'like', label: '包含' },
            { value: 'not like', label: '不包含' },
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' }
          ],
          attribute: {}
        },

        {
          name: 'create_date',
          comments: '创建时间',
          tag: 'el-date-picker',
          queryType: '=',
          operations: [
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' },
            { value: 'between', label: '介于' },
            { value: 'not between', label: '不介于' }
          ],
          attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
        },

        {
          name: 'update_by',
          comments: '更新者',
          tag: 'el-input',
          queryType: 'like',
          operations: [
            { value: 'like', label: '包含' },
            { value: 'not like', label: '不包含' },
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' }
          ],
          attribute: {}
        },

        {
          name: 'update_date',
          comments: '更新时间',
          tag: 'el-date-picker',
          queryType: '=',
          operations: [
            { value: '=', label: '等于' },
            { value: '<>', label: '不等于' },
            { value: 'between', label: '介于' },
            { value: 'not between', label: '不介于' }
          ],
          attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
        }
      ],

      queryFormData: {
        name: '' // 名称
      },

      tableData: [],

      // 查询表格列头
      tableColumns: {
        version: 0,

        163: {
          title: '标识',
          field: 'id',

          visible: false,
          order: 0
        },

        164: {
          title: '名称',
          field: 'name',

          visible: true,
          order: 1
        },

        165: {
          title: '属性定义',
          field: 'propertiesDef',

          visible: false,
          order: 2
        },

        166: {
          title: '备注信息',
          field: 'remarks',

          visible: true,
          order: 3
        },

        167: {
          title: '创建者',
          field: 'createBy',

          visible: false,
          order: 4
        },

        168: {
          title: '创建时间',
          field: 'createDate',

          visible: false,
          order: 5
        },

        169: {
          title: '更新者',
          field: 'updateBy',

          visible: false,
          order: 6
        },

        170: {
          title: '更新时间',
          field: 'updateDate',

          visible: true,
          order: 7
        },

        148: {
          title: '操作',

          visible: true,
          order: 8
        }
      },

      // 分页属性

      tableDataPage: 1,

      tableDataTotal: 0,

      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1305565930798669899',
      schemeId: '1305565930798669897'
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
      }
      listPropertySetPage(this.search)
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
      this.setLoad()
      try {
        this.initOptions(this.queryModel)
        this.search.params = []
        // 数据权限: 属性集sys_property_set
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listPropertySetRespData, listPermissionRespData] = await Promise.all([
          listPropertySetPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listPropertySetRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableDataTotal = listPropertySetRespData.data.total
          this.tableData = listPropertySetRespData.data.rows ? listPropertySetRespData.data.rows : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'propertySet:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'propertySet:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'propertySet:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'propertySet:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'propertySet:delete'
          })
        } else {
          this.showMessage(listPermissionRespData.code != 100 ? listPermissionRespData : listPropertySetRespData)
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
          deletePropertySet(scope.row)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.getList()
                this.showMessage({ type: 'success', msg: '删除成功' })
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
