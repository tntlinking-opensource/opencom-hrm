package com.geeke.sys.controller;

import com.alibaba.fastjson.JSONObject;

import com.geeke.sys.entity.Action;
import com.geeke.sys.service.RecycleService;
import com.geeke.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志Controller
 * @author lys   手工编写
 * @version 2019-12-12
 */
@Api(value = "/sys/recycle", tags = {"操作日志Controller"})
@RestController
@RequestMapping(value = "/sys/recycle")
public class RecycleController extends BaseController {

	@Autowired
	private RecycleService recycleService;
	
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Action", name = "entity", value = "", required = true)
    })
    @ApiOperation(value = "修改操作日志状态", notes = "修改操作日志状态", httpMethod = "POST")
    @PostMapping(value = "restor")
    public ResponseEntity<JSONObject> restor(@RequestBody Action entity) {
        int rows = recycleService.restore(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}