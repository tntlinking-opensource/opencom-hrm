package com.geeke.accumulation.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.accumulation.dto.DefaultPayConfigDto;
import com.geeke.accumulation.entity.DefaultPayConfig;
import com.geeke.accumulation.service.DefaultPayConfigService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;

import java.util.ArrayList;
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
 * 默认配置Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/defaultPayConfig")
public class DefaultPayConfigController extends BaseController {

    @Autowired
    private DefaultPayConfigService defaultPayConfigService;


    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> parameters = Lists.newArrayList(generateCompanyParam());
        parameters.addAll(searchParams.getParams());
        searchParams.setParams(parameters);
        Page<DefaultPayConfigDto> result = defaultPayConfigService.listDtoPage(searchParams, SessionUtils.getUser().getCompanyId());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Parameter> parameters = Lists.newArrayList(generateCompanyParam());
        parameters.addAll(searchParams.getParams());
        List<DefaultPayConfig> result = defaultPayConfigService.listAll(parameters, searchParams.getOrderby());
        List<DefaultPayConfigDto> defaultPayConfigDtos = defaultPayConfigService.entityToDto(result, SessionUtils.getUser().getCompanyId());
        return ResponseEntity.ok(ResultUtil.successJson(defaultPayConfigDtos));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody DefaultPayConfig entity) {
        entity.setCompanyId(SessionUtils.getUser().getCompanyId());
        String id = defaultPayConfigService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }


    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<DefaultPayConfig> entitys) {
        setCompanyIdToParam(entitys);
        List<String> ids = defaultPayConfigService.bulkUpdateWithVerify(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<DefaultPayConfig> entitys) {
        setCompanyIdToParam(entitys);
        int rows = defaultPayConfigService.bulkDeleteWithVerify(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    public Parameter generateCompanyParam() {
        return new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId());
    }

    public void setCompanyIdToParam (List<DefaultPayConfig> entitys) {
        if (CollectionUtils.isEmpty(entitys)) {
            return;
        }
        entitys.forEach(item -> item.setCompanyId(SessionUtils.getUser().getCompanyId()));
    }
}
