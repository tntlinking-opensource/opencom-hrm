package com.geeke.wf.controller;

import java.util.List;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.wf.service.WfUtils;


/**
 * 流程实例 Controller
 * @author lys
 * @version 2020-04-30
 */
@Api(value = "/wf/task", tags = {"流程实例 Controller"})
@RestController
@RequestMapping(value = "/wf/task")
public class TaskController extends BaseController {

	@Autowired
	private TaskService taskService;

	@Autowired
	protected WfUtils wfUtils;

	/**
	 * 签收任务
	 * @param taskId
	 * @return
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "", required = true)
	})
	@ApiOperation(value = "签收任务", notes = "签收任务", httpMethod = "GET")
	@GetMapping("claim/{taskId}")
	public ResponseEntity<JSONObject> claim(@PathVariable("taskId") String taskId) {
		wfUtils.claim(taskId);
		return ResponseEntity.ok(ResultUtil.successJson("claim success")); 
	}

    /**
     * 获取人员指派任务和候选任务
     * @param assignee
     * @param descriptionLike
     * @param sortBy
     * @param sortOrder
     * @param firstResult
     * @param maxResults
     * @return
     */
    @ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "string", name = "assignee", value = "", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "string", name = "descriptionLike", value = ""),
			@ApiImplicitParam(paramType = "query", dataType = "string", name = "sortBy", value = "", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "string", name = "sortOrder", value = "", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "int", name = "firstResult", value = "", required = true),
			@ApiImplicitParam(paramType = "query", dataType = "int", name = "maxResults", value = "", required = true)
	})
	@ApiOperation(value = "获取人员指派任务和候选任务", notes = "获取人员指派任务和候选任务", httpMethod = "GET")
	@GetMapping("/page")
    public ResponseEntity<JSONObject> listPage(@RequestParam("assignee") String assignee,
		@RequestParam(value = "descriptionLike", required = false) String descriptionLike,
		@RequestParam("sortBy") String sortBy,
		@RequestParam("sortOrder") String sortOrder,
		@RequestParam("firstResult") int firstResult,
		@RequestParam("maxResults") int maxResults
	){
		TaskQuery taskQuery = taskService.createTaskQuery().or().taskAssignee(assignee).taskCandidateUser(assignee).endOr();
		if(StringUtils.isNotBlank(descriptionLike)) {
		 	taskQuery.taskDescriptionLike(descriptionLike);
		}
		
		long total = taskQuery.count();

		if("dueDate".equals(sortBy)) {
		 	taskQuery.orderByDueDate();
		} else if("description".equals(sortBy)) {
			taskQuery.orderByTaskDescription();
		} else if("name".equals(sortBy)) {
			taskQuery.orderByTaskName();
		} else if("priority".equals(sortBy)) {
			taskQuery.orderByTaskPriority();
		} else if("created".equals(sortBy)) {
			taskQuery.orderByTaskCreateTime();
		}

		if("desc".equalsIgnoreCase(sortOrder)) {
			taskQuery.desc();
		} else if("asc".equalsIgnoreCase(sortOrder)) {
			taskQuery.asc();
		}

		List<Task>  tasks = taskQuery.initializeFormKeys().listPage(firstResult, maxResults);
		JSONArray jsonArr = new JSONArray();
		if(tasks != null) {
			for(Task task: tasks) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", task.getId());
				jsonObj.put("name", task.getName());
				jsonObj.put("assignee", task.getAssignee());
				jsonObj.put("created", task.getCreateTime());
				jsonObj.put("due", task.getDueDate());
				jsonObj.put("followUp", task.getFollowUpDate());
				jsonObj.put("delegationState", task.getDelegationState());
				jsonObj.put("description", task.getDescription());
				jsonObj.put("executionId", task.getExecutionId());
				jsonObj.put("owner", task.getOwner());
				jsonObj.put("parentTaskId", task.getParentTaskId());
				jsonObj.put("priority", task.getPriority());
				jsonObj.put("processDefinitionId", task.getProcessDefinitionId());
				jsonObj.put("processInstanceId", task.getProcessInstanceId());
				jsonObj.put("taskDefinitionKey", task.getTaskDefinitionKey());
				jsonObj.put("caseExecutionId", task.getCaseExecutionId());
				jsonObj.put("caseInstanceId", task.getCaseInstanceId());
				jsonObj.put("caseDefinitionId", task.getCaseDefinitionId());
				jsonObj.put("suspended", task.isSuspended());
				jsonObj.put("formKey", task.getFormKey());
				jsonObj.put("tenantId", task.getTenantId());

				jsonArr.add(jsonObj);
			}
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("total", total);
		jsonResult.put("rows", jsonArr);		
		return ResponseEntity.ok(ResultUtil.successJson(jsonResult)); 
    }
    
}