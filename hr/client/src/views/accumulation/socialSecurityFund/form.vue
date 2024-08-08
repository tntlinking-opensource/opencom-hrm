<template>
  <van-row class="dm-container">
    <el-dialog v-on:open="onDialogOpen" v-on:close="onDialogClose" v-loading="loading" width="80%" :fullscreen="false"
      :title="dialogProps.title" :visible.sync="dialogProps.visible" :close-on-click-modal="false"
      class="dc-el-dialog_ElDialog">
      <el-row>
        <el-form ref="editForm" :model="editFormData" label-width="120px" :disabled="action == 'view'"
          class="dc-el-form_ElEditForm">
          <el-divider>员工信息</el-divider>
          <el-row>
            <el-col :span="8">
              <el-form-item prop="payCityConfigId" label="缴费城市"
                :rules="[{ required: true, message: '缴费城市不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.payCityConfigId" @change="changeCityName" :style="{ width: '100%' }"
                  placeholder="请选择" clearable filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in cityOptions" :key="index" :label="item.payCityName"
                    :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="employeeNo" label="员工工号"
                :rules="[{ required: true, message: '员工工号不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input @input="getUserName" v-model="editFormData.employeeNo" :maxLength="64" placeholder="请输入员工工号"
                  clearable autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="employeeName" label="员工姓名"
                :rules="[{ required: true, message: '员工姓名不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.employeeName" disabled :maxLength="64" placeholder="请输入员工姓名" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="deptName" label="部门" :rules="[{ required: true, message: '部门不能为空', trigger: 'blur' }]"
                class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.deptName" disabled :maxLength="18" placeholder="请输入部门" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="probationSalary" label="试用期薪资"
                :rules="[{ required: true, message: '试用期薪资不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input v-model="editFormData.probationSalary" :precision="2" :min="0" placeholder="请输入试用期薪资"
                  clearable autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="ragularSalary" label="转正薪资"
                :rules="[{ required: true, message: '转正薪资不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <number-input v-model="editFormData.ragularSalary" :precision="2" :min="0" placeholder="请输入试用期薪资"
                  clearable autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="entryTime" label="入职时间"
                :rules="[{ required: true, message: '入职时间不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-date-picker v-model="editFormData.entryTime" :style="{ width: '100%' }" placeholder="请选择" type="date"
                  format="yyyy-MM-dd" value-format="yyyy-MM-dd" class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-divider>产品信息</el-divider>
            <el-col :span="8">
              <el-form-item prop="productType" label="产品类型"
                :rules="[{ required: true, message: '产品类型不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.productType" :style="{ width: '100%' }" placeholder="请选择" clearable
                  @change="changeCity" filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in projectOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="defaultPayConfigId" label="产品名称"
                :rules="[{ required: true, message: '产品名称不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">

                <el-select v-model="editFormData.defaultPayConfigId" @change="changeProjectName"
                  :style="{ width: '100%' }" placeholder="请选择" clearable filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in productNameOptions" :key="index" :label="item.productName"
                    :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="discountStatus" label="所属状态"
                :rules="[{ required: true, message: '所属状态不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-select v-model="editFormData.discountStatus" :style="{ width: '100%' }" placeholder="请选择" clearable
                  filterable class="dc-el-select_SelectInput">
                  <el-option v-for="(item, index) in blongOptions" :key="index" :label="item.name"
                    :value="item.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="companyPayBase" label="公司缴纳基数"
                :rules="[{ required: true, message: '公司缴纳基数不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.companyPayBase" :maxLength="18" placeholder="请输入公司缴纳基数" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="personPayBase" label="个人缴费基数"
                :rules="[{ required: true, message: '个人缴费基数不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.personPayBase" :maxLength="18" placeholder="请输入个人缴费基数" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="companyPayScale" label="公司缴费比例"
                :rules="[{ required: true, message: '公司缴费比例不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.companyPayScale" :maxLength="18" disabled placeholder="请输入公司缴费比例"
                  clearable autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item prop="personPayScale" label="个人缴费比例"
                :rules="[{ required: true, message: '个人缴费比例不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.personPayScale" :maxLength="18" disabled placeholder="请输入个人缴费比例" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="effectStartDate" label="有效起始年月"
                :rules="[{ required: true, message: '有效起始年月不能为空', trigger: 'blur' }]" class="dc-el-form-item_SingleInput">
                <el-date-picker v-model="editFormData.effectStartDate" :style="{ width: '100%' }" placeholder="请选择"
                  type="month" format="yyyyMM" value-format="yyyyMM"
                  class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="effectEndDate" label="有效终止年月" class="dc-el-form-item_SingleInput">
                <el-date-picker v-model="editFormData.effectEndDate" :style="{ width: '100%' }" placeholder="请选择"
                  type="month" format="yyyyMM" value-format="yyyyMM"
                  class="dc-el-date-picker_PickerTimeDate"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="remark" label="备注" class="dc-el-form-item_SingleInput">
                <el-input v-model="editFormData.remark" :maxLength="255" placeholder="请输入备注" type="textarea" clearable
                  autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
              </el-form-item>
            </el-col>

          </el-row>
        </el-form>
      </el-row>
      <el-divider>产品信息</el-divider>
      <ux-grid column-key ref="table" :data="socalData" border v-on:header-dragend="onWidthChange($refs.table)"
        :tree-config="{ children: '', indent: 20, accordion: false, line: false, showIcon: true, iconOpen: '', iconClose: '' }"
        class="dc-ux-grid_QueryTable">
        <ux-table-column :field="socalColumns['129'].field" :title="socalColumns['129'].title"
          :width="socalColumns['129'].width" :visible="socalColumns['129'].visible"
          :params="{ sortId: '129', summary: false }" tree-node resizable min-width="180px" align="left"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="socalColumns['130'].field" :title="socalColumns['130'].title"
          :width="socalColumns['130'].width" :visible="socalColumns['130'].visible"
          :params="{ sortId: '130', summary: false }" tree-node resizable min-width="180px" align="left"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="socalColumns['131'].field" :title="socalColumns['131'].title"
          :width="socalColumns['131'].width" :visible="socalColumns['131'].visible"
          :params="{ sortId: '131', summary: false }" tree-node resizable min-width="180px" align="left"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="socalColumns['132'].field" :title="socalColumns['132'].title"
          :width="socalColumns['132'].width" :visible="socalColumns['132'].visible"
          :params="{ sortId: '132', summary: false }" tree-node resizable min-width="180px" align="left"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
        <ux-table-column :field="socalColumns['133'].field" :title="socalColumns['133'].title"
          :width="socalColumns['133'].width" :visible="socalColumns['133'].visible"
          :params="{ sortId: '133', summary: false }" tree-node resizable min-width="180px" align="left"
          header-align="center" sortable show-overflow class="dc-ux-table-column_ElTableColumn"></ux-table-column>
      </ux-grid>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onSubmit" type="primary" v-show="action != 'view'">保存</el-button>
        <el-button v-on:click="onDialogClose" v-if="action != 'view'">取消</el-button>
        <el-button v-on:click="onDialogClose" v-if="action == 'view'">关闭</el-button>
      </span>
    </el-dialog>
  </van-row>
</template>
<script>
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { listDefultConfig } from "@/api/socialSecurityFund/payCity";
/** 根据用户界面配置import组件 结束 */
import { getSocialSecurityFundById, saveSocialSecurityFund } from '@/api/socialSecurityFund/socialSecurityFund'
import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import {
  listSalaryArchivesPage,

} from '@/api/salaryArchives/salaryArchives'
export default {
  extends: BaseUI,
  name: 'socialSecurityFund-form',
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
        title: '比例维护'
      },
      dialogTitle: '比例维护',
      // 选项变量

      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: '',
      socalData: [],
      socalColumns: {
        129: {
          title: '社会平均工资',
          field: 'socialAvgAmount',
          visible: true,
          order: 0
        },
        130: {
          title: '个人最高基数',
          field: 'personHighBase',
          visible: true,
          order: 3
        },
        131: {
          title: '个人最低基数',
          field: 'personLowBase',
          visible: true,
          order: 1
        },
        132: {
          title: '公司最高基数',
          field: 'companyHighBase',
          visible: true,
          order: 2
        },
        133: {
          title: '公司最低基数',
          field: 'companyLowBase',
          visible: true,
          order: 4
        }
      },
      userInformation: {
        employeeName: '',
        deptName: ''
      },
      productNameOptions: [],
      tableObj: {
        socialAvgAmount: '',
        personHighBase: '',
        personLowBase: '',
        companyHighBase: '',
        companyLowBase: ''
      }
    }
  },
  props: ['cityOptions', 'projectOptions', 'blongOptions'],
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
      Object.assign(this.editFormData, this.tableObj)
      saveSocialSecurityFund(this.editFormData)
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
    changeCity() {
      this.productNameOptions = []
      this.editFormData.defaultPayConfigId = ''
      if (!this.editFormData.payCityConfigId) {
        this.$message.warning('请先选择缴费城市')
      } else {
        this.getList()
      }
    },
    getList() {
      let search = {
        // 查询条件   业务表设置的筛选条件
        params: [{// 城市查询参数
          "columnName": 'pay_city_id',
          "queryType": '=',
          "value": this.editFormData.payCityConfigId
        }, {// 产品类型参数
          "columnName": 'product_type',
          "queryType": '=',
          "value": this.editFormData.productType
        }],
        offset: 0,
        limit: 20,
        columnName: '', // 排序字段名
        order: '' // 排序
      }

      listDefultConfig(search).then(responseData => {
        if (responseData.code == 100) {
          this.productNameOptions = responseData.data.rows ? responseData.data.rows : []
        } else {
          this.productNameOptions = []
        }
      }).catch(error => {
        this.outputError(error)
      })
    },
    changeCityName(val) {
      let obj = this.cityOptions.find(item => item.id === val)
      this.editFormData.cityName = obj ? obj.payCityName : ''
    },
    changeProjectName(val) {
      let obj = this.productNameOptions.find(item => item.id === val)
      this.editFormData.productName = obj ? obj.productName : ''
      this.tableObj = {
        socialAvgAmount: obj ? obj.socialAvgAmount : '',
        personHighBase: obj ? obj.personHighBase : '',
        personLowBase: obj ? obj.personLowBase : '',
        companyHighBase: obj ? obj.companyHighBase : '',
        companyLowBase: obj ? obj.companyLowBase : '',
      }
      this.editFormData.companyPayScale = obj ? obj.unitScale : ''
      this.editFormData.personPayScale = obj ? obj.personScale : ''
      this.socalData = [this.tableObj]
    },
    getUserName(val) {
      let search = {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 5,
        columnName: '', // 排序字段名
        order: '' // 排序
      }
      if (val) {
        search.params.push({
          "columnName": "employee_no",
          "queryType": "=",
          "value": val
        })
      }
      listSalaryArchivesPage(search).then(res => {
        if (res.code == 100) {
          let arr = res.data.rows ? res.data.rows : []
          if (arr.length > 0) {
            this.userInformation = {
              employeeName: arr[0].employeeName,
              deptName: arr[0].deptName
            }
          } else {
            this.userInformation = {
              employeeName: '',
              deptName: ''
            }
          }
        } else {
          this.userInformation = {
            employeeName: '',
            deptName: ''
          }
        }
        this.editFormData.employeeName = this.userInformation.employeeName
        this.editFormData.deptName = this.userInformation.deptName
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
        getSocialSecurityFundById(id)
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
    },
    initEditData(This) {
      return {
        cityName: '', // 缴费城市
        payCityConfigId: '',//城市名称id
        employeeNo: '', // 员工工号
        employeeName: '', // 员工姓名
        deptName: '', // 部门
        probationSalary: '', // 试用期薪资
        ragularSalary: '', // 转正薪资
        entryTime: '', // 入职时间
        productType: '', // 产品类型
        productName: '', // 产品名称
        defaultPayConfigId: '',  //产品名称id
        discountStatus: '', // 所属状态
        companyPayBase: '', // 公司缴纳基数
        companyPayScale: '', // 公司缴费比例
        personPayBase: '', // 个人缴费基数
        personPayScale: '', // 个人缴费比例
        effectStartDate: '', // 有效起始年月
        effectEndDate: '', // 有效终止年月
        remark: '', // 备注
        companyPayType: '', // 公司缴费方式
        companyPayAmount: '', // 公司缴费额度
        companyPayMonth: '', // 公司缴费月份
        personPayType: '', // 个人缴费方式
        personPayAmount: '', // 个人缴费额度
        personPayMonth: '',// 个人缴费月份
        enable: '0'
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
        this.getList()
        this.tableObj = {
          socialAvgAmount: this.editFormData.socialAvgAmount,
          personHighBase: this.editFormData.personHighBase,
          personLowBase: this.editFormData.personLowBase,
          companyHighBase: this.editFormData.companyHighBase,
          companyLowBase: this.editFormData.companyLowBase,
        }
        this.socalData = [this.tableObj]
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
        this.getList()
        this.tableObj = {
          socialAvgAmount: this.editFormData.socialAvgAmount,
          personHighBase: this.editFormData.personHighBase,
          personLowBase: this.editFormData.personLowBase,
          companyHighBase: this.editFormData.companyHighBase,
          companyLowBase: this.editFormData.companyLowBase,
        }
        this.socalData = [this.tableObj]
        this.dialogProps.visible = true
      })
      this.$on('openAddDialog', function () {
        this.action = 'add'
        this.dialogProps.title = `添加${this.dialogTitle}`
        this.editFormData = this.initEditData()
        this.initOptions(this.editFormData)
        this.tableObj = {
          socialAvgAmount: '',
          personHighBase: '',
          personLowBase: '',
          companyHighBase: '',
          companyLowBase: '',
        }
        this.socalData = [this.tableObj]
        this.dialogProps.visible = true
      })

    })
  }
}
</script>
