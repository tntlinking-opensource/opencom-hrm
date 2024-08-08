package com.geeke.recruit.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmHrRecruitService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * hrRecruitController
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/hrRecruit/tsmRecruit")
public class TsmHrRecruitController extends BaseController {

    @Autowired
    private TsmHrRecruitService tsmHrRecruitService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TsmRecruit entity = tsmHrRecruitService.get(id);
        if (entity.isApproved()) {
            return ResponseEntity.ok(ResultUtil.successJson(entity));
        }
        return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_500));
    }


    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        tsmHrRecruitService.procAndActStatusParameterBuild(searchParams);

        Page<TsmRecruit> result = tsmHrRecruitService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        tsmHrRecruitService.procAndActStatusParameterBuild(searchParams);

        List<TsmRecruit> result = tsmHrRecruitService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TsmRecruit entity) {
        Optional.ofNullable(entity.getId())
            .map(tsmHrRecruitService::get)
            .map(TsmRecruit::getUpdateDate)
            .ifPresent(entity::setUpdateDate);

        String id = tsmHrRecruitService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TsmRecruit entity) {
        int rows = tsmHrRecruitService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TsmRecruit> entitys) {
        List<String> ids = tsmHrRecruitService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TsmRecruit> entitys) {
        List<String> ids = tsmHrRecruitService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TsmRecruit> entitys) {
        int rows = tsmHrRecruitService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
