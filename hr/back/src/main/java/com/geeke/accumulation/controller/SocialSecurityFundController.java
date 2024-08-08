package com.geeke.accumulation.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.accumulation.dto.InitEmployeeFundParam;
import com.geeke.accumulation.dto.SocialSecurityFundDto;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.accumulation.entity.SocialSecurityFund;
import com.geeke.accumulation.service.SocialSecurityFundService;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 社保公积金Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/socialSecurityFund")
public class SocialSecurityFundController extends BaseController {

    @Autowired
    private SocialSecurityFundService socialSecurityFundService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SocialSecurityFund entity = socialSecurityFundService.get(id);
        SocialSecurityFundDto socialSecurityFundDto = socialSecurityFundService.entityToDto(entity);
        return ResponseEntity.ok(ResultUtil.successJson(socialSecurityFundDto));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        List<Parameter> parameters = Lists.newArrayList(generateCompanyParam());
        parameters.addAll(searchParams.getParams());
        String companyId = SessionUtils.getUser().getCompanyId();
        Page<SocialSecurityFund> result = socialSecurityFundService.listPage(
                parameters,
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );
        Page<SocialSecurityFundDto> socialSecurityFundDtoPage = new Page<>(result.getTotal(), socialSecurityFundService.entityListToDtoList(result.getRows(), companyId));
        return ResponseEntity.ok(ResultUtil.successJson(socialSecurityFundDtoPage));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<Parameter> parameters = Lists.newArrayList(generateCompanyParam());
        parameters.addAll(searchParams.getParams());
        List<SocialSecurityFund> result = socialSecurityFundService.listAll(
            parameters,
            searchParams.getOrderby()
        );
        List<SocialSecurityFundDto> socialSecurityFundDtos = socialSecurityFundService.entityListToDtoList(result, SessionUtils.getUser().getCompanyId());
        return ResponseEntity.ok(ResultUtil.successJson(socialSecurityFundDtos));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SocialSecurityFund entity) {
        entity.setCompanyId(SessionUtils.getUser().getCompanyId());
        String id = socialSecurityFundService.saveWithVerify(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SocialSecurityFund entity) {
        int rows = socialSecurityFundService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<SocialSecurityFund> entitys) {
        List<String> ids = socialSecurityFundService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<SocialSecurityFund> entitys) {
        int rows = socialSecurityFundService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "initAllEmployee")
    public ResponseEntity<JSONObject> initAllEmployee() {
        socialSecurityFundService.initAllEmployeeConfig(SessionUtils.getUser().getCompanyId());
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @PostMapping(value = "initEmployee")
    public ResponseEntity<JSONObject> initEmployee(@RequestBody InitEmployeeFundParam param) {
        socialSecurityFundService.initEmployConfig(param.getEmployeeNos(), SessionUtils.getUser().getCompanyId(), param.getNeedVerify());
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @PostMapping(value = "import")
    public ResponseEntity<JSONObject> initEmployee(@RequestParam("file") MultipartFile file) throws IOException {
        List<String> result = socialSecurityFundService.importDataFromExcel(file.getInputStream());
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @GetMapping(value = "/template")
    public ResponseEntity<Resource> downloadTemplate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream;charset=utf-8");
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("社保公积金比例维护模板.xls", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(resourceLoader.getResource("classpath:/template/socialSecurityFundTemplate.xls"));
    }


    public Parameter generateCompanyParam() {
        return new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId());
    }

    @PostMapping(value = "exportExcel")
    public ResponseEntity<JSONObject> exportExcel(@RequestBody SearchParams searchParams, HttpServletResponse response) {
        socialSecurityFundService.exportExcel(searchParams, response);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
}
