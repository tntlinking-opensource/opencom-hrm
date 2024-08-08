package com.geeke.hrm.controller;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.hrm.entity.HrmUserLaid;
import com.geeke.hrm.service.HrmUserLaidCrudService;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Maps;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

/**
 * @author TBH
 * 交接确认
 */
@RestController
@RequestMapping(value = "/hrm/hrmUserCrudLaid")
public class HrmUserLaidCrudController extends BaseController {

    @Autowired
    private HrmUserLaidCrudService hrmUserLaidService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/{id}")
    public ResponseEntity<JSONObject> getById(@PathVariable("id") String id) {
        HrmUserLaid entity = hrmUserLaidService.get(id);

        return ResponseEntity.ok(ResultUtil.successJson(entity));
    }

    @PostMapping(value = { "list", "" })
    public ResponseEntity<JSONObject> listPage(@RequestBody SearchParams searchParams) {
        Page<HrmUserLaid> result = hrmUserLaidService.listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "listAll")
    public ResponseEntity<JSONObject> listAll(@RequestBody SearchParams searchParams) {
        List<HrmUserLaid> result = hrmUserLaidService.listAll(searchParams.getParams(), searchParams.getOrderby());

        return ResponseEntity.ok(ResultUtil.successJson(result));
    }

    @PostMapping(value = "save")
    public ResponseEntity<JSONObject> save(@RequestBody HrmUserLaid entity) {
        String id = hrmUserLaidService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    //交接确认
    @PostMapping(value = "handover")
    public ResponseEntity<JSONObject> handover(@RequestBody HrmUserLaid entity) {
        //设置为交接完成
        entity.setHandingstatus("1");
        //设置行政办理状态为办理中
        entity.setAdminaffairsstatus("1");
        String id = hrmUserLaidService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    //事务确认
    @PostMapping(value = "processing")
    public ResponseEntity<JSONObject> processing(@RequestBody HrmUserLaid entity) {
        //设置为事务已办理
        entity.setAdminaffairsstatus("2");
        //设置财务办理为办理中
        entity.setFinanceaffairsstatus("1");
        String id = hrmUserLaidService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
    //财务确认
    @PostMapping(value = "finance")
    public ResponseEntity<JSONObject> finance(@RequestBody HrmUserLaid entity) {
        //设置为事务已办理
        entity.setFinanceaffairsstatus("2");
        //设置人事确认为办理中
        entity.setHumanaffairsstatus("1");
        String id = hrmUserLaidService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
    //离职人事确认
    @PostMapping(value = "resignation")
    public ResponseEntity<JSONObject> resignation(@RequestBody HrmUserLaid entity) {
        //设置为人事已确认
        entity.setHumanaffairsstatus("2");
        String id = hrmUserLaidService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }
    //离职确认
    @PostMapping(value = "laid")
    public ResponseEntity<JSONObject> laid(@RequestBody HrmUserLaid entity) {
        //设置离职状态为已离职
        entity.setLaidstatus("1");
        String id = hrmUserLaidService.save(entity).getId();
        return ResponseEntity.ok(ResultUtil.successJson(id));
    }

    @PostMapping(value = "delete")
    public ResponseEntity<JSONObject> delete(@RequestBody HrmUserLaid entity) {
        int rows = hrmUserLaidService.delete(entity);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    @PostMapping(value = "bulkInsert")
    public ResponseEntity<JSONObject> bulkInsert(@RequestBody List<HrmUserLaid> entitys) {
        List<String> ids = hrmUserLaidService.bulkInsert(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkUpdate")
    public ResponseEntity<JSONObject> bulkUpdate(@RequestBody List<HrmUserLaid> entitys) {
        List<String> ids = hrmUserLaidService.bulkUpdate(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(ids));
    }

    @PostMapping(value = "bulkDelete")
    public ResponseEntity<JSONObject> bulkDelete(@RequestBody List<HrmUserLaid> entitys) {
        int rows = hrmUserLaidService.bulkDelete(entitys);
        return ResponseEntity.ok(ResultUtil.successJson(rows));
    }

    //离职详情单
    @PostMapping(value = "/doc/laid")
    public ResponseEntity<Resource> docLaid(@RequestBody HrmUserLaid entity) throws Exception{
        InputStream inputStream = resourceLoader.getResource("classpath:/template/laid.docx").getInputStream();
        Map<String, String> dataMap = laidMap(entity);
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(dataMap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        template.write(byteArrayOutputStream);
        InputStream fileInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        PdfOptions pdfOptions = PdfOptions.create();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(xwpfDocument,outputStream,pdfOptions);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("laid.pdf", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(outputStream.toByteArray()));
    }

    //离职申请单
    @PostMapping(value = "/doc/laidApply")
    public ResponseEntity<Resource> docLaidApply(@RequestBody HrmUserLaid entity) throws Exception{
        InputStream inputStream = resourceLoader.getResource("classpath:/template/laidApply.docx").getInputStream();
        Map<String, String> laidApplyMap = laidApplyMap(entity);
        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(laidApplyMap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        template.write(byteArrayOutputStream);
        InputStream fileInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        PdfOptions pdfOptions = PdfOptions.create();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(xwpfDocument,outputStream,pdfOptions);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode("laidApply.pdf", "utf-8")));
        return ResponseEntity.ok().headers(headers).body(new ByteArrayResource(outputStream.toByteArray()));
    }

    private Map<String,String> laidMap(HrmUserLaid hrmUserLaid){
        Map<String,String> laidMap = Maps.newHashMap();
        //姓名
        laidMap.put("name",hrmUserLaid.getUser().getName());
        //工号
        laidMap.put("employno",hrmUserLaid.getUser().getEmpolyno());
        //所属部门
        laidMap.put("deptname",hrmUserLaid.getUser().getDepartment().getName());
        //技术职务
        laidMap.put("post",hrmUserLaid.getUser().getPost().getName());
        //员工职级
        laidMap.put("rank",hrmUserLaid.getUser().getRanks().getName());
        //设备归还
        laidMap.put("equipment", Objects.equals(hrmUserLaid.getEquipment(),"0") ? "未损坏" : "已损坏");
        //扣款
        laidMap.put("equipmentmoney", StringUtils.isBlank(hrmUserLaid.getEquipmentmoney()) ? "0": hrmUserLaid.getEquipmentmoney());
        //胸卡回收
        laidMap.put("card",Objects.equals(hrmUserLaid.getCard(),"0") ? "未归还" : "已归还");
        //扣款
        laidMap.put("cardmoney",StringUtils.isBlank(hrmUserLaid.getCardmoney()) ? "0": hrmUserLaid.getCardmoney());
        //签名
        laidMap.put("adminsign",hrmUserLaid.getAdminsign());
        //日期
        laidMap.put("admindate",hrmUserLaid.getAdmindate());
        //个人借款
        laidMap.put("borrow",Objects.equals(hrmUserLaid.getBorrow(),"0") ? "没有" : "有");
        //借款金额
        laidMap.put("borrowmoney",StringUtils.isBlank(hrmUserLaid.getBorrowmoney()) ? "0": hrmUserLaid.getBorrowmoney());
        //签名
        laidMap.put("financesign",hrmUserLaid.getFinancesign());
        //日期
        laidMap.put("financedate",hrmUserLaid.getFinancedate());
        //离职面谈
        laidMap.put("laidtalk",Objects.equals(hrmUserLaid.getLaidtalk(),"0") ? "无" : "有");
        //违约金
        laidMap.put("falsify",Objects.equals(hrmUserLaid.getFalsify(),"0") ? "无" : "有");
        //违约金额
        laidMap.put("falsifymoney",StringUtils.isBlank(hrmUserLaid.getFalsifymoney()) ? "0": hrmUserLaid.getFalsifymoney());
        //其他扣款
        laidMap.put("other",Objects.equals(hrmUserLaid.getOther(),"0") ? "无" : "有");
        //扣款金额
        laidMap.put("othermoney",StringUtils.isBlank(hrmUserLaid.getOthermoney()) ? "0": hrmUserLaid.getOthermoney());
        //经济补偿
        laidMap.put("damages",Objects.equals(hrmUserLaid.getDamages(),"0") ? "无" : "有");
        //补偿金额
        laidMap.put("annuity",StringUtils.isBlank(hrmUserLaid.getAnnuity()) ? "0": hrmUserLaid.getAnnuity());
        //签名
        laidMap.put("humansign",hrmUserLaid.getHumansign());
        //日期
        laidMap.put("humandate",hrmUserLaid.getHumandate());
        //最后工资结算日
        laidMap.put("lastworkdate",hrmUserLaid.getLastworkdate());
        //社保缴纳月
        laidMap.put("lastsocial",hrmUserLaid.getLastsocial());
        //工资发放日
        laidMap.put("lastsalary",hrmUserLaid.getLastsalary());
        return laidMap;
    }

    private Map<String,String> laidApplyMap(HrmUserLaid hrmUserLaid){
        Map<String,String> laidApplyMap = Maps.newHashMap();
        //编号
        laidApplyMap.put("formnum",hrmUserLaid.getName());
        //发起人
        laidApplyMap.put("name",hrmUserLaid.getUser().getName());
        //提交时间
        laidApplyMap.put("laidapplydate",hrmUserLaid.getLaidapplydate());
        //状态(审批状态)
        laidApplyMap.put("laidstatus",hrmUserLaid.getProcStatus().getName());
        //姓名
        //工号
        laidApplyMap.put("employno",hrmUserLaid.getUser().getEmpolyno());
        //部门
        laidApplyMap.put("deptname",hrmUserLaid.getUser().getDepartment().getName());
        //公司
        laidApplyMap.put("company",hrmUserLaid.getUser().getCompany().getName());
        //技术职务
        laidApplyMap.put("post",hrmUserLaid.getUser().getPost().getName());
        //职级
        laidApplyMap.put("rank",hrmUserLaid.getUser().getRanks().getName());
        //入职日期
        laidApplyMap.put("induction","");
        //申请离职日期
        //最后工作日
        laidApplyMap.put("lastworkdate",hrmUserLaid.getLastworkdate());
        //工作是否移交完
        laidApplyMap.put("handingstatus",Objects.equals(hrmUserLaid.getHandingstatus(),"0") ? "交接中" : "已确认");
        //离职原因
        laidApplyMap.put("laidmaincausename",hrmUserLaid.getLaidmaincause().getName());
        //离职类型
        laidApplyMap.put("laidtype",hrmUserLaid.getLaidtype().getName());
        //离职主要原因
        //流程编号
        //事业部总经理
        laidApplyMap.put("shiyebu","");
        //板块CEO
        laidApplyMap.put("ceo","");
        //执行总裁
        laidApplyMap.put("zhixing","");
        return laidApplyMap;
    }
}
