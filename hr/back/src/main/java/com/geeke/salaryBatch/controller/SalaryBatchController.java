package com.geeke.salaryBatch.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.salaryBatch.entity.SalaryBatch;
import com.geeke.salaryBatch.service.SalaryBatchService;
import com.geeke.salaryData.entity.SalaryData;
import com.geeke.salaryData.service.SalaryDataService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.DateUtils;
import com.geeke.utils.ResultUtil;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import com.geeke.utils.constants.Constants;
import com.geeke.utils.constants.ErrorEnum;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 薪资计算Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/salaryBatch/salaryBatch")
public class SalaryBatchController extends BaseController {

    @Autowired
    private SalaryBatchService salaryBatchService;

    @Autowired
    private SalaryDataService salaryDataService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SalaryBatch entity = salaryBatchService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<SalaryBatch> result = salaryBatchService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<SalaryBatch> result = salaryBatchService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SalaryBatch entity) {
        String id = salaryBatchService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SalaryBatch entity) {
        int rows = salaryBatchService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<SalaryBatch> entitys) {
        List<String> ids = salaryBatchService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<SalaryBatch> entitys) {
        List<String> ids = salaryBatchService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<SalaryBatch> entitys) {
        int rows = salaryBatchService.bulkDelete(entitys);
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
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("salaryDataDetail.xlsx", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(resourceLoader.getResource("classpath:/template/salaryDataDetail.xlsx"));
    }

    /**
     * 根据薪资批次导出薪资数据
     * @param entity
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/exportSalaryDataByBatch")
    public ResponseEntity<JSONObject> exportSalaryDataByBatch(@RequestBody SalaryBatch entity, HttpServletResponse response){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/octet-stream;charset=utf-8");
//        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("salaryDataDetail.xlsx", "utf-8")));
//        Resource resource = resourceLoader.getResource("classpath:/template/salaryDataDetail.xlsx");
        try {
            salaryDataService.exportSalaryDataByBatch(entity,response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400));
        }
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    /**
     * 薪资确认 *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/salaryConfirm")
    public ResponseEntity<JSONObject> salaryConfirm(@RequestBody SalaryBatch entity) {
        String isVerify = entity.getIsVerify();
        //是否已经确认
        if ("1".equals(isVerify)){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"数据已确认"));
        }
        entity.setIsVerify("1");
        entity.setVerifyTime(DateUtils.getDateTime());
        salaryBatchService.updateSalaryBatch(entity);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
    /**
     * 是否已确认
     */
    @PostMapping(value = "/isSalaryConfirm")
    public ResponseEntity<JSONObject> isSalaryConfirm(@RequestBody SalaryBatch entity) {
        String isVerify = entity.getIsVerify();
        //是否已经确认
        if ("1".equals(isVerify)){
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,"数据已确认,无法修改！"));
        }
        return ResponseEntity.ok(ResultUtil.successJson());
    }
}
