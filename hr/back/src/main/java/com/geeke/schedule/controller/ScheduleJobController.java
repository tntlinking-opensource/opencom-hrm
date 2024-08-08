package com.geeke.schedule.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.schedule.service.ScheduleJobService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;

import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务管理Controller
 * @author
 * @version
 */
@Api(value = "/schedule/scheduleJob", tags = {"定时任务管理Controller"})
@RestController
@RequestMapping(value = "/schedule/scheduleJob")
public class ScheduleJobController extends BaseController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取定时任务详情", notes = "通过id获取定时任务详情", httpMethod = "GET",response = ScheduleJob.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        ScheduleJob entity = scheduleJobService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取定时任务详情", notes = "通过搜索条件获取定时任务详情", httpMethod = "POST",response = ScheduleJob.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<ScheduleJob> result = scheduleJobService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过条件获取定时任务详情", notes = "通过条件获取定时任务详情", httpMethod = "POST",response = ScheduleJob.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<ScheduleJob> result = scheduleJobService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存定时任务详情", notes = "保存定时任务详情，并且返回id", httpMethod = "POST",response = String.class)
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody ScheduleJob entity) {
        String id = scheduleJobService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除定时任务详情", notes = "删除定时任务详情，并且返回删除条数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody ScheduleJob entity) {
        int rows = scheduleJobService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加定时任务", notes = "批量添加定时任务，并且返回ids（list）", httpMethod = "POST",response = String.class,responseContainer = "List")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<ScheduleJob> entitys) {
        List<String> ids = scheduleJobService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新定时任务", notes = "批量更新定时任务，并且返回ids（list）", httpMethod = "POST",response = String.class,responseContainer = "List")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<ScheduleJob> entitys) {
        List<String> ids = scheduleJobService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除定时任务", notes = "批量删除定时任务，并且返回删除条数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<ScheduleJob> entitys) {
        int rows = scheduleJobService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
