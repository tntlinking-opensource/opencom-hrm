package com.geeke.sys.entity;


import com.geeke.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * 字典Entity
 * @author
 * @version 2013-05-15
 */
@ApiModel(description = "字典Entity")
public class Dict extends DataEntity<Dict> {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("数据值")
	private String value;	// 数据值
	@ApiModelProperty("标签名")
	private String label;	// 标签名
	@ApiModelProperty("类型")
	private String type;	// 类型
	@ApiModelProperty("描述")
	private String description;// 描述
	@ApiModelProperty("排序")
	private Integer sort;	// 排序
	@ApiModelProperty("父Id")
	private String parentId;//父Id

	public Dict() {
		super();
	}
	
	public Dict(String id){
		super(id);
	}
	
	public Dict(String value, String label){
		this.value = value;
		this.label = label;
	}
	
	@XmlAttribute
	@Length(min=1, max=100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlAttribute
	@Length(min=1, max=100)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min=1, max=100)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	@Length(min=0, max=100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Length(min=1, max=100)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Override
	public String toString() {
		return label;
	}
}