package com.geeke.collect.controller;

import java.util.List;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.geeke.collect.entity.SysCollect;
import com.geeke.collect.service.SysCollectService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;

/**
 * 收藏夹Controller
 * @author szy
 * @version 2021-07-28
 */
@Api(value = "/collect/sysCollect", tags = {"收藏夹Controller"})
@RestController
@RequestMapping(value = "/collect/sysCollect")
public class SysCollectController extends BaseController {

	@Autowired
	private SysCollectService sysCollectService;

    /**
     * 根据用户id查询收藏列表
     * @param userId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "userId", value = "", required = true)
    })
    @ApiOperation(value = "根据用户id查询收藏列表", notes = "根据用户id查询收藏列表", httpMethod = "GET",response=SysCollect.class,responseContainer = "List")
    @GetMapping(value = "listAllByUserId/{userId}")
    public ResponseEntity<JSONObject> listAllByUserId(@PathVariable("userId") String userId) {
        List<SysCollect> result = sysCollectService.listAllByUserId(userId);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    /**
     * 保存收藏
     * @param entity
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysCollect", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "保存收藏", notes = "保存收藏", httpMethod = "POST")
    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SysCollect entity) {
        String id = sysCollectService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    /**
     * 移动收藏触发
     * @param sysCollectList
     * @param userId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<SysCollect>", name = "sysCollectList", value = "", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "userId", value = "", required = true)
    })
    @ApiOperation(value = "移动收藏触发", notes = "移动收藏触发", httpMethod = "POST",response=SysCollect.class,responseContainer = "List")
    @PostMapping(value = "updateBatch/{userId}")
    public ResponseEntity<JSONObject> updateBatch(@RequestBody List<SysCollect> sysCollectList, @PathVariable String userId) {
        List<SysCollect> result = sysCollectService.updateBatch(sysCollectList,userId);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }


    /**
     * 删除收藏
     * @param entity
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SysCollect", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "删除收藏", notes = "删除收藏", httpMethod = "DELETE")
    @DeleteMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SysCollect entity) {
        int rows = sysCollectService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

}