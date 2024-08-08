package com.geeke.wf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.service.CrudService;
import com.geeke.utils.SessionUtils;
import com.geeke.wf.entity.WfDraft;
import com.geeke.wf.persistence.WfCrudDao;
import com.geeke.wf.persistence.WfDataEntity;


/**
 * WfBaseService 基类
 * @author lys
 * @version 2020-5-29
 */
@Transactional(readOnly = true)
public abstract class WfBaseService<D extends WfCrudDao<T>, T extends WfDataEntity<T>> extends CrudService<D, T> implements IWfCallback<T> {	
	
	@Autowired
	protected WfUtils wfUtils;

	@Autowired
	protected WfDraftService wfDraftService;
	
	/**
	 * 通过任务Id获取业务数据
	 * @param taskId	任务Id
	 * @return
	 */
	public abstract T getByTaskId(String taskId);
	

	/**
	 * 保存草稿
	 * @param wfDraft
	 * @param entity
	 * @param attachments
	 * @return
	 * @throws IOException
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int saveDraft(WfDraft wfDraft, T entity, MultipartFile[] attachments) throws IOException {
		wfDraft.setFormData(JSON.toJSONString(entity));
		wfDraft.setApplyer( SessionUtils.getUser());
		wfDraftService.save(wfDraft, attachments);
		return 0;
	}


	/**
	 * 创建流程
	 * @param procDefId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult create(String procDefId, T entity, MultipartFile[] attachments);
	
	
	
	/**
	 * 重新发起申请
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult reapply(String taskId, T entity, MultipartFile[] attachments);	
	
	

	
	/**
	 * 审批同意
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult agree(String taskId, T entity, MultipartFile[] attachments);
	

	
	/**
	 * 转派任务
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult forward(String taskId, T entity, MultipartFile[] attachments);
	

	
	/**
	 * 委派任务
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult commission(String taskId, T entity, MultipartFile[] attachments);
	

	
	/**
	 * 拟办任务（对委派过来的任务，给出意见后返回给任务持有者）
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult propose(String taskId, T entity, MultipartFile[] attachments);
	

	
	/**
	 * 退回任务, 并保存意见
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult back(String taskId, T entity, MultipartFile[] attachments);
	

	
	/**
	 * 拒绝任务, 并保存意见
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult reject(String taskId, T entity, MultipartFile[] attachments);
	
	

	
	/**
	 * 终止任务的申请流程
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract int terminate(String taskId, T entity, MultipartFile[] attachments);
	
	/**
	 * 撤回任务
	 * @param taskId
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult reverse(String taskId, T entity);



	/**
	 * 保存审批单据
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public abstract ActResult saveForm(String taskId, T entity);
	
	/**
	 * 查找回退的活动节点和用户
	 * @param procInstId 流程实例Id
	 * @return
	 */
	@Transactional(readOnly = true)
	public ActResult listBackActivity(String taskId) {
		return wfUtils.listBackActivity(taskId);
	}
	
	/**
	 * 获取表单对象的class
	 * @return
	 */
	protected abstract Class<T> getBizClass();
	
	
	@Override
	public ActResult beforeCreate(T entity) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}

	@Override
	public ActResult afterCreate(T entity) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}
	
	@Override
	public ActResult beforeReapply(T entity) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}
	
	@Override
	public ActResult afterReapply(T entity) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}
	
	@Override
	public ActResult beforeAgree(T entity) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}

	@Override
	public ActResult afterAgree(T entity) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}
	
	@Override
	public ActResult beforeBack(T entity, boolean toStart) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}

	@Override
	public ActResult afterBack(T entity, boolean toStart) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}
	
	
	
	@Override
	public ActResult beforeReverse(T entity, boolean toStart) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}

	@Override
	public ActResult afterReverse(T entity, boolean toStart) {
		return new ActResult(ActResult.EnumCodes.Done, "回调成功");
	}

}
