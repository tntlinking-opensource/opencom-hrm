package com.geeke.common.persistence;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据Entity类
 * @author lys
 * @version 2014-05-16
 */
@ApiModel(description = "数据Entity类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class DataEntity<T> extends BaseEntity<T> {

	@ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("名称")
    protected String name;		//名称
	@ApiModelProperty("备注")
    protected String remarks;	// 备注
	@ApiModelProperty("创建者")
    protected String createBy;	// 创建者
	@ApiModelProperty("创建日期")
    protected String createDate;	// 创建日期
	@ApiModelProperty("更新者")
    protected String updateBy;	// 更新者
	@ApiModelProperty("更新日期")
    protected String updateDate;	// 更新日期
	@ApiModelProperty("删除标记（0：正常；1：删除；2：审核）")
    protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	@ApiModelProperty("上次更新时间")
    protected String oldUpdateDate;  // 上次更新时间

	@ApiModelProperty(hidden = true)
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	@JsonIgnore
	public void preInsert(){
		super.preInsert();
		JSONObject userObj = SessionUtils.getUserJson();
		if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))){
			this.updateBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
			this.createBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
		}

		this.updateDate = simpleDateFormat.format(new Date());
		this.createDate = this.updateDate;
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	@JsonIgnore
	public void preUpdate(){
		super.preUpdate();
		JSONObject userObj = SessionUtils.getUserJson();
		if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))){
			this.updateBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
		}
		this.oldUpdateDate = this.updateDate;
		this.updateDate = simpleDateFormat.format(new Date());
	}	
	
	@Length(min=1, max=100, message="名称长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}		
	
	@Length(min=0, max=255)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	@JsonIgnore
	public String getOldUpdateDate() { return oldUpdateDate; }

	public void setOldUpdateDate(String oldUpdateDate) { this.oldUpdateDate = oldUpdateDate; }

	@JsonIgnore
	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
