package com.geeke.common.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页查询结果类
 * 
 * @author Lining
 * @date 2017/11/7
 * @param <T>
 */
@ApiModel(description = "分页查询结果类")
public final class Page<T> implements java.io.Serializable {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("总行数")
	private long total;

	@ApiModelProperty("行数据")
	private List<T> rows;

	public Page(long total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return this.total;
	}

	public List<T> getRows() {
		return this.rows;
	}

}
