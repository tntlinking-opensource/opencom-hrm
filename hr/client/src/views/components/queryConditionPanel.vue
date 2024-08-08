<template>
  <div class="condition-panel" v-loading='loading'>
    <el-row :key='"ConditionPanel" + idx' v-for='idx in [0, 1, 2]' :gutter='5'>
      <el-col :span='11'>
        <el-col :span='3'>
          <span v-if='idx == 0' style='text-align: center;display:block;'>一组</span>
          <el-select v-else v-model='groupOne[idx].logic' placeholder='请选择逻辑符' clearable>
              <el-option v-for='item in logics' :key='item.value' :label='item.label' :value='item.value'></el-option>
          </el-select>
        </el-col>
        <el-col :span='5'>
          <el-select v-model='groupOne[idx].column' value-key='name' @change='onColumnChange(groupOne[idx].column, groupOne, idx)' clearable placeholder='请选择字段'>
              <el-option v-for="col in conditionColumns" :key='col.name' :label='col.comments' :value='col'></el-option>
          </el-select>
        </el-col>
        <el-col :span='4'>
          <el-select v-model='groupOne[idx].queryType' @change='onQueryTypeChange(groupOne[idx].queryType, groupOne, idx)' clearable placeholder='请选择操作符'>
            <el-option v-for='opr in groupOne[idx].operations' :key='opr.value' :label='opr.label' :value='opr.value'></el-option>
          </el-select>
        </el-col>
        <el-col :span='12'>
          <div v-if="groupOne[idx].column && groupOne[idx].column.tag == 'el-input'">
            <el-input v-model='groupOne[idx].value' :clearable='true' placeholder='请输入条件值' ></el-input>
          </div>
          <div v-else-if="groupOne[idx].column && groupOne[idx].column.tag == 'number-input'">
            <number-range-input v-if="groupOne[idx].queryType == 'between' || groupOne[idx].queryType == 'not between'" v-model='groupOne[idx].value'
              :type="groupOne[idx].column.javaType" :precision="groupOne[idx].attribute ? groupOne[idx].attribute[':precision'] : 0"></number-range-input>
            <number-input v-else v-model="groupOne[idx].value" :currency='groupOne[idx].column.javaType == "java.math.BigDecimal" ? "CNY":null'
              :precision="groupOne[idx].attribute ? groupOne[idx].attribute[':precision'] : 0" :max="groupOne[idx].attribute ? groupOne[idx].attribute[':max'] : 999999" :min="groupOne[idx].attribute ? groupOne[idx].attribute[':min'] : 0" placeholder='请输入条件值'></number-input>
          </div>
          <div v-else-if="groupOne[idx].column && groupOne[idx].column.tag == 'el-date-picker'">
            <data-range-picker v-if="groupOne[idx].queryType == 'between' || groupOne[idx].queryType == 'not between'" v-model='groupOne[idx].value' :type='groupOne[idx].attribute ? groupOne[idx].attribute.type : "date"'></data-range-picker>
            <el-date-picker v-else v-model='groupOne[idx].value' placeholder='请选择日期' :type='groupOne[idx].attribute ? groupOne[idx].attribute.type : "date"' :value-format='groupOne[idx].attribute ? groupOne[idx].attribute["value-format"] : "yyyy-MM-dd"' clearable></el-date-picker>
          </div>
          <div v-else-if="groupOne[idx].column && groupOne[idx].column.tag == 'el-time-picker'">
            <el-time-picker v-if="groupOne[idx].queryType == 'between' || groupOne[idx].queryType == 'not between'" is-range v-model='groupOne[idx].value' clearable range-separator='至' start-placeholder='开始时间' end-placeholder='结束时间' placeholder='选择时间范围' value-format='HH:mm:ss'></el-time-picker>
            <el-time-picker v-else  v-model='groupOne[idx].value' placeholder='请选择时间' :value-format='groupOne[idx].attribute ? groupOne[idx].attribute["value-format"] : "HH:mm:ss"' clearable></el-time-picker>
          </div>
          <div v-else-if="groupOne[idx].column && groupOne[idx].column.tag == 'el-switch'">
            <el-switch v-model='groupOne[idx].value' active-color='#13ce66' inactive-color='#dbdfe6' :active-value='groupOne[idx].attribute ? groupOne[idx].attribute["active-value"] : 1' :inactive-value='groupOne[idx].attribute ? groupOne[idx].attribute["inactive-value"] : 0'></el-switch>
          </div>
          <!--<div v-else-if="groupOne[idx].column && groupOne[idx].column.showType && groupOne[idx].column.showType.value == 'IncNumber'">
            <number-range-input v-if="groupOne[idx].queryType == 'between' || groupOne[idx].queryType == 'not between'" v-model='groupOne[idx].value'></number-range-input>
            <el-input-number v-else v-model='groupOne[idx].value' controls-position='right' :controls='false' placeholder='请输入条件值'></el-input-number>
          </div>-->
          <!-- <div v-else-if="groupOne[idx].column && groupOne[idx].column.showType && (groupOne[idx].column.showType.value == 'InputNumber' || groupOne[idx].column.showType.value == 'IncNumber')">
            <number-range-input v-if="groupOne[idx].queryType == 'between' || groupOne[idx].queryType == 'not between'" v-model='groupOne[idx].value' :type="groupOne[idx].column.javaType.value" :precision="groupOne[idx].column.decimalLenth"></number-range-input>
            <number-input v-else v-model="groupOne[idx].value" :currency='groupOne[idx].column.javaType.value == "java.math.BigDecimal" ? "CNY":null' :precision="groupOne[idx].column.decimalLenth" placeholder='请输入条件值'></number-input>
          </div> -->
          <div v-else-if="groupOne[idx].column && groupOne[idx].column.tag == 'el-select'">
            <el-select v-if="groupOne[idx].queryType == 'in' || groupOne[idx].queryType == 'not in'" v-model='groupOne[idx].value' :value-key='groupOne[idx].column.attribute.fieldId' clearable multiple filterable placeholder='请选择条件值'>
                <el-option v-for="(item, index) in pp[idx]" :key='index' :label='item[groupOne[idx].column.attribute.labelField]' :value='item[groupOne[idx].column.attribute.fieldId]'></el-option>
            </el-select>
            <el-select v-else v-model='groupOne[idx].value' :value-key='groupOne[idx].column.attribute.fieldId'   filterable clearable placeholder='请选择条件值'>
               <el-option v-for="(item, index) in pp[idx]" :key='index' :label='item[groupOne[idx].column.attribute.labelField]' :value='item[groupOne[idx].column.attribute.fieldId]'></el-option>
            </el-select>
          </div>
          <div v-else-if="groupOne[idx].column && groupOne[idx].column.tag == 'el-cascader'">
            <el-cascader v-if="groupOne[idx].queryType == 'in' || groupOne[idx].queryType == 'not in'" v-model='groupOne[idx].value' :options="pp[idx]" :props='{value: groupOne[idx].column.attribute.fieldId, label: groupOne[idx].column.attribute.labelField, checkStrictly: true, emitPath: false, multiple: true}' filterable clearable placeholder='请选择条件值'></el-cascader>
            <el-cascader v-else v-model='groupOne[idx].value' :options="pp[idx]" :props='{value: groupOne[idx].column.attribute.fieldId, label: groupOne[idx].column.attribute.labelField, checkStrictly: true, emitPath: false}' filterable clearable placeholder='请选择条件值'></el-cascader>
          </div>
          <el-input v-else v-model='groupOne[idx].value' :clearable='true' placeholder='请输入条件值' ></el-input>
        </el-col>
    </el-col>


      <el-col :span='2' style="padding: 0 15px">
        <div v-if='idx == 1'>
          <el-select v-model='groupLogic'  placeholder='请选择组间逻辑符' clearable>
              <el-option v-for='lg in logics' :key='lg.value' :label='lg.label' :value='lg.value'></el-option>
          </el-select>
        </div>
        <div v-else>&nbsp;</div>
      </el-col>


      <el-col :span='11'>
          <el-col :span='3'>
            <span v-if='idx == 0' style='text-align: center;display:block;'>二组</span>
            <el-select v-else v-model='groupTwo[idx].logic' placeholder='请选择逻辑符' clearable>
                <el-option v-for='item in logics' :key='item.value' :label='item.label' :value='item.value'></el-option>
            </el-select>
          </el-col>
          <el-col :span='5'>
            <el-select v-model='groupTwo[idx].column' value-key='name' @change='onColumnChange(groupTwo[idx].column, groupTwo, idx)'  clearable placeholder='请选择字段'>
                <el-option v-for="col in conditionColumns" :key='col.name' :label='col.comments' :value='col'></el-option>
            </el-select>
          </el-col>
          <el-col :span='4'>
            <el-select v-model='groupTwo[idx].queryType' @change='onQueryTypeChange(groupTwo[idx].queryType, groupTwo, idx)' clearable placeholder='请选择操作符'>
              <el-option v-for='opr in groupTwo[idx].operations' :key='opr.value' :label='opr.label' :value='opr.value'></el-option>
            </el-select>
          </el-col>
          <el-col :span='12'>
            <div v-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'el-input'">
            <el-input v-model='groupTwo[idx].value' :clearable='true' placeholder='请输入条件值' ></el-input>
          </div>
          <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'number-input'">
            <number-range-input v-if="groupTwo[idx].queryType == 'between' || groupTwo[idx].queryType == 'not between'" v-model='groupTwo[idx].value'
              :type="groupTwo[idx].column.javaType" :precision="groupTwo[idx].attribute ? groupTwo[idx].attribute[':precision'] : 0"></number-range-input>
            <number-input v-else v-model="groupTwo[idx].value" :currency='groupTwo[idx].column.javaType == "java.math.BigDecimal" ? "CNY":null'
              :precision="groupTwo[idx].attribute ? groupTwo[idx].attribute[':precision'] : 0" :max="groupTwo[idx].attribute ? groupTwo[idx].attribute[':max'] : 999999" :min="groupTwo[idx].attribute ? groupTwo[idx].attribute[':min'] : 0" placeholder='请输入条件值'></number-input>
          </div>
          <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'el-date-picker'">
            <data-range-picker v-if="groupTwo[idx].queryType == 'between' || groupTwo[idx].queryType == 'not between'" v-model='groupTwo[idx].value' :type='groupTwo[idx].attribute ? groupTwo[idx].attribute.type : "date"'></data-range-picker>
            <el-date-picker v-else v-model='groupTwo[idx].value' placeholder='请选择日期' :type='groupTwo[idx].attribute ? groupTwo[idx].attribute.type : "date"' :value-format='groupTwo[idx].attribute ? groupTwo[idx].attribute["value-format"] : "yyyy-MM-dd"' clearable></el-date-picker>
          </div>
          <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'el-time-picker'">
            <el-time-picker v-if="groupTwo[idx].queryType == 'between' || groupTwo[idx].queryType == 'not between'" is-range v-model='groupTwo[idx].value' clearable range-separator='至' start-placeholder='开始时间' end-placeholder='结束时间' placeholder='选择时间范围' value-format='HH:mm:ss'></el-time-picker>
            <el-time-picker v-else  v-model='groupTwo[idx].value' placeholder='请选择时间' :value-format='groupTwo[idx].attribute ? groupTwo[idx].attribute["value-format"] : "HH:mm:ss"' clearable></el-time-picker>
          </div>
          <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'el-switch'">
            <el-switch v-model='groupTwo[idx].value' active-color='#13ce66' inactive-color='#dbdfe6' :active-value='groupTwo[idx].attribute ? groupTwo[idx].attribute["active-value"] : 1' :inactive-value='groupTwo[idx].attribute ? groupTwo[idx].attribute["inactive-value"] : 0'></el-switch>
          </div>
          <!--<div v-else-if="groupTwo[idx].column && groupTwo[idx].column.showType && groupTwo[idx].column.showType.value == 'IncNumber'">
            <number-range-input v-if="groupTwo[idx].queryType == 'between' || groupTwo[idx].queryType == 'not between'" v-model='groupTwo[idx].value'></number-range-input>
            <el-input-number v-else v-model='groupTwo[idx].value' controls-position='right' :controls='false' placeholder='请输入条件值'></el-input-number>
          </div>-->
          <!-- <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.showType && (groupTwo[idx].column.showType.value == 'InputNumber' || groupTwo[idx].column.showType.value == 'IncNumber')">
            <number-range-input v-if="groupTwo[idx].queryType == 'between' || groupTwo[idx].queryType == 'not between'" v-model='groupTwo[idx].value' :type="groupTwo[idx].column.javaType.value" :precision="groupTwo[idx].column.decimalLenth"></number-range-input>
            <number-input v-else v-model="groupTwo[idx].value" :currency='groupTwo[idx].column.javaType.value == "java.math.BigDecimal" ? "CNY":null' :precision="groupTwo[idx].column.decimalLenth" placeholder='请输入条件值'></number-input>
          </div> -->
          <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'el-select'">
            <el-select v-if="groupTwo[idx].queryType == 'in' || groupTwo[idx].queryType == 'not in'" v-model='groupTwo[idx].value' :value-key='groupTwo[idx].column.attribute.fieldId' clearable multiple filterable placeholder='请选择条件值'>
                <el-option v-for="(item, index) in pp[idx + 3]" :key='index' :label='item[groupTwo[idx].column.attribute.labelField]' :value='item[groupTwo[idx].column.attribute.fieldId]'></el-option>
            </el-select>
            <el-select v-else v-model='groupTwo[idx].value' :value-key='groupTwo[idx].column.attribute.fieldId'   filterable clearable placeholder='请选择条件值'>
               <el-option v-for="(item, index) in pp[idx + 3]" :key='index' :label='item[groupTwo[idx].column.attribute.labelField]' :value='item[groupTwo[idx].column.attribute.fieldId]'></el-option>
            </el-select>
          </div>
          <div v-else-if="groupTwo[idx].column && groupTwo[idx].column.tag == 'el-cascader'">
            <el-cascader v-if="groupTwo[idx].queryType == 'in' || groupTwo[idx].queryType == 'not in'" v-model='groupTwo[idx].value' :options="pp[idx + 3]" :props='{value: groupTwo[idx].column.attribute.fieldId, label: groupTwo[idx].column.attribute.labelField, checkStrictly: true, emitPath: false, multiple: true}' filterable clearable placeholder='请选择条件值'></el-cascader>
            <el-cascader v-else v-model='groupTwo[idx].value' :options="pp[idx + 3]" :props='{value: groupTwo[idx].column.attribute.fieldId, label: groupTwo[idx].column.attribute.labelField, checkStrictly: true, emitPath: false}' filterable clearable placeholder='请选择条件值'></el-cascader>
          </div>
            <el-input v-else v-model='groupTwo[idx].value' :clearable='true' placeholder='请输入条件值' ></el-input>
          </el-col>
        </el-col>
    </el-row>
  </div>
</template>

<script>
import BaseUI from './baseUI'
import DataRangePicker from '@/components/DcDataRangePicker'
import NumberRangeInput from '@/components/NumberRangeInput'
import { listDcTableColumnAll } from '@/api/dc/dcTableColumn'
import { validatenull } from '../../utils/validate'
export default {
  extends: BaseUI,
  name: 'queryConditionPanel',
  components: {
    DataRangePicker,
    NumberRangeInput
  },
  data() {
    return {
      pp: {
        "0": [],  "1": [], "2": [], "3": [], "4": [], "5": []
      },  // 对象字段的值选择项
      columns: [],  // 字段列表
      logics: [
        {value: 'AND', label: '且'},
        {value: 'OR', label: '或'}
      ],
      groupOne: [
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: [], attribute: {}},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: [], attribute: {}},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: [], attribute: {}}
      ],
      groupTwo: [
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: [], attribute: {}},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: [], attribute: {}},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: [], attribute: {}}
      ],
      groupLogic: 'OR'    // 两组连接逻辑运算符
    }
  },
  props: {
    value: {
      type: Object,
      default: () => {return {}}
    },
    tableId: {
      type: String
    },
    schemeId: {
      type: String
    },
    // 不作为条件的字段
    exclude: {
      type: String,
      default: () => {return null}
    },
    curNode: {
      type: Object,
      default: () => {return {}}
    },
    routerId: {
      type: String
    },
    conditionColumns: {
      type: Array,
      default: () => {return []}
    }
  },
  watch:{
    value(val, oldVal) {
      if(val && val != oldVal) {
        this.groupOne = this.value['groupOne']
        this.groupTwo = this.value['groupTwo']
        this.groupLogic = this.value['groupLogic']
      }
    },
    groupLogic(val, oldVal) {
      if(val!=oldVal){
        this.value['groupLogic'] = val    // 非对象，需要监控修改
      }
    },
    curNode(val, oldVal) {
      if(val == oldVal) {
        return
      }
      if(this.exclude) {
        this.watchGroupHandler(this.exclude, this.groupOne)
        this.watchGroupHandler(this.exclude, this.groupTwo)
      }
    },
    'groupOne.0.column': {
      handler(val, oldVal) {
        if(val && val != oldVal) {
          this.doInitOptions(val, this.groupOne, 0)
        }
      }
    },
    'groupOne.1.column': {
      handler(val, oldVal) {
        if(val && val != oldVal) {
          this.doInitOptions(val, this.groupOne, 1)
        }
      }
    },
    'groupOne.2.column': {
      handler(val, oldVal) {
        if(val && val != oldVal) {
          this.doInitOptions(val, this.groupOne, 2)
        }
      }
    },
    'groupTwo.0.column': {
      handler(val, oldVal) {
        if(val && val != oldVal) {
          this.doInitOptions(val, this.groupTwo, 0)
        }
      }
    },
    'groupTwo.1.column': {
      handler(val, oldVal) {
        if(val && val != oldVal) {
          this.doInitOptions(val, this.groupTwo, 1)
        }
      }
    },
    'groupTwo.2.column': {
      handler(val, oldVal) {
        if(val && val != oldVal) {
          this.doInitOptions(val, this.groupTwo, 2)
        }
      }
    },
    'groupOne.0.value': {
      handler(val, oldVal) {
        if(val) {
          if(this.groupOne[0].column) {
              this.watchValueHandler(this.groupOne, 0)
          }
        }
      }
    },
    'groupOne.1.value': {
      handler(val, oldVal) {
        if(val) {
          if(this.groupOne[1].column) {
              this.watchValueHandler(this.groupOne, 1)
          }
        }
      }
    },
    'groupOne.2.value': {
      handler(val, oldVal) {
        if(val) {
          if(this.groupOne[2].column) {
              this.watchValueHandler(this.groupOne, 2)
          }
        }
      }
    },
    'groupTwo.0.value': {
      handler(val, oldVal) {
        if(val) {
          if(this.groupTwo[0].column) {
              this.watchValueHandler(this.groupTwo, 0)
          }
        }
      }
    },
    'groupTwo.1.value': {
      handler(val, oldVal) {
        if(val) {
          if(this.groupTwo[1].column) {
              this.watchValueHandler(this.groupTwo, 1)
          }
        }
      }
    },
    'groupTwo.2.value': {
      handler(val, oldVal) {
        if(val) {
          if(this.groupTwo[2].column) {
              this.watchValueHandler(this.groupTwo, 2)
          }
        }
      }
    }
  },
  methods: {
    watchValueHandler(gpOfValue, idx) {
      let interColumnName = gpOfValue[idx].column.name

      let group = this.groupOne
      for(var i= 0 ; i < group.length; i++) {
        if(group[i].column && group[i].column.interaction) {
          let colNames = group[i].column.interaction.split(",")
          for(var j = 0; j < colNames.length; j++) {
            if(colNames[j] == interColumnName) { //
              if((group == gpOfValue && group[Math.max(idx, i)].logic == 'AND')
                || this.groupLogic == 'AND') {
                group[i].value = null
                let [This, operates] = this.constructModel(colNames, group, i)
                this.initOptions(This, operates, group[i].column, group == this.groupOne ? i : i + 3)
              }
            }
          }
        }
      }

      group = this.groupTwo
      for(var i= 0 ; i < group.length; i++) {
        if(group[i].column && group[i].column.interaction) {
          let colNames = group[i].column.interaction.split(",")
          for(var j = 0; j < colNames.length; j++) {
            if(colNames[j] == interColumnName) { //
              if((group == gpOfValue && group[Math.max(idx, i)].logic == 'AND')
                || this.groupLogic == 'AND') {
                group[i].value = null
                let [This, operates] = this.constructModel(colNames, group, i)
                this.initOptions(This, operates, group[i].column, group == this.groupOne ? i : i + 3)
              }
            }
          }
        }
      }
    },
    // interColumnName 联动列字段名
    watchGroupHandler(interColumnName, group) {
      for(var i= 0 ; i < group.length; i++) {
        if(group[i].column && group[i].column.interaction) {
          let colNames = group[i].column.interaction.split(",")
          for(var j = 0; j < colNames.length; j++) {
            if(colNames[j] == interColumnName) { // 有联动变量是依赖于左边选择框的
              group[i].value = null
              let [This, operates] = this.constructModel(colNames, group, i)
              this.initOptions(This, operates, group[i].column, group == this.groupOne ? i : i+ 3)
            }
          }
        }
      }
    },
    // colNames 联动变量数组;
    async constructModel(colNames, group, idx) {
      let model = {}
      let operates = {}
      let colIdx = group == this.groupOne ? idx : idx + 3 // 需要查询的选项的下标
      for(var i=0; i < colNames.length; i++) {
        /** 此时的 column 可能还没值 会报错 */
        let column = await this.init() && this.columns.find(item => {
          return item.name == colNames[i]
        })

        if (colNames[i] == this.exclude) {
          model[column.simpleJavaField] = this.curNode
          operates[colNames[i] + colIdx] = '='
        } else {
          // 同一组中
          model[column.simpleJavaField] = {}
          model[column.simpleJavaField][column.attribute.fieldId] = null
          let find = false
          for(var j = 0; j < group.length; j++) {
            if(j == idx) {
              continue
            }

            if (group[j].column && colNames[i] == group[j].column.name) {
              if(group[Math.max(idx, j)].logic == 'AND') {
                model[column.simpleJavaField][column.attribute.fieldId] = group[j].value
                operates[colNames[i] + colIdx] = group[j].queryType
                find = true
                break
              }
            }
          }

          // 同一组中未找到, 且组之间为AND
          if(!find && this.groupLogic == 'AND') {
            let gp = group == this.groupOne ? this.groupTwo : this.groupOne
            for(var j = 0; j < gp.length; j++) {
              if (gp[j].column && colNames[i] == gp[j].column.name) {
                model[column.simpleJavaField][column.attribute.fieldId] = gp[j].value
                operates[colNames[i] + colIdx] = gp[j].queryType
                break
              }
            }
          }
        }
      }
      return [model, operates]
    },
    init() {
      this.setLoad()
      return new Promise((resolve, reject) => {
        let search_List = {
          params: [{ columnName: 'dc_table_id', queryType: '=', value: this.tableId }]
        }
        listDcTableColumnAll(search_List).then(responseData => {
          if(responseData.code == 100) {
            this.columns = responseData.data
            resolve(true)
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch(error => {
          this.outputError(error)
        })
      })
    },
    onQueryTypeChange(queryType, group, index) {
      // 根据操作符 和显示类型确定初始值，有待测试  变化时同步修改dataPermistion.vue onQueryTypeChange(rule)方法
      if(group[index].column && (group[index].column.tag == "el-select" || group[index].column.tag == "el-cascader") && (queryType == "in" || queryType == "not in")) {
        group[index].value = []
      } else {
        group[index].value = null
      }
    },
    onColumnChange(column, group, index) {
      // 重置查询条件值
      group[index].value = null
      group[index].queryType = ''
      group[index].operations = []
      group[index].attribute = {}

      if (!validatenull(column)) {
        group[index].attribute = column.attribute
        group[index].operations = column.operations
        group[index].queryType = column.queryType
      }
    },
    async doInitOptions(column, group, index) {
      if(column && column.tag && (column.tag == 'el-select' || column.tag == 'el-cascader')) {
        let This = {}
        // TODO 联动处理
        // let operates = {}
        // if(column.interaction) {
        //   let colNames = column.interaction.split(",")
        //   let [model, opr] = await this.constructModel(colNames, group, index)
        //   This = model
        //   operates = opr
        // }
        this.initOptions(This, column, group == this.groupOne ? index : index + 3)
      }
    },
    // This curNode； column 选择的字段； index 0~2 groupOne的，3~5 groupTwo
    initOptions(This, column, index) {
      // 选项列表数据
      let fnOption = ''
      if(column.tag == 'el-select') {
        fnOption = require('@/api/' + column.attribute.apiPath)['list' + column.attribute.className + 'All']
      } else {
        fnOption = require('@/api/' + column.attribute.apiPath)['tree' + column.attribute.className]
      }
      // 联动参数
      let parameters = []
      if(column.attribute.filter) {
        eval('parameters = [' + column.attribute.filter + ']')
      }
      // 数据权限
      let search = {params: parameters, offset: 0, limit: 10, orderby: ''}
      // 权限
      if(column.attribute.tableId) {
        this.pushDataPermissions(search.params, this.$route.meta.routerId, column.attribute.tableId)
      }
      this.pp[index] = []
      fnOption(search).then(responseData => {
        if(responseData.code == 100) {
          this.pp[index] = responseData.data
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    reset() {
      this.groupOne = [
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: []},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: []},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: []}
      ]
      this.groupTwo = [
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: []},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: []},
        {logic: 'AND', column: null, queryType: '', constant: false, value: null, operations: []}
      ]
      this.groupLogic = 'OR'

      this.$emit('input', {
        groupOne: this.groupOne,
        groupTwo: this.groupTwo,
        groupLogic: this.groupLogic
      })

    }
  },
  mounted(){
    this.$nextTick(() => {
      if(this.value) {
        this.groupOne = this.value['groupOne']
        this.groupTwo = this.value['groupTwo']
        this.groupLogic = this.value['groupLogic']
      } else {  // 若数据为空，进行初始化
        this.reset()
      }

      this.$on('reset', function() {
        this.reset()
      })

      this.$on('init', function() {
       // this.init()
      })
     // this.init()
    })
  }
}
</script>


<style scoped lang="scss">
  .condition-panel {
    .el-row {
      margin-bottom: 5px;
      span {
        font-size: 12px;
      }
    }
  }
.el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 100%;
}

.el-select {
    display: inline-block;
    position: relative;
    width: 100%;
}
</style>
