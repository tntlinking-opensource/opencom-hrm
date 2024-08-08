package com.geeke.accumulation.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.accumulation.entity.DefaultPayConfig;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.accumulation.entity.PayCityConfig;
import com.geeke.accumulation.service.PayCityConfigService;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
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
 * 缴费城市Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/payCity")
public class PayCityConfigController extends BaseController {

    @Autowired
    private PayCityConfigService payCityConfigService;

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> parameters = Lists.newArrayList(generateCompanyParam());
        parameters.addAll(searchParams.getParams());
        Page<PayCityConfig> result = payCityConfigService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Parameter> parameters = Lists.newArrayList(generateCompanyParam());
        parameters.addAll(searchParams.getParams());
        List<PayCityConfig> result = payCityConfigService.listAll(parameters, searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody PayCityConfig entity) {
        entity.setCompanyId(SessionUtils.getUser().getCompanyId());
        String id = payCityConfigService.saveWithDefaultConfig(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody PayCityConfig entity) {
        entity.setCompanyId(SessionUtils.getUser().getCompanyId());
        int rows = payCityConfigService.deleteCityWithDefaultConfig(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    public Parameter generateCompanyParam() {
        return new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId());
    }


}
