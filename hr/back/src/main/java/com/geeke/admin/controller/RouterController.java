package com.geeke.admin.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.admin.entity.Router;
import com.geeke.admin.service.RouterService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 路由管理Controller
 * @author
 * @version
 */
@Api(value = "/admin/router", tags = {"路由管理Controller"})
@RestController
@RequestMapping(value = "/admin/router")
public class RouterController extends BaseController {

    @Autowired
    private RouterService routerService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取路由管理", notes = "通过id获取路由管理", httpMethod = "GET",response=Router.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        Router entity = routerService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取路由管理（分页）", notes = "通过搜索条件获取路由管理（分页）", httpMethod = "POST",response=Router.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<Router> result = routerService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取路由管理", notes = "通过搜索条件获取路由管理", httpMethod = "POST",response=Router.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Router> result = routerService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取树桩路由管理", notes = "通过搜索条件获取树桩路由管理", httpMethod = "POST",response=Router.class,responseContainer = "List")
    @PostMapping(value = "tree")
    public ResponseEntity<JSONObject> tree(@RequestBody SearchParams searchParams) {
        List<Router> result = routerService.tree(searchParams.getParams(), searchParams.getOrderby());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strEntity", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "MultipartFile[]", name = "iconIdUploads", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "strDeleteIds", value = "")
    })
    @ApiOperation(value = "保存路由管理", notes = "保存路由管理，并且返回id", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(
        @RequestParam(value = "entity", required = false) String strEntity,
        @RequestParam(value = "iconIdUploads", required = false) MultipartFile[] iconIdUploads, // 文件: 图片地址
        @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws java.io.IOException {
        Router entity = JSONObject.parseObject(strEntity, Router.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = routerService.save(entity, iconIdUploads, deleteIds).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Router", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除路由管理", notes = "删除路由管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody Router entity) {
        int rows = routerService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加路由管理", notes = "批量添加路由管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<Router> entitys) {
        List<String> ids = routerService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新路由管理", notes = "批量更新路由管理，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<Router> entitys) {
        List<String> ids = routerService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除路由管理", notes = "批量删除路由管理，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<Router> entitys) {
        int rows = routerService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
    // 修改加密字段

}
