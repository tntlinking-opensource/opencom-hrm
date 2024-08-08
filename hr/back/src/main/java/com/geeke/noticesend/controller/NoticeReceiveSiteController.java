package com.geeke.noticesend.controller;

import java.util.Date;
import java.util.List;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.noticesend.entity.NoticeReceiveSite;
import com.geeke.noticesend.service.NoticeReceiveSiteService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

/**
 * 消息接收站点Controller
 * @author ycy
 * @version 2021-11-24
 */
@Api(value = "/noticesend/noticeReceiveSite", tags = {"消息接收站点Controller"})
@RestController
@RequestMapping(value = "/noticesend/noticeReceiveSite")
public class NoticeReceiveSiteController extends BaseController {

	@Autowired
	private NoticeReceiveSiteService noticeReceiveSiteService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取消息接收站点", notes = "通过id获取消息接收站点", httpMethod = "GET",response = NoticeReceiveSite.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        NoticeReceiveSite entity = noticeReceiveSiteService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取消息接收站点（分页）", notes = "通过搜索条件获取消息接收站点（分页）", httpMethod = "POST",response = NoticeReceiveSite.class)
    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<NoticeReceiveSite> result = noticeReceiveSiteService.listPage(searchParams.getParams(), searchParams.getOffset(), searchParams.getLimit(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取消息接收站点", notes = "通过搜索条件获取消息接收站点", httpMethod = "POST",response = NoticeReceiveSite.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<NoticeReceiveSite> result = noticeReceiveSiteService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "NoticeReceiveSite", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存消息接收站点", notes = "保存消息接收站点", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody NoticeReceiveSite entity) {
        entity.setReadTime(new Date());
        String id = noticeReceiveSiteService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
  
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "NoticeReceiveSite", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除消息接收站点", notes = "删除消息接收站点", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody NoticeReceiveSite entity) {
        int rows = noticeReceiveSiteService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加消息接收站点", notes = "批量添加消息接收站点，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<NoticeReceiveSite> entitys) {
        List<String> ids = noticeReceiveSiteService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
    

    @ApiOperation(value = "批量更新消息接收站点", notes = "批量更新消息接收站点，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<NoticeReceiveSite> entitys) {
        List<String> ids = noticeReceiveSiteService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
    

    @ApiOperation(value = "批量删除消息接收站点", notes = "批量删除消息接收站点，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<NoticeReceiveSite> entitys) {
        int rows = noticeReceiveSiteService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

}