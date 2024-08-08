package com.geeke.wf.entity;

import com.geeke.admin.entity.User;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.geeke.common.persistence.DataEntity;
/**
 * 我的草稿Entity
 * @author lys
 * @version 2021-04-27
 */
@ApiModel(description = "我的草稿Entity")
public class WfDraft extends DataEntity<WfDraft> {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("申请人")
	private User applyer;      // 申请人
	@ApiModelProperty("流程id")
	private String procdefId;		// 流程id
	@ApiModelProperty("流程名称")
	private String procdefName;		// 流程名称
	@ApiModelProperty("表单数据")
	private String formData;		// 表单数据

	
	public WfDraft() {
		super();
	}

	public WfDraft(String id){
		super(id);
	}
	

	@NotNull(message="申请人不能为空")
    public User getApplyer() {
        return applyer;
    }

    public void setApplyer(User applyer) {
        this.applyer = applyer;
    }
    
	@Length(min=1, max=20, message="流程id长度必须介于 1 和 20 之间")
	public String getProcdefId() {
		return procdefId;
	}
	public void setProcdefId(String procdefId) {
		this.procdefId = procdefId;
	}
	
	
	@Length(min=0, max=256, message="流程名称长度必须介于 0 和 256 之间")
	public String getProcdefName() {
		return procdefName;
	}
	public void setProcdefName(String procdefName) {
		this.procdefName = procdefName;
	}
	
	
	public String getFormData() {
		return formData;
	}
	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	
	
	
	
	/**
     * 获取实体对应表名
    */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "wf_draft";
    }
    
    /**
     * 获取实体对应Id
    */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "410233663838962154";
    }
    
    
    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
            return true;
    }
}