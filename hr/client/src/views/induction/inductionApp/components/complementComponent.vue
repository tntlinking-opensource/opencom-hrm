<template>
  <el-row class="dc-container complementComponent" v-loading="loading">
    <el-divider class="dc-el-divider_ElDivider">hr补全信息</el-divider>
    <el-form
      ref="hrFormData"
      :model="hrFormData"
      label-width="145px"
      class="dc-el-form_ElEditForm"
    >
      <el-col :span="6">
        <el-form-item
          prop="jobPosition"
          label="招聘岗位"
          :rules="[
            { required: true, message: '招聘岗位不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.jobPosition"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            disabled
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in positionOptions"
              :key="index"
              :label="item.postName"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="workCity"
          label="工作城市"
          :rules="[
            { required: true, message: '工作城市不能为空', trigger: 'blur' }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="hrFormData.workCity"
            :maxLength="255"
            placeholder="请输入工作城市"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="companyName"
          label="所属公司"
          :rules="[
            { required: true, message: '所属公司不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-input
            v-model="hrFormData.companyName"
            disabled
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="personnelArea"
          label="人事区域"
          :rules="[
            { required: true, message: '人事区域不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-input
            v-model="hrFormData.personnelArea"
            placeholder="请输入人事区域"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="department.id"
          label="部门"
          :rules="[
            { required: true, message: '部门不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.department"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            clearable
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in departmentOptions"
              :key="index"
              :label="item.label"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="employeeNumber"
          label="工号"
          :rules="[
            { required: true, message: '工号不能为空', trigger: 'blur' }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="hrFormData.employeeNumber"
            :maxLength="64"
            placeholder="请输入工号"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="companyEmail"
          label="公司邮箱"
          :rules="[
            { required: true, message: '公司邮箱不能为空', trigger: 'blur' }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="hrFormData.companyEmail"
            :maxLength="100"
            placeholder="请输入公司邮箱"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="employeeType.id"
          label="员工类型"
          :rules="[
            { required: true, message: '员工类型不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.employeeType"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in employeeTypeOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="employeeNature.id"
          label="员工性质"
          :rules="[
            { required: true, message: '员工性质不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.employeeNature"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in employeeNatureOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="workingHoursCategory.id"
          label="工时类别"
          :rules="[
            { required: true, message: '工时类别不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.workingHoursCategory"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in categoryOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="employeeStatus.id"
          label="员工状态"
          :rules="[
            { required: true, message: '员工状态不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.employeeStatus"
            :style="{ width: '100%' }"
            placeholder="请选择员工状态"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in employeeStatusOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="entryTime"
          label="入职时间"
          :rules="[
            { required: true, message: '入职时间不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_PickerTimeDate"
        >
          <el-date-picker
            v-model="hrFormData.entryTime"
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
          prop="probationMonth"
          label="试用期(月)"
          :rules="[
            {
              required: true,
              message: '试用期(月)不能为空',
              trigger: 'change'
            }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.probationMonth"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            @change="changeProbationMonth"
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
        <el-form-item
          prop="probationEndTime"
          label="试用结束时间"
          :rules="[
            {
              required: true,
              message: '试用结束时间不能为空',
              trigger: 'change'
            }
          ]"
          class="dc-el-form-item_PickerTimeDate"
        >
          <el-date-picker
            v-model="hrFormData.probationEndTime"
            :style="{ width: '100%' }"
            placeholder="请选择"
            type="date"
            disabled
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            class="dc-el-date-picker_PickerTimeDate"
          ></el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="techPosition"
          label="技术职务"
          :rules="[
            { required: true, message: '技术职务不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.techPosition"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            @change="changePosition"
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
      <el-col :span="6">
        <el-form-item
          prop="employeeLevel"
          label="员工职级"
          :rules="[
            { required: true, message: '员工职级不能为空', trigger: 'blur' }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="hrFormData.employeeLevel"
            placeholder="请输入员工职级"
            clearable
            disabled
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="jobType"
          label="从事工作"
          :rules="[
            { required: true, message: '从事工作不能为空', trigger: 'blur' }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="hrFormData.jobType"
            placeholder="请输入从事工作"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="computerCondition"
          label="电脑情况"
          :rules="[
            { required: true, message: '电脑情况不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.computerCondition"
            :style="{ width: '100%' }"
            placeholder="请选择"
            @change="changeComputer"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in computerConditionOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="recruitmentMode"
          label="招聘方式"
          :rules="[
            { required: true, message: '招聘方式不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.recruitmentMode"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in recruitmentModeOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="contractType"
          label="合同类型"
          :rules="[
            { required: true, message: '合同类型不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.contractType"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in contractTypeOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="contractProperty"
          label="合同属性"
          :rules="[
            { required: true, message: '合同属性不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.contractProperty"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in contractPropertyOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="contractSignUnit"
          label="合同签约单位"
          :rules="[
            {
              required: true,
              message: '合同签约单位不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            disabled
            v-model="hrFormData.contractSignUnit"
            :maxLength="255"
            placeholder="请输入合同签约单位"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="contractStartTime"
          label="合同开始时间"
          :rules="[
            {
              required: true,
              message: '合同开始时间不能为空',
              trigger: 'change'
            }
          ]"
          class="dc-el-form-item_PickerTimeDate"
        >
          <el-date-picker
            v-model="hrFormData.contractStartTime"
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
          prop="contractMonths"
          label="合同期限(月)"
          :rules="[
            {
              required: true,
              message: '合同期限(月)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_NumberInput"
        >
          <number-input
            v-model="hrFormData.contractMonths"
            :min="0"
            placeholder="请输入合同期限(月)"
            clearable
            class="dc-number-input_NumberInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="contractEndTime"
          label="合同结束时间"
          :rules="[
            {
              required: true,
              message: '合同结束时间不能为空',
              trigger: 'change'
            }
          ]"
          class="dc-el-form-item_PickerTimeDate"
        >
          <el-date-picker
            v-model="hrFormData.contractEndTime"
            :style="{ width: '100%' }"
            placeholder="请选择"
            disabled
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            class="dc-el-date-picker_PickerTimeDate"
          ></el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="fullWages"
          label="全额工资(元)"
          :rules="[
            {
              required: true,
              message: '全额工资(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.fullWages"
            :min="0"
            placeholder="请输入全额工资(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="probationWages"
          label="试用期工资(元)"
          :rules="[
            {
              required: true,
              message: '试用期工资(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.probationWages"
            :min="0"
            placeholder="请输入试用期工资(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="probationBasicWages"
          label="试用期基本工资(元)"
          :rules="[
            {
              required: true,
              message: '试用期基本工资(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.probationBasicWages"
            :min="0"
            placeholder="请输入试用期基本工资(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="basicWages"
          label="基本工资(元)"
          :rules="[
            {
              required: true,
              message: '基本工资(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.basicWages"
            :min="0"
            placeholder="请输入基本工资(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="secrecyReward"
          label="保密奖(元)"
          :rules="[
            {
              required: true,
              message: '保密奖(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.secrecyReward"
            :min="0"
            placeholder="请输入保密奖(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="systemReward"
          label="制度奖(元)"
          :rules="[
            {
              required: true,
              message: '制度奖(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.systemReward"
            :min="0"
            placeholder="请输入制度奖(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="basicPerformance"
          label="基本绩效(元)"
          :rules="[
            {
              required: true,
              message: '基本绩效(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.basicPerformance"
            :min="0"
            placeholder="请输入基本绩效(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="floatingPerformance"
          label="浮动绩效(元)"
          :rules="[
            {
              required: true,
              message: '浮动绩效(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.floatingPerformance"
            :min="0"
            placeholder="请输入浮动绩效(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="probationBasicPerformance"
          label="试用期基本绩效(元)"
          :rules="[
            {
              required: true,
              message: '试用期基本绩效(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.probationBasicPerformance"
            :min="0"
            placeholder="请输入试用期基本绩效(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="probationFloatingPerformance"
          label="试用期浮动绩效(元)"
          :rules="[
            {
              required: true,
              message: '试用期浮动绩效(元)不能为空',
              trigger: 'blur'
            }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <number-input
            v-model="hrFormData.probationFloatingPerformance"
            :min="0"
            placeholder="请输入试用期浮动绩效(元)"
            clearable
            class="dc-el-input_SingleInput"
          ></number-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="reportTime"
          label="报道日期"
          :rules="[
            { required: true, message: '报道日期不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_PickerTimeDate"
        >
          <el-date-picker
            v-model="hrFormData.reportTime"
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
          prop="entryType"
          label="入职类型"
          :rules="[
            { required: true, message: '入职类型不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-select
            v-model="hrFormData.entryType"
            :style="{ width: '100%' }"
            placeholder="请选择"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in entryTypeOptions"
              :key="index"
              :label="item.name"
              :value="item"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="employeeBelongs"
          label="员工归属"
          :rules="[
            { required: true, message: '员工归属不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-input
            v-model="hrFormData.employeeBelongs"
            placeholder="请输入员工归属"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          prop="companyLegalPerson"
          label="法人"
          :rules="[
            { required: true, message: '法人不能为空', trigger: 'change' }
          ]"
          class="dc-el-form-item_SelectInput"
        >
          <el-input
            v-model="hrFormData.companyLegalPerson"
            placeholder="请输入法人"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item
          prop="reportAddress"
          label="报道地址"
          :rules="[
            { required: true, message: '报道地址不能为空', trigger: 'blur' }
          ]"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="hrFormData.reportAddress"
            placeholder="请输入报道地址"
            clearable
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item
          prop="computerSpecialRemarks"
          label="电脑特殊需求备注"
          v-if="hrFormData.computerCondition.id === '1513455318726787145'"
          class="dc-el-form-item_MutilpleInput"
          :rules="[
            {
              required: true,
              message: '电脑特殊需求备注不能为空',
              trigger: 'change'
            }
          ]"
        >
          <el-input
            v-model="hrFormData.computerSpecialRemarks"
            type="textarea"
            placeholder="请输入"
            rows="2"
            class="dc-el-input_MutilpleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item
          prop="remarks"
          label="备注"
          class="dc-el-form-item_MutilpleInput"
        >
          <el-input
            v-model="hrFormData.remarks"
            type="textarea"
            placeholder="请输入备注"
            rows="2"
            :maxLength="255"
            class="dc-el-input_MutilpleInput"
          ></el-input>
        </el-form-item>
      </el-col>

      <el-col style="display: flex;justify-content: center;margin-bottom: 10px;"
        ><el-button icon="el-icon-folder-checked" @click="saveHrForm"
          >保存</el-button
        ></el-col
      >
    </el-form>
  </el-row>
</template>
<script>
/** 根据用户界面配置import组件 结束 */
import OperationIcon from "@/components/OperationIcon";
import { listTsmRecruitAll } from "@/api/recruit/tsmRecruit";
import baseUI from "@/views/components/baseUI";
import { getDictTypeById } from "@/api/sys/dictType";
import {
  getInfoCompletionBasicById,
  saveInfoCompletion
} from "@/api/induction/tsmInductionBasic";
import { listAllByCompany } from "@/api/org/department";
export default {
  extends: baseUI,
  name: "complementComponent",
  components: {
    OperationIcon
  },
  props: ["rowData"],
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      hrFormData: {
        jobPosition: "", // '招聘岗位'
        workCity: "", // '工作城市',
        companyName: "", //'所属公司',
        personnelArea: "", //  '人事区域',
        department: "", //  '部门',
        employeeNumber: "", //  '工号',
        employeeType: "", //    员工类型
        employeeStatus: "", //    员工状态
        employeeNature: "", //     员工性质
        workingHoursCategory: "", //工时类别
        companyEmail: "", // 公司邮箱,
        entryTime: "", //     入职时间
        probationMonth: "", //试用期(月)
        probationEndTime: "", //试用结束时间
        techPosition: "", // 技术职务
        employeeLevel: "", // 员工职级
        jobType: "",
        computerCondition: "", //  电脑情况
        recruitmentMode: "", //招聘方式
        contractType: "", //    合同类型
        contractProperty: "", //合同属性
        contractSignUnit: "", //合同签约单位
        contractStartTime: "", //合同开始时间
        contractMonths: "", //合同期限（月）
        contractEndTime: "", // 合同结束时间
        fullWages: "", //   全额工资（元）
        probationWages: "", // 试用期工资（元）
        probationBasicWages: "", //试用期基本工资（元）
        basicWages: "", //   基本工资（元）
        secrecyReward: "", // 保密奖（元）
        systemReward: "", // 制度奖（元）
        basicPerformance: "", //基本绩效（元）
        floatingPerformance: "", //  浮动绩效（元）
        probationBasicPerformance: "", // 试用期基本绩效（元）
        probationFloatingPerformance: "", //     试用期浮动绩效（元）
        reportTime: "", //   报道日期
        entryType: "", //  入职类型
        employeeBelongs: "", //   员工归属
        companyLegalPerson: "",
        reportAddress: "", //  报道地址
        remarks: "",
        computerSpecialRemarks: ""
      },
      selectOptions: [],
      positionOptions: [],
      employeeTypeOptions: [],
      employeeNatureOptions: [],
      categoryOptions: [],
      employeeStatusOptions: [],
      probationMonthOptions: [],
      techPositionOptions: [],
      computerConditionOptions: [],
      recruitmentModeOptions: [],
      contractTypeOptions: [],
      contractPropertyOptions: [],
      entryTypeOptions: [],
      departmentOptions: []
    };
  },

  methods: {
    initOptions() {
      // 初始化自定义类型选择框选项变量
      this.getFormData();
      this.getPositionOptions();
      this.getEmployeeTypeOptions();
      this.getEmployeeNatureOptions();
      this.getCategoryOptions();
      this.getEmployeeStatusOptions();
      this.getProbationMonthOptions();
      this.getComputerConditionOptions();
      this.getRecruitmentModeOptions();
      this.getContractTypeOptions();
      this.getContractPropertyOptions();
      this.getEntryTypeOptions();
      this.getTechPositionOptions();
      this.getDepartment();
    },
    getFormData() {
      getInfoCompletionBasicById(this.rowData.id).then(res => {
        if (res.code === "100") {
          let obj = JSON.parse(JSON.stringify(res.data));
          if (!obj.employeeType["id"]) {
            delete obj.employeeType;
          }
          if (!obj.department["id"]) {
            delete obj.department;
          }
          if (!obj.employeeNature["id"]) {
            delete obj.employeeNature;
          }
          if (!obj.workingHoursCategory["id"]) {
            delete obj.workingHoursCategory;
          }
          if (!obj.employeeStatus["id"]) {
            delete obj.employeeStatus;
          }
          this.hrFormData = Object.assign(this.hrFormData, obj);
        }
      });
    },
    saveHrForm() {
      this.$refs["hrFormData"].validate(valid => {
        if (valid) {
          saveInfoCompletion(this.hrFormData).then(res => {
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
    changeProbationMonth(val) {
      if (this.hrFormData.entryTime) {
        this.hrFormData.probationEndTime = this.changeTime(
          this.hrFormData.entryTime,
          val.value
        );
      } else {
        this.showMessage({ type: "warning", msg: "请先选择入职时间" });
      }
    },
    changeContractTime(entryTime, val) {
      let d = new Date(entryTime);
      var Month = d.getMonth() + 1;
      var Day = d.getDate();
      var Year = d.getFullYear();
      //合同结束
      if (val !== "0" && val !== 0) {
        var mydate = new Date(Year, Month + Number(val), Day);
        var Yeara = mydate.getFullYear();
        var Monthb = mydate.getMonth();
        var Datec = mydate.getDate();

        if (Monthb === 4 || Monthb === 6 || Monthb === 9 || Monthb === 11) {
          Datec = 30;
        } else {
          Datec = 31;
        }

        if (Monthb === 2 && Datec > 29) {
          if (Yeara % 4 === 0) {
            //判断为闰月
            Datec = 29;
          } else {
            Datec = 28;
          }
        }
        let str = `${Yeara}-${Monthb > 10 ? Monthb : "0" + Monthb}-${
          Datec > 10 ? Datec : "0" + Datec
        }`;
        return str;
      } else {
        return entryTime;
      }
    },
    changeTime(entryTime, val) {
      let d = new Date(entryTime);
      var Month = d.getMonth() + 1;
      var Day = d.getDate();
      var Year = d.getFullYear();
      //试用期截至
      if (val !== "0" && val !== 0) {
        var mydate = new Date(Year, Month + Number(val), Day - 1);
        var Yeara = mydate.getFullYear();
        var Monthb = mydate.getMonth();
        var Datec = mydate.getDate();

        if (Day === 1) {
          if (Monthb === 4 || Monthb === 6 || Monthb === 9 || Monthb === 11) {
            Datec = 30;
          } else {
            Datec = 31;
          }
        }
        if (Monthb === 2 && Datec > 29) {
          if (Yeara % 4 === 0) {
            //判断为闰月
            Datec = 29;
          } else {
            Datec = 28;
          }
        }
        if (Monthb === 0) {
          Monthb = 12;
          Yeara = Yeara - 1;
        }
        let str = `${Yeara}-${Monthb > 9 ? Monthb : "0" + Monthb}-${
          Datec > 9 ? Datec : "0" + Datec
        }`;
        return str;
      } else {
        return entryTime;
      }
    },

    //获取岗位下拉
    getPositionOptions() {
      let search = {
        columnName: "",
        order: "",
        params: []
      };
      listTsmRecruitAll(search).then(res => {
        if (res.code === "100") {
          this.positionOptions = res.data.filter(item => item.staffNum !== "0");
        }
      });
    },
    //获取员工类型
    getEmployeeTypeOptions() {
      getDictTypeById("1513227015445110975").then(res => {
        this.employeeTypeOptions = res.data.dictItemList;
      });
    },
    //获取员工性质
    getEmployeeNatureOptions() {
      getDictTypeById("1513455318726787096").then(res => {
        this.employeeNatureOptions = res.data.dictItemList;
      });
    },
    //获取工时类别
    getCategoryOptions() {
      getDictTypeById("1513455318726787103").then(res => {
        this.categoryOptions = res.data.dictItemList;
      });
    },
    //获取员工状态
    getEmployeeStatusOptions() {
      getDictTypeById("1513455318726787117").then(res => {
        this.employeeStatusOptions = res.data.dictItemList;
      });
    },
    //获取试用期
    getProbationMonthOptions() {
      getDictTypeById("1513455318726787124").then(res => {
        this.probationMonthOptions = res.data.dictItemList;
      });
    },
    //获取电脑情况
    getComputerConditionOptions() {
      getDictTypeById("1513455318726787139").then(res => {
        this.computerConditionOptions = res.data.dictItemList;
      });
    },
    //获取招聘方式
    getRecruitmentModeOptions() {
      getDictTypeById("1513455318726787147").then(res => {
        this.recruitmentModeOptions = res.data.dictItemList;
      });
    },
    //获取合同类型
    getContractTypeOptions() {
      getDictTypeById("1513455318726787156").then(res => {
        this.contractTypeOptions = res.data.dictItemList;
      });
    },
    //获取合同属性
    getContractPropertyOptions() {
      getDictTypeById("1513455318726787161").then(res => {
        this.contractPropertyOptions = res.data.dictItemList;
      });
    },
    getEntryTypeOptions() {
      getDictTypeById("1476148957913825287").then(res => {
        this.entryTypeOptions = res.data.dictItemList;
      });
    },
    //获取技术职务
    getTechPositionOptions() {
      getDictTypeById("1515026469303484430").then(res => {
        this.techPositionOptions = res.data.dictItemList;
      });
    },
    //获取部门
    getDepartment() {
      listAllByCompany({}).then(res => {
        this.departmentOptions = res.data || [];
      });
    },
    changeComputer(val) {
      if (val.id !== "1513455318726787145") {
        this.hrFormData.computerSpecialRemarks = "";
      }
    },
    changePosition(val) {
      this.hrFormData.employeeLevel = val.value;
    }
  },
  watch: {
    "hrFormData.contractMonths"(val) {
      if (this.hrFormData.contractStartTime) {
        this.hrFormData.contractEndTime = this.changeContractTime(
          this.hrFormData.contractStartTime,
          val
        );
      }
    }
  },
  mounted() {
    this.initOptions();
  }
};
</script>
<style lang="scss">
.complementComponent {
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
}
</style>
