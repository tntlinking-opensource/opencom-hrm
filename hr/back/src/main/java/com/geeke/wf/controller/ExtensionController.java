package com.geeke.wf.controller;

import java.util.Collection;
import java.util.HashMap;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.google.common.collect.Maps;


/**
 * 流程Bpmn的扩展属性 Controller
 * @author lys
 * @version 2023-03-30
 */
@RestController
@RequestMapping(value = "/wf/extension")
public class ExtensionController extends BaseController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;

	/**
	 * 获取任务节点的扩展配置信息
	 * @param taskId
	 * @return
	 */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getTaskExtension(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return getTaskDefExtension(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
    }


	/**
	 * 获取流程全局扩展配置信息
	 * @param processId
	 * @return
	 */
    @GetMapping("/process/{processId}")
    public ResponseEntity<JSONObject> getGlobalExtension(@PathVariable("processId") String processId){
		HashMap<Object, Object> extensions = Maps.newHashMap();
      	BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processId);

        Collection<Process> processCollection = bpmnModelInstance.getModelElementsByType(Process.class);
        for (Process process: processCollection) {
            ExtensionElements extensionElements = process.getExtensionElements();
			if(extensionElements == null) {
				continue;
			}
			CamundaProperties camundaProperties = extensionElements.getElementsQuery().filterByType(CamundaProperties.class).singleResult();
			if(camundaProperties == null) {
				continue;
			}

			Collection<CamundaProperty> properties = camundaProperties.getCamundaProperties();
			for (CamundaProperty property : properties) {
				extensions.put(property.getCamundaName(), property.getCamundaValue());
			}
        }
 
        return ResponseEntity.ok(ResultUtil.successJson(extensions));
    }

	/**
	 * 获取流程开始节点扩展配置信息
	 * @param processId
	 * @return
	 */
	@GetMapping("/process/{processId}/start")
    public ResponseEntity<JSONObject> getStartExtension(@PathVariable("processId") String processId){
		HashMap<Object, Object> extensions = Maps.newHashMap();
      	BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processId);

		Collection<StartEvent> startEventCollection = bpmnModelInstance.getModelElementsByType(StartEvent.class);
		for (StartEvent startEvent: startEventCollection) {
			ExtensionElements extensionElements = startEvent.getExtensionElements();
			if(extensionElements == null) {
				continue;
			}
			CamundaProperties camundaProperties = extensionElements.getElementsQuery().filterByType(CamundaProperties.class).singleResult();
			if( camundaProperties == null) {
				continue;
			}
			Collection<CamundaProperty> properties = camundaProperties.getCamundaProperties();
			for (CamundaProperty property : properties) {
				extensions.put(property.getCamundaName(), property.getCamundaValue());
			}
		}
        return ResponseEntity.ok(ResultUtil.successJson(extensions));
    }  

	/**
	 * 获取流程指定节点扩展配置信息
	 * @param processId
	 * @return
	 */
	@GetMapping("/process/{processId}/{taskDefinitionKey}")
    public ResponseEntity<JSONObject> getTaskDefExtension(@PathVariable("processId") String processId, @PathVariable("taskDefinitionKey") String taskDefinitionKey){
		HashMap<Object, Object> extensions = Maps.newHashMap();
      	BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processId);

		BaseElement el = (BaseElement) bpmnModelInstance.getModelElementById(taskDefinitionKey);
		ExtensionElements extensionElements = el.getExtensionElements();
		if(extensionElements != null) {
			CamundaProperties camundaProperties =	extensionElements.getElementsQuery().filterByType(CamundaProperties.class).singleResult();
			if(camundaProperties != null) {
				Collection<CamundaProperty> properties = camundaProperties.getCamundaProperties();
				for (CamundaProperty property : properties) {
					extensions.put(property.getCamundaName(), property.getCamundaValue());
				}
			}
		}
		return ResponseEntity.ok(ResultUtil.successJson(extensions));
    }  

	/**
	 * 获取流程所有扩展配置信息
	 * @param processId
	 * @return
	 */
	@GetMapping("/process/{processId}/all")
    public ResponseEntity<JSONObject> getAllExtension(@PathVariable("processId") String processId){
		HashMap<Object, Object> extensions = Maps.newHashMap();
      	BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processId);

        Collection<ExtensionElements> extensionElementsCollection = bpmnModelInstance.getModelElementsByType(ExtensionElements.class);
        for (ExtensionElements extensionElements: extensionElementsCollection) {
			CamundaProperties camundaProperties = extensionElements.getElementsQuery().filterByType(CamundaProperties.class).singleResult();
            if(camundaProperties == null) {
				continue;
			}
			Collection<CamundaProperty> properties = camundaProperties.getCamundaProperties();
            for (CamundaProperty property : properties) {
				extensions.put(property.getCamundaName(), property.getCamundaValue());
            }
        }
 
        return ResponseEntity.ok(ResultUtil.successJson(extensions));
    }  
}