package com.geeke.common.data;

import com.geeke.common.persistence.BaseEntity;
import com.geeke.dc.utils.DcConfigure;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 分页请求类
 * 
 * @author Lining
 * @date 2017/11/7
 */
public final class PageRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int offset;

	private int limit;

	private String order;

	private List<Parameter> params;
	
	
	public PageRequest(List<Parameter> parameters) {
		this(0, 1, parameters, null);
	}		

	
	public PageRequest(List<Parameter> parameters, String order) {
		this(0, 1, parameters, order);
	}
	

	public PageRequest(int offset, int limit, List<Parameter> parameters, String order) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset must not be less than zero!");
		}

		if (limit < 1) {
			throw new IllegalArgumentException("Limit must not be less than one!");
		}

		this.offset = offset;
		this.limit = limit;
		if(parameters == null){
			this.params = Lists.newArrayList();
		}else{
			this.params = parameters;
		}
		
		this.order = order;
	}	
	

	public int getPage() {
		return this.offset;
	}

	public int getSize() {
		return this.limit;
	}

	public String getOrder() {
		return this.order;
	}

	public int getOffset() {
		return this.offset;
	}
	
	public List<Parameter> getParams() {
		return params;
	}

	public void setParams(List<Parameter> params) {
		this.params = params;
	}

	/**
	 * 获取数据库名称
	 */
	public String getDbName(){
		return DcConfigure.dbName;
	}

	
	/* 数据库存储状态 */
	public String getDEL_FLAG_NORMAL() {
		return BaseEntity.DEL_FLAG_NORMAL;
	}

	public String getDEL_FLAG_DELETE() {
		return BaseEntity.DEL_FLAG_DELETE;
	}

	public String getDEL_FLAG_AUDIT() {
		return BaseEntity.DEL_FLAG_AUDIT;
	}
	
}
