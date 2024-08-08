package com.geeke.induction.contract.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.induction.contract.service.InductionBatchContractService;
import com.geeke.sys.controller.BaseController;
import com.geeke.test.entity.Leave;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;

import com.geeke.wf.entity.WfDraft;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 合约发起Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/induction/contract/inductionBatchContract")
public class InductionBatchContractController extends BaseController {

    @Autowired
    private InductionBatchContractService inductionBatchContractService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        InductionBatchContract entity = inductionBatchContractService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<InductionBatchContract> result = inductionBatchContractService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<InductionBatchContract> result = inductionBatchContractService.listAll(
            searchParams.getParams(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody InductionBatchContract entity) {
        String id = inductionBatchContractService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody InductionBatchContract entity) {
        int rows = inductionBatchContractService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<InductionBatchContract> entitys) {
        List<String> ids = inductionBatchContractService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<InductionBatchContract> entitys) {
        List<String> ids = inductionBatchContractService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<InductionBatchContract> entitys) {
        int rows = inductionBatchContractService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @GetMapping(value = "generate/{id}")
    public ResponseEntity<JSONObject> generate(@PathVariable("id") String id) {
        InductionBatchContract entity = inductionBatchContractService.generate(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @GetMapping(value = "start/{id}")
    public ResponseEntity<JSONObject> start(@PathVariable("id") String id) {
        inductionBatchContractService.start(id);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @GetMapping(value = "viewContract/{id}")
    public ResponseEntity<JSONObject> viewContract(@PathVariable("id") String id) {
        String pageUrl = inductionBatchContractService.viewContract(id);
        return ResponseEntity.ok(ResultUtil.successJson(pageUrl));
    }

    @GetMapping(value = "upload/{id}")
    public ResponseEntity<JSONObject> uploadContract(@PathVariable("id") String id, HttpServletResponse response) throws Exception {
        inductionBatchContractService.uploadContract(id, response);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @PostMapping(value = "bulkGenerate")
    public ResponseEntity<JSONObject> bulkGenerate(@RequestBody List<InductionBatchContract> entitys) {
        List<String> ids = inductionBatchContractService.bulkGenerate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkStart")
    public ResponseEntity<JSONObject> bulkStart(@RequestBody List<InductionBatchContract> entitys) {
        List<String> ids = inductionBatchContractService.bulkStart(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @GetMapping(value = "report/{id}")
    public ResponseEntity<JSONObject> report(@PathVariable("id") String id) {
        String pageUrl = inductionBatchContractService.report(id);
        return ResponseEntity.ok(ResultUtil.successJson(pageUrl));
    }

    /**
     * 发起发起签署
     * @param procDefId 流程定义Id
     * @param entity    业务对象
     * @return
     */
    @PostMapping(value = "/{procDefId}/firstCreate")
    public ResponseEntity<JSONObject> firstCreate(@PathVariable("procDefId") String procDefId, @RequestBody InductionBatchContract entity) throws java.io.IOException {
        ActResult actResult = inductionBatchContractService.firstCreate(procDefId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 批量发起签署
     * @param procDefId 流程定义Id
     * @param entitys    业务对象
     * @return
     */
    @PostMapping(value = "/{procDefId}/bulkSignCreate")
    public ResponseEntity<JSONObject> bulkSignCreate(@PathVariable("procDefId") String procDefId, @RequestBody List<InductionBatchContract> entitys) {
        List<ActResult> list = inductionBatchContractService.bulkSignCreate(procDefId, entitys);
        return ResponseEntity.ok(ResultUtil.successJson(list));
    }

    /**
     * 通过流程任务Id获取业务对象
     * @param taskId
     * @return
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        InductionBatchContract entity = inductionBatchContractService.getByTaskId(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    /**
     * 保存草稿
     * @param strDraft 草稿对象
     * @param strEntity    业务对象
     * @param attachments 审批附件
     * @return
     */
    @PostMapping(value = "/saveDraft")
    public ResponseEntity<JSONObject> saveDraft(
            @RequestParam(value = "strDraft", required = false) String strDraft,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        int ret = inductionBatchContractService.saveDraft(wfDraft, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(ret));
    }

    /**
     * 发起申请
     * @param procDefId 流程定义Id
     * @param strEntity    业务对象
     * @param attachments 审批附件
     * @return
     */
    @PostMapping(value = "/{procDefId}/create")
    public ResponseEntity<JSONObject> create(
            @PathVariable("procDefId") String procDefId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.create(procDefId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 重新发起申请
     * @param taskId   任务Id
     * @param strEntity 业务对象
     * @param attachments 审批附件
     * @return
     */
    @PostMapping(value = "/{taskId}/reapply")
    public ResponseEntity<JSONObject> reapply(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.reapply(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 同意申请
     * @param taskId   任务Id
     * @param strEntity 业务对象
     * @param attachments 附件列表
     * @return
     */
    @PostMapping(value = "/{taskId}/agree")
    public ResponseEntity<JSONObject> agree(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.agree(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 转派任务
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/forward")
    public ResponseEntity<JSONObject> forward(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.forward(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 委派任务
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/commission")
    public ResponseEntity<JSONObject> commission(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.commission(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 拟办任务 ，  办理委派过来的任务，把任务转给任务持有者
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/propose")
    public ResponseEntity<JSONObject> propose(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.propose(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 退回任务到指定的节点和用户
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/back")
    public ResponseEntity<JSONObject> back(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.back(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 退回任务到指定的节点和用户
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/reject")
    public ResponseEntity<JSONObject> reject(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        ActResult actResult = inductionBatchContractService.reject(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 终止任务的申请流程
     * @param taskId    任务Id
     * @param strEntity    业务对象
     * @param attachments
     * @return
     */
    @PostMapping(value = "/{taskId}/terminate")
    public ResponseEntity<JSONObject> terminate(
            @PathVariable("taskId") String taskId,
            @RequestParam(value = "strEntity", required = false) String strEntity,
            @RequestParam(value = "attachments", required = false) MultipartFile[] attachments
    ) throws java.io.IOException {
        InductionBatchContract entity = JSONObject.parseObject(strEntity, InductionBatchContract.class);
        int rows = inductionBatchContractService.terminate(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
     * @param taskId
     * @return
     */
    @PostMapping(value = "/{taskId}/reverse")
    public ResponseEntity<JSONObject> reverse(@PathVariable("taskId") String taskId) {
        InductionBatchContract entity = inductionBatchContractService.getByTaskId(taskId);
        ActResult actResult = inductionBatchContractService.reverse(taskId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 列举可回退的节点和用户
     * @param taskId    任务Id
     * @return
     */
    @GetMapping(value = "/{taskId}/listBackActivity")
    public ResponseEntity<JSONObject> listBackActivity(@PathVariable("taskId") String taskId) {
        ActResult actResult = inductionBatchContractService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

}
