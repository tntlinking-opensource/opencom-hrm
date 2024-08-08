<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="90%"
      :fullscreen="false"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <div slot='title' class='dialog-header'>
        {{ dialogProps.title }}
        <OperationIcon v-show='dialogProps.action != "view" && permission.add' type='primary' text='选择人员' placement='top-start' icon-name='el-icon-edit' @click='onChangeUser'></OperationIcon>
      </div>
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-divider content-position="left">个人信息</el-divider>
            <el-col :span="6">
              <el-form-item
                prop="name"
                label="姓名"
                :rules="[{ required: true, message: '姓名不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.name"
                  @input="onInputName"
                  :maxLength="128"
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
                :rules="[{ required: true, message: '性别不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.gender"
                  :style="{ width: '100%' }"
                  placeholder="请选择性别"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option v-for="(item, index) in genderOptions" :key="index" :label="item.name" :value="item"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item
                prop="entryTime"
                label="入职时间"
                :rules="[{ required: true, message: '入职时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.entryTime"
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
              <el-form-item prop="companyEmail" label="公司邮箱"
                            :rules="[{ required: true, message: '公司邮箱不能为空', trigger: 'blur' },
                            { pattern: new RegExp(`^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$`), message: '请输入正确的公司邮箱', trigger: 'blur' }]"
                            class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.companyEmail"
                  :maxLength="100"
                  placeholder="请输入公司邮箱"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item
                prop="probationMonth.id"
                label="试用期"
                :rules="[{ required: true, message: '试用期不能为空', trigger: 'change' }]"
                class="dc-el-form-item_SelectInput"
              >
                <el-select
                  v-model="editFormData.probationMonth"
                  :style="{ width: '100%' }"
                  placeholder="请选择试用期"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in probationMonthOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="employeeNumber" label="工号" :rules="[{ required: true, message: '工号不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.employeeNumber"
                  :maxLength="64"
                  placeholder="请输入工号"
                  clearable
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="company.id" label="公司" :rules="[{ required: true, message: '公司不能为空', trigger: 'change' }]" class="dc-el-form-item_SelectInput">
                <el-cascader
                  ref="cascader104"
                  :options="companyOptions"
                  v-model="editFormData.company.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择公司"
                  :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                  clearable
                  filterable
                  separator="/"
                  class="dc-el-cascader_CascaderInput"
                ></el-cascader>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item prop="department.id" label="部门" :rules="[{ required: true, message: '部门不能为空', trigger: 'change' }]" class="dc-el-form-item_SelectInput">
                <el-cascader
                  ref="cascader105"
                  :options="departmentOptions"
                  v-model="editFormData.department.id"
                  :style="{ width: '100%' }"
                  placeholder="请选择公司"
                  :props="{ label: 'name', value: 'id', children: 'children', checkStrictly: true, emitPath: false }"
                  clearable
                  filterable
                  separator="/"
                  class="dc-el-cascader_CascaderInput"
                ></el-cascader>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-divider content-position="left">劳动合同</el-divider>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="code" label="合同编号" class="dc-el-form-item_AutoSerial">
                <el-input
                  v-model="editFormData.code"
                  :maxLength="255"
                  placeholder="保存后自动生成"
                  disabled
                  :disabled="true"
                  class="dc-el-input_AutoSerial"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="theme" label="合同主题" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.theme"
                  :maxLength="255"
                  placeholder="请输入合同主题"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="idNumber"
                label="身份证号码"
                :rules="[{ required: true, message: '身份证号码不能为空', trigger: 'blur' },
                {
                  pattern: new RegExp(`^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$`),
                  message: '请输入正确的身份证号',
                  trigger: 'blur'
                }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.idNumber"
                  :maxLength="255"
                  placeholder="请输入身份证号码"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="address"
                label="居住地地址"
                :rules="[{ required: true, message: '居住地地址不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.address"
                  :maxLength="255"
                  placeholder="请输入居住地地址"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="phone"
                label="联系方式"
                :rules="[{ required: true, message: '联系方式不能为空', trigger: 'blur' },
                  { pattern: new RegExp(`^1[34578]\\d{9}$`), message: '请输入正确的手机号格式', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.phone"
                  :maxLength="64"
                  placeholder="请输入联系方式"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="email"
                label="个人邮箱"
                :rules="[{ required: true, message: '个人邮箱不能为空', trigger: 'blur' },
                { pattern: new RegExp(`^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$`), message: '请输入正确的邮箱', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.email"
                  :maxLength="128"
                  placeholder="请输入个人邮箱"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="contractStartTime"
                label="合同开始时间"
                :rules="[{ required: true, message: '合同开始时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.contractStartTime"
                  :style="{ width: '100%' }"
                  placeholder="请选择合同开始时间"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="contractEndTime"
                label="合同结束时间"
                :rules="[{ required: true, message: '合同结束时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.contractEndTime"
                  :style="{ width: '100%' }"
                  placeholder="请选择合同结束时间"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label-width="120px"
                prop="probationEndTime"
                label="试用期结束时间"
                :rules="[{ required: true, message: '试用期结束时间不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.probationEndTime"
                  :style="{ width: '100%' }"
                  placeholder="请选择试用期结束时间"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="techPosition.id" label="担任职务" :rules="[{ required: true, message: '担任职务不能为空', trigger: 'change' }]" class="dc-el-form-item_SelectInput">
                <el-select
                  v-model="editFormData.techPosition"
                  :style="{ width: '100%' }"
                  placeholder="请选择担任职务"
                  @change="onChangeTechPosition"
                  clearable
                  value-key="id"
                  filterable
                  class="dc-el-select_SelectInput"
                >
                  <el-option
                    v-for="(item, index) in techPositionOptions"
                    :key="index"
                    :label="item.name"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="employeeLevel" label="员工职级" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.employeeLevel"
                  :maxLength="21"
                  placeholder="请输入员工职位"
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="bjob" label="从事工作" :rules="[{ required: true, message: '从事工作不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.bjob"
                  :maxLength="128"
                  placeholder="请输入从事工作"
                  clearable
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="fullWages" label="全额工资" :rules="[{ required: true, message: '全额工资不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.fullWages"
                  :min="0"
                  :max="999999999"
                  :precision="2"
                  placeholder="请输入全额工资（元）"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="probationWages" label="试用期工资" :rules="[{ required: true, message: '试用期工资不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.probationWages"
                  :min="0"
                  :max="999999999"
                  :precision="2"
                  placeholder="请输入试用期工资（元）"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="basicWages" label="基本工资" :rules="[{ required: true, message: '基本工资不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.basicWages"
                  :min="0"
                  :max="999999999"
                  :precision="2"
                  placeholder="请输入基本工资（元）"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="systemReward" label="制度奖" :rules="[{ required: true, message: '制度奖不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.systemReward"
                  :min="0"
                  :max="999999999"
                  :precision="2"
                  placeholder="请输入制度奖（元）"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="secrecyReward" label="保密奖" :rules="[{ required: true, message: '保密奖不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.secrecyReward"
                  :min="0"
                  :max="999999999"
                  :precision="2"
                  placeholder="请输入保密奖（元）"
                  clearable
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="contractSignUnit" label="甲方" :rules="[{ required: true, message: '甲方不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.contractSignUnit"
                  :maxLength="255"
                  placeholder="请输入合同签约单位"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="16">
              <el-form-item label-width="120px" prop="reportAddress" label="甲方住所地" :rules="[{ required: true, message: '甲方住所地不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.reportAddress"
                  :maxLength="255"
                  placeholder="请输入甲方住所地"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label-width="120px" prop="laborLegalmana" label="法定代表人" :rules="[{ required: true, message: '法定代表人不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.laborLegalmana"
                  :maxLength="128"
                  placeholder="请输入法定代表人"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider content-position="left">工作协议</el-divider>
          <div style="display: flex;justify-content: center">
            <div>
              <el-row>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="basicPerformance" label="基本绩效" :rules="[{ required: true, message: '基本绩效不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                    <number-input
                      v-model="editFormData.basicPerformance"
                      :min="0"
                      :max="999999999"
                      :precision="2"
                      placeholder="请输入基本绩效（元）"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="probationBasicPerformance" label="试用期基本绩效" :rules="[{ required: true, message: '试用期基本绩效不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                    <number-input
                      v-model="editFormData.probationBasicPerformance"
                      :min="0"
                      :max="999999999"
                      :precision="2"
                      placeholder="请输入试用期基本绩效（元）"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="floatingPerformance" label="浮动绩效" :rules="[{ required: true, message: '浮动绩效不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                    <number-input
                      v-model="editFormData.floatingPerformance"
                      :min="0"
                      :max="999999999"
                      :precision="2"
                      placeholder="请输入浮动绩效（元）"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item
                    label-width="140px"
                    prop="probationFloatingPerformance"
                    label="试用期浮动绩效"
                    :rules="[{ required: true, message: '试用期浮动绩效不能为空', trigger: 'blur' }]"
                    class="dc-el-form-item_SingleInput"
                  >
                    <number-input
                      v-model="editFormData.probationFloatingPerformance"
                      :min="0"
                      :max="999999999"
                      :precision="2"
                      placeholder="请输入试用期浮动绩效（元）"
                      clearable
                      class="dc-number-input_NumberInput"
                    ></number-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <el-divider content-position="left">其余信息</el-divider>
          <div style="display: flex;justify-content: center">
            <div>
              <el-row>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="expirationDate" label="上份合约结束时间" :rules="[{ required: true, message: '上份合约结束时间不能为空', trigger: 'blur' }]" class="dc-el-form-item_PickerTimeDate">
                    <el-date-picker
                      v-model="editFormData.expirationDate"
                      :style="{ width: '100%' }"
                      placeholder="请选择上份合约结束时间"
                      type="date"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd"
                      class="dc-el-date-picker_PickerTimeDate"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="endDate" label="合同签订截至日期" :rules="[{ required: true, message: '合同签订截至日期不能为空', trigger: 'blur' }]" class="dc-el-form-item_PickerTimeDate">
                    <el-date-picker
                      v-model="editFormData.endDate"
                      :style="{ width: '100%' }"
                      placeholder="请选择合同签订截至日期"
                      type="date"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd"
                      class="dc-el-date-picker_PickerTimeDate"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="arriveName" label="送达人" :rules="[{ required: true, message: '送达人不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.arriveName"
                      :maxLength="128"
                      placeholder="请输入送达人"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="arrivePhone" label="送达人联系方式"
                                :rules="[{ required: true, message: '送达人联系方式不能为空', trigger: 'blur' },
                                { pattern: new RegExp(`^1[34578]\\d{9}$`), message: '请输入正确的格式', trigger: 'blur' },
                                { pattern: new RegExp(`^\\d{3,4}-?\\d{7,9}$`), message: '请输入正确的格式', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                    <el-input
                      v-model="editFormData.arrivePhone"
                      :maxLength="128"
                      placeholder="请输入送达人联系方式"
                      clearable
                      autocomplete="new-password"
                      class="dc-el-input_SingleInput"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label-width="140px" prop="signDate" label="签字日期" :rules="[{ required: true, message: '签字日期不能为空', trigger: 'blur' }]" class="dc-el-form-item_PickerTimeDate">
                    <el-date-picker
                      v-model="editFormData.signDate"
                      :style="{ width: '100%' }"
                      placeholder="请选择合签字日期"
                      type="date"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd"
                      class="dc-el-date-picker_PickerTimeDate"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
    <select-user ref="selectUser" v-on:select-finished="getInductionBatchContract"></select-user>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { getInductionBatchContractById } from '@/api/induction/contract/inductionBatchContract'
import { listDictItemAll } from '@/api/sys/dictItem.js'
import { treeCompany } from '@/api/org/company.js'
import { treeDepartment } from '@/api/org/department.js'
/** 根据用户界面配置import组件 结束 */
import {
  getInductionContractRenewalById,
  saveInductionContractRenewal
} from '@/api/induction/contract/inductionContractRenewal'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import SelectUser from './selectUser'
export default {
  extends: BaseUI,
  name: 'inductionContractRenewal-form',
  components: {
    OperationIcon,
    SelectUser,
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      // 对话框属性变量
      dialogProps: {
        visible: false,
        title: '合约续签'
      },
      dialogTitle: '合约续签',
      contractInformation: '', // 合同信息ID
      // 性别选项
      genderOptions: [],
      // 试用期选项
      probationMonthOptions: [],
      // 员工技术职务选项
      techPositionOptions: [],
      // 公司选项
      companyOptions: [],
      // 部门选项
      departmentOptions: [],
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
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      })
    },
    doSave() {
      this.setLoad()
      saveInductionContractRenewal(this.editFormData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.showMessage({
              type: 'success',
              msg: '保存成功'
            })
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    onChangeUser() { // 更换入职人员
      this.$refs.selectUser.$emit('openSelectUserDialog')
    },
    getInductionBatchContract(contractId) { // 根据合约信息ID，获取合约信息
      if (!validatenull(contractId)) {
        this.setLoad()
        getInductionBatchContractById(contractId).then((responseData) => {
          if (responseData.code == 100) {
            this.contractInformation = responseData.data
            this.editFormData = this.changeContract(this.contractInformation) // 转换为入职续签字段
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch((error) => {
          this.outputError(error)
        })
      }
    },
    changeContract(contract) { // 数值转换
      return{
        code: '', // 合同编号
        theme: contract.induction ? contract.induction.name + '-合约续签' : '合约续签', // 合同主题
        name: contract.induction ? contract.induction.name : '', // 姓名
        gender: contract.induction ? contract.induction.gender : { id: null, name: null },
        phone: contract.induction ? contract.induction.phoneNumber : '', // 手机号
        idNumber: contract.induction ? contract.induction.idNumber : '', // 身份证号码
        email: contract.induction ? contract.induction.email : '', // 邮箱
        address: contract.induction ? contract.induction.residenceAddress : '', // 居住地地址
        entryTime: contract.entryTime ? contract.entryTime : '', // 入职时间
        probationMonth: contract.probationMonth ? contract.probationMonth : { id: null, name: null},
        employeeLevel: contract.techPosition ? contract.techPosition.value : '',  // 员工职级
        employeeNumber: contract.employeeNumber ? contract.employeeNumber : '', // 工号
        techPosition: contract.techPosition ? contract.techPosition : { id: null, name: null, value: null}, // 员工职位
        bjob: contract.bjob ? contract.bjob : '', // 从事工作
        contractStartTime: contract.contractEndTime ? contract.contractEndTime : '', // 合同开始时间
        contractEndTime: '', // 合同结束时间
        probationEndTime: '', // 试用期结束时间
        company: contract.company ? contract.company : { id: null, name: null, code: null},
        department: contract.department ? contract.department : {id: null, name: null, code: null},
        companyEmail: contract.companyEmail ? contract.companyEmail : '', // 公司邮箱
        contractSignUnit: contract.contractSignUnit ? contract.contractSignUnit : '', // 合同签约单位
        laborLegalmana: contract.laborLegalmana ? contract.laborLegalmana : '', // 合同签约方法人
        reportAddress: contract.reportAddress ? contract.reportAddress : '', // 甲方住所地
        fullWages: contract.fullWages ? contract.fullWages : '', // 全额工资（元）
        probationWages: contract.probationWages ? contract.probationWages : '', // 试用期工资（元）
        basicWages: contract.basicWages ? contract.basicWages : '', // 基本工资（元）
        probationBasicWages: contract.probationBasicWages ? contract.probationBasicWages : '', // 试用期基本工资（元）
        secrecyReward: contract.secrecyReward ? contract.secrecyReward : '', // 保密奖（元）
        systemReward: contract.systemReward ? contract.systemReward : '', // 制度奖（元）
        basicPerformance: contract.basicPerformance ? contract.basicPerformance : '', // 基本绩效（元）
        floatingPerformance: contract.floatingPerformance ? contract.floatingPerformance : '', // 浮动绩效（元）
        probationBasicPerformance: contract.probationBasicPerformance ? contract.probationBasicPerformance : '', // 试用期基本绩效（元）
        probationFloatingPerformance: contract.probationFloatingPerformance ? contract.probationFloatingPerformance : '', // 试用期浮动绩效（元）
        contractDate: '', // 合同生成时间
        contractStartDate: '', // 合同发起时间
        contractStatus: {
          name: null,
          value: '0'
        },
        signStatus: {
          name: null,
          value: '0',
          id: null
        },
        expirationDate: contract.contractEndTime ? contract.contractEndTime : '', // 到期日期
        endDate: '', // 截止日期
        arriveName: '', // 送达人
        arrivePhone: '', // 送达人联系方式
        signDate: '', // 签字时间
        remarks: '' // 备注信息
      }
    },
    onChangeTechPosition(val) {
      this.editFormData.employeeLevel = val.value
    },
    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getInductionContractRenewalById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
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
    onInputName() {
      this.editFormData.theme = !validatenull(this.editFormData.name) ? this.editFormData.name + '-合约续签' : '合约续签'
    },
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },

    listGenderOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'dict_type_id',
          queryType: '=',
          value: '1495769794452094977'
        }
      ])
      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.genderOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listProbationMonthOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'dict_type_id',
          queryType: '=',
          value: '1513455318726787124'
        }
      ])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.probationMonthOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listTechPositionOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'dict_type_id',
          queryType: '=',
          value: '1515026469303484430'
        }
      ])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.techPositionOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listCompanyOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: 'id',
          queryType: '=',
          value: currentUser.company.id == '0' ? '' : currentUser.company.id
        }
      ])
      // 数据权限: 公司org_company
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000000')
      treeCompany(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.companyOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
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

      treeDepartment(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.departmentOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listGenderOptions()

      this.listProbationMonthOptions()

      this.listTechPositionOptions()

      this.listCompanyOptions()

      this.listDepartmentOptions()
    },
    initEditData(This) {
      return {
        code: '', // 合同编号
        theme: '', // 合同主题
        name: '', // 姓名
        gender: {
          id: null,
          name: null
        },
        phone: '', // 手机号
        idNumber: '', // 身份证号码
        email: '', // 邮箱
        address: '', // 居住地地址
        entryTime: '', // 入职时间
        probationMonth: {
          id: null,
          name: null
        },
        employeeLevel: '', // 员工职级
        employeeNumber: '', // 工号
        techPosition: {
          id: null,
          name: null,
          value: null
        }, // 员工职位
        bjob: '', // 从事工作
        contractStartTime: '', // 合同开始时间
        contractEndTime: '', // 合同结束时间
        probationEndTime: '', // 试用期结束时间
        company: {
          id: null,
          name: null,
          code: null
        },
        department: {
          id: null,
          name: null,
          code: null
        },
        companyEmail: '', // 公司邮箱
        contractSignUnit: '', // 合同签约单位
        laborLegalmana: '', // 合同签约方法人
        reportAddress: '', // 甲方住所地
        fullWages: '', // 全额工资（元）
        probationWages: '', // 试用期工资（元）
        basicWages: '', // 基本工资（元）
        probationBasicWages: '', // 试用期基本工资（元）
        secrecyReward: '', // 保密奖（元）
        systemReward: '', // 制度奖（元）
        basicPerformance: '', // 基本绩效（元）
        floatingPerformance: '', // 浮动绩效（元）
        probationBasicPerformance: '', // 试用期基本绩效（元）
        probationFloatingPerformance: '', // 试用期浮动绩效（元）
        contractDate: '', // 合同生成时间
        contractStartDate: '', // 合同发起时间
        contractStatus: {
          name: null,
          value: null
        },
        signStatus: {
          name: null,
          value: null,
          id: null
        },
        expirationDate: '', // 到期日期
        endDate: '', // 截止日期
        arriveName: '', // 送达人
        arrivePhone: '', // 送达人联系方式
        signDate: '', // 签字时间
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
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
        this.editFormData.code = null

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
