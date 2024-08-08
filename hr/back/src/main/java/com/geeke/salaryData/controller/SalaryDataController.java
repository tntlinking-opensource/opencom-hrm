package com.geeke.salaryData.controller;

import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.salaryData.entity.SalaryData;
import com.geeke.salaryData.service.SalaryDataService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;

import java.util.ArrayList;
import java.util.List;

import com.geeke.utils.constants.Constants;
import com.geeke.utils.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 薪资明细Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/salaryData/salaryData")
public class SalaryDataController extends BaseController {

    @Autowired
    private SalaryDataService salaryDataService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        SalaryData entity = salaryDataService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<SalaryData> result = salaryDataService.listSalaryPage(
            searchParams.getParams(),
            searchParams.getOffset(),
            searchParams.getLimit(),
            searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }
    @PostMapping(value = { "listSalaryData", "" })
    public ResponseEntity<JSONObject> listSalaryPage(@RequestBody String data) {
        SearchParams searchParams = new SearchParams();
        searchParams.setOffset(0);
        searchParams.setLimit(10);
        Parameter parameter = new Parameter();
        parameter.setColumnName("sa_batch_id");
        parameter.setValue(data.substring(0,data.length()-1));
        parameter.setQueryType("=");
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        searchParams.setParams(parameters);
        Page<SalaryData> result = salaryDataService.listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<SalaryData> result = salaryDataService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody SalaryData entity) {
        String id = salaryDataService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody SalaryData entity) {
        int rows = salaryDataService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<SalaryData> entitys) {
        List<String> ids = salaryDataService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<SalaryData> entitys) {
        List<String> ids = salaryDataService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<SalaryData> entitys) {
        int rows = salaryDataService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 导入静态薪资数据 *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/file")
    public ResponseEntity<JSONObject> fileToData(@RequestParam("file") MultipartFile file,@RequestParam("taxYear")String taxYear,@RequestParam("billYear")String billYear){
        try{
            return salaryDataService.importSalary(file,taxYear,billYear);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,e.getMessage()));
        }
    }

    /**
     * 获取账单数据
     * @param entitys
     * @return
     * @throws Exception
     */
    @PostMapping("/getBilling")
    public ResponseEntity<JSONObject> getBilling(@RequestBody List<SalaryData> entitys) {
        return salaryDataService.getBilling(entitys);
    }

    /**
     * 全量获取账单数据
     * @param data
     * @return
     * @throws Exception
     */
    @PostMapping("/getAllBilling")
    public ResponseEntity<JSONObject> getAllBilling(@RequestBody String data) {
        //查询薪资数据
        SearchParams searchParams = new SearchParams();
        searchParams.setOffset(0);
        searchParams.setLimit(10);
        Parameter parameter = new Parameter();
        parameter.setColumnName("sa_batch_id");
        parameter.setValue(data.substring(0,data.length()-1));
        parameter.setQueryType("=");
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        searchParams.setParams(parameters);
        List<SalaryData> salaryDataList = salaryDataService.listAll(searchParams.getParams(), searchParams.getOrderby());
        return salaryDataService.getBilling(salaryDataList);
    }

    /**
     * 获取专项附加
     * @param entitys
     * @return
     */
    @PostMapping("/getSpecialAddDeduct")
    public ResponseEntity<JSONObject> batchGetSpecialAndSave(@RequestBody List<SalaryData> entitys){
        String msg = salaryDataService.batchGetSpecialAndSave(entitys);
        if("未获取到专项附加数据:".equals(msg)){
            return ResponseEntity.ok(ResultUtil.successJson());
        }else{
            JSONObject resultJson = new JSONObject();
            resultJson.put(Constants.RETURN_MSG, msg);
            return ResponseEntity.ok(resultJson);
        }
    }

    /**
     * 全量获取专项附加
     * @param data
     * @return
     */
    @PostMapping("/getAllSpecialAddDeduct")
    public ResponseEntity<JSONObject> batchGetAllSpecialAndSave(@RequestBody String data){
        //查询薪资数据
        SearchParams searchParams = new SearchParams();
        searchParams.setOffset(0);
        searchParams.setLimit(10);
        Parameter parameter = new Parameter();
        parameter.setColumnName("sa_batch_id");
        parameter.setValue(data.substring(0,data.length()-1));
        parameter.setQueryType("=");
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        searchParams.setParams(parameters);
        List<SalaryData> salaryDataList = salaryDataService.listAll(searchParams.getParams(), searchParams.getOrderby());
        String msg = salaryDataService.batchGetSpecialAndSave(salaryDataList);
        if("未获取到专项附加数据:".equals(msg)){
            return ResponseEntity.ok(ResultUtil.successJson());
        }else{
            JSONObject resultJson = new JSONObject();
            resultJson.put(Constants.RETURN_MSG, msg);
            return ResponseEntity.ok(resultJson);
        }
    }

    /**
     * 薪资计算
     * @param entitys
     * @return
     * @throws Exception
     */
    @PostMapping("/salaryCalculate")
    public ResponseEntity<JSONObject> salaryCalculate(@RequestBody List<SalaryData> entitys) throws Exception {
        long startTime = System.currentTimeMillis();
        salaryDataService.batchCalculateAndSave(entitys);
        long endTime = System.currentTimeMillis();
        System.out.println("计算运行时间：" + (endTime - startTime) + "ms");

        return ResponseEntity.ok(ResultUtil.successJson());
    }

    /**
     * 全量薪资计算
     * @param data
     * @return
     * @throws Exception
     */
    @PostMapping("/allSalaryCalculate")
    public ResponseEntity<JSONObject> allSalaryCalculate(@RequestBody String data) throws Exception {
        //查询薪资数据
        SearchParams searchParams = new SearchParams();
        searchParams.setOffset(0);
        searchParams.setLimit(10);
        Parameter parameter = new Parameter();
        parameter.setColumnName("sa_batch_id");
        parameter.setValue(data.substring(0,data.length()-1));
        parameter.setQueryType("=");
        ArrayList<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        searchParams.setParams(parameters);
        List<SalaryData> salaryDataList = salaryDataService.listAll(searchParams.getParams(), searchParams.getOrderby());
        long startTime = System.currentTimeMillis();
        salaryDataService.batchCalculateAndSave(salaryDataList);
        long endTime = System.currentTimeMillis();
        System.out.println("计算运行时间：" + (endTime - startTime) + "ms");
        return ResponseEntity.ok(ResultUtil.successJson());
    }
}

