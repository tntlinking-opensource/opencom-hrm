<template>
  <el-row class="dc-container baseComponent" v-loading="loading">
    <el-divider>待入职员工信息</el-divider>
    <el-form
      ref="baseFormData"
      :model="baseFormData"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item
            prop="name"
            label="姓名"
            :rules="[
              { required: true, message: '姓名不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.name"
              :maxLength="50"
              placeholder="请输入姓名"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="gender.id"
            label="性别"
            :rules="[
              { required: true, message: '性别不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.gender"
              :style="{ width: '100%' }"
              placeholder="请选择性别"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in genderOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="phoneNumber"
            label="手机号码"
            class="dc-el-form-item_SingleInput"
            :rules="[
              { required: true, message: '手机号码不能为空', trigger: 'change' }
            ]"
          >
            <el-input
              v-model="baseFormData.phoneNumber"
              :maxLength="20"
              placeholder="请输入手机号码"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="email"
            label="个人邮箱"
            class="dc-el-form-item_SingleInput"
            :rules="[
              { required: true, message: '个人邮箱不能为空', trigger: 'change' }
            ]"
          >
            <el-input
              v-model="baseFormData.email"
              :maxLength="100"
              placeholder="请输入个人邮箱"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="idNumber"
            label="身份证号码"
            :rules="[
              { required: true, message: '身份证号码不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.idNumber"
              :maxLength="18"
              placeholder="请输入身份证号码"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="idCardExpiryDate"
            label="证件到期时间"
            :rules="[
              {
                required: true,
                message: '证件到期时间不能为空',
                trigger: 'change'
              }
            ]"
            class="dc-el-form-item_PickerTimeDate"
          >
            <el-date-picker
              v-model="baseFormData.idCardExpiryDate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="accumulationFundId"
            label="公积金账号"
            :rules="[
              { required: true, message: '公积金账号不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.accumulationFundId"
              :maxLength="50"
              placeholder="请输入公积金账号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="cardId"
            label="银行卡号"
            :rules="[
              { required: true, message: '银行卡号不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.cardId"
              :maxLength="50"
              placeholder="请输入银行卡号"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="accountOwner"
            label="开户人"
            :rules="[
              { required: true, message: '开户人不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.accountOwner"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="bankName"
            label="银行名称"
            :rules="[
              { required: true, message: '银行名称不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.bankName"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="bankAddress"
            label="开户银行"
            :rules="[
              { required: true, message: '开户银行不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.bankAddress"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="bankCity"
            label="开户城市"
            :rules="[
              { required: true, message: '开户城市不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.bankCity"
              placeholder="请输入"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="nationality"
            label="国籍或地区"
            :rules="[
              { required: true, message: '国籍或地区不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.nationality"
              :maxLength="50"
              placeholder="请输入国籍或地区"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="ethnicity.id"
            label="民族"
            :rules="[
              { required: true, message: '民族不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.ethnicity"
              :style="{ width: '100%' }"
              value-key="id"
              placeholder="请选择"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in ethnicityOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="nativePlace"
            label="籍贯"
            :rules="[
              { required: true, message: '籍贯不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_CascaderInput"
          >
            <el-input
              v-model="baseFormData.nativePlace"
              :maxLength="100"
              placeholder="请输入籍贯"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="householdRegisterLocation"
            label="户口所在地"
            :rules="[
              { required: true, message: '户口所在地不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.householdRegisterLocation"
              :maxLength="100"
              placeholder="请输入户口所在地"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="householdRegisterType.id"
            label="户口状态"
            :rules="[
              { required: true, message: '户口状态不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.householdRegisterType"
              :style="{ width: '100%' }"
              placeholder="请选择"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in registerOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="residenceAddress"
            label="居住地址"
            :rules="[
              { required: true, message: '居住地址不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="baseFormData.residenceAddress"
              :maxLength="100"
              placeholder="请输入居住地址"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="maritalStatus.id"
            label="婚姻状态"
            :rules="[
              { required: true, message: '婚姻状态不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.maritalStatus"
              :style="{ width: '100%' }"
              placeholder="请选择"
              filterable
              value-key="id"
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in maritalStatusOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="politicalStatus.id"
            label="政治面貌"
            :rules="[
              { required: true, message: '政治面貌不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.politicalStatus"
              :style="{ width: '100%' }"
              placeholder="请选择政治面貌"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in politicalStatusOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="childbirthStatus.id"
            label="生育状态"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.childbirthStatus"
              :style="{ width: '100%' }"
              placeholder="请选择生育状态"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in childbirthStatusOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="healthStatus.id"
            label="健康状态"
            :rules="[
              { required: true, message: '健康状态不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.healthStatus"
              :style="{ width: '100%' }"
              placeholder="请选择健康状态"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in healthStatusOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="educationBackground.id"
            label="最高学历"
            :rules="[
              { required: true, message: '最高学历不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.educationBackground"
              :style="{ width: '100%' }"
              placeholder="请选择"
              filterable
              value-key="id"
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
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="highestDegree.id"
            label="最高学位"
            :rules="[
              { required: true, message: '最高学位不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="baseFormData.highestDegree"
              :style="{ width: '100%' }"
              placeholder="请选择"
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in academicOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item
            prop="workStartDate"
            label="参加工作时间"
            :rules="[
              {
                required: true,
                message: '参加工作时间不能为空',
                trigger: 'change'
              }
            ]"
            class="dc-el-form-item_PickerMonth"
          >
            <el-date-picker
              v-model="baseFormData.workStartDate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerMonth"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="interviewTime"
            label="面试时间"
            :rules="[
              { required: true, message: '面试时间不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_PickerMonth"
          >
            <el-date-picker
              v-model="baseFormData.interviewTime"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="expectedSalary"
            label="期望薪资"
            :rules="[
              { required: true, message: '期望薪资不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_NumberInput"
          >
            <number-input
              v-model="baseFormData.expectedSalary"
              placeholder="请输入"
              :min="0"
              clearable
              class="dc-number-input_NumberInput"
            ></number-input>
          </el-form-item>
          <el-button icon="el-icon-folder-checked" @click="saveBaseForm"
            >保存</el-button
          >
        </el-col>
      </el-row></el-form
    >

    <el-divider class="dc-el-divider_ElDivider">工作经历</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addWork">新增</el-button>
      <el-button
        icon="el-icon-folder-checked"
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
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          v-if="action != 'view'"
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

    <el-divider class="dc-el-divider_ElDivider">教育经历</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addEducation">新增</el-button>
      <el-button
        icon="el-icon-folder-checked"
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
          width="80px"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          v-if="action != 'view'"
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

    <el-divider class="dc-el-divider_ElDivider">资格证书</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addCertificate">新增</el-button>
      <el-button
        icon="el-icon-folder-checked"
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

    <el-divider class="dc-el-divider_ElDivider">个人技能</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addSkill">新增</el-button>
      <el-button
        icon="el-icon-folder-checked"
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

    <el-divider class="dc-el-divider_ElDivider">家庭情况</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addFamily">新增</el-button>
      <el-button
        icon="el-icon-folder-checked"
        @click="saveTable(familyForm, 'familyForm')"
        >保存</el-button
      >
    </div>

    <el-form
      ref="familyForm"
      :model="familyForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="familyForm.dataList"
        ref="tsmFamilyMemberInfoTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="familyName"
          title="亲属姓名"
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
              :prop="'dataList.' + rowIndex + '.familyName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '亲属姓名不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.familyName"
                :maxLength="255"
                placeholder="请输入亲属姓名"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.familyName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="kinship"
          title="家属关系"
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
              :prop="'dataList.' + rowIndex + '.kinship'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '家属关系不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="row.kinship"
                :style="{ width: '100%' }"
                placeholder="请输入家属关系"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in shipOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.kinship.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="career"
          title="职业"
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
              :prop="'dataList.' + rowIndex + '.career'"
              label-width="0px"
              :rules="[
                { required: true, message: '职业不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.career"
                :maxLength="255"
                placeholder="请输入职业"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.career }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="contactAddress"
          title="联系地址"
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
              :prop="'dataList.' + rowIndex + '.contactAddress'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系地址不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.contactAddress"
                :maxLength="255"
                placeholder="请输入联系地址"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.contactAddress }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="contactType"
          title="联系方式"
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
              :prop="'dataList.' + rowIndex + '.contactType'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系方式不能为空',
                  trigger: 'blur'
                },
                { pattern: phoneNumberPattern, message: '请输入正确的联系方式' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.contactType"
                :maxLength="255"
                placeholder="请输入联系方式"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.contactType }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
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
                onDeleteChild(familyForm.dataList, scope, 'familyForm')
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

    <el-divider class="dc-el-divider_ElDivider">紧急联系人</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addEmergency">新增</el-button>
      <el-button
        icon="el-icon-folder-checked"
        @click="saveTable(emergencyForm, 'emergencyForm')"
        >保存</el-button
      >
    </div>
    <el-form
      ref="emergencyForm"
      :model="emergencyForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="emergencyForm.dataList"
        ref="tsmEmergencyContactTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="name"
          title="联系人姓名"
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
              :prop="'dataList.' + rowIndex + '.name'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系人姓名不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.name"
                :maxLength="255"
                placeholder="请输入联系人姓名"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="relationship"
          title="联系人关系"
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
              :prop="'dataList.' + rowIndex + '.relationship'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系人关系不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="row.relationship"
                :style="{ width: '100%' }"
                placeholder="请输入联系人关系"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in shipOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.relationship.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="phoneNumber"
          title="联系人电话"
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
              :prop="'dataList.' + rowIndex + '.phoneNumber'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系人电话不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.phoneNumber"
                :maxLength="255"
                placeholder="请输入联系人电话"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.phoneNumber }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="address"
          title="联系地址"
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
              :prop="'dataList.' + rowIndex + '.address'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系地址不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.address"
                :maxLength="255"
                placeholder="请输入联系地址"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.address }}
          </template>
        </ux-table-column>

        <ux-table-column
          title="操作"
          tree-node
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
                onDeleteChild(emergencyForm.dataList, scope, 'emergencyForm')
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
  </el-row>
</template>
<script>
  import {getTsmInductionEnhanceBasicById, saveEnhanceBasic} from "@/api/induction/tsmInductionBasic.js";
  import {deleteTsmWorkExperience, saveTsmWorkExperience} from "@/api/induction/tsmWorkExperience.js";
  import {getDictTypeById} from "@/api/sys/dictType";
  import {deleteTsmEmergencyContact, saveTsmEmergencyContact} from "@/api/induction/tsmEmergencyContact.js";
  import {deleteTsmFamilyMemberInfo, saveTsmFamilyMemberInfo} from "@/api/induction/tsmFamilyMemberInfo.js";
  import {deleteTsmPersonalSkills, saveTsmPersonalSkills} from "@/api/induction/tsmPersonalSkills.js";
  import {deleteTsmQualification, saveTsmQualification} from "@/api/induction/tsmQualification.js";
  import {deleteTsmEducationalRecords, saveTsmEducationalRecords} from "@/api/induction/tsmEducationalRecords.js";

  import OperationIcon from "@/components/OperationIcon";
  import baseUI from "@/views/components/baseUI";

  export default {
  extends: baseUI,
  name: "baseComponent",
  components: {
    OperationIcon
  },
  props: ["rowData"],
  data() {
    return {
      baseFormData: {
        name: "", // 姓名
        gender: {
          id: null,
          name: null
        },
        phoneNumber: "", // 手机号码
        email: "", // 个人邮箱
        idNumber: "", // 身份证号码
        idCardExpiryDate: "", // 证件到期时间
        ethnicity: {
          id: null,
          name: null
        },
        nativePlace: null, // 籍贯
        residenceAddress: "",
        householdRegisterType: {
          id: null,
          name: null
        },
        residenceAddress: "", // 居住地址
        maritalStatus: {
          id: null,
          name: null
        },
        healthStatus: {
          id: null,
          name: null
        },
        childbirthStatus: { id: null, name: null },
        educationBackground: { id: null, name: null },
        highestDegree: { id: null, name: null },
        accumulationFundId: "",
        salaryCardId: "",
        cardId: "",
        nationality: "",
        householdRegisterLocation: "",
        workStartDate: "",
        accountOwner: "",
        bankName: "",
        bankAddress: "",
        bankCity: "",
        interviewTime: "",
        expectedSalary: ""
      },
      //工作经历表格
      workForm: { dataList: [] },
      //教育经历表格
      educationForm: { dataList: [] },
      //资格证书表格
      qualificaForm: { dataList: [] },
      //个人技能表格
      skillForm: { dataList: [] },
      //家庭情况表格
      familyForm: { dataList: [] },
      //紧急联系人表格
      emergencyForm: { dataList: [] },

      // 性别选项
      genderOptions: [],
      // 民族选项
      ethnicityOptions: [],
      //婚姻选项
      maritalStatusOptions: [],
      // 政治面貌选项
      politicalStatusOptions: [],
      // 生育状态选项
      childbirthStatusOptions: [],
      // 健康状态选项
      healthStatusOptions: [],
      //最高学历
      educationBackgroundOptions: [],
      //最高学位
      academicOptions: [],
      //户口状态
      registerOptions: [],
      //教育方式
      educationMethodOptions: [],
      //技能熟练程度
      skillOptions: [],
      inductionId: "",
      shipOptions: [],
      phoneNumberPattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    };
  },

  computed: {},
  methods: {
    //获取表格表单数据
    getFormData() {
      getTsmInductionEnhanceBasicById(this.rowData.id).then(res => {
        if (res.code === "100") {
          let obj = JSON.parse(JSON.stringify(res.data));
          if (!obj.householdRegisterType["id"]) {
            delete obj.householdRegisterType;
          }
          if (!obj.healthStatus["id"]) {
            delete obj.healthStatus;
          }
          if (!obj.educationBackground["id"]) {
            delete obj.educationBackground;
          }
          if (!obj.highestDegree["id"]) {
            delete obj.highestDegree;
          }
          this.baseFormData = Object.assign(this.baseFormData, obj);
          this.workForm.dataList = res.data.tsmWorkExperienceList.filter(
            item =>
              item.position ||
              item.referenceName ||
              item.referencePhone ||
              item.resignReason ||
              item.workUnit ||
              item.startTime ||
              item.endTime
          );
          this.educationForm.dataList = res.data.tsmEducationalRecordsList;
          this.qualificaForm.dataList = res.data.tsmQualificationList.filter(
            item =>
              item.certificateName ||
              item.certificateNum ||
              item.certificateDate
          );
          this.skillForm.dataList = res.data.tsmPersonalSkillsList.filter(
            item => item.skillName || item.remarks || item.skillLevel.name
          );
          this.familyForm.dataList = res.data.tsmFamilyMemberInfoList;
          this.emergencyForm.dataList = res.data.tsmEmergencyContactList;
          this.inductionId = res.data.id;
        }
      });
    },
    //保存个人信息
    saveBaseForm() {
      this.$refs["baseFormData"].validate(valid => {
        if (valid) {
          saveEnhanceBasic(this.baseFormData).then(res => {
            if (res.code === "100") {
              this.showMessage({ msg: "数据保存成功", type: "success" });
              this.getFormData();
            } else {
              this.showMessage({ type: "warning", msg: "网络错误请重试！" });
            }
          });
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
        referenceName: "",
        inductionId: this.inductionId
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
        degreeCertificateNumber: "",
        inductionId: this.inductionId
      });
    },
    //新增资格证书
    addCertificate() {
      this.qualificaForm.dataList.push({
        certificateName: "",
        certificateNum: "",
        certificateDate: "",
        inductionId: this.inductionId
      });
    },
    //新增个人技能
    addSkill() {
      this.skillForm.dataList.push({
        remarks: "",
        skillName: "",
        skillLevel: "",
        inductionId: this.inductionId
      });
    },
    //新增家庭成员
    addFamily() {
      this.familyForm.dataList.push({
        familyName: "",
        kinship: "",
        career: "",
        contactAddress: "",
        contactType: "",
        inductionId: this.inductionId
      });
    },
    //新增紧急联系人
    addEmergency() {
      this.emergencyForm.dataList.push({
        name: "",
        relationship: "",
        phoneNumber: "",
        address: "",
        inductionId: this.inductionId
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
        let timeVaild = true;
        let phoneValid = true;
        if (formName === "workForm") {
          API = saveTsmWorkExperience;
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
          API = saveTsmEducationalRecords;
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
              timeVaild = false;
            }
          });
        }
        if (formName === "qualificaForm") {
          API = saveTsmQualification;
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
        if (formName === "familyForm") {
          API = saveTsmFamilyMemberInfo;
          formData.dataList.forEach(item => {
            if (
              !item.familyName ||
              !item.kinship ||
              !item.career ||
              !item.contactAddress ||
              !item.contactType
            ) {
              isValid = false;
            }
            if (!this.phoneNumberPattern.test(item.contactType)) {
              phoneValid = false;
            }
          });
        }
        if (formName === "skillForm") {
          API = saveTsmPersonalSkills;
          formData.dataList.forEach(item => {
            if (!item.skillName || !item.skillLevel) {
              isValid = false;
            }
          });
        }
        if (formName === "emergencyForm") {
          API = saveTsmEmergencyContact;
          formData.dataList.forEach(item => {
            if (
              !item.name ||
              !item.relationship ||
              !item.phoneNumber ||
              !item.address
            ) {
              isValid = false;
            }
          });
        }
        if (!timeVaild) {
          this.showMessage({
            type: "warning",
            msg: "毕业时间要大于入学时间"
          });
        } else if (!phoneValid) {
          this.showMessage({
            type: "warning",
            msg: "请输入正确的联系方式！"
          });
        } else {
          if (isValid) {
            API(formData.dataList).then(res => {
              if (res.code === "100") {
                this.getFormData();
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
            if (formName === "familyForm") {
              API = deleteTsmFamilyMemberInfo;
            }
            if (formName === "skillForm") {
              API = deleteTsmPersonalSkills;
            }
            if (formName === "emergencyForm") {
              API = deleteTsmEmergencyContact;
            }
            API(scope.row).then(res => {
              if (res.code === "100") {
                this.getFormData();
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
    getGenderOptions() {
      getDictTypeById("1495769794452094977").then(res => {
        this.genderOptions = res.data.dictItemList;
      });
    },
    getEthnicityOptions() {
      getDictTypeById("1495769794452094981").then(res => {
        this.ethnicityOptions = res.data.dictItemList;
      });
    },
    getMaritalStatusOptions() {
      getDictTypeById("1495769794452094986").then(res => {
        this.maritalStatusOptions = res.data.dictItemList;
      });
    },
    getChildbirthStatusOptions() {
      getDictTypeById("1495769794452094992").then(res => {
        this.childbirthStatusOptions = res.data.dictItemList;
      });
    },
    getPoliticalStatusOptions() {
      getDictTypeById("1495769794452094996").then(res => {
        this.politicalStatusOptions = res.data.dictItemList;
      });
    },
    getHighestEducationOptions() {
      getDictTypeById("1496815670528286753").then(res => {
        this.educationBackgroundOptions = res.data.dictItemList;
      });
    },
    getAcademicOptionsOptions() {
      getDictTypeById("1510239419013955718").then(res => {
        this.academicOptions = res.data.dictItemList;
      });
    },
    getHealthStatusOptionsOptions() {
      getDictTypeById("1510239419013955733").then(res => {
        this.healthStatusOptions = res.data.dictItemList;
      });
    },
    getRegisterOptionsOptions() {
      getDictTypeById("1510239419013955729").then(res => {
        this.registerOptions = res.data.dictItemList;
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
    getShipOptions() {
      getDictTypeById("1529905266810732545").then(res => {
        this.shipOptions = res.data.dictItemList;
      });
    },
    initOptions() {
      this.getFormData();
      this.getGenderOptions();
      this.getEthnicityOptions();
      this.getMaritalStatusOptions();
      this.getChildbirthStatusOptions();
      this.getPoliticalStatusOptions();
      this.getHighestEducationOptions();
      this.getAcademicOptionsOptions();
      this.getHealthStatusOptionsOptions();
      this.getRegisterOptionsOptions();
      this.getEducationMethodOptions();
      this.getSkillOptions();
      this.getShipOptions();
    }
  },
  watch: {},
  mounted() {
    this.initOptions();
  }
};
</script>
<style lang="scss">
.baseComponent {
  padding: 0 15px;
  .my-input-sc {
    display: inline-block;
    height: 30px;
    line-height: 30px;
    -webkit-appearance: none;
    background-color: #ffffff;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    box-sizing: border-box;
    color: #606266;
    font-size: inherit;
    outline: none;
    padding: 0 15px;
    transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
    width: 100%;
  }
  .dc-el-divider_ElDivider {
    margin-top: 50px;
  }
}
</style>
