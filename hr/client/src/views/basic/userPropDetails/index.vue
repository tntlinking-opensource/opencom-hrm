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
    <edit-form1
      ref="editForm1"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form1>
    <edit-form2
      ref="editForm2"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form2>
    <edit-form3
      ref="editForm3"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form3>
    <el-container direction="horizontal" class="dc-el-container_ElContainer">
      <el-aside width="200px" height="60px">
        <el-row>
          <list-tree
            ref="listTree"
            border
            v-on:current-change="onChangeCurrentRow"
            title="配置列表"
            field="name"
            align="left"
            header-align="center"
            show-overflow
            :tree-config="{
              children: '',
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
              url: 'basic/userPropList/list',
              params: [],
              data: 'return {\n        params: []\n      }',
              reqBefore:
                'config.headers[&quot;X-Token&quot;] = sessionStorage.getItem(&quot;token&quot;)\n    return config',
              reqResponse:
                'if (response.data.code == 100) {\n          \n\treturn response.data.data.rows\n}',
              reqError: 'console.error(error)'
            }"
            class="dc-list-tree_ListTree"
          ></list-tree>
        </el-row>
      </el-aside>
      <el-main>
        <el-row>
          <!--          <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">-->
          <!--            <el-col :span="24">-->
          <!--              <query-tag-->
          <!--                ref="queryTag"-->
          <!--                v-model="queryEditObject"-->
          <!--                :moreParm="moreParm"-->
          <!--                :routerId="$route && $route.meta.routerId"-->
          <!--                :isQueryConditionPanel="isQueryConditionPanel"-->
          <!--                v-on:queryTagPanel="onQueryTagPanel"-->
          <!--                v-on:queryTagClick="onQueryTagClick"-->
          <!--                v-on:search="onSearch"-->
          <!--                class="dc-query-tag_ElConditionTag"-->
          <!--              ></query-tag>-->
          <!--            </el-col>-->
          <!--            <el-col :span="6">-->
          <!--              <div style="text-align: left">-->
          <!--                <el-button-group class="dc-el-button-group_ButtonGroup">-->
          <!--                  <el-tooltip effect="dark" content="搜索" placement="top">-->
          <!--                    <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>-->
          <!--                  </el-tooltip>-->
          <!--                  <el-tooltip effect="dark" content="重置" placement="top">-->
          <!--                    <el-button-->
          <!--                      v-on:click="isQueryConditionPanel ? $refs.conditionPanel.reset() : $refs.queryForm.resetFields()"-->
          <!--                      type="primary"-->
          <!--                      icon="el-icon-refresh-right"-->
          <!--                    ></el-button>-->
          <!--                  </el-tooltip>-->
          <!--                  <el-tooltip effect="dark" content="更多" placement="top">-->
          <!--                    <el-button-->
          <!--                      v-on:click="onQueryConditionPanel('query')"-->
          <!--                      type="primary"-->
          <!--                      icon="el-icon-d-arrow-right"-->
          <!--                    ></el-button>-->
          <!--                  </el-tooltip>-->
          <!--                </el-button-group>-->
          <!--              </div>-->
          <!--            </el-col>-->
          <!--          </el-row>-->
          <query-condition-panel
            v-if="isQueryConditionPanel"
            ref="conditionPanel"
            :conditionColumns="
              metadata.find(item => {
                return item.type == 'main';
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
            <el-row></el-row>
          </el-form>
          <el-row
            gutter="0"
            type="flex"
            justify="end"
            align="top"
            class="dc-el-row_ElRow"
          >
            <el-tooltip
              v-show="permission.add"
              disabled
              class="dc-el-tooltip_Button"
            >
              <el-button
                type="primary"
                icon="el-icon-plus"
                v-if="currentRow.code !== '004'"
                v-on:click="onCreate"
                >添加</el-button
              >
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
              :field="tableColumns['134'].field"
              :title="tableColumns['134'].title"
              :width="tableColumns['134'].width"
              :visible="tableColumns['134'].visible"
              :params="{ sortId: '134', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['135'].field"
              :title="tableColumns['135'].title"
              :width="tableColumns['135'].width"
              :visible="tableColumns['135'].visible"
              :params="{ sortId: '135', summary: false }"
              tree-node
              resizable
              min-width="180px"
              align="left"
              header-align="center"
              sortable
              show-overflow
              class="dc-ux-table-column_ElTableColumn"
            >
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['136'].field"
              :title="tableColumns['136'].title"
              :width="tableColumns['136'].width"
              :visible="tableColumns['136'].visible"
              v-if="tableColumns['136'].visible"
              :params="{ sortId: '136', summary: false }"
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
                  v-if="currentRow.code === '002'"
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '2':
                          return '否';
                        case '1':
                          return '是';
                        default:
                          return value;
                      }
                    })(row.attribute1, row, rowIndex)
                  "
                ></div>
                <div
                  v-else
                  v-html="
                    ((value, row, rowIndex) => {
                      return '*************';
                    })(row.attribute1, row, rowIndex)
                  "
                ></div>
              </template>
            </ux-table-column>
            <ux-table-column
              :field="tableColumns['146'].field"
              :title="tableColumns['146'].title"
              :width="tableColumns['146'].width"
              :visible="tableColumns['146'].visible"
              :params="{ sortId: '146', summary: false }"
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
                  v-if="currentRow.code !== '004'"
                  v-html="
                    ((value, row, rowIndex) => {
                      switch (value) {
                        case '0':
                          return '停用';
                        case '1':
                          return '启用';
                        default:
                          return '';
                      }
                    })(row.used, row, rowIndex)
                  "
                ></div>
                <el-switch
                  :value="row.used === '1'"
                  @change="changeUsed(row)"
                  v-if="currentRow.code === '004'"
                >
                </el-switch>
              </template>
            </ux-table-column>

            <ux-table-column
              title="操作"
              :params="{ sortId: '119' }"
              tree-node
              v-if="currentRow.code !== '004'"
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
                  <!--                  <template slot="reference">-->
                  <!--                    <OperationIcon-->
                  <!--                      type="primary"-->
                  <!--                      content="自定义列"-->
                  <!--                      placement="top"-->
                  <!--                      icon-name="el-icon-tickets"-->
                  <!--                    ></OperationIcon>-->
                  <!--                  </template>-->
                </el-popover>
                <!--                <OperationIcon-->
                <!--                  v-on:click="onExport(tableData)"-->
                <!--                  type="primary"-->
                <!--                  v-show="permission.export"-->
                <!--                  content="导出excel文件"-->
                <!--                  placement="top"-->
                <!--                  icon-name="el-icon-download"-->
                <!--                  class="dc-OperationIcon_IconButton"-->
                <!--                ></OperationIcon>-->
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
                  content="修改"
                  placement="top"
                  icon-name="el-icon-edit"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <!--                <OperationIcon-->
                <!--                  v-on:click="onCopy(scope)"-->
                <!--                  type="primary"-->
                <!--                  v-show="permission.add"-->
                <!--                  content="复制"-->
                <!--                  placement="top"-->
                <!--                  icon-name="el-icon-document"-->
                <!--                  class="dc-OperationIcon_IconButton"-->
                <!--                ></OperationIcon>-->
                <OperationIcon
                  v-on:click="onDelete(scope)"
                  type="danger"
                  v-show="permission.remove"
                  content="删除"
                  placement="top"
                  icon-name="el-icon-delete"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
                <!--                <OperationIcon-->
                <!--                  v-on:click="onShowHistory(scope)"-->
                <!--                  type="info"-->
                <!--                  v-show="permission.view"-->
                <!--                  content="历史记录"-->
                <!--                  placement="top"-->
                <!--                  icon-name="el-icon-info"-->
                <!--                  class="dc-OperationIcon_IconButton"-->
                <!--                ></OperationIcon>-->
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
import { validatenull } from "@/utils/validate";
import {
  listUserPropDetailsPage,
  deleteUserPropDetails
} from "@/api/basic/userPropDetails";

import { listResourcePermission } from "@/api/admin/common/permission";
/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import EditForm from "./form";
import EditForm1 from "./form1.vue";
import EditForm2 from "./form2.vue";
import EditForm3 from "./form3.vue";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import DcMain from "@/views/components/dcMain";
import OperationIcon from "@/components/OperationIcon";
import { saveUserPropDetails } from "@/api/basic/userPropDetails";
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    History,
    EditForm,
    EditForm1,
    EditForm2,
    EditForm3,
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
      queryFormData: {},
      tableData: [],
      // 查询表格列头
      tableColumns: {
        134: {
          title: "attribute2",
          field: "attribute2",
          visible: true,
          order: 0
        },
        135: {
          title: "attribute0",
          field: "attribute0",
          visible: true,
          order: 0
        },
        136: {
          title: "attribute1",
          field: "attribute1",
          visible: true,
          order: 1
        },
        146: {
          title: "状态",
          field: "used",
          visible: true,
          order: 2
        },
        119: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: true,
          order: 3
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      version: 10,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943430927",
      schemeId: "1457254743943430925",
      //当前选择的左树
      currentRow: {}
    };
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad();
      /* 查询参数 和数据权限 */
      this.search.params = [];

      if (validatenull(this.parentTable)) {
        this.$alert("请选择左侧列表", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      this.search.params.push({
        columnName: "user_prop_list_id",
        queryType: "=",
        value: this.parentTable.id
      });

      if (this.isQueryConditionPanel) {
        this.search.params = this.search.params.concat(
          this.compositeCondition()
        );
      } else {
      }
      // 数据权限: 基础配置项user_prop_details
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );
      listUserPropDetailsPage(this.search)
        .then(responseData => {
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
        .catch(error => {
          this.outputError(error);
        });
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
    //修改权限使用状态
    changeUsed(val) {
      let obj = JSON.parse(JSON.stringify(val));
      if (val.used === "1") {
        obj.used = "0";
      } else if (val.used === "0") {
        obj.used = "1";
      }
      saveUserPropDetails(obj)
        .then(responseData => {
          if (responseData.code == 100) {
            this.showMessage({
              type: "success",
              msg: "状态修改成功"
            });
            this.getList();
          } else {
            this.showMessage(responseData);
          }
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    doSave() {
      saveUserPropDetails(this.editFormData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.showMessage({
              type: "success",
              msg: "保存成功"
            });
            this.$emit("save-finished");
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
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
      this.initOptions(this.queryModel);
      this.setLoad();
      listResourcePermission(this.$route.meta.routerId)
        .then(responseData => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find(item => {
              return item.permission === "userPropDetails:read";
            });
            this.permission.export = responseData.data.find(item => {
              return item.permission === "userPropDetails:export";
            });

            this.permission.add = responseData.data.find(item => {
              return item.permission === "userPropDetails:create";
            });
            this.permission.edit = responseData.data.find(item => {
              return item.permission === "userPropDetails:update";
            });
            this.permission.remove = responseData.data.find(item => {
              return item.permission === "userPropDetails:delete";
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
    onView(scope) {
      if (this.currentRow.code === "001") {
        this.$refs.editForm.$emit("openViewDialog", scope.row.id);
      }
      if (this.currentRow.code === "002") {
        this.$refs.editForm1.$emit("openViewDialog", scope.row.id);
      }
      if (this.currentRow.code === "003") {
        this.$refs.editForm2.$emit("openViewDialog", scope.row.id);
      }
      if (this.currentRow.code === "004") {
        this.$refs.editForm3.$emit("openViewDialog", scope.row.id);
      }
    },

    onCreate() {
      if (validatenull(this.parentTable)) {
        this.$alert("请选择左侧列表", "提示", {
          confirmButtonText: "确定",
          type: "info"
        });
        return;
      }
      let row = {
        userPropList: {
          id: this.parentTable.id,

          name: this.parentTable.name,

          code: this.parentTable.code,

          tenant_id: this.parentTable.tenant_id,

          attribute4: this.parentTable.attribute4,

          attribute3: this.parentTable.attribute3,

          attribute2: this.parentTable.attribute2,

          attribute1: this.parentTable.attribute1,

          attribute0: this.parentTable.attribute0
        }
      };
      if (this.currentRow.code === "001") {
        this.$refs.editForm.$emit("openAddDialog", row);
      }
      if (this.currentRow.code === "002") {
        this.$refs.editForm1.$emit("openAddDialog", row);
      }
      if (this.currentRow.code === "003") {
        this.$refs.editForm2.$emit("openAddDialog", row);
      }
      if (this.currentRow.code === "004") {
        this.$refs.editForm3.$emit("openAddDialog", row);
      }
    },
    onEdit(scope) {
      if (this.currentRow.code === "001") {
        this.$refs.editForm.$emit("openEditDialog", scope.row.id);
      }
      if (this.currentRow.code === "002") {
        this.$refs.editForm1.$emit("openEditDialog", scope.row.id);
      }
      if (this.currentRow.code === "003") {
        this.$refs.editForm2.$emit("openEditDialog", scope.row.id);
      }
      if (this.currentRow.code === "004") {
        this.$refs.editForm3.$emit("openEditDialog", scope.row.id);
      }
    },
    onCopy(scope) {
      if (this.currentRow.code === "001") {
        this.$refs.editForm.$emit("openCopyDialog", scope.row.id);
      }
      if (this.currentRow.code === "002") {
        this.$refs.editForm1.$emit("openCopyDialog", scope.row.id);
      }
      if (this.currentRow.code === "003") {
        this.$refs.editForm2.$emit("openCopyDialog", scope.row.id);
      }
      if (this.currentRow.code === "004") {
        this.$refs.editForm3.$emit("openCopyDialog", scope.row.id);
      }
    },
    onDelete(scope) {
      if (scope.row.used === "1") {
        this.$message({
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: "停用状态才允许删除!",
          type: "warning"
        });
      } else {
        this.$confirm("确定删除吗？", "确认", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.setLoad();
            deleteUserPropDetails(scope.row)
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
      }
    },
    onChangeCurrentRow(currentRow, oldCurrentRow) {
      if (currentRow != oldCurrentRow) {
        this.parentTable = currentRow;
        this.initOptions(this.queryModel);
        this.onSearch();
        //更改配置
        this.changeViewProp(currentRow);
      }
    },
    changeViewProp(currentRow) {
      if (currentRow.code === "001") {
        this.tableColumns["134"].title = "邮箱序号";
        this.tableColumns["135"].title = "邮箱名称";
        this.tableColumns["136"].title = "密码";
        this.tableColumns["136"].visible = true;
      }
      if (currentRow.code === "002") {
        this.tableColumns["134"].title = "附件序号";
        this.tableColumns["135"].title = "附件名称";
        this.tableColumns["136"].title = "是否必传";
        this.tableColumns["136"].visible = true;
      }
      if (currentRow.code === "003") {
        this.tableColumns["134"].title = "资料序号";
        this.tableColumns["135"].title = "资料名称";
        this.tableColumns["136"].visible = false;
      }
      if (currentRow.code === "004") {
        this.tableColumns["134"].title = "权限序号";
        this.tableColumns["135"].title = "权限名称";
        this.tableColumns["136"].visible = false;
      }
      this.currentRow = currentRow;
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
    initOptions(This) {}
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
  }
};
</script>
<style>
[v-cloak] {
  display: none;
}
</style>
