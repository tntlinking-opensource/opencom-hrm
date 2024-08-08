
package com.geeke.common.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.utils.IdGen;
import com.geeke.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Entity支持类
 * @author lys
 * @version 2014-05-16
 */
@ApiModel(description = "Entity支持类")
public abstract class BaseEntity<T> implements Serializable {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1L;

	/**
	 * 实体编号（唯一标识）
	 */
	@ApiModelProperty("实体编号（唯一标识）")
	protected String id;

	public BaseEntity() {
	}
	
	public BaseEntity(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@JsonIgnore
	public void preInsert(){
		if (StringUtils.isBlank(getId())) {
			setId(IdGen.uuid());
		}
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@JsonIgnore
	public void preUpdate(){
	}
	
	/**
	 * 获取实体对应表名，在实体类中覆盖
	 */
	@JsonIgnore
	public String getBusTableName() {
		return "";
	}
	
	/**
	 * 获取实体对应表Id，在实体类中覆盖
	 */
	@JsonIgnore
	public String getBusTableId() {
		return "";
	}

	/**
	 * 获取实体对应表描述，在实体类中覆盖
	 */
	@JsonIgnore
	public String getBusTableComments() {
		return "";
	}
	
	/**
	 * 获取实体表中是否存在del_flag字段，在实体类中覆盖
	 */
	@JsonIgnore
	public boolean getBusTableHasDelFlag() {
		return false;
	}	
	
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	@ApiModelProperty("删除标记（0：正常；1：删除；2：审核；）")
	public static final String DEL_FLAG_NORMAL = "0";
	@ApiModelProperty(hidden = true)
	public static final String DEL_FLAG_DELETE = "1";
	@ApiModelProperty(hidden = true)
	public static final String DEL_FLAG_AUDIT = "2";
	
}
