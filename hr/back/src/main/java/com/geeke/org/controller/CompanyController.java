package com.geeke.org.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.dc.entity.DcCondition;
import com.geeke.org.entity.Company;
import com.geeke.org.service.CompanyService;
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

import javax.servlet.http.HttpServletRequest;

/**
 * 公司管理Controller
 * @author
 * @version
 */
@Api(value = "/org/company", tags = {"公司管理Controller"})
@RestController
@RequestMapping(value = "/org/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "获取公司管理信息", notes = "通过id获取公司管理信息", httpMethod = "GET",response = Company.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Company entity = companyService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "(分页)批量获取公司管理信息", notes = "通过搜索查询获取分页的公司管理信息", httpMethod = "POST",response = Company.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Company> result = companyService.listPage(
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
    @ApiOperation(value = "获取全部公司管理信息", notes = "通过搜索查询获取的公司管理信息", httpMethod = "POST",response = Company.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Company> result = companyService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "获取树状公司管理信息", notes = "通过搜索查询获取的树状公司管理信息", httpMethod = "POST",response = Company.class,responseContainer = "List")
    @PostMapping(value = "tree")
    public ResponseEntity<JSONObject> tree(@RequestBody SearchParams searchParams) {
        List<Company> result = companyService.tree(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Company", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "存储公司管理信息", notes = "存储公司管理信息，并且返回生成了的id", httpMethod = "POST",response = String.class)
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody Company entity) {
        String id = companyService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Company", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除公司管理信息", notes = "通过参数删除公司管理信息，并且返回执行成功条数", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Company entity) {
        int rows = companyService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加公司信息", notes = "批量添加公司信息，并且返回ids（list）", httpMethod = "POST",response = List.class)
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<Company> entitys) {
        List<String> ids = companyService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新公司管理信息", notes = "批量更新公司管理信息，并且返回ids（list）", httpMethod = "POST",response = List.class)
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<Company> entitys) {
        List<String> ids = companyService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除公司管理信息", notes = "批量删除公司管理信息，并且返回ids（list）", httpMethod = "POST",response = Integer.class)
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<Company> entitys) {
        int rows = companyService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
