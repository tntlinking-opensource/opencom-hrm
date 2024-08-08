package com.geeke.wf.service;

import com.geeke.camundaex.entity.ActResult;
import com.geeke.wf.persistence.WfDataEntity;

public interface IWfCallback<T extends WfDataEntity<T>> {

	/**
	 * 创建流程前回调
	 * @param entity
	 * @return
	 */
	public ActResult beforeCreate(T entity);
	
	/**
	 * 创建流程后回调
	 * @param entity
	 * @return
	 */
	public ActResult afterCreate(T entity);
	
	/**
	 * 重新提交前回调
	 * @param entity
	 * @return
	 */
	public ActResult beforeReapply(T entity);

	/**
	 * 重新提交后回调
	 * @param entity
	 * @return
	 */
	public ActResult afterReapply(T entity);
	
	/**
	 * 审批前回调
	 * @param entity
	 * @return
	 */
	public ActResult beforeAgree(T entity);
	
	/**
	 * 审批后回调
	 * @param entity
	 * @return
	 */
	public ActResult afterAgree(T entity);
	
	/**
	 * 退回前回调
	 * @param entity
	 * @param toStart	是否退回到发起人节点
	 * @return
	 */
	public ActResult beforeBack(T entity, boolean toStart);
	
	/**
	 * 退回后回调
	 * @param entity
	 * @param toStart	是否退回到发起人节点
	 * @return
	 */
	public ActResult afterBack(T entity, boolean toStart);
	
	/**
	 * 撤销前回调
	 * @param entity
	 * @param toStart	是否撤销到发起人节点
	 * @return
	 */
	public ActResult beforeReverse(T entity, boolean toStart);
	
	/**
	 * 撤销后回调
	 * @param entity
	 * @param toStart	是否撤销到发起人节点
	 * @return
	 */
	public ActResult afterReverse(T entity, boolean toStart);
}
