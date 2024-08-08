package com.geeke.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.admin.entity.UserRole;
import com.geeke.admin.service.UserRoleService;
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
 * 用户管理Controller
 * @author
 * @version
 */
@Api(value = "/admin/userRole", tags = {"用户管理-用户角色Controller"})
@RestController
@RequestMapping(value = "/admin/userRole")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取用户角色", notes = "通过id获取用户角色", httpMethod = "GET", response = UserRole.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        UserRole entity = userRoleService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取用户角色（分页）", notes = "通过搜索条件获取用户角色（分页）", httpMethod = "POST", response = UserRole.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<UserRole> result = userRoleService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取用户角色", notes = "通过搜索条件获取用户角色", httpMethod = "POST", response = UserRole.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<UserRole> result = userRoleService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserRole", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存用户角色", notes = "保存用户角色，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody UserRole entity) {
        String id = userRoleService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserRole", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除用户角色", notes = "删除用户角色，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody UserRole entity) {
        int rows = userRoleService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加用户角色", notes = "批量添加用户角色，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<UserRole> entitys) {
        List<String> ids = userRoleService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新用户角色", notes = "批量更新用户角色，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<UserRole> entitys) {
        List<String> ids = userRoleService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除用户角色", notes = "批量删除用户角色，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<UserRole> entitys) {
        int rows = userRoleService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
