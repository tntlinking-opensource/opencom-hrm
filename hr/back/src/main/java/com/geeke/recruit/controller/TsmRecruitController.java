package com.geeke.recruit.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.org.service.DepartmentService;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.google.common.collect.Lists;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * recruitController
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/recruit/tsmRecruit")
public class TsmRecruitController extends BaseController {

    @Autowired
    private TsmRecruitService tsmRecruitService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/number/{actStatus}")
    public ResponseEntity<JSONObject> getNumberOfPeople(@PathVariable("actStatus")String actStatus) {

        //招聘需求
        //actStatus 8044-审批完成
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("act_status", "=", actStatus));
        List<TsmRecruit> recruitNeeds = tsmRecruitService.listAll(params,"");

        //已招聘人数
        //candidate 相加
        params = Lists.newArrayList();
        List<TsmRecruit> recruited = tsmRecruitService.listAll(params,"");


        long recruitNeedsNum;
        long recruitedNum;
        Map<String, Long> numberMap = new HashMap<>();
        numberMap.put("recruitNeeds", recruitNeedsNum = recruitNeeds.stream()
            .mapToLong(x -> Long.parseLong(x.getStaffNum())).sum());
        numberMap.put("recruited", recruitedNum = recruited.stream()
            .map(TsmRecruit::getCandidate).filter(StrUtil::isNotBlank).mapToLong(Long::parseLong).sum());
        numberMap.put("waitRecruit", recruitNeedsNum - recruitedNum);

        return ResponseEntity.ok(ResultUtil.successJson(numberMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TsmRecruit entity = tsmRecruitService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<TsmRecruit> result = tsmRecruitService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<TsmRecruit> result = tsmRecruitService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TsmRecruit entity) {
        int rows = tsmRecruitService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 通过流程任务Id获取业务对象
     * @param taskId
     * @return
     */
    @GetMapping("/task/{taskId}")
    public ResponseEntity<JSONObject> getByTaskId(@PathVariable("taskId") String taskId) {
        TsmRecruit entity = tsmRecruitService.getByTaskId(taskId);
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
        int ret = tsmRecruitService.saveDraft(strDraft, strEntity, attachments);
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
          ActResult actResult = tsmRecruitService.createTsmRecruit(procDefId, strEntity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.reapply(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.agree(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.forward(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.commission(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.propose(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.back(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        ActResult actResult = tsmRecruitService.reject(taskId, entity, attachments);
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
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        int rows = tsmRecruitService.terminate(taskId, entity, attachments);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 撤回已处理的任务，仅在后续节点任务未处理时可以撤回
     * @param taskId
     * @return
     */
    @PostMapping(value = "/{taskId}/reverse")
    public ResponseEntity<JSONObject> reverse(@PathVariable("taskId") String taskId) {
        TsmRecruit entity = tsmRecruitService.getByTaskId(taskId);
        ActResult actResult = tsmRecruitService.reverse(taskId, entity);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }

    /**
     * 列举可回退的节点和用户
     * @param taskId    任务Id
     * @return
     */
    @GetMapping(value = "/{taskId}/listBackActivity")
    public ResponseEntity<JSONObject> listBackActivity(@PathVariable("taskId") String taskId) {
        ActResult actResult = tsmRecruitService.listBackActivity(taskId);
        return ResponseEntity.ok(ResultUtil.successJson(actResult));
    }
}
