<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form ref="queryForm" :model="queryFormData" label-width="100px" class="dc-el-form_ElQueryForm">
      <el-row> <el-col :span="6">
          <el-form-item prop="employeeNo" label="工号/姓名:" class="dc-el-form-item_SingleInput">
            <el-input v-model="queryFormData.employeeNo" :maxLength="64" placeholder="员工工号" clearable
              class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="employeeStatus" label="员工状态:" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.employeeStatus" :style="{ width: '100%' }" placeholder="请选择" clearable
              filterable class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in statusOptions" :key="index" :label="item.name"
                :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="periodStart" label="所得期:" class="dc-el-form-item_SelectInput">
            <el-date-picker v-model="queryFormData.periodStart" type="daterange" value-format='yyyyMMdd'
              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row> <el-row>
        <el-col :span="6">
          <el-form-item prop="taxYearMonth" label="报税年月:" class="dc-el-form-item_SelectInput">
            <el-date-picker v-model="queryFormData.taxYearMonth" value-format='yyyyMM' type="month" style="width: 100%;">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="18"> <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="getList" style="margin-left:20px" icon="el-icon-search"
              class="dc-el-button_Button">查询</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button icon="el-icon-refresh" class="dc-el-button_Button" @click="reset">重置</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button class="dc-el-button_Button" type="primary" @click="uploadData"
              icon="el-icon-upload2">导入</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button class="dc-el-button_Button" type="primary" @click="downloadTemp"
              icon="el-icon-download">模板下载</el-button>
          </el-tooltip>
        </el-col></el-row>
    </el-form>
    <ux-grid column-key ref="table" :data="tableData" border v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      class="dc-ux-grid_QueryTable">
      <ux-table-column :field="tableColumns['130'].field" :title="tableColumns['130'].title"
        :width="tableColumns['130'].width" :visible="tableColumns['130'].visible" fixed="left"
        :params="{ sortId: '130', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['131'].field" :title="tableColumns['131'].title"
        :width="tableColumns['131'].width" :visible="tableColumns['131'].visible" fixed="left"
        :params="{ sortId: '131', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['132'].field" :title="tableColumns['132'].title"
        :width="tableColumns['132'].width" :visible="tableColumns['132'].visible" fixed="left"
        :params="{ sortId: '132', summary: false }" tree-node resizable min-width="140px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['133'].field" :title="tableColumns['133'].title"
        :width="tableColumns['133'].width" :visible="tableColumns['133'].visible" fixed="left"
        :params="{ sortId: '133', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['134'].field" :title="tableColumns['134'].title"
        :width="tableColumns['134'].width" :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['135'].field" :title="tableColumns['135'].title"
        :width="tableColumns['135'].width" :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['136'].field" :title="tableColumns['136'].title"
        :width="tableColumns['136'].width" :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['137'].field" :title="tableColumns['137'].title"
        :width="tableColumns['137'].width" :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['138'].field" :title="tableColumns['138'].title"
        :width="tableColumns['138'].width" :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }" tree-node resizable min-width="130px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['139'].field" :title="tableColumns['139'].title"
        :width="tableColumns['139'].width" :visible="tableColumns['139'].visible"
        :params="{ sortId: '139', summary: false }" tree-node resizable min-width="130px" align="center"
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
        :params="{ sortId: '142', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['143'].field" :title="tableColumns['143'].title"
        :width="tableColumns['143'].width" :visible="tableColumns['143'].visible"
        :params="{ sortId: '143', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['144'].field" :title="tableColumns['144'].title"
        :width="tableColumns['144'].width" :visible="tableColumns['144'].visible"
        :params="{ sortId: '144', summary: false }" tree-node resizable min-width="120px" align="center"
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
        :params="{ sortId: '147', summary: false }" tree-node resizable min-width="140px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['148'].field" :title="tableColumns['148'].title"
        :width="tableColumns['148'].width" :visible="tableColumns['148'].visible"
        :params="{ sortId: '148', summary: false }" tree-node resizable min-width="130px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['149'].field" :title="tableColumns['149'].title"
        :width="tableColumns['149'].width" :visible="tableColumns['149'].visible"
        :params="{ sortId: '149', summary: false }" tree-node resizable min-width="130px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['150'].field" :title="tableColumns['150'].title"
        :width="tableColumns['150'].width" :visible="tableColumns['150'].visible"
        :params="{ sortId: '150', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['151'].field" :title="tableColumns['151'].title"
        :width="tableColumns['151'].width" :visible="tableColumns['151'].visible"
        :params="{ sortId: '151', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['152'].field" :title="tableColumns['152'].title"
        :width="tableColumns['152'].width" :visible="tableColumns['152'].visible"
        :params="{ sortId: '152', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['153'].field" :title="tableColumns['153'].title"
        :width="tableColumns['153'].width" :visible="tableColumns['153'].visible"
        :params="{ sortId: '153', summary: false }" tree-node resizable min-width="140px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['154'].field" :title="tableColumns['154'].title"
        :width="tableColumns['154'].width" :visible="tableColumns['154'].visible"
        :params="{ sortId: '154', summary: false }" tree-node resizable min-width="140px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['155'].field" :title="tableColumns['155'].title"
        :width="tableColumns['155'].width" :visible="tableColumns['155'].visible"
        :params="{ sortId: '155', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['156'].field" :title="tableColumns['156'].title"
        :width="tableColumns['156'].width" :visible="tableColumns['156'].visible"
        :params="{ sortId: '156', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['157'].field" :title="tableColumns['157'].title"
        :width="tableColumns['157'].width" :visible="tableColumns['157'].visible"
        :params="{ sortId: '157', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['158'].field" :title="tableColumns['158'].title"
        :width="tableColumns['158'].width" :visible="tableColumns['158'].visible"
        :params="{ sortId: '158', summary: false }" tree-node resizable min-width="120px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['159'].field" :title="tableColumns['159'].title"
        :width="tableColumns['159'].width" :visible="tableColumns['159'].visible"
        :params="{ sortId: '159', summary: false }" tree-node resizable min-width="120px" align="center"
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
      </ux-table-column>
    </ux-grid>
    <el-pagination :total="tableDataTotal" :page-size="search.limit" background :current-page="tableDataPage"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"></el-pagination>
    <el-dialog title="导入" :visible.sync="dialogVisible" width="30%">
      <div style="display: flex;margin-bottom: 20px;"> <span>报税年月：</span> <el-date-picker v-model="year" type="month"
          value-format='yyyyMM'></el-date-picker></div>
      <div style="display: flex;"> <span>选择文件：</span> <el-button @click="importData" class="dc-el-button_Button"
          type="primary" icon="el-icon-upload2">上传</el-button>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { listSpecialAddDeductPage, downloadTemplate, fileUpload } from '@/api/specialAddDeduct/specialAddDeduct'
/** 根据用户界面配置import组件 开始 */
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import { getDictTypeById } from "@/api/sys/dictType";
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: DcMain,
  components: {
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
        employeeStatus: '',
        periodStart: [],
        taxYearMonth: ''
      },
      tableData: [],
      dialogVisible: false,
      year: '',
      // 查询表格列头
      tableColumns: {
        130: {
          title: '员工工号',
          field: 'employeeNo',
          visible: true,
          order: 0
        },
        131: {
          title: '员工姓名',
          field: 'employeeName',
          visible: true,
          order: 1
        },

        132: {
          title: '身份证号',
          field: 'idCardNo',
          visible: true,
          order: 2
        },
        133: {
          title: '员工状态',
          field: 'employeeStatusName',
          visible: true,
          order: 3
        },
        134: {
          title: '所得期间起',
          field: 'periodStart',
          visible: true,
          order: 4
        },
        135: {
          title: '所得期间止',
          field: 'periodEnd',
          visible: true,
          order: 5
        },
        136: {
          title: '本期收入',
          field: 'currentIncome',
          visible: true,
          order: 6
        },
        137: {
          title: '本期免税收入',
          field: 'currentIncomeNoTax',
          visible: true,
          order: 7
        },
        138: {
          title: '基本养老保险费',
          field: 'baseAnnuity',
          visible: true,
          order: 8
        },
        139: {
          title: '基本医疗保险费',
          field: 'baseIllMedical',
          visible: true,
          order: 9
        },
        140: {
          title: '失业保险费',
          field: 'unemploy',
          visible: true,
          order: 10
        },
        141: {
          title: '住房公积金',
          field: 'accumulaFund',
          visible: true,
          order: 11
        },
        142: {
          title: '累计子女教育',
          field: 'accSubEducation',
          visible: true,
          order: 12
        },
        143: {
          title: '累计继续教育',
          field: 'accKeepEducation',
          visible: true,
          order: 13
        },
        144: {
          title: '累计住房租金',
          field: 'accHouseRent',
          visible: true,
          order: 14
        },
        145: {
          title: '累计住房贷款',
          field: 'accHouseLoanTax',
          visible: true,
          order: 15
        },

        146: {
          title: '累计赡养老人',
          field: 'accCareOld',
          visible: true,
          order: 16
        },
        147: {
          title: '累计3岁以下婴幼儿照护',
          field: 'accBabyPassport',
          visible: true,
          order: 17
        },
        148: {
          title: '累计个人养老金',
          field: 'accPersonAnnuity',
          visible: true,
          order: 18
        },
        149: {
          title: '企业(职业)年金',
          field: 'unitAnnuity',
          visible: true,
          order: 19
        },

        150: {
          title: '商业健康保险',
          field: 'busHealthInsurance',
          visible: true,
          order: 20
        },
        151: {
          title: '税延养老保险',
          field: 'taxDelayOldInsurance',
          visible: true,
          order: 21
        },
        152: {
          title: '其他',
          field: 'otherInsurance',
          visible: true,
          order: 22
        },
        153: {
          title: '准予扣除的捐赠',
          field: 'deductDonateAmount',
          visible: true,
          order: 23
        },
        154: {
          title: '税前扣除项目合计',
          field: 'preTaxDeductTotal',
          visible: true,
          order: 24
        },
        155: {
          title: '减免税额',
          field: 'deductTaxAmount',
          visible: true,
          order: 25
        },
        156: {
          title: '减除费用标准',
          field: 'deductExpenseStandard',
          visible: true,
          order: 26
        },
        157: {
          title: '已缴税额',
          field: 'taxPaidAmount',
          visible: true,
          order: 27
        },
        158: {
          title: '备注',
          field: 'remark',
          visible: true,
          order: 28
        },
        159: {
          title: '导入时间',
          field: 'createDate',
          visible: false,
          order: 29
        },
        114: {
          title: '操作',
          width: '140px',
          visible: true,
          order: 36
        }
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
      tableId: '1683007003934705360',
      schemeId: '1683007003934705358',
      statusOptions: [],

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
          "columnName": "",
          "queryType": "(",
          "value": ``
        })
        this.search.params.push({
          "logic":"",
          "columnName": "employee_no",
          "queryType": "like",
          "value": `%${this.queryFormData.employeeNo}%`
        })
        this.search.params.push({
          "logic":"or",
          "columnName": "employee_name",
          "queryType": "like",
          "value": `%${this.queryFormData.employeeNo}%`
        })
        this.search.params.push({
          "columnName": "",
          "queryType": ")",
          "value": ``
        })
      }
      if (this.queryFormData.employeeStatus) {
        this.search.params.push({
          "columnName": "employee_status",
          "queryType": "=",
          "value": this.queryFormData.employeeStatus
        })
      }
      if (this.queryFormData.taxYearMonth) {
        this.search.params.push({
          "columnName": "tax_year_month",
          "queryType": "=",
          "value": this.queryFormData.taxYearMonth
        })
      }
      if (this.queryFormData.periodStart && this.queryFormData.periodStart.length > 0) {
        this.search.params.push({
          "columnName": "period_start",
          "queryType": "<=",
          "value": this.queryFormData.periodStart[0]
        },
          {
            "columnName": "period_end",
            "queryType": ">=",
            "value": this.queryFormData.periodStart[1]
          },)
      }
      listSpecialAddDeductPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total
            let arr = responseData.data.rows ? responseData.data.rows : []
            arr.map(item => {
              let obj = this.statusOptions.find(it => it.value === item.employeeStatus)
              item.employeeStatusName = obj ? obj.name : item.employeeStatus

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
    reset() {
      this.queryFormData = {
        employeeNo: '',
        employeeStatus: '',
        periodStart: [],
        taxYearMonth: ''
      }
      this.getList()
    },
    // 导入
    importData: function () {
      if (this.year) {
        const fileInput = document.createElement("input");
        fileInput.style = "display:none";
        fileInput.setAttribute("type", "file");
        fileInput.setAttribute("accept", ".xls,.xlsx");
        fileInput.click();
        fileInput.addEventListener("change", (e) => this.fileToData(e));
      } else {
        this.$message.warning('请先选择报税年月')
      }

    },
    fileToData(e) {
      let formData = new FormData();
      formData.append("file", e.target.files[0]);
      fileUpload(formData, this.year)
        .then((resp) => {
          this.showMessage({ type: resp.type, msg: resp.msg });
          this.dialogVisible = false
          this.getList();
        })
        .catch((error) => {
          this.outputError(error);

        });
    },
    onSearch() {
      this.$refs['queryForm'].validate((valid) => {
        if (valid) {
          this.search.offset = 0
          this.tableDataPage = 1
          this.getList()
        } else {
          return false
        }
      })
    },
    //获取员工状态
    getStatus() {
      getDictTypeById("1513455318726787117").then(res => {
        this.statusOptions = res.data.dictItemList;
      });
    },
    uploadData() {
      this.dialogVisible = true
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    // 下载模板
    downloadTemp() {
      downloadTemplate().then((resp) => {
        const blob = new Blob([resp], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8",
        });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "专项附加模版表.xlsx";
        link.click();
        URL.revokeObjectURL(link.href);
      });
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    async pageInit() {
      // this.setLoad()
      this.initOptions(this.queryModel)
      this.onSearch()
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
    initOptions(This) { }
  },
  watch: {},
  mounted() {
    this.pageInit()
    this.getStatus()
    this.columnDrop(this.$refs.table)
  }
}
</script>
