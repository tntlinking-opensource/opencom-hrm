<template>
  <el-row class="dc-container">
    <el-dialog
      v-on:open="onDialogOpen"
      v-on:close="onDialogClose"
      v-loading="loading"
      width="50%"
      :fullscreen="false"
      :title="dialogProps.title"
      :visible.sync="dialogProps.visible"
      :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog"
    >
      <el-row>
        <el-form
          ref="editForm"
          :model="editFormData"
          label-width="120px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="13">
              <el-form-item
                prop="laidtalk"
                label="离职面谈"
                :rules="[{ required: true, message: '离职面谈不能为空', trigger: 'change' }]"
                class="dc-el-form-item_RadioGroup"
              >
                <el-radio-group v-model="editFormData.laidtalk" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in laidtalkOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="falsify"
                label="违约金"
                :rules="[{ required: true, message: '违约金不能为空', trigger: 'change' }]"
                class="dc-el-form-item_RadioGroup"
              >
                <el-radio-group v-model="editFormData.falsify" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in falsifyOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="falsifymoney" :rules="editFormData.falsify === '1'?[{ required: true, message: '违约金金额不能为空', trigger: 'change' },{ pattern: new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/), message: '违约金额必须大于0', trigger: 'blur' }]:[]" label="违约金额" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.falsifymoney"
                  :disabled="editFormData.falsify !== '1'"
                  :min="0"
                  :max="99999"
                  :precision="2"
                  currency='CNY'
                  clearable
                  placeholder="请输入违约金金额"
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="other"
                label="其他退款"
                :rules="[{ required: true, message: '其他退款不能为空', trigger: 'change' }]"
                class="dc-el-form-item_RadioGroup"
              >
                <el-radio-group v-model="editFormData.other" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in otherOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="othermoney" :rules="editFormData.other === '1'?[{ required: editFormData.other === '1', message: '其他退款金额不能为空', trigger: 'change' },{ pattern: new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/), message: '其他退款金额必须大于0', trigger: 'blur' }]:[]" label="退款金额" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.othermoney"
                  :disabled="editFormData.other !== '1'"
                  :min="0"
                  :max="99999"
                  :precision="2"
                  currency='CNY'
                  clearable
                  placeholder="请输入退款金额"
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="damages"
                label="经济补偿"
                :rules="[{ required: true, message: '经济补偿不能为空', trigger: 'change' }]"
                class="dc-el-form-item_RadioGroup"
              >
                <el-radio-group v-model="editFormData.damages" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in damagesOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="annuity" :rules="editFormData.damages === '1' ?[{ required: true, message: '经济补偿不能为空', trigger: 'change' },{ pattern: new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/), message: '经济补偿必须大于0', trigger: 'blur' }]:[]" label="补偿金额" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.annuity"
                  :disabled="editFormData.damages !== '1'"
                  :min="0"
                  :max="99999"
                  :precision="2"
                  currency='CNY'
                  clearable
                  placeholder="请输入经济补偿"
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="humansign"
                label="签名"
                :rules="[{ required: true, message: '签名不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.humansign"
                  :maxLength="100"
                  placeholder="请输入人事确认签名"
                  disabled="true"
                  clearable
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="humandate"
                label="日期"
                :rules="[{ required: true, message: '日期不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.humandate"
                  :style="{ width: '100%' }"
                  placeholder="请选择人事签名日期"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="lastworkdate"
                label="最后工资结算日"
                :rules="[{ required: true, message: '最后工资结算日不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.lastworkdate"
                  :style="{ width: '100%' }"
                  placeholder="请选择最后工作日"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="lastsocial"
                label="最后社保缴纳月"
                :rules="[{ required: true, message: '最后社保缴纳月不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.lastsocial"
                  :style="{ width: '100%' }"
                  placeholder="请选择最后社保缴纳日期"
                  type="date"
                  format="yyyy-MM"
                  value-format="yyyy-MM"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="lastsalary"
                label="最后工资发放日"
                :rules="[{ required: true, message: '最后工资发放日不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.lastsalary"
                  :style="{ width: '100%' }"
                  placeholder="请选择最后工资发放日期"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </el-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import { getHrmUserLaidById, saveHrmUserLaid,resignationHrmUserLaid } from '@/api/hrm/hrmUserLaidCrud'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BaseUI,
  name: 'hrmUserLaid-form',
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
        title: '离职确认'
      },
      dialogTitle: '离职确认',
      // 选项变量
      // 离职面谈选项
      laidtalkOptions: [
        {
          label: '无',
          value: '0'
        },
        {
          label: '有',
          value: '1'
        }
      ],
      // 违约金选项
      falsifyOptions: [
        {
          label: '无',
          value: '0'
        },
        {
          label: '有',
          value: '1'
        }
      ],
      // 其他退款选项
      otherOptions: [
        {
          label: '无',
          value: '0'
        },
        {
          label: '有',
          value: '1'
        }
      ],
      // 经济补偿选项
      damagesOptions: [
        {
          label: '无',
          value: '0'
        },
        {
          label: '有',
          value: '1'
        }
      ],
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

      resignationHrmUserLaid(this.editFormData)
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
    

    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getHrmUserLaidById(id)
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
      this.editFormData.humansign = currentUser.name
    },
    initEditData(This) {
      return {
        laidtalk: null, // 离职面谈
        falsify: null, // 违约金
        falsifymoney: '', // 违约金额
        other: null, // 其他退款
        othermoney: '', // 退款金额
        damages: null, // 经济补偿
        annuity: '', // 补偿金额
        humansign: '', // 签名
        humandate: '', // 日期
        lastworkdate: '', // 最后工资结算日
        lastsocial: '', // 最后社保缴纳月
        lastsalary: '' // 最后工资发放日
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

        this.dialogProps.visible = true
      })
    })
  }
}
</script>
