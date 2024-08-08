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
          label-width="100px"
          :disabled="action == 'view'"
          class="dc-el-form_ElEditForm"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item
                prop="equipment"
                label="设备归还"
                :rules="[{ required: true, message: '设备归还不能为空', trigger: 'change' }]"
                class="dc-el-form-item_RadioGroup"
              >
                <el-radio-group v-model="editFormData.equipment" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in equipmentOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12"> 
              <el-form-item prop="equipmentmoney"  :rules="editFormData.equipment=== '1' ?[{ required: true, message: '设备损坏扣款不能为空', trigger: 'change' },{ pattern: new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/), message: '设备损坏扣款必须大于0', trigger: 'blur' }]:[]" label="扣款" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.equipmentmoney"
                  :disabled=" editFormData.equipment !== '1'"
                  :min="0"
                  :max="99999"
                  :precision="2"
                  currency='CNY'
                  clearable
                  placeholder="请输入设备损坏扣款"
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="card"
                label="胸卡归还"
                :rules="[{ required: true, message: '胸卡归还不能为空', trigger: 'change' }]"
                class="dc-el-form-item_RadioGroup"
              >
                <el-radio-group v-model="editFormData.card" class="dc-el-radio-group_RadioGroup">
                  <el-radio v-for="(item, index) in cardOptions" :key="index" :label="item.value">
                    {{ item.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="cardmoney" :rules="editFormData.card === '1'?[{ required: true, message: '胸卡归还不能为空', trigger: 'change' },{ pattern: new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/), message: '胸卡归还扣款必须大于0', trigger: 'blur' }]:[]" label="扣款" class="dc-el-form-item_SingleInput">
                <number-input
                  v-model="editFormData.cardmoney"
                  :disabled="editFormData.card !== '1'"
                  :min="0"
                  :max="99999"
                  :precision="2"
                  currency='CNY'
                  clearable
                  placeholder="请输入胸卡归还扣款"
                  class="dc-number-input_NumberInput"
                ></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="adminsign"
                label="签名"
                :rules="[{ required: true, message: '签名不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput"
              >
                <el-input
                  v-model="editFormData.adminsign"
                  :maxLength="100"
                  disabled="true"
                  placeholder="请输入行政签名"
                  class="dc-el-input_SingleInput"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item
                prop="admindate"
                label="日期"
                :rules="[{ required: true, message: '日期不能为空', trigger: 'change' }]"
                class="dc-el-form-item_PickerTimeDate"
              >
                <el-date-picker
                  v-model="editFormData.admindate"
                  :style="{ width: '100%' }"
                  placeholder="请选择行政签名日期"
                  type="datetime"
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
import { getHrmUserLaidById, saveHrmUserLaid,processingHrmUserLaid } from '@/api/hrm/hrmUserLaidCrud'
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
        title: '事务确认'
      },
      dialogTitle: '事务确认',
      // 选项变量
      // 设备归还选项
      equipmentOptions: [
        {
          label: '未损坏',
          value: '0'
        },
        {
          label: '有损坏',
          value: '1'
        }
      ],
      // 胸卡归还选项
      cardOptions: [
        {
          label: '归还',
          value: '1'
        },
        {
          label: '遗失',
          value: '0'
        },
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

      processingHrmUserLaid(this.editFormData)
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
      //设置签名为申请人名字
      this.editFormData.adminsign = currentUser.name
    },
    initEditData(This) {
      return {
        equipment: '', // 设备归还
        equipmentmoney: '', // 扣款
        card: null, // 胸卡归还
        cardmoney: '', // 扣款
        adminsign: '', // 签名
        admindate: '' // 日期
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
        this.dialogProps.title = `${this.dialogTitle}`
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
