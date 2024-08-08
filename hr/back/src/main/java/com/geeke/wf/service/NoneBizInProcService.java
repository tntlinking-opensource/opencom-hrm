package com.geeke.wf.service;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.geeke.camundaex.constant.WfConstant;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.wf.persistence.WfCrudDao;
import com.geeke.wf.persistence.WfDataEntity;


/**
 * 工作流sevice， 在流程过程中不保存业务对象
 * @author lys
 * @version 2020-5-29
 */
@Transactional(readOnly = true)
@DSTransactional
public abstract class NoneBizInProcService<D extends WfCrudDao<T>, T extends WfDataEntity<T>> extends WfBaseService<D, T> {	

	/**
	 * 通过任务Id获取业务数据
	 * @param taskId	任务Id
	 * @return
	 */
	@Override
	public T getByTaskId(String taskId) {
		HistoricTaskInstance histTask = wfUtils.getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		JSONObject  jsonForm = (JSONObject)wfUtils.getVariable(histTask.getProcessInstanceId(), WfConstant.PROCESS_FORM_DATA);
		T entity = jsonForm.toJavaObject(getBizClass());
		entity.setProcInst(histTask.getProcessInstanceId());	// 设置流程实例Id
		entity.setProcStatusByValue((String)wfUtils.getVariable(histTask.getProcessInstanceId(), WfConstant.PROCESS_INST_STATUS));
		return entity;
	}
	
	
	/**
	 * 创建流程
	 * @param procDefId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult create(String procDefId, T entity, MultipartFile[] attachments) {
		entity.preInsert();
		
		ActResult actResult = wfUtils.create(procDefId, entity, attachments, this);
		if( entity.isApproved() ) {
			this.doInsert(entity);
		}
		return actResult;
	}
	
	/**
	 * 重新发起申请
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult reapply(String taskId, T entity, MultipartFile[] attachments) {
		ActResult actResult = wfUtils.reapply(taskId, entity, attachments, this);
		if( entity.isApproved() ) {
			this.doInsert(entity);
		}
		return actResult;
	}	
	
	/**
	 * 审批同意
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult agree(String taskId, T entity, MultipartFile[] attachments) {
		ActResult actResult = wfUtils.agree(taskId, entity, attachments, this);
		if( entity.isApproved() ) {
			this.doInsert(entity);
		}
		return actResult;
	}
	
	/**
	 * 转派任务
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult forward(String taskId, T entity, MultipartFile[] attachments) {
		return wfUtils.forward(taskId, entity, attachments);
	}
	
	/**
	 * 委派任务
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult commission(String taskId, T entity, MultipartFile[] attachments) {
		return wfUtils.commission(taskId, entity, attachments);
	}	
	
	/**
	 * 拟办任务（对委派过来的任务，给出意见后返回给任务持有者）
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult propose(String taskId, T entity, MultipartFile[] attachments) {
		return wfUtils.propose(taskId, entity, attachments);
	}
	
	/**
	 * 退回任务, 并保存意见
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult back(String taskId, T entity, MultipartFile[] attachments){
		return wfUtils.back(taskId, entity, attachments, this);
	}
	
	/**
	 * 拒绝任务, 并保存意见
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult reject(String taskId, T entity, MultipartFile[] attachments){		 
		return wfUtils.reject(taskId, entity, attachments, this);
	}
	
	/**
	 * 终止任务的申请流程
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int terminate(String taskId, T entity, MultipartFile[] attachments) {
		return wfUtils.terminate(taskId, entity, attachments);
	}
	
	/**
	 * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
	 * @param taskId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult reverse(String taskId, T entity) {
		return wfUtils.reverse(taskId, entity, this);
	}
	
	/**
	 * 保存审批单据
	 * @param taskId
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult saveForm(String taskId, T entity) {
		return wfUtils.saveForm(taskId, entity);
	}
}
