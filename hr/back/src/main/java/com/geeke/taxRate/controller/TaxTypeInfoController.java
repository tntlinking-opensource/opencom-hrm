package com.geeke.taxRate.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.taxRate.entity.TaxTypeInfo;
import com.geeke.taxRate.service.TaxTypeInfoService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import java.util.List;
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
 * 税率类型Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/tax/taxTypeInfo")
public class TaxTypeInfoController extends BaseController {

    @Autowired
    private TaxTypeInfoService taxTypeInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        TaxTypeInfo entity = taxTypeInfoService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        searchParams.getParams().add(new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId()));
        Page<TaxTypeInfo> result = taxTypeInfoService.listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        searchParams.getParams().add(new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId()));

        List<TaxTypeInfo> result = taxTypeInfoService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody TaxTypeInfo entity) {
        String id = taxTypeInfoService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody TaxTypeInfo entity) {
        int rows = taxTypeInfoService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<TaxTypeInfo> entitys) {
        List<String> ids = taxTypeInfoService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<TaxTypeInfo> entitys) {
        List<String> ids = taxTypeInfoService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<TaxTypeInfo> entitys) {
        int rows = taxTypeInfoService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }
}
