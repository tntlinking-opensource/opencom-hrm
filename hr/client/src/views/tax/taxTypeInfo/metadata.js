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
    name: 'tax_name',
    comments: '税率表名称',
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
    name: 'tax_type',
    comments: '类型',
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
    name: 'national_tariff',
    comments: '固定税率',
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
    name: 'exp_deduction',
    comments: '费用扣除',
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
    name: 'month_avg',
    comments: '月平均金额',
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
    name: 'month_tax_income',
    comments: '月纳税所得额 ',
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
    name: 'deduct_rate',
    comments: '扣除费率',
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
    name: 'update_by',
    comments: '修改者',
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
    comments: '创建日期',
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
    name: 'update_date',
    comments: '更新日期',
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
    id: '1641034040897357276',
    schemeId: '1641034040897357274',
    name: '税率类型',
    conditionPanel: conditions,
    type: 'main',
    dataRules: []
  }
]
export default metadata
