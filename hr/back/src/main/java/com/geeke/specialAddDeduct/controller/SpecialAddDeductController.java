package com.geeke.specialAddDeduct.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.specialAddDeduct.entity.SpecialAddDeduct;
import com.geeke.specialAddDeduct.entity.TaxDeclareConfig;
import com.geeke.specialAddDeduct.service.SpecialAddDeductService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;

import java.net.URLEncoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 专项附加扣除Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/specialAddDeduct")
public class SpecialAddDeductController extends BaseController {

    @Autowired
    private SpecialAddDeductService specialAddDeductService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SpecialAddDeduct entity = specialAddDeductService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        searchParams.getParams().add(new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId()));
        Page<SpecialAddDeduct> result = specialAddDeductService.listPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<SpecialAddDeduct> result = specialAddDeductService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SpecialAddDeduct entity) {
        String id = specialAddDeductService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SpecialAddDeduct entity) {
        int rows = specialAddDeductService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<SpecialAddDeduct> entitys) {
        List<String> ids = specialAddDeductService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<SpecialAddDeduct> entitys) {
        List<String> ids = specialAddDeductService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<SpecialAddDeduct> entitys) {
        int rows = specialAddDeductService.bulkDelete(entitys);
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
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("专项附加模版表.xlsx", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(resourceLoader.getResource("classpath:/template/specialAddTemplate.xlsx"));
    }

    /**
     * 导入 *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/file/{taxYearMonth}")
    public ResponseEntity<JSONObject> fileToData(@PathVariable("taxYearMonth") String taxYearMonth, @RequestParam("file") MultipartFile file) throws Exception {
        specialAddDeductService.fileToData(taxYearMonth,file);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    /**
     * 获取个税申报基本配置信息 *
     * @return
     */
    @GetMapping(value = "getTaxDeclareConfig")
    public ResponseEntity<JSONObject> getTaxDeclareConfig() {
        return ResponseEntity.ok(ResultUtil.successJson(specialAddDeductService.getTaxDeclareConfig()));
    }

    /**
     * 保存个税申报基本配置信息 *
     * @return
     */
    @PostMapping(value = "saveTaxDeclareConfig")
    public ResponseEntity<JSONObject> saveTaxDeclareConfig(@RequestBody TaxDeclareConfig taxDeclareConfig) {
        return ResponseEntity.ok(ResultUtil.successJson(specialAddDeductService.saveTaxDeclareConfig(taxDeclareConfig)));
    }
}
