<template>
  <el-row class="dc-container inductionApp" v-loading="loading">
    <history ref="history" :bussObject="curentRow"></history>
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
            prop="name"
            label="姓名"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.name"
              :maxLength="50"
              placeholder="请输入姓名"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="phoneNumber"
            label="手机号"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="queryFormData.phoneNumber"
              :maxLength="20"
              placeholder="请输入手机号码"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="entryTime"
            label="入职时间"
            class="dc-el-form-item_PickerTimeDate"
          >
            <el-date-picker
              v-model="queryFormData.entryTime"
              :style="{ width: '100%' }"
              placeholder="请选择入职时间"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="procStatus"
            label="审批状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.actStatus"
              :style="{ width: '100%' }"
              placeholder="请选择审批状态"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in procStatusOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="dataSubmission"
            label="资料提交"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.dataSubmission"
              :style="{ width: '100%' }"
              placeholder="请选择资料是否提交"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in submissionOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="dataValidation"
            label="资料验收"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.dataValidation"
              :style="{ width: '100%' }"
              placeholder="请选择资料是否验收"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in validationOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recordStatus"
            label="入职状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.recordStatus"
              :style="{ width: '100%' }"
              placeholder="请选择入职状态"
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
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="abandonReporting"
            label="放弃报道"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="queryFormData.abandonReporting"
              :style="{ width: '100%' }"
              placeholder="请选择是否放弃报道"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-tooltip
          disabled
          class="dc-el-tooltip_Button"
          style="margin-left:60px"
        >
          <el-button
            type="primary"
            icon="el-icon-search"
            class="dc-el-button_Button"
            @click="getList"
            >查询</el-button
          >
        </el-tooltip>

        <el-tooltip class="dc-el-tooltip_Button" disabled>
          <el-button
            type="primary"
            @click="reset"
            icon="el-icon-refresh"
            class="dc-el-button_Button"
            >重置</el-button
          >
        </el-tooltip>
        <el-tooltip class="dc-el-tooltip_Button" disabled>
          <el-button
            type="primary"
            icon="el-icon-menu"
            @click="code"
            class="dc-el-button_Button"
            >面试二维码</el-button
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
        keyPrefix="HRRZ_"
        procName="入职审批流程"
        formKey="wf/common/wfForm.vue#induction/inductionApp/form.vue"
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
        :field="tableColumns['123'].field"
        :title="tableColumns['123'].title"
        :width="tableColumns['123'].width"
        :visible="tableColumns['123'].visible"
        :params="{ sortId: '123', summary: false }"
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
        :field="tableColumns['124'].field"
        :title="tableColumns['124'].title"
        :width="tableColumns['124'].width"
        :visible="tableColumns['124'].visible"
        :params="{ sortId: '124', summary: false }"
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
        :field="tableColumns['142'].field"
        :title="tableColumns['142'].title"
        :width="tableColumns['142'].width"
        :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }"
        tree-node
        resizable
        sortable
        min-width="100px"
        align="center"
        header-align="center"
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
        sortable
        min-width="120px"
        align="center"
        header-align="center"
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
        sortable
        min-width="120px"
        align="center"
        header-align="center"
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['125'].field"
        :title="tableColumns['125'].title"
        :width="tableColumns['125'].width"
        :visible="tableColumns['125'].visible"
        :params="{ sortId: '125', summary: false }"
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
        :field="tableColumns['126'].field"
        :title="tableColumns['126'].title"
        :width="tableColumns['126'].width"
        :visible="tableColumns['126'].visible"
        :params="{ sortId: '126', summary: false }"
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
        :field="tableColumns['127'].field"
        :title="tableColumns['127'].title"
        :width="tableColumns['127'].width"
        :visible="tableColumns['127'].visible"
        :params="{ sortId: '127', summary: false }"
        tree-node
        resizable
        min-width="140px"
        align="center"
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
        min-width="120px"
        align="center"
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
        :params="{ sortId: '128', summary: false }"
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
        :field="tableColumns['130'].field"
        :title="tableColumns['130'].title"
        :width="tableColumns['130'].width"
        :visible="tableColumns['130'].visible"
        :params="{ sortId: '130', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">
          <OperationIcon
            type="success"
            content="面试通过"
            v-if="row.interviewResult === '1'"
            placement="top"
            icon-name="el-icon-success"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="面试不通过"
            v-else-if="row.interviewResult === '0'"
            placement="top"
            icon-name=" el-icon-close"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="还未获取面试结果"
            v-else
            placement="top"
            icon-name="el-icon-warning"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> </template
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">
          <OperationIcon
            type="success"
            content="审批通过"
            placement="top"
            v-if="row.procStatus.id === '8044'"
            icon-name="el-icon-success"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="退回"
            v-if="row.procStatus.id === '8046'"
            placement="top"
            icon-name="el-icon-circle-close"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            v-if="row.procStatus.id === '8045'"
            content="审批中"
            placement="top"
            icon-name="el-icon-video-play"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="未发起"
            v-if="row.procStatus.id === '8047'"
            placement="top"
            icon-name="el-icon-warning"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> </template
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['133'].field"
        :title="tableColumns['133'].title"
        :width="tableColumns['133'].width"
        :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">
          <OperationIcon
            type="success"
            content="已提交"
            v-if="row.dataSubmission === '1'"
            placement="top"
            icon-name="el-icon-success"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="未提交"
            v-if="row.dataSubmission === '0'"
            placement="top"
            icon-name="el-icon-warning"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> </template
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['129'].field"
        :title="tableColumns['129'].title"
        :width="tableColumns['129'].width"
        :visible="tableColumns['129'].visible"
        :params="{ sortId: '129', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">
          <OperationIcon
            type="success"
            content="验收通过"
            v-if="row.dataValidation && row.dataValidation === '1'"
            placement="top"
            icon-name="el-icon-success"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="未验收"
            v-else
            placement="top"
            icon-name="el-icon-warning"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> </template
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      >
        <template v-slot="{ row }">
          <OperationIcon
            type="success"
            content="已归档"
            v-if="row.recordStatus === '1'"
            placement="top"
            icon-name="el-icon-success"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="success"
            content="未归档"
            v-if="row.recordStatus === '0'"
            placement="top"
            icon-name="el-icon-warning"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon> </template
      ></ux-table-column>
      <ux-table-column
        title="操作"
        :params="{ sortId: '113' }"
        tree-node
        resizable
        width="230px"
        min-width="160px"
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
            offset="0"
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
            content="资料维护"
            placement="top"
            @click="maintenance(scope.row)"
            icon-name="el-icon-edit-outline"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            content="审批发起"
            v-if="
              scope.row.interviewResult &&
                scope.row.interviewResult === '1' &&
                scope.row.procStatus.id === '8047'
            "
            @click="startProcess(scope.row)"
            placement="top"
            icon-name="el-icon-video-play"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            @click="sendEmail(scope.row)"
            content="发送邮件"
            v-if="
              scope.row.procStatus &&
                scope.row.procStatus.id === '8044' &&
                scope.row.employeeNumber
            "
            placement="top"
            icon-name="el-icon-folder-add"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onView(scope)"
            type="primary"
            v-if="scope.row.procStatus.id != '8047'"
            content="查看"
            placement="top"
            icon-name="el-icon-view"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onShowHistory(scope)"
            type="primary"
            content="历史记录"
            v-if="scope.row.procStatus.id != '8047'"
            placement="top"
            icon-name="el-icon-info"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            content="面试通过"
            v-if="scope.row.procStatus.id === '8047'"
            @click="interViewResult(scope, '1')"
            placement="top"
            icon-name="el-icon-circle-check"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            content="面试失败"
            v-if="scope.row.procStatus.id === '8047'"
            @click="interViewResult(scope, '0')"
            placement="top"
            icon-name="el-icon-circle-close"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            content="放弃报道"
            placement="top"
            v-if="scope.row.dataValidation != '1' || !scope.row.dataValidation"
            @click="setReport(scope)"
            icon-name="el-icon-document-delete"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            content="归档"
            v-if="scope.row.dataValidation && scope.row.dataValidation === '1'"
            @click="archiveData(scope)"
            placement="top"
            icon-name="el-icon-s-custom"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            type="primary"
            @click="deleteData(scope)"
            v-if="
              !scope.row.interviewResult || scope.row.interviewResult != '1'
            "
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
    <maintenanceDialog ref="maintenanceDialog"></maintenanceDialog>
    <emailDialog ref="emailDialog"></emailDialog>
    <codeDialog ref="codeDialog"></codeDialog>
  </el-row>
</template>
<script>
import { validatenull } from "@/utils/validate";

import {
  listTsmInductionBasicPage,
  deleteTsmInductionBasic,
  listInductionHomeSearch
} from "@/api/induction/tsmInductionBasic";

import { listResourcePermission } from "@/api/admin/common/permission";
import { getHistProcInstance } from "@/api/wf/procinst";
import { getProcessByKey, getStartForm } from "@/api/wf/processdefinition";

/** 根据用户界面配置import组件 开始 */
import History from "@/views/components/history";
import QueryTag from "@/views/components/queryTag";
import QueryConditionPanel from "@/views/components/queryConditionPanel";
import { listDictItemAll } from "@/api/sys/dictItem.js";
import processPanel from "@/views/wf/common/processPanel";
import ViewColumnsSelect from "@/views/components/DcViewColumnsSelect";
import Comment from "@/views/wf/common/comment";
/** 根据用户界面配置import组件 结束 */
import metadata from "./metadata";
import ExportExcelButton from "@/components/ExportExcelButton";
import WfMainUI from "@/views/wf/common/wfMainUI";
import OperationIcon from "@/components/OperationIcon";
import maintenanceDialog from "./components/maintenanceDialog";
import emailDialog from "./components/emailDialog";
import codeDialog from "./components/codeDialog";
import { getDictTypeById } from "@/api/sys/dictType";
import {
  saveInterviewResult,
  archive,
  saveReportingType
} from "@/api/induction/tsmInductionPcOther";

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
    OperationIcon,
    maintenanceDialog,
    emailDialog,
    codeDialog
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
        name: "", // 姓名
        phoneNumber: "", // 手机号
        entryTime: "", // 入职时间
        dataSubmission: "", // 资料提交
        actStatus: "",
        dataValidation: "",
        recordStatus: "", // 入职状态
        abandonReporting: "0" // 放弃报道
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        123: {
          title: "姓名",
          field: "name",
          visible: true,
          order: 0
        },
        124: {
          title: "工号",
          field: "employeeNumber",
          visible: true,
          order: 1
        },
        142: {
          title: "性别",
          field: "gender.name",
          visible: true,
          order: 2
        },
        145: {
          title: "手机",
          field: "phoneNumber",
          visible: true,
          order: 2
        },
        143: {
          title: "部门",
          field: "department.name",
          visible: true,
          order: 2
        },
        125: {
          title: "民族",
          field: "ethnicity.name",
          visible: true,
          order: 3
        },
        126: {
          title: "身份证号码",
          field: "idNumber",
          visible: true,
          order: 4
        },
        127: {
          title: "职位",
          field: "jobPosition.postName",
          visible: true,
          order: 5
        },
        144: {
          title: "职级",
          field: "employeeRank",
          visible: true,
          order: 5
        },

        128: {
          title: "入职时间",
          field: "entryTime",
          visible: true,
          order: 6
        },
        129: {
          title: "资料验收",
          field: "dataValidation",
          visible: true,
          order: 6
        },
        130: {
          title: "面试情况",
          field: "interviewResult",
          visible: true,
          order: 6
        },
        131: {
          title: "审批状态",
          field: "procStatus",
          visible: true,
          order: 6
        },
        133: {
          title: "资料提交",
          field: "dataSubmission",
          visible: true,
          order: 6
        },
        132: {
          title: "档案状态",
          field: "recordStatus",
          visible: true,
          order: 6
        },

        113: {
          title: "操作",
          width: "160px",
          isShow: true,
          visible: true,
          order: 7
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 审批状态选项
      procStatusOptions: [],
      // 资料提交选项
      submissionOptions: [],
      // 资料验收选项
      validationOptions: [],
      // 入职状态选项
      statusOptions: [],
      // 放弃报道选项
      abandonReportOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 5,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: "", // 排序字段名
        order: "" // 排序
      },
      searchList: [],
      tableId: "1457254743943432647",
      schemeId: "1457254743943432645"
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
      }
      // 数据权限: 个人信息表tsm_induction_basic
      this.pushDataPermissions(
        this.search.params,
        this.$route.meta.routerId,
        this.tableId
      );

      if (Object.values(this.queryFormData).filter(item => !!item).length > 0) {
        //表示此时有搜索条件
        listInductionHomeSearch(this.queryFormData)
          .then(responseData => {
            if (responseData.code == 100) {
              this.tableDataTotal = responseData.data.total;
              this.tableData = responseData.data.rows
                ? responseData.data.rows
                : [];
            } else {
            }
            this.resetLoad();
          })
          .catch(error => {
            this.outputError(error);
          });
      } else {
        listTsmInductionBasicPage(this.search)
          .then(responseData => {
            if (responseData.code == 100) {
              this.tableDataTotal = responseData.data.total;
              this.tableData = responseData.data.rows
                ? responseData.data.rows
                : [];
            } else {
            }
            this.resetLoad();
          })
          .catch(error => {
            this.outputError(error);
          });
      }
    },
    //修改面试状态
    interViewResult(scope, value) {
      this.setLoad();
      saveInterviewResult(value, scope.row).then(res => {
        if (res.code === "100") {
          this.showMessage({
            type: "success",
            msg: "面试结果修改成功"
          });
          this.getList();
        } else {
          this.showMessage(res);
        }
        this.resetLoad();
      });
    },
    //放弃报道
    setReport(scope) {
      this.setLoad();
      saveReportingType(scope.row.id).then(res => {
        if (res.code === "100") {
          this.showMessage({
            type: "success",
            msg: "报道状态修改成功"
          });
          this.getList();
        } else {
          this.showMessage(res);
        }

        this.resetLoad();
      });
    },
    reset() {
      this.queryFormData = {
        name: "", // 姓名
        phoneNumber: "", // 手机号
        entryTime: "", // 入职时间
        procStatus: "", // 审批状态
        dataSubmission: "", // 资料提交
        recordStatus: "", // 入职状态
        dataValidation: "",
        abandonReporting: "" // 放弃报道
      };
      this.getList();
    },
    //二维码查看
    code() {
      this.$refs.codeDialog.init();
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
    archiveData(scope) {
      this.setLoad();
      archive(scope.row.id).then(responseData => {
        if (responseData.code == 100) {
          this.getList();
          this.showMessage({
            type: "success",
            msg: "归档成功"
          });
          this.getList();
        } else {
          this.showMessage(responseData);
        }
        this.resetLoad();
      });
    },
    deleteData(scope) {
      this.$confirm("确定要删除该条数据吗？", "确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.setLoad();
          deleteTsmInductionBasic(scope.row)
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
    //流程发起
    startProcess(row) {
      this.$refs.processPanel.onCreate(row);
    },
    //发送邮件
    sendEmail(row) {
      this.$refs.emailDialog.init(row);
    },
    async pageInit() {
      this.setLoad();
      try {
        this.initOptions(this.queryModel);
        this.search.params = [];
        // 数据权限: 个人信息表tsm_induction_basic
        this.pushDataPermissions(
          this.search.params,
          this.$route.meta.routerId,
          this.tableId
        );
        let [
          listTsmInductionBasicRespData,
          listPermissionRespData
        ] = await Promise.all([
          listTsmInductionBasicPage(this.search),
          listResourcePermission(this.$route.meta.routerId)
        ]);
        if (
          listTsmInductionBasicRespData.code == 100 &&
          listPermissionRespData.code == 100
        ) {
          this.tableDataTotal = listTsmInductionBasicRespData.data.total;
          this.tableData = listTsmInductionBasicRespData.data.rows
            ? listTsmInductionBasicRespData.data.rows
            : [];
          this.permission.view = listPermissionRespData.data.find(item => {
            return item.permission === "inductionApp:read";
          });
          this.permission.export = listPermissionRespData.data.find(item => {
            return item.permission === "inductionApp:export";
          });

          // this.permission.add = listPermissionRespData.data.find(item => {
          //   return item.permission === "inductionApp:create";
          // });
          this.permission.edit = listPermissionRespData.data.find(item => {
            return item.permission === "inductionApp:update";
          });
          this.permission.remove = listPermissionRespData.data.find(item => {
            return item.permission === "inductionApp:delete";
          });
        } else {
          this.showMessage(
            listPermissionRespData.code != 100
              ? listPermissionRespData
              : listTsmInductionBasicRespData
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

    //获取审批状态数据
    getApproval() {
      getDictTypeById("1485271468451037312").then(res => {
        this.procStatusOptions = res.data.dictItemList;
      });
    },
    //获取资料提交
    getSourceSubmit() {
      getDictTypeById("1513227015445110955").then(res => {
        this.submissionOptions = res.data.dictItemList;
      });
    },
    //获取资料验收
    getSourceCheck() {
      getDictTypeById("1513227015445110960").then(res => {
        this.validationOptions = res.data.dictItemList;
      });
    },
    //获取入职状态
    getEntryStatus() {
      getDictTypeById("1513227015445110967").then(res => {
        this.statusOptions = res.data.dictItemList;
      });
    },
    //获取放弃报道
    getRenounce() {
      getDictTypeById("1513227015445110971").then(res => {
        this.abandonReportOptions = res.data.dictItemList;
      });
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.getApproval();
      this.getSourceSubmit();
      this.getSourceCheck();
      this.getEntryStatus();
      this.getRenounce();
    },
    //资料维护
    maintenance(row) {
      this.$refs.maintenanceDialog.init(row);
    }
  },
  watch: {},
  mounted() {
    this.pageInit();

    this.columnDrop(this.$refs.table);
  }
};
</script>
<style  lang="scss">
.inductionApp {
  .process-panel-btn {
    bottom: 10px !important;
  }
}
</style>
