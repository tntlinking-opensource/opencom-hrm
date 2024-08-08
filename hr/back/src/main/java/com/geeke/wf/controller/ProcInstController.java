package com.geeke.wf.controller;

import java.util.List;
import java.util.Set;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.task.Attachment;
import org.camunda.bpm.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.google.common.collect.Sets;

/**
 * 流程实例 Controller
 * @author lys
 * @version 2020-04-30
 */
@Api(value = "/wf/procinst", tags = {"流程实例 Controller"})
@RestController
@RequestMapping(value = "/wf/procinst")
public class ProcInstController extends BaseController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	
	/**
	 * 获取流程实例的审批记录
	 * @param procInstId
	 * @return
	 */
    @ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "string", name = "procInstId", value = "", required = true)
	})
	@ApiOperation(value = "获取流程实例的审批记录", notes = "获取流程实例的审批记录", httpMethod = "GET",response = Comment.class ,responseContainer = "List")
	@GetMapping("/{procInstId}/comments")
    public ResponseEntity<JSONObject> getProcInstComments(@PathVariable("procInstId") String procInstId) {
    	
    	List<Comment> comments = taskService.getProcessInstanceComments(procInstId); 	
    	
    	return ResponseEntity.ok(ResultUtil.successJson(comments));
    }
    
    
	/**
	 * 获取流转过的活动和路径
	 * @param procInstId
	 * @return
	 */
    @ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "string", name = "procInstId", value = "", required = true)
	})
	@ApiOperation(value = "获取流转过的活动和路径", notes = "获取流转过的活动和路径", httpMethod = "GET")
	@GetMapping("/{procInstId}/histoicFlow")
    public ResponseEntity<JSONObject> histoicFlow(@PathVariable("procInstId") String procInstId) {
    	Set<String> activeIds = Sets.newHashSet();
    	List<HistoricActivityInstance> histActitityInsts = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInstId).finished().list();
    	
    	if(histActitityInsts.size() > 0) {
        	for(HistoricActivityInstance actInst: histActitityInsts) {
        		activeIds.add(actInst.getActivityId());
        	}
        	
        	Set<String> flowIds = Sets.newHashSet();
        	
        	// 流程定义Id
        	String procDefId = histActitityInsts.get(0).getProcessDefinitionId();
        	ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);

        	// 已完成的活动节点入口流的源活动也完成，表示经过次顺序流
        	for(String activityId: activeIds){
        		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        		if(activityImpl == null) {
        			continue;
        		}
        		for(PvmTransition tran: activityImpl.getIncomingTransitions()) {
        			if(tran.getSource() != null && activeIds.contains(tran.getSource().getId())) {
        				flowIds.add(tran.getId());
        			}
        		}
        	}
        	activeIds.addAll(flowIds);
        	
    	}
    	
    	return ResponseEntity.ok(ResultUtil.successJson(activeIds));
    }
    

	/**
     * 查询某个实例所关联的附件集合（多个task组合成一个实例）
	 * @param procInstId
	 * @return
	 */
    @ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "string", name = "procInstId", value = "", required = true)
	})
	@ApiOperation(value = "查询某个实例所关联的附件集合（多个task组合成一个实例）", notes = "查询某个实例所关联的附件集合（多个task组合成一个实例）", httpMethod = "GET",response = Attachment.class,responseContainer = "List")
	@GetMapping("/{procInstId}/attachments")
    public ResponseEntity<JSONObject> getProcInstAttachments(@PathVariable("procInstId") String procInstId){
    	List<Attachment> attachments = taskService.getProcessInstanceAttachments(procInstId);
    	return ResponseEntity.ok(ResultUtil.successJson(attachments)); 
    }
    
}