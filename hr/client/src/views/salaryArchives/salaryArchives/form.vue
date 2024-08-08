<template>
  <van-row class="dm-container">
    <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" width="90%" :fullscreen="false"
      :title="dialogProps.title" :visible.sync="dialogProps.visible" :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog">
      <el-row>
        <el-form ref="editForm" :model="editFormData" label-width="120px" :disabled="action == 'view'"
          class="dc-el-form_ElEditForm">
          <el-divider>员工信息</el-divider>
          <el-row>
            <el-col :span="8">
              <el-form-item prop="employeeNo" label="员工工号"
                :rules="[{ required: true, message: '员工工号不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.employeeNo" :maxLength="30" placeholder="请输入员工工号" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="employeeName" label="员工姓名"
                :rules="[{ required: true, message: '员工姓名不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.employeeName" :maxLength="30" placeholder="请输入员工姓名" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="sex" label="性别" :rules="[{ required: true, message: '性别不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.sex" :style="{ width: '100%' }" placeholder="请选择" clearable filterable
                  class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in sexOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>

              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item prop="idCardType" label="证件类型"
                :rules="[{ required: true, message: '证件类型不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.idCardType" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in idOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="idCardNo" label="证件号码" :rules="[{ required: true, message: '证件号码不能为空', trigger: 'blur' }, {
                pattern: editFormData.idCardType === 'identityCard' ? new RegExp(`^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$`) : '',
                message: '请输入正确的身份证号',
                trigger: 'blur'
              }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.idCardNo" :maxLength="30" placeholder="请输入证件号码" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="birthDate" label="出生日期" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.birthDate" :maxLength="24" placeholder="请输入出生日期" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="phoneNumber" label="手机号"
                :rules="[{ required: true, message: '手机号不能为空', trigger: 'blur' }, { pattern: new RegExp(`^1[34578]\\d{9}$`), message: '请输入正确的手机号格式', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.phoneNumber" :maxLength="11" placeholder="请输入手机号" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="nationality" label="国籍" class="dc-el-form-item_SingleInput"
                :rules="[{ required: true, message: '国籍不能为空', trigger: 'blur' }]">
                <el-input v-model="editFormData.nationality" :maxLength="10" placeholder="请输入国籍" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="deptId" label="所属部门" :rules="[{ required: true, message: '所属部门不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.deptId" :style="{ width: '100%' }" placeholder="请选择" clearable filterable
                  class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in departmentOptions" :key="index" :label="item.name"
                    :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="postName" label="岗位" :rules="[{ required: true, message: '岗位不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.postName" :maxLength="30" placeholder="请输入岗位" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="employeeType" label="员工类型"
                :rules="[{ required: true, message: '员工类型不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.employeeType" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in userTypeOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="employeeBelongs" label="员工归属" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.employeeBelongs" :maxLength="30" placeholder="请输入员工归属" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="employeeStatus" label="员工状态" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.employeeStatus" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in statusOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="entryTime" label="入职时间"
                :rules="[{ required: true, message: '入职时间不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-date-picker v-model="editFormData.entryTime" :style="{ width: '100%' }" placeholder="请选择入职时间"
                  type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"></el-date-picker>

              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="probationMonth" label="试用期"
                :rules="[{ required: true, message: '试用期不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.probationMonth" @change="changeProbationMonth" :style="{ width: '100%' }"
                  placeholder="请选择" clearable filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in probationOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="probationEndTime" label="试用期结束 "
                :rules="[{ required: true, message: '试用期结束 不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-date-picker v-model="editFormData.probationEndTime" :style="{ width: '100%' }" placeholder="请选择入职时间"
                  type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="leaveTime" label="离职时间" class="dc-el-form-item_SingleInput">
                <el-date-picker v-model="editFormData.leaveTime" :style="{ width: '100%' }" placeholder="请选择入职时间"
                  type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                  class="dc-el-date-picker_PickerTimeDate"></el-date-picker>

              </el-form-item>
            </el-col>
            <el-divider>银行卡信息</el-divider>
            <el-col :span="8">
              <el-form-item prop="bankCardType" label="银行类型" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.bankCardType" :maxLength="30" placeholder="请输入银行类型" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="bankName" label="开户银行"
                :rules="[{ required: true, message: '开户银行不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.bankName" :maxLength="30" placeholder="请输入开户银行" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="bankNo" label="银行卡号" :rules="[{ required: true, message: '银行卡号不能为空', trigger: 'blur' }, { pattern: new RegExp(/^([1-9]{1})(\d{15}|\d{16}|\d{18})$/), message: '请输入正确的银行卡号', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.bankNo" :maxLength="30" placeholder="请输入银行卡号" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="bankUserName" label="开户姓名"
                :rules="[{ required: true, message: '开户姓名不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.bankUserName" :maxLength="30" placeholder="请输入开户姓名" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="bankCityName" label="账号市区名" class="dc-el-form-item_SingleInput"
                :rules="[{ required: true, message: '账号市区名不能为空', trigger: 'blur' }]">
                <el-input v-model="editFormData.bankCityName" :maxLength="30" placeholder="请输入账号市区名" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-divider>税率表信息</el-divider>

            <el-col :span="8">
              <el-form-item prop="salaryRateTable" label="工资薪金税率表"
                :rules="[{ required: true, message: '工资薪金税率表不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.salaryRateTable" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in salaryOptions" :key="index" :label="item.taxName"
                    :value="item.id"></el-option></el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="yearEndRateTable" label="年终奖税率表"
                :rules="[{ required: true, message: '年终奖税率表不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.yearEndRateTable" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in yearsOptions" :key="index" :label="item.taxName"
                    :value="item.id"></el-option></el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="rewardRateTable" label="劳务报酬税率表"
                :rules='[{ required: true, message: "劳务报酬税率表不能为空", trigger: "blur" }]'
                class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.rewardRateTable" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in laobrOptions" :key="index" :label="item.taxName"
                    :value="item.id"></el-option></el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="leaveCompensationRateTable" label="离职补偿税率表"
                :rules="[{ required: true, message: '离职补偿税率表不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.leaveCompensationRateTable" :style="{ width: '100%' }" placeholder="请选择"
                  clearable filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in dimissionOptions" :key="index" :label="item.taxName"
                    :value="item.id"></el-option></el-select>
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
  </van-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */
import {
  getSalaryArchivesById,
  saveSalaryArchives
} from '@/api/salaryArchives/salaryArchives'
import BaseUI from '@/views/components/baseUI'
import { listTaxTypeInfoPage } from '@/api/tax/taxTypeInfo'
import OperationIcon from '@/components/OperationIcon'
import { getDictTypeById } from "@/api/sys/dictType";
export default {
  extends: BaseUI,
  name: 'salaryArchives-form',
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
        title: '薪酬档案'
      },
      dialogTitle: '薪酬档案',
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */
      // 窗口操作类型 view/edit/add
      action: '',

      laobrOptions: [],
      yearsOptions: [],
      dimissionOptions: [],
      salaryOptions: [],
      idOptions: []
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    },
    departmentOptions: {
      type: Array
    },
    statusOptions: {
      type: Array
    },
    sexOptions: {
      type: Array
    },

    userTypeOptions: {
      type: Array
    },
    probationOptions: {
      type: Array
    }
  },
  computed: {},
  methods: {
    onSubmit() {
      this.$refs['editForm'].validate(valid => {
        if (valid) {
          this.doSave()
        } else {
          return false
        }
      });
    },
    doSave() {
      this.setLoad()
      saveSalaryArchives(this.editFormData).then(responseData => {
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
      }).catch(error => {
        this.outputError(error)
      })
    },


    getFormById(id) {
      this.setLoad()
      return new Promise((resolve) => {
        getSalaryArchivesById(id).then((responseData) => {
          let form = {}
          if (responseData.code == 100) {
            form = responseData.data
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
          resolve(form)
        }).catch((error) => {
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
    changeProbationMonth(val) {
      if (this.editFormData.entryTime) {
        this.editFormData.probationEndTime = this.changeTime(
          this.editFormData.entryTime,
          val
        );
      } else {
        this.showMessage({ type: "warning", msg: "请先选择入职时间" });
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
        let str = `${Yeara}-${Monthb > 9 ? Monthb : "0" + Monthb}-${Datec > 9 ? Datec : "0" + Datec
          }`;
        return str;
      } else {
        return entryTime;
      }
    },
    initEditData(This) {
      return {
        employeeNo: '', // 员工工号
        employeeName: '', // 员工姓名
        sex: '', // 性别，选项：男、女
        idCardType: '', // 证件类型,选项：身份证、护照
        idCardNo: '', // 证件号码
        birthDate: '', // 出生日期
        phoneNumber: '', // 手机号
        nationality: '', // 国籍
        deptId: '', // 部门
        employeeType: '', // 员工类型，选项：正式员工、实习生
        employeeBelongs: '', // 员工归属
        employeeStatus: '', // 员工状态，选项：在职、离职
        entryTime: '', // 入职时间
        probationMonth: '', // 试用期
        probationEndTime: '', // 试用期结束
        leaveTime: '', // 离职时间
        postName: '', // 岗位
        bankCardType: '', // 银行卡类型
        bankName: '', // 开户行
        bankNo: '', // 银行卡号
        bankUserName: '', // 开户姓名
        bankCityName: '', // 账号市区名
        salaryRateTable: '', // 工资薪金税率表
        yearEndRateTable: '', // 年终奖税率表
        rewardRateTable: '', // 劳务报酬税
        leaveCompensationRateTable: '', // 离职补偿税率表
      }
    },
    getSalary() {
      let search = {
        params: [
          {
            "columnName": "tax_type",
            "queryType": "=",
            "value": "1"
          },
        ],
        offset: 0,
        limit: 1000,
        columnName: '', // 排序字段名
        order: '' // 排序
      }

      listTaxTypeInfoPage(search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.salaryOptions = responseData.data.rows ? responseData.data.rows : []
          }
        })
    },
    getIdType() {
      getDictTypeById("1660844079343558656").then(res => {
        this.idOptions = res.data.dictItemList;
      });
    },
    getLaobrSalary() {
      let search = {
        params: [
          {
            "columnName": "tax_type",
            "queryType": "=",
            "value": "3"
          },
        ],
        offset: 0,
        limit: 1000,
        columnName: '', // 排序字段名
        order: '' // 排序
      }
      listTaxTypeInfoPage(search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.laobrOptions = responseData.data.rows ? responseData.data.rows : []
          }
        })
    },
    getYearBonus() {
      let search = {
        params: [
          {
            "columnName": "tax_type",
            "queryType": "=",
            "value": "2"
          },
        ],
        offset: 0,
        limit: 1000,
        columnName: '', // 排序字段名
        order: '' // 排序
      }

      listTaxTypeInfoPage(search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.yearsOptions = responseData.data.rows ? responseData.data.rows : []
          }
        })
    },
    getDimission() {
      let search = {
        params: [
          {
            "columnName": "tax_type",
            "queryType": "=",
            "value": "4"
          },
        ],
        offset: 0,
        limit: 1000,
        columnName: '', // 排序字段名
        order: '' // 排序
      }

      listTaxTypeInfoPage(search)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dimissionOptions = responseData.data.rows ? responseData.data.rows : []
          }
        })
    }
  },
  watch: {
    'editFormData.idCardNo'(val) {
      if (val.length === 18) {
        this.editFormData.birthDate = val.slice(6, 14)
      }
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openViewDialog', async function (id) {
        this.action = 'view'
        this.dialogProps.title = `查看${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
        }
        this.initOptions(this.editFormData)
        this.dialogProps.visible = true
      })

      this.$on('openEditDialog', async function (id) {
        this.action = 'edit'
        this.dialogProps.title = `修改${this.dialogTitle}`
        this.editFormData = {
          ...this.initEditData(),
          ...await this.getFormById(id)
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
      this.getSalary()
      this.getLaobrSalary()
      this.getYearBonus()
      this.getDimission()
      this.getIdType()
    })
  }
}
</script>
