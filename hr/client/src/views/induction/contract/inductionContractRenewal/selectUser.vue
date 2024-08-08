<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="30%"
      title="选择续签人员"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-form ref="editForm" :model="editFormData" label-width="110px" class="dc-el-form_ElEditForm">
        <el-row>
          <el-col :span="24">
            <el-form-item
              prop="contract.id"
              label="续签人员"
              :rules="[{ required: true, message: '续签人员不能为空', trigger: 'change' }]"
              class="dc-el-form-item_SelectInput"
            >
              <el-select
                v-model="editFormData.contract"
                :style="{ width: '100%' }"
                placeholder="请选择续签人员"
                clearable
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option v-for="(item, index) in contractOptions" :key="index" :label="item.induction.name" :value="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary">保存</el-button>
        <el-button v-on:click="onDialogClose">取消</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>

<script>
import { validatenull } from '@/utils/validate'
import { listInductionBatchContractAll } from '@/api/induction/contract/inductionBatchContract'
import BaseUI from '@/views/components/baseUI'
import moment from "moment";
export default {
  extends: BaseUI,
  name: "select-user",
  data() {
    return {
      // 对话框属性变量
      dialogVisible: false,
      editFormData: this.initEditData(),
      contractOptions: [], // 续签人员
    }
  },
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.dialogVisible = false
          this.$emit('select-finished', this.editFormData.contract.id)
        } else {
          return false
        }
      })
    },
    onDialogClose() {
      this.dialogVisible = false
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    initEditData() {
      return {
        contract: {
          id: null,
          code: '',
          theme: ''
        }
      }
    },
    initOptions(This) {
      const date = moment().add(30, 'days').format("YYYY-MM-DD")
      let search_List = {
        params: [{ columnName: 'contract_end_time', queryType: '<=', value: date }]
      }
      // filter条件
      this.setLoad()
      // filter条件
      search_List.params.push.apply(search_List.params, [])
      // 数据权限: 发起合约（合约信息附属表）tsm_induction_batch_contract
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1538459003414151400')
      listInductionBatchContractAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.contractOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      })
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openSelectUserDialog', async function () {
        console.log('-----')
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.dialogVisible = true
      })
    })
  }
}
</script>

<style scoped>

</style>
