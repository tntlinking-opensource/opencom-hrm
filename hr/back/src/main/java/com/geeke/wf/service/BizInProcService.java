package com.geeke.wf.service;

import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.geeke.camundaex.entity.ActResult;
import com.geeke.camundaex.entity.ActResult.EnumCodes;
import com.geeke.wf.persistence.WfCrudDao;
import com.geeke.wf.persistence.WfDataEntity;


/**
 * 工作流sevice， 在流程过程中保存业务对象
 * @author lys
 * @version 2020-4-12
 */
@Transactional(readOnly = true)
public abstract class BizInProcService<D extends WfCrudDao<T>, T extends WfDataEntity<T>> extends WfBaseService<D, T> {	
	
	/**
	 * 通过任务Id获取业务数据
	 * @param taskId	任务Id
	 * @return
	 */
	@Override
	public T getByTaskId(String taskId) {
		HistoricTaskInstance histTask = wfUtils.getHistoryService().createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		HistoricProcessInstance histProcInst = wfUtils.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(histTask.getProcessInstanceId()).singleResult();
		return super.get(histProcInst.getBusinessKey());
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
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
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
		entity.preUpdate();
		ActResult actResult = wfUtils.reapply(taskId, entity, attachments, this);
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
			this.doUpdate(entity);
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
		entity.preUpdate();
		ActResult actResult = wfUtils.agree(taskId, entity, attachments, this);
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
			this.doUpdate(entity);
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
		entity.preUpdate();
		ActResult actResult = wfUtils.forward(taskId, entity, attachments);
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
			this.doUpdate(entity);
		}
		return actResult;
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
		entity.preUpdate();
		ActResult actResult = wfUtils.commission(taskId, entity, attachments);
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
			this.doUpdate(entity);
		}
		return actResult;
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
		entity.preUpdate();
		ActResult actResult = wfUtils.propose(taskId, entity, attachments);
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
			this.doUpdate(entity);
		}
		return actResult;
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
		entity.preUpdate();
		ActResult actResult = wfUtils.back(taskId, entity, attachments, this);
		if(actResult.getCode() == EnumCodes.AutoSubmit || actResult.getCode() == EnumCodes.Done) {
			this.doUpdate(entity);
		}
		return actResult;
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
		wfUtils.terminate(taskId, entity, attachments);

		// 保存业务数据
		entity.preUpdate();
		entity.setProcStatusByValue(WfDataEntity.PROC_FLAG_TERMINATED);
		this.doUpdate(entity);
		return this.doDelete(entity);
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
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public ActResult saveForm(String taskId, T entity) {
		entity.preUpdate();		
		// 保存业务数据
		dao.update(entity);
		return wfUtils.saveForm(taskId, entity);
	}

}
