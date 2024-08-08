<template>
  <van-row class="dm-container">
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:editCity="getCity"
      v-on:after-load="resetLoad"
    ></edit-form>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      style="padding-top: 20px"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            prop="recruitStatus"
            label="城市:"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.cityName"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in cityOptions"
                :key="index"
                :label="item.payCityName"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recruitStatus"
            label="产品类型:"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.productType"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in projectOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24" style="margin-bottom: 10px">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button
              @click="getList"
              style="margin-left: 20px"
              icon="el-icon-search"
              class="dc-el-button_Button"
              >查询</el-button
            >
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button
              @click="reset"
              icon="el-icon-refresh"
              class="dc-el-button_Button"
              >重置</el-button
            >
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button
              class="dc-el-button_Button"
              icon="el-icon-plus"
              type="primary"
              @click="addData"
              >新增</el-button
            >
          </el-tooltip>
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button
              @click="onCreate"
              class="dc-el-button_Button"
              type="primary"
              icon="el-icon-office-building"
              >缴费城市维护</el-button
            >
          </el-tooltip>
        </el-col></el-row
      >
    </el-form>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :edit-config="{ trigger: 'click', mode: 'cell' }"
      :tree-config="{
        children: '',
        indent: 20,
        accordion: false,
        line: false,
        showIcon: true,
        iconOpen: '',
        iconClose: '',
      }"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        :field="tableColumns['129'].field"
        :title="tableColumns['129'].title"
        :width="tableColumns['129'].width"
        :visible="tableColumns['129'].visible"
        min-width="120px"
        :edit-render="{ autofocus: '.el-input__inner' }"
        :params="{ sortId: '129', summary: false }"
        tree-node
        resizable
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <el-select
            v-model="row[tableColumns['129'].field]"
            :style="{ width: '100%' }"
            placeholder="请选择"
            clearable
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in cityOptions"
              :key="index"
              :label="item.payCityName"
              :value="item.id"
            ></el-option>
          </el-select>
        </template>
        <template v-slot="{ row }">
          {{ getCityName(row.payCityId) }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['130'].field"
        :title="tableColumns['130'].title"
        :edit-render="{ autofocus: '.el-input__inner' }"
        :width="tableColumns['130'].width"
        :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <el-select
            v-model="row[tableColumns['130'].field]"
            :style="{ width: '100%' }"
            placeholder="请选择"
            clearable
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in blongOptions"
              :key="index"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
        <template v-slot="{ row }">
          {{ getBlongName(row.status, blongOptions) }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :edit-render="{ autofocus: '.el-input__inner' }"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
        tree-node
        resizable
        min-width="160px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <el-select
            v-model="row[tableColumns['131'].field]"
            :style="{ width: '100%' }"
            placeholder="请选择"
            clearable
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in projectOptions"
              :key="index"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
        <template v-slot="{ row }">
          {{ getBlongName(row.productType, projectOptions) }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['141'].field"
        :title="tableColumns['141'].title"
        :edit-render="{ autofocus: '.el-input__inner' }"
        :width="tableColumns['141'].width"
        :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <el-input
            v-model="row[tableColumns['141'].field]"
            :maxLength="128"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></el-input>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :edit-render="{ autofocus: '.el-input__inner' }"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
        tree-node
        resizable
        min-width="130px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['132'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input
        ></template>
        <template v-slot="{ row }">
          {{ row[tableColumns["132"].field] | getFloat }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['133'].field"
        :title="tableColumns['133'].title"
        :edit-render="{ autofocus: '.el-input__inner' }"
        :width="tableColumns['133'].width"
        :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }"
        tree-node
        resizable
        min-width="130px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['133'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input
        ></template>
        <template v-slot="{ row }">
          {{ row[tableColumns["133"].field] | getFloat }}
        </template></ux-table-column
      >
      <ux-table-column
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['134'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input
        ></template>
        <template v-slot="{ row }">
          {{ row[tableColumns["134"].field] | getFloat }}
        </template></ux-table-column
      >
      <ux-table-column
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['135'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input>
        </template>
        <template v-slot="{ row }">
          {{ row[tableColumns["135"].field] | getFloat }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['136'].field"
        :title="tableColumns['136'].title"
        :width="tableColumns['136'].width"
        :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['136'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input>
        </template>
        <template v-slot="{ row }">
          {{ row[tableColumns["136"].field] | getFloat }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['137'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input>
        </template>
        <template v-slot="{ row }">
          {{ row[tableColumns["137"].field] | getFloat }}
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['138'].field"
        :title="tableColumns['138'].title"
        :width="tableColumns['138'].width"
        :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <number-input
            v-model="row[tableColumns['138'].field]"
            :precision="2"
            :min="0"
            placeholder="请输入"
            clearable
            autocomplete="new-password"
            class="dc-el-input_SingleInput"
          ></number-input>
        </template>
        <template v-slot="{ row }">
          {{ row[tableColumns["138"].field] | getFloat }}
        </template>
      </ux-table-column>
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
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <el-select
            v-model="row[tableColumns['139'].field]"
            :style="{ width: '100%' }"
            placeholder="请选择"
            clearable
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in scaleOptions"
              :key="index"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
        <template v-slot="{ row }">
          <OperationIcon
            content="是"
            type="primary"
            v-if="row.isDefault == '0'"
            placement="top"
            icon-name="el-icon-circle-check"
            class="dc-OperationIcon_IconButton"
          >
          </OperationIcon>
          <OperationIcon
            content="否"
            type="warning"
            v-if="row.isDefault == '1'"
            placement="top"
            icon-name="el-icon-remove-outline"
            class="dc-OperationIcon_IconButton"
          >
          </OperationIcon>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['140'].field"
        :title="tableColumns['140'].title"
        :width="tableColumns['140'].width"
        :visible="tableColumns['140'].visible"
        :params="{ sortId: '140', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        :edit-render="{ autofocus: '.el-input__inner' }"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="{ row, rowIndex }">
          <el-select
            v-model="row[tableColumns['140'].field]"
            :style="{ width: '100%' }"
            placeholder="请选择"
            clearable
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in statusOptions"
              :key="index"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
        <template v-slot="{ row }">
          <OperationIcon
            content="启用"
            type="primary"
            v-if="row.enable == '0'"
            placement="top"
            icon-name="el-icon-success"
            class="dc-OperationIcon_IconButton"
          >
          </OperationIcon>
          <OperationIcon
            content="停用"
            type="warning"
            v-if="row.enable == '1'"
            placement="top"
            icon-name="el-icon-remove"
            class="dc-OperationIcon_IconButton"
          >
          </OperationIcon>
        </template>
      </ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '114' }"
        tree-node
        resizable
        min-width="120px"
        fixed="right"
        align="center"
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
            content="导出excel文件"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
        </template>
        <template slot-scope="scope">
          <OperationIcon
            type="primary"
            content="保存"
            placement="top"
            icon-name="el-icon-circle-check"
            @click="saveData(scope)"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>

          <OperationIcon
            content="停用"
            @click="stopData(scope)"
            type="danger"
            placement="top"
            icon-name="el-icon-video-pause"
            class="dc-OperationIcon_IconButton"
          >
          </OperationIcon>
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
  </van-row>
</template>
<script>
import { validatenull } from "@/utils/validate";
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
  saveDefultConfig,
} from "@/api/socialSecurityFund/payCity";
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
      },
      tableData: [],
      version: 3,
      // 查询表格列头
      tableColumns: {
        129: {
          title: "缴费城市",
          field: "payCityId",
          visible: true,
          order: 0,
        },
        130: {
          title: "所属状态",
          field: "status",
          visible: true,
          order: 1,
        },
        131: {
          title: "产品类型",
          field: "productType",
          visible: true,
          order: 2,
        },
        141: {
          title: "产品名称",
          field: "productName",
          visible: true,
          order: 3,
        },
        132: {
          title: "单位比例(%)",
          field: "unitScale",
          visible: true,
          order: 4,
        },
        133: {
          title: "个人比例(%)",
          field: "personScale",
          visible: true,
          order: 5,
        },

        134: {
          title: "社会平均工资",
          field: "socialAvgAmount",
          visible: true,
          order: 6,
        },
        135: {
          title: "个人最高基数",
          field: "personHighBase",
          visible: true,
          order: 7,
        },
        136: {
          title: "个人最低基数",
          field: "personLowBase",
          visible: true,
          order: 8,
        },
        137: {
          title: "公司最高基数",
          field: "companyHighBase",
          visible: true,
          order: 9,
        },
        138: {
          title: "公司最低基数",
          field: "companyLowBase",
          visible: true,
          order: 10,
        },
        139: {
          title: "默认比例",
          field: "isDefault",
          visible: true,
          order: 11,
        },
        140: {
          title: "状态",
          field: "enable",
          visible: true,
          order: 12,
        },

        114: {
          title: "操作",
          width: "140px",
          visible: true,
          order: 30,
        },
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "", // 排序
      },
      projectOptions: [],
      statusOptions: [],
      blongOptions: [],
      scaleOptions: [],
      cityOptions: [],
      tableId: "1457254724943432563",
    };
  },
  computed: {},
  filters: {
    getFloat(val) {
      if (val) {
        return Number(val).toFixed(2);
      }
      if (val === 0) {
        return "0.00";
      }
      return null;
    },
  },
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];
      if (this.queryFormData.cityName) {
        this.search.params.push({
          // 城市查询参数
          columnName: "pay_city_id",
          queryType: "=",
          value: this.queryFormData.cityName,
        });
      }
      if (this.queryFormData.productType) {
        this.search.params.push({
          // 产品类型参数
          columnName: "product_type",
          queryType: "=",
          value: this.queryFormData.productType,
        });
      }
      listDefultConfig(this.search)
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
    saveData(scope) {
      let obj = JSON.parse(JSON.stringify(scope.row));
      delete obj._XID;
      let canValid = true;
      if (!scope.row.id) {
        let arr = Object.keys(obj);
        arr.forEach((item) => {
          if (
            obj[item] === "" ||
            obj[item] === null ||
            obj[item] === undefined ||
            arr.length !== 13
          ) {
            canValid = false;
          }
        });
      }
      if (!canValid) {
        this.$message.warning("请填写完整的数据！");
      } else {
        this.setLoad();
        saveDefultConfig(obj)
          .then((responseData) => {
            if (responseData.code == 100) {
              this.$message.success("数据保存成功！");
              this.getList();
            } else {
              this.showMessage(responseData);
            }
            this.resetLoad();
          })
          .catch((error) => {
            this.outputError(error);
          });
      }
    },
    stopData(scope) {
      let obj = JSON.parse(JSON.stringify(scope.row));
      obj.enable = "1";
      this.setLoad();
      saveDefultConfig(obj)
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
    getBlongName(val, options) {
      let obj = options.find((it) => it.value === val);
      return obj ? obj.name : val;
    },

    addData() {
      this.tableData.push({ payCityId: "" });
    },
    onSearch() {
      this.search.offset = 0;
      this.tableDataPage = 1;
      this.getList();
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
      setTimeout(() => {
        this.onSearch();
      });
    },
    onCreate() {
      this.$refs.editForm.$emit("openAddDialog");
    },
    getCityName(val) {
      let obj = this.cityOptions.find((it) => it.id === val);
      return obj ? obj.payCityName : val;
    },
    //重置
    reset() {
      this.queryFormData = {
        cityName: "",
        productType: "",
      };
      this.onSearch();
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
  },
  watch: {},
  mounted() {
    this.pageInit();
    this.getProject();
    this.getStatus();
    this.getScale();
    this.getBlongsType();
    this.getCity();
    this.columnDrop(this.$refs.table);
  },
};
</script>
