<template>
  <el-row class="dc-container" v-loading="loading">
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

        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button
            type="primary"
            style="margin-left:20px"
            icon="el-icon-search"
            @click="getList"
            class="dc-el-button_Button"
            >查询</el-button
          >
        </el-tooltip>

        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button
            type="primary"
            icon="el-icon-refresh"
            class="dc-el-button_Button"
            @click="reset"
            >重置</el-button
          >
        </el-tooltip>
      </el-row>
    </el-form>
    <el-row
      gutter="0"
      type="flex"
      justify="end"
      align="top"
      class="dc-el-row_ElRow"
    >
      <process-panel
        ref="processPanel"
        :bizAdd="permission.add"
        :tenantId="currentUser.company.id"
        keyPrefix="PROC_"
        procName="XX审批流程"
        formKey="wf/common/wfForm.vue#XX/XX/form.vue"
        procTitile="${start_user_name}发起${form.name}XX审批"
        v-on:v-loading="onVLoading"
        v-on:save-finished="getList"
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
        :field="tableColumns['141'].field"
        :title="tableColumns['141'].title"
        :width="tableColumns['141'].width"
        :visible="tableColumns['141'].visible"
        :params="{ sortId: '141', summary: false }"
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
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['146'].field"
        :title="tableColumns['146'].title"
        :width="tableColumns['146'].width"
        :visible="tableColumns['146'].visible"
        :params="{ sortId: '146', summary: false }"
        tree-node
        resizable
        min-width="150px"
        align="center"
        header-align="center"
        sortable
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
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">{{
          approveLabel(row.actStatus)
        }}</template></ux-table-column
      >
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
            v-if="scope.row.actStatus != '8047'"
            type="primary"
            content="招聘详情查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="primary"
            v-if="scope.row.actStatus != '8047'"
            content="审批详情查看"
            placement="top"
            icon-name="el-icon-video-camera"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            v-if="scope.row.actStatus === '8047'"
            content="修改"
            v-on:click="onEdit(scope)"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            content="流程发起"
            @click="processStart(scope)"
            v-if="scope.row.actStatus === '8047'"
            placement="top"
            icon-name="el-icon-video-play"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            v-if="scope.row.actStatus === '8047'"
            content="删除"
            @click="deleteData(scope)"
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
      :page-sizes="[10, 20, 30, 40, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper"
      v-on:size-change="onSizeChange"
      v-on:current-change="onCurrentChange"
      class="dc-el-pagination_ElPagination"
    ></el-pagination>
    <comment ref="commentForm" class="dc-comment_ElImport"></comment>
    <component
      ref="wfForm"
      v-on:save-finished="getList"
      v-on:after-wfForm-load="afterWfFormload"
      :is="wfForm"
      class="dc-component_ElImport"
    ></component>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";

import { listTsmRecruitPage, deleteTsmRecruit } from "@/api/recruit/tsmRecruit";

import { listResourcePermission } from "@/api/admin/common/permission";
import { getHistProcInstance } from "@/api/wf/procinst";
import { getProcessByKey, getStartForm } from "@/api/wf/processdefinition";
import { listAllByCompany } from "@/api/org/department";
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import processPanel from "@/views/wf/common/processPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
import Comment from "@/views/wf/common/comment";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import WfMainUI from "@/views/wf/common/wfMainUI";
import OperationIcon from "@/components/OperationIcon";
import { getDictTypeById } from "@/api/sys/dictType";
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
        postName: "", // 招聘岗位
        actStatus: null // 审批状态
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        136: {
          title: "招聘编码",
          field: "recruitCode",
          visible: true,
          order: 0
        },
        137: {
          title: "招聘岗位",
          field: "postName",
          visible: true,
          order: 1
        },

        141: {
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
        146: {
          title: "归属部门",
          field: "deptName",
          visible: true,
          order: 5
        },
        148: {
          title: "工作城市",
          field: "workCity",
          visible: true,
          order: 6
        },
        156: {
          title: "审批状态",
          field: "actStatus",
          visible: true,
          order: 7
        },
        159: {
          title: "申请时间",
          field: "applyDate",
          visible: true,
          order: 8
        },
        114: {
          title: "操作",
          width: "140px",
          isShow: true,
          visible: true,
          order: 9
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
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
      tableId: "1457254743943430518",
      schemeId: "1457254743943430516",
      formStatus: "view", //表格状态，view和edit，processStart
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
        actStatus: null
      };
      this.getList();
    },
    //删除
    deleteData(scope) {
      this.$confirm("确定要删除该条数据吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.setLoad();
          deleteTsmRecruit(scope.row)
            .then(responseData => {
              if (responseData.code == 100) {
                this.getList();
                this.showMessage({
                  type: "success",
                  msg: "删除成功"
                });
              } else {
                this.showMessage(responseData);
              }
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        })
        .catch(() => {});
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
    //流程发起
    processStart(scope) {
      this.onEdit(scope, "processStart");
    },
    onEdit(scope, status = "edit") {
      this.formStatus = status;
      this.setLoad();
      getStartForm(scope.row.procdefId)
        .then(responseData => {
          if (responseData.hasOwnProperty("key")) {
            if (responseData.key) {
              this.currentRow = {
                formKey: responseData.key,
                definitionId: scope.row.procdefId,
                row: scope.row
              };
              this.loadWfForm(responseData.key);
            } else {
              this.showMessage({
                type: "warning",
                msg: "流程未配置form，请联系管理员。"
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
    onView(scope) {
      this.formStatus = "view";
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
      if (this.formStatus === "view") {
        this.$refs.wfForm.$emit(
          "openViewBizDialog",
          this.currentRow.formKey,
          this.currentRow.definitionId,
          this.currentRow.row
        );
      } else if (this.formStatus === "edit") {
        //编辑
        this.$refs.wfForm.$emit(
          "openEditDialog",
          this.currentRow.formKey,
          this.currentRow.definitionId,
          this.currentRow.row
        );
      } else {
        //流程发起
        this.$refs.wfForm.$emit(
          "openEditDialog",
          this.currentRow.formKey,
          this.currentRow.definitionId,
          this.currentRow.row,
          "proStart"
        );
      }
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
  }
};
</script>
