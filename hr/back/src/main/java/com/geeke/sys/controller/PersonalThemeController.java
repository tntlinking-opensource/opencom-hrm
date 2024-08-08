package com.geeke.sys.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.sys.entity.PersonalTheme;
import com.geeke.sys.service.PersonalThemeService;
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
 * 自定义主题Controller
 * @author
 * @version
 */
@Api(value = "/sys/personalTheme", tags = {"自定义主题Controller"})
@RestController
@RequestMapping(value = "/sys/personalTheme")
public class PersonalThemeController extends BaseController {

    @Autowired
    private PersonalThemeService personalThemeService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取自定义主题", notes = "通过id获取自定义主题", httpMethod = "GET",response = PersonalTheme.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        PersonalTheme entity = personalThemeService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取自定义主题（分页）", notes = "通过搜索条件获取自定义主题（分页）", httpMethod = "POST",response = PersonalTheme.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<PersonalTheme> result = personalThemeService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取自定义主题", notes = "通过搜索条件获取自定义主题", httpMethod = "POST",response = PersonalTheme.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<PersonalTheme> result = personalThemeService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PersonalTheme", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存自定义主题", notes = "保存自定义主题，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody PersonalTheme entity) {
        String id = personalThemeService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "PersonalTheme", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除自定义主题", notes = "删除自定义主题，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody PersonalTheme entity) {
        int rows = personalThemeService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加自定义主题", notes = "批量添加自定义主题，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<PersonalTheme> entitys) {
        List<String> ids = personalThemeService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新自定义主题", notes = "批量更新自定义主题，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<PersonalTheme> entitys) {
        List<String> ids = personalThemeService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除自定义主题", notes = "批量删除自定义主题，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<PersonalTheme> entitys) {
        int rows = personalThemeService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
