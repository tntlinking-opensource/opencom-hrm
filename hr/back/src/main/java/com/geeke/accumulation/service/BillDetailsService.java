package com.geeke.accumulation.service;

import com.geeke.accumulation.dao.BillDetailsDao;
import com.geeke.accumulation.entity.BillDetails;
import com.geeke.accumulation.entity.SocialSecurityFund;
import com.geeke.accumulation.entity.vo.BillDetailsVo;
import com.geeke.accumulation.entity.vo.BillListVo;
import com.geeke.accumulation.enums.ProductType;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.common.service.ServiceException;
import com.geeke.org.entity.Department;
import com.geeke.org.service.DepartmentService;
import com.geeke.salaryArchives.entity.SalaryArchives;
import com.geeke.salaryArchives.service.SalaryArchivesService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.geeke.utils.SessionUtils;
import com.geeke.utils.TemplateExcelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * 生成账单Service
 * @author
 * @version
 */

@Service("billDetailsService")
@Transactional(readOnly = true)
public class BillDetailsService extends CrudService<BillDetailsDao, BillDetails> {

    @Autowired
    private SalaryArchivesService salaryArchivesService;

    @Autowired
    private SocialSecurityFundService socialSecurityFundService;

    @Autowired
    private DepartmentService departmentService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public List<BillDetails> generateBillDetails(String billYearMonth){
        List<String> employeeNos = super.dao.getEmployeeNo();
        List<BillDetails> billDetailsList = new ArrayList<>();
        // 获取账单设置日期
        BillDetails billConfig = super.listAll(Arrays.asList(new Parameter("batch_no", "=","bill_default_config")),"").get(0);
        Map<String,List<Map<String, Object>>> productPayMaps = this.getProductPayByEmployeeNos(employeeNos);
        // 获取最大的批次号
        String batchNo = super.dao.getMaxBatchNo();
        if(CollectionUtils.isNotEmpty(employeeNos)){
            for (String employeeNo : employeeNos) {
                BillDetails billDetails = new BillDetails();
                SalaryArchives salaryArchives = salaryArchivesService.listAll(Arrays.asList(new Parameter("employee_no", "=",employeeNo)),"").get(0);
                boolean leaveTimeFlag = false;
                if(salaryArchives.getLeaveTime() != null && !Objects.equals("", salaryArchives.getLeaveTime()) && salaryArchives.getLeaveTime().length() > 2) {
                    String configTime = billConfig.getBillYearMonth().length() > 1 ? billConfig.getBillYearMonth() : ("0" + billConfig.getBillYearMonth());
                    String leaveTime = salaryArchives.getLeaveTime().substring(salaryArchives.getLeaveTime().length() - 2);
                    leaveTimeFlag = leaveTime.compareTo(configTime) > 0;
                }
                // 判断员工离职日期是否是在当前月账单日期设置范围内，不是则不生成账单
                if(salaryArchives.getLeaveTime() == null || Objects.equals("", salaryArchives.getLeaveTime()) || leaveTimeFlag) {

                    List<Map<String, Object>> productPayMapList = productPayMaps.get(employeeNo);
                    List<Map<String, Object>> tempProductPayMapList = new ArrayList<>();

                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("0")).collect(Collectors.toList());
                    String unitInjury = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitInjury(unitInjury);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("1")).collect(Collectors.toList());
                    String unitUnemploy = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitUnemploy(unitUnemploy);
                    String personUnemploy = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("personBase").toString() : "0";
                    billDetails.setPersonUnemploy(personUnemploy);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("2")).collect(Collectors.toList());
                    String unitMedical = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitMedical(unitMedical);
                    String personMedical = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("personBase").toString() : "0";
                    billDetails.setPersonMedical(personMedical);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("3")).collect(Collectors.toList());
                    String unitBear = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitBear(unitBear);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("4")).collect(Collectors.toList());
                    String unitAnnuity = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitAnnuity(unitAnnuity);
                    String personAnnuity = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("personBase").toString() : "0";
                    billDetails.setPersonAnnuity(personAnnuity);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("5")).collect(Collectors.toList());
                    String unitDisabledHund = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitDisabledHund(unitDisabledHund);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("6")).collect(Collectors.toList());
                    String unitIllMedical = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitIllMedical(unitIllMedical);
                    String personIllMedical = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("personBase").toString() : "0";
                    billDetails.setPersonIllMedical(personIllMedical);
                    tempProductPayMapList = productPayMapList.stream().filter(i -> i.get("productType").equals("7")).collect(Collectors.toList());
                    String unitAccumulaHund = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("companyBase").toString() : "0";
                    billDetails.setUnitAccumulaHund(unitAccumulaHund);
                    billDetails.setUnitSocialSecurityAmount(new BigDecimal(unitAnnuity).add(new BigDecimal(unitMedical)).add(new BigDecimal(unitBear)).add(new BigDecimal(unitUnemploy)).add(new BigDecimal(unitInjury)).add(new BigDecimal(unitIllMedical)).toString());
                    String personAccumulaHund = CollectionUtils.isNotEmpty(tempProductPayMapList) ? tempProductPayMapList.get(0).get("personBase").toString() : "0";
                    billDetails.setPersonAccumulaHund(personAccumulaHund);

                    billDetails.setPersonSocialSecurityAmount(new BigDecimal(personAnnuity).add(new BigDecimal(personMedical)).add(new BigDecimal(personUnemploy)).add(new BigDecimal(personIllMedical)).toString());
                    billDetails.setPersonAmount(new BigDecimal(billDetails.getPersonSocialSecurityAmount()).add(new BigDecimal(personAccumulaHund)).toString());
                    billDetails.setAmount(new BigDecimal(unitAccumulaHund).add(new BigDecimal(billDetails.getUnitSocialSecurityAmount())).add(new BigDecimal(personAccumulaHund)).add(new BigDecimal(billDetails.getPersonSocialSecurityAmount())).add(new BigDecimal(unitDisabledHund)).toString());
                    //设置批次号
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                    AtomicInteger counter = new AtomicInteger(0);
                    String date = dateFormat.format(new Date());
                    int count = counter.incrementAndGet() % 100000;
                    if (batchNo != null) {
                        count = Integer.parseInt(batchNo.substring(8)) + count % 100000;
                    }
                    billDetails.setBatchNo(date + String.format("%05d", count));

                    billDetails.setCityName((CollectionUtils.isNotEmpty(productPayMapList)) ? productPayMapList.get(0).get("cityName").toString() : "");
                    billDetails.setEmployeeNo(employeeNo);
                    billDetails.setEmployeeName(salaryArchives.getEmployeeName());
                    billDetails.setDeptId(salaryArchives.getDeptId());
                    billDetails.setIdCardNo(salaryArchives.getIdCardNo());
                    billDetails.setSocialSecurityPayMonth(billYearMonth);
                    //社保缴纳基数 数值取比例维护中的【社保缴纳基数】
                    billDetails.setSocialSecurityPayBase(CollectionUtils.isNotEmpty(productPayMapList) ? productPayMapList.get(0).get("companyPayBase").toString() : "0");
                    billDetails.setAccumulaFundPayMonth(billYearMonth);
                    //公积金缴纳基数 数值取比例维护中的【公积金缴纳基数】
                    billDetails.setAccumulaFundPayBase(CollectionUtils.isNotEmpty(productPayMapList) ? productPayMapList.get(0).get("personPayBase").toString() : "0");
                    billDetails.setCompanyId(SessionUtils.getUser().getCompanyId());
                    billDetails.setBillYearMonth(billYearMonth);

                    billDetailsList.add(billDetails);
                }
            }
            if (CollectionUtils.isNotEmpty(billDetailsList)) {
                this.bulkInsert(billDetailsList);
            }
        }
        return billDetailsList;
    }

    public Map<String,List<Map<String, Object>>> getProductPayByEmployeeNos(List<String> employeeNos){
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        employeeNos.forEach(item -> {
            List<Map<String, Object>> maps = super.dao.getProductPayByEmployeeNo(item);
            result.put(item, maps);
        });
        return result;
    }

    public Page<BillListVo> getBillListVos(List<Parameter> parameters, int offset, int limit, String orderby) {
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
        List<BillListVo> list = super.dao.getBillListVos(pageRequest);
        int total = CollectionUtils.isNotEmpty(list) ? list.size() : 0;
        return new Page<>(total, list);
    }

    public Page<BillDetailsVo> getBillDetailsList(BillDetails billDetails) {
        List<BillDetailsVo> billDetailsVoList = this.getBillDetailsVo(billDetails);
        int total = CollectionUtils.isNotEmpty(billDetailsVoList) ? billDetailsVoList.size() : 0;
        return new Page<>(total, billDetailsVoList);
    }

    /**
     * 组装账单详情 *
     * @return
     */
    public BillDetailsVo getBillDetailsVo(BillDetailsVo billDetailsVo, SocialSecurityFund fund) {
        String base = fund.getCompanyPayBase();
        String unitScale = fund.getCompanyPayScale() + "%";
        String unitScaleString = fund.getCompanyPayScale();
        String personScale = fund.getPersonPayScale() + "%";
        String personScaleString = fund.getPersonPayScale();
        BigDecimal unitAmount = new BigDecimal(base).multiply(new BigDecimal(unitScaleString)).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        BigDecimal personAmount = new BigDecimal(base).multiply(new BigDecimal(personScaleString)).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        if (ProductType.INDUSTRIAL_INJURY_INSURANCE.getType().equals(fund.getProductType())) {
            billDetailsVo.setInjuryBase(base);
            billDetailsVo.setUnitInjuryScale(unitScale);
            billDetailsVo.setUnitInjuryAmount(unitAmount.toString());
            billDetailsVo.setPersonInjuryScale(personScale);
            billDetailsVo.setPersonInjuryAmount(personAmount.toString());
            billDetailsVo.setInjuryAmount((unitAmount.add(personAmount)).toString());
        } else if (ProductType.UNEMPLOYMENT_INSURANCE.getType().equals(fund.getProductType())) {
            billDetailsVo.setUnemployBase(base);
            billDetailsVo.setUnitUnemployScale(unitScale);
            billDetailsVo.setUnitUnemployAmount(unitAmount.toString());
            billDetailsVo.setPersonUnemployScale(personScale);
            billDetailsVo.setPersonUnemployAmount(personAmount.toString());
            billDetailsVo.setUnemployAmount((unitAmount.add(personAmount)).toString());
        } else if (ProductType.MEDICAL_INSURANCE.getType().equals(fund.getProductType())) {
            billDetailsVo.setMedicalBase(base);
            billDetailsVo.setUnitMedicalScale(unitScale);
            billDetailsVo.setUnitMedicalAmount(unitAmount.toString());
            billDetailsVo.setPersonMedicalScale(personScale);
            billDetailsVo.setPersonMedicalAmount(personAmount.toString());
            billDetailsVo.setMedicalAmount((unitAmount.add(personAmount)).toString());
        } else if (ProductType.MATERNITY_INSURANCE.getType().equals(fund.getProductType())) {
            billDetailsVo.setBearBase(base);
            billDetailsVo.setUnitBearScale(unitScale);
            billDetailsVo.setUnitBearAmount(unitAmount.toString());
            billDetailsVo.setPersonBearScale(personScale);
            billDetailsVo.setPersonBearAmount(personAmount.toString());
            billDetailsVo.setBearAmount((unitAmount.add(personAmount)).toString());
        } else if (ProductType.CRITICAL_ILLNESS_INSURANCE.getType().equals(fund.getProductType())) {
            billDetailsVo.setIllMedicalBase(base);
            billDetailsVo.setUnitIllMedicalScale(unitScale);
            billDetailsVo.setUnitIllMedicalAmount(unitAmount.toString());
            billDetailsVo.setPersonIllMedicalScale(personScale);
            billDetailsVo.setPersonIllMedicalAmount(personAmount.toString());
            billDetailsVo.setIllMedicalAmount((unitAmount.add(personAmount)).toString());
        } else if (ProductType.ENDOWMENT_INSURANCE.getType().equals(fund.getProductType())) {
            billDetailsVo.setAnnuityBase(base);
            billDetailsVo.setUnitAnnuityScale(unitScale);
            billDetailsVo.setUnitAnnuityAmount(unitAmount.toString());
            billDetailsVo.setPersonAnnuityScale(personScale);
            billDetailsVo.setPersonAnnuityAmount(personAmount.toString());
            billDetailsVo.setAnnuityAmount((unitAmount.add(personAmount)).toString());
        } else if (ProductType.HOUSING_ACCUMULATION_FUND.getType().equals(fund.getProductType())) {
            billDetailsVo.setAccumulaHundBase(base);
            billDetailsVo.setUnitAccumulaHundScale(unitScale);
            billDetailsVo.setUnitAccumulaHundAmount(unitAmount.toString());
            billDetailsVo.setPersonAccumulaHundScale(personScale);
            billDetailsVo.setPersonAccumulaHundAmount(personAmount.toString());
            billDetailsVo.setAccumulaHundAmount((unitAmount.add(personAmount)).toString());
        }
        return billDetailsVo;
    }

    public void exportBillDetails(BillDetails billDetails, HttpServletResponse response) throws Exception {
        List<BillDetailsVo> list = this.getBillDetailsVo(billDetails);
        try {
            if(CollectionUtils.isNotEmpty(list)) {
                for(int i = 0; i < list.size(); i++) {
                    list.get(i).setSerialNumber(i+1);
                }
                Map<String, Object> param = new HashMap<>();
                param = transToMap(list.get(0));
//                param.put("list", list);
                TemplateExcelUtils.downLoadExcel("社保公积金明细账单", "社保公积金明细账单.xlsx", param, list, response);
            }
        } catch (Exception e) {
            throw new ServiceException("导出失败");
        }
    }

    public Map<String, Object> transToMap(BillDetailsVo billDetails) {
        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId());
        parameters.add(parameter);
        Parameter parameter1 = new Parameter("city_name", "=", billDetails.getCityName());
        parameters.add(parameter1);
        List<SocialSecurityFund> socialSecurityFunds = socialSecurityFundService.listAll(parameters, "");
        String unitAnnuityScale = "0";
        String unitMedicalScale = "0";
        String unitBearScale = "0";
        String unitUnemployScale = "0";
        String unitInjuryScale = "0";
        String unitAccumulaHundScale = "0";
        String unitDisabledHundScale = "0";
        String unitIllMedicalScale = "0";
        String personAnnuityScale = "0";
        String personMedicalScale = "0";
        String personUnemployScale = "0";
        String personAccumulaHundScale = "0";
        String personIllMedicalScale = "0";
        if(CollectionUtils.isNotEmpty(socialSecurityFunds)){
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("4")).collect(Collectors.toList()))) {
                unitAnnuityScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("4")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("2")).collect(Collectors.toList()))) {
                unitMedicalScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("2")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("3")).collect(Collectors.toList()))) {
                unitBearScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("3")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("1")).collect(Collectors.toList()))) {
                unitUnemployScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("1")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("0")).collect(Collectors.toList()))) {
                unitInjuryScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("0")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("7")).collect(Collectors.toList()))) {
                unitAccumulaHundScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("7")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("5")).collect(Collectors.toList()))) {
                unitDisabledHundScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("5")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("6")).collect(Collectors.toList()))) {
                unitIllMedicalScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("6")).collect(Collectors.toList()).get(0).getCompanyPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("6")).collect(Collectors.toList()))) {
                personIllMedicalScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("6")).collect(Collectors.toList()).get(0).getPersonPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("4")).collect(Collectors.toList()))) {
                personAnnuityScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("4")).collect(Collectors.toList()).get(0).getPersonPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("2")).collect(Collectors.toList()))) {
                personMedicalScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("2")).collect(Collectors.toList()).get(0).getPersonPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("1")).collect(Collectors.toList()))) {
                personUnemployScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("1")).collect(Collectors.toList()).get(0).getPersonPayScale();
            }
            if(CollectionUtils.isNotEmpty(socialSecurityFunds.stream().filter(item -> item.getProductType().equals("7")).collect(Collectors.toList()))) {
                personAccumulaHundScale = socialSecurityFunds.stream().filter(item -> item.getProductType().equals("7")).collect(Collectors.toList()).get(0).getPersonPayScale();
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("unitName", SessionUtils.getUser().getCompany().getName());
        map.put("payMonth", billDetails.getBillYearMonth());
        map.put("unitAnnuityScale", unitAnnuityScale + "%");
        map.put("unitMedicalScale",unitMedicalScale + "%");
        map.put("unitBearScale", unitBearScale + "%");
        map.put("unitUnemployScale",unitUnemployScale + "%");
        map.put("unitInjuryScale", unitInjuryScale + "%");
        map.put("unitAccumulaHundScale",unitAccumulaHundScale + "%");
        map.put("unitDisabledHundScale", unitDisabledHundScale + "%");
        map.put("unitIllMedicalScale",unitIllMedicalScale + "%");
        map.put("personAnnuityScale", personAnnuityScale + "%");
        map.put("personMedicalScale",personMedicalScale + "%");
        map.put("personUnemployScale", personUnemployScale + "%");
        map.put("personAccumulaHundScale",personAccumulaHundScale + "%");
        map.put("personIllMedicalScale",personIllMedicalScale + "%");
        return map;
    }

    public List<BillDetailsVo> getBillDetailsVo(BillDetails billDetails) {
        List<BillDetailsVo> list = new ArrayList<>();
        List<BillDetailsVo> billDetailsVoList = new ArrayList<>();
        if("0".equals(billDetails.getBillType())){
            return super.dao.getBillSummaryList(billDetails);
        } else if("1".equals(billDetails.getBillType())) {
            list = super.dao.getBillDetailsList(billDetails);
            Map<String, List<BillDetailsVo>>  collect =  list.stream().collect(Collectors.groupingBy(BillDetailsVo :: getEmployeeNo));
            Set<String> keys = collect.keySet();
            keys.stream().filter(item -> CollectionUtils.isNotEmpty(collect.get(item)))
                    .forEach(item -> {
                        BillDetailsVo details = collect.get(item).get(0);
                        List<Parameter> parameters = new ArrayList<>();
                        BillDetailsVo billDetailsVo = new BillDetailsVo();
                        BeanUtils.copyProperties(details, billDetailsVo);
                        // 组装部门
                        Map<String, String> companyDept = salaryArchivesService.getCompanyDept(billDetailsVo.getCompanyId());
                        billDetailsVo.setDeptName(companyDept.get(billDetailsVo.getDeptId()));
                        parameters.add(new Parameter("employee_no", "=", details.getEmployeeNo()));
                        List<SocialSecurityFund> socialSecurityFunds = socialSecurityFundService.listAll(parameters, null);
                        // 组装账单详情
                        for(SocialSecurityFund entity : socialSecurityFunds) {
                            billDetailsVo = getBillDetailsVo(billDetailsVo, entity);
                        }
                        billDetailsVoList.add(billDetailsVo);
                    });
            return billDetailsVoList;
        }
        return Collections.emptyList();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int deleteByBatchNo(BillDetails billDetails) {
        return super.dao.deleteByBatchNo(billDetails.getBatchNo());
    }

}
