<template>
  <van-row class="dm-container">
    <edit-form ref="editForm" v-on:save-finished="getList" :projectOptions="projectOptions" :blongOptions="blongOptions"
      :cityOptions="cityOptions" v-on:before-load="setLoad" v-on:after-load="resetLoad"></edit-form>
    <el-form ref="queryForm" :model="queryFormData" label-width="100px" style="padding-top: 20px"
      class="dc-el-form_ElQueryForm">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="cityName" label="城市:" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.cityName" :style="{ width: '100%' }" placeholder="请选择" clearable filterable
              class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in cityOptions" :key="index" :label="item.payCityName"
                :value="item.payCityName"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="productType" label="产品类型:" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.productType" :style="{ width: '100%' }" placeholder="请选择" clearable
              filterable class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in projectOptions" :key="index" :label="item.name"
                :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="status" label="所属状态:" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.status" :style="{ width: '100%' }" placeholder="请选择" clearable filterable
              class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in blongOptions" :key="index" :label="item.name"
                :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="employeeNo" label="工号" class="dc-el-form-item_SelectInput">
            <el-input v-model="queryFormData.employeeNo" :maxLength="128" placeholder="请输入工号" clearable
              autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="deptId" label="部门" class="dc-el-form-item_SelectInput">
            <el-select v-model="queryFormData.deptId" :style="{ width: '100%' }" placeholder="请选择" clearable filterable
              class="dc-el-select_SelectInput">
              <el-option v-for="(item, index) in departmentOptions" :key="index" :label="item.name"
                :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24" style="margin-bottom: 10px">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="getList" style="margin-left: 20px" icon="el-icon-search"
              class="dc-el-button_Button">查询</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="reset" icon="el-icon-refresh" class="dc-el-button_Button">重置</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button class="dc-el-button_Button" icon="el-icon-plus" type="primary" @click="onCreate">新增</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="downloadTemp" class="dc-el-button_Button" type="primary"
              icon="el-icon-download">模板下载</el-button>
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button @click="importData" class="dc-el-button_Button" type="primary"
              icon="el-icon-upload2">导入</el-button>
          </el-tooltip>
        </el-col></el-row>
    </el-form>
    <ux-grid column-key ref="table" :data="tableData" border v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)" :tree-config="{
        children: '',
        indent: 20,
        accordion: false,
        line: false,
        showIcon: true,
        iconOpen: '',
        iconClose: '',
      }" class="dc-ux-grid_QueryTable">
      <ux-table-column :field="tableColumns['129'].field" :title="tableColumns['129'].title"
        :width="tableColumns['129'].width" :visible="tableColumns['129'].visible"
        :params="{ sortId: '129', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
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
        :params="{ sortId: '132', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['134'].field" :title="tableColumns['134'].title"
        :width="tableColumns['134'].width" :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn">
        <template v-slot="{ row }">
          {{ getBlongName(row.productType, projectOptions) }}
        </template></ux-table-column>
      <ux-table-column :field="tableColumns['135'].field" :title="tableColumns['135'].title"
        :width="tableColumns['135'].width" :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }" tree-node resizable min-width="150px" align="center"
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
        :params="{ sortId: '139', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['140'].field" :title="tableColumns['140'].title"
        :width="tableColumns['140'].width" :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['141'].field" :title="tableColumns['141'].title"
        :width="tableColumns['141'].width" :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }" tree-node resizable min-width="100px" align="center"
        header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      <ux-table-column :field="tableColumns['142'].field" :title="tableColumns['142'].title"
        :width="tableColumns['142'].width" :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }" tree-node resizable min-width="120px" align="center"
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
          <OperationIcon type="danger" content="停用" @click="stopData(scope)" placement="top"
            icon-name="el-icon-video-pause" class="dc-OperationIcon_IconButton">
          </OperationIcon>
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
import { validatenull } from "@/utils/validate";

import {
  listSocialSecurityFundPage,
  deleteSocialSecurityFund,
} from "@/api/socialSecurityFund/socialSecurityFund";

import EditForm from "./form";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import DcMain from "@/views/components/dcMain";
import OperationIcon from "@/components/OperationIcon";
import { getDictTypeById } from "@/api/sys/dictType";
import {
  listPayCityPage,
  listDefultConfig,
} from "@/api/socialSecurityFund/payCity";
import {
  downloadTemplate,
  fileUpload,
} from "@/api/socialSecurityFund/socialSecurityFund";
import { listDepartmentAll } from "@/api/org/department.js";
import { saveSocialSecurityFund } from "@/api/socialSecurityFund/socialSecurityFund";
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    EditForm,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
  },
  data() {
    return {
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        cityName: "",
        productType: "",
        status: "",
        employeeNo: "",
        deptId: "",
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        129: {
          title: "缴费城市",
          field: "cityName",
          visible: true,
          order: 0,
        },
        130: {
          title: "部门",
          field: "deptName",
          visible: true,
          order: 1,
        },
        131: {
          title: "员工工号",
          field: "employeeNo",
          visible: true,
          order: 2,
        },
        132: {
          title: "员工姓名",
          field: "employeeName",
          visible: true,
          order: 3,
        },
        134: {
          title: "产品类型",
          field: "productType",
          visible: true,
          order: 5,
        },
        135: {
          title: "产品名称",
          field: "productName",
          visible: true,
          order: 6,
        },
        136: {
          title: "公司比例(%)",
          field: "companyPayScale",
          visible: true,
          order: 7,
        },
        137: {
          title: "个人比例(%)",
          field: "personPayScale",
          visible: true,
          order: 8,
        },
        138: {
          title: "公司缴纳基数",
          field: "companyPayBase",
          visible: true,
          order: 9,
        },
        139: {
          title: "个人缴费基数",
          field: "personPayBase",
          visible: true,
          order: 10,
        },
        140: {
          title: "所属状态",
          field: "discountStatusName",
          visible: true,
          order: 1,
        },
        141: {
          title: "状态",
          field: "enableName",
          visible: true,
          order: 12,
        },
        142: {
          title: "备注",
          field: "remark",
          visible: true,
          order: 12,
        },
        114: {
          title: "操作",
          width: "140px",
          visible: true,
          order: 27,
        },
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
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      version: 3,
      tableId: "1641034040897358441",
      schemeId: "1641034040897358439",
      projectOptions: [],
      statusOptions: [],
      blongOptions: [],
      scaleOptions: [],
      cityOptions: [],
      departmentOptions: [],

      dialogVisible: false,
    };
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];

      if (this.queryFormData.cityName) {
        this.search.params.push({
          columnName: "city_name",
          queryType: "=",
          value: this.queryFormData.cityName,
        });
      }
      if (this.queryFormData.productType) {
        this.search.params.push({
          columnName: "product_type",
          queryType: "=",
          value: this.queryFormData.productType,
        });
      }
      if (this.queryFormData.status) {
        this.search.params.push({
          columnName: "discount_status",
          queryType: "=",
          value: this.queryFormData.status,
        });
      }
      if (this.queryFormData.employeeNo) {
        this.search.params.push({
          columnName: "employee_no",
          queryType: "like",
          value: `%${this.queryFormData.employeeNo}%`,
        });
      }
      if (this.queryFormData.deptId) {
        this.search.params.push({
          columnName: "employee.dept_id",
          queryType: "=",
          value: this.queryFormData.deptId,
        });
      }

      listSocialSecurityFundPage(this.search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total;
            this.tableData = responseData.data.rows
              ? responseData.data.rows
              : [];
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    reset() {
      this.queryFormData = {
        cityName: "",
        productType: "",
        status: "",
        employeeNo: "",
        deptId: "",
      };
      this.getList();
    },
    onSearch() {
      this.search.offset = 0;
      this.tableDataPage = 1;
      this.getList();
    },
    stopData(scope) {
      let obj = JSON.parse(JSON.stringify(scope.row));
      obj.enable = "1";
      this.setLoad();
      saveSocialSecurityFund(obj)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.$message.success("该数据已停用！");
            this.getList();
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch((error) => {
          this.outputError(error);
        });
    },
    onSizeChange(val) {
      this.tableDataPage = 1;
      this.search.limit = val;
      this.search.offset = (this.tableDataPage - 1) * val;
      this.getList();
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit;
      this.tableDataPage = val;
      this.getList();
    },
    async pageInit() {
      this.setLoad();
      this.onSearch();
    },
    getBlongName(val, options) {
      let obj = options.find((it) => it.value === val);
      return obj ? obj.name : val;
    },
    listDepartmentOptions() {
      let search_List = {
        params: [],
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "company_id",
          queryType: "=",
          value: currentUser.company.id == "0" ? "" : currentUser.company.id,
        },
      ]);
      // 数据权限: 部门org_department
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1220000000000000014"
      );

      listDepartmentAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },
    onCreate() {
      this.$refs.editForm.$emit("openAddDialog");
    },
    onDelete(scope) {
      this.$confirm("确定删除吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.setLoad();
          deleteSocialSecurityFund(scope.row)
            .then((responseData) => {
              if (responseData.code == 100) {
                this.getList();
                this.showMessage({
                  type: "success",
                  msg: "删除成功",
                });
              } else {
                this.showMessage(responseData);
              }
              this.resetLoad();
            })
            .catch((error) => {
              this.outputError(error);
            });
        })
        .catch(() => { });
    },
    onView(scope) {
      this.$refs.editForm.$emit("openViewDialog", scope.row.id);
    },
    onEdit(scope) {
      this.$refs.editForm.$emit("openEditDialog", scope.row.id);
    },
    onSortChange(orderby) {
      if (validatenull(orderby.prop)) {
        this.search.columnName = "";
        this.search.order = "";
      } else {
        this.search.columnName = orderby.prop;
        this.search.order = orderby.order;
      }
      this.getList();
    },
    //获取产品类型
    getProject() {
      getDictTypeById("1675679033823035407").then((res) => {
        this.projectOptions = res.data.dictItemList;
      });
    },
    //获取状态
    getStatus() {
      getDictTypeById("1675679033823035402").then((res) => {
        this.statusOptions = res.data.dictItemList;
      });
    },
    //获取默认比例
    getScale() {
      getDictTypeById("1675679033823035398").then((res) => {
        this.scaleOptions = res.data.dictItemList;
      });
    },
    //获取所属状态
    getBlongsType() {
      getDictTypeById("1675679033823035393").then((res) => {
        this.blongOptions = res.data.dictItemList;
      });
    },
    //获取城市列表
    getCity() {
      listPayCityPage({ params: [] }).then((res) => {
        if (res.code == 100) {
          this.cityOptions = res.data.rows;
        }
      });
    },
    // 下载模板
    downloadTemp() {
      this.dialogVisible = true;
      downloadTemplate().then((resp) => {
        const blob = new Blob([resp], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8",
        });
        const link = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "社保公积金比例维护模板.xls";
        link.click();
        URL.revokeObjectURL(link.href);
        this.dialogVisible = false;
      });
    },
    // 导入
    importData: function () {
      this.dialogVisible = true;
      const fileInput = document.createElement("input");
      fileInput.style = "display:none";
      fileInput.setAttribute("type", "file");
      fileInput.setAttribute("accept", ".xls,.xlsx");
      fileInput.click();

      fileInput.addEventListener("change", (e) => this.fileToData(e));
      fileInput.addEventListener("cancel", (e) => (this.dialogVisible = false));
    },
    fileToData(e) {
      let formData = new FormData();
      formData.append("file", e.target.files[0]);
      fileUpload(formData)
        .then((resp) => {
          this.showMessage({ type: resp.type, msg: resp.msg });
          this.dialogVisible = false;
          this.getList();
        })
        .catch((error) => {
          this.outputError(error);
          this.dialogVisible = false;
        });
    },
  },
  watch: {},
  mounted() {
    this.pageInit();
    this.getProject();
    this.getStatus();
    this.getScale();
    this.getBlongsType();
    this.getCity();
    this.listDepartmentOptions();
    this.columnDrop(this.$refs.table);
  },
};
</script>
<style>
.uploadDialog .el-dialog {
  display: none;
}
</style>
