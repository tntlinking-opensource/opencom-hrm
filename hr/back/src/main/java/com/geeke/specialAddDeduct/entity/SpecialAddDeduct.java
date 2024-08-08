package com.geeke.specialAddDeduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 专项附加扣除Entity
 * @author
 * @version
 */

@JsonInclude(JsonInclude.Include.ALWAYS)
public class SpecialAddDeduct extends DataEntity<SpecialAddDeduct> {

    private static final long serialVersionUID = 1683007003934705360L;

    private String companyId; // 公司id

    private String employeeNo; // 员工工号

    private String employeeName; // 员工姓名

    private String idCardType; // 证件类型,选项：身份证、护照

    private String idCardNo; // 证件号码

    private String employeeStatus; // 员工状态

    private String periodStart; // 所得期间起

    private String periodEnd; // 所得期间止

    private String currentIncome; // 本期收入

    private String currentIncomeNoTax; // 本期免税收入

    private String baseAnnuity; // 基本养老保险费

    private String baseIllMedical; // 基本医疗保险费

    private String unemploy; // 失业保险费

    private String accumulaFund; // 住房公积金

    private String accSubEducation; // 累计子女教育

    private String accKeepEducation; // 累计继续教育

    private String accHouseLoanTax; // 累计住房贷款利息

    private String accHouseRent; // 累积住房租金

    private String accCareOld; // 累积赡养老人

    private String accBabyPassport; // 累计3岁以下婴幼儿照护

    private String accPersonAnnuity; // 累计个人养老金

    private String unitAnnuity; // 企业年金

    private String professionAnnuity; // 职业年金

    private String busHealthInsurance; // 商业健康保险

    private String taxDelayOldInsurance; // 税延养老保险

    private String otherInsurance; // 其他

    private String deductDonateAmount; // 准予扣除的捐赠额

    private String preTaxDeductTotal; // 税前扣除项目合计

    private String deductTaxAmount; // 减免税额

    private String deductExpenseStandard; // 减除费用标准

    private String taxPaidAmount; // 已缴税额

    private String taxYearMonth; // 报税年月

    private String remark; // 备注

    // 构造方法
    public SpecialAddDeduct() {
        super();
    }

    public SpecialAddDeduct(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 21, message = "公司id长度必须介于 1 和 21 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 30, message = "员工工号长度必须介于 0 和 30 之间")
    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    @Length(min = 0, max = 30, message = "员工姓名长度必须介于 0 和 30 之间")
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Length(min = 0, max = 30, message = "证件类型,选项：身份证、护照长度必须介于 0 和 30 之间")
    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    @Length(min = 1, max = 30, message = "证件号码长度必须介于 1 和 30 之间")
    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @Length(min = 0, max = 30, message = "员工状态长度必须介于 0 和 30 之间")
    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Length(min = 0, max = 24, message = "所得期间起长度必须介于 0 和 24 之间")
    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    @Length(min = 0, max = 24, message = "所得期间止长度必须介于 0 和 24 之间")
    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    @Length(min = 0, max = 18, message = "本期收入长度必须介于 0 和 18 之间")
    public String getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(String currentIncome) {
        this.currentIncome = currentIncome;
    }

    @Length(min = 0, max = 18, message = "本期免税收入长度必须介于 0 和 18 之间")
    public String getCurrentIncomeNoTax() {
        return currentIncomeNoTax;
    }

    public void setCurrentIncomeNoTax(String currentIncomeNoTax) {
        this.currentIncomeNoTax = currentIncomeNoTax;
    }

    @Length(min = 0, max = 18, message = "基本医疗保险费长度必须介于 0 和 18 之间")
    public String getBaseAnnuity() {
        return baseAnnuity;
    }

    public void setBaseAnnuity(String baseAnnuity) {
        this.baseAnnuity = baseAnnuity;
    }

    @Length(min = 0, max = 18, message = "基本医疗保险费长度必须介于 0 和 18 之间")
    public String getBaseIllMedical() {
        return baseIllMedical;
    }

    public void setBaseIllMedical(String baseIllMedical) {
        this.baseIllMedical = baseIllMedical;
    }

    @Length(min = 0, max = 18, message = "基本医疗保险费长度必须介于 0 和 18 之间")
    public String getUnemploy() {
        return unemploy;
    }

    public void setUnemploy(String unemploy) {
        this.unemploy = unemploy;
    }

    @Length(min = 0, max = 18, message = "住房公积金长度必须介于 0 和 18 之间")
    public String getAccumulaFund() {
        return accumulaFund;
    }

    public void setAccumulaFund(String accumulaFund) {
        this.accumulaFund = accumulaFund;
    }

    @Length(min = 0, max = 18, message = "累计子女教育长度必须介于 0 和 18 之间")
    public String getAccSubEducation() {
        return accSubEducation;
    }

    public void setAccSubEducation(String accSubEducation) {
        this.accSubEducation = accSubEducation;
    }

    @Length(min = 0, max = 18, message = "累计继续教育长度必须介于 0 和 18 之间")
    public String getAccKeepEducation() {
        return accKeepEducation;
    }

    public void setAccKeepEducation(String accKeepEducation) {
        this.accKeepEducation = accKeepEducation;
    }

    @Length(min = 0, max = 18, message = "累计住房贷款利息长度必须介于 0 和 18 之间")
    public String getAccHouseLoanTax() {
        return accHouseLoanTax;
    }

    public void setAccHouseLoanTax(String accHouseLoanTax) {
        this.accHouseLoanTax = accHouseLoanTax;
    }

    @Length(min = 0, max = 18, message = "累积住房租金长度必须介于 0 和 18 之间")
    public String getAccHouseRent() {
        return accHouseRent;
    }

    public void setAccHouseRent(String accHouseRent) {
        this.accHouseRent = accHouseRent;
    }

    @Length(min = 0, max = 18, message = "累积赡养老人长度必须介于 0 和 18 之间")
    public String getAccCareOld() {
        return accCareOld;
    }

    public void setAccCareOld(String accCareOld) {
        this.accCareOld = accCareOld;
    }

    @Length(min = 0, max = 18, message = "累计3岁以下婴幼儿照护长度必须介于 0 和 18 之间")
    public String getAccBabyPassport() {
        return accBabyPassport;
    }

    public void setAccBabyPassport(String accBabyPassport) {
        this.accBabyPassport = accBabyPassport;
    }

    @Length(min = 0, max = 18, message = "累计个人养老金长度必须介于 0 和 18 之间")
    public String getAccPersonAnnuity() {
        return accPersonAnnuity;
    }

    public void setAccPersonAnnuity(String accPersonAnnuity) {
        this.accPersonAnnuity = accPersonAnnuity;
    }

    @Length(min = 0, max = 18, message = "企业年金长度必须介于 0 和 18 之间")
    public String getUnitAnnuity() {
        return unitAnnuity;
    }

    public void setUnitAnnuity(String unitAnnuity) {
        this.unitAnnuity = unitAnnuity;
    }

    @Length(min = 0, max = 18, message = "职业年金长度必须介于 0 和 18 之间")
    public String getProfessionAnnuity() {
        return professionAnnuity;
    }

    public void setProfessionAnnuity(String professionAnnuity) {
        this.professionAnnuity = professionAnnuity;
    }

    @Length(min = 0, max = 18, message = "商业健康保险长度必须介于 0 和 18 之间")
    public String getBusHealthInsurance() {
        return busHealthInsurance;
    }

    public void setBusHealthInsurance(String busHealthInsurance) {
        this.busHealthInsurance = busHealthInsurance;
    }

    @Length(min = 0, max = 18, message = "税延养老保险长度必须介于 0 和 18 之间")
    public String getTaxDelayOldInsurance() {
        return taxDelayOldInsurance;
    }

    public void setTaxDelayOldInsurance(String taxDelayOldInsurance) {
        this.taxDelayOldInsurance = taxDelayOldInsurance;
    }

    @Length(min = 0, max = 18, message = "其他长度必须介于 0 和 18 之间")
    public String getOtherInsurance() {
        return otherInsurance;
    }

    public void setOtherInsurance(String otherInsurance) {
        this.otherInsurance = otherInsurance;
    }

    @Length(min = 0, max = 18, message = "准予扣除的捐赠额长度必须介于 0 和 18 之间")
    public String getDeductDonateAmount() {
        return deductDonateAmount;
    }

    public void setDeductDonateAmount(String deductDonateAmount) {
        this.deductDonateAmount = deductDonateAmount;
    }

    @Length(min = 0, max = 18, message = "税前扣除项目合计长度必须介于 0 和 18 之间")
    public String getPreTaxDeductTotal() {
        return preTaxDeductTotal;
    }

    public void setPreTaxDeductTotal(String preTaxDeductTotal) {
        this.preTaxDeductTotal = preTaxDeductTotal;
    }

    @Length(min = 0, max = 18, message = "减免税额长度必须介于 0 和 18 之间")
    public String getDeductTaxAmount() {
        return deductTaxAmount;
    }

    public void setDeductTaxAmount(String deductTaxAmount) {
        this.deductTaxAmount = deductTaxAmount;
    }

    @Length(min = 0, max = 18, message = "减除费用标准长度必须介于 0 和 18 之间")
    public String getDeductExpenseStandard() {
        return deductExpenseStandard;
    }

    public void setDeductExpenseStandard(String deductExpenseStandard) {
        this.deductExpenseStandard = deductExpenseStandard;
    }

    @Length(min = 0, max = 18, message = "已缴税额长度必须介于 0 和 18 之间")
    public String getTaxPaidAmount() {
        return taxPaidAmount;
    }

    public void setTaxPaidAmount(String taxPaidAmount) {
        this.taxPaidAmount = taxPaidAmount;
    }

    @Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaxYearMonth() {
        return taxYearMonth;
    }

    public void setTaxYearMonth(String taxYearMonth) {
        this.taxYearMonth = taxYearMonth;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "special_add_deduct";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1683007003934705360";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "专项附加扣除";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
