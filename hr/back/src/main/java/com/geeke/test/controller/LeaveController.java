
package com.geeke.test.controller;

import java.util.List;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.geeke.test.entity.Leave;
import com.geeke.test.service.LeaveService;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.wf.entity.WfDraft;

/**
 * 请假管理Controller
 * @author lys
 * @version 2022-01-28
 */
@Api(value = "/test/leave", tags = {"请假管理Controller"})
@RestController
@RequestMapping(value = "/test/leave")
public class LeaveController extends BaseController {

	@Autowired
	private LeaveService leaveService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取请假管理", notes = "通过id获取请假管理", httpMethod = "GET",response = Leave.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Leave entity = leaveService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取请假管理（分页）", notes = "通过搜索条件获取请假管理（分页）", httpMethod = "POST",response = Leave.class)
    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Leave> result = leaveService.listPage(searchParams.getParams(), searchParams.getOffset(), searchParams.getLimit(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取请假管理", notes = "通过搜索条件获取请假管理", httpMethod = "POST",response = Leave.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Leave> result = leaveService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Leave", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存请假管理", notes = "保存请假管理")
    public ResponseEntity<JSONObject> save(@RequestBody Leave entity) {
        String id = leaveService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
  
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Leave", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除请假管理", notes = "删除请假管理", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Leave entity) {
        int rows = leaveService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    
    /**
     * 通过流程任务Id获取业务对象
     * @param instanceId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "", required = true)
    })
    @ApiOperation(value = "通过流程任务Id获取业务对象", notes = "通过流程任务Id获取业务对象", httpMethod = "GET",response = Leave.class)
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        Leave entity = leaveService.getByTaskId(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }    
    
     /**
     * 保存草稿
     * @param strDraft 草稿对象
     * @param entity    业务对象
     * @param attachments 审批附件
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strDraft", value = "草稿对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "审批附件", required = true)
    })
    @ApiOperation(value = "保存草稿", notes = "保存草稿", httpMethod = "POST")
    @PostMapping(value = "/saveDraft")
    public ResponseEntity<JSONObject> saveDraft(@RequestParam("strDraft") String strDraft, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments  ) throws java.io.IOException {
        Leave entity = JSONObject.parseObject(strEntity, Leave.class);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        int ret = leaveService.saveDraft(wfDraft, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(ret));
    }
    
    /**
     * 发起申请
     * @param procDefId 流程定义Id
     * @param entity    业务对象
     * @param attachments 审批附件
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "procDefId", value = "流程定义Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "审批附件", required = true)
    })
    @ApiOperation(value = "发起申请", notes = "发起申请", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{procDefId}/create")
    public ResponseEntity<JSONObject> create(@PathVariable("procDefId") String procDefId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments  ) throws java.io.IOException {
        Leave entity = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.create(procDefId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }
    
     /**
     * 重新发起申请
     * @param taskId   任务Id
     * @param strEntity 业务对象
     * @param attachments 审批附件
     * @return
     */
     @ApiImplicitParams({
             @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
             @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "业务对象", required = true),
             @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "审批附件", required = true)
     })
     @ApiOperation(value = "重新发起申请", notes = "重新发起申请", httpMethod = "POST",response = ActResult.class)
     @PostMapping(value = "/{taskId}/reapply")
    public ResponseEntity<JSONObject> reapply(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.reapply(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }


    /**
     * 同意申请
     * @param taskId   任务Id
     * @param strEntity 业务对象
     * @param attachments 附件列表
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "业务对象", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "附件列表", required = true)
    })
    @ApiOperation(value = "同意申请", notes = "同意申请", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/agree")
    public ResponseEntity<JSONObject> agree(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.agree(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

      
    /**
     * 转派任务
     * @param taskId    任务Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "转派任务", notes = "转派任务", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/forward")
    public ResponseEntity<JSONObject> forward(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.forward(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }
    
    /**
     * 委派任务
     * @param taskId    任务Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "委派任务", notes = "委派任务", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/commission")
    public ResponseEntity<JSONObject> commission(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.commission(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }    
    
    /**
     * 拟办任务 ，  办理委派过来的任务，把任务转给任务持有者
     * @param taskId    任务Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "拟办任务 ，  办理委派过来的任务，把任务转给任务持有者", notes = "拟办任务 ，  办理委派过来的任务，把任务转给任务持有者", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/propose")
    public ResponseEntity<JSONObject> propose(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.propose(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }    

    /**
     * 退回任务到指定的节点和用户
     * @param taskId    任务Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "退回任务到指定的节点和用户", notes = "退回任务到指定的节点和用户", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/back")
    public ResponseEntity<JSONObject> back(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.back(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }    
    
    /**
     * 退回任务到指定的节点和用户
     * @param taskId    任务Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "退回任务到指定的节点和用户", notes = "退回任务到指定的节点和用户", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/reject")
    public ResponseEntity<JSONObject> reject(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        ActResult actResult = leaveService.reject(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }  
    
    /**
     * 终止任务的申请流程
     * @param taskId    任务Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "终止任务的申请流程", notes = "终止任务的申请流程", httpMethod = "POST")
    @PostMapping(value = "/{taskId}/terminate")
    public ResponseEntity<JSONObject> terminate(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments ) throws java.io.IOException {
        Leave entity  = JSONObject.parseObject(strEntity, Leave.class);
        int rows = leaveService.terminate(taskId, entity, attachments );
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    
    
    /**
     * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
     * @param taskId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "", required = true)
    })
    @ApiOperation(value = "撤回已处理的任务，仅在后续节点任务未处理时可以撤回", notes = "撤回已处理的任务，仅在后续节点任务未处理时可以撤回", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{taskId}/reverse")
    public ResponseEntity<JSONObject> reverse(@PathVariable("taskId") String taskId) {
        Leave entity = leaveService.getByTaskId(taskId);
        ActResult actResult = leaveService.reverse(taskId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }
    
    /**
     * 列举可回退的节点和用户
     * @param taskId    任务Id
     * @param entity    业务对象
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "taskId", value = "任务Id", required = true)
    })
    @ApiOperation(value = "列举可回退的节点和用户", notes = "列举可回退的节点和用户", httpMethod = "GET",response = ActResult.class)
    @GetMapping(value = "/{taskId}/listBackActivity")
    public ResponseEntity<JSONObject> listBackActivity(@PathVariable("taskId") String taskId) {
        ActResult actResult = leaveService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

}