package com.geeke.test.entity;

import com.geeke.test.entity.Expense;
import javax.validation.constraints.NotNull;
import com.geeke.sys.entity.DictItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.wf.persistence.WfDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 请假管理Entity
 * @author lys
 * @version 2020-08-05
 */

@ApiModel(description = "请假管理Entity")
public class ExpenseDetail extends WfDataEntity<ExpenseDetail> {
	@ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1L;
	@ApiModelProperty("费用")
    private Expense expense;      // 费用
	@ApiModelProperty("金额")
    private Float amount;		// 金额

	
	public ExpenseDetail() {
		super();
	}

	public ExpenseDetail(String id){
		super(id);
	}
	

	public ExpenseDetail(Expense expense){
		this.expense = expense;
	}

	@NotNull(message="费用不能为空")
    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }
    
	@NotNull(message="金额不能为空")
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "test_expense_detail";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "4017";
    }
    
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
            return true;
    }
}