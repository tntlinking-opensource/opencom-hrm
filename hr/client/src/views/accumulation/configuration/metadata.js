
      
      
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
        
          
            
      
      
          
        
          
            
      
      
          
        
      
      
          
        
          
            const conditions = [
            
              
              {
                name: 'id',
                comments: '标识',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'employee_no',
                comments: '员工工号',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'employee_name',
                comments: '员工姓名',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'sex',
                comments: '性别，选项：男、女',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'id_card_type',
                comments: '证件类型,选项：身份证、护照',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'id_card_no',
                comments: '证件号码',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'birth_date',
                comments: '出生日期',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'phone_number',
                comments: '手机号',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'nationality',
                comments: '国籍',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'dept',
                comments: '部门',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'employee_type',
                comments: '员工类型，选项：正式员工、实习生',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'employee_belongs',
                comments: '员工归属',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'employee_status',
                comments: '员工状态，选项：在职、离职',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'entry_time',
                comments: '入职时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'probation_month',
                comments: '试用期',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'probation_end_time',
                comments: '试用期结束 ',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'leave_time',
                comments: '离职时间',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'post_name',
                comments: '岗位',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'bank_card_type',
                comments: '银行卡类型',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'bank_name',
                comments: '开户行',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'bank_no',
                comments: '银行卡号',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'bank_user_name',
                comments: '开户姓名',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'bank_city_name',
                comments: '账号市区名',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'salary_rate_table',
                comments: '工资薪金税率表',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'year_end_rate_table',
                comments: '年终奖税率表',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'reward_rate_table',
                comments: '劳务报酬税',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'leave_compensation_rate_table',
                comments: '离职补偿税率表',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'create_by',
                comments: '创建者',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'update_by',
                comments: '修改者',
                tag: 'el-input',
                queryType: 'like',
                operations: [{"value":"like","label":"包含"},{"value":"not like","label":"不包含"},{"value":"=","label":"等于"},{"value":"<>","label":"不等于"}],
                attribute: {}
              },
              
              {
                name: 'create_date',
                comments: '创建日期',
                tag: 'el-date-picker',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {"type":"datetime","value-format":"yyyy-MM-dd HH:mm:ss"}
              },
              
              {
                name: 'update_date',
                comments: '更新日期',
                tag: 'el-date-picker',
                queryType: '=',
                operations: [{"value":"=","label":"等于"},{"value":"<>","label":"不等于"},{"value":"between","label":"介于"},{"value":"not between","label":"不介于"}],
                attribute: {"type":"datetime","value-format":"yyyy-MM-dd HH:mm:ss"}
              },
              
            
            ]
            
      
      
          
        
          
            
      
        
      
      
          
        
          
            
      
        
          
            
      
        
          
            
      
      
          
        
      
      
          
        
      
      
          
        
          
            
      
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
        
          
            
      
        
          
            
      
        
          
            
      
      
          
        
      
      
          
        
          
            
      
      
          
        
      
      
          
        
          
            
      
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
          
            
      
      
          
        
      
      
          
        
      
      
          
        
      
      
          
        
          
            
      
      
          
        
      
      
const metadata = [
	{
    id: '1641034040897357711',
    schemeId: '1641034040897357709',
    name: '薪酬档案信息',
    conditionPanel: conditions,
    type: 'main',
    dataRules:[]
  }
]
export default metadata