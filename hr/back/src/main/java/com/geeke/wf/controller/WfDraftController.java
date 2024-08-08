package com.geeke.wf.controller;

import java.io.IOException;
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
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.sys.entity.Attachment;
import com.geeke.utils.ResultUtil;
import com.geeke.wf.entity.WfDraft;
import com.geeke.wf.service.WfDraftService;

/**
 * 我的草稿Controller
 * @author lys
 * @version 2021-04-27
 */
@Api(value = "/wf/wfDraft", tags = {"我的草稿Controller"})
@RestController
@RequestMapping(value = "/wf/wfDraft")
public class WfDraftController extends BaseController {

	@Autowired
	private WfDraftService wfDraftService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取我的草稿", notes = "通过id获取我的草稿", httpMethod = "GET",response =WfDraft.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        WfDraft entity = wfDraftService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取我的草稿（分页）", notes = "通过搜索条件获取我的草稿（分页）", httpMethod = "POST",response =WfDraft.class)
    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<WfDraft> result = wfDraftService.listPage(searchParams.getParams(), searchParams.getOffset(), searchParams.getLimit(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取我的草稿", notes = "通过搜索条件获取我的草稿", httpMethod = "POST",response =WfDraft.class ,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<WfDraft> result = wfDraftService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "files", value = "", required = true)
    })
    @ApiOperation(value = "保存我的草稿", notes = "保存我的草稿，并且返回id", httpMethod = "POST",response =String.class)
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> create(@RequestParam("strEntity") String strEntity, @RequestParam("attachments") MultipartFile[] files) throws IOException {
    	WfDraft entity  = JSONObject.parseObject(strEntity, WfDraft.class);
        String id = wfDraftService.save(entity, files).getId();
        return ResponseEntity.ok(ResultUtil.successSaveDraft(id));
    }
  
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "WfDraft", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除我的草稿", notes = "删除我的草稿，并且返回删除行数", httpMethod = "POST",response =Integer.class)
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody WfDraft entity) {
        int rows = wfDraftService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "bizId", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取文件附件", notes = "通过id获取文件附件列表", httpMethod = "GET",response =Attachment.class,responseContainer = "List")
    @GetMapping("/{bizId}/attachment")
    public ResponseEntity<JSONObject> getAttachments(@PathVariable("bizId") String bizId) {
        List<Attachment> attachements = wfDraftService.getAttachments(bizId);
        return ResponseEntity.ok(ResultUtil.successJson(attachements));
    }
    
}