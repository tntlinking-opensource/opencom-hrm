package com.geeke.wf.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessInstanceWithVariablesImpl;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Attachment;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.camundaex.cfg.TaskAssignees;
import com.geeke.camundaex.cfg.TaskServiceExImpl;
import com.geeke.camundaex.cmd.GetBackActivityImplsCmd;
import com.geeke.camundaex.cmd.RollbackMultiTaskCmd;
import com.geeke.camundaex.constant.WfConstant;
import com.geeke.camundaex.entity.ActAttachment;
import com.geeke.camundaex.entity.ActComment;
import com.geeke.camundaex.entity.ActNode;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.camundaex.entity.ActResult.EnumCodes;
import com.geeke.camundaex.entity.ActUser;
import com.geeke.camundaex.task.TaskExtInfo;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.org.dao.CompanyDao;
import com.geeke.org.entity.Company;
import com.geeke.utils.SessionUtils;
import com.geeke.wf.persistence.WfDataEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;


/**
 * 流程处理工具类
 * @author asus
 * @version 2021-3-5
 */
@Service("wfUtils")
public class WfUtils {	
	private Logger logger = LoggerFactory.getLogger(WfUtils.class);
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private CompanyDao companyDao;
	
	
	/**
	 * 创建流程
	 * @param procDefId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult create(String procDefId, WfDataEntity entity, MultipartFile[] attachments, IWfCallback callback) {
		try {
			
			
			Object savepoint = initSavePoint(entity);
			
			// 流程创建前回调
			ActResult actResult = callback.beforeCreate(entity);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}

			User user = SessionUtils.getUser();
	
			// 设置流程处理人
			setAuthentication(user);
			// 业务对象标记为审批中
			entity.setProcStatusByValue(WfDataEntity.PROC_FLAG_APPROVE);
			// 创建流程
			Map<String, Object> variables = Maps.newHashMap();
			variables.put(WfConstant.START_UP_USER_ID, user.getId());	// 启动用户Id流程变量
			variables.put(WfConstant.START_UP_USER_NAME, user.getName());	// 启动用户名称流程变量
			variables.put(WfConstant.PROCESS_FORM_DATA, JSON.toJSON(entity));				// 表单数据
			
			TaskAssignees.setCreateFlag(true);
			ProcessInstanceWithVariablesImpl procInstance = (ProcessInstanceWithVariablesImpl)runtimeService.startProcessInstanceById(procDefId, entity.getId(), variables);
			TaskAssignees.setCreateFlag(false);

			// 创建流程后，查看后续的人工处理环节
			List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstance.getId()).active().list();
			if(tasks.size() == 0) {
				rollbackTransation(savepoint);
				return new ActResult(EnumCodes.Error, "流程没有人工提交节点，请重新配置流程!");
			} else if(tasks.size() > 1) {
				rollbackTransation(savepoint);
				return new ActResult(EnumCodes.Error, "流程有多个人工提交节点，请重新配置流程!");
			}
			Task task = tasks.get(0);

			// 用户提交节点任务流程变量
			variables = Maps.newHashMap();
			entity.setProcInst(procInstance.getId());	// 设置业务对象对应的流程实例和流程状态
			// 流程实例状态的变量标志为审批完成
			variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_COMPLETED);
			taskService.complete(task.getId(), variables);
			actResult = TaskAssignees.getActNodeAssigneInfo();
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}

			// 添加意见 
			Comment comment = addComment(task, user, "提交", "");
			// 添加附件
			addAttachments(task, user, comment, attachments);

			// 如果流程结束，设置状态
			if(runtimeService.createProcessInstanceQuery().processInstanceId(procInstance.getId()).count() == 0) {	// 流程结束
				entity.setProcStatusByValue(WfDataEntity.PROC_FLAG_COMPLETED);
			} else {
				variables = Maps.newHashMap();
				// 流程实例状态的变量标志为审批中
				variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_APPROVE);		// 流程实例状态的变量标志为完成
				runtimeService.setVariables(procInstance.getId(), variables);
			}
			
			// 流程创建后回调
			actResult = callback.afterCreate(entity);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			return new ActResult(ActResult.EnumCodes.Done, "提交成功");
		} finally {
			clearAuthentication();
			TaskAssignees.restore();
		}
	}
	

	/**
	 * 重新提交
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult reapply(String taskId, WfDataEntity entity, MultipartFile[] attachments, IWfCallback callback) {
		try {
			Object savepoint = initSavePoint(entity);
			
			// 重新提交流程前回调
			ActResult actResult = callback.beforeReapply(entity);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			User user = SessionUtils.getUser();
			
			// 设置流程处理人
			setAuthentication(user);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			Map<String, Object> variables = Maps.newHashMap();
			// 流程实例状态的变量标志为审批完成
			variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_COMPLETED);
			variables.put(WfConstant.PROCESS_FORM_DATA, JSON.toJSON(entity));
			
			// 提交任务
			taskService.complete(taskId, variables);
			actResult = TaskAssignees.getActNodeAssigneInfo();
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}

			// 添加意见 
			Comment comment = addComment(task, user, "同意", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);

			// 如果流程结束，设置状态
			if(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).count() == 0) {	// 流程结束
				entity.setProcStatusByValue(WfDataEntity.PROC_FLAG_COMPLETED);
			} else {
				variables = Maps.newHashMap();
				// 流程实例状态的变量标志为审批中
				variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_APPROVE);
				runtimeService.setVariables(task.getProcessInstanceId(), variables);
			}
			
			// 重新提交流程后回调
			actResult = callback.afterReapply(entity);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			return new ActResult(ActResult.EnumCodes.Done, "提交成功");
		} finally {
			clearAuthentication();
			TaskAssignees.restore();
		}
	}
	
	
	/**
	 * 审批同意
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult agree(String taskId, WfDataEntity entity, MultipartFile[] attachments, IWfCallback callback) {
		try {
			Object savepoint = initSavePoint(entity);
			
			// 流程审批前回调
			ActResult actResult = callback.beforeAgree(entity);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			User user = SessionUtils.getUser();
			
			// 设置流程处理人
			setAuthentication(user);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			Map<String, Object> variables = Maps.newHashMap();
			// 流程实例状态的变量标志为审批完成
			variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_COMPLETED);
			variables.put(WfConstant.PROCESS_FORM_DATA, JSON.toJSON(entity));
			// 提交任务
			taskService.complete(taskId, variables);
			actResult = TaskAssignees.getActNodeAssigneInfo();
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}

			// 添加意见 
			Comment comment = addComment(task, user, "同意", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);

			// 如果流程结束，设置状态
			if(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).count() == 0) {
				entity.setProcStatusByValue(WfDataEntity.PROC_FLAG_COMPLETED);
			} else {
				variables = Maps.newHashMap();
				// 流程实例状态的变量标志为审批中
				variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_APPROVE);
				runtimeService.setVariables(task.getProcessInstanceId(), variables);
			}
			
			// 流程审批后回调
			actResult = callback.afterAgree(entity);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			return new ActResult(ActResult.EnumCodes.Done, "提交成功");
		} finally {
			clearAuthentication();
			TaskAssignees.restore();
		}
	}
	
	/**
	 * 转派任务
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult forward(String taskId, WfDataEntity entity, MultipartFile[] attachments) {
		try {
			User user = SessionUtils.getUser();
			List<ActNode> actNodes= entity.getActNodes();
			ActUser assignee = actNodes.get(0).getAssignees().get(0);
			
			// 设置流程处理人
			setAuthentication(user);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			// 添加意见 
			Comment comment = addComment(task, user, "转派", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);
			// 流程变量
			Map<String, Object> variables = Maps.newHashMap();
			variables.put(WfConstant.PROCESS_FORM_DATA, (JSONObject)JSON.toJSON(entity));
	
			// 转派任务
			TaskServiceExImpl taskServiceEx = (TaskServiceExImpl)taskService;
			taskServiceEx.forward(taskId, assignee, variables);
	
	
			return new ActResult(ActResult.EnumCodes.Done, "提交成功");
		} finally {
			clearAuthentication();
		}
	}
	
	/**
	 * 委派任务
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult commission(String taskId, WfDataEntity entity, MultipartFile[] attachments) {
		try {		
			User user = SessionUtils.getUser();
			List<ActNode> actNodes= entity.getActNodes();
			ActUser assignee = actNodes.get(0).getAssignees().get(0);
			
			// 设置流程处理人
			setAuthentication(user);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			// 添加意见 
			Comment comment = addComment(task, user, "委派", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);
			// 流程变量
			Map<String, Object> variables = Maps.newHashMap();
			variables.put(WfConstant.PROCESS_FORM_DATA, (JSONObject)JSON.toJSON(entity));
			
			// 委派任务
			TaskServiceExImpl taskServiceEx = (TaskServiceExImpl)taskService;
			taskServiceEx.commission(taskId, assignee, variables);
	
			return new ActResult(ActResult.EnumCodes.Done, "提交成功");
		} finally {
			clearAuthentication();
		}
	}
	
	/**
	 * 拟办任务（对委派过来的任务，给出意见后返回给任务持有者）
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult propose(String taskId, WfDataEntity entity, MultipartFile[] attachments) {
		try {
			User user = SessionUtils.getUser();
			
			// 设置流程处理人
			setAuthentication(user);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			// 添加意见 
			Comment comment = addComment(task, user, "拟办", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);
			// 流程变量
			Map<String, Object> variables = Maps.newHashMap();
			variables.put(WfConstant.PROCESS_FORM_DATA, (JSONObject)JSON.toJSON(entity));
	
			// 拟办任务
			TaskServiceExImpl taskServiceEx = (TaskServiceExImpl)taskService;
			taskServiceEx.propose(taskId, variables);
	
			return new ActResult(ActResult.EnumCodes.Done, "提交成功");
		} finally {
			clearAuthentication();
		}
	}
	
	/**
	 * 退回任务, 并保存意见
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @param callback
	 * @return
	 */
	public ActResult back(String taskId, WfDataEntity entity, MultipartFile[] attachments, IWfCallback callback) {
		try {
			// 没有指定退回的节点信息
			if(entity.getActNodes() == null || entity.getActNodes().size() <= 0) {
				return new ActResult(ActResult.EnumCodes.Warning, "未指定退回的节点和用户。");
			}
			
			Object savepoint =  initSavePoint(entity);
			
			boolean toStart = false; // 是否退回发起节点
			if(entity.getActNodes().size() == 1) {
				String startId = (String)taskService.getVariable(taskId, WfConstant.START_USER_ACTIVITY_ID);
				ActNode act = (ActNode) entity.getActNodes().get(0);
				toStart = act.getId().equals(startId);
			}
			// 流程回退前回调
			ActResult actResult = callback.beforeBack(entity, toStart);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			User user = SessionUtils.getUser();
			
			// 设置流程处理人
			setAuthentication(user);
			 
			Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			// 添加意见 	
			Comment comment = addComment(task, user, "退回", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);
			
			Map<String, Object> variables = Maps.newHashMap();
			variables.put(WfConstant.PROCESS_FORM_DATA, (JSONObject)JSON.toJSON(entity));
			
			// 回退任务
			List<ActNode> actNodes= entity.getActNodes();
			String[] toActivityIds = new String[actNodes.size()];
			for(int i = 0; i < actNodes.size(); i++) {
				toActivityIds[i] = actNodes.get(i).getId();
			}
			TaskServiceExImpl taskServiceEx = (TaskServiceExImpl)taskService;
			taskServiceEx.jump(taskId, toActivityIds, variables);
			
			// 流程回退后回调
			actResult = callback.afterBack(entity, toStart);
			if(isStop(actResult)) {
				rollbackTransation(savepoint);
				return actResult;
			}
			
			return new ActResult(ActResult.EnumCodes.Done, "退回成功");
		} finally {
			clearAuthentication();
			TaskAssignees.restore();
		}	
	}
	
	/**
	 * 拒绝任务, 并保存意见
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @param callback
	 * @return
	 */
	public ActResult reject(String taskId, WfDataEntity entity, MultipartFile[] attachments, IWfCallback callback) {
		// 发起节点id
		String toActivityId = (String)taskService.getVariable(taskId, WfConstant.START_USER_ACTIVITY_ID);
		// 流程启动用户Id和用户名称
		String strStarterId = (String)taskService.getVariable(taskId, WfConstant.START_UP_USER_ID);
		String strStarterName = (String)taskService.getVariable(taskId, WfConstant.START_UP_USER_NAME);
		
		ActNode actNode = new ActNode(toActivityId);
		actNode.getAssignees().add(new ActUser(strStarterId, strStarterName));
		List<ActNode> actNodes = Lists.newArrayList();
		actNodes.add(actNode);
		entity.setActNodes(actNodes);

		return back(taskId, entity, attachments, callback);
	}
	
	public void claim(String taskId) {
		User user = SessionUtils.getUser();

		TaskServiceExImpl taskServiceEx = (TaskServiceExImpl)taskService;
		taskServiceEx.claim(taskId, user.getId(), user.getName());

		// 添加意见 	
		addComment(taskId, user, "认领", "");
	}

	/**
	 * 终止任务的申请流程
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public int terminate(String taskId, WfDataEntity entity, MultipartFile[] attachments) {
		try {
			User user = SessionUtils.getUser();
			int rows = 0;
			
			// 设置流程处理人
			setAuthentication(user);
			
			// 设置为我终止的任务
			taskService.setVariableLocal(taskId, WfConstant.USER_TASK_FLAG, WfConstant.MY_TERMINATED_TASK);
			
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			// 设置任务实际处理人
			setHandleAssignee(task, user);
			
			// 添加意见 	
			Comment comment = addComment(task, user, "终止", entity.getTaskComement());
			// 添加附件
			addAttachments(task, user, comment, attachments);
			
			Map<String, Object> variables = Maps.newHashMap();
			entity.setProcStatusByValue(WfDataEntity.PROC_FLAG_TERMINATED);
			variables.put(WfConstant.PROCESS_FORM_DATA, JSON.toJSON(entity));
			variables.put(WfConstant.PROCESS_INST_STATUS, WfDataEntity.PROC_FLAG_TERMINATED);		// 流程实例状态的变量标志为完成
			runtimeService.setVariables(task.getProcessInstanceId() , variables);
			
			// 终止流程
			runtimeService.deleteProcessInstance(task.getProcessInstanceId(), entity.getTaskComement());
	
			return rows;
		} finally {
			clearAuthentication();
		}
	}
	
	/**
	 * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
	 * @param taskId
	 * @return
	 */
	public ActResult reverse(String taskId, WfDataEntity entity, IWfCallback callback) {
		try {
			User user = SessionUtils.getUser();
			
			// 设置流程处理人
			setAuthentication(user);
			
			HistoricTaskInstance historicTask =  historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
			
			ProcessInstance procInstance = runtimeService.createProcessInstanceQuery().processInstanceId(historicTask.getProcessInstanceId()).singleResult();
			if(procInstance == null) {
				return new ActResult(ActResult.EnumCodes.Warning, "流程已经结束，任务不能撤回。");
			}
			
			TaskExtInfo extInfo = TaskExtInfo.parse(historicTask.getDescription());
			ActUser assignee = new ActUser(extInfo.getApproverId(),extInfo.getApproverName());
			
			TaskServiceExImpl taskServiceEx = (TaskServiceExImpl)taskService;
			// 撤回的任务是转派、委派任务
			if(historicTask.getParentTaskId() != null) {
				Task parentTask = taskService.createTaskQuery().taskId(historicTask.getParentTaskId()).singleResult();
				if(parentTask != null) {
					// 设置任务实际处理人
					setHandleAssignee(parentTask, user);
					// 添加意见 	
					addComment(parentTask, user, "撤回", "撤回");
					taskServiceEx.forward(historicTask.getParentTaskId(), assignee, null);
					return new ActResult(ActResult.EnumCodes.Done, "任务已经撤回，请在待办列表中查看。");
				}
			}
			
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(historicTask.getProcessDefinitionId());
			ActivityImpl currentActivityImpl = processDefinitionEntity.findActivity(historicTask.getTaskDefinitionKey());
			List<PvmTransition> outerTransitions;
			if(isMultiInstance(currentActivityImpl)) {
				String result = taskServiceEx.rollbackMultiTask(taskId);
				if(RollbackMultiTaskCmd.HANDLE_RESULT_EXIST.equals(result)) {
					return new ActResult(ActResult.EnumCodes.Warning, "待办中有此任务，不能撤回。"); 
				} else if(RollbackMultiTaskCmd.HANDLE_RESULT_NOTLATE.equals(result)) {
					return new ActResult(ActResult.EnumCodes.Warning, "选择撤回的任务不是你最后处理的，请根据时间找到你最后处理的任务。"); 
				} if(RollbackMultiTaskCmd.HANDLE_RESULT_SUCESS.equals(result)) {
					addComment(historicTask, user, "撤回", "撤回");
					return new ActResult(ActResult.EnumCodes.Done, "任务已经撤回，请在待办列表中查看。"); 
				} 
				
				outerTransitions = currentActivityImpl.getParentFlowScopeActivity().getOutgoingTransitions();
			} else {
				outerTransitions = currentActivityImpl.getOutgoingTransitions();
			}
			
			// 当前节点的流出的用户节点
			Set<String> activityIds = Sets.newHashSet();
			for (PvmTransition pvmTransition : outerTransitions) {
				TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
				ActivityImpl nextActImpl = (ActivityImpl)transitionImpl.getDestination();
				activityIds.addAll(nextUserActivity(nextActImpl));
			}
			String[] ids =  activityIds.toArray(new String[activityIds.size()]);		
			long count = historyService.createHistoricTaskInstanceQuery().processInstanceId(historicTask.getProcessInstanceId()).taskDefinitionKeyIn(ids).finished().finishedAfter(historicTask.getEndTime()).count();
			if(count > 0) {
				return new ActResult(ActResult.EnumCodes.Warning, "流程已经通过后续节点的处理，请联系当前处理人通过退回操作处理。");
			}
			List<Task> activeTasks = taskService.createTaskQuery().processInstanceId(historicTask.getProcessInstanceId()).taskDefinitionKeyIn(ids).list();
 			if(activeTasks == null || activeTasks.size() == 0) {
				return new ActResult(ActResult.EnumCodes.Warning, "后续节点没有任务，已撤回或被其他人撤回。");
			}
 			
			String startId = (String)getVariable(historicTask.getProcessInstanceId(), WfConstant.START_USER_ACTIVITY_ID);
			boolean toStart = historicTask.getTaskDefinitionKey().equals(startId);   // 是否测回到发起节点
			// 流程测回前回调
			ActResult actResult = callback.beforeReverse(entity, toStart);
			if(isStop(actResult)) {
				return actResult;
			}
 			
			Task activeTask = activeTasks.get(0);	// 取其中一个作为撤回的任务
			
			// 设置任务实际处理人
			setHandleAssignee(activeTask, user);
			
			// 添加意见 	
			addComment(activeTask, user, "撤回", "撤回");
			
			
			List<ActUser> assignees = Lists.newArrayList();	
			assignees.add(assignee);
			ActNode actNode = new ActNode(historicTask.getTaskDefinitionKey());
			actNode.setAssignees(assignees);
			List<ActNode> actNodes = Lists.newArrayList();
			actNodes.add(actNode);
			
			TaskAssignees.restore();
			TaskAssignees.setAssigneeList(actNodes);
			TaskAssignees.setRouteFlag(false);
			
		    taskServiceEx.jump(activeTask.getId(), new String[]{historicTask.getTaskDefinitionKey()}, null);
		    TaskAssignees.restore();
		    
		    // 流程测回后回调
		    actResult = callback.afterReverse(entity, toStart);
			if(isStop(actResult)) {
				return actResult;
			}
		    
			return new ActResult(ActResult.EnumCodes.Done, "任务已经撤回，请在待办列表中查看。");
		} finally {
			clearAuthentication();
			TaskAssignees.restore();
		}
	}

	
	private Set<String> nextUserActivity(ActivityImpl activityImpl) {
		Set<String> activityIds = Sets.newHashSet();
		
		if(activityImpl == null) {
			return activityIds;
		}
		
		if (activityImpl.getActivityBehavior() instanceof NoneStartEventActivityBehavior) {// 开始节点，停止递归
			return activityIds;
		}
		
		if (activityImpl.getActivityBehavior() instanceof UserTaskActivityBehavior) {// 用户任务		
			activityIds.add(activityImpl.getId());
			return activityIds;
		} else if(activityImpl.getActivityBehavior() instanceof MultiInstanceActivityBehavior) {
			if(activityImpl.getActivities().size() > 0) {
				activityIds.add(activityImpl.getActivities().get(0).getId());
				return activityIds;
			}
		}
		
		
		// 当前节点的流入来源
		List<PvmTransition> outerTransitions;
		if(isMultiInstance(activityImpl)) {
			outerTransitions = activityImpl.getParentFlowScopeActivity().getOutgoingTransitions();
		} else {
			outerTransitions = activityImpl.getOutgoingTransitions();
		}
		for (PvmTransition pvmTransition : outerTransitions) {
			TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
			ActivityImpl nextActImpl = (ActivityImpl)transitionImpl.getDestination();
			activityIds.addAll(nextUserActivity(nextActImpl));
		}
		return activityIds;
	}
	
	
	/**
	 * 保存审批单据
	 * @param taskId
	 * @param entity
	 * @param attachments
	 * @return
	 */
	public ActResult saveForm(String taskId, WfDataEntity entity) {
		entity.preUpdate();
		// 设置流程处理人
		// User user = SessionUtils.getUser();
		// setAuthentication(user);  TODO 待测试： 不设置权限的情况下，能否保存流程变量
		
		taskService.setVariable(taskId, WfConstant.PROCESS_FORM_DATA, (JSONObject)JSON.toJSON(entity));
		return new ActResult(ActResult.EnumCodes.Save, "保存成功");
	}
	
	/**
	 * 查找回退的活动节点和用户
	 * @param procInstId 流程实例Id
	 * @return
	 */
	public ActResult listBackActivity(String taskId) {
		
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		List<ActNode> listNode = commandExecutor.execute(new GetBackActivityImplsCmd(taskId));
		if(listNode == null || listNode.size() == 0) {
			return new ActResult(ActResult.EnumCodes.Warning, "没有可退回流程节点。");
		}
		
		ActResult actResult = new ActResult(ActResult.EnumCodes.SelectNode, "选择用户");
		actResult.getActNodes().addAll(listNode);
		return actResult;
	}
	
	/**
	 * 获取流程变量
	 * @param procInstId
	 * @param variableName
	 * @return
	 */
	public Object getVariable(String procInstId, String variableName) {
		return historyService.createHistoricVariableInstanceQuery().processInstanceId(procInstId).variableName(variableName).singleResult().getValue();
	}
	
	/**
	 * 设置流程变量（在执行流程）
	 * @param procInstId
	 * @param variableName
	 * @param variableValue
	 */
	public void setVariable(String procInstId, String variableName, Object variableValue) {	
		runtimeService.setVariable(procInstId, variableName, variableValue);
		
	}
	
	
	/**
	 * 通过流程实例Id获取表单对象
	 * @param procInstId
	 * @return
	 */
	public JSONObject getFormDataByProcInstId(String procInstId)
	{
		return (JSONObject)getVariable(procInstId, WfConstant.PROCESS_FORM_DATA);
	}

	/**
	 * 初始化事务回滚点
	 * @param entity
	 * @return  事务回滚点
	 */
	private Object initSavePoint(WfDataEntity entity) {
		Object savepoint = null;
		TaskAssignees.restore();

		
		boolean routeFlag = false; 
		if(entity.getActNodes() == null) {	// 没有流程节点数据，需要路由
			routeFlag = true;
			savepoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
		} else {
			TaskAssignees.setAssigneeList(entity.getActNodes());
		}
		TaskAssignees.setRouteFlag(routeFlag);
		
		return savepoint;
	}
	

	/**
	 * 检查actResult的值，是否需要终止操作
	 * @param actResult
	 * @return
	 */
	private boolean isStop(ActResult actResult) {
		return actResult != null && actResult.getCode() != ActResult.EnumCodes.Done;
	}


	/**
	 * 回滚事务
	 * @param savepoint
	 */
	private void rollbackTransation(Object savepoint) {
		if(savepoint != null) {
			TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
		}
	}

	/**
	 * 添加备注
	 * @param taskId
	 * @param user
	 * @param message
	 */
	protected Comment addComment(Task task, User user, String operation, String message ) {	
		String extInfo = String.format("{'%s': '%s', '%s': '%s', '%s': '%s', '%s': '%s', '%s': '%s' }", 
				ActComment.EXT_INFO_MESSAGE, message, 
				ActComment.EXT_INFO_ACTIVITYID, task.getTaskDefinitionKey(),
				ActComment.EXT_INFO_ACTIVITYNAME, task.getName(),
				ActComment.EXT_INFO_COMMENTBY, user.getName(),
				ActComment.EXT_INFO_OPERATION, operation);
		return taskService.createComment(task.getId(), task.getProcessInstanceId(), extInfo);
	}
	
	/**
	 * 添加备注
	 * @param taskId
	 * @param user
	 * @param message
	 */
	protected Comment addComment(String taskId, User user, String operation, String message ) {	
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return addComment(task, user, operation, message);
	}

	/**
	 * 添加备注
	 * @param taskId
	 * @param user
	 * @param message
	 */
	protected Comment addComment(HistoricTaskInstance task, User user, String operation, String message ) {	
		String extInfo = String.format("{'%s': '%s', '%s': '%s', '%s': '%s', '%s': '%s', '%s': '%s' }", 
				ActComment.EXT_INFO_MESSAGE, message, 
				ActComment.EXT_INFO_ACTIVITYID, task.getTaskDefinitionKey(),
				ActComment.EXT_INFO_ACTIVITYNAME, task.getName(),
				ActComment.EXT_INFO_COMMENTBY, user.getName(),
				ActComment.EXT_INFO_OPERATION, operation);
		return taskService.createComment(task.getId(), task.getProcessInstanceId(), extInfo);
	}
	
	/**
	 * 添加任务附件
	 * @param task
	 * @param attachments
	 */
	protected List<Attachment> addAttachments(Task task, User user, Comment comment, MultipartFile[] attachments) {
		List<Attachment> atts = Lists.newArrayList();
		if(Objects.nonNull(attachments) && attachments.length > 0){
			for(MultipartFile file: attachments) {
				try {
					String extInfo = String.format("{'%s': '%s', '%s': '%s', '%s': '%s'}",
							ActAttachment.EXT_INFO_DESCRIPTION, file.getName(),
							ActAttachment.EXT_INFO_USERID, user.getId(),
							ActAttachment.EXT_INFO_COMMENTID, comment.getId());

					Attachment attachment = taskService.createAttachment(file.getContentType(), task.getId(), task.getProcessInstanceId(), file.getOriginalFilename(), extInfo, file.getInputStream());
					atts.add(attachment);
				} catch (IOException e) {
					logger.error("任务[%s]:创建审批附件出错。", task.getId());
					e.printStackTrace();
				}

			}
		}
		return atts;
	}
	
	/**
	 * 设置任务的实际处理人
	 * @param task
	 * @param user
	 */
	protected void setHandleAssignee(Task task, User user) {
		// 设置任务实际处理人 TODO 与实际人不一致是否需要更换description的审批人扩展信息？？
		if(!user.getId().equals(task.getAssignee())) {
			taskService.setAssignee(task.getId(), user.getId());
		}
	}
	
	/**
	 * 设置流程处理人和权限 
	 * TODO companys数据存放到缓存
	 * @param user
	 */
	protected void setAuthentication(User user) {
        List<Parameter> params = Lists.newArrayList();;
        PageRequest pageRequest = new PageRequest(params);
        List<Company> companys = companyDao.listAll(pageRequest);
		List<String> tenants = Lists.newArrayList();
		if(companys != null) {
			for(Company company : companys) {
				tenants.add(company.getId());
			}
		}
		identityService.setAuthentication(user.getId(), null, tenants);
	}
	
	protected void clearAuthentication() {
		identityService.clearAuthentication();
	}

	public RuntimeService getRuntimeService() {
		return runtimeService;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public IdentityService getIdentityService() {
		return identityService;
	}

	public FormService getFormService() {
		return formService;
	}
	
	/**
	 * 活动是否为多实例
	 * @param activity
	 * @return
	 */
	private boolean isMultiInstance(ActivityImpl activity) {
		ActivityImpl parent = activity.getParentFlowScopeActivity();
	    return parent != null && parent.getActivityBehavior() instanceof MultiInstanceActivityBehavior;
	}
}
