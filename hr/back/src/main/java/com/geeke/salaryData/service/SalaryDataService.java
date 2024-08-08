package com.geeke.salaryData.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.salaryBatch.entity.CodeID;
import com.geeke.salaryBatch.entity.SalaryBatch;
import com.geeke.salaryBatch.service.SalaryBatchService;
import com.geeke.salaryData.dao.SalaryArchivesInfoDao;
import com.geeke.salaryData.dao.SalaryDataDao;
import com.geeke.salaryData.entity.BillSalaryDetails;
import com.geeke.salaryData.entity.SalaryArchivesInfo;
import com.geeke.salaryData.entity.SalaryData;
import com.geeke.salaryData.entity.SalaryTemp;
import com.geeke.specialAddDeduct.dao.SpecialAddDeductDao;
import com.geeke.specialAddDeduct.entity.SpecialAddDeduct;
import com.geeke.specialAddDeduct.entity.TaxDeclareConfig;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.taxRate.dao.TaxDetailsInfoDao;
import com.geeke.taxRate.dao.TaxTypeInfoDao;
import com.geeke.taxRate.entity.TaxDetailsInfo;
import com.geeke.taxRate.entity.TaxTypeInfo;
import com.geeke.utils.*;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;

import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.params.Params;

import javax.servlet.http.HttpServletResponse;


/**
 * 薪资明细Service
 * @author
 * @version
 */

@Service("salaryDataService")
@Transactional
public class SalaryDataService extends CrudService<SalaryDataDao, SalaryData> {

    @Autowired
    private SalaryArchivesInfoDao salaryArchivesInfoDao;

    @Autowired
    private SalaryDataDao salaryDataDao;

    @Autowired
    private SalaryBatchService salaryBatchService;

    @Autowired
    private SalaryArchivesInfoService salaryArchivesInfoService;

    @Autowired
    private BillSalaryDetailsService billSalaryDetailsService;

    @Autowired
    private SalaryTempService salaryTempService;

    @Autowired
    private TaxDetailsInfoDao taxDetailsInfoDao;

    @Autowired
    private TaxTypeInfoDao taxTypeInfoDao;

    @Autowired
    private SpecialAddDeductDao specialAddDeductDao;

    @Override
    public SalaryData get(String id) {
        SalaryData salaryData = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   薪酬档案信息*/
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", salaryData.getId()));
        salaryData.setSalaryArchivesInfoList(salaryArchivesInfoService.listAll(params, ""));

        return salaryData;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public SalaryData save(SalaryData salaryData) {
        SalaryData salaryDataTemp = super.save(salaryData);

        /* 保存子表数据     薪酬档案信息 */
        saveSalaryArchivesInfoList(salaryDataTemp);

        return salaryDataTemp;
    }

    /**
     * 删除
     * @param salaryData
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(SalaryData salaryData) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     薪酬档案信息 */
        params = Lists.newArrayList();

        params.add(new Parameter("id", "=", salaryData.getId()));
        pageRequest = new PageRequest(params);
        salaryData.setSalaryArchivesInfoList(salaryArchivesInfoDao.listAll(pageRequest));

        if (salaryData.getSalaryArchivesInfoList() != null && salaryData.getSalaryArchivesInfoList().size() > 0) {
            salaryArchivesInfoService.bulkDelete(salaryData.getSalaryArchivesInfoList());
        }

        int rows = super.delete(salaryData);
        return rows;
    }

    /**
     * 批量删除
     * @param salaryDataList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<SalaryData> salaryDataList) {
        for (SalaryData salaryData : salaryDataList) {
            delete(salaryData);
        }

        return salaryDataList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, SalaryData entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (SalaryArchivesInfo child : entity.getSalaryArchivesInfoList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     薪酬档案信息 */
    private void saveSalaryArchivesInfoList(SalaryData salaryData) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("id", "=", salaryData.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<SalaryArchivesInfo> list_SalaryArchivesInfo = salaryArchivesInfoDao.listAll(pageRequest);

        List<SalaryArchivesInfo> deletes = Lists.newArrayList(); // 删除列表
        List<SalaryArchivesInfo> inserts = Lists.newArrayList(); // 添加列表
        List<SalaryArchivesInfo> updates = Lists.newArrayList(); // 更新列表
        for (SalaryArchivesInfo salaryArchivesInfoSaved : list_SalaryArchivesInfo) {
            boolean found = false;
            for (SalaryArchivesInfo salaryArchivesInfo : salaryData.getSalaryArchivesInfoList()) {
                if (salaryArchivesInfoSaved.getId().equals(salaryArchivesInfo.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(salaryArchivesInfoSaved);
            }
        }
        if (deletes.size() > 0) {
            salaryArchivesInfoService.bulkDelete(deletes);
        }
        for (SalaryArchivesInfo salaryArchivesInfo : salaryData.getSalaryArchivesInfoList()) {
            if (StringUtils.isBlank(salaryArchivesInfo.getId())) {
                salaryArchivesInfo.setId(salaryData.getId()); // 子表id与父表id一致

                inserts.add(salaryArchivesInfo);
            } else {
                updates.add(salaryArchivesInfo);
            }
        }
        if (updates.size() > 0) {
            salaryArchivesInfoService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            salaryArchivesInfoService.bulkInsert(inserts);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ResponseEntity<JSONObject> importSalary(MultipartFile dataFile,String taxYear,String billYear) throws Exception{
        // 读取 Excel 文件
        InputStream inputStream = dataFile.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        DataFormatter dataFormatter = new DataFormatter();
        List<SalaryData> salaryDataList = new ArrayList<>();

        //创建薪资批次信息
        SalaryBatch salaryBatch = salaryBatchService.insertSalarybatch(billYear,taxYear);
        //薪资项目下标
        SearchParams searchParams = new SearchParams();
        List<SalaryTemp> salaryTemps = salaryTempService.listAll(searchParams.getParams(),searchParams.getOrderby());
        //薪资项目名称
        Row salaryName = sheet.getRow(1);

        double totalAmount = 0;//实发合计
        int pep = 0;//导入人数
        for (int rowIndex = 2; rowIndex <= lastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            //空行校验
            if (row == null || !SalaryCountUtils.isRowNotEmpty(row)){
                continue;
            }

//            int lastCellNum = row.getLastCellNum();
            SalaryData salaryData = new SalaryData();
            //设置批次号
            salaryData.setSaBatchId(salaryBatch.getId());
            salaryData.setPayYearMonth(dataFormatter.formatCellValue(row.getCell(3)));

            row.getCell(0).setCellType(CellType.STRING);
            //员工编号
            String empCode = row.getCell(0).getStringCellValue();
            //查询员工档案信息
            SalaryArchivesInfo emp = salaryArchivesInfoService.getEmp(empCode);
            if (emp == null){
                throw new Exception("导入失败：未查询到工号为'"+empCode+"'的员工薪资档案！");
            }
            salaryData.setSaDoc(emp);
            for (int cellIndex = 4; cellIndex < 126; cellIndex++) {
                String itemName = "";
                Cell cellName = salaryName.getCell(cellIndex);
                String cellNameValue = dataFormatter.formatCellValue(cellName);
                for (SalaryTemp salaryTemp : salaryTemps) {
                    if (salaryTemp.getSaItemName().equals(cellNameValue)){
                        itemName = salaryTemp.getSaItemDesc();
                        break;
                    }
                }
                //薪资模板不匹配
                if (itemName == ""){
                    throw new Exception("薪资模板不匹配！");
                }
                Cell cell = row.getCell(cellIndex);
                String cellValue = dataFormatter.formatCellValue(cell);
                Field declaredField = salaryData.getClass().getDeclaredField("def" + itemName);
                declaredField.setAccessible(true);
                if (cellValue == null || "".equals(cellValue)){
                    declaredField.set(salaryData,new BigDecimal(0));
                }else{
                    declaredField.set(salaryData,new BigDecimal(cellValue));
                }
                //计算实发合计
                if (cellIndex == 124){
                    totalAmount = totalAmount + NumberUtils.toDouble(cellValue);
                }
            }
            //备注
            Cell cell = row.getCell(126);
            String cellValue = dataFormatter.formatCellValue(cell);
            salaryData.setNote(cellValue);
            salaryDataList.add(salaryData);
            pep ++;
        }
        workbook.close();

        //更新薪资批次总金额信息
//        salaryBatch.setTotalAmount(new BigDecimal(totalAmount));
        salaryBatch.setTotalDecimal(String.valueOf(pep));
        salaryBatchService.updateSalaryBatch(salaryBatch);
        //保存数据到数据库
        if (CollectionUtils.isNotEmpty(salaryDataList)) {
            super.bulkInsert(salaryDataList);
        }
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    public ResponseEntity<JSONObject> getBilling(List<SalaryData> entitys) {
        //工号+账单年月获取账单信息
        for (int i = 0; i < entitys.size(); i++) {
            SalaryData salaryData = entitys.get(i);
            SalaryArchivesInfo salaryArchivesInfo = salaryData.getSaDoc();
            String employeeNo = salaryArchivesInfo.getEmployeeNo();
            BillSalaryDetails bs = new BillSalaryDetails();
            String saBatchId = salaryData.getSaBatchId();
            SalaryBatch salaryBatch = salaryBatchService.get(saBatchId);
            bs.setEmployeeNo(employeeNo);
            bs.setBillYearMonth(salaryBatch.getBillYearMonth());
            List<BillSalaryDetails> billDetailsVos;
            try {
                billDetailsVos = billSalaryDetailsService.salaryCountBill(bs);
                if (billDetailsVos.size() == 0){
                    return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400,salaryArchivesInfo.getEmployeeName()+"当月社保数据为空！"));
                }
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_400));
            }
            //获取最新社保账单
            BillSalaryDetails billSalaryDetails = billDetailsVos.get(0);
            salaryData.setDef18(new BigDecimal(billSalaryDetails.getPersonMedical()));//医疗保险个人扣除
            salaryData.setDef17(new BigDecimal(billSalaryDetails.getPersonAnnuity()));//养老保险个人扣除
            salaryData.setDef19(new BigDecimal(billSalaryDetails.getPersonUnemploy()));//失业保险个人扣除
            salaryData.setDef20(new BigDecimal(billSalaryDetails.getPersonIllMedical()));//大病保险个人扣除
            salaryData.setDef21(new BigDecimal(billSalaryDetails.getPersonSocialSecurityAmount()));//社保个人扣除
            salaryData.setDef22(new BigDecimal(billSalaryDetails.getPersonAccumulaHund()));//公积金个人扣除
            doUpdate(salaryData);
        }
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    /**
     * 获取专项附加
     *
     * @param salarydataVoList
     * @return
     */
    public String batchGetSpecialAndSave(List<SalaryData> salarydataVoList) {
        //获取到sa_class_id
//        String saClassId = salarydataVoList.get(0).getSa_class_id();
//        //获取到属于专项附加项的薪资项目信息
//        List<EntityMap> itemA7s = salarybatchDao.getSalaryItemA7BySalaryClassId(saClassId);
        String msg = "未获取到专项附加数据:";
        for (int i = 0; i < salarydataVoList.size(); i++) {
            SalaryData salarydataVo = salarydataVoList.get(i);
            String id_card = salarydataVo.getSaDoc().getIdCardNo();
            //根据批次id查询薪资批次数据
            SalaryBatch currBatch = salaryBatchService.get(salarydataVo.getSaBatchId());
            String taxMonth = currBatch.getTaxMonth();
//            SalarydocVo salaryDocVo = salaryDocDao.getSalaryDocVoById(salarydataVo.getSa_doc_id());
//            String emp_code = salaryDocVo.getEmp_code();
            //本月是否已经获取专项附加
            BigDecimal socil = new BigDecimal(0);
            //获取当月薪资数据
            List<SalaryData> salaryDataList = salaryDataDao.getListByEmpidAndTaxMonth(salarydataVo.getSaDoc().getEmployeeNo(), taxMonth);
            for (int j = 0; j < salaryDataList.size(); j++) {
                SalaryData entityMap = salaryDataList.get(j);
                socil = socil   .add(entityMap.getDef37() == null ? new BigDecimal(0) : entityMap.getDef37())
                                .add(entityMap.getDef38() == null ? new BigDecimal(0) : entityMap.getDef38())
                                .add(entityMap.getDef39() == null ? new BigDecimal(0) : entityMap.getDef39())
                                .add(entityMap.getDef40() == null ? new BigDecimal(0) : entityMap.getDef40())
                                .add(entityMap.getDef41() == null ? new BigDecimal(0) : entityMap.getDef41());
//                socil = socil.add(SalaryBatchUtil.getSpecialAdditional(entityMap, itemA7s));
            }
            if (socil.compareTo(new BigDecimal(0)) == 1) {//已经获取过专项附加
                String emp_name = salarydataVo.getSaDoc().getEmployeeName();
                msg += emp_name + "（本月已获取）,";
                continue;
            }
            List<SpecialAddDeduct> specialByEmpCodeList = specialAddDeductDao.getSpecialAddDeductByTaxMonth(id_card, taxMonth);
            if (specialByEmpCodeList.size() == 0) {
                msg += salarydataVo.getSaDoc().getEmployeeName() + ",";
                continue;
            }
            updateSalaryData(salarydataVo,specialByEmpCodeList.get(0), salarydataVo.getId());
        }
        return msg;
    }

    private void updateSalaryData(SalaryData salaryData,SpecialAddDeduct specialAddDeduct, String id) {
        salaryData.setDef37(new BigDecimal(specialAddDeduct.getAccSubEducation()));
        salaryData.setDef38(new BigDecimal(specialAddDeduct.getAccKeepEducation()));
        salaryData.setDef39(new BigDecimal(specialAddDeduct.getAccHouseRent()));
        salaryData.setDef40(new BigDecimal(specialAddDeduct.getAccHouseLoanTax()));
        salaryData.setDef41(new BigDecimal(specialAddDeduct.getAccCareOld()));
        salaryData.setDef114(new BigDecimal(specialAddDeduct.getAccBabyPassport()));
        salaryData.setDef115(new BigDecimal(specialAddDeduct.getAccPersonAnnuity()));
        salaryData.setId(id);
        doUpdate(salaryData);
    }


    public void batchCalculateAndSave(List<SalaryData> entitys) {
        try {
            //批次号
            SalaryData salaryData1 = entitys.get(0);
            String saBatchId = salaryData1.getSaBatchId();
//            final int MAX_THREADS = 10; //定义线程数最大值
//            ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
//            CountDownLatch latch = new CountDownLatch(entitys.size());
//            for (int i = 0; i < entitys.size(); i++) {
//                final int index = i;
//                Runnable run = new Runnable() {
//                    public void run() {
//                        try {
////                        new Thread().sleep(1000);
////                        //模拟耗时操作
//                            System.out.println("[1]" + Thread.currentThread().getName() + "----当前计算位置：" + index + "--------");
//                            SalaryData salaryData = entitys.get(index);
//
//                            //根据批次id查询薪资批次数据
//                            SalaryBatch salarydataVoBybatchId = salaryBatchService.get(saBatchId);
//                            //计算
//                            SalaryData salaryCalculate = salaryCalculate(salaryData,salarydataVoBybatchId);
//                            //保存
//                            dao.insert(salaryCalculate);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            latch.countDown();
//                        }
//                    }
//                };
//                pool.execute(run);
//            }
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            pool.shutdown();
//            pool.shutdownNow();
            BigDecimal totalAmount = new BigDecimal(0);//实发合计
            //查询该薪资类别下的公积金上限
            List<TaxDeclareConfig> configurationList = specialAddDeductDao.getTaxDeclareConfig();
            //根据批次id查询薪资批次数据
            SalaryBatch salarydataVoBybatchId = salaryBatchService.get(saBatchId);
            //模拟耗时操作
            for (int i = 0; i < entitys.size(); i++) {
                int index = i;
                System.out.println("[1]" + Thread.currentThread().getName() + "----当前计算位置：" + index + "--------");
                SalaryData salaryData = entitys.get(index);


                //计算
                SalaryData salaryCalculate = salaryCalculate(salaryData, salarydataVoBybatchId,configurationList);
                //保存
                dao.update(salaryCalculate);
                //更新薪资批次信息
                totalAmount = totalAmount.add(salaryCalculate.getDef111());

            }
            salarydataVoBybatchId.setTotalAmount(totalAmount);
            salaryBatchService.updateSalaryBatch(salarydataVoBybatchId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Transactional
    public SalaryData salaryCalculate(SalaryData dataVo,SalaryBatch batchEntityMap,List<TaxDeclareConfig> configurationList) {
        //根据id获取该员工薪资数据
        SalaryData salarydataVoBybatchId = super.get(dataVo.getId());
        //获取到属于专项附加项的薪资项目信息
        List<SalaryTemp> itemA7s = salaryTempService.getSalaryItemA7();

        //变量定义
        BigDecimal def1 = BigDecimalUtils.getIsZero(dataVo.getDef1());//基本工资
        BigDecimal def2 = BigDecimalUtils.getIsZero(dataVo.getDef2());//奖金
        BigDecimal def3 = BigDecimalUtils.getIsZero(dataVo.getDef3());//绩效奖金
        BigDecimal def4 = BigDecimalUtils.getIsZero(dataVo.getDef4());//加班费
        BigDecimal def5 = BigDecimalUtils.getIsZero(dataVo.getDef5());//薪资调整
        BigDecimal def6 = BigDecimalUtils.getIsZero(dataVo.getDef6());//餐饮补助
        BigDecimal def7 = BigDecimalUtils.getIsZero(dataVo.getDef7());//交通补助
        BigDecimal def8 = BigDecimalUtils.getIsZero(dataVo.getDef8());//差旅津贴
        BigDecimal def9 = BigDecimalUtils.getIsZero(dataVo.getDef9());//通信补贴
        BigDecimal def10 = BigDecimalUtils.getIsZero(dataVo.getDef10());//补其他
        BigDecimal def11 = BigDecimalUtils.getIsZero(dataVo.getDef11());//高温费
        BigDecimal def12 = BigDecimalUtils.getIsZero(dataVo.getDef12());//独生子女费
        BigDecimal def13 = BigDecimalUtils.getIsZero(dataVo.getDef13());//十三薪
        BigDecimal def14 = BigDecimalUtils.getIsZero(dataVo.getDef14());//迟到扣款
        BigDecimal def15 = BigDecimalUtils.getIsZero(dataVo.getDef15());//病事假扣款
        BigDecimal def16 = BigDecimalUtils.ZERO;//本次工资应发合计
        BigDecimal def17 = salarydataVoBybatchId.getDef17();//养老保险个人扣除*****
        BigDecimal def18 = salarydataVoBybatchId.getDef18();//医疗保险个人扣除
        BigDecimal def19 = salarydataVoBybatchId.getDef19();//失业保险个人扣除
        BigDecimal def20 = salarydataVoBybatchId.getDef20();//大病保险个人扣除
        BigDecimal def21 = BigDecimalUtils.ZERO;//社保个人扣除
        BigDecimal def22 = salarydataVoBybatchId.getDef22();//公积金个人扣除*****
        BigDecimal def23 = BigDecimalUtils.ZERO;//公积金免税金额 来源系统*******************************************
        BigDecimal def24 = BigDecimalUtils.ZERO;//公积金超额款 来源系统*******************************************
        BigDecimal def25 = BigDecimalUtils.getIsZero(dataVo.getDef25());//企业年金个人扣除
        BigDecimal def26 = BigDecimalUtils.ZERO;//企业年金免税金额
        BigDecimal def27 = BigDecimalUtils.ZERO;//企业年金合并计税金额

        BigDecimal def28 = BigDecimalUtils.getIsZero(dataVo.getDef28());//工资薪金免税收入
        BigDecimal def29 = BigDecimalUtils.getIsZero(dataVo.getDef29());//工资薪金免税事项
        BigDecimal def30 = BigDecimalUtils.getIsZero(dataVo.getDef30());//工资薪金其他扣除
        BigDecimal def31 = BigDecimalUtils.getIsZero(dataVo.getDef31());//工资薪金其他扣除事项
        BigDecimal def32 = BigDecimalUtils.getIsZero(dataVo.getDef32());//工资薪金其他合并计税项(计税不发)
        BigDecimal def33 = BigDecimalUtils.ZERO;//当月免税所得额
        BigDecimal def34 = BigDecimalUtils.ZERO;//本次工资应计税金额
        BigDecimal def35 = BigDecimalUtils.ZERO;//上次累计工资应计税金额
        BigDecimal def36 = BigDecimalUtils.ZERO;//累计工资应计税金额
        BigDecimal def37 = salarydataVoBybatchId.getDef37();//子女教育免税累计*******
        BigDecimal def38 = salarydataVoBybatchId.getDef38();//继续教育免税累计
        BigDecimal def39 = salarydataVoBybatchId.getDef39();//租房免税累计
        BigDecimal def40 = salarydataVoBybatchId.getDef40();//住房贷款免税累计
        BigDecimal def41 = salarydataVoBybatchId.getDef41();//赡养老人免税累计*******
        BigDecimal def42 = BigDecimalUtils.ZERO;//当前累计工资计税金额
        BigDecimal def43 = BigDecimalUtils.ZERO;//当前累计工资个税
//        BigDecimal def44 = BigDecimalUtils.getIsZero(dataVo.getDef44());//上次累计工资个税
        BigDecimal def44 = BigDecimalUtils.ZERO;
        BigDecimal def45 = BigDecimalUtils.ZERO;//本次工资个税
        BigDecimal def46 = BigDecimalUtils.ZERO;//残疾人减免税额（薪资）
        BigDecimal def47 = BigDecimalUtils.ZERO;//本次工资应纳个税
        BigDecimal def48 = BigDecimalUtils.getIsZero(dataVo.getDef48());//不计税补贴项（税后减项）
        BigDecimal def49 = BigDecimalUtils.getIsZero(dataVo.getDef49());//不计税补贴项（税后增加项）
        BigDecimal def50 = BigDecimalUtils.ZERO;//本次工资项目实发金额

        BigDecimal def51 = BigDecimalUtils.getIsZero(dataVo.getDef51());//本次年终奖金额
        BigDecimal def52 = BigDecimalUtils.getIsZero(dataVo.getDef52());//年终金免税收入
        BigDecimal def53 = BigDecimalUtils.getIsZero(dataVo.getDef53());//年终金免税事项
        BigDecimal def54 = BigDecimalUtils.getIsZero(dataVo.getDef54());//年终金其他扣除
        BigDecimal def55 = BigDecimalUtils.getIsZero(dataVo.getDef55());//年终金其他扣除事项
        BigDecimal def56 = BigDecimalUtils.getIsZero(dataVo.getDef56());//年终金其他合并计税项(计税不发)
        BigDecimal def57 = BigDecimalUtils.ZERO;//本次年终奖应计税金额
        BigDecimal def58 = BigDecimalUtils.ZERO;//上次年终奖累计税金额
        BigDecimal def59 = BigDecimalUtils.ZERO;//累计年终奖计税金额
        BigDecimal def60 = BigDecimalUtils.ZERO;//累计年终奖个税
        BigDecimal def61 = BigDecimalUtils.ZERO;//上次累计年终奖个税
        BigDecimal def62 = BigDecimalUtils.ZERO;//本次年终奖个税
        BigDecimal def63 = BigDecimalUtils.ZERO;//残疾人减免税额（年终奖）
        BigDecimal def64 = BigDecimalUtils.ZERO;//本次年终奖应纳个税
        BigDecimal def65 = BigDecimalUtils.ZERO;//本次年终奖实发金额

        BigDecimal def66 = BigDecimalUtils.getIsZero(dataVo.getDef66());//本次劳务费金额
        BigDecimal def67 = BigDecimalUtils.getIsZero(dataVo.getDef67());//劳务费免税收入
        BigDecimal def68 = BigDecimalUtils.getIsZero(dataVo.getDef68());//劳务费免税事项
        BigDecimal def69 = BigDecimalUtils.getIsZero(dataVo.getDef69());//劳务费其他扣除
        BigDecimal def70 = BigDecimalUtils.getIsZero(dataVo.getDef70());//劳务费其他扣除事项
        BigDecimal def71 = BigDecimalUtils.getIsZero(dataVo.getDef71());//劳务费其他合并计税项(计税不发)
        BigDecimal def72 = BigDecimalUtils.ZERO;//本次劳务费应计税金额
        BigDecimal def73 = BigDecimalUtils.ZERO;//上次劳务费累计税金额
        BigDecimal def74 = BigDecimalUtils.ZERO;//累计劳务费计税金额
        BigDecimal def75 = BigDecimalUtils.ZERO;//累计劳务费个税
        BigDecimal def76 = BigDecimalUtils.ZERO;//上次累计劳务费个税
        BigDecimal def77 = BigDecimalUtils.ZERO;//本次劳务报酬个税
        BigDecimal def78 = BigDecimalUtils.ZERO;//残疾人减免税额（劳务费）
        BigDecimal def79 = BigDecimalUtils.ZERO;//本次劳务费应纳个税
        BigDecimal def80 = BigDecimalUtils.ZERO;//本次劳务报酬实发金额

        BigDecimal def81 = BigDecimalUtils.getIsZero(dataVo.getDef81());//实习生劳务费金额
        BigDecimal def82 = BigDecimalUtils.getIsZero(dataVo.getDef82());//实习生劳务费免税收入
        BigDecimal def83 = BigDecimalUtils.getIsZero(dataVo.getDef83());//实习生劳务费免税事项
        BigDecimal def84 = BigDecimalUtils.getIsZero(dataVo.getDef84());//实习生劳务费其他扣除
        BigDecimal def85 = BigDecimalUtils.getIsZero(dataVo.getDef85());//实习生劳务费其他扣除事项
        BigDecimal def86 = BigDecimalUtils.getIsZero(dataVo.getDef86());//实习生劳务费其他合并计税项(计税不发)
        BigDecimal def87 = BigDecimalUtils.ZERO;//实习生本月免税所得额
        BigDecimal def88 = BigDecimalUtils.ZERO;//本次其他劳务费应计税金额
        BigDecimal def89 = BigDecimalUtils.ZERO;//上次累计其他劳务费计税金额
        BigDecimal def90 = BigDecimalUtils.ZERO;//累计其他劳务费计税金额
        BigDecimal def91 = BigDecimalUtils.ZERO;//累计其他劳务费个税
        BigDecimal def92 = BigDecimalUtils.ZERO;//上次累计其他劳务费个税
        BigDecimal def93 = BigDecimalUtils.ZERO;//本次其他劳务费个税
        BigDecimal def94 = BigDecimalUtils.ZERO;//残疾人减免税额(其他劳务费)
        BigDecimal def95 = BigDecimalUtils.ZERO;//本次其他劳务费应纳个税
        BigDecimal def96 = BigDecimalUtils.ZERO;//本次其它劳务费实发金额

        BigDecimal def97 = BigDecimalUtils.getIsZero(dataVo.getDef97());//离职补偿金金额
        BigDecimal def98 = BigDecimalUtils.getIsZero(dataVo.getDef98());//离职补偿金免税收入
        BigDecimal def99 = BigDecimalUtils.getIsZero(dataVo.getDef99());//离职补偿金免税事项
        BigDecimal def100 = BigDecimalUtils.getIsZero(dataVo.getDef100());//离职补偿金其他扣除
        BigDecimal def101 = BigDecimalUtils.getIsZero(dataVo.getDef101());//离职补偿金其他扣除事项
        BigDecimal def102 = BigDecimalUtils.getIsZero(dataVo.getDef102());//离职补偿金其他合并计税项(计税不发)
        BigDecimal def103 = BigDecimalUtils.ZERO;//离职补偿金应计税金额
        BigDecimal def104 = BigDecimalUtils.ZERO;//离职补偿金免税金额***************************
        BigDecimal def105 = BigDecimalUtils.ZERO;//离职补偿金计税金额

        BigDecimal def106 = BigDecimalUtils.ZERO;//离职补偿金个税
        BigDecimal def107 = BigDecimalUtils.ZERO;//残疾人减免税额（离职）
        BigDecimal def108 = BigDecimalUtils.ZERO;//离职补偿金应纳个税
        BigDecimal def109 = BigDecimalUtils.ZERO;//离职补偿金实发金额

        BigDecimal def110 = BigDecimalUtils.ZERO;//本次应发合计
        BigDecimal def111 = BigDecimalUtils.ZERO;//本次实发合计
        BigDecimal def112 = BigDecimalUtils.ZERO;//本次扣税合计
        //备注
//        String def113 = BigDecimalUtils.string(dataVo.getDef113());
        //累计婴幼儿照护费用
        BigDecimal def114 = BigDecimal.ZERO;
        //计算插件
//        Binding binding = new Binding();
//        binding.setVariable("language", "Groovy");
//        GroovyShell shell = new GroovyShell(binding);
        //残疾人减免（总）
        BigDecimal allDisabilityMoney = BigDecimalUtils.ZERO;
        //当前残疾人减免税额
        BigDecimal currentDisabilityMoney = BigDecimalUtils.StringToBigDecimal("6000", 4);
        //是否合并计税
        String is_unite_tax = batchEntityMap.getIsUniteTax();

//******************************************************************************************************************************************薪资
        //1.本次工资应发合计=基本工资+奖金+绩效奖金+加班费+薪资调整+餐饮补助+交通补助+差旅津贴+通信补贴+补其他+高温费+独生子女费+十三薪-迟到扣款-病事假扣款
        //本次工资应发合计=（所有薪资项目成本组属于  基本工资）的增项 相加 -（所有薪资项目成本组属于  基本工资）的减项
//        def16 = def1.add(def2).add(def3).add(def4).add(def5).add(def6).add(def7).add(def8).add(def9).add(def10).add(def11).add(def12).add(def13).subtract(def14).subtract(def15);
        def16 = BigDecimal.ZERO;
        List<SalaryTemp> itemA1 = salaryTempService.getSalaryItemA1();
//        for (int i = 114; i <items.size() ; i++) {
        for (int i = 0; i < itemA1.size(); i++) {
            String item_serial_new_id = itemA1.get(i).getSaItemDesc();
            //0增项 1减项
            String property = itemA1.get(i).getProperty();
            try {
                Method method = SalaryData.class.getMethod("getDef" + item_serial_new_id, null);
                Object result = method.invoke(dataVo, null) != null ? method.invoke(dataVo, null) : "0.00";
                BigDecimal bdObj = BigDecimalUtils.object2Bigdecimal(result, 4);
                if ("1".equals(property)) {
                    def16 = def16.subtract(bdObj);
                } else if ("0".equals(property)) {
                    def16 = def16.add(bdObj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //2.社保个人扣除=养老保险个人扣除+医疗保险个人扣除+失业保险个人扣除+大病保险个人扣除
        def21 = def17.add(def18).add(def19).add(def20);
        //企业年金合并计税金额=企业年金个人扣除-企业年金免税金额
//        def27 = def25.subtract(def26);

        //公积金超额上限=公积金免税金额 def23
//        BigDecimal accumulationCeil = BigDecimalUtils.ZERO;
        String accIsTax = "";
        //企业年金上限=企业年金免税金额 def26
        String enterIsTax = "";
        int size = configurationList.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                TaxDeclareConfig configuration = configurationList.get(i);
                //公积金唯一标识符为0
                if ("accumulation_fund".equals(configuration.getCode())) {
                    def23 = BigDecimalUtils.StringToBigDecimal(configuration.getLimitAmount(), 4);
                    accIsTax = configuration.getIsMergeTax();
                }
                //企业年金
                if ("unit_annuity".equals(configuration.getCode())) {
                    def26 = BigDecimalUtils.StringToBigDecimal(configuration.getLimitAmount(), 4);
                    enterIsTax = configuration.getIsMergeTax();
                }
            }
        }
        //公积金扣除
        BigDecimal go = BigDecimalUtils.ZERO;
        if (def22.compareTo(BigDecimalUtils.ZERO) == 1) {//当公积金个人扣除大于0
            if (def22.compareTo(def23) == 1) {//公积金个人扣除金额大于公积金免税额
                //公积金超额款=公积金个人扣除-公积金免税金额
                def24 = def22.subtract(def23);
                go = def23;
            } else {
                def23 = BigDecimalUtils.ZERO;
                go = def22;
            }
        } else {
            go = def22;
            def23 = BigDecimalUtils.ZERO;
        }
        //企业年金扣除
        BigDecimal qi = BigDecimalUtils.ZERO;
        if (def25.compareTo(BigDecimalUtils.ZERO) == 1) {//当企业年金个人扣除大于0
            if (def25.compareTo(def26) == 1) {
                //企业年金合并计税金额=企业年金个人扣除-企业年金免税金额
                def27 = def25.subtract(def26);
                qi = def26;
            } else {
                def26 = BigDecimalUtils.ZERO;
                qi = def25;

            }
        } else {
            def26 = BigDecimalUtils.ZERO;
        }
//        if(formula.size() != 0){//公式
//            binding.setVariable("go",go);
//            binding.setVariable("qi",qi);
//        }

        //3.本次工资应计税金额=本次工资应发合计+企业年金其他合并计税项(计税不发)-社保个人扣除-工资薪金免税收入-工资薪金其他扣除-企业年金扣除-公积金扣除
        def34 = def16.add(def32).subtract(def21).subtract(def28).subtract(def30).subtract(qi).subtract(go);
        //公积金超额款与企业年金合并计税金额是否合并计税
        if ("1".equals(accIsTax)) {//1为需要合并计税
            def34 = def34.add(def24);
        } else {
            def24 = BigDecimalUtils.ZERO;
        }
        if ("1".equals(enterIsTax)) {
            def34 = def34.add(def27);
        } else {
            def27 = BigDecimalUtils.ZERO;
        }
        String tax_month = batchEntityMap.getTaxMonth();
        //报税月份
        String month = tax_month.substring(4);
        //上月
        String last_yearMonth = DateUtils.topreMonth(tax_month);
        String last_month = last_yearMonth.substring(4);
        //报税年份
        String year = last_yearMonth.substring(0, 4);
        //获取员工唯一号
        String empCode = dataVo.getSaDoc().getEmployeeNo();
        //获取员工身份证号
        String idCard = dataVo.getSaDoc().getIdCardNo();
        //获取到员工最新的入职日期
        List<SalaryArchivesInfo> newestApplyOnPostDate = salaryArchivesInfoDao.NewestApplyOnPostDate(idCard);
        String newestYearMonth = "";
        if (newestApplyOnPostDate.size() > 0) {
            //最新的入职时间
            String applyOnPostDate = newestApplyOnPostDate.get(0).getEntryTime();
            //年
            String year1 = applyOnPostDate.substring(0, 4);
            //月
            String month1 = applyOnPostDate.substring(5, 7);
            newestYearMonth = year1 + "" + month1;
        }
        //根据salaryClassId查询对应的薪资档案
//        EntityMap theCurrentSaDoc = salarybatchDao.getSalaryDocBySalaryClassId(saClassId, empCode);
//        //获取到当前的报税主体
//        String currentTaxFlag = theCurrentSaDoc.getString("tax_flag");
        //薪资计算查询上次当前累计数-》--
        List<SalaryData> entityMapList = dao.salaryDataByempAndYear(dataVo.getSaDoc().getEmployeeNo(), year+"02", (1+Integer.parseInt(year))+"01");
        //
        boolean isSamePost=true;
        //判断是否是二月计税
        if (Integer.parseInt(month) == 02) {//是二月计税
            def35 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计工资应计税金额
            def44 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计工资个税
            def58 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计年终奖金额
            def61 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计年终个税
            def73 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次劳务费累计税金额
            def76 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计劳务费个税
            def89 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计其他劳务费
            // 计税金额
            def92 = BigDecimalUtils.StringToBigDecimal("0", 4);//上次累计其他劳务费个税
        } else { //不是二月计税
            if (entityMapList.size() > 0) {
                //工资标记
                int f = 0;
                //实习生劳务费标记
                int s = 0;
                //判断上次是否有累计数
                for (int i = 0; i < entityMapList.size(); i++) {
                    SalaryData entityMapL = entityMapList.get(i);
                    String dataMonth = batchEntityMap.getTaxMonth().substring(4);
                    //new requirements：if the tax reporting months are not consecutive，need determine if last emp_post_id is the same as this one ，if different,accumulate by 0
//                    if (i==0&&!(Integer.parseInt(dataMonth)==Integer.parseInt(last_month))){
//                        SalarydocVo lastSaDoc = salaryDocDao.getSalaryDocVoById(entityMapL.getSaDoc().getId());
//                        SalarydocVo thisSaDoc = salaryDocDao.getSalaryDocVoById(dataVo.getSaDoc().getId());
//                        if (!lastSaDoc.getEmp_post_id().equals(thisSaDoc.getEmp_post_id())){
//                            isSamePost=false;
//                        }
//                    }
                    //小于本月&&本次工资应发合计不等于0&&上次工资应发合计不等于0&&还未取到满足的累计数
                    if (Integer.parseInt(dataMonth) <= Integer.parseInt(last_month) && def16.compareTo(BigDecimalUtils.ZERO) != 0 && !"0".equals(entityMapL.getDef16()) && f == 0) {//有上次工资数据
                        //页面数据是否为空
                        if (def35.compareTo(BigDecimalUtils.ZERO) == 0) {
                            //当前累计工资应计税金额是否为空
                            if (!"".equals(entityMapL.getDef36()) || entityMapL.getDef36() != null&&isSamePost) {
                                //上月的（当前累计数）不为空则把上月当前累计数赋值给这个月的（上月累计数字段）
                                def35 = entityMapL.getDef36();
                            }
                        }
                        if (def44.compareTo(BigDecimalUtils.ZERO) == 0) {
                            //当前累计工资应计个税是否为空
                            if (!"".equals(entityMapL.getDef43()) || entityMapL.getDef43() != null&&isSamePost) {
                                def44 = entityMapL.getDef43();
                            }
                        }
                        f++;
                    } else if (Integer.parseInt(dataMonth) <= Integer.parseInt(last_month) && f == 0 && def16.compareTo(BigDecimalUtils.ZERO) == 0 && !"0".equals(entityMapL.getDef16()) && (def21.compareTo(BigDecimal.ZERO) < 0 || def22.compareTo(BigDecimal.ZERO) < 0)) {//针对本次应发合计为0且社保公积金存在负数的情况
                        if (def35.compareTo(BigDecimalUtils.ZERO) == 0) {
                            //当前累计工资应计税金额是否为空
                            if (!"".equals(entityMapL.getDef36()) || entityMapL.getDef36() != null&&isSamePost) {
                                //上月的（当前累计数）不为空则把上月当前累计数赋值给这个月的（上月累计数字段）
                                def35 = entityMapL.getDef36();
                            }
                        }
                        if (def44.compareTo(BigDecimalUtils.ZERO) == 0) {
                            //当前累计工资应计个税是否为空
                            if (!"".equals(entityMapL.getDef43()) || entityMapL.getDef43() != null&&isSamePost) {
                                def44 = entityMapL.getDef43();
                            }
                        }
                        f++;
                    }
                    if (Integer.parseInt(dataMonth) <= Integer.parseInt(last_month) && def81.compareTo(BigDecimalUtils.ZERO) != 0 && !"0".equals(entityMapL.getDef81()) && s == 0) {
                        if (def89.compareTo(BigDecimalUtils.ZERO) == 0) {
                            //累计实习生劳务费应计税金额是否为空
                            if (!"".equals(entityMapL.getDef90()) || entityMapL.getDef90() != null&&isSamePost) {
                                def89 = entityMapL.getDef90();
                            }
                        }
                        if (def92.compareTo(BigDecimalUtils.ZERO) == 0) {
                            //累计实习生劳务费应计个税是否为空
                            if (!"".equals(entityMapL.getDef91()) || entityMapL.getDef91() != null&&isSamePost) {
                                def92 = entityMapL.getDef91();
                            }
                        }
                        s++;
                    }

                    //def46 def63 def78 def94 def107累加每一项残疾免税额
                    allDisabilityMoney = allDisabilityMoney.add(entityMapL.getDef46())
                            .add(entityMapL.getDef63())
                            .add(entityMapL.getDef78())
                            .add(entityMapL.getDef94())
                            .add(entityMapL.getDef107());
                }
            } else {//新员工
                allDisabilityMoney = BigDecimalUtils.ZERO;
            }
        }

        SalaryArchivesInfo salaryDocVo = salaryArchivesInfoDao.get(dataVo.getSaDoc().getId());
        String taxId = salaryDocVo.getSalaryRateTable();
        List<TaxDetailsInfo> taxTableList = taxDetailsInfoDao.getTaxTableList(taxId);

        // 获取此员工本月已发放工资数据
        List<SalaryData> salaryDataList = dao.getListByEmpidAndTaxMonth(dataVo.getSaDoc().getEmployeeNo(), tax_month);
        //判断是否第二次发工资，如果第二次，则不去加上月的专项累计
        boolean isTwice = false;
        // 工资薪金 判断本批次是否需要合并计税
//        BigDecimal def35_1 = BigDecimalUtils.ZERO;
        //已发工资
        BigDecimal def16_2 = BigDecimalUtils.ZERO;
        for (int i = 0; i < salaryDataList.size(); i++) {
            SalaryData entityMap = salaryDataList.get(i);
            def16_2 = def16_2.add(entityMap.getDef16());
        }
        //获取本月上次 当前累计计税金额
        if (is_unite_tax.equals("1")) {
            if (salaryDataList.size() != 0) {
                int l = 0;
                for (int i = 0; i < salaryDataList.size(); i++) {
                    SalaryData entityMap = salaryDataList.get(i);
                    BigDecimal yingfa = entityMap.getDef16();
                    BigDecimal jishuibufa = entityMap.getDef32();
                    if (def16.compareTo(BigDecimalUtils.ZERO) != 0 && yingfa.add(jishuibufa).compareTo(BigDecimalUtils.ZERO) != 0 && l == 0) {
                        def35 = entityMap.getDef42();
                        isTwice = true;
                        def44 = entityMap.getDef43();
                        l++;
                    }
                }
            }
        }

        //4.专项附加免税=子女教育免税累计+继续教育免税累计+租房免税累计+住房贷款免税累计+赡养老人免税累计.......
        BigDecimal a = BigDecimal.ZERO;
        if (itemA7s != null && itemA7s.size() > 0) {
            a = SalaryCountUtils.getSpecialAdditional(salarydataVoBybatchId, itemA7s);
        }
        //上次专项附加扣除
        BigDecimal a1 = BigDecimalUtils.ZERO;
        //上次是否是末次
        int num1 = -1;
        //若有专项附加，补上上月已扣专项附加
        if (Integer.parseInt(month) != 02) {//不是二月计税
            String monthC = "";//上次发工资月份
            for (int i = 0; i < entityMapList.size(); i++) {
                SalaryData entityMapL = entityMapList.get(i);
                String saBatchId = entityMapL.getSaBatchId();
                //根据批次id查询薪资批次数据
                SalaryBatch currBatch = salaryBatchService.get(saBatchId);
                String dataMonth = currBatch.getTaxMonth();
                if (Integer.parseInt(dataMonth) <= Integer.parseInt(last_month) && def16.compareTo(BigDecimalUtils.ZERO) != 0 && !"0".equals(entityMapL.getDef16())) {
                    if (monthC.equals("") || monthC.equals(dataMonth)) {//同月循环
                        a1 = a1.add(SalaryCountUtils.getSpecialAdditional(entityMapL, itemA7s));
                        num1++;
                        monthC = dataMonth;
                        if (a1.compareTo(BigDecimalUtils.ZERO) == 1) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        //上月累计数金额=上月累计数金额+上月专项附加
        if (num1 != 0 && !isTwice) {//不是末次扣除专项
            def35 = def35.add(a1);
        }

        String is_already = "1";//上年度是否超过6w 0 否 1 是
        //当前报税月
        String cu_tax_month = batchEntityMap.getTaxMonth();
        //报入职报税月
//        String departure_belong_tax = entityMap_dom.getString("office_belong_tax");
        //5.
        if ("0".equals(is_already)) {//未达6w
            //未达6w
            def33 = BigDecimalUtils.StringToBigDecimal("0", 2);//当月免税额为0
//            def35 = def34_1.add(def35);//上次累计工资应计税金额
            def36 = def35.add(def34);//累计工资应计税金额=上次累计工资应计税金额+本次工资应计税金额
            BigDecimal w6 = BigDecimalUtils.StringToBigDecimal("60000", 2);
            def42 = def36.subtract(a);//当前累计工资计税金额
            if (def36.compareTo(a) == 1) {//累计工资应计税金额是否大于专项扣除
                if (def42.compareTo(w6) == 1) {//当前累计工资计税金额大于6w
                    BigDecimal def42_1 = def42.subtract(w6);
                    for (int i = 0; i < taxTableList.size(); i++) {
                        TaxDetailsInfo entityMapTax = taxTableList.get(i);
                        //如果当前累计工资计税金额大于下限，小于等于上限
                        if (def42_1.compareTo(entityMapTax.getAmountDown()) == 1 && def42_1.compareTo(entityMapTax.getAmountUp()) != 1) {
                            //当前累计工资个税=超出部分计税金额*当前税率-算数扣除数
                            def43 = def42_1.multiply(entityMapTax.getRate()).divide(new BigDecimal(100)).subtract(entityMapTax.getSpeedDeductAmount());
                            def43 = def43.setScale(2, BigDecimal.ROUND_HALF_UP);
                            break;
                        }
                    }
                } else {
                    def43 = BigDecimalUtils.ZERO;
                }
            } else {
                def43 = BigDecimalUtils.ZERO;
            }
        } else {//达到6w

            if ((def16_2.compareTo(BigDecimalUtils.ZERO) == 0 && def16.compareTo(BigDecimalUtils.ZERO) != 0) || (def16_2.compareTo(BigDecimalUtils.ZERO) == 0 && def16.compareTo(BigDecimalUtils.ZERO) == 0) && (def21.compareTo(BigDecimal.ZERO) < 0 || def22.compareTo(BigDecimal.ZERO) < 0)) {//本月未发放工资&&本次工资应发合计不等于0
                //本次工资应计税金额 = 本次工资应计税金额 - 当月免税额
                //当月免税额为5000
                def33 = BigDecimalUtils.StringToBigDecimal("5000", 2);
                List<SalaryData> entityMapList2 = dao.salaryDataByempAndYear(dataVo.getSaDoc().getEmployeeNo(), year+"02", (1+Integer.parseInt(year))+"01");
                if (Integer.parseInt(month) != 02) {//2月报税按5k
                    if (cu_tax_month.equals(newestYearMonth)) {//第一次发工资
                        //任职入职日期
                        String en = salaryDocVo.getEntryTime();
                        String cu_tax_month_new = cu_tax_month.substring(0, 4) + "-" + cu_tax_month.substring(4, 6) + "-01";
                        int monthSpace = 0;
                        try {
                            monthSpace = DateUtils.getMonthSpace(en, cu_tax_month_new);
                            //if cross year,start accumulating from February
                            if (!cu_tax_month.substring(0, 4).equals(en.substring(0, 4))){
                                String tempTaxMonth=cu_tax_month.substring(0,4)+"-01-01";
                                monthSpace = DateUtils.getMonthSpace(tempTaxMonth, cu_tax_month_new);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        def33 = def33.multiply(BigDecimalUtils.object2Bigdecimal(monthSpace, 2));

                    } else {
                        if (entityMapList2.size() > 0) {
                            SalaryData entityMap = entityMapList2.get(0);
                            SalaryBatch salaryBatch = salaryBatchService.get(entityMap.getSaBatchId());
                            String t = salaryBatch.getTaxMonth();
                            String l_month = t.substring(0, 4) + "-" + t.substring(4, 6) + "-01";
                            String cu_tax_month_new = cu_tax_month.substring(0, 4) + "-" + cu_tax_month.substring(4, 6) + "-01";
                            int monthSpace = 0;
                            try {
                                monthSpace = DateUtils.getMonthSpace(l_month, cu_tax_month_new);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            def33 = def33.multiply(BigDecimalUtils.object2Bigdecimal(monthSpace, 2));

                        }
                    }
                }
                def34 = def34.subtract(def33);
            } else {//该员工本月已发过 工资==》不扣免税额
//                def36 = def36.add(def35_1);
            }

            //算累计工资应计税金额  上次累计工资应计税金额是否为0
            if (def35.compareTo(BigDecimalUtils.ZERO) == 0) {//为0 算当月工资
                def36 = def34;//累计工资应计税金额
                def42 = def36.subtract(a);//当前累计工资计税金额
                if (def36.compareTo(a) == 1) {
                    for (int i = 0; i < taxTableList.size(); i++) {
                        TaxDetailsInfo entityMapTax = taxTableList.get(i);
                        //如果当前累计工资计税金额大于下限，小于等于上限
                        if (def42.compareTo(entityMapTax.getAmountDown()) == 1 && def42.compareTo(entityMapTax.getAmountUp()) != 1) {
                            //当前累计工资个税=超出部分计税金额*当前税率-算数扣除数
                            def43 = def42.multiply(entityMapTax.getRate()).divide(new BigDecimal(100)).subtract(entityMapTax.getSpeedDeductAmount());
                            def43 = def43.setScale(2, BigDecimal.ROUND_HALF_UP);
                            break;
                        }
                    }
                } else {
                    def43 = BigDecimalUtils.ZERO;
                }
            } else {
                def36 = def34.add(def35);
                def42 = def36.subtract(a);//当前累计工资计税金额
                if (def36.compareTo(a) == 1) {
                    for (int i = 0; i < taxTableList.size(); i++) {
                        TaxDetailsInfo entityMapTax = taxTableList.get(i);
                        //如果当前累计工资计税金额大于下限，小于等于上限
                        if (def42.compareTo(entityMapTax.getAmountDown()) == 1 && def42.compareTo(entityMapTax.getAmountUp()) != 1) {
                            //当前累计工资个税=超出部分计税金额*当前税率-算数扣除数
                            def43 = def42.multiply(entityMapTax.getRate()).divide(new BigDecimal(100)).subtract(entityMapTax.getSpeedDeductAmount());
                            def43 = def43.setScale(2, BigDecimal.ROUND_HALF_UP);
                            break;
                        }
                    }
                } else {
                    def43 = BigDecimalUtils.ZERO;
                }
            }
        }

        //6.本次工资个税=当前累计工资个税-上次累计工资个税
        if (def43.compareTo(def44) == 1) {
            def45 = def43.subtract(def44);
            if (def16.compareTo(BigDecimalUtils.ZERO) != 1) {//本次应发是否大于0
                def45 = BigDecimalUtils.ZERO;
            }
        } else {
            def43 = def44;
            def45 = BigDecimalUtils.ZERO;
        }

//是否有年终奖金额
        if (def51.compareTo(BigDecimalUtils.ZERO) != 0 || def56.compareTo(BigDecimalUtils.ZERO) != 0) {
            //本次年终奖应计税金额=本次年终奖金额+年终金其他合并计税项(计税不发)-年终金免税收入-年终金其他扣除
            def57 = def51.add(def56).subtract(def52).subtract(def54);
            //本月已发应计税年终奖
            BigDecimal def57_1 = BigDecimalUtils.ZERO;
            for (int i = 0; i < salaryDataList.size(); i++) {
                SalaryData entityMap = salaryDataList.get(i);
                def57_1 = def57_1.add(entityMap.getDef57());
            }
            //上次年终奖累计税金额=一月到上月（省去）+本月已发应计税
            def58 = def58.add(def57_1);
            //累计年终奖计税金额=本次年终奖应计税金额+上次年终奖累计税金额
            def59 = def57.add(def58);
            //月平均年终奖
            BigDecimal def59_1 = def59.divide(BigDecimalUtils.StringToBigDecimal("12", 1), 2, BigDecimal.ROUND_HALF_UP);
            //累计年终奖个税
            String bonus_tax_id = salaryDocVo.getYearEndRateTable();
            List<TaxDetailsInfo> bonusTaxList = taxDetailsInfoDao.getTaxTableList(bonus_tax_id);
            for (int i = 0; i < bonusTaxList.size(); i++) {
                TaxDetailsInfo entityMapTax = bonusTaxList.get(i);
                //如果累计年终奖计税金额大于下限，小于等于上限
                if (def59_1.compareTo(entityMapTax.getAmountDown()) == 1 && def59_1.compareTo(entityMapTax.getAmountUp()) != 1) {
                    //当前累计年终奖个税=月平均累计年终奖计税金额*当前税率-算数扣除数
                    def60 = def59.multiply(entityMapTax.getRate()).divide(new BigDecimal(100)).subtract(entityMapTax.getSpeedDeductAmount());
                    def60 = def60.setScale(2, BigDecimal.ROUND_HALF_UP);
                    break;
                }
            }
            //当月已发年终奖个税
            BigDecimal def62_1 = BigDecimalUtils.ZERO;
            for (int i = 0; i < salaryDataList.size(); i++) {
                SalaryData entityMap = salaryDataList.get(i);
                def62_1 = def62_1.add(entityMap.getDef62());
            }
            def61 = def61.add(def62_1);
            //本次年终奖个税=当前累计年终奖个税-上次累计年终奖个税
            if (def60.compareTo(def61) == 1) {
                def62 = def60.subtract(def61);
            } else {
                def62 = BigDecimalUtils.ZERO;
            }
        }
//是否有劳务报酬
        if (def66.compareTo(BigDecimalUtils.ZERO) != 0 || def71.compareTo(BigDecimalUtils.ZERO) != 0) {
            // 劳务报酬 判断本批次是否需要合并计税
            BigDecimal def72_1 = BigDecimalUtils.ZERO;
            if (is_unite_tax.equals("1")) {
                for (int i = 0; i < salaryDataList.size(); i++) {
                    SalaryData entityMap = salaryDataList.get(i);
                    def72_1 = def72_1.add(entityMap.getDef72());
                }
            }
            //当月已发劳务报酬应计税
            def73 = def73.add(def72_1);
            //本次劳务费应计税金额=本次劳务费金额+劳务费其他合并计税项(计税不发)-劳务费免税收入-劳务费其他扣除
            def72 = def66.add(def71).subtract(def67).subtract(def69);
            //累计劳务费计税金额=本次劳务费应计税金额+上次劳务费累计税金额
            def74 = def72.add(def73);
            //累计劳务费个税
            String labor_tax_id = salaryDocVo.getRewardRateTable();
            List<TaxDetailsInfo> laborTaxList = taxDetailsInfoDao.getTaxTableList(labor_tax_id);
            for (int i = 0; i < laborTaxList.size(); i++) {
                TaxDetailsInfo entityMapTax = laborTaxList.get(i);
                //如果累计劳务费计税金额大于下限，小于等于上限
                if (def74.compareTo(entityMapTax.getAmountDown()) == 1 && def74.compareTo(entityMapTax.getAmountUp()) != 1) {
                    //累计劳务费个税=累计劳务费计税金额*税率-算数扣除数
                    //累计劳务费是否大于4000
                    if (def74.compareTo(BigDecimalUtils.StringToBigDecimal("4000", 0)) == 1) {
                        def75 = def74.multiply(BigDecimalUtils.StringToBigDecimal("8", 0)).divide(BigDecimalUtils.StringToBigDecimal("10", 1)).multiply(entityMapTax.getRate()).divide(new BigDecimal(100)).subtract(entityMapTax.getSpeedDeductAmount());
                        def75 = def75.setScale(2, BigDecimal.ROUND_HALF_UP);
                        break;
                    } else {
                        BigDecimal subtract = def74.subtract(BigDecimalUtils.StringToBigDecimal("800", 0));
                        def75 = (subtract.compareTo(BigDecimalUtils.ZERO) != -1 ? subtract : BigDecimalUtils.ZERO).multiply(BigDecimalUtils.StringToBigDecimal("2", 0)).divide(BigDecimalUtils.StringToBigDecimal("10", 1));
                        def75 = def75.setScale(2, BigDecimal.ROUND_HALF_UP);
                        break;
                    }
//                def75 = def74.multiply(BigDecimalUtils.StringToBigDecimal(entityMapTax.getString("tax_rate"), 2)).divide(new BigDecimal(108)).subtract(BigDecimalUtils.StringToBigDecimal(entityMapTax.getString("quick_deduct"),2));
                }
            }
            //本月已发劳务报酬个税
            BigDecimal def77_1 = BigDecimalUtils.ZERO;
            for (int i = 0; i < salaryDataList.size(); i++) {
                SalaryData entityMap = salaryDataList.get(i);
                def77_1 = def77_1.add(entityMap.getDef77());
            }
            //
            def76 = def76.add(def77_1);
            //本次劳务报酬个税=当前累计劳务费个税-上次累计劳务费个税
            if (def75.compareTo(def76) == 1) {
                def77 = def75.subtract(def76);
            } else {
                def77 = BigDecimalUtils.ZERO;
            }
        }

//        if (def81.compareTo(BigDecimalUtils.ZERO) != 0 || def86.compareTo(BigDecimalUtils.ZERO) != 0) {
//            //其他劳务报酬本月已发应计税
//            BigDecimal def88_1 = BigDecimalUtils.ZERO;
//            //其他劳务报酬本月已发
//            BigDecimal def81_1 = BigDecimalUtils.ZERO;
//            for (int i = 0; i < salaryDataList.size(); i++) {
//                SalaryData entityMap = salaryDataList.get(i);
//                def88_1 = def88_1.add(entityMap.getDef88());
//                def81_1 = def81_1.add(entityMap.getDef81());
//            }
//
//            //本次其他劳务费应计税金额=[实习生劳务费金额+实习生劳务费其他合并计税项(计税不发)]*0.8-实习生劳务费免税收入-实习生劳务费其他扣除-实习生本月免税所得额
//            if (def81.compareTo(BigDecimalUtils.ZERO) == 1) {
//                if (def81_1.compareTo(BigDecimalUtils.ZERO) != 1) {//计算免税额值
//                    //实习生本月免税所得额
//                    def87 = BigDecimalUtils.StringToBigDecimal("5000", 0);
//
//                    if (cu_tax_month.equals(newestYearMonth)) {//第一次发工资
//                        //任职入职日期
//                        String en = salaryDocVo.getEntryTime();
//                        String cu_tax_month_new = cu_tax_month.substring(0, 4) + "-" + cu_tax_month.substring(4, 6) + "-01";
//                        int monthSpace = 0;
//                        try {
//                            monthSpace = DateUtils.getMonthSpace(en, cu_tax_month_new);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        def87 = def87.multiply(BigDecimalUtils.object2Bigdecimal(monthSpace, 2));
//                    }
//                }
//                def88 = def81.add(def86).multiply(BigDecimalUtils.object2Bigdecimal(8, 2)).divide(BigDecimalUtils.StringToBigDecimal("10", 1)).subtract(def82).subtract(def84).subtract(def87);
////            if(def88.compareTo(BigDecimalUtils.ZERO) == -1){
////                def88 = BigDecimalUtils.ZERO;
////            }
//            }
//
//            def89 = def89.add(def88_1);
//            //累计其他劳务费计税金额=本次其他劳务费应计税金额+上次累计其他劳务费计税金额
//            def90 = def88.add(def89);
//            //累计其他劳务费个税
//            String other_labor_tax_id = salaryDocVo.getOther_labor_tax_id();
//            List<EntityMap> taxTableList1 = salarybatchDao.getTaxTableList(other_labor_tax_id);
//            for (int i = 0; i < taxTableList1.size(); i++) {
//                EntityMap entityMapTax = taxTableList1.get(i);
//                //如果累计其他劳务费计税金额大于下限，小于等于上限
//                if (def90.compareTo(BigDecimalUtils.StringToBigDecimal(entityMapTax.getString("min_amount"), 2)) == 1 && def90.compareTo(BigDecimalUtils.StringToBigDecimal(entityMapTax.getString("max_amount"), 2)) != 1) {
//                    //当前累计其他劳务费个税=累计其他劳务费计税金额*当前税率-算数扣除算
//                    def91 = def90.multiply(BigDecimalUtils.StringToBigDecimal(entityMapTax.getString("tax_rate"), 2)).divide(new BigDecimal(100)).subtract(BigDecimalUtils.StringToBigDecimal(entityMapTax.getString("quick_deduct"), 2));
//                    def91 = def91.setScale(2, BigDecimal.ROUND_HALF_UP);
//                    break;
//                }
//            }
//
//            //本月已发其他劳务报酬个税
//            BigDecimal def93_1 = BigDecimalUtils.ZERO;
//            for (int i = 0; i < salaryDataList.size(); i++) {
//                SalaryData entityMap = salaryDataList.get(i);
//                def93_1 = def93_1.add(entityMap.getDef93());
//            }
//            def92 = def92.add(def93_1);
//            //本次其他劳务费个税=当前累计其他劳务费个税-上次累计其他劳务费个税
//            if (def91.compareTo(def92) == 1) {
//                def93 = def91.subtract(def92);
//            } else {
//                def93 = BigDecimalUtils.ZERO;
//            }
//        }
        //离职补偿金应计税金额 = 离职补偿金金额+离职补偿金其他合并计税项(计税不发)-离职补偿金免税收入-离职补偿金其他扣除
        def103 = def97.add(def102).subtract(def98).subtract(def100);
        if (def103.compareTo(BigDecimalUtils.ZERO) == 1) {
            TaxTypeInfo severanceMoney1 = taxTypeInfoDao.getTaxType5();
            //离职补偿金免税金额
            def104 = severanceMoney1.getNationalTariff();
            if (def103.compareTo(def104) == 1) {
                def105 = def103.subtract(def104);
            }
        }

        //离职补偿金个税
        String departrue_tax_id = salaryDocVo.getLeaveCompensationRateTable();
        List<TaxDetailsInfo> taxTableList2 = taxDetailsInfoDao.getTaxTableList(departrue_tax_id);
        for (int i = 0; i < taxTableList2.size(); i++) {
            TaxDetailsInfo entityMapTax = taxTableList2.get(i);
            //如果离职补偿金计税金额大于下限，小于等于上限
            if (def105.compareTo(entityMapTax.getAmountDown()) == 1 && def105.compareTo(entityMapTax.getAmountUp()) != 1) {
                //离职补偿金个税=离职补偿金金额*当前税率-算数扣除数
                def106 = def105.multiply(entityMapTax.getRate()).divide(new BigDecimal(100)).subtract(entityMapTax.getSpeedDeductAmount());
                def106 = def106.setScale(2, BigDecimal.ROUND_HALF_UP);
                break;
            }
        }
        //判断是否残疾人
//        String is_disability = entityMap_dom.getString("is_disability");
//        if ("1".equals(is_disability)) {//是残疾人
//            //剩余残疾人可免税额
//            if (allDisabilityMoney.compareTo(currentDisabilityMoney) == 1) {
//                currentDisabilityMoney = BigDecimalUtils.ZERO;
//            } else {
//                currentDisabilityMoney = currentDisabilityMoney.subtract(allDisabilityMoney);
//            }
//            if ("1".equals(entityMap_dom.getString("disability_nature"))) {//按比例
////                BigDecimal disability_proportion = BigDecimalUtils.StringToBigDecimal("1", 2).subtract(BigDecimalUtils.StringToBigDecimal(entityMap.getString("disability_proportion"), 4));
//                //例：0.1*100  ：比例
//                BigDecimal disability_proportion = BigDecimalUtils.StringToBigDecimal(entityMap_dom.getString("disability_proportion"), 2);
//                //残疾人减免税额
//                def46 = def45.multiply(disability_proportion);
//                //本次工资应纳个税=本次工资个税 - 残疾人减免税额
//                if (currentDisabilityMoney.compareTo(def46) != -1) {//剩余残疾人减免金额>减免额
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def46);
//                    if (def45.compareTo(def46) == 1) {
//                        def47 = def45.subtract(def46);
//                    } else {
//                        def47 = BigDecimalUtils.ZERO;
//                    }
//                } else {
//                    if (def45.compareTo(currentDisabilityMoney) == 1) {
//                        def47 = def45.subtract(currentDisabilityMoney);
//                    } else {
//                        def47 = BigDecimalUtils.ZERO;
//                    }
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//
//                def63 = def62.multiply(disability_proportion);
//                if (currentDisabilityMoney.compareTo(def63) != -1) {//剩余残疾人减免金额>减免额
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def63);
//                    //本次年终奖应纳个税=本次年终奖个税 - 残疾人减免税额
//                    if (def62.compareTo(def63) == 1) {
//                        def64 = def62.subtract(def63);
//                    } else {
//                        def64 = BigDecimalUtils.ZERO;
//                    }
//                } else {
//                    if (def62.compareTo(currentDisabilityMoney) == 1) {
//                        def64 = def62.subtract(currentDisabilityMoney);
//                    } else {
//                        def64 = BigDecimalUtils.ZERO;
//                    }
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//                //劳务报酬
//                def78 = def77.multiply(disability_proportion);
//                if (currentDisabilityMoney.compareTo(def78) != -1) {//剩余残疾人减免金额>减免额
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def78);
//                    if (def77.compareTo(def78) == 1) {
//                        def79 = def77.subtract(def78);
//                    } else {
//                        def79 = BigDecimalUtils.ZERO;
//                    }
//                } else {
//                    if (def77.compareTo(currentDisabilityMoney) == 1) {
//                        def79 = def77.subtract(currentDisabilityMoney);
//                    } else {
//                        def79 = BigDecimalUtils.ZERO;
//                    }
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//                //其他劳务报酬
//                def94 = def93.multiply(disability_proportion);
//                if (currentDisabilityMoney.compareTo(def94) != -1) {//剩余残疾人减免金额>减免额
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def94);
//                    if (def93.compareTo(def94) == 1) {
//                        def95 = def93.subtract(def94);
//                    } else {
//                        def95 = BigDecimalUtils.ZERO;
//                    }
//                } else {
//                    if (def93.compareTo(currentDisabilityMoney) == 1) {
//                        def95 = def93.subtract(currentDisabilityMoney);
//                    } else {
//                        def95 = BigDecimalUtils.ZERO;
//                    }
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//                //离职补偿
//                def107 = def106.multiply(disability_proportion);
//                if (currentDisabilityMoney.compareTo(def107) != -1) {//剩余残疾人减免金额>减免额
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def107);
//                    if (def106.compareTo(def107) == 1) {
//                        def108 = def106.subtract(def107);
//                    } else {
//                        def108 = BigDecimalUtils.ZERO;
//                    }
//                } else {
//                    if (def106.compareTo(currentDisabilityMoney) == 1) {
//                        def108 = def106.subtract(currentDisabilityMoney);
//                    } else {
//                        def108 = BigDecimalUtils.ZERO;
//                    }
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//            } else {//按6000抵扣
//
//                //薪资
//                if (currentDisabilityMoney.compareTo(def45) != -1) {//剩余残疾人可免税额大于等于本次工资个税
//                    def46 = def45;//残疾人减免税额（薪资）=本次工资个税
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def45);//剩余残疾人可免税额
//                    def47 = BigDecimalUtils.ZERO;//本次工资应纳个税减为0
//                } else {//剩余残疾人可免税额小于本次工资个税
//                    def47 = def45.subtract(currentDisabilityMoney);//本次工资应纳个税=本次工资个税-残疾人减免税额（薪资）
//                    def46 = currentDisabilityMoney;//剩余残疾人可免税额=残疾人减免税额（薪资）
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;//残疾人减免税额（薪资）减为0
//                }
//                //同上 年终奖
//                if (currentDisabilityMoney.compareTo(def62) != -1) {
//                    def63 = def62;
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def62);
//                    def64 = BigDecimalUtils.ZERO;
//                } else {
//                    def64 = def62.subtract(currentDisabilityMoney);
//                    def63 = currentDisabilityMoney;
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//                //劳务报酬
//                if (currentDisabilityMoney.compareTo(def77) != -1) {
//                    def78 = def77;
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def77);
//                    def79 = BigDecimalUtils.ZERO;
//                } else {
//                    def79 = def77.subtract(currentDisabilityMoney);
//                    def78 = currentDisabilityMoney;
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//                //其他劳务报酬
//                if (currentDisabilityMoney.compareTo(def93) != -1) {
//                    def94 = def93;
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def93);
//                    def95 = BigDecimalUtils.ZERO;
//                } else {
//                    def95 = def93.subtract(currentDisabilityMoney);
//                    def94 = currentDisabilityMoney;
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//                //离职补偿
//                if (currentDisabilityMoney.compareTo(def106) != -1) {
//                    def107 = def106;
//                    currentDisabilityMoney = currentDisabilityMoney.subtract(def106);
//                    def108 = BigDecimalUtils.ZERO;
//                } else {
//                    def108 = def106.subtract(currentDisabilityMoney);
//                    def107 = currentDisabilityMoney;
//                    currentDisabilityMoney = BigDecimalUtils.ZERO;
//                }
//            }
//        } else {//不是残疾人
//            //本次工资应纳个税=本次工资个税 薪资
//            def47 = def45;
//            //同上 年终奖
//            def64 = def62;
//            def79 = def77;
//            def95 = def93;
//            def108 = def106;
//        }
        //本次工资应纳个税=本次工资个税 薪资
            def47 = def45;
            //同上 年终奖
            def64 = def62;
            def79 = def77;
            def95 = def93;
            def108 = def106;
        //7.本次工资项目应发金额=本次工资应发合计+不计税补贴项（税后加项）-社保个人扣除-公积金个人扣除-企业年金个人扣除-不计税补贴项（税后减项）
        BigDecimal def_110 = def16.add(def49).subtract(def21).subtract(def22).subtract(def25).subtract(def48);
        //本次工资项目实发金额=本次工资项目应发金额-本次工资应纳个税
        def50 = def_110.subtract(def47);
        if (def50.compareTo(BigDecimalUtils.ZERO) == -1) {
            def50 = BigDecimalUtils.ZERO;
        }
        //本次年终奖实发金额=本次年终奖金额-本次年终奖应纳个税
        def65 = def51.subtract(def64);
        if (def65.compareTo(BigDecimalUtils.ZERO) == -1) {
            def65 = BigDecimalUtils.ZERO;
        }
        //本次劳务报酬实发金额=本次劳务费金额-本次劳务费应纳个税
        def80 = def66.subtract(def79);
        if (def80.compareTo(BigDecimalUtils.ZERO) == -1) {
            def80 = BigDecimalUtils.ZERO;
        }
        //本次其它劳务费实发金额=实习生劳务费金额-本次其他劳务费应纳个税
        def96 = def81.subtract(def95);
        if (def96.compareTo(BigDecimalUtils.ZERO) == -1) {
            def96 = BigDecimalUtils.ZERO;
        }
        //离职补偿金实发金额=离职补偿金金额-离职补偿金应纳个税
        def109 = def97.subtract(def108);
        if (def109.compareTo(BigDecimalUtils.ZERO) == -1) {
            def109 = BigDecimalUtils.ZERO;
        }
        //本次应发合计=本次工资应发合计+本次年终奖金额+本次劳务费金额+实习生劳务费金额+离职补偿金金额
        def110 = def16.add(def51).add(def66).add(def81).add(def97);
        //工资薪金校验
        if (def50.compareTo(BigDecimalUtils.ZERO) != 1) {
            def50 = BigDecimalUtils.ZERO;
            def47 = BigDecimalUtils.ZERO;
        }
        //本次实发合计=本次工资项目实发金额+本次年终奖实发金额+本次劳务报酬实发金额+本次其它劳务费实发金额+离职补偿金实发金额
        def111 = def50.add(def65).add(def80).add(def96).add(def109);
        //本次扣税合计=本次工资应纳个税+本次年终奖应纳个税+本次劳务费应纳个税+本次其他劳务费应纳个税+离职补偿金应纳个税
        def112 = def47.add(def64).add(def79).add(def95).add(def108);

        //设置薪资项
        dataVo.setDef16(def16);
        dataVo.setDef17(def17);
        dataVo.setDef18(def18);
        dataVo.setDef19(def19);
        dataVo.setDef20(def20);
        dataVo.setDef21(def21);
        dataVo.setDef22(def22);
        dataVo.setDef23(def23);
        dataVo.setDef24(def24);
        dataVo.setDef25(def25);
        dataVo.setDef26(def26);
        dataVo.setDef27(def27);
        dataVo.setDef28(def28);
        dataVo.setDef29(def29);
        dataVo.setDef30(def30);
        dataVo.setDef31(def31);
        dataVo.setDef32(def32);

        dataVo.setDef33(def33);
        dataVo.setDef34(def34);
        dataVo.setDef35(def35);
        dataVo.setDef36(def36);
        dataVo.setDef42(def42);
        dataVo.setDef43(def43);
        dataVo.setDef44(def44);
        dataVo.setDef45(def45);
        dataVo.setDef46(def46);
        dataVo.setDef47(def47);
        dataVo.setDef48(def48);
        dataVo.setDef49(def49);
        dataVo.setDef50(def50);
        dataVo.setDef51(def51);
        dataVo.setDef52(def52);
        dataVo.setDef53(def53);
        dataVo.setDef54(def54);
        dataVo.setDef55(def55);
        dataVo.setDef56(def56);
        dataVo.setDef57(def57);
        dataVo.setDef58(def58);
        dataVo.setDef59(def59);
        dataVo.setDef60(def60);
        dataVo.setDef61(def61);
        dataVo.setDef62(def62);
        dataVo.setDef63(def63);
        dataVo.setDef64(def64);
        dataVo.setDef65(def65);
        dataVo.setDef66(def66);
        dataVo.setDef67(def67);
        dataVo.setDef68(def68);
        dataVo.setDef69(def69);
        dataVo.setDef70(def70);
        dataVo.setDef71(def71);
        dataVo.setDef72(def72);
        dataVo.setDef73(def73);
        dataVo.setDef74(def74);
        dataVo.setDef75(def75);
        dataVo.setDef76(def76);
        dataVo.setDef77(def77);
        dataVo.setDef78(def78);
        dataVo.setDef79(def79);
        dataVo.setDef80(def80);
        dataVo.setDef81(def81);
        dataVo.setDef82(def82);
        dataVo.setDef83(def83);
        dataVo.setDef84(def84);
        dataVo.setDef85(def85);
        dataVo.setDef86(def86);
        dataVo.setDef87(def87);
        dataVo.setDef88(def88);
        dataVo.setDef89(def89);
        dataVo.setDef90(def90);
        dataVo.setDef91(def91);
        dataVo.setDef92(def92);
        dataVo.setDef93(def93);
        dataVo.setDef94(def94);
        dataVo.setDef95(def95);
        dataVo.setDef96(def96);
        dataVo.setDef97(def97);
        dataVo.setDef98(def98);
        dataVo.setDef99(def99);
        dataVo.setDef100(def100);
        dataVo.setDef101(def101);
        dataVo.setDef102(def102);
        dataVo.setDef103(def103);
        dataVo.setDef104(def104);
        dataVo.setDef105(def105);
        dataVo.setDef106(def106);
        dataVo.setDef107(def107);
        dataVo.setDef108(def108);
        dataVo.setDef109(def109);
        dataVo.setDef110(def110);
        dataVo.setDef111(def111);
        dataVo.setDef112(def112);
//        dataVo.setDef113(def113);
//        dataVo.setDef114(def114);


        return dataVo;
    }

    public Page<SalaryData> listSalaryPage(List<Parameter> parameters, int offset, int limit, String orderby) {
//        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
//        int total = dao.count(pageRequest);
        List<SalaryData> list = dao.listSalaryPage((String)parameters.get(0).getValue(),
                (String)parameters.get(1).getValue(),
                (String)parameters.get(2).getValue(),
                offset,
                limit,
                orderby);
        return new Page<>(list.size(), list);
    }

    public void exportSalaryDataByBatch(SalaryBatch salaryBatch, HttpServletResponse response)throws Exception {
        SearchParams searchParams = new SearchParams();
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter();
        parameter.setColumnName("sa_batch_id");
        parameter.setQueryType("=");
        parameter.setValue(salaryBatch.getId());
        parameters.add(parameter);
        searchParams.setParams(parameters);
        //薪资数据
        List<SalaryData> salaryDataList = listAll(searchParams.getParams(), searchParams.getOrderby());
        //薪资模板
        List<SalaryTemp> salaryTemps = salaryTempService.slistAll();
        //添加execl下标与薪资项map集合
        Map<Integer, Integer> tempMap = new HashMap<>();
        for (SalaryTemp salaryTemp : salaryTemps) {
            tempMap.put(Integer.parseInt(salaryTemp.getSaItemDesc()),Integer.parseInt(salaryTemp.getDataField()));
        }
        InputStream resourceAsStream = SalaryDataService.class.getClassLoader().getResourceAsStream("template/exportSalaryDataTemp.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(resourceAsStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 0; i < salaryDataList.size(); i++) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            SalaryData entityMap = salaryDataList.get(i);
            row.createCell(0).setCellValue(entityMap.getSaDoc().getEmployeeNo());
            row.createCell(1).setCellValue(entityMap.getSaDoc().getEmployeeName());
            row.createCell(2).setCellValue(entityMap.getSaDoc().getIdCardNo());
            row.createCell(3).setCellValue(entityMap.getPayYearMonth());
            String d = "def";
            for (int j = 1; j < 124; j++) {
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(d + j, SalaryData.class);
                Method readMethod = propertyDescriptor.getReadMethod();
                //拿到对应薪资数据
                Object value = readMethod.invoke(entityMap);
                //拿到对应薪资数据execl下标
                Integer exKey = tempMap.get(j);
                row.createCell(exKey - 1).setCellValue(Double.parseDouble(String.valueOf(value)));
            }
        }

        // 设置响应类型与编码
        String inf_name = CodeID.id()+"数据.xlsx";
        OutputStream os = getOutputStream(inf_name, response);
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();
    }

    /**
     * 导出文件时为Writer生成OutputStream.
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }
}