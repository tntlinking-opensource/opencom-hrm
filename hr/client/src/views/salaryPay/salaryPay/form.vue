<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="60%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-button v-on:click="onDialogClose" v-if="action == 'view'" style="color: #0a7be0;">审批流程</el-button>
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="120px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-divider>账单信息</el-divider>
          <el-row>
            <el-col :span="8">
              <el-form-item prop="saBatchCode" label="账单批次号" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.saBatchCode"
                  :maxLength="50"
                  placeholder=""
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="billYearMonth" label="账单年月" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.billYearMonth"
                  :maxLength="100"
                  placeholder=" "
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="totalAmount" label="账单金额" class="dc-el-form-item_NumberInput">
                <number-input
                  v-model="editFormData.totalAmount"
                  :min="0"
                  :max="999999999999.99"
                  :precision="2"
                  placeholder=""
                  clearable
                  disabled
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="totalDecimal" label="工资发放人数" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.totalDecimal"
                  :maxLength="18"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="applyPayDate" label="账单生成时间" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.applyPayDate"
                  :maxLength="-1"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="applyPayUserId" label="生成人" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.applyPayUserId"
                  :maxLength="40"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                prop="payState"
                label="是否确认"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.payState"
                  :maxLength="128"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="payTime" label="账单确认时间" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.payTime"
                  :maxLength="-1"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="auditUser" label="确认人" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.auditUser"
                  :maxLength="19"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="payYearMonth" label="工资所属月" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.payYearMonth"
                  :maxLength="100"
                  placeholder=""
                  clearable
                  disabled
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="taxMonth" label="工资报税年月" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.taxMonth"
                  :maxLength="100"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-divider>申请发放</el-divider>
            <el-col :span="8">
              <el-form-item prop="isVerify" label="申请单号" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.id"
                  :maxLength="128"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="applyState" label="申请状态" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.applyState" :style="{ width: '100%' }" placeholder="请选择" clearable
                           filterable class="dc-el-select_SelectInput">
                  <el-option
                    v-for="(item, index) in applyStatetion"
                    :key="index"
                    :label="item.name"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="submitTime" label="申请发放日期" class="dc-el-form-item_SingleInput"
                            :rules="[{ required: true, message: '申请发放日期不能为空', trigger: 'blur' }]">
                <el-date-picker v-model="editFormData.submitTime" :style="{ width: '100%' }" placeholder="请选择入职时间"
                                type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
<!--                <el-input-->
<!--                  v-model="editFormData.submitTime"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder=""-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="submitTime" label="申请发放银行" class="dc-el-form-item_SingleInput"
                            :rules="[{ required: true, message: '申请发放银行不能为空', trigger: 'blur' }]">
                <el-select v-model="editFormData.employeeStatus" :style="{ width: '100%' }" placeholder="请选择" clearable
                           filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in statusOptions" :key="index" :label="item.name"
                             :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="submitTime" label="发放名称" class="dc-el-form-item_SingleInput"
                            :rules="[{ required: true, message: '发放名称不能为空', trigger: 'blur' }]">
                <el-input
                  v-model="editFormData.payName"
                  :maxLength="-1"
                  placeholder=""
                  clearable
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="submitTime" label="申请人" class="dc-el-form-item_SingleInput">
                <el-input
                  v-model="editFormData.submitUserId"
                  :maxLength="-1"
                  placeholder=""
                  clearable
                  disabled
                  autocomplete="new-password"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="isVerify" label="是否数据确认　1：是　0：否" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.isVerify"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入是否数据确认　1：是　0：否"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="verifyTime" label="确认时间systime(审核通过时间)" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.verifyTime"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入确认时间systime(审核通过时间)"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="isPay" label="是否支付" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.isPay"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入是否支付"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="payAuditId" label="支付审核流程ID" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.payAuditId"-->
<!--                  :maxLength="19"-->
<!--                  placeholder="请输入支付审核流程ID"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="payName" label="发放名称" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.payName"-->
<!--                  :maxLength="100"-->
<!--                  placeholder="请输入发放名称"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="isUniteTax" label="是否合并计税 1.是 0. 否" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.isUniteTax"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入是否合并计税 1.是 0. 否"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="24">-->
<!--              <el-form-item prop="remarks" label="备注" class="dc-el-form-item_MutilpleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.remarks"-->
<!--                  type="textarea"-->
<!--                  placeholder="请输入备注"-->
<!--                  rows="2"-->
<!--                  :maxLength="2000"-->
<!--                  class="dc-el-input_MutilpleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="submitUserId" label="提交审核人/客服（同收费单导入）" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.submitUserId"-->
<!--                  :maxLength="19"-->
<!--                  placeholder="请输入提交审核人/客服（同收费单导入）"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item-->
<!--                prop="auditState"-->
<!--                label="审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成"-->
<!--                class="dc-el-form-item_SingleInput"-->
<!--              >-->
<!--                <el-input-->
<!--                  v-model="editFormData.auditState"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="mustDay" label="应到账日" class="dc-el-form-item_NumberInput">-->
<!--                <number-input-->
<!--                  v-model="editFormData.mustDay"-->
<!--                  :min="0"-->
<!--                  :max="999999999999.99"-->
<!--                  :precision="2"-->
<!--                  placeholder="请输入应到账日"-->
<!--                  clearable-->
<!--                  class="dc-number-input_NumberInput"-->
<!--                ></number-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="rejectCause" label="驳回备注" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.rejectCause"-->
<!--                  :maxLength="2000"-->
<!--                  placeholder="请输入驳回备注"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="ebankTime" label="生成网银时间systime" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.ebankTime"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入生成网银时间systime"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="failDecimal" label="生成失败金额" class="dc-el-form-item_NumberInput">-->
<!--                <number-input-->
<!--                  v-model="editFormData.failDecimal"-->
<!--                  :min="0"-->
<!--                  :max="999999999999.99"-->
<!--                  :precision="2"-->
<!--                  placeholder="请输入生成失败金额"-->
<!--                  clearable-->
<!--                  class="dc-number-input_NumberInput"-->
<!--                ></number-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item-->
<!--                prop="payrollTypeBd"-->
<!--                label="工资单类型：0：不需要 1：保密工资单2：Email 3:保密+Email 4：信封5：保密+信封"-->
<!--                class="dc-el-form-item_SingleInput"-->
<!--              >-->
<!--                <el-input-->
<!--                  v-model="editFormData.payrollTypeBd"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入工资单类型：0：不需要 1：保密工资单2：Email 3:保密+Email 4：信封5：保密+信封"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="payrollStateBd" label="工资单情况：1:已打印 0：未打印" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.payrollStateBd"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入工资单情况：1:已打印 0：未打印"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="isDel" label="是否删除 0:正常　1:删除" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.isDel"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入是否删除 0:正常　1:删除"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="createUserId" label="创建人" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.createUserId"-->
<!--                  :maxLength="40"-->
<!--                  placeholder="请输入创建人"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="createDate" label="创建日期" class="dc-el-form-item_PickerTimeDate">-->
<!--                <el-date-picker-->
<!--                  v-model="editFormData.createDate"-->
<!--                  :style="{ width: '100%' }"-->
<!--                  placeholder="请选择创建日期"-->
<!--                  type="datetime"-->
<!--                  format="yyyy-MM-dd HH:mm:ss"-->
<!--                  value-format="yyyy-MM-dd HH:mm:ss"-->
<!--                  class="dc-el-date-picker_PickerTimeDate"-->
<!--                ></el-date-picker>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="lastUpdateUserId" label="最后修改人" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.lastUpdateUserId"-->
<!--                  :maxLength="40"-->
<!--                  placeholder="请输入最后修改人"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="lastUpdateDate" label="最后修改日期" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.lastUpdateDate"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入最后修改日期"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="auditTime" label="AUDIT_TIME" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.auditTime"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入AUDIT_TIME"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="genMoneyCode" label="到款编号" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.genMoneyCode"-->
<!--                  :maxLength="50"-->
<!--                  placeholder="请输入到款编号"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item-->
<!--                prop="paySalaryType"-->
<!--                label="发放工资类型：0:有到款编号，1无到款编号"-->
<!--                class="dc-el-form-item_SingleInput"-->
<!--              >-->
<!--                <el-input-->
<!--                  v-model="editFormData.paySalaryType"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入发放工资类型：0:有到款编号，1无到款编号"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item-->
<!--                prop="notCodeReason"-->
<!--                label="无到款编号原因：1:垫付申请2:固定垫付3:客户打款凭证4:北京打款预收款5:预收款6:其他"-->
<!--                class="dc-el-form-item_SingleInput"-->
<!--              >-->
<!--                <el-input-->
<!--                  v-model="editFormData.notCodeReason"-->
<!--                  :maxLength="128"-->
<!--                  placeholder="请输入无到款编号原因：1:垫付申请2:固定垫付3:客户打款凭证4:北京打款预收款5:预收款6:其他"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="claimUserId" label="工资到款认领人id" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.claimUserId"-->
<!--                  :maxLength="19"-->
<!--                  placeholder="请输入工资到款认领人id"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="claimDate" label="认领时间" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.claimDate"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入认领时间"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="realPayDay" label="实际发放日" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.realPayDay"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入实际发放日"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="applyPayTime" label="申请发放时间systime" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.applyPayTime"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入申请发放时间systime"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item-->
<!--                prop="isCsVerify"-->
<!--                label="是否客服数据确认　1：是　0：否(暂不用到)"-->
<!--                class="dc-el-form-item_SingleInput"-->
<!--              >-->
<!--                <el-input-->
<!--                  v-model="editFormData.isCsVerify"-->
<!--                  :maxLength="1"-->
<!--                  placeholder="请输入是否客服数据确认　1：是　0：否(暂不用到)"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item-->
<!--                prop="applyPayState"-->
<!--                label="工资发放申请状态 0：未申请 1：已申请  "-->
<!--                class="dc-el-form-item_SingleInput"-->
<!--              >-->
<!--                <el-input-->
<!--                  v-model="editFormData.applyPayState"-->
<!--                  :maxLength="1"-->
<!--                  placeholder="请输入工资发放申请状态 0：未申请 1：已申请  "-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="oldId" label="key" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.oldId"-->
<!--                  :maxLength="22"-->
<!--                  placeholder="请输入key"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="batchCodeQg" label="全国委托户导入批次号" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.batchCodeQg"-->
<!--                  :maxLength="50"-->
<!--                  placeholder="请输入全国委托户导入批次号"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="printDate" label="工资单打印日期" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.printDate"-->
<!--                  :maxLength="-1"-->
<!--                  placeholder="请输入工资单打印日期"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="rejectReason" label="驳回原因" class="dc-el-form-item_SingleInput">-->
<!--                <el-input-->
<!--                  v-model="editFormData.rejectReason"-->
<!--                  :maxLength="2"-->
<!--                  placeholder="请输入驳回原因"-->
<!--                  clearable-->
<!--                  autocomplete="new-password"-->
<!--                  class="dc-el-input_SingleInput"-->
<!--                ></el-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="6">-->
<!--              <el-form-item prop="rejectNumber" label="驳回人数" class="dc-el-form-item_NumberInput">-->
<!--                <number-input-->
<!--                  v-model="editFormData.rejectNumber"-->
<!--                  :min="0"-->
<!--                  :max="99999999"-->
<!--                  :precision="0"-->
<!--                  placeholder="请输入驳回人数"-->
<!--                  clearable-->
<!--                  class="dc-number-input_NumberInput"-->
<!--                ></number-input>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">确定</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
import { getDictTypeById } from "@/api/sys/dictType";
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getSalaryBatchById, saveSalaryBatch } from '@/api/salaryPay/salaryBatch'
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
        title: '申请发放模板'
      },
      dialogTitle: '申请发放模板',
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      payStatetion: [],
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    },
    applyStatetion: {
      type: Array
    },
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

      saveSalaryBatch(this.editFormData)
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
    switchEdit() {
      this.action = 'edit'
      this.dialogProps.title = `修改${this.dialogTitle}`
      this.initOptions(this.editFormData)
    },

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getSalaryBatchById(id)
          .then((responseData) => {
            let form = {}
            if (responseData.code == 100) {
              form = responseData.data
              let obj2 = this.payStatetion.find(it => it.value === form.payState)
              form.payState = obj2 ? obj2.name : form.payState
              // this.tableData = JSON.parse(JSON.stringify(arr))
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
    onDialogClose() {
      this.dialogProps.visible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量
    },
    getPayStatetion() {
      getDictTypeById("1923313087036954280").then(res => {
        this.payStatetion = res.data.dictItemList;
      });
    },
    initEditData(This) {
      return {
        saBatchCode: '', // 账单批次号
        billYearMonth: '', // 账单年月
        totalAmount: null, // 总金额
        payYearMonth: '', // 应发年月
        isVerify: '0', // 是否数据确认　1：是　0：否
        verifyTime: '', // 确认时间systime(审核通过时间)
        isPay: '', // 是否支付
        payTime: '', // 支付时间 systime（财务确认支付完成时间）
        payAuditId: '', // 支付审核流程ID
        auditUser: '', // 审核人（数据确认人）
        payName: '', // 发放名称
        taxMonth: '', // 计税年月
        isUniteTax: '1', // 是否合并计税 1.是 0. 否
        remarks: '', // 备注
        submitUserId: '', // 提交审核人/客服（同收费单导入）
        submitTime: '', // 提交审核时间
        applyState: '', // 审核申请状态：0：未申请 1：已申请
        auditState: '0', // 审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成
        mustDay: null, // 应到账日
        rejectCause: '', // 驳回备注
        ebankTime: '', // 生成网银时间systime
        totalDecimal: '', // 生成人数
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
  beforeMount: function() {
    this.getPayStatetion()
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `申请发放`
        this.editFormData = {
          ...this.initEditData(),
          ...(await this.getFormById(id))
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `申请发放`
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

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
