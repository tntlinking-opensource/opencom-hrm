package com.geeke.basic.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.basic.entity.UserPropDetails;
import com.geeke.basic.service.UserPropDetailsService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import java.util.List;
import java.util.Objects;

import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 基础配置项Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/basic/userPropDetails")
public class UserPropDetailsController extends BaseController {

    @Autowired
    private UserPropDetailsService userPropDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        UserPropDetails entity = userPropDetailsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<UserPropDetails> result = userPropDetailsService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<UserPropDetails> result = userPropDetailsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody UserPropDetails entity) {
        String id = userPropDetailsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "file/save")
    public ResponseEntity<JSONObject> fileSave(
            @RequestParam(value = "entity", required = false) String strEntity,
            @RequestParam(value = "attribute0Uploads", required = false) MultipartFile[] attribute0Uploads, // 文件: attribute0
            @RequestParam(value = "deleteIds", required = false) String strDeleteIds
    ) throws java.io.IOException {
        UserPropDetails entity = JSONObject.parseObject(strEntity, UserPropDetails.class);
        String[] deleteIds = JSONObject.parseObject(strDeleteIds, String[].class);
        String id = userPropDetailsService.fileSave(entity, attribute0Uploads, deleteIds).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody UserPropDetails entity) {
        if(Objects.equals(entity.getUsed(),"1")){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "停用状态下才可删除!"));
        }
        int rows = userPropDetailsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<UserPropDetails> entitys) {
        List<String> ids = userPropDetailsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<UserPropDetails> entitys) {
        List<String> ids = userPropDetailsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<UserPropDetails> entitys) {
        int rows = userPropDetailsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
