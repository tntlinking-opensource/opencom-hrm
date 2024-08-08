package com.geeke.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.admin.entity.Resource;
import com.geeke.admin.service.ResourceService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
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
 * 资源管理Controller
 * @author
 * @version
 */
@Api(value = "/admin/resource", tags = {"资源管理Controller"})
@RestController
@RequestMapping(value = "/admin/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取资源管理", notes = "通过id获取资源管理", httpMethod = "GET",response = Resource.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Resource entity = resourceService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取资源管理（分页）", notes = "通过搜索条件获取资源管理（分页）", httpMethod = "POST",response = Resource.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Resource> result = resourceService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取资源管理", notes = "通过搜索条件获取资源管理", httpMethod = "POST",response = Resource.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Resource> result = resourceService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Resource", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存资源管理", notes = "保存资源管理，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Resource entity) {
        String id = resourceService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Resource", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除资源管理", notes = "删除资源管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Resource entity) {
        int rows = resourceService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加资源管理", notes = "批量添加资源管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<Resource> entitys) {
        List<String> ids = resourceService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新资源管理", notes = "批量更新资源管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<Resource> entitys) {
        List<String> ids = resourceService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除资源管理", notes = "批量删除资源管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<Resource> entitys) {
        int rows = resourceService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
