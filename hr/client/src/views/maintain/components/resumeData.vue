<template>
  <div class="resumeData">
    <el-divider class="dc-el-divider_ElDivider">工作经历</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addWork" v-if="!resumeDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        v-if="!resumeDisabled"
        @click="saveTable(workForm, 'workForm')"
        >保存</el-button
      >
    </div>

    <el-form
      ref="workForm"
      :model="workForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="workForm.dataList"
        ref="tsmWorkExperienceTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="workUnit"
          title="公司名称"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.workUnit'"
              :rules="[
                {
                  required: true,
                  message: '公司名称不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.workUnit"
                :maxLength="100"
                min-width="120px"
                placeholder="请输入公司名称"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.workUnit }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="position"
          title="职位"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.position'"
              :rules="[
                { required: true, message: '职位不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.position"
                :maxLength="50"
                placeholder="请输入职位"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.position }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="startTime"
          title="工作开始时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.startTime'"
              :rules="[
                {
                  required: true,
                  message: '工作开始时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.startTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.startTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="endTime"
          title="工作结束时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.endTime'"
              :rules="[
                {
                  required: true,
                  message: '工作结束时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.endTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.endTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="resignReason"
          title="离职原因"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.resignReason'"
              class="dc-el-form-item_SingleInput"
              :rules="[
                {
                  required: true,
                  message: '离职原因不能为空',
                  trigger: 'blur'
                }
              ]"
            >
              <el-input
                v-model="row.resignReason"
                :maxLength="100"
                placeholder="请输入离职原因"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.resignReason }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="referenceName"
          title="证明人"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.referenceName'"
              :rules="[
                {
                  required: true,
                  message: '证明人不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.referenceName"
                :maxLength="50"
                placeholder="请输入证明人"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.referenceName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="referencePhone"
          title="证明人电话"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              label-width="0px"
              :prop="'dataList.' + rowIndex + '.referencePhone'"
              :rules="[
                {
                  required: true,
                  message: '证明人电话不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.referencePhone"
                :maxLength="20"
                placeholder="请输入证明人电话"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.referencePhone }}
          </template>
        </ux-table-column>

        <ux-table-column
          title="操作"
          tree-node
          resizable
          width="80px"
          v-if="!resumeDisabled"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="onDeleteChild(workForm.dataList, scope, 'workForm')"
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column> </ux-grid
    ></el-form>
    <el-divider class="dc-el-divider_ElDivider">项目经历</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addProject" v-if="!resumeDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        v-if="!resumeDisabled"
        @click="saveTable(projectForm, 'projectForm')"
        >保存</el-button
      >
    </div>

    <el-form
      ref="projectForm"
      :model="projectForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="projectForm.dataList"
        ref="tsmFamilyMemberInfoTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="projectName"
          title="项目名称"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.projectName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '项目名称不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.projectName"
                :maxLength="255"
                placeholder="请输入项目名称"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.projectName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="position"
          title="担任职位"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.position'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '担任职位不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.position"
                :maxLength="255"
                placeholder="请输入担任职位"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.position }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="startTime"
          title="项目开始时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.startTime'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '项目开始时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.startTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.startTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="endTime"
          title="项目结束时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.endTime'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '项目结束时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.endTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.endTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="description"
          title="项目描述"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.description'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '项目描述不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.description"
                :maxLength="255"
                placeholder="请输入项目描述"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.description }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          v-if="!resumeDisabled"
          resizable
          width="80px"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="
                onDeleteChild(projectForm.dataList, scope, 'projectForm')
              "
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>

    <el-divider class="dc-el-divider_ElDivider">培训经历</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addTrain" v-if="!resumeDisabled"
        >新增</el-button
      >
      <el-button
        v-if="!resumeDisabled"
        icon="el-icon-folder-checked"
        @click="saveTable(trainForm, 'trainForm')"
        >保存</el-button
      >
    </div>
    <el-form
      ref="trainForm"
      :model="trainForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="trainForm.dataList"
        ref="tsmEmergencyContactTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="course"
          title="培训课程"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.course'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '培训课程不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.course"
                :maxLength="255"
                placeholder="请输入培训课程"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.course }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="startTime"
          title="培训开始时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.startTime'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '培训开始时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.startTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.startTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="endTime"
          title="培训结束时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.endTime'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '培训结束时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.endTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.endTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="duration"
          title="培训时长"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.duration'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '培训时长不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.duration"
                :maxLength="255"
                placeholder="请输入培训时长"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.duration }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="institutionName"
          title="培训机构名称"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.institutionName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '培训机构名称不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.institutionName"
                :maxLength="255"
                placeholder="请输入培训机构名称"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.institutionName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="location"
          title="培训地点"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.location'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '培训地点不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.location"
                :maxLength="255"
                placeholder="请输入培训地点"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.location }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          resizable
          width="80px"
          min-width="80px"
          v-if="!resumeDisabled"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="onDeleteChild(trainForm.dataList, scope, 'trainForm')"
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>
    <el-divider class="dc-el-divider_ElDivider">资格证书</el-divider>
    <div style="margin-bottom:10px">
      <el-button
        icon="el-icon-plus"
        @click="addCertificate"
        v-if="!resumeDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        v-if="!resumeDisabled"
        @click="saveTable(qualificaForm, 'qualificaForm')"
        >保存</el-button
      >
    </div>
    <el-form
      ref="qualificaForm"
      :model="qualificaForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="qualificaForm.dataList"
        ref="tsmQualificationTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="certificateName"
          title="证书名称"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.certificateName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '证书名称不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.certificateName"
                :maxLength="128"
                placeholder="请输入证书名称"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.certificateName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="certificateNum"
          title="证书编号"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.certificateNum'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '证书编号不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.certificateNum"
                :maxLength="128"
                placeholder="请输入证书编号"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.certificateNum }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="certificateDate"
          title="发证时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.certificateDate'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '发证时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.certificateDate"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.certificateDate }}
          </template>
        </ux-table-column>

        <ux-table-column
          title="操作"
          tree-node
          resizable
          v-if="!resumeDisabled"
          width="80px"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="
                onDeleteChild(qualificaForm.dataList, scope, 'qualificaForm')
              "
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>

    <el-divider class="dc-el-divider_ElDivider">教育经历</el-divider>
    <div style="margin-bottom:10px">
      <el-button
        icon="el-icon-plus"
        @click="addEducation"
        v-if="!resumeDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        v-if="!resumeDisabled"
        @click="saveTable(educationForm, 'educationForm')"
        >保存</el-button
      >
    </div>
    <el-form
      ref="educationForm"
      :model="educationForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="educationForm.dataList"
        ref="tsmEducationalRecordsTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="graduatedSchool"
          title="毕业学校"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.graduatedSchool'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '毕业学校不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.graduatedSchool"
                :maxLength="128"
                placeholder="请输入毕业学校"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.graduatedSchool }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="major"
          title="专业"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.major'"
              label-width="0px"
              :rules="[
                { required: true, message: '专业不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.major"
                :maxLength="128"
                placeholder="请输入专业"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.major }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="highestEducation"
          title="最高学历"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.highestEducation'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '最高学历不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="row.highestEducation"
                :style="{ width: '100%' }"
                placeholder="请选择"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in educationBackgroundOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.highestEducation.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="educationMethod"
          title="教育方式"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.educationMethod'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '教育方式不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="row.educationMethod"
                :style="{ width: '100%' }"
                placeholder="请选择"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in educationMethodOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.educationMethod.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="admissionTime"
          title="入学时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.admissionTime'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '入学时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.admissionTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.admissionTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="graduationTime"
          title="毕业时间"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.graduationTime'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '毕业时间不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.graduationTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.graduationTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="diplomaNumber"
          title="毕业证编号"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.diplomaNumber'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '毕业证编号不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.diplomaNumber"
                :maxLength="128"
                placeholder="请输入毕业证编号"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.diplomaNumber }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="degreeCertificateNumber"
          title="学位证编号"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.degreeCertificateNumber'"
              label-width="0px"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.degreeCertificateNumber"
                :maxLength="128"
                placeholder="请输入学位证编号"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.degreeCertificateNumber }}
          </template>
        </ux-table-column>

        <ux-table-column
          title="操作"
          tree-node
          resizable
          v-if="!resumeDisabled"
          width="80px"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="
                onDeleteChild(educationForm.dataList, scope, 'educationForm')
              "
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>

    <el-divider class="dc-el-divider_ElDivider">个人技能</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addSkill" v-if="!resumeDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        v-if="!resumeDisabled"
        @click="saveTable(skillForm, 'skillForm')"
        >保存</el-button
      >
    </div>
    <el-form
      ref="skillForm"
      :model="skillForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="skillForm.dataList"
        ref="tsmPersonalSkillsTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="skillName"
          title="技能名称"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.skillName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '技能名称不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.skillName"
                :maxLength="255"
                placeholder="请输入技能名称"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.skillName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="skillLevel"
          title="熟练程度"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.skillLevel'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '熟练程度不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="row.skillLevel"
                :style="{ width: '100%' }"
                placeholder="请选择"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in skillOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.skillLevel.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="remarks"
          title="备注"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_MutilpleInput"
        >
          <template v-slot:header="{ column }">
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.remarks'"
              label-width="0px"
              class="dc-el-form-item_MutilpleInput"
            >
              <el-input
                v-model="row.remarks"
                type="textarea"
                placeholder="请输入备注"
                rows="2"
                :maxLength="255"
                class="dc-el-input_MutilpleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.remarks }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          resizable
          width="80px"
          v-if="!resumeDisabled"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="onDeleteChild(skillForm.dataList, scope, 'skillForm')"
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>
  </div>
</template>
<script>
import OperationIcon from "@/components/OperationIcon";
import {
  getWorkExperience,
  getProjectExperience,
  getPersonalSkills,
  getEducationalRecords,
  getTrainingExperience,
  getQualification,
  saveWorkExperience,
  saveProjectExperience,
  saveTrainingExperience,
  saveQualification,
  saveEducationalRecords,
  savePersonalSkills,
  deleteProjectExperience,
  deleteTrainingExperience
} from "@/api/induction/EmployeeInfo.js";
import { deleteTsmWorkExperience } from "@/api/induction/tsmWorkExperience.js";
import { getDictTypeById } from "@/api/sys/dictType";
import { deleteTsmPersonalSkills } from "@/api/induction/tsmPersonalSkills.js";
import { deleteTsmQualification } from "@/api/induction/tsmQualification.js";
import { deleteTsmEducationalRecords } from "@/api/induction/tsmEducationalRecords.js";
import baseUI from "@/views/components/baseUI";
export default {
  props: ["rowData", "resumeDisabled"],
  extends: baseUI,
  components: {
    OperationIcon
  },
  data() {
    return {
      //工作经历表格
      workForm: { dataList: [] },
      //教育经历表格
      educationForm: { dataList: [] },
      //资格证书表格
      qualificaForm: { dataList: [] },
      //个人技能表格
      skillForm: { dataList: [] },
      //项目经历表格
      projectForm: { dataList: [] },
      //培训经历表格
      trainForm: { dataList: [] },
      //最高学历
      educationBackgroundOptions: [],
      //教育方式
      educationMethodOptions: [],
      //技能熟练程度
      skillOptions: [],
      inductionId: "",
      shipOptions: []
    };
  },
  mounted() {
    this.initOptions();
  },
  methods: {
    //获取工作经历
    getWorkForm() {
      getWorkExperience(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.workForm.dataList = res.data.filter(
            item =>
              item.position ||
              item.referenceName ||
              item.referencePhone ||
              item.resignReason ||
              item.startTime ||
              item.endTime
          );
        }
      });
    },
    //获取项目经历
    getProjectForm() {
      getProjectExperience(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.projectForm.dataList = res.data;
        }
      });
    },
    //获取培训经历
    getTrainForm() {
      getTrainingExperience(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.trainForm.dataList = res.data;
        }
      });
    },
    //获取证书
    getQuaForm() {
      getQualification(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.qualificaForm.dataList = res.data.filter(
            item =>
              item.certificateName ||
              item.certificateNum ||
              item.certificateDate
          );
        }
      });
    },
    //获取教育经历
    getEducationForm() {
      getEducationalRecords(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.educationForm.dataList = res.data;
        }
      });
    },
    //获取个人技能
    getSkillForm() {
      getPersonalSkills(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.skillForm.dataList = res.data.filter(
            item => item.skillName || item.remarks || item.skillLevel.name
          );
        }
      });
    },
    //新增工作经历
    addWork() {
      this.workForm.dataList.push({
        workUnit: "",
        position: "",
        startTime: "",
        endTime: "",
        resignReason: "",
        referencePhone: "",
        referenceName: ""
      });
    },
    //新增教育经历
    addEducation() {
      this.educationForm.dataList.push({
        graduatedSchool: "",
        major: "",
        highestEducation: {
          id: "",
          name: ""
        },
        educationMethod: { id: "", name: "" },
        admissionTime: "",
        graduationTime: "",
        diplomaNumber: "",
        degreeCertificateNumber: ""
      });
    },
    //新增资格证书
    addCertificate() {
      this.qualificaForm.dataList.push({
        certificateName: "",
        certificateNum: "",
        certificateDate: ""
      });
    },
    //新增个人技能
    addSkill() {
      this.skillForm.dataList.push({
        remarks: "",
        skillName: "",
        skillLevel: ""
      });
    },
    //新增培训经历
    addTrain() {
      this.trainForm.dataList.push({
        course: "", // 培训课程
        startTime: "", // 培训开始时间
        endTime: "", // 培训结束时间
        duration: "", // 培训时长（小时）
        institutionName: "", // 培训机构名称
        location: "" // 培训地点
      });
    },
    //新增项目经历
    addProject() {
      this.projectForm.dataList.push({
        projectName: "", // 项目名称
        position: "", // 担任职位
        startTime: "", // 项目开始时间
        endTime: "", // 项目结束时间
        description: "" // 项目描述
      });
    },
    //保存表格
    saveTable(formData, formName) {
      if (formData.dataList.length > 0) {
        formData.dataList.map(item => {
          delete item._XID;
          return item;
        });
        let API;
        let isValid = true;
        let timeVliad = true;
        let projectVliad = true;
        if (formName === "workForm") {
          API = saveWorkExperience;
          formData.dataList.forEach(item => {
            if (
              !item.workUnit ||
              !item.position ||
              !item.startTime ||
              !item.endTime ||
              !item.resignReason ||
              !item.referencePhone ||
              !item.referenceName
            ) {
              isValid = false;
            }
          });
        }
        if (formName === "educationForm") {
          API = saveEducationalRecords;
          formData.dataList.forEach(item => {
            if (
              !item.graduatedSchool ||
              !item.major ||
              !item.highestEducation.id ||
              !item.educationMethod.id ||
              !item.admissionTime ||
              !item.graduationTime
            ) {
              isValid = false;
            }
            if (new Date(item.admissionTime) > new Date(item.graduationTime)) {
              timeVliad = false;
            }
          });
        }
        if (formName === "qualificaForm") {
          API = saveQualification;
          formData.dataList.forEach(item => {
            if (
              !item.certificateName ||
              !item.certificateNum ||
              !item.certificateDate
            ) {
              isValid = false;
            }
          });
        }

        if (formName === "skillForm") {
          API = savePersonalSkills;
          formData.dataList.forEach(item => {
            if (!item.skillName || !item.skillLevel) {
              isValid = false;
            }
          });
        }

        if (formName === "trainForm") {
          API = saveTrainingExperience;
          formData.dataList.forEach(item => {
            if (
              !item.course ||
              !item.startTime ||
              !item.endTime ||
              !item.duration ||
              !item.institutionName ||
              !item.location
            ) {
              isValid = false;
            }
          });
        }

        if (formName === "projectForm") {
          API = saveProjectExperience;
          formData.dataList.forEach(item => {
            if (
              !item.projectName ||
              !item.position ||
              !item.startTime ||
              !item.endTime ||
              !item.description
            ) {
              isValid = false;
            }
            if (new Date(item.startTime) > new Date(item.endTime)) {
              projectVliad = false;
            }
          });
        }
        if (!timeVliad) {
          this.showMessage({
            type: "warning",
            msg: "毕业时间要大于入学时间"
          });
        } else if (!projectVliad) {
          this.showMessage({
            type: "warning",
            msg: "项目结束时间要大于项目开始时间"
          });
        } else {
          if (isValid) {
            API(formData.dataList, this.rowData.empolyno).then(res => {
              if (res.code === "100") {
                this.getForm(formName);
                this.showMessage({ msg: "数据保存成功", type: "success" });
              } else {
                this.showMessage({ type: "warning", msg: "网络错误请重试" });
              }
            });
          } else {
            this.showMessage({
              type: "warning",
              msg: "存在未填写的数据,请完善！"
            });
          }
        }
      } else {
        this.showMessage({ type: "warning", msg: "请填写相关数据！" });
      }
    },
    //删除某条数据
    onDeleteChild(tableData, scope, formName) {
      if (scope.row.id) {
        this.$confirm("确定要删除该条数据吗？", "确认", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            let API;
            if (formName === "workForm") {
              API = deleteTsmWorkExperience;
            }
            if (formName === "educationForm") {
              API = deleteTsmEducationalRecords;
            }
            if (formName === "qualificaForm") {
              API = deleteTsmQualification;
            }

            if (formName === "skillForm") {
              API = deleteTsmPersonalSkills;
            }
            if (formName === "trainForm") {
              API = deleteTrainingExperience;
            }
            if (formName === "projectForm") {
              API = deleteProjectExperience;
            }
            API(scope.row).then(res => {
              if (res.code === "100") {
                this.getForm(formName);
                this.showMessage({ msg: "删除数据成功", type: "success" });
              } else {
                this.showMessage({ type: "warning", msg: "网络错误请重试" });
              }
            });
          })
          .catch(() => {});
      } else {
        tableData.splice(scope.rowIndex, 1);
      }
    },

    getHighestEducationOptions() {
      getDictTypeById("1496815670528286753").then(res => {
        this.educationBackgroundOptions = res.data.dictItemList;
      });
    },

    getEducationMethodOptions() {
      getDictTypeById("1496815670528286761").then(res => {
        this.educationMethodOptions = res.data.dictItemList;
      });
    },
    getSkillOptions() {
      getDictTypeById("1496815670528286765").then(res => {
        this.skillOptions = res.data.dictItemList;
      });
    },
    getForm(formName) {
      if (formName === "workForm") {
        this.getWorkForm();
      }
      if (formName === "educationForm") {
        this.getEducationForm();
      }
      if (formName === "qualificaForm") {
        this.getQuaForm;
      }
      if (formName === "skillForm") {
        this.getSkillForm();
      }

      if (formName === "trainForm") {
        this.getTrainForm();
      }
      if (formName === "projectForm") {
        this.getProjectForm();
      }
    },

    initOptions() {
      this.getWorkForm();
      this.getProjectForm();
      this.getTrainForm();
      this.getQuaForm();
      this.getEducationForm();
      this.getSkillForm();
      this.getHighestEducationOptions();
      this.getEducationMethodOptions();
      this.getSkillOptions();
    }
  }
};
</script>
<style lang="scss" scoped>
.resumeData {
  width: 100%;
  height: 500px;
  background: #ffffff;
  margin-left: 10px;
  padding: 10px;
  overflow: auto;
}
</style>