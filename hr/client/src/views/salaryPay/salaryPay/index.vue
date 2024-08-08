<template>
  <el-row class="dc-container" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      :applyStatetion="applyStatetion":auditStatetion="auditStatetion":payStatetion="payStatetion"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
<!--    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">-->
<!--      <el-col :span="24">-->
<!--        <query-tag-->
<!--          ref="queryTag"-->
<!--          v-model="queryEditObject"-->
<!--          :moreParm="moreParm"-->
<!--          :routerId="$route && $route.meta.routerId"-->
<!--          :isQueryConditionPanel="isQueryConditionPanel"-->
<!--          v-on:queryTagPanel="onQueryTagPanel"-->
<!--          v-on:queryTagClick="onQueryTagClick"-->
<!--          v-on:search="onSearch"-->
<!--          class="dc-query-tag_ElConditionTag"-->
<!--        ></query-tag>-->
<!--      </el-col>-->
<!--      <el-col :span="6">-->
<!--        <div style="text-align: right">-->
<!--          <el-button-group class="dc-el-button-group_ButtonGroup">-->
<!--            <el-tooltip effect="dark" content="搜索" placement="top">-->
<!--              <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>-->
<!--            </el-tooltip>-->
<!--            <el-tooltip effect="dark" content="重置" placement="top">-->
<!--              <el-button-->
<!--                v-on:click="isQueryConditionPanel ? $refs.conditionPanel.reset() : $refs.queryForm.resetFields()"-->
<!--                type="primary"-->
<!--                icon="el-icon-refresh-right"-->
<!--              ></el-button>-->
<!--            </el-tooltip>-->
<!--            <el-tooltip effect="dark" content="更多" placement="top">-->
<!--              <el-button v-on:click="onQueryConditionPanel('query')" type="primary" icon="el-icon-d-arrow-right"></el-button>-->
<!--            </el-tooltip>-->
<!--          </el-button-group>-->
<!--        </div>-->
<!--      </el-col>-->
<!--    </el-row>-->
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
        <el-col :span="5">
          <el-form-item prop="saBatchCode" label="薪资批次号：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.saBatchCode"
              :maxLength="100"
              placeholder="请输入薪资批次号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="applyState" label="申请状态：" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.applyState"
              :style="{ width: '100%' }"
              placeholder="请选择审核申请状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in applyStateOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="auditState" label="审核状态：" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.auditState"
              :style="{ width: '100%' }"
              placeholder="请选择审核状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in auditStateOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-form-item prop="billYearMonth" label="账单年月：" class="dc-el-form-item_PickerMonthRange">
            <el-date-picker
              v-model="queryFormData.billYearMonth"
              :style="{ width: '100%' }"
              type="monthrange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM"
              value-format="yyyyMM"
              class="dc-el-date-picker_PickerMonthRange"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="payState" label="发放状态：" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.payState"
              :style="{ width: '100%' }"
              placeholder="请选择发放状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in payStateOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="1">
          &nbsp;
        </el-col>
        <el-col :span="2">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-search" v-on:click="onSearch" class="dc-el-button_Button">
              查询
            </el-button>
          </el-tooltip>
        </el-col>
        <el-col :span="2">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-refresh-right" v-on:click="isQueryConditionPanel ? $refs.conditionPanel.reset() : $refs.queryForm.resetFields()" class="dc-el-button_Button">
              重置
            </el-button>
          </el-tooltip>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
      </el-tooltip>
    </el-row>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-s-operation" v-on:click="onEdit"
                   class="dc-el-button_Button">
          申请发放
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-search" v-on:click="onView" class="dc-el-button_Button">
          查看详情
        </el-button>
      </el-tooltip>
      <el-dialog :visible.sync="dialogView" class="uploadDialog"></el-dialog>
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
      @row-click="singleElection"
    >
      <ux-table-column align="center" header-align="center" type="selection" width="35" fixed="left">
        <template slot-scope="scope">
          <!-- 可以手动的修改label的值，从而控制选择哪一项 -->
          <el-radio class="radio" v-model="templateSelection" :label="scope.row.id"
          ></el-radio>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
        tree-node
        resizable
        min-width="150px"
        align="left"
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
        :field="tableColumns['156'].field"
        :title="tableColumns['156'].title"
        :width="tableColumns['156'].width"
        :visible="tableColumns['156'].visible"
        :params="{ sortId: '156', summary: false }"
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
        :field="tableColumns['136'].field"
        :title="tableColumns['136'].title"
        :width="tableColumns['136'].width"
        :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }"
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
        :field="tableColumns['145'].field"
        :title="tableColumns['145'].title"
        :width="tableColumns['145'].width"
        :visible="tableColumns['145'].visible"
        :params="{ sortId: '145', summary: false }"
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
        :field="tableColumns['165'].field"
        :title="tableColumns['165'].title"
        :width="tableColumns['165'].width"
        :visible="tableColumns['165'].visible"
        :params="{ sortId: '165', summary: false }"
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
        :field="tableColumns['150'].field"
        :title="tableColumns['150'].title"
        :width="tableColumns['150'].width"
        :visible="tableColumns['150'].visible"
        :params="{ sortId: '150', summary: false }"
        tree-node
        resizable
        min-width="80px"
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
        min-width="80px"
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
        min-width="80px"
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
import { getDictTypeById } from "@/api/sys/dictType";

import { validatenull } from '@/utils/validate'

import { listSalaryBatchPage, deleteSalaryBatch } from '@/api/salaryPay/salaryBatch'

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
        saBatchCode: '', // 薪资批次号
        applyState: null, // 申请状态：
        auditState: null, // 审核状态：
        payState: null, // 发放状态：
        billYearMonth: [] //账单年月
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        131: {
          title: '薪资批次号',
          field: 'saBatchCode',
          visible: true,
          order: 0
        },
        144: {
          title: '账单年月 ',
          field: 'billYearMonth',
          visible: true,
          order: 1
        },
        156: {
          title: '账单金额',
          field: 'totalAmount',
          visible: true,
          order: 2
        },
        136: {
          title: '工资所属月',
          field: 'payYearMonth',
          visible: true,
          order: 3
        },
        145: {
          title: '报税所属期',
          field: 'taxMonth',
          visible: true,
          order: 4
        },
        165: {
          title: '申请发放日期',
          field: 'applyPayDate',
          visible: true,
          order: 5
        },
        150: {
          title: '申请状态',
          field: 'applyState',
          visible: true,
          order: 6
        },
        151: {
          title: '审核状态',
          field: 'auditState',
          visible: true,
          order: 7
        },
        173: {
          title: '账单确认',
          field: 'payState',
          visible: true,
          order: 8
        },
        114: {
          title: '操作',
          width: '140px',
          visible: true,
          order: 9
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 申请状态：选项
      applyStateOptions: [
        {
          label: '未申请',
          value: '0'
        },
        {
          label: '已申请',
          value: '1'
        }
      ],
      // 审核状态：选项
      auditStateOptions: [
        {
          label: '待审核',
          value: '0'
        },
        {
          label: '已通过',
          value: '1'
        },
        {
          label: '未通过（驳回）',
          value: '2'
        },
        {
          label: '已完成',
          value: '3'
        }
      ],
      // 发放状态：选项
      payStateOptions: [
        {
          label: '待发放',
          value: '0'
        },
        {
          label: '已发放',
          value: '1'
        }
      ],
      /** 根据用户界面配置生成data数据 结束 */
      version: 4,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      tableId: '1740772218121938768',
      schemeId: '1740772218121938766',
      applyStatetion: [],
      auditStatetion: [],
      payStatetion: [],
      selectData: [],
      //   当前选择的行的id
      templateSelection: "",
      //   当前选择的行的数据
      checkList: [],
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
          columnName: 'SA_BATCH_CODE',
          queryType: 'like',

          value: !validatenull(this.queryFormData.saBatchCode) ? this.queryFormData.saBatchCode : null
        })

        this.search.params.push({
          columnName: 'APPLY_STATE',
          queryType: '=',

          value: !validatenull(this.queryFormData.applyState) ? this.queryFormData.applyState : null
        })

        this.search.params.push({
          columnName: 'AUDIT_STATE',
          queryType: '=',

          value: !validatenull(this.queryFormData.auditState) ? this.queryFormData.auditState : null
        })

        this.search.params.push({
          columnName: 'PAY_STATE',
          queryType: '=',

          value: !validatenull(this.queryFormData.payState) ? this.queryFormData.payState : null
        })

        this.search.params.push({
          columnName: 'BILL_YEAR_MONTH',
          queryType: '>=',
          value: this.queryFormData.billYearMonth[0]
        },
          {
            columnName: 'BILL_YEAR_MONTH',
            queryType: '<=',
            value: this.queryFormData.billYearMonth[1]
          })
      }
      // 数据权限: 员工薪资发放批次表salary_batch
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listSalaryBatchPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {

            this.tableDataTotal = responseData.data.total
            let arr = responseData.data.rows ? responseData.data.rows : []
            arr.map(item => {
              let obj = this.applyStatetion.find(it => it.value === item.applyState)
              item.applyState = obj ? obj.name : item.applyState
              let obj1 = this.auditStatetion.find(it => it.value === item.auditState)
              item.auditState = obj1 ? obj1.name : item.auditState
              let obj2 = this.payStatetion.find(it => it.value === item.payState)
              item.payState = obj2 ? obj2.name : item.payState
              return item
            })
            this.tableData = JSON.parse(JSON.stringify(arr))
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
      this.initOptions(this.queryModel)
      this.onSearch()
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === 'salaryBatch:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'salaryBatch:export'
            })

            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'salaryBatch:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'salaryBatch:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'salaryBatch:delete'
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
    onView() {
      if (this.selectData.length === 0) {
        this.$message.warning('请选择！')
      }else {
        // console.log(this.selectData)
        this.$refs.editForm.$emit('openViewDialog', this.selectData.id)
      }
      // this.$refs.editForm.$emit('openViewDialog', scope.row.id)
    },

    onCreate() {
      this.$refs.editForm.$emit('openAddDialog')
    },
    onEdit() {
      if (this.selectData.length === 0) {
        this.$message.warning('请选择！')
      }else {
        // console.log(this.selectData)
        this.$refs.editForm.$emit('openEditDialog', this.selectData.id)
      }
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
          deleteSalaryBatch(scope.row)
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

    isDisabled(row) {
      return this.selectedRow && !row.selected
    },
    singleElection(row) {
      this.templateSelection = row.id
      this.selectData = row
      this.checkList = this.tableData.filter((item) => item.id === row.id)
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
    getApplyStatetion() {

      getDictTypeById("1923313087036954250").then(res => {
        this.applyStatetion = res.data.dictItemList;
      });
    },
    getAuditStatetion() {
      getDictTypeById("1923313087036954264").then(res => {
        this.auditStatetion = res.data.dictItemList;
      });
    },
    getPayStatetion() {
      getDictTypeById("1923313087036954280").then(res => {
        this.payStatetion = res.data.dictItemList;
      });
    },
    wait() {
      setTimeout(() => {
        // 这里写需要等待后执行的逻辑
        console.log('等待结束');
      }, 1000);
    },
    // 初始化自定义类型选择框选项变量
    initOptions(This) {}
  },
  watch: {},
  beforeMount() {
  },
  mounted() {
    this.getApplyStatetion()
    this.getAuditStatetion()
    this.getPayStatetion()
    this.pageInit()
    this.columnDrop(this.$refs.table)
  }
}
</script>
