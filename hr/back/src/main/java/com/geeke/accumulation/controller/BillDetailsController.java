package com.geeke.accumulation.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.geeke.accumulation.entity.BillDetails;
import com.geeke.accumulation.entity.vo.BillDetailsVo;
import com.geeke.accumulation.entity.vo.BillListVo;
import com.geeke.accumulation.service.BillDetailsService;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import javax.servlet.http.HttpServletResponse;

/**
 * 生成账单Controller
 * @author
 * @version
 */
@RestController
@RequestMapping(value = "/billDetails")
public class BillDetailsController extends BaseController {

    @Autowired
    private BillDetailsService billDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        BillDetails entity = billDetailsService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        searchParams.getParams().add(new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId()));
        Page<BillListVo> result = billDetailsService.getBillListVos(
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
        List<BillDetails> result = billDetailsService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody BillDetails entity) {
        entity.setCompanyId(SessionUtils.getUser().getCompanyId());
        String id = billDetailsService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody BillDetails entity) {
        int rows = billDetailsService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<BillDetails> entitys) {
        List<String> ids = billDetailsService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<BillDetails> entitys) {
        List<String> ids = billDetailsService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<BillDetails> entitys) {
        int rows = billDetailsService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 根据批次号删除账单 *
     * @param entity
     * @return
     */
    @PostMapping(value = "deleteByBatchNo")
    public ResponseEntity<JSONObject> deleteByBatchNo(@RequestBody BillDetails entity) {
        int rows = billDetailsService.deleteByBatchNo(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    /**
     * 生成账单 *
     * @param billDetails 账单年月
     * @return
     */
    @PostMapping(value = "generateBillDetails")
    public ResponseEntity<JSONObject> generateBillDetails(@RequestBody BillDetails billDetails) {
        return ResponseEntity.ok(ResultUtil.successJson(billDetailsService.generateBillDetails(billDetails.getBillYearMonth())));
    }

    /**
     * 查询账单明细 *
     * @param billDetails
     * @return
     */
    @PostMapping(value = { "getBillDetailsList", "" })
    public ResponseEntity<JSONObject> getBillDetailsList(@RequestBody BillDetails billDetails) {
        billDetails.setCompanyId(SessionUtils.getUser().getCompanyId());
        Page<BillDetailsVo> result = billDetailsService.getBillDetailsList(billDetails);
        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    /**
     * 导出账单明细 *
     * @param billDetails
     * @return
     */
    @PostMapping(value = { "/exportBillDetails", "" })
    public ResponseEntity<JSONObject> exportBillDetails(@RequestBody BillDetails billDetails, HttpServletResponse response) throws Exception {
        billDetails.setCompanyId(SessionUtils.getUser().getCompanyId());
        billDetailsService.exportBillDetails(billDetails, response);
        return ResponseEntity.ok(ResultUtil.successJson());
    }
}
