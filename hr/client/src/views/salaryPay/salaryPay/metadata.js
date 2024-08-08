const conditions = [
  {
    name: 'COMPANY_ID',
    comments: '分公司ID',
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
    name: 'ID',
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
    name: 'SA_BATCH_CODE',
    comments: '薪资批次号',
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
    name: 'CUSTOMER_NAME',
    comments: '客户名称',
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
    name: 'CUSTOMER_CODE',
    comments: '客户编码',
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
    name: 'CUSTOMER_ID',
    comments: '客户id',
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
    name: 'SA_CLASS_ID',
    comments: '薪资类别ID',
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
    name: 'PAY_YEAR_MONTH',
    comments: '应发年月',
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
    name: 'IS_VERIFY',
    comments: '是否数据确认　1：是　0：否',
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
    name: 'VERIFY_TIME',
    comments: '确认时间systime(审核通过时间)',
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
    name: 'IS_PAY',
    comments: '是否支付',
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
    name: 'PAY_TIME',
    comments: '支付时间 systime（财务确认支付完成时间）',
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
    name: 'PAY_AUDIT_ID',
    comments: '支付审核流程ID',
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
    name: 'AUDIT_USER',
    comments: '审核人（数据确认人）',
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
    name: 'PAY_NAME',
    comments: '发放名称',
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
    name: 'BILL_YEAR_MONTH',
    comments: '账单年月 ',
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
    name: 'TAX_MONTH',
    comments: '计税年月',
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
    name: 'IS_UNITE_TAX',
    comments: '是否合并计税 1.是 0. 否',
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
    name: 'REMARKS',
    comments: '备注',
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
    name: 'SUBMIT_USER_ID',
    comments: '提交审核人/客服（同收费单导入）',
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
    name: 'SUBMIT_TIME',
    comments: '提交审核时间',
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
    name: 'APPLY_STATE',
    comments: '审核申请状态：0：未申请 1：已申请  ',
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
    name: 'AUDIT_STATE',
    comments: '审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成',
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
    name: 'MUST_DAY',
    comments: '应到账日',
    tag: 'number-input',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { ':min': 0, ':max': 999999999999, ':precision': 2 }
  },

  {
    name: 'REJECT_CAUSE',
    comments: '驳回备注',
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
    name: 'EBANK_TIME',
    comments: '生成网银时间systime',
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
    name: 'TOTAL_DECIMAL',
    comments: '生成人数',
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
    name: 'TOTAL_AMOUNT',
    comments: '总金额',
    tag: 'number-input',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { ':min': 0, ':max': 999999999999, ':precision': 2 }
  },

  {
    name: 'FAIL_DECIMAL',
    comments: '生成失败金额',
    tag: 'number-input',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { ':min': 0, ':max': 999999999999, ':precision': 2 }
  },

  {
    name: 'PAYROLL_TYPE_BD',
    comments: '工资单类型：0：不需要 1：保密工资单2：Email 3:保密+Email 4：信封5：保密+信封',
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
    name: 'PAYROLL_STATE_BD',
    comments: '工资单情况：1:已打印 0：未打印',
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
    name: 'IS_DEL',
    comments: '是否删除 0:正常　1:删除',
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
    name: 'CREATE_USER_ID',
    comments: '创建人',
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
    name: 'CREATE_DATE',
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
    name: 'LAST_UPDATE_USER_ID',
    comments: '最后修改人',
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
    name: 'LAST_UPDATE_DATE',
    comments: '最后修改日期',
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
    name: 'APPLY_PAY_DATE',
    comments: '申请发放日期',
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
    name: 'AUDIT_TIME',
    comments: 'AUDIT_TIME',
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
    name: 'GEN_MONEY_CODE',
    comments: '到款编号',
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
    name: 'PAY_SALARY_TYPE',
    comments: '发放工资类型：0:有到款编号，1无到款编号',
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
    name: 'NOT_CODE_REASON',
    comments: '无到款编号原因：1:垫付申请2:固定垫付3:客户打款凭证4:北京打款预收款5:预收款6:其他',
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
    name: 'CLAIM_USER_ID',
    comments: '工资到款认领人id',
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
    name: 'CLAIM_DATE',
    comments: '认领时间',
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
    name: 'REAL_PAY_DAY',
    comments: '实际发放日',
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
    name: 'PAY_STATE',
    comments: '发放状态：0：待发放 1：已发放(财务确认支付完成)',
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
    name: 'APPLY_PAY_TIME',
    comments: '申请发放时间systime',
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
    name: 'APPLY_PAY_USER_ID',
    comments: '申请工资发放人',
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
    name: 'IS_CS_VERIFY',
    comments: '是否客服数据确认　1：是　0：否(暂不用到)',
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
    name: 'APPLY_PAY_STATE',
    comments: '工资发放申请状态 0：未申请 1：已申请  ',
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
    name: 'OLD_ID',
    comments: 'key',
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
    name: 'BATCH_CODE_QG',
    comments: '全国委托户导入批次号',
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
    name: 'PRINT_DATE',
    comments: '工资单打印日期',
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
    name: 'REJECT_REASON',
    comments: '驳回原因',
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
    name: 'REJECT_NUMBER',
    comments: '驳回人数',
    tag: 'number-input',
    queryType: '=',
    operations: [
      { value: '=', label: '等于' },
      { value: '<>', label: '不等于' },
      { value: 'between', label: '介于' },
      { value: 'not between', label: '不介于' }
    ],
    attribute: { ':min': 0, ':max': 999999999999, ':precision': 0 }
  }
]

const metadata = [
  {
    id: '1740772218121938768',
    schemeId: '1740772218121938766',
    name: '员工薪资发放批次表',
    conditionPanel: conditions,
    type: 'main',
    dataRules: []
  }
]
export default metadata
