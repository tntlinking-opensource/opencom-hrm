package com.geeke.hrm.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.hrm.entity.HrmPayrollBillDetails;
import com.geeke.hrm.entity.HrmSalaryUploadSummary;
import com.geeke.hrm.service.HrmPayrollBillDetailsService;
import com.geeke.hrm.service.HrmSalaryUploadSummaryService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;

import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 工资上传Controller
 *
 * @author
 */
@RestController
@RequestMapping(value = "/hrm/hrmSalaryUploadSummary")
public class HrmSalaryUploadSummaryController extends BaseController {

    @Autowired
    private HrmSalaryUploadSummaryService hrmSalaryUploadSummaryService;

    @Autowired
    private HrmPayrollBillDetailsService hrmPayrollBillDetailsService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        HrmSalaryUploadSummary entity = hrmSalaryUploadSummaryService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = {"list", ""})
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<HrmSalaryUploadSummary> result = hrmSalaryUploadSummaryService.listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<HrmSalaryUploadSummary> result = hrmSalaryUploadSummaryService.listAll(
                searchParams.getParams(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody HrmSalaryUploadSummary entity) {
        String id = hrmSalaryUploadSummaryService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody HrmSalaryUploadSummary entity) {
        int rows = hrmSalaryUploadSummaryService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<HrmSalaryUploadSummary> entitys) {
        List<String> ids = hrmSalaryUploadSummaryService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<HrmSalaryUploadSummary> entitys) {
        List<String> ids = hrmSalaryUploadSummaryService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<HrmSalaryUploadSummary> entitys) {
        int rows = hrmSalaryUploadSummaryService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @GetMapping(value = "/downloadTemplate")
    public ResponseEntity<Resource> downloadTemplate() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream;charset=utf-8");
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("salary.xlsx", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(resourceLoader.getResource("classpath:/template/salary.xlsx"));
    }

    @DSTransactional
    @PostMapping("/file")
    public ResponseEntity<JSONObject> fileToData(@RequestParam("file") MultipartFile file) throws Exception {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        addHeader(reader);
        List<HrmPayrollBillDetails> hrmPayrollBillDetails = reader.read(1, 2, HrmPayrollBillDetails.class);
        List<String> salaryYearAndMonths = hrmPayrollBillDetails.stream().map(HrmPayrollBillDetails::getSalaryYearAndMonth).distinct().collect(Collectors.toList());
        if(salaryYearAndMonths.size() > 1){
            //说明年月出现多个的,无法确定是具体哪个月，抛出错误
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "文档信息有误,出现不同月份数据!"));
        }
        if(salaryYearAndMonths.size() < 1){
            //说明年月出现多个的,无法确定是具体哪个月，抛出错误
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "文档信息有误,年月为必填项!"));
        }
        //保存数据到数据库
        HrmSalaryUploadSummary hrmSalaryUploadSummary = new HrmSalaryUploadSummary();
        hrmSalaryUploadSummary.setSalaryPeopleNumber(String.valueOf(hrmPayrollBillDetails.size()));
        hrmSalaryUploadSummary.setSalaryYearAndMonth(salaryYearAndMonths.get(0));
        HrmSalaryUploadSummary hrmSalaryUploadSummaryTemp = hrmSalaryUploadSummaryService.save(hrmSalaryUploadSummary);
        List<HrmPayrollBillDetails> billDetails = hrmPayrollBillDetails.stream().peek(details -> details.setSummary(hrmSalaryUploadSummaryTemp)).collect(Collectors.toList());
        hrmPayrollBillDetailsService.bulkInsert(billDetails);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    private void addHeader(ExcelReader reader){
        reader.addHeaderAlias("*薪资期间","salaryYearAndMonth");
        reader.addHeaderAlias("部门","deptName");
        reader.addHeaderAlias("姓名","name");
        reader.addHeaderAlias("工号","empolyno");
        reader.addHeaderAlias("全额工资","totalSalary");
        reader.addHeaderAlias("奖金合计","totalBonus");
        reader.addHeaderAlias("补扣款合计","totalAllowanceAndDeduction");
        reader.addHeaderAlias("社保小计（个人）","personalSocialInsurance");
        reader.addHeaderAlias("公积金小计（个人）","personalAccumulationFund");
        reader.addHeaderAlias("计税工资","salaryNeedTax");
        reader.addHeaderAlias("个人税款","personalTax");
        reader.addHeaderAlias("税后项合计","totalAfterTax");
        reader.addHeaderAlias("所得税","salaryTax");
        reader.addHeaderAlias("实发工资1","netSalary");
        reader.addHeaderAlias("实发工资2","netSalary2");
        reader.addHeaderAlias("备注1","remark");
        reader.addHeaderAlias("备注2","remark2");
    }
}
