<template>
  <el-row class="dc-container" v-loading="loading">
    <edit-form
      ref="editForm"
      v-on:save-finished="getList"
      v-on:before-load="setLoad"
      v-on:after-load="resetLoad"
      :permission="permission"
    ></edit-form>
    <el-row gutter="0" type="flex" justify="start" align="top" class="dc-el-row_ElRow">
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
          class="dc-query-tag_ElConditionTag"
        ></query-tag>
      </el-col>
      <el-col :span="6">
        <div style="text-align: right">
          <el-button-group class="dc-el-button-group_ButtonGroup">
            <el-tooltip effect="dark" content="搜索" placement="top">
              <el-button v-on:click="onSearch" type="primary" icon="el-icon-search"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="重置" placement="top">
              <el-button
                v-on:click="reset()"
                type="primary"
                icon="el-icon-refresh-right"
              ></el-button>
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
        <el-col :span="6">
          <el-form-item prop="name" label="签约人" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.name"
              :maxLength="128"
              placeholder="请输入姓名"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="employeeNumber" label="工号" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.employeeNumber"
              :maxLength="64"
              placeholder="请输入工号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="department" label="申请人部门" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.department"
              :style="{ width: '100%' }"
              placeholder="请选择部门"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in departmentOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="contractStatus" label="合约状态" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.contractStatus"
              :style="{ width: '100%' }"
              placeholder="请选择合约状态"
              clearable
              value-key="value"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in contractStatusOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="signStatus" label="签署状态" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="queryFormData.signStatus"
              :style="{ width: '100%' }"
              placeholder="请选择签署状态"
              clearable
              value-key="value"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in signStatusOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="createDate" label="创建时间" class="dc-el-form-item_PickerTimeDate">
            <el-date-picker
              v-model="queryFormData.createDate"
              :style="{ width: '100%' }"
              placeholder="请选择创建时间"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <process-panel
        ref="processPanel"
        :bizAdd="false"
        :tenantId="currentUser.company.id"
        keyPrefix="HYXQ_"
        procName="入职续签申请"
        formKey="wf/common/wfForm.vue#induction/contract/inductionContractRenewal/contractWf.vue"
        procTitile="${start_user_name}发起的（${form.theme}）入职续签申请"
        v-on:v-loading="onVLoading"
        class="dc-process-panel_ProcessPanel"
      ></process-panel>
    </el-row>
    <el-row gutter="0" type="flex" justify="end" align="top" class="dc-el-row_ElRow">
      <el-tooltip v-show="permission.generate" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-circle-check" v-on:click="onBulkGenerateCheck" class="dc-el-button_Button">生成合约</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.start" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-video-play" v-on:click="onBulkStartCheck" class="dc-el-button_Button">发起合约</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.sign" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-top" class="dc-el-button_Button" v-on:click="onBulkSignCheck">发起签署</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-document" class="dc-el-button_Button" v-on:click="onBulkCopyCheck">复制</el-button>
      </el-tooltip>
      <el-tooltip v-show="permission.add" disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-plus" v-on:click="onCreate">添加</el-button>
      </el-tooltip>
    </el-row>
    <el-row>
      <span class="select-name">
        <OperationIcon
          type="primary"
          content="已选择人员"
          placement="top"
          icon-name="el-icon-info"
          class="dc-OperationIcon_IconButton"
        ></OperationIcon>
        已选择{{selectBatchContractList.length}}项 姓名：{{selectContractUserName}}
      </span>
    </el-row>
    <ux-grid
      column-key
      ref="table"
      :data="tableData"
      border
      v-on:sort-change="onSortChange"
      v-on:header-dragend="onWidthChange($refs.table)"
      :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
      @selection-change="handleSelectionChange"
      class="dc-ux-grid_QueryTable"
    >
      <ux-table-column
        align="center"
        header-align="center"
        type="checkbox"
        width="55">
      </ux-table-column>
      <ux-table-column
        :field="tableColumns['133'].field"
        :title="tableColumns['133'].title"
        :width="tableColumns['133'].width"
        :visible="tableColumns['133'].visible"
        :params="{ sortId: '133', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['135'].field"
        :title="tableColumns['135'].title"
        :width="tableColumns['135'].width"
        :visible="tableColumns['135'].visible"
        :params="{ sortId: '135', summary: false }"
        tree-node
        resizable
        width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['134'].field"
        :title="tableColumns['134'].title"
        :width="tableColumns['134'].width"
        :visible="tableColumns['134'].visible"
        :params="{ sortId: '134', summary: false }"
        tree-node
        resizable
        min-width="100px"
        align="left"
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
        min-width="120px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['138'].field"
        :title="tableColumns['138'].title"
        :width="tableColumns['138'].width"
        :visible="tableColumns['138'].visible"
        :params="{ sortId: '138', summary: false }"
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
        :field="tableColumns['142'].field"
        :title="tableColumns['142'].title"
        :width="tableColumns['142'].width"
        :visible="tableColumns['142'].visible"
        :params="{ sortId: '142', summary: false }"
        tree-node
        resizable
        width="100px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['131'].field"
        :title="tableColumns['131'].title"
        :width="tableColumns['131'].width"
        :visible="tableColumns['131'].visible"
        :params="{ sortId: '131', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="left"
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
        min-width="120px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['132'].field"
        :title="tableColumns['132'].title"
        :width="tableColumns['132'].width"
        :visible="tableColumns['132'].visible"
        :params="{ sortId: '132', summary: false }"
        tree-node
        resizable
        width="150px"
        align="left"
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
        min-width="150px"
        align="left"
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
        min-width="120px"
        align="right"
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
        min-width="120px"
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
        min-width="130px"
        align="left"
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
        align="right"
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
        min-width="120px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['147'].field"
        :title="tableColumns['147'].title"
        :width="tableColumns['147'].width"
        :visible="tableColumns['147'].visible"
        :params="{ sortId: '147', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['174'].field"
        :title="tableColumns['174'].title"
        :width="tableColumns['174'].width"
        :visible="tableColumns['174'].visible"
        :params="{ sortId: '174', summary: false }"
        tree-node
        resizable
        width="150px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['166'].field"
        :title="tableColumns['166'].title"
        :width="tableColumns['166'].width"
        :visible="tableColumns['166'].visible"
        :params="{ sortId: '166', summary: false }"
        tree-node
        resizable
        width="150px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['167'].field"
        :title="tableColumns['167'].title"
        :width="tableColumns['167'].width"
        :visible="tableColumns['167'].visible"
        :params="{ sortId: '167', summary: false }"
        tree-node
        resizable
        width="150px"
        align="right"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['169'].field"
        :title="tableColumns['169'].title"
        :width="tableColumns['169'].width"
        :visible="tableColumns['169'].visible"
        :params="{ sortId: '169', summary: false }"
        tree-node
        resizable
        width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['171'].field"
        :title="tableColumns['171'].title"
        :width="tableColumns['171'].width"
        :visible="tableColumns['171'].visible"
        :params="{ sortId: '171', summary: false }"
        tree-node
        resizable
        width="100px"
        align="center"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['172'].field"
        :title="tableColumns['172'].title"
        :width="tableColumns['172'].width"
        :visible="tableColumns['172'].visible"
        :params="{ sortId: '172', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
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
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['175'].field"
        :title="tableColumns['175'].title"
        :width="tableColumns['175'].width"
        :visible="tableColumns['175'].visible"
        :params="{ sortId: '175', summary: false }"
        tree-node
        resizable
        min-width="180px"
        align="left"
        header-align="center"
        sortable
        show-overflow
        class="dc-ux-table-column_ElTableColumn"
      ></ux-table-column>
      <ux-table-column
        :field="tableColumns['176'].field"
        :title="tableColumns['176'].title"
        :width="tableColumns['176'].width"
        :visible="tableColumns['176'].visible"
        :params="{ sortId: '176', summary: false }"
        tree-node
        resizable
        min-width="120px"
        align="right"
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
        width="160px"
        fixed="right"
        align="left"
        header-align="center"
        class="dc-ux-table-column_ElTableOptColumn"
      >
        <template v-slot:header="scope">
          <span>操作</span>
<!--          <el-popover ref="popover" width="10" trigger="click" placement="bottom" offset="10" class="dc-el-popover_ElPopover">-->
<!--            <view-columns-select-->
<!--              v-model="tableColumns"-->
<!--              ref="viewColumnsSelect"-->
<!--              v-on:save-column-view="saveColumn($event, 'table')"-->
<!--              v-on:show-all-column="showAllColumn($event, 'table')"-->
<!--              v-on:show-default-column="showDefaultColumn('table')"-->
<!--              class="dc-view-columns-select_ViewColumnsSelect"-->
<!--            ></view-columns-select>-->
<!--            <template slot="reference">-->
<!--              <OperationIcon type="primary" content="自定义列" placement="top" icon-name="el-icon-tickets"></OperationIcon>-->
<!--            </template>-->
<!--          </el-popover>-->
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
            v-on:click="onEdit(scope)"
            type="primary"
            v-show="permission.edit && scope.row.contractStatus && scope.row.contractStatus.value != '9'"
            content="编辑"
            placement="top"
            icon-name="el-icon-edit"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onGenerate(scope)"
            type="primary"
            v-show="permission.generate && scope.row.contractStatus && scope.row.contractStatus.value == '0'"
            content="生成合约"
            placement="top"
            icon-name="el-icon-circle-check"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onStart(scope)"
            type="primary"
            v-show="permission.start && scope.row.contractStatus && scope.row.contractStatus.value == '1'"
            content="发起合约"
            placement="top"
            icon-name="el-icon-video-play"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onSign(scope.row, false)"
            type="primary"
            v-show="permission.sign && scope.row.contractStatus && scope.row.contractStatus.value == '9' && (scope.row.procInst == null || scope.row.procInst == '') && scope.row.signStatus.value != '6'"
            content="发起签署"
            placement="top"
            icon-name="el-icon-top"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onReport(scope)"
            type="primary"
            v-show="permission.report && scope.row.contractStatus && scope.row.contractStatus.value == '9' && scope.row.signStatus.value != '6'"
            content="存证报告"
            placement="top"
            icon-name="el-icon-tickets"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onViewContract(scope)"
            type="primary"
            v-show="permission.edit && scope.row.contractDate != null && scope.row.contractDate != ''"
            content="预览合约"
            placement="top"
            icon-name="el-icon-search"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onUploadContract(scope)"
            type="primary"
            v-show="permission.export && scope.row.contractDate != null && scope.row.contractDate != ''"
            content="下载合约文档"
            placement="top"
            icon-name="el-icon-download"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onCopy(scope)"
            type="primary"
            v-show="permission.add"
            content="复制"
            placement="top"
            icon-name="el-icon-document"
            class="dc-OperationIcon_IconButton"
          ></OperationIcon>
          <OperationIcon
            v-on:click="onDelete(scope)"
            type="danger"
            v-show="permission.remove && scope.row.contractStatus && scope.row.contractStatus.value != '9'"
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
    <select-user
      ref="userForm"
      :wfDaiog="this"
      v-on:select-user-finished="afterUserSelected"
    ></select-user>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'

import {
  listInductionContractRenewalPage,
  deleteInductionContractRenewal,
  generateById,
  startById,
  viewReport,
  viewContract,
  uploadContract,
  bulkGenerate,
  bulkStart,
  bulkInsertInductionContractRenewal,
  firstCreateContract,
  bulkSignCreate
} from '@/api/induction/contract/inductionContractRenewal'

import { listResourcePermission } from '@/api/admin/common/permission'

import { getProcessByKey, getStartForm} from "@/api/wf/processdefinition";

/** 根据用户界面配置import组件 开始 */
import EditForm from './form'
import QueryTag from '@/views/components/queryTag'
import QueryConditionPanel from '@/views/components/queryConditionPanel'
import { listDepartmentAll } from '@/api/org/department.js'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import ViewColumnsSelect from '@/views/components/DcViewColumnsSelect'
import processPanel from "@/views/wf/common/processPanel";
/** 根据用户界面配置import组件 结束 */
import metadata from './metadata'
import ExportExcelButton from '@/components/ExportExcelButton'
import DcMain from '@/views/components/dcMain'
import OperationIcon from '@/components/OperationIcon'
import selectUser from "@/views/wf/common/userForm";
export default {
  extends: DcMain,
  components: {
    /** 根据用户界面配置组件 开始 */
    EditForm,
    QueryTag,
    QueryConditionPanel,
    ViewColumnsSelect,
    /** 根据用户界面配置组件 结束 */
    ExportExcelButton,
    OperationIcon,
    selectUser,
    processPanel
  },
  data() {
    return {
      permission: {
        view: false,
        add: false,
        edit: false,
        remove: false,
        export: false,
        start: false,
        generate: false,
        sign: false,
        report: false
      },
      metadata,
      /** 根据用户界面配置生成data数据 开始 */
      queryFormData: {
        name: '', // 签约人
        employeeNumber: '', // 工号
        department: {
          id: null,
          name: null,
          code: null
        },
        contractStatus: {
          name: null,
          value: null
        },
        signStatus: {
          name: null,
          value: null,
          id: null
        },
        createDate: '' // 创建时间
      },
      tableData: [],
      // 查询表格列头
      tableColumns: {
        133: {
          title: '签约人',
          field: 'name',
          visible: true,
          order: 0
        },
        135: {
          title: '联系电话',
          field: 'phone',
          visible: true,
          order: 1
        },
        134: {
          title: '性别',
          field: 'gender.name',
          visible: false,
          order: 2
        },
        137: {
          title: '邮箱',
          field: 'email',
          visible: false,
          order: 3
        },
        138: {
          title: '居住地地址',
          field: 'address',
          visible: false,
          order: 4
        },
        142: {
          title: '工号',
          field: 'employeeNumber',
          visible: true,
          order: 5
        },
        131: {
          title: '合同编号',
          field: 'code',
          visible: false,
          order: 6
        },
        151: {
          title: '所属部门',
          field: 'department.name',
          visible: true,
          order: 7
        },
        132: {
          title: '合同主题',
          field: 'theme',
          visible: true,
          order: 8
        },
        136: {
          title: '身份证号码',
          field: 'idNumber',
          visible: false,
          order: 9
        },
        139: {
          title: '入职时间',
          field: 'entryTime',
          visible: false,
          order: 10
        },
        140: {
          title: '试用期',
          field: 'probationMonth.name',
          visible: false,
          order: 11
        },
        144: {
          title: '从事工作',
          field: 'bJob',
          visible: false,
          order: 12
        },
        145: {
          title: '合同开始时间',
          field: 'contractStartTime',
          visible: false,
          order: 13
        },
        146: {
          title: '合同结束时间',
          field: 'contractEndTime',
          visible: false,
          order: 14
        },
        147: {
          title: '试用期结束时间',
          field: 'probationEndTime',
          visible: false,
          order: 15
        },
        174: {
          title: '创建时间',
          field: 'createDate',
          visible: true,
          order: 16
        },
        166: {
          title: '合同生成时间',
          field: 'contractDate',
          visible: true,
          order: 17
        },
        167: {
          title: '合同发起时间',
          field: 'contractStartDate',
          visible: true,
          order: 18
        },
        169: {
          title: '合约状态',
          field: 'contractStatus.name',
          visible: true,
          order: 19
        },
        171: {
          title: '签署状态',
          field: 'signStatus.name',
          visible: true,
          order: 20
        },
        172: {
          title: '备注信息',
          field: 'remarks',
          visible: false,
          order: 21
        },
        173: {
          title: '创建者',
          field: 'createBy',
          visible: false,
          order: 22
        },
        175: {
          title: '更新者',
          field: 'updateBy',
          visible: false,
          order: 23
        },
        176: {
          title: '更新时间',
          field: 'updateDate',
          visible: false,
          order: 24
        },
        114: {
          title: '操作',
          width: '140px',
          isShow: true,
          visible: true,
          order: 25
        }
      },
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      // 选项变量
      // 申请人部门选项
      departmentOptions: [],
      // 合约状态选项
      contractStatusOptions: [],
      // 签署状态选项
      signStatusOptions: [],
      /** 根据用户界面配置生成data数据 结束 */
      version: 5,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],

        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      searchList: [],
      isBulkCreate: false,
      keyPrefix: 'HYXQ_', // 流程前缀
      companyId: currentUser.company.id, // 公司ID
      selectBatchContractList: [], // 复选框多选
      selectContractUserName: '', // 复选框多选人员名称
      actNodes: null, // 流程节点
      proc: {}, // 流程对象
      currentSignRow: {}, // 当前发起签署对象
      contractList: [], // 批量签署对象
      tableId: '1538459003414151556',
      schemeId: '1538459003414151554'
    }
  },
  computed: {},
  methods: {
    getList() {
      this.setLoad()
      this.selectBatchContractList = []
      this.selectContractUserName = '' // 复选框处理
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

        this.search.params.push({
          columnName: 'employee_number',
          queryType: 'like',

          value: !validatenull(this.queryFormData.employeeNumber) ? this.queryFormData.employeeNumber : null
        })

        this.search.params.push({
          columnName: 'department_id',
          queryType: '=',

          value: !validatenull(this.queryFormData.department.id) ? this.queryFormData.department.id : null
        })

        this.search.params.push({
          columnName: 'contract_status',
          queryType: '=',

          value: !validatenull(this.queryFormData.contractStatus.value) ? this.queryFormData.contractStatus.value : null
        })

        this.search.params.push({
          columnName: 'sign_status',
          queryType: '=',

          value: !validatenull(this.queryFormData.signStatus.value) ? this.queryFormData.signStatus.value : null
        })

        this.search.params.push({
          columnName: 'create_date',
          queryType: '=',

          value: !validatenull(this.queryFormData.createDate) ? this.queryFormData.createDate : null
        })
      }
      // 数据权限: 合约续签tsm_induction_contract_renewal
      this.pushDataPermissions(this.search.params, this.$route.meta.routerId, this.tableId)
      listInductionContractRenewalPage(this.search)
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
    reset() {
      if (this.isQueryConditionPanel) {
        this.$refs.conditionPanel.reset()
      } else {
        this.$refs.queryForm.resetFields()
        this.getList()
      }
    },
    async pageInit() {
      this.setLoad()
      this.initOptions(this.queryModel)
      this.onSearch()
      listResourcePermission(this.$route.meta.routerId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.permission.view = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:read'
            })
            this.permission.export = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:export'
            })
            this.permission.add = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:create'
            })
            this.permission.edit = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:update'
            })
            this.permission.remove = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:delete'
            })
            this.permission.start = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:start'
            })
            this.permission.generate = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:generat'
            })
            this.permission.sign = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:sign'
            })
            this.permission.report = responseData.data.find((item) => {
              return item.permission === 'inductionContractRenewal:report'
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
    onVLoading(loading) {
      loading ? this.setLoad() : this.resetLoad();
    },
    handleSelectionChange(val) {
      this.selectBatchContractList = val
      let nameList = []
      if (!validatenull(val)) {
        val.forEach(item => {
          nameList.push(item.name)
        })
      }
      this.selectContractUserName = nameList.join('、')
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
          deleteInductionContractRenewal(scope.row)
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
    onBulkGenerateCheck() { // 生成合约校验（已发起合约不能重复生成）
      const length = this.selectBatchContractList.length
      const noStart = [] // 未发起人员名单
      const yesStart = [] // 已发起人员名单
      let yesStartNum = 0 // 已发起记录条数
      this.selectBatchContractList.forEach(item => {
        if (item.contractStatus.value != '9') {
          noStart.push(item.induction ? item.induction.name : '') // 未发起人员名单
        } else {
          yesStartNum += 1
          yesStart.push(item.induction ? item.induction.name : '') // 已发起人员名单
        }
      })
      if (length <= 0) {
        this.$alert('请选择要生成的记录', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      if (yesStartNum == length) {
        this.$alert('当前所选用户皆已生成合约，请勿重复操作！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      let message = '请确认是否对所选择的' + length + '名员工生成入职合同？'
      if (yesStartNum > 0) {
        let count = length - yesStartNum
        message = '员工【' + yesStart.join(',') + '】尚未生成合约或已经发起合约，无法发起操作！是否忽略后继续对余下' + count + '名员工【' + noStart.join(',') + '】生成合约？'
      }
      this.onBulkGenerate(message)
    },
    onBulkGenerate(message) { // 批量生成合约
      const contractList = this.selectBatchContractList.filter(item => {
        return item.contractStatus.value != '9'
      })
      this.$confirm(message, '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        bulkGenerate(contractList).then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({
              type: 'success',
              msg: '生成合约成功'
            })
            this.getList() // 更新列表
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }).catch(() => {})
    },
    onBulkStartCheck() {
      const length = this.selectBatchContractList.length
      const noGenerate = [] // 未生成人员名单
      const yesGenerate = [] // 已生成人员名单
      let noGenerateNum = 0
      this.selectBatchContractList.forEach(item => {
        if (item.contractStatus.value == '0' || item.contractStatus.value == '9') {
          noGenerateNum += 1
          noGenerate.push(item.induction ? item.induction.name : '') // 未生成人员名单
        } else {
          yesGenerate.push(item.induction ? item.induction.name : '') // 已生成合约人员名单
        }
      })
      if (length <= 0) {
        this.$alert('请选择要发起的记录', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      if (noGenerateNum == length) {
        this.$alert('当前所选用户皆无法发起合约，请检查合约状态或联系管理员！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      let message = '请确认是否对所选择的' + length + '名员工发起入职合同？'
      if (noGenerateNum > 0) {
        let count = length - noGenerateNum
        message = '员工【' + noGenerate.join(',') + '】尚未生成合约或已经发起合约，无法发起操作！是否忽略后继续对余下' + count + '名员工【' + yesGenerate.join(',') + '】发起合约？'
      }
      this.onBulkStart(message)
    },
    onBulkStart(message) {
      const contractList = this.selectBatchContractList.filter(item => {
        return item.contractStatus.value == '1'
      })
      this.$confirm(message, '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        bulkStart(contractList).then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({
              type: 'success',
              msg: '发起合约成功'
            })
            this.getList() // 更新列表
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }).catch(() => {})
    },
    onBulkSignCheck() {
      const length = this.selectBatchContractList.length
      const noSing = [] // 不可发起签署人员名单
      const yesSing = [] // 可以发起人员名单
      let noSingNum = 0
      this.selectBatchContractList.forEach(item => {
        if (item.contractStatus.value == '9' && validatenull(item.procInst)) { // 可以发起签署
          yesSing.push(item.induction ? item.induction.name : '') // 可以发起人员名单
        } else { // 不可发起签署
          noSingNum += 1
          noSing.push(item.induction ? item.induction.name : '') // 不可发起签署人员名单
        }
      })
      if (length <= 0) {
        this.$alert('请选择要发起的记录', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      if (noSingNum == length) {
        this.$alert('当前所选用户皆无法发起签署，请检查合约状态或联系管理员！', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      let message = '请确认是否对所选择的' + length + '名员工发起入职合同申请？'
      if (noSingNum > 0) {
        let count = length - noSingNum
        message = '员工【' + noSing.join(',') + '】尚未发起合约或已经发起签署，无法发起操作！是否忽略后继续对余下' + count + '名员工【' + yesSing.join(',') + '】发起签署？'
      }
      this.onBulkSign(message)
    },
    onBulkSign(message) {
      this.contractList = this.selectBatchContractList.filter(item => {
        this.$set(item, 'actNodes', null) // 选择节点及审批用户
        return item.contractStatus.value == '9' && validatenull(item.procInst)
      })
      this.$confirm(message, '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.onSign(this.contractList[0], true)
      }).catch(() => {})
    },
    onBulkCopyCheck() { // 复制已作废记录
      const length = this.selectBatchContractList.length
      if (length <= 0) {
        this.$alert('请选择要复制的记录', '提示', {
          confirmButtonText: '确定',
          type: 'info'
        })
        return false
      }
      let message = '请确认是否对所选择的' + length + '名员工进行复制添加？'
      this.onBulkCopy(message)
    },
    onBulkCopy(message) {
      this.selectBatchContractList.forEach(item => {
        item.id = null
      })
      this.$confirm(message, '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        bulkInsertInductionContractRenewal(this.selectBatchContractList).then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.getList() // 更新列表
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }).catch(() => {})
    },
    onGenerate(scope) { // 生成合约
      this.$confirm('确定生成合约吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        generateById(scope.row.id).then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({
              type: 'success',
              msg: '生成合约成功'
            })
            this.getList() // 更新列表
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }).catch(() => {})
    },
    onStart(scope) {
      this.$confirm('确定发起合约吗？', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.setLoad()
        startById(scope.row.id).then((responseData) => {
          if (responseData.code == 100) {
            this.showMessage({
              type: 'success',
              msg: '发起合约成功'
            })
            this.getList() // 更新列表
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }).catch(() => {})
    },
    onSign(row, type) { // 发起签署
      this.isBulkCreate = type
      let procKey = this.keyPrefix + this.companyId; // 合同审批流程key
      this.setLoad()
      getProcessByKey(procKey, this.companyId).then(responseData => {
        if (responseData.id) {
          this.startProcess(responseData, row);
        } else {
          // 没有找到流程
          if (responseData.code == 404) {
            this.showMessage({
              type: "warning",
              msg: "请联系公司管理员配置审批流程。"
            });
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad()
        }
      }).catch(error => {
        this.resetLoad()
        this.outputError(error);
      });
    },
    startProcess(proc, row) {
      getStartForm(proc.id).then(responseData => {
        if (responseData.hasOwnProperty("key")) {
          if (responseData.key) {
            this.currentSignRow = row
            this.proc = proc
            this.doCreate(proc.id, row)
          } else {
            this.showMessage({
              type: "warning",
              msg: "流程未配置form，请联系管理员。"
            });
            this.resetLoad()
          }
        } else {
          this.showMessage(responseData);
        }
      }).catch(error => {
        this.resetLoad()
        this.outputError(error);
      });
    },
    // 创建一个新流程 process流程 actNodes是提交的用户任务节点及其审批用户
    doCreate(procDefId, row) {
      this.$set(row, 'actNodes', null) // 选择节点及审批用户
      firstCreateContract(procDefId, row).then(responseData => {
        this.handleResponse(responseData)
        this.resetLoad()
      }).catch(error => {
        this.resetLoad()
        this.outputError(error)
      })
    },
    handleResponse(responseData) {
      if (responseData.code == 100) {
        this.actNodes = responseData.data.actNodes
        if (responseData.data.code == "Done" || responseData.data.code == "AutoSubmit") {
          this.getList()
          if (responseData.data && responseData.data.msg) {
            responseData.msg = responseData.data.msg;
          }
          this.showMessage(responseData);
        } else if (responseData.data.code == "SelectUser") {
          // 显示用户选择框
          this.$refs.userForm.$emit(
            "openUserDialog",
            responseData.data.actNodes
          );
        } else {
          this.showMessage({
            type: responseData.data.code == "Warning" ? "warning" : "error",
            msg: responseData.data.msg
          });
        }
      } else {
        this.showMessage(responseData);
      }
    },
    afterUserSelected(actNodes) {
      this.setLoad()
      if (this.isBulkCreate) { // 批量
        this.contractList.forEach(item => {
          this.$set(item, 'actNodes', actNodes) // 选择节点及审批用户
        })
        bulkSignCreate(this.proc.id, this.contractList).then(responseData => {
          if (responseData.code == 100) {
            this.getList()
          }
          this.resetLoad()
        }).catch(error => {
          this.outputError(error)
          this.resetLoad()
        })
      } else { // 单个
        this.$set(this.currentSignRow, 'actNodes', actNodes) // 选择节点及审批用户
        firstCreateContract(this.proc.id, this.currentSignRow).then(responseData => {
          if (responseData.code == 100) {
            this.getList()
          }
          this.resetLoad()
        }).catch(error => {
          this.resetLoad()
          this.outputError(error)
        })
      }
    },
    onReport(scope) { // 存证报告
      this.setLoad()
      viewReport(scope.row.id).then((responseData) => {
        if (responseData.code == 100) {
          let url = responseData.data
          window.open(url)
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    onViewContract(scope) { // 预览合约文档
      this.setLoad()
      viewContract(scope.row.id).then((responseData) => {
        if (responseData.code == 100) {
          let url = responseData.data
          window.open(url)
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
      })
    },
    onUploadContract(scope) { // 下载合约文档
      this.setLoad()
      uploadContract(scope.row.id).then((responseData) => {
        const blob = new Blob([responseData]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", '入职合同.pdf');
        document.body.appendChild(dom);
        dom.click();
        this.resetLoad()
      }).catch((error) => {
        this.outputError(error)
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

    listDepartmentOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'company_id',
          queryType: '=',
          value: currentUser.company.id == '0' ? '' : currentUser.company.id
        }
      ])
      // 数据权限: 部门org_department
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000014')

      listDepartmentAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listContractStatusOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'dict_type_id',
          queryType: '=',
          value: '1548811077194866691'
        }
      ])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.contractStatusOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listSignStatusOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'dict_type_id',
          queryType: '=',
          value: '1548811077194866696'
        }
      ])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.signStatusOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    // 初始化自定义类型选择框选项变量
    initOptions(This) {
      this.listDepartmentOptions()

      this.listContractStatusOptions()

      this.listSignStatusOptions()
    }
  },
  watch: {},
  mounted() {
    this.pageInit()

    this.columnDrop(this.$refs.table)
  }
}
</script>
<style lang='scss' scoped>
.select-name {
  width: 100%;
  display: block;
  color: #606266;
  font-size: 14px;
  margin-top: 10px;
  padding: 6px 8px;
  border: 1px solid #dbdbdb;
  background: #f5f5f5;
}
</style>
