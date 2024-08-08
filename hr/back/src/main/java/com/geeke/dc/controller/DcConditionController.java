package com.geeke.dc.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.dc.entity.DcCondition;
import com.geeke.dc.service.DcConditionService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
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

/**
 * 查询条件Controller
 * @author
 * @version
 */
@Api(value = "/dc/dcCondition", tags = {"低代码-查询条件Controller"})
@RestController
@RequestMapping(value = "/dc/dcCondition")
public class DcConditionController extends BaseController {

    @Autowired
    private DcConditionService dcConditionService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取查询条件", notes = "通过id获取查询条件", httpMethod = "GET",response = DcCondition.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        DcCondition entity = dcConditionService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取查询条件（分页）", notes = "通过搜索条件获取查询条件（分页）", httpMethod = "POST",response = DcCondition.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<DcCondition> result = dcConditionService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取查询条件", notes = "通过搜索条件获取查询条件", httpMethod = "POST",response = DcCondition.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<DcCondition> result = dcConditionService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DcCondition", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存查询条件", notes = "保存查询条件，并且返回id", httpMethod = "POST",response = String.class)
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody DcCondition entity) {
        String id = dcConditionService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DcCondition", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除查询条件", notes = "删除查询条件，并且返回删除条数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody DcCondition entity) {
        int rows = dcConditionService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiOperation(value = "批量添加查询条件", notes = "批量添加查询条件，并且返回ids（list）", httpMethod = "POST",response = DcCondition.class,responseContainer = "List")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<DcCondition> entitys) {
        List<String> ids = dcConditionService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新查询条件", notes = "批量更新查询条件，并且返回ids（list）", httpMethod = "POST",response = DcCondition.class,responseContainer = "List")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<DcCondition> entitys) {
        List<String> ids = dcConditionService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除查询条件", notes = "批量删除查询条件，并且返回删除条数", httpMethod = "POST",response =Integer.class )
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<DcCondition> entitys) {
        int rows = dcConditionService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 移动查询条件触发
     * @param conditionList
     * @param userId
     * @param routerId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<DcCondition>", name = "conditionList", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "userId", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "routerId", value = "", required = true)
    })
    @ApiOperation(value = "移动查询条件触发", notes = "移动查询条件触发", httpMethod = "POST",response = DcCondition.class,responseContainer = "List")
    @PostMapping(value = "bulkUpdateSort/{userId}/{routerId}")
    public ResponseEntity<JSONObject> bulkUpdateSort(
        @RequestBody List<DcCondition> conditionList,
        @PathVariable("userId") String userId,
        @PathVariable("routerId") String routerId
    ) {
        List<DcCondition> result = dcConditionService.bulkUpdateSort(conditionList, userId, routerId);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
}
