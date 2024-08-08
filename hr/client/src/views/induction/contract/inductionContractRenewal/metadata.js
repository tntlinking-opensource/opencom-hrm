const conditions = [
  {
    name: 'id',
    comments: '主键',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'code',
    comments: '合同编号',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'theme',
    comments: '合同主题',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'name',
    comments: '姓名',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'gender',
    comments: '性别',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'DictItem',
      fieldId: 'id',
      tableId: '1538459003414151556',
      apiPath: 'sys/dictItem',
      filter: ''
    }
  },

  {
    name: 'phone',
    comments: '手机号',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'id_number',
    comments: '身份证号码',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'email',
    comments: '邮箱',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'address',
    comments: '居住地地址',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'entry_time',
    comments: '入职时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'probation_month',
    comments: '试用期',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'DictItem',
      fieldId: 'id',
      tableId: '1538459003414151556',
      apiPath: 'sys/dictItem',
      filter: ''
    }
  },

  {
    name: 'employee_level',
    comments: '员工职级',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'DictItem',
      fieldId: 'id',
      tableId: '1538459003414151556',
      apiPath: 'sys/dictItem',
      filter: ''
    }
  },

  {
    name: 'employee_number',
    comments: '工号',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'tech_position',
    comments: '员工职位',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'b_job',
    comments: '从事工作',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'contract_start_time',
    comments: '合同开始时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'contract_end_time',
    comments: '合同结束时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'probation_end_time',
    comments: '试用期结束时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'company_id',
    comments: '公司',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'Company',
      fieldId: 'id',
      tableId: '1538459003414151556',
      apiPath: 'org/company',
      filter: "{'columnName':'id', 'queryType': '=', 'value': currentUser.company.id=='0' ? '': currentUser.company.id}"
    }
  },

  {
    name: 'department_id',
    comments: '部门',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'Department',
      fieldId: 'id',
      tableId: '1538459003414151556',
      apiPath: 'org/department',
      filter: ''
    }
  },

  {
    name: 'company_email',
    comments: '公司邮箱',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'contract_sign_unit',
    comments: '合同签约单位',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'labor_legalmana',
    comments: '合同签约方法人',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'report_address',
    comments: '甲方住所地',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'full_wages',
    comments: '全额工资（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'probation_wages',
    comments: '试用期工资（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'basic_wages',
    comments: '基本工资（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'probation_basic_wages',
    comments: '试用期基本工资（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'secrecy_reward',
    comments: '保密奖（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'system_reward',
    comments: '制度奖（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'basic_performance',
    comments: '基本绩效（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'floating_performance',
    comments: '浮动绩效（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'probation_basic_performance',
    comments: '试用期基本绩效（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'probation_floating_performance',
    comments: '试用期浮动绩效（元）',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'contract_date',
    comments: '合同生成时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'contract_start_date',
    comments: '合同发起时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'contract_status',
    comments: '合约状态',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'DictItem',
      fieldId: 'value',
      tableId: '1538459003414151556',
      apiPath: 'sys/dictItem',
      filter: ''
    }
  },

  {
    name: 'sign_status',
    comments: '签署状态',
    tag: 'el-select',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'in', label: '在列表' },
      { value: 'not in', label: '不在列表' }
    ],
    attribute: {
      labelField: 'name',
      className: 'DictItem',
      fieldId: 'value',
      tableId: '1538459003414151556',
      apiPath: 'sys/dictItem',
      filter: ''
    }
  },

  {
    name: 'remarks',
    comments: '备注信息',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'create_by',
    comments: '创建者',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'create_date',
    comments: '创建时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  },

  {
    name: 'update_by',
    comments: '更新者',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  },

  {
    name: 'update_date',
    comments: '更新时间',
    tag: 'el-date-picker',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { type: 'datetime', 'value-format': 'yyyy-MM-dd HH:mm:ss' }
  }
]

const metadata = [
  {
    id: '1538459003414151556',
    schemeId: '1538459003414151554',
    name: '合约续签',
    conditionPanel: conditions,
    type: 'main',
    dataRules: []
  }
]
export default metadata
