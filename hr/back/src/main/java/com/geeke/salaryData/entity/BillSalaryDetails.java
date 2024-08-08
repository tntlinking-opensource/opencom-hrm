package com.geeke.salaryData.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 生成账单Entity
 * @author
 * @version
 */

public class BillSalaryDetails extends DataEntity<BillSalaryDetails> {

    private static final long serialVersionUID = 1641034040897358682L;

    private String companyId;

    private String cityName; // 缴费城市

    private String employeeName; // 员工姓名

    private String employeeNo; // 员工工号

    private String deptId; // 部门id

    private String idCardNo; // 证件号码

    private String socialSecurityPayMonth; // 社保缴纳月份

    private String socialSecurityPayBase; // 社保缴纳基数

    private String accumulaFundPayMonth; // 公积金缴纳月份

    private String accumulaFundPayBase; // 公积金缴纳基数

    private String unitAnnuity; // 单位养老

    private String unitMedical; // 单位医疗

    private String unitBear; // 单位生育

    private String unitIllMedical; // 单位大病医疗保险

    private String unitUnemploy; // 单位失业

    private String unitInjury; // 单位工伤

    private String unitDisabledHund; // 单位残疾人基金

    private String unitAccumulaHund; // 单位公积金

    private String unitSocialSecurityAmount; // 单位社保小计

    private String personAnnuity; // 个人养老

    private String personMedical; // 个人医疗

    private String personIllMedical; // 个人大病医疗保险

    private String personUnemploy; // 个人失业

    private String personSocialSecurityAmount; // 个人社保小计

    private String personAccumulaHund; // 个人公积金

    private String personAmount; // 个人小计

    private String amount; // 合计

    private String batchNo; // 批次号

    private String billYearMonth; // 账单生成年月

    private String billType; //账单类型：0-汇总账单，1-明细账单

    private String totalSocialSecurityAmount; // 社保小计 = 单位社保 + 个人社保

    private String totalAccumulaHundAmount; // 公积金小计 = 单位公积金 + 个人公积金

    private String taxAmount; // 税金

    private String totalAmount; // 合计 = 社保小计 + 公积金小计

    private String employeeNoOrName;

    private int offset = 0;

    private int limit = 10;

    // 构造方法
    public BillSalaryDetails() {
        super();
    }

    public BillSalaryDetails(String id) {
        super(id);
    }

    // 生成get和set方法


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 30, message = "缴费城市长度必须介于 0 和 30 之间")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Length(min = 0, max = 30, message = "员工姓名长度必须介于 0 和 30 之间")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Length(min = 0, max = 30, message = "员工工号长度必须介于 0 和 30 之间")
    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    @Length(min = 0, max = 30, message = "部门id长度必须介于 0 和 30 之间")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Length(min = 0, max = 30, message = "证件号码长度必须介于 0 和 30 之间")
    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @Length(min = 0, max = 18, message = "社保缴纳月份长度必须介于 0 和 18 之间")
    public String getSocialSecurityPayMonth() {
        return socialSecurityPayMonth;
    }

    public void setSocialSecurityPayMonth(String socialSecurityPayMonth) {
        this.socialSecurityPayMonth = socialSecurityPayMonth;
    }

    @Length(min = 0, max = 18, message = "社保缴纳基数 长度必须介于 0 和 18 之间")
    public String getSocialSecurityPayBase() {
        return socialSecurityPayBase;
    }

    public void setSocialSecurityPayBase(String socialSecurityPayBase) {
        this.socialSecurityPayBase = socialSecurityPayBase;
    }

    @Length(min = 0, max = 18, message = "公积金缴纳月份长度必须介于 0 和 18 之间")
    public String getAccumulaFundPayMonth() {
        return accumulaFundPayMonth;
    }

    public void setAccumulaFundPayMonth(String accumulaFundPayMonth) {
        this.accumulaFundPayMonth = accumulaFundPayMonth;
    }

    @Length(min = 0, max = 18, message = "公积金缴纳基数长度必须介于 0 和 18 之间")
    public String getAccumulaFundPayBase() {
        return accumulaFundPayBase;
    }

    public void setAccumulaFundPayBase(String accumulaFundPayBase) {
        this.accumulaFundPayBase = accumulaFundPayBase;
    }

    @Length(min = 0, max = 18, message = "单位养老长度必须介于 0 和 18 之间")
    public String getUnitAnnuity() {
        return unitAnnuity;
    }

    public void setUnitAnnuity(String unitAnnuity) {
        this.unitAnnuity = unitAnnuity;
    }

    @Length(min = 0, max = 18, message = "单位医疗长度必须介于 0 和 18 之间")
    public String getUnitMedical() {
        return unitMedical;
    }

    public void setUnitMedical(String unitMedical) {
        this.unitMedical = unitMedical;
    }

    @Length(min = 0, max = 18, message = "单位生育长度必须介于 0 和 18 之间")
    public String getUnitBear() {
        return unitBear;
    }

    public void setUnitBear(String unitBear) {
        this.unitBear = unitBear;
    }

    @Length(min = 0, max = 18, message = "单位大病医疗保险长度必须介于 0 和 18 之间")
    public String getUnitIllMedical() {
        return unitIllMedical;
    }

    public void setUnitIllMedical(String unitIllMedical) {
        this.unitIllMedical = unitIllMedical;
    }

    @Length(min = 0, max = 18, message = "单位失业长度必须介于 0 和 18 之间")
    public String getUnitUnemploy() {
        return unitUnemploy;
    }

    public void setUnitUnemploy(String unitUnemploy) {
        this.unitUnemploy = unitUnemploy;
    }

    @Length(min = 0, max = 18, message = "单位工伤长度必须介于 0 和 18 之间")
    public String getUnitInjury() {
        return unitInjury;
    }

    public void setUnitInjury(String unitInjury) {
        this.unitInjury = unitInjury;
    }

    @Length(min = 0, max = 18, message = "单位残疾人基金长度必须介于 0 和 18 之间")
    public String getUnitDisabledHund() {
        return unitDisabledHund;
    }

    public void setUnitDisabledHund(String unitDisabledHund) {
        this.unitDisabledHund = unitDisabledHund;
    }

    @Length(min = 0, max = 18, message = "单位公积金长度必须介于 0 和 18 之间")
    public String getUnitAccumulaHund() {
        return unitAccumulaHund;
    }

    public void setUnitAccumulaHund(String unitAccumulaHund) {
        this.unitAccumulaHund = unitAccumulaHund;
    }

    @Length(min = 0, max = 18, message = "单位社保小计长度必须介于 0 和 18 之间")
    public String getUnitSocialSecurityAmount() {
        return unitSocialSecurityAmount;
    }

    public void setUnitSocialSecurityAmount(String unitSocialSecurityAmount) {
        this.unitSocialSecurityAmount = unitSocialSecurityAmount;
    }

    @Length(min = 0, max = 18, message = "个人养老长度必须介于 0 和 18 之间")
    public String getPersonAnnuity() {
        return personAnnuity;
    }

    public void setPersonAnnuity(String personAnnuity) {
        this.personAnnuity = personAnnuity;
    }

    @Length(min = 0, max = 18, message = "个人医疗长度必须介于 0 和 18 之间")
    public String getPersonMedical() {
        return personMedical;
    }

    public void setPersonMedical(String personMedical) {
        this.personMedical = personMedical;
    }

    @Length(min = 0, max = 18, message = "个人大病医疗保险长度必须介于 0 和 18 之间")
    public String getPersonIllMedical() {
        return personIllMedical;
    }

    public void setPersonIllMedical(String personIllMedical) {
        this.personIllMedical = personIllMedical;
    }

    @Length(min = 0, max = 18, message = "个人失业长度必须介于 0 和 18 之间")
    public String getPersonUnemploy() {
        return personUnemploy;
    }

    public void setPersonUnemploy(String personUnemploy) {
        this.personUnemploy = personUnemploy;
    }

    @Length(min = 0, max = 18, message = "个人社保小计长度必须介于 0 和 18 之间")
    public String getPersonSocialSecurityAmount() {
        return personSocialSecurityAmount;
    }

    public void setPersonSocialSecurityAmount(String personSocialSecurityAmount) {
        this.personSocialSecurityAmount = personSocialSecurityAmount;
    }

    @Length(min = 0, max = 18, message = "个人公积金长度必须介于 0 和 18 之间")
    public String getPersonAccumulaHund() {
        return personAccumulaHund;
    }

    public void setPersonAccumulaHund(String personAccumulaHund) {
        this.personAccumulaHund = personAccumulaHund;
    }

    @Length(min = 0, max = 18, message = "个人小计长度必须介于 0 和 18 之间")
    public String getPersonAmount() {
        return personAmount;
    }

    public void setPersonAmount(String personAmount) {
        this.personAmount = personAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Length(min = 0, max = 30, message = "批次号长度必须介于 0 和 30 之间")
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Length(min = 0, max = 255, message = "账单生成年月长度必须介于 0 和 255 之间")
    public String getBillYearMonth() {
        return billYearMonth;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth = billYearMonth;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTotalSocialSecurityAmount() {
        return totalSocialSecurityAmount;
    }

    public void setTotalSocialSecurityAmount(String totalSocialSecurityAmount) {
        this.totalSocialSecurityAmount = totalSocialSecurityAmount;
    }

    public String getTotalAccumulaHundAmount() {
        return totalAccumulaHundAmount;
    }

    public void setTotalAccumulaHundAmount(String totalAccumulaHundAmount) {
        this.totalAccumulaHundAmount = totalAccumulaHundAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEmployeeNoOrName() {
        return employeeNoOrName;
    }

    public void setEmployeeNoOrName(String employeeNoOrName) {
        this.employeeNoOrName = employeeNoOrName;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "bill_details";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358682";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "账单明细表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
