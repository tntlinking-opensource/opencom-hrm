package com.geeke.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统附件Controller
 * @author szy
 * @version 2021-09-22
 */
@Api(value = "/sys/sysFile", tags = {"系统附件Controller"})
@RestController
@RequestMapping(value = "/sys/sysFile")
public class SysFileController extends BaseController {

	@Autowired
	private SysFileService sysFileService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取系统附件", notes = "通过id获取系统附件", httpMethod = "GET",response =SysFile.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SysFile entity = sysFileService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取系统附件（分页）", notes = "通过搜索条件获取系统附件（分页）", httpMethod = "POST",response =SysFile.class)
    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<SysFile> result = sysFileService.listPage(searchParams.getParams(), searchParams.getOffset(), searchParams.getLimit(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取系统附件", notes = "通过搜索条件获取系统附件", httpMethod = "POST",response =SysFile.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<SysFile> result = sysFileService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysFile", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存系统附件", notes = "保存系统附件,并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SysFile entity) {
        String id = sysFileService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
  
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysFile", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除系统附件", notes = "删除系统附件，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SysFile entity) {
        int rows = sysFileService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加系统附件", notes = "批量添加系统附件，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<SysFile> entitys) {
        List<String> ids = sysFileService.saves(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
    

    @ApiOperation(value = "批量更新系统附件（未用,未接入minio）", notes = "批量更新系统附件，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<SysFile> entitys) {
        List<String> ids = sysFileService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }
    

    @ApiOperation(value = "批量删除系统附件（未用，未接入minio）", notes = "批量删除系统附件，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<SysFile> entitys) {
        int rows = sysFileService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

}