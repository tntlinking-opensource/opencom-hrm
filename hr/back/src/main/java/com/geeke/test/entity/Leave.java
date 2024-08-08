package com.geeke.test.entity;

import com.geeke.admin.entity.User;
import javax.validation.constraints.NotNull;
import com.geeke.sys.entity.DictItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.wf.persistence.WfDataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 请假管理Entity
 * @author lys
 * @version 2022-01-28
 */

@ApiModel(description = "请假管理Entity")
public class Leave extends WfDataEntity<Leave> {
	@ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 4015L;
	@ApiModelProperty("请假人")
    private User leaver;      // 请假人
	@ApiModelProperty("休假天数")
    private Integer days;		// 休假天数

	
	public Leave() {
		super();
	}

	public Leave(String id){
		super(id);
	}
	

	@NotNull(message="请假人不能为空")
    public User getLeaver() {
        return leaver;
    }

    public void setLeaver(User leaver) {
        this.leaver = leaver;
    }
    
	@NotNull(message="休假天数不能为空")
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	
	
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "test_leave";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "4015";
    }
    
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
            return true;
    }
}