package com.geeke.sys.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.sys.entity.DictType;
import com.geeke.sys.service.DictTypeService;
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
@Api(value = "/sys/dictType", tags = {"字典管理-字典类型Controller"})
@RestController
@RequestMapping(value = "/sys/dictType")
public class DictTypeController extends BaseController {

    @Autowired
    private DictTypeService dictTypeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取字典类型", notes = "通过id获取字典类型", httpMethod = "GET",response = DictType.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        DictType entity = dictTypeService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索获取字典类型（分页）", notes = "通过搜索获取字典类型（分页）", httpMethod = "POST",response = DictType.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<DictType> result = dictTypeService.listPage(
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
    @ApiOperation(value = "通过搜索获取字典类型", notes = "通过搜索获取字典类型", httpMethod = "POST",response = DictType.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<DictType> result = dictTypeService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DictType", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存字典类型", notes = "保存字典类型，并且返回id", httpMethod = "POST",response = String.class)
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody DictType entity) {
        String id = dictTypeService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DictType", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除字典类型", notes = "删除字典类型，并且返回删除条数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody DictType entity) {
        int rows = dictTypeService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "添加字典类型", notes = "添加字典类型，并且返回ids（list）", httpMethod = "POST",response = String.class,responseContainer = "List")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<DictType> entitys) {
        List<String> ids = dictTypeService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "更新字典类型", notes = "更新字典类型", httpMethod = "POST",response = String.class,responseContainer = "List")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<DictType> entitys) {
        List<String> ids = dictTypeService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除字典类型", notes = "批量删除字典类型，并且返回ids（list）", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<DictType> entitys) {
        int rows = dictTypeService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "DictType", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "导入字典类型", notes = "导入字典类型", httpMethod = "POST",response = String.class)
    @PostMapping({ "importJson" })
    public ResponseEntity<JSONObject> importJson(@RequestBody DictType entity) {
        String id = this.dictTypeService.importJson(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
}
