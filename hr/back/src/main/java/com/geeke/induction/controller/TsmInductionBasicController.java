package com.geeke.induction.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.request.InductionHomeSearchRequest;
import com.geeke.induction.request.TsmInductionBasicEnhanceRequest;
import com.geeke.induction.request.TsmInductionInfoCompletionRequest;
import com.geeke.induction.response.TsmInductionBasicEnhanceResponse;
import com.geeke.induction.response.TsmInductionFlowInitResponse;
import com.geeke.induction.response.TsmInductionInfoCompletionResponse;
import com.geeke.induction.service.TsmInductionBasicService;
import com.geeke.org.service.DepartmentService;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.wf.entity.WfDraft;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * inductionController
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/induction/tsmInductionBasic")
@RequiredArgsConstructor
public class TsmInductionBasicController extends BaseController {

    private final DepartmentService departmentService;
    private final TsmInductionBasicService tsmInductionBasicService;
    private final TsmRecruitService tsmRecruitService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TsmInductionBasic entity = tsmInductionBasicService.get(id);
        TsmInductionBasicEnhanceResponse response = new TsmInductionBasicEnhanceResponse()
            .tsmInductionBasicConvert(entity,tsmRecruitService);

        return ResponseEntity.ok(ResultUtil.successJson(response));
    }

    @GetMapping("/enhanceBasic/{id}")
    public ResponseEntity<JSONObject> getBasicInfoById(@PathVariable("id") String id) {
        TsmInductionBasic entity = tsmInductionBasicService.get(id);
        TsmInductionBasicEnhanceResponse response =new TsmInductionBasicEnhanceResponse()
            .tsmInductionBasicConvert(entity,tsmRecruitService);
        return ResponseEntity.ok(ResultUtil.successJson(response));

    }

    @GetMapping("/infoCompletion/{id}")
    public ResponseEntity<JSONObject> getInfoCompletionById(@PathVariable("id") String id) {
        TsmInductionBasic entity = tsmInductionBasicService.get(id);
        TsmInductionInfoCompletionResponse response =new TsmInductionInfoCompletionResponse()
            .tsmInductionBasicConvert(entity,tsmRecruitService);
        return ResponseEntity.ok(ResultUtil.successJson(response));
    }

    @GetMapping("/flowInit/{id}")
    public ResponseEntity<JSONObject> getFlowInit(@PathVariable("id") String id) {
        TsmInductionBasic entity = tsmInductionBasicService.get(id);
        TsmInductionFlowInitResponse response =new TsmInductionFlowInitResponse()
            .tsmInductionBasicConvert(entity,tsmRecruitService);

        return ResponseEntity.ok(ResultUtil.successJson(response));
    }

    @PostMapping(value = { "homeSearch", "" })
    public ResponseEntity<JSONObject> inductionHomeSearch(@RequestBody InductionHomeSearchRequest searchParams) {
        Page<TsmInductionBasicEnhanceResponse> result = tsmInductionBasicService.inductionHomeSearch(searchParams);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }


    /**************************************通用代码块**************************************/


    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TsmInductionBasicEnhanceResponse> result = tsmInductionBasicService.listEnhancePage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TsmInductionBasic> result = tsmInductionBasicService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TsmInductionBasic entity) {
        int rows = tsmInductionBasicService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 通过流程任务Id获取业务对象
     * @param taskId
     * @return
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        TsmInductionBasic entity = tsmInductionBasicService.getByTaskId(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(
            new TsmInductionInfoCompletionResponse().tsmInductionBasicConvert(entity,tsmRecruitService)));
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        int ret = tsmInductionBasicService.saveDraft(wfDraft, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(ret));
    }

    /**
     * 保存增强返回
     * @param request 请求体
     * @return
     */
    @PostMapping(value = "/saveEnhanceBasic")
    public ResponseEntity<JSONObject> saveEnhanceBasic(@RequestBody TsmInductionBasicEnhanceRequest request) {
        tsmInductionBasicService.saveEnhanceBasic(request);
        TsmInductionBasic entity = tsmInductionBasicService.get(request.getId());
        TsmInductionBasicEnhanceResponse response = new TsmInductionBasicEnhanceResponse()
            .tsmInductionBasicConvert(entity,tsmRecruitService)
            ;
        return ResponseEntity.ok(ResultUtil.successJson(response));
    }

    /**
     * 保存补全信息返回
     * @param request 请求体
     * @return
     */
    @PostMapping(value = "/saveInfoCompletion")
    public ResponseEntity<JSONObject> saveInfoCompletion(@RequestBody TsmInductionInfoCompletionRequest request) {
        tsmInductionBasicService.saveInfoCompletion(request);
        TsmInductionBasic entity = tsmInductionBasicService.get(request.getId());
        TsmInductionInfoCompletionResponse response = new TsmInductionInfoCompletionResponse()
            .tsmInductionBasicConvert(entity,tsmRecruitService);


        return ResponseEntity.ok(ResultUtil.successJson(response));
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.save(procDefId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.reapply(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.agree(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.forward(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.commission(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.propose(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.back(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        ActResult actResult = tsmInductionBasicService.reject(taskId, entity, attachments);
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
        TsmInductionBasic entity = JSONObject.parseObject(strEntity, TsmInductionBasic.class);
        int rows = tsmInductionBasicService.terminate(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
     * @param taskId
     * @return
     */
    @PostMapping(value = "/{taskId}/reverse")
    public ResponseEntity<JSONObject> reverse(@PathVariable("taskId") String taskId) {
        TsmInductionBasic entity = tsmInductionBasicService.getByTaskId(taskId);
        ActResult actResult = tsmInductionBasicService.reverse(taskId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 列举可回退的节点和用户
     * @param taskId    任务Id
     * @return
     */
    @GetMapping(value = "/{taskId}/listBackActivity")
    public ResponseEntity<JSONObject> listBackActivity(@PathVariable("taskId") String taskId) {
        ActResult actResult = tsmInductionBasicService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }
}
