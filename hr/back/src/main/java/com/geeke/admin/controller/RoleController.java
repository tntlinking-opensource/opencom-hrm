package com.geeke.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.admin.entity.Role;
import com.geeke.admin.service.RoleService;
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
 * 角色管理Controller
 * @author
 * @version
 */
@Api(value = "/admin/role", tags = {"角色管理Controller"})
@RestController
@RequestMapping(value = "/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取角色管理", notes = "通过id获取角色管理", httpMethod = "GET",response=Role.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Role entity = roleService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取角色管理（分页）", notes = "通过搜索条件获取角色管理（分页）", httpMethod = "POST",response=Role.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Role> result = roleService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取角色管理", notes = "通过搜索条件获取角色管理", httpMethod = "POST",response=Role.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Role> result = roleService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Role", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存角色管理", notes = "保存角色管理，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Role entity) {
        String id = roleService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Role", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除角色管理", notes = "删除角色管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Role entity) {
        int rows = roleService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加角色管理", notes = "批量添加角色管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<Role> entitys) {
        List<String> ids = roleService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新角色管理", notes = "批量更新角色管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<Role> entitys) {
        List<String> ids = roleService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除角色管理", notes = "批量删除角色管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<Role> entitys) {
        int rows = roleService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
