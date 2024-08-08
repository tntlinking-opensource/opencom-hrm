package com.geeke.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.admin.entity.User;
import com.geeke.admin.service.UserService;
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
@Api(value = "/admin/user", tags = {"用户管理Controller"})
@RestController
@RequestMapping(value = "/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取用户信息", notes = "通过id获取用户信息", httpMethod = "GET",response = User.class )
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        User entity = userService.get(id);

        // 不返回密码
        entity.setLoginPassword("");

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取用户信息（分页）", notes = "通过搜索条件获取用户信息（分页）", httpMethod = "POST",response = User.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<User> result = userService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        // 不返回密码
        if (result.getRows() != null) {
            for (User entity : result.getRows()) {
                entity.setLoginPassword("");
            }
        }

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取用户信息", notes = "通过搜索条件获取用户信息", httpMethod = "POST",response = User.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<User> result = userService.listAll(searchParams.getParams(), searchParams.getOrderby());

        // 不返回密码
        if (result != null) {
            for (User entity : result) {
                entity.setLoginPassword("");
            }
        }

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "User", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存用户信息", notes = "保存用户信息，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody User entity) {
        String id = userService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "User", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息,并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody User entity) {
        int rows = userService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加用户信息", notes = "批量添加用户信息，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<User> entitys) {
        List<String> ids = userService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新用户信息", notes = "批量更新用户信息，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<User> entitys) {
        List<String> ids = userService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除用户信息", notes = "批量删除用户信息，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<User> entitys) {
        int rows = userService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    // 修改加密字段

    /**
     * 修改 密码
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "password", value = "", required = true)
    })
    @ApiOperation(value = "修改 密码", notes = "修改 密码", httpMethod = "PUT")
    @PutMapping("/{id}/loginPassword")
    public ResponseEntity<JSONObject> changeLoginPassword(@PathVariable("id") String id, String password) {
        int rows = 0;
        if (!StringUtils.isBlank(id)) {
            rows = userService.changeLoginPassword(id, password);
        }
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
