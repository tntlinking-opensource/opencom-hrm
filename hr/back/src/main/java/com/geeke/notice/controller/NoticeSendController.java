package com.geeke.notice.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.notice.entity.NoticeSend;
import com.geeke.notice.service.NoticeSendService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import java.util.List;

import com.geeke.notice.entity.CompanyTree;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息记录Controller
 * @author
 * @version
 */
@Api(value = "/notice/noticeSend", tags = {"消息记录Controller"})
@RestController
@RequestMapping(value = "/notice/noticeSend")
public class NoticeSendController extends BaseController {

    @Autowired
    private NoticeSendService noticeSendService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "通过id获取消息记录", notes = "通过id获取消息记录", httpMethod = "GET",response = NoticeSend.class)
    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        NoticeSend entity = noticeSendService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SearchParams", name = "searchParams", value = "", required = true)
    })
    @ApiOperation(value = "通过搜索条件获取消息记录（分页）", notes = "通过搜索条件获取消息记录（分页）", httpMethod = "POST",response = NoticeSend.class)
    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<NoticeSend> result = noticeSendService.listPage(
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
    @ApiOperation(value = "通过搜索条件获取消息记录", notes = "通过搜索条件获取消息记录", httpMethod = "POST",response = NoticeSend.class,responseContainer = "List")
    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<NoticeSend> result = noticeSendService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "NoticeSend", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存消息记录", notes = "保存消息记录", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody NoticeSend entity) {
        String id = noticeSendService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "NoticeSend", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除消息记录", notes = "删除消息记录", httpMethod = "POST")
    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody NoticeSend entity) {
        int rows = noticeSendService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }


    @ApiOperation(value = "批量添加消息记录", notes = "批量添加消息记录，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody @ApiParam() List<NoticeSend> entitys) {
        List<String> ids = noticeSendService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量更新消息记录", notes = "批量更新消息记录，并且返回ids（list）", httpMethod = "POST")
    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody @ApiParam() List<NoticeSend> entitys) {
        List<String> ids = noticeSendService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }


    @ApiOperation(value = "批量删除消息记录", notes = "批量删除消息记录，并且返回删除条数", httpMethod = "POST")
    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody @ApiParam() List<NoticeSend> entitys) {
        int rows = noticeSendService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 获取公司-部门-人员树
     * @param code
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "code", value = "", required = true)
    })
    @ApiOperation(value = "获取公司-部门-人员树", notes = "获取公司-部门-人员树", httpMethod = "GET",response = CompanyTree.class,responseContainer = "List")
    @GetMapping(value = "/tree/{code}")
    public ResponseEntity<JSONObject> getCompanyTree(@PathVariable("code")String code) {
        List<CompanyTree> list = this.noticeSendService.getCompanyTree(code);
        return ResponseEntity.ok(ResultUtil.successJson(list));
    }
}
