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
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item prop="salaryYearAndMonth" label="薪资年月" class="dc-el-form-item_PickerTimeDate">
            <el-date-picker
              v-model="queryFormData.salaryYearAndMonth"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="month"
              format="yyyyMM"
              value-format="yyyyMM"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
          <el-col :span="8">
            <div style="text-align: right">
              <el-button-group class="dc-el-button-group_ButtonGroup">
                <el-tooltip effect="dark" content="搜索" placement="top">
                  <el-button style="margin-right: 20px" v-on:click="onSearch" type="primary" icon="el-icon-search">搜索</el-button>
                </el-tooltip>
                <el-tooltip effect="dark" content="重置" placement="top">
                  <el-button
                    v-on:click="reset()"
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
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-upload2" class="dc-el-button_Button" @click="importData">导入
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-download" class="dc-el-button_Button" @click="downloadTemp">
          导入模板下载
        </el-button>
      </el-tooltip>
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
          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10"
                      class="dc-el-popover_ElPopover">
            <view-columns-select
              v-model="tableColumns"
              ref="viewColumnsSelect"
              v-on:save-column-view="saveColumn($event, 'table')"
              v-on:show-all-column="showAllColumn($event, 'table')"
              v-on:show-default-column="showDefaultColumn('table')"
              class="dc-view-columns-select_ViewColumnsSelect"
            ></view-columns-select>
            <template slot="reference">
              <OperationIcon type="primary" content="自定义列" placement="top"
                             icon-name="el-icon-tickets"></OperationIcon>
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
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove"
            content="删除"
            placement="top"
            icon-name="el-icon-delete"
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
import {validatenull} from '@/utils/validate'

import {
  listHrmSalaryUploadSummaryPage,
  deleteHrmSalaryUploadSummary,
  downloadTemplate,
  fileUpload
} from '@/api/hrm/hrmSalaryUploadSummary'

import {listResourcePermission} from '@/api/admin/common/permission'

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
        salaryYearAndMonth: '' // 薪资年月
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        132: {
          title: '薪资年月',
          field: 'salaryYearAndMonth',
          visible: true,
          order: 0
        },
        133: {
          title: '薪资人数',
          field: 'salaryPeopleNumber',
          visible: true,
          order: 1
        },
        139: {
          title: '上传人',
          field: 'createBy',
          visible: true,
          order: 2
        },
        140: {
          title: '上传时间',
          field: 'createDate',
          visible: true,
          order: 3
        },
        114: {
          title: '操作',
          width: '140px',
          isShow: true,
          visible: true,
          order: 4
        }
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
      tableId: '1457254743943434047',
      schemeId: '1457254743943434045'
    }
  },
  computed: {},
  methods: {
    importData: function () {
      const fileInput = document.createElement('input');
      fileInput.style = 'display:none'
      fileInput.setAttribute("type", "file");
      fileInput.setAttribute("accept", ".xls,.xlsx");
      fileInput.click()
      fileInput.addEventListener('change', (e) => this.fileToData(e))
    },
    fileToData(e){
      let formData = new FormData()
      formData.append('file', e.target.files[0])
      fileUpload(formData).then(resp => {
        if(resp.code == 100){
          //请求成功,处理完了数据刷新列表
          this.getList();
        }
      }).catch((error) => {
        this.outputError(error)
      })
    },
    downloadTemp() {
      downloadTemplate().then(resp => {
        const blob = new Blob([resp], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'})
        const link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = '工资条.xlsx'
        link.click();
        URL.revokeObjectURL(link.href);
      })
    },
    getList() {
      this.setLoad()
      /* 查询参数 和数据权限 */
      this.search.params = []
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(this.compositeCondition())
      } else {
        this.search.params.push({
          columnName: 'salary_year_and_month',
          queryType: '=',

          value: !validatenull(this.queryFormData.salaryYearAndMonth) ? this.queryFormData.salaryYearAndMonth : null
        })
      }
      // 数据权限: 薪资条账单汇总表hrm_salary_upload_summary
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listHrmSalaryUploadSummaryPage(this.search)
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
      try {
        this.initOptions(this.queryModel)
        this.search.params = []
        // 数据权限: 薪资条账单汇总表hrm_salary_upload_summary
        this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
        let [listHrmSalaryUploadSummaryRespData, listPermissionRespData] = await Promise.all([
          listHrmSalaryUploadSummaryPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ])
        if (listHrmSalaryUploadSummaryRespData.code == 100 && listPermissionRespData.code == 100) {
          this.tableDataTotal = listHrmSalaryUploadSummaryRespData.data.total
          this.tableData = listHrmSalaryUploadSummaryRespData.data.rows ? listHrmSalaryUploadSummaryRespData.data.rows : []
          this.permission.view = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmSalaryUploadSummary:read'
          })
          this.permission.export = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmSalaryUploadSummary:export'
          })

          this.permission.add = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmSalaryUploadSummary:create'
          })
          this.permission.edit = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmSalaryUploadSummary:update'
          })
          this.permission.remove = listPermissionRespData.data.find((item) => {
            return item.permission === 'hrmSalaryUploadSummary:delete'
          })
        } else {
          this.showMessage(listPermissionRespData.code != 100 ? listPermissionRespData : listHrmSalaryUploadSummaryRespData)
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
          deleteHrmSalaryUploadSummary(scope.row)
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
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
