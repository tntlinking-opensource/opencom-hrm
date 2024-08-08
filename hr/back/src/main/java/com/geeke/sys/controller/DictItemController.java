package com.geeke.sys.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.service.DictItemService;
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
 * 字典管理Controller
 * @author
 * @version
 */
@Api(value = "/sys/dictItem", tags = {"字典管理-字典项Controller"})
@RestController
@RequestMapping(value = "/sys/dictItem")
public class DictItemController extends BaseController {

    @Autowired
    private DictItemService dictItemService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取字典项", notes = "通过id获取字典项", httpMethod = "GET",response =DictItem.class )
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        DictItem entity = dictItemService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取字典项（分页）", notes = "通过搜索条件获取字典项（分页）", httpMethod = "POST",response=DictItem.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<DictItem> result = dictItemService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取字典项", notes = "通过搜索条件获取字典项", httpMethod = "POST",response = DictItem.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<DictItem> result = dictItemService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DictItem", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存字典项", notes = "保存字典项，并且返回id", httpMethod = "POST",response = String.class)
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody DictItem entity) {
        String id = dictItemService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DictItem", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除字典项", notes = "删除字典项，并且返回删除行数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody DictItem entity) {
        int rows = dictItemService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加字典项", notes = "批量添加字典项，并且返回ids（list）", httpMethod = "POST",response = String.class,responseContainer = "List")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<DictItem> entitys) {
        List<String> ids = dictItemService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新字典项", notes = "批量更新字典项，并且返回ids（list）", httpMethod = "POST",response = String.class,responseContainer = "List")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<DictItem> entitys) {
        List<String> ids = dictItemService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除字典项", notes = "批量删除字典项，并且返回删除行数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<DictItem> entitys) {
        int rows = dictItemService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
