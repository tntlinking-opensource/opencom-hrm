package com.geeke.salaryArchives.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.salaryArchives.entity.SalaryArchives;
import com.geeke.salaryArchives.service.SalaryArchivesService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.List;

/**
 * 薪酬档案Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/salaryArchives/salaryArchives")
public class SalaryArchivesController extends BaseController {

    @Autowired
    private SalaryArchivesService salaryArchivesService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SalaryArchives entity = salaryArchivesService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        searchParams.getParams().add(new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId()));
        Page<? extends SalaryArchives> result = salaryArchivesService.listPageWithParam(searchParams, SessionUtils.getUser().getCompanyId());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<SalaryArchives> result = salaryArchivesService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SalaryArchives entity) {
        String id = salaryArchivesService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SalaryArchives entity) {
        int rows = salaryArchivesService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<SalaryArchives> entitys) {
        List<String> ids = salaryArchivesService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<SalaryArchives> entitys) {
        List<String> ids = salaryArchivesService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<SalaryArchives> entitys) {
        int rows = salaryArchivesService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 模版下载 *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream;charset=utf-8");
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("薪资档案模版表.xlsx", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(resourceLoader.getResource("classpath:/template/salaryArchives.xlsx"));
    }

    /**
     * 导入 *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/file")
    public ResponseEntity<JSONObject> fileToData(@RequestParam("file") MultipartFile file) throws Exception {
        return salaryArchivesService.fileToData(file);
    }

}
