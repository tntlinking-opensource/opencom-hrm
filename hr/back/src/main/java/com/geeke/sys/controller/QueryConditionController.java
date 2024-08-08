package com.geeke.sys.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.geeke.sys.entity.QueryCondition;
import com.geeke.sys.service.QueryConditionService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

/**
 * 查询条件Controller
 * @author lys
 * @version 2021-07-05
 */
@Api(value = "/sys/queryCondition", tags = {"查询条件Controller"})
@RestController
@RequestMapping(value = "/sys/queryCondition")
public class QueryConditionController extends BaseController {

	@Autowired
	private QueryConditionService queryConditionService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取查询条件", notes = "通过id获取查询条件", httpMethod = "GET",response = QueryCondition.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        QueryCondition entity = queryConditionService.get(id);
        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取查询条件（分页）", notes = "通过搜索条件获取查询条件（分页）", httpMethod = "POST",response = QueryCondition.class)
    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<QueryCondition> result = queryConditionService.listPage(searchParams.getParams(), searchParams.getOffset(), searchParams.getLimit(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取查询条件", notes = "通过搜索条件获取查询条件", httpMethod = "POST",response = QueryCondition.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<QueryCondition> result = queryConditionService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryCondition", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存查询条件", notes = "保存查询条件", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody QueryCondition entity) {
    	String id = queryConditionService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
  
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryCondition", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除查询条件", notes = "删除查询条件", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody QueryCondition entity) {
        int rows = queryConditionService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

	
}