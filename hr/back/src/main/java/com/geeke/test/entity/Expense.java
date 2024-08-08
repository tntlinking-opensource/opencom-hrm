package com.geeke.test.entity;

import com.geeke.admin.entity.User;
import javax.validation.constraints.NotNull;
import com.geeke.sys.entity.DictItem;
import java.util.List;
import com.google.common.collect.Lists;

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
public class Expense extends WfDataEntity<Expense> {
	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("申请人")
	private User applier;      // 申请人
	@ApiModelProperty("金额")
	private Float amount;		// 金额

	@ApiModelProperty("子表列表")
	private List<ExpenseDetail> expenseDetailList = Lists.newArrayList();		// 子表列表
	
	public Expense() {
		super();
	}

	public Expense(String id){
		super(id);
	}
	

	@NotNull(message="申请人不能为空")
    public User getApplier() {
        return applier;
    }

    public void setApplier(User applier) {
        this.applier = applier;
    }
    
	@NotNull(message="金额不能为空")
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	
	
	public List<ExpenseDetail> getExpenseDetailList() {
		return expenseDetailList;
	}

	public void setExpenseDetailList(List<ExpenseDetail> expenseDetailList) {
		this.expenseDetailList = expenseDetailList;
	}
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "test_expense";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "4016";
    }
    
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
            return true;
    }
}