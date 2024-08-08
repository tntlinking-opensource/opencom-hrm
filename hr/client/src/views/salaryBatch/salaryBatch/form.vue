<template>
  <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" width="90%" :fullscreen="false"
             :title="dialogProps.title" :visible.sync="dialogProps.visible" :close-on-click-modal="false"
             class="dc-el-dialog_ElDialog">
    <el-form
      ref="queryForm"
      :model="queryFormData"
      label-width="100px"
      v-if="!isQueryConditionPanel"
      class="dc-el-form_ElQueryForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="employeeName" label="姓名：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.employeeName"
              :maxLength="100"
              placeholder="请输入姓名"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="idCardNo" label="证件号码：" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="queryFormData.idCardNo"
              :maxLength="100"
              placeholder="请输入证件号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
<!--        <el-col :span="6">-->
<!--          <el-form-item prop="saBatchId" label="la" :style="{'display': 'none'}" class="dc-el-form-item_SingleInput">-->
<!--            <el-input-->
<!--              v-model="queryFormData.saBatchId"-->
<!--              :maxLength="100"-->
<!--              placeholder=""-->
<!--              clearable-->
<!--              class="dc-el-input_SingleInput"-->
<!--            ></el-input>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :span="2">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-search" v-on:click="onSearch" class="dc-el-button_Button">
              查询
            </el-button>
          </el-tooltip>
        </el-col>
        <el-col :span="2">
          <el-tooltip disabled class="dc-el-tooltip_Button">
            <el-button type="primary" icon="el-icon-refresh" v-on:click="refresh" class="dc-el-button_Button">重置
            </el-button>
          </el-tooltip>
        </el-col>
      </el-row>

      <el-row>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-s-unfold" v-on:click="getBilling" class="dc-el-button_Button" v-show="action != 'view'">
            获取账单数据
          </el-button>
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-s-fold" v-on:click="getSpecialAddDeduct" class="dc-el-button_Button" v-show="action != 'view'">
            获取专项附加
          </el-button>
        </el-tooltip>
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-edit" v-on:click="onSubmit" class="dc-el-button_Button" v-show="action != 'view'">工资计算</el-button>
        </el-tooltip>
<!--        <el-tooltip disabled class="dc-el-tooltip_Button">-->
<!--          <el-button type="primary" icon="el-icon-circle-close" v-on:click="doDelete" class="dc-el-button_Button">-->
<!--            删除-->
<!--          </el-button>-->
<!--        </el-tooltip>-->
        <el-tooltip disabled class="dc-el-tooltip_Button">
          <el-button type="primary" icon="el-icon-circle-check" v-on:click="doSave" class="dc-el-button_Button" v-show="action != 'view'">
            保存
          </el-button>
        </el-tooltip>
      </el-row>
    </el-form>
    <el-divider class="dc-el-divider_ElDivider"></el-divider>
    <el-row class="dc-container">
      <el-form ref="editForm211" :model="editForm211Data" label-width="100px" class="dc-el-form_ElEditForm" :disabled="action == 'view'">
        <el-row>
          <ux-grid
            :data="editForm211Data.table213Data"
            ref="table213"
            :currentRow="table213CurrentRow"
            height="300px"
            border
            stripe
            :edit-config="{ trigger: 'click', mode: 'cell' }"
            class="dc-ux-grid_EditTable"
            @selection-change="handleSelectionChange"
          >
            <ux-table-column align="center" header-align="center" type="checkbox" width="55" fixed="left" renderable="isSelected">
            </ux-table-column>
            <ux-table-column
              fixed="left"
              field="employeeNo"
              title="工号"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="100"
              class="dc-ux-table-column_ElTableColumn"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.saDoc.employeeNo'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.saDoc.employeeNo"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.saDoc.employeeNo'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.saDoc.employeeNo }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              fixed="left"
              field="employeeName"
              title="员工姓名"
              tree-node
              resizable
              align="center"
              header-align="center"
              show-overflow
              min-width="100"
              class="dc-ux-table-column_ElTableColumn"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.saDoc.employeeName'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.saDoc.employeeName"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.saDoc.employeeName'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.saDoc.employeeName }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              fixed="left"
              field="idCardNo"
              title="证件号码"
              tree-node
              resizable
              align="center"
              header-align="center"
              show-overflow
              min-width="180"
              class="dc-ux-table-column_ElTableColumn"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.saDoc.idCardNo'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.saDoc.idCardNo"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.saDoc.idCardNo'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.saDoc.idCardNo }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              fixed="left"
              field="payYearMonth"
              title="工资所属月"
              tree-node
              resizable
              align="center"
              header-align="center"
              show-overflow
              min-width="100"
              class="dc-ux-table-column_ElTableColumn"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.payYearMonth'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.payYearMonth"
                    :maxLength="18"
                    placeholder="请输入基本工资"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.payYearMonth'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.payYearMonth }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def1"
              title="基本工资"
              tree-node
              resizable
              align="center"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def1'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def1"
                    :maxLength="18"
                    placeholder="请输入基本工资"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def1'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def1 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def2"
              title="奖金"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def2'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def2"
                    :maxLength="18"
                    placeholder="请输入奖金"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def2'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def2 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def3"
              title="绩效奖金"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def3'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def3"
                    :maxLength="18"
                    placeholder="请输入绩效奖金"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def3'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def3 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def4"
              title="加班费"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def4'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def4"
                    :maxLength="18"
                    placeholder="请输入加班费"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def4'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def4 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def5"
              title="薪资调整"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def5'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def5"
                    :maxLength="18"
                    placeholder="请输入薪资调整"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def5'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def5 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def6"
              title="餐饮补助"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def6'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def6"
                    :maxLength="18"
                    placeholder="请输入餐饮补助"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def6'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def6 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def7"
              title="交通补助"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def7'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def7"
                    :maxLength="18"
                    placeholder="请输入交通补助"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def7'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def7 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def8"
              title="差旅津贴"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def8'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def8"
                    :maxLength="18"
                    placeholder="请输入差旅津贴"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def8'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def8 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def9"
              title="通信补贴"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def9'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def9"
                    :maxLength="18"
                    placeholder="请输入通信补贴"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def9'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def9 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def10"
              title="补其他"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def10'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def10"
                    :maxLength="18"
                    placeholder="请输入补其他"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def10'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def10 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def11"
              title="高温费"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def11'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def11"
                    :maxLength="18"
                    placeholder="请输入高温费"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def11'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def11 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def12"
              title="独生子女费"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def12'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def12"
                    :maxLength="18"
                    placeholder="请输入独生子女费"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def12'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def12 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def13"
              title="十三薪"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def13'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def13"
                    :maxLength="18"
                    placeholder="请输入十三薪"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def13'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def13 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def14"
              title="迟到扣款"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def14'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def14"
                    :maxLength="18"
                    placeholder="请输入迟到扣款"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def14'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def14 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def15"
              title="病事假扣款"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def15'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def15"
                    :maxLength="18"
                    placeholder="请输入病事假扣款"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def15'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def15 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def16"
              title="本次工资应发合计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def16'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def16"
                    :maxLength="18"
                    placeholder="请输入本次工资应发合计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def16'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def16 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def17"
              title="养老保险个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def17'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def17"
                    :maxLength="18"
                    placeholder="请输入养老保险个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def17'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def17 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def18"
              title="医疗保险个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def18'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def18"
                    :maxLength="18"
                    placeholder="请输入医疗保险个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def18'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def18 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def19"
              title="失业保险个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def19'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def19"
                    :maxLength="18"
                    placeholder="请输入失业保险个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def19'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def19 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def20"
              title="大病保险个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def20'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def20"
                    :maxLength="18"
                    placeholder="请输入大病保险个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def20'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def20 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def21"
              title="社保个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def21'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def21"
                    :maxLength="18"
                    placeholder="请输入社保个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def21'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def21 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def22"
              title="公积金个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def22'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def22"
                    :maxLength="18"
                    placeholder="请输入公积金个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def22'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def22 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def23"
              title="公积金免税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def23'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def23"
                    :maxLength="18"
                    placeholder="请输入公积金免税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def23'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def23 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def24"
              title="公积金超额款"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def24'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def24"
                    :maxLength="18"
                    placeholder="请输入公积金超额款"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def24'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def24 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def25"
              title="企业年金个人扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def25'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def25"
                    :maxLength="18"
                    placeholder="请输入企业年金个人扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def25'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def25 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def26"
              title="企业年金免税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def26'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def26"
                    :maxLength="18"
                    placeholder="请输入企业年金免税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def26'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def26 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def27"
              title="企业年金合并计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def27'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def27"
                    :maxLength="18"
                    placeholder="请输入企业年金合并计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def27'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def27 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def28"
              title="工资薪金免税收入"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def28'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def28"
                    :maxLength="18"
                    placeholder="请输入工资薪金免税收入"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def28'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def28 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def29"
              title="工资薪金免税事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def29'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def29"
                    :maxLength="18"
                    placeholder="请输入工资薪金免税事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def29'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def29 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def30"
              title="工资薪金其他扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def30'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def30"
                    :maxLength="18"
                    placeholder="请输入工资薪金其他扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def30'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def30 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def31"
              title="工资薪金其他扣除事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def31'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def31"
                    :maxLength="18"
                    placeholder="请输入工资薪金其他扣除事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def31'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def31 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def32"
              title="工资薪金其他合并计税项(计税不发)"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def32'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def32"
                    :maxLength="18"
                    placeholder="请输入工资薪金其他合并计税项(计税不发)"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def32'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def32 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def33"
              title="当月免税所得额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def33'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def33"
                    :maxLength="18"
                    placeholder="请输入当月免税所得额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def33'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def33 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def34"
              title="本次工资应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def34'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def34"
                    :maxLength="18"
                    placeholder="请输入本次工资应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def34'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def34 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def35"
              title="上次累计工资应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def35'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def35"
                    :maxLength="18"
                    placeholder="请输入上次累计工资应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def35'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def35 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def36"
              title="累计工资应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def36'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def36"
                    :maxLength="18"
                    placeholder="请输入累计工资应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def36'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def36 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def37"
              title="子女教育免税累计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def37'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def37"
                    :maxLength="18"
                    placeholder="请输入子女教育免税累计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def37'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def37 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def38"
              title="继续教育免税累计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def38'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def38"
                    :maxLength="18"
                    placeholder="请输入继续教育免税累计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def38'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def38 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def39"
              title="租房免税累计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def39'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def39"
                    :maxLength="18"
                    placeholder="请输入租房免税累计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def39'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def39 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def40"
              title="住房贷款免税累计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def40'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def40"
                    :maxLength="18"
                    placeholder="请输入住房贷款免税累计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def40'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def40 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def41"
              title="赡养老人免税累计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def41'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def41"
                    :maxLength="18"
                    placeholder="请输入赡养老人免税累计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def41'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def41 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def42"
              title="当前累计工资计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def42'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def42"
                    :maxLength="18"
                    placeholder="请输入当前累计工资计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def42'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def42 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def43"
              title="当前累计工资个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def43'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def43"
                    :maxLength="18"
                    placeholder="请输入当前累计工资个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def43'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def43 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def44"
              title="上次累计工资个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def44'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def44"
                    :maxLength="18"
                    placeholder="请输入上次累计工资个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def44'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def44 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def45"
              title="本次工资个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def45'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def45"
                    :maxLength="18"
                    placeholder="请输入本次工资个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def45'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def45 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def46"
              title="残疾人减免税额（薪资）"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def46'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def46"
                    :maxLength="18"
                    placeholder="请输入残疾人减免税额（薪资）"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def46'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def46 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def47"
              title="本次工资应纳个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def47'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def47"
                    :maxLength="18"
                    placeholder="请输入本次工资应纳个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def47'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def47 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def48"
              title="不计税补贴项（税后减项）"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def48'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def48"
                    :maxLength="18"
                    placeholder="请输入不计税补贴项（税后减项）"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def48'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def48 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def49"
              title="不计税补贴项（税后增加项）"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def49'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def49"
                    :maxLength="18"
                    placeholder="请输入不计税补贴项（税后增加项）"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def49'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def49 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def50"
              title="本次工资项目实发金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def50'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def50"
                    :maxLength="18"
                    placeholder="请输入本次工资项目实发金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def50'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def50 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def51"
              title="本次年终奖金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def51'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def51"
                    :maxLength="18"
                    placeholder="请输入本次年终奖金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def51'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def51 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def52"
              title="年终金免税收入"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def52'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def52"
                    :maxLength="18"
                    placeholder="请输入年终金免税收入"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def52'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def52 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def53"
              title="年终金免税事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def53'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def53"
                    :maxLength="18"
                    placeholder="请输入年终金免税事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def53'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def53 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def54"
              title="年终金其他扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def54'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def54"
                    :maxLength="18"
                    placeholder="请输入年终金其他扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def54'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def54 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def55"
              title="年终金其他扣除事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def55'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def55"
                    :maxLength="18"
                    placeholder="请输入年终金其他扣除事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def55'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def55 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def56"
              title="年终金其他合并计税项(计税不发)"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def56'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def56"
                    :maxLength="18"
                    placeholder="请输入年终金其他合并计税项(计税不发)"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def56'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def56 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def57"
              title="本次年终奖应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def57'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def57"
                    :maxLength="18"
                    placeholder="请输入本次年终奖应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def57'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def57 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def58"
              title="上次年终奖累计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def58'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def58"
                    :maxLength="18"
                    placeholder="请输入上次年终奖累计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def58'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def58 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def59"
              title="累计年终奖计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def59'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def59"
                    :maxLength="18"
                    placeholder="请输入累计年终奖计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def59'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def59 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def60"
              title="累计年终奖个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def60'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def60"
                    :maxLength="18"
                    placeholder="请输入累计年终奖个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def60'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def60 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def61"
              title="上次累计年终奖个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def61'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def61"
                    :maxLength="18"
                    placeholder="请输入上次累计年终奖个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def61'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def61 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def62"
              title="本次年终奖个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def62'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def62"
                    :maxLength="18"
                    placeholder="请输入本次年终奖个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def62'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def62 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def63"
              title="残疾人减免税额（年终奖）"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def63'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def63"
                    :maxLength="18"
                    placeholder="请输入残疾人减免税额（年终奖）"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def63'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def63 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def64"
              title="本次年终奖应纳个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def64'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def64"
                    :maxLength="18"
                    placeholder="请输入本次年终奖应纳个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def64'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def64 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def65"
              title="本次年终奖实发金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def65'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def65"
                    :maxLength="18"
                    placeholder="请输入本次年终奖实发金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def65'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def65 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def66"
              title="本次劳务费金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def66'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def66"
                    :maxLength="18"
                    placeholder="请输入本次劳务费金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def66'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def66 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def67"
              title="劳务费免税收入"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def67'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def67"
                    :maxLength="18"
                    placeholder="请输入劳务费免税收入"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def67'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def67 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def68"
              title="劳务费免税事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def68'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def68"
                    :maxLength="18"
                    placeholder="请输入劳务费免税事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def68'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def68 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def69"
              title="劳务费其他扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def69'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def69"
                    :maxLength="18"
                    placeholder="请输入劳务费其他扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def69'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def69 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def70"
              title="劳务费其他扣除事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def70'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def70"
                    :maxLength="18"
                    placeholder="请输入劳务费其他扣除事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def70'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def70 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def71"
              title="劳务费其他合并计税项(计税不发)"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def71'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def71"
                    :maxLength="18"
                    placeholder="请输入劳务费其他合并计税项(计税不发)"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def71'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def71 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def72"
              title="本次劳务费应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def72'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def72"
                    :maxLength="18"
                    placeholder="请输入本次劳务费应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def72'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def72 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def73"
              title="上次劳务费累计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def73'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def73"
                    :maxLength="18"
                    placeholder="请输入上次劳务费累计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def73'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def73 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def74"
              title="累计劳务费计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def74'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def74"
                    :maxLength="18"
                    placeholder="请输入累计劳务费计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def74'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def74 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def75"
              title="累计劳务费个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def75'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def75"
                    :maxLength="18"
                    placeholder="请输入累计劳务费个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def75'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def75 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def76"
              title="上次累计劳务费个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def76'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def76"
                    :maxLength="18"
                    placeholder="请输入上次累计劳务费个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def76'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def76 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def77"
              title="本次劳务报酬个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def77'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def77"
                    :maxLength="18"
                    placeholder="请输入本次劳务报酬个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def77'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def77 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def78"
              title="残疾人减免税额（劳务费）"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def78'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def78"
                    :maxLength="18"
                    placeholder="请输入残疾人减免税额（劳务费）"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def78'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def78 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def79"
              title="本次劳务费应纳个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def79'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def79"
                    :maxLength="18"
                    placeholder="请输入本次劳务费应纳个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def79'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def79 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def80"
              title="本次劳务报酬实发金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def80'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def80"
                    :maxLength="18"
                    placeholder="请输入本次劳务报酬实发金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def80'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def80 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def81"
              title="实习生劳务费金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def81'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def81"
                    :maxLength="18"
                    placeholder="请输入实习生劳务费金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def81'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def81 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def82"
              title="实习生劳务费免税收入"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def82'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def82"
                    :maxLength="18"
                    placeholder="请输入实习生劳务费免税收入"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def82'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def82 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def83"
              title="实习生劳务费免税事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def83'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def83"
                    :maxLength="18"
                    placeholder="请输入实习生劳务费免税事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def83'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def83 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def84"
              title="实习生劳务费其他扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def84'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def84"
                    :maxLength="18"
                    placeholder="请输入实习生劳务费其他扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def84'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def84 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def85"
              title="实习生劳务费其他扣除事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def85'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def85"
                    :maxLength="18"
                    placeholder="请输入实习生劳务费其他扣除事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def85'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def85 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def86"
              title="实习生劳务费其他合并计税项(计税不发)"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def86'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def86"
                    :maxLength="18"
                    placeholder="请输入实习生劳务费其他合并计税项(计税不发)"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def86'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def86 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def87"
              title="实习生本月免税所得额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def87'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def87"
                    :maxLength="18"
                    placeholder="请输入实习生本月免税所得额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def87'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def87 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def88"
              title="本次其他劳务费应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def88'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def88"
                    :maxLength="18"
                    placeholder="请输入本次其他劳务费应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def88'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def88 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def89"
              title="上次累计其他劳务费计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def89'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def89"
                    :maxLength="18"
                    placeholder="请输入上次累计其他劳务费计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def89'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def89 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def90"
              title="累计其他劳务费计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def90'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def90"
                    :maxLength="18"
                    placeholder="请输入累计其他劳务费计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def90'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def90 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def91"
              title="累计其他劳务费个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def91'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def91"
                    :maxLength="18"
                    placeholder="请输入累计其他劳务费个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def91'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def91 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def92"
              title="上次累计其他劳务费个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def92'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def92"
                    :maxLength="18"
                    placeholder="请输入上次累计其他劳务费个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def92'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def92 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def93"
              title="本次其他劳务费个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def93'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def93"
                    :maxLength="18"
                    placeholder="请输入本次其他劳务费个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def93'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def93 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def94"
              title="残疾人减免税额(其他劳务费)"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def94'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def94"
                    :maxLength="18"
                    placeholder="请输入残疾人减免税额(其他劳务费)"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def94'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def94 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def95"
              title="本次其他劳务费应纳个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def95'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def95"
                    :maxLength="18"
                    placeholder="请输入本次其他劳务费应纳个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def95'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def95 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def96"
              title="本次其它劳务费实发金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def96'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def96"
                    :maxLength="18"
                    placeholder="请输入本次其它劳务费实发金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def96'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def96 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def97"
              title="离职补偿金金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def97'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def97"
                    :maxLength="18"
                    placeholder="请输入离职补偿金金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def97'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def97 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def98"
              title="离职补偿金免税收入"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.def98'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.def98"
                    :maxLength="18"
                    placeholder="请输入离职补偿金免税收入"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def98'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def98 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def99"
              title="离职补偿金免税事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def99'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def99"
                    :maxLength="18"
                    placeholder="请输入离职补偿金免税事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def99'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def99 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def100"
              title="离职补偿金其他扣除"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def100'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def100"
                    :maxLength="18"
                    placeholder="请输入离职补偿金其他扣除"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def100'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def100 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def101"
              title="离职补偿金其他扣除事项"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def101'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def101"
                    :maxLength="18"
                    placeholder="请输入离职补偿金其他扣除事项"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def101'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def101 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def102"
              title="离职补偿金其他合并计税项(计税不发)"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def102'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def102"
                    :maxLength="18"
                    placeholder="请输入离职补偿金其他合并计税项(计税不发)"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def102'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def102 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def103"
              title="离职补偿金应计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def103'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def103"
                    :maxLength="18"
                    placeholder="请输入离职补偿金应计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def103'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def103 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def104"
              title="离职补偿金免税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def104'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def104"
                    :maxLength="18"
                    placeholder="请输入离职补偿金免税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def104'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def104 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def105"
              title="离职补偿金计税金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def105'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def105"
                    :maxLength="18"
                    placeholder="请输入离职补偿金计税金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def105'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def105 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def106"
              title="离职补偿金个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def106'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def106"
                    :maxLength="18"
                    placeholder="请输入离职补偿金个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def106'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def106 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def107"
              title="残疾人减免税额（离职）"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def107'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def107"
                    :maxLength="18"
                    placeholder="请输入残疾人减免税额（离职）"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def107'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def107 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def108"
              title="离职补偿金应纳个税"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def108'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def108"
                    :maxLength="18"
                    placeholder="请输入离职补偿金应纳个税"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def108'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def108 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def109"
              title="离职补偿金实发金额"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def109'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def109"
                    :maxLength="18"
                    placeholder="请输入离职补偿金实发金额"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def109'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def109 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def110"
              title="本次应发合计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def110'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def110"
                    :maxLength="18"
                    placeholder="请输入本次应发合计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def110'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def110 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def111"
              title="本次实发合计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def111'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def111"
                    :maxLength="18"
                    placeholder="请输入本次实发合计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def111'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def111 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="def112"
              title="本次扣税合计"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item
                  :prop="'table213Data.' + rowIndex + '.def112'"
                  label-width="0px"
                  class="dc-el-form-item_SingleInput"
                >
                  <el-input
                    v-model="row.def112"
                    :maxLength="18"
                    placeholder="请输入本次扣税合计"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.def112'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.def112 }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              field="note"
              title="备注"
              tree-node
              resizable
              align="left"
              header-align="center"
              show-overflow
              min-width="150"
              :edit-render="{ autofocus: '.el-input__inner' }"
              class="dc-ux-table-column_SingleInput"
            >
              <template v-slot:header="{ column }">
                {{ column.title }}
              </template>
              <template v-slot:edit="{ row, rowIndex }">
                <el-form-item :prop="'table213Data.' + rowIndex + '.note'" label-width="0px"
                              class="dc-el-form-item_SingleInput">
                  <el-input
                    v-model="row.note"
                    :maxLength="4000"
                    placeholder="请输入备注"
                    clearable
                    class="dc-el-input_SingleInput"
                  ></el-input>
                </el-form-item>
              </template>
              <template v-slot="{ row, rowIndex }">
                <span class="my-input-sc">
                  <el-form-item
                    :prop="'table213Data.' + rowIndex + '.note'"
                    label-width="0px"
                    class="dc-el-form-item_SingleInput"
                  >
                    {{ row.note }}
                  </el-form-item>
                </span>
              </template>
            </ux-table-column>
            <ux-table-column
              title="操作"
              tree-node
              resizable
              width="55px"
              align="left"
              header-align="center"
              class="dc-ux-table-column_ElTableOptColumn"
            >
              <template v-slot:header="scope">
                <span>操作</span>
              </template>
              <template slot-scope="scope">
                <OperationIcon
                  v-on:click="onDelete(scope)"
                  type="danger"
                  content="删除"
                  placement="bottom"
                  icon-name="el-icon-delete-solid"
                  class="dc-OperationIcon_IconButton"
                ></OperationIcon>
              </template>
            </ux-table-column>
          </ux-grid>
        </el-row>
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
      </el-form>
    </el-row>
    <el-row>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-s-unfold" v-on:click="getAllBilling" class="dc-el-button_Button" v-show="action != 'view'">
          获取全部账单数据
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-s-fold" v-on:click="getAllSpecialAddDeduct" class="dc-el-button_Button" v-show="action != 'view'">
          获取全部专项附加
        </el-button>
      </el-tooltip>
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-edit" v-on:click="allSalaryCalculate" class="dc-el-button_Button" v-show="action != 'view'">全部工资计算</el-button>
      </el-tooltip>
      <!--        <el-tooltip disabled class="dc-el-tooltip_Button">-->
      <!--          <el-button type="primary" icon="el-icon-circle-close" v-on:click="doDelete" class="dc-el-button_Button">-->
      <!--            删除-->
      <!--          </el-button>-->
      <!--        </el-tooltip>-->
      <el-tooltip disabled class="dc-el-tooltip_Button">
        <el-button type="primary" icon="el-icon-circle-check" v-on:click="doAllSave" class="dc-el-button_Button" v-show="action != 'view'">
          全部保存
        </el-button>
      </el-tooltip>
    </el-row>
  </el-dialog>
</template>
<script>
  import {validatenull} from '@/utils/validate'
  /** 根据用户界面配置import组件 结束 */

  /** 根据用户界面配置import组件 结束 */
  import {getSalaryBatchById,
    getBillingFun,
    salaryCalculateFun,
    getSpecialAddDeductFun,
    listSalaryDataDetailsInfoPage,
    allSalaryCalculateFun,
    getAllSpecialAddDeductFun,
    getAllBillingFun}
    from '@/api/salaryBatch/salaryBatch'
  import {bulkUpdateSalaryData, bulkUpdateAllSalaryData, deleteSalaryData} from '@/api/salaryData/salaryData'
  import BaseUI from '@/views/components/baseUI'
  import OperationIcon from '@/components/OperationIcon'

  export default {
    extends: BaseUI,
    name: 'salaryBatch-form',
    components: {
      /** 根据用户界面配置组件 开始 */

      /** 根据用户界面配置组件 结束 */

      OperationIcon
    },
    data() {
      return {
        /** 根据用户界面配置生成data数据 开始 */
        editFormData: this.initEditData(),
        // 对话框属性变量
        dialogProps: {
          visible: false,
          title: '薪资明细'
        },
        dialogTitle: '薪资明细',
        editForm211Data: {
          table213Data: []
        },
        queryFormData: {
          employeeName: '', // 员工姓名
          idCardNo: '' // 身份证号
        },
        search: {
          // 查询条件   业务表设置的筛选条件
          params: [],
          offset: 0,
          limit: 10,
          columnName: '', // 排序字段名
          order: '' // 排序
        },
        searchList: [],
        dataId: '',
        // 分页属性
        tableDataPage: 1,
        tableDataTotal: 0,
        importFormData: this.initImportData(),
        // 选项变量
        table213CurrentRow: {}, // 当前行
        selectData: [],
        /** 根据用户界面配置生成data数据 结束 */

        // 窗口操作类型 view/edit/add
        action: ''
      }
    },
    props: {
      // 权限
      permission: {
        type: Object
      }
    },
    computed: {},
    methods: {
      initImportData(This) {
        return {
          name: '', // 离职类型
          remarks: '', // 备注信息
          hrmLaidValueList: []
        }
      },
      handleSelectionChange(val) { this.selectData = val },
      getBilling(){
        this.setLoad()
        if (this.selectData.length === 0){
          this.$message.warning('请先选择一条数据！')
          this.resetLoad()
          return
        }
        getBillingFun(this.selectData)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '获取成功！'
              })
              this.$emit('save-finished')
            } else if (responseData.code == 400) {
              this.showMessage({
                type: 'error',
                msg: responseData.msg
              })
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      getAllBilling(){
        this.setLoad()
        getAllBillingFun(this.dataId)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '获取成功！'
              })
              this.$emit('save-finished')
            } else if (responseData.code == 400) {
              this.showMessage({
                type: 'error',
                msg: responseData.msg
                // msg: '社保账单数据不止一条，请删除多余数据！'
              })
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      getSpecialAddDeduct(){
        this.setLoad()
        if (this.selectData.length === 0){
          this.$message.warning('请先选择一条数据！')
          this.resetLoad()
          return
        }
        getSpecialAddDeductFun(this.selectData)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '获取成功！'
              })
              this.$emit('save-finished')
            } else if (responseData.code == 400) {
              this.showMessage({
                type: 'error',
                msg: '专项附加获取错误！'
              })
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      getAllSpecialAddDeduct(){
        this.setLoad()
        getAllSpecialAddDeductFun(this.dataId)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '获取成功！'
              })
              this.$emit('save-finished')
            } else if (responseData.code == 400) {
              this.showMessage({
                type: 'error',
                msg: '专项附加获取错误！'
              })
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      salaryCalculate(){
        this.setLoad()
        if (this.selectData.length === 0){
          this.$message.warning('请先选择一条数据！')
          this.resetLoad()
          return
        }
        salaryCalculateFun(this.selectData)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '计算完成！'
              })
              this.$emit('save-finished')
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      allSalaryCalculate(){
        this.setLoad()
        allSalaryCalculateFun(this.dataId)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '计算完成！'
              })
              this.$emit('save-finished')
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      onSubmit() {
        this.$refs['queryForm'].validate((valid) => {
          if (valid) {
            this.salaryCalculate()
          } else {
            return false
          }
        })
      },
      doSave() {
        this.setLoad()
        if (this.selectData.length === 0){
          this.$message.warning('请先选择一条数据！')
          this.resetLoad()
          return
        }
        bulkUpdateSalaryData(this.selectData)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '保存成功'
              })
              this.$emit('save-finished')
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      doAllSave() {
        this.setLoad()
        bulkUpdateAllSalaryData(this.editForm211Data.table213Data)
          .then((responseData) => {
            if (responseData.code == 100) {
              // this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '保存成功'
              })
              this.$emit('save-finished')
            } else {
              this.showMessage(responseData)
            }
            this.getFormList()
            this.resetLoad()
          })
          .catch((error) => {
            this.outputError(error)
          })
      },
      onDelete(scope) {
        this.$confirm('确定删除吗？', '确认', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.setLoad()
          deleteSalaryData(scope.row).then(responseData => {
            if (responseData.code == 100) {
              this.dialogProps.visible = false
              this.showMessage({
                type: 'success',
                msg: '删除成功'
              })
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
          }).catch(error => {
            this.outputError(error)
          })
        }).catch((error) => {
          this.outputError(error)
        })
      },
      switchEdit() {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.initOptions(this.editFormData)
      },

      getSalaryFormById(id) {
        this.dataId = id
        this.setLoad()
        return new Promise((resolve) => {
          getSalaryBatchById(id)
            .then((responseData) => {
              let form = {}
              if (responseData.code == 100) {
                this.tableDataTotal = responseData.data.total
                form.table213Data = responseData.data.rows
              } else {
                this.showMessage(responseData)
              }
              this.resetLoad()
              resolve(form)
            })
            .catch((error) => {
              this.outputError(error)
            })
        })
      },
      onSizeChange(val) {
        this.tableDataPage = 1
        this.search.limit = val
        this.search.offset = (this.tableDataPage - 1) * val
        this.getFormList()
      },
      onCurrentChange(val) {
        this.search.offset = (val - 1) * this.search.limit
        this.tableDataPage = val
        this.getFormList()
      },
      getFormList() {
        this.setLoad()
        return new Promise((resolve) => {
          this.search.params = []
          this.search.params.push(
            {
              "columnName": "employeeName",
              "queryType": "like",
              "value": !validatenull(this.queryFormData.employeeName) ? this.queryFormData.employeeName : null
            },
            {
              "columnName": "idCardNo",
              "queryType": "like",
              "value": !validatenull(this.queryFormData.idCardNo) ? this.queryFormData.idCardNo : null
            },
            {
              "columnName": "saBatchId",
              "queryType": "=",
              "value": !validatenull(this.dataId) ? this.dataId : null
            },
          )
          listSalaryDataDetailsInfoPage(this.search)
            .then((responseData) => {
              let form = {}
              if (responseData.code == 100) {
                this.editForm211Data.table213Data = responseData.data.rows
              } else {
                this.showMessage(responseData)
              }
              this.selectData = []
              this.resetLoad()
              // resolve(form)
            })
            .catch((error) => {
              this.outputError(error)
            })
        })
      },
      //重置
      refresh() {
        this.queryFormData = {
          employeeName: '',
          idCardNo: '',
        }
        this.onSearch()
      },
      onSearch() {
        if (this.isQueryConditionPanel) {
          this.search.offset = 0
          this.tableDataPage = 1
          this.getFormList()
        } else {
          this.$refs['queryForm'].validate((valid) => {
            if (valid) {
              this.search.offset = 0
              this.tableDataPage = 1
              this.getFormList()
            } else {
              return false
            }
          })
        }
      },
      onDialogClose() {
        this.dialogProps.visible = false
      },
      onDialogOpen() {
        this.$nextTick(() => {
          this.$refs['queryForm'].clearValidate()
        })
      },

      initOptions(This) {
        // 初始化自定义类型选择框选项变量
      },
      initEditData(This) {
        return {
          companyId: '', // 分公司ID
          id: '', // 主键
          saBatchCode: '', // 薪资批次号
          customerName: '', // 客户名称
          customerCode: '', // 客户编码
          customerId: '', // 客户id
          saClassId: '', // 薪资类别ID
          payYearMonth: '', // 应发年月
          isVerify: '0', // 是否数据确认　1：是　0：否
          verifyTime: '', // 确认时间systime(审核通过时间)
          isPay: '', // 是否支付
          payTime: '', // 支付时间 systime（财务确认支付完成时间）
          payAuditId: '', // 支付审核流程ID
          auditUser: '', // 审核人（数据确认人）
          payName: '', // 发放名称
          billYearMonth: '', // 账单年月
          taxMonth: '', // 计税年月
          isUniteTax: '1', // 是否合并计税 1.是 0. 否
          remarks: '', // 备注
          submitUserId: '', // 提交审核人/客服（同收费单导入）
          submitTime: '', // 提交审核时间
          applyState: '0', // 审核申请状态：0：未申请 1：已申请
          auditState: '0', // 审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成
          mustDay: null, // 应到账日
          rejectCause: '', // 驳回备注
          ebankTime: '', // 生成网银时间systime
          totalDecimal: '', // 生成人数
          totalAmount: null, // 总金额
          failDecimal: null, // 生成失败金额
          payrollTypeBd: '', // 工资单类型：0：不需要 1：保密工资单2：Email 3:保密+Email 4：信封5：保密+信封
          payrollStateBd: '0', // 工资单情况：1:已打印 0：未打印
          isDel: '0', // 是否删除 0:正常　1:删除
          createUserId: '', // 创建人
          createDate: '', // 创建日期
          lastUpdateUserId: '', // 最后修改人
          lastUpdateDate: '', // 最后修改日期
          applyPayDate: '', // 申请发放日期
          auditTime: '', // AUDIT_TIME
          genMoneyCode: '', // 到款编号
          paySalaryType: '', // 发放工资类型：0:有到款编号，1无到款编号
          notCodeReason: '', // 无到款编号原因：1:垫付申请2:固定垫付3:客户打款凭证4:北京打款预收款5:预收款6:其他
          claimUserId: '', // 工资到款认领人id
          claimDate: '', // 认领时间
          realPayDay: '', // 实际发放日
          payState: '0', // 发放状态：0：待发放 1：已发放(财务确认支付完成)
          applyPayTime: '', // 申请发放时间systime
          applyPayUserId: '', // 申请工资发放人
          isCsVerify: '0', // 是否客服数据确认　1：是　0：否(暂不用到)
          applyPayState: '0', // 工资发放申请状态 0：未申请 1：已申请
          oldId: '', // key
          batchCodeQg: '', // 全国委托户导入批次号
          printDate: '', // 工资单打印日期
          rejectReason: '', // 驳回原因
          rejectNumber: null // 驳回人数
        }
      }
    },
    watch: {},
    mounted: function () {
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.importFormData = this.initImportData()
        this.initOptions(this.importFormData)
        this.tabName = 'upForm'
        this.dialogProps.visible = true
      })
      this.$nextTick(() => {
        this.$on('openViewDialog', async function (id) {
          this.action = 'view'
          this.dialogProps.title = `查看${this.dialogTitle}`
          this.editForm211Data = {
            ...this.initEditData(),
            ...(await this.getSalaryFormById(id))
          }
          this.initOptions(this.editForm211Data)
          this.dialogProps.visible = true
        })

        this.$on('openEditDialog', async function (id) {

          this.action = 'edit'
          this.dialogProps.title = `修改${this.dialogTitle}`
          this.editForm211Data = {
            ...this.initEditData(),
            ...(await this.getSalaryFormById(id))
          }
          this.initOptions(this.editForm211Data)
          this.dialogProps.visible = true
        })
        this.$on('openAddDialog', function () {
          this.action = 'add'
          this.dialogProps.title = `添加${this.dialogTitle}`
          this.editFormData = this.initEditData()
          this.initOptions(this.editFormData)
          this.dialogProps.visible = true
        })
        this.$on('openCopyDialog', async function (id) {
          this.action = 'add'
          this.dialogProps.title = `添加${this.dialogTitle}`
          this.editFormData = {
            ...this.initEditData(),
            ...(await this.getFormById(id))
          }
          this.initOptions(this.editFormData)
          this.editFormData.id = null //把id设置为空，添加一个新的

          this.dialogProps.visible = true
        })
      })
    }
  }
</script>
