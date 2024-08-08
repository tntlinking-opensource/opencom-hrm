package com.geeke.org.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.org.entity.Department;
import com.geeke.org.service.DepartmentService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.QueryUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理Controller
 * @author
 * @version
 */
@Api(value = "/org/department", tags = {"部门管理Controller"})
@RestController
@RequestMapping(value = "/org/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取部门管理", notes = "通过id获取部门管理", httpMethod = "GET",response=Department.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Department entity = departmentService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取部门管理（分页）", notes = "通过搜索条件获取部门管理（分页）", httpMethod = "POST",response=Department.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Department> result = departmentService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取部门管理", notes = "通过搜索条件获取部门管理", httpMethod = "POST",response=Department.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Department> result = departmentService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取树桩部门管理", notes = "通过搜索条件获取树桩部门管理", httpMethod = "POST",response=Department.class,responseContainer = "List")
    @PostMapping(value = "tree")
    public ResponseEntity<JSONObject> tree(@RequestBody SearchParams searchParams) {
        List<Department> result = departmentService.tree(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Department", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存部门管理", notes = "保存部门管理，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Department entity) {
        String id = departmentService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Department", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除部门管理", notes = "删除部门管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Department entity) {
        int rows = departmentService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<Department>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "批量添加部门管理", notes = "批量添加部门管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<Department> entitys) {
        List<String> ids = departmentService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<Department>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "批量更新部门管理", notes = "批量更新部门管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<Department> entitys) {
        List<String> ids = departmentService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<Department>", name = "entitys", value = "", required = true)
    })
    @ApiOperation(value = "批量删除部门管理", notes = "批量删除部门管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<Department> entitys) {
        int rows = departmentService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取部门管理", notes = "通过搜索条件获取部门管理", httpMethod = "POST",response=Department.class,responseContainer = "List")
    @PostMapping(value = "/listAllByCompany")
    public ResponseEntity<JSONObject> listAllByCompany(@RequestBody SearchParams searchParams) {
        User user = SessionUtils.getUser();
        List<Department> result = departmentService.listAll(QueryUtils.queryAndList("company_id",user.getCompanyId()), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
}
