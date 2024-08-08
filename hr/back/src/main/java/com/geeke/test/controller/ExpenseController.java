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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.test.entity.Expense;
import com.geeke.test.service.ExpenseService;
import com.geeke.utils.ResultUtil;

/**
 * 请假管理Controller
 * @author lys
 * @version 2020-06-29
 */
@Api(value = "/test/expense", tags = {"请假管理-费用申请Controller"})
@RestController
@RequestMapping(value = "/test/expense")
public class ExpenseController extends BaseController {

	@Autowired
	private ExpenseService expenseService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取费用申请", notes = "通过id获取费用申请", httpMethod = "GET",response = Expense.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Expense entity = expenseService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取费用申请（分页）", notes = "通过搜索条件获取费用申请（分页）", httpMethod = "POST",response = Expense.class)
    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Expense> result = expenseService.listPage(searchParams.getParams(), searchParams.getOffset(), searchParams.getLimit(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取费用申请", notes = "通过搜索条件获取费用申请", httpMethod = "POST",response = Expense.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Expense> result = expenseService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Expense", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存费用申请", notes = "保存费用申请,并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Expense entity) {
        String id = expenseService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
  
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Expense", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除费用申请", notes = "删除费用申请，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Expense entity) {
        int rows = expenseService.delete(entity);
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
    @ApiOperation(value = "通过流程任务Id获取业务对象", notes = "通过流程任务Id获取业务对象", httpMethod = "GET",response = Expense.class)
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        Expense entity = expenseService.getByTaskId(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }    
    
    /**
     * 发起申请
     * @param procDefId 流程定义Id
     * @param entity    业务对象
     * @param attachments
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "procDefId", value = "流程定义Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "attachments", value = "", required = true)
    })
    @ApiOperation(value = "发起申请", notes = "发起申请", httpMethod = "POST",response = ActResult.class)
    @PostMapping(value = "/{procDefId}/create")
    public ResponseEntity<JSONObject> create(@PathVariable("procDefId") String procDefId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.create(procDefId, entity, attachments);
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
    public ResponseEntity<JSONObject> agree(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.agree(taskId, entity, attachments);
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
    public ResponseEntity<JSONObject> forward(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.forward(taskId, entity, attachments);
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
    public ResponseEntity<JSONObject> commission(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.commission(taskId, entity, attachments);
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
    public ResponseEntity<JSONObject> propose(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.propose(taskId, entity, attachments);
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
    public ResponseEntity<JSONObject> back(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.back(taskId, entity, attachments);
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
    public ResponseEntity<JSONObject> reject(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        ActResult actResult = expenseService.reject(taskId, entity, attachments);
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
    public ResponseEntity<JSONObject> terminate(@PathVariable("taskId") String taskId, @RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] attachments) {
        Expense entity  = JSONObject.parseObject(strEntity, Expense.class);
        int rows = expenseService.terminate(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
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
        ActResult actResult = expenseService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

}