<template>
  <el-row class="dc-container" v-loading="loading">
    <recruitNum ref="recruitNum"></recruitNum>
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            prop="postName"
            label="招聘岗位"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.postName"
              :maxLength="64"
              placeholder="请输入招聘岗位"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="actStatus"
            label="审批状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.actStatus"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in approvalOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recruitStatus"
            label="招聘状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.recruitStatus"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in recruitOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button
            @click="getList"
            type="primary"
            style="margin-left:20px"
            icon="el-icon-search"
            class="dc-el-button_Button"
            >查询</el-button
          >
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button
            type="primary"
            @click="reset"
            icon="el-icon-refresh"
            class="dc-el-button_Button"
            >重置</el-button
          >
        </el-tooltip>
      </el-row>
    </el-form>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      :edit-config="{ trigger: 'click', mode: 'cell' }"
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
        :field="tableColumns['136'].field"
        :title="tableColumns['136'].title"
        :width="tableColumns['136'].width"
        :visible="tableColumns['136'].visible"
        :params="{ sortId: '136', summary: false }"
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
        :field="tableColumns['159'].field"
        :title="tableColumns['159'].title"
        :width="tableColumns['159'].width"
        :visible="tableColumns['159'].visible"
        :params="{ sortId: '159', summary: false }"
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
        :field="tableColumns['137'].field"
        :title="tableColumns['137'].title"
        :width="tableColumns['137'].width"
        :visible="tableColumns['137'].visible"
        :params="{ sortId: '137', summary: false }"
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
        :field="tableColumns['142'].field"
        :title="tableColumns['142'].title"
        :width="tableColumns['142'].width"
        :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }"
        tree-node
        resizable
        min-width="150px"
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
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['155'].field"
        :title="tableColumns['155'].title"
        :width="tableColumns['155'].width"
        :visible="tableColumns['155'].visible"
        :params="{ sortId: '155', summary: false }"
        tree-node
        edit-render
        resizable
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="scope">
          <el-input v-model="scope.row.candidate"></el-input>
        </template>
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['171'].field"
        :title="tableColumns['171'].title"
        :width="tableColumns['171'].width"
        :visible="tableColumns['171'].visible"
        :params="{ sortId: '171', summary: false }"
        tree-node
        resizable
        min-width="135px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['148'].field"
        :title="tableColumns['148'].title"
        :width="tableColumns['148'].width"
        :visible="tableColumns['148'].visible"
        :params="{ sortId: '148', summary: false }"
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
        :field="tableColumns['156'].field"
        :title="tableColumns['156'].title"
        :width="tableColumns['156'].width"
        :visible="tableColumns['156'].visible"
        :params="{ sortId: '156', summary: false }"
        tree-node
        resizable
        min-width="135px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">{{ approveLabel(row.actStatus) }}</template>
      </ux-table-column>
      <ux-table-column
        edit-render
        :field="tableColumns['154'].field"
        :title="tableColumns['154'].title"
        :width="tableColumns['154'].width"
        :visible="tableColumns['154'].visible"
        :params="{ sortId: '154', summary: false }"
        tree-node
        resizable
        min-width="135px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot:edit="scope">
          <el-select
            v-model="scope.row.recruitStatus"
            @change="$refs.table.updateStatus(scope)"
          >
            <el-option
              v-for="item in recruitOptions"
              :key="item.value"
              :label="item.name"
              :value="item.value"
            ></el-option>
          </el-select>
        </template>
        <template v-slot="{ row }">{{ getLabel(row.recruitStatus) }}</template>
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
            v-on:click="onView(scope)"
            type="info"
            content="详情查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="info"
            content="历史记录"
            placement="top"
            icon-name="el-icon-info"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            v-on:click="saveStatus(scope)"
            content="保存招聘状态"
            placement="top"
            icon-name="el-icon-finished"
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
      v-on:save-finished="getTaskList()"
      v-on:after-wfForm-load="afterWfFormload()"
      :is="wfForm"
      class="dc-component_ElImport"
    ></component>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";

import {
  listTsmRecruitPage,
  deleteTsmRecruit,
  saveTsmRecruit
} from "@/api/hrRecruit/tsmRecruit";
import { listResourcePermission } from "@/api/admin/common/permission";
import { getHistProcInstance } from "@/api/wf/procinst";
import { getProcessByKey, getStartForm } from "@/api/wf/processdefinition";

/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
import Comment from "@/views/wf/common/comment";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import WfMainUI from "@/views/wf/common/wfMainUI";
import OperationIcon from "@/components/OperationIcon";
import recruitNum from "./../components/recruitNum.vue";
import { getDictTypeById } from "@/api/sys/dictType";
import { listAllByCompany } from "@/api/org/department";
export default {
  extends: WfMainUI,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    Comment,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    recruitNum
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
        postName: "", // 招聘岗位
        actStatus: null, // 审批状态
        recruitStatus: null // 招聘状态
      },
      tableData: [],
      newTableData: [],
      // 查询表格列头
      tableColumns: {
        136: {
          title: "招聘编号",
          field: "recruitCode",
          visible: true,
          order: 0
        },
        159: {
          title: "申请时间",
          field: "applyDate",
          visible: true,
          order: 1
        },
        137: {
          title: "招聘岗位",
          field: "postName",
          visible: true,
          order: 2
        },
        142: {
          title: "薪资范围",
          field: "salarySpecial",
          visible: true,
          order: 3
        },
        143: {
          title: "招聘人数",
          field: "staffNum",
          visible: true,
          order: 4
        },
        155: {
          title: "已招聘人数",
          field: "candidate",
          visible: true,
          order: 5
        },
        171: {
          title: "归属部门",
          field: "deptName",
          visible: true,
          order: 6
        },
        148: {
          title: "工作城市",
          field: "workCity",
          visible: true,
          order: 7
        },
        156: {
          title: "审批状态",
          field: "actStatus",
          visible: true,
          order: 8
        },
        154: {
          title: "招聘状态",
          field: "recruitStatus",
          visible: true,
          order: 9
        },
        114: {
          title: "操作",
          width: "140px",
          isShow: true,
          visible: true,
          order: 10
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 招聘状态选项
      recruitOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 6,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943430851",
      schemeId: "1457254743943430849",
      departmentOptions: [], //部门人员
      approvalOptions: [] //审批状态下拉
    };
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];
      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
        this.search.params.push({
          columnName: "post_name",
          queryType: "like",

          value: !validatenull(this.queryFormData.postName)
            ? this.queryFormData.postName
            : null
        });

        this.search.params.push({
          columnName: "act_status",
          queryType: "=",

          value: !validatenull(this.queryFormData.actStatus)
            ? this.queryFormData.actStatus
            : null
        });

        this.search.params.push({
          columnName: "recruit_status",
          queryType: "=",

          value: !validatenull(this.queryFormData.recruitStatus)
            ? this.queryFormData.recruitStatus
            : null
        });
      }
      // 数据权限: 招聘表tsm_recruit
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listTsmRecruitPage(this.search)
        .then(responseData => {
          if (responseData.code == 100) {
            this.tableDataTotal = responseData.data.total;
            this.tableData = responseData.data.rows
              ? responseData.data.rows
              : [];
            this.tableData.map(item => {
              item.salarySpecial =
                item.salaryLow + "k" + "~" + item.salaryHigh + "k";
              return item;
            });
            this.newTableData = JSON.parse(JSON.stringify(this.tableData));
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    //重置
    reset() {
      this.queryFormData = {
        postName: "",
        actStatus: null,
        recruitStatus: null
      };
      this.getList();
    },
    onSearch() {
      if (this.isQueryConditionPanel) {
        this.search.offset = 0;
        this.tableDataPage = 1;
        this.getList();
      } else {
        this.$refs["queryForm"].validate(valid => {
          if (valid) {
            this.search.offset = 0;
            this.tableDataPage = 1;
            this.getList();
          } else {
            return false;
          }
        });
      }
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
    getLabel(val) {
      const item = this.recruitOptions.filter(item => item["value"] === val)[0];
      return item ? item.name : "";
    },
    saveStatus(scope) {
      if (scope.row.candidate && isNaN(scope.row.candidate)) {
        this.$message({
          message: "请填写正确的已招聘人数",
          type: "warning"
        });
      } else if (Number(scope.row.candidate) > Number(scope.row.staffNum)) {
        this.$message({
          message: "已招聘人数不能超过招聘人数！",
          type: "warning"
        });
        let obj = this.newTableData.find(item => item.id === scope.row.id);
        scope.row.candidate = obj.candidate;
      } else {
        saveTsmRecruit(scope.row).then(res => {
          if (res.code === "100") {
            this.showMessage({
              type: "success",
              msg: "修改流程状态成功"
            });
            this.getList();
            this.$refs.recruitNum.getNumber();
          } else {
            this.showMessage({
              type: "error",
              msg: "网络请求失败，请重试"
            });
          }
        });
      }
    },
    async pageInit() {
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        this.search.params = [];
        // 数据权限: 招聘表tsm_recruit
        this.pushDataPermissions(
          this.search.params,
          this.$route.meta.routerId,
          this.tableId
        );
        let [
          listTsmRecruitRespData,
          listPermissionRespData
        ] = await Promise.all([
          listTsmRecruitPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ]);
        if (
          listTsmRecruitRespData.code == 100 &&
          listPermissionRespData.code == 100
        ) {
          this.tableDataTotal = listTsmRecruitRespData.data.total;
          this.tableData = listTsmRecruitRespData.data.rows
            ? listTsmRecruitRespData.data.rows
            : [];
          this.tableData.map(item => {
            item.salarySpecial =
              item.salaryLow + "k" + "~" + item.salaryHigh + "k";
            return item;
          });
          this.newTableData = JSON.parse(JSON.stringify(this.tableData));
          this.permission.view = listPermissionRespData.data.find(item => {
            return item.permission === "tsmRecruit:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "tsmRecruit:export";
          });

          this.permission.add = listPermissionRespData.data.find(item => {
            return item.permission === "tsmRecruit:create";
          });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "tsmRecruit:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "tsmRecruit:delete";
          });
        } else {
          this.showMessage(
            listPermissionRespData.code != 100
              ? listPermissionRespData
              : listTsmRecruitRespData
          );
        }
        this.resetLoad();
      } catch (error) {
        this.outputError(error);
      }
    },
    onView(scope) {
      this.setLoad();
      getHistProcInstance(scope.row.procInst)
        .then(responseData => {
          if (responseData.hasOwnProperty("processDefinitionId")) {
            let definitionId = responseData.processDefinitionId;
            if (definitionId) {
              getStartForm(definitionId).then(startFormResp => {
                if (startFormResp.hasOwnProperty("key")) {
                  if (startFormResp.key) {
                    this.currentRow = {
                      formKey: startFormResp.key,
                      definitionId: definitionId,
                      row: scope.row
                    };
                    this.loadWfForm(startFormResp.key);
                  } else {
                    this.showMessage({
                      type: "warning",
                      msg: "流程未配置form，请联系管理员。"
                    });
                  }
                } else {
                  this.showMessage(startFormResp);
                }
                this.resetLoad();
              });
            } else {
              this.showMessage({
                type: "warning",
                msg: "流程实例没有找到流程定义。"
              });
            }
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 重写dcMain的方法
    onShowHistory(scope) {
      this.$refs.commentForm.$emit("openComment", scope.row.procInst);
    },
    afterWfFormload() {
      this.$refs.wfForm.$emit(
        "openViewBizDialog",
        this.currentRow.formKey,
        this.currentRow.definitionId,
        this.currentRow.row
      );
    },
    onVLoading(loading) {
      loading ? this.setLoad() : this.resetLoad();
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

    // 初始化自定义类型选择框选项变量
    initOptions(This) {},
    //获取全部部门数据
    getDepartment() {
      listAllByCompany({}).then(res => {
        this.departmentOptions = res.data || [];
      });
    },

    //获取审批状态数据
    getApproval() {
      getDictTypeById("1485271468451037312").then(res => {
        this.approvalOptions = res.data.dictItemList;
      });
    },
    //根据审批状态的value返回中文
    approveLabel(val) {
      const item = this.approvalOptions.filter(
        item => item["value"] === val
      )[0];
      return item ? item.name : "";
    },
    //获取招聘状态数据
    getRecruit() {
      getDictTypeById("1485271468451037318").then(res => {
        this.recruitOptions = res.data.dictItemList;
      });
    }
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
    //获取部门
    this.getDepartment();
    //获取审批状态
    this.getApproval();
    //获取招聘状态
    this.getRecruit();
  }
};
</script>
