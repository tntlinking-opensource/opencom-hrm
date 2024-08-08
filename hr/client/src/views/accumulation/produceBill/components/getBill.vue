<template>
  <el-dialog title="生成账单" :visible.sync="dialogVisible" width="400px">
    <div style='display: flex;align-items: center;'>
      <div style="width:80px">账单年月</div>
      <el-date-picker v-model="billMonth" :style="{ width: '100%' }" placeholder="请选择" type="month" format="yyyyMM"
        value-format="yyyyMM" class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitMonth">保 存</el-button>
      <el-button @click="dialogVisible = false">返 回</el-button>
    </span>
  </el-dialog>
</template>
<script>
import BaseUI from '@/views/components/baseUI'
import { generateBillDetails } from "@/api/socialSecurityFund/socialSecurityFund";
export default {
  extends: BaseUI,
  data() {
    return { dialogVisible: false, billMonth: '' }
  },
  methods: {
    submitMonth() {
      if (this.billMonth) {
        generateBillDetails({ billYearMonth: this.billMonth }).then(responseData => {
          if (responseData.code == 100) {
            this.$message.success('生成账单成功！')
            this.dialogVisible = false
            this.$emit('save-finished')
          } else {
            this.showMessage(responseData)
          }
        }).catch(error => {
          this.outputError(error)
        })
      } else {
        this.$message.warning('请先选择账单年月')
      }
    },
    init() {
      this.dialogVisible = true
      this.billMonth = ''
    }
  }
}
</script>
