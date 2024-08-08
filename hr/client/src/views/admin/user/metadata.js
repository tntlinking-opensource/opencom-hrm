const parentConditions = [
  {
    name: 'id',
    comments: '标识',
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
    comments: '名称',
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
    comments: '编号',
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
    name: 'full_name',
    comments: '全称',
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
    name: 'ids',
    comments: '全标识',
    tag: 'el-input',
    queryType: 'like',
    operations: [
      { value: 'like', label: '包含' },
      { value: 'not like', label: '不包含' },
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: {}
  }
]

const conditions = [
  {
    name: 'id',
    comments: '标识',
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
      tableId: '1289059804542828547',
      apiPath: 'org/company'
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
      tableId: '1289059804542828547',
      apiPath: 'org/department'
    }
  },

  {
    name: 'name',
    comments: '名称',
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
    name: 'login_name',
    comments: '登录账号',
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
    name: 'login_password',
    comments: '密码',
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
    name: 'is_locked',
    comments: '禁用',
    tag: 'el-switch',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' }
    ],
    attribute: { 'active-value': true, 'inactive-value': false }
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
    name: 'email',
    comments: '邮箱地址',
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
    id: '1289059804542828547',
    schemeId: '1289059804542828545',
    name: '用户',
    conditionPanel: conditions,
    type: 'main',
    dataRules: []
  },

  {
    id: '1287908822026887245',
    schemeId: '',
    name: '公司',
    conditionPanel: parentConditions,
    type: 'parent',
    dataRules: []
  }
]
export default metadata
