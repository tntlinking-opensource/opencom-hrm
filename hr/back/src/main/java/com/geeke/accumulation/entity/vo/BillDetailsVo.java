package com.geeke.accumulation.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 生成账单Entity
 * @author
 * @version
 */

public class BillDetailsVo extends DataEntity<BillDetailsVo> {

    private static final long serialVersionUID = 1641034040897358682L;

    private String companyId;

    private Integer serialNumber; // 序号

    private String cityName; // 缴费城市

    private String employeeName; // 员工姓名

    private String employeeNo; // 员工工号

    private String deptId; // 部门id

    private String deptName; // 部门名称

    private String idCardNo; // 证件号码

    private String socialSecurityPayMonth; // 社保缴纳月份

    private String socialSecurityPayBase; // 社保缴纳基数

    private String accumulaFundPayMonth; // 公积金缴纳月份

    private String accumulaFundPayBase; // 公积金缴纳基数

    private String annuityBase; // 养老保险基数
    private String annuityAmount; // 养老保险总额
    private String unitAnnuityScale; // 养老保险单位比例
    private String personAnnuityScale; // 养老保险个人比例
    private String unitAnnuityAmount; // 养老保险单位金额
    private String personAnnuityAmount; // 养老保险个人金额



    private String medicalBase; // 医疗保险基数
    private String medicalAmount; // 医疗保险总额
    private String unitMedicalScale; // 医疗保险单位比例
    private String personMedicalScale; // 医疗保险个人比例
    private String unitMedicalAmount; // 医疗医疗单位金额
    private String personMedicalAmount; // 医疗医疗个人金额

    private String bearBase; // 生育保险基数
    private String bearAmount; // 生育保险总额
    private String unitBearScale; // 生育保险单位比例
    private String personBearScale; // 生育保险个人比例
    private String unitBearAmount; // 生育保险个人金额
    private String personBearAmount; // 生育保险个人金额

    private String illMedicalBase; // 大病医疗保险基数
    private String illMedicalAmount; // 大病医疗保险总额
    private String unitIllMedicalScale; // 大病医疗保险单位比例
    private String personIllMedicalScale; // 大病医疗保险个人比例
    private String unitIllMedicalAmount; // 大病医疗保险单位金额
    private String personIllMedicalAmount; // 大病医疗保险个人金额

    private String unemployBase; // 失业保险基数
    private String unemployAmount; // 失业保险总额
    private String unitUnemployScale; // 失业保险单位比例
    private String personUnemployScale; // 失业保险个人比例
    private String unitUnemployAmount; // 失业保险单位金额
    private String personUnemployAmount; // 失业保险个人金额

    private String injuryBase; // 工伤保险基数
    private String injuryAmount; // 工伤保险总额
    private String unitInjuryScale; // 工伤保险单位比例
    private String personInjuryScale; // 工伤保险个人比例
    private String unitInjuryAmount; // 工伤保险单位金额
    private String personInjuryAmount; // 工伤保险个人金额

    private String unitDisabledHund; // 单位残疾人基金


    private String accumulaHundBase; // 公积金基数
    private String accumulaHundAmount; // 公积金总额
    private String unitAccumulaHundScale; // 公积金单位比例
    private String personAccumulaHundScale; // 公积金个人比例
    private String unitAccumulaHundAmount; // 公积金单位金额
    private String personAccumulaHundAmount; // 公积金个人金额

    private String unitSocialSecurityAmount; // 单位社保小计

    private String unitAccumulaHund; // 个人公积金

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

    private String totalAmount; // 合计 = 社保小计 + 公积金小计

    private String employeeNoOrName;

    private String taxAmount = "0.00"; // 税金

    private String managementAmount = "0.00"; // 管理费

    private int offset = 0;

    private int limit = 10;

    // 构造方法
    public BillDetailsVo() {
        super();
    }

    public BillDetailsVo(String id) {
        super(id);
    }

    // 生成get和set方法


    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getAnnuityBase() {
        return annuityBase;
    }

    public void setAnnuityBase(String annuityBase) {
        this.annuityBase = annuityBase;
    }

    public String getAnnuityAmount() {
        return annuityAmount;
    }

    public void setAnnuityAmount(String annuityAmount) {
        this.annuityAmount = annuityAmount;
    }

    public String getUnitAnnuityScale() {
        return unitAnnuityScale;
    }

    public void setUnitAnnuityScale(String unitAnnuityScale) {
        this.unitAnnuityScale = unitAnnuityScale;
    }

    public String getPersonAnnuityScale() {
        return personAnnuityScale;
    }

    public void setPersonAnnuityScale(String personAnnuityScale) {
        this.personAnnuityScale = personAnnuityScale;
    }

    public String getUnitAnnuityAmount() {
        return unitAnnuityAmount;
    }

    public void setUnitAnnuityAmount(String unitAnnuityAmount) {
        this.unitAnnuityAmount = unitAnnuityAmount;
    }

    public String getPersonAnnuityAmount() {
        return personAnnuityAmount;
    }

    public void setPersonAnnuityAmount(String personAnnuityAmount) {
        this.personAnnuityAmount = personAnnuityAmount;
    }

    public String getMedicalBase() {
        return medicalBase;
    }

    public void setMedicalBase(String medicalBase) {
        this.medicalBase = medicalBase;
    }

    public String getMedicalAmount() {
        return medicalAmount;
    }

    public void setMedicalAmount(String medicalAmount) {
        this.medicalAmount = medicalAmount;
    }

    public String getUnitMedicalScale() {
        return unitMedicalScale;
    }

    public void setUnitMedicalScale(String unitMedicalScale) {
        this.unitMedicalScale = unitMedicalScale;
    }

    public String getPersonMedicalScale() {
        return personMedicalScale;
    }

    public void setPersonMedicalScale(String personMedicalScale) {
        this.personMedicalScale = personMedicalScale;
    }

    public String getUnitMedicalAmount() {
        return unitMedicalAmount;
    }

    public void setUnitMedicalAmount(String unitMedicalAmount) {
        this.unitMedicalAmount = unitMedicalAmount;
    }

    public String getPersonMedicalAmount() {
        return personMedicalAmount;
    }

    public void setPersonMedicalAmount(String personMedicalAmount) {
        this.personMedicalAmount = personMedicalAmount;
    }

    public String getBearBase() {
        return bearBase;
    }

    public void setBearBase(String bearBase) {
        this.bearBase = bearBase;
    }

    public String getBearAmount() {
        return bearAmount;
    }

    public void setBearAmount(String bearAmount) {
        this.bearAmount = bearAmount;
    }

    public String getUnitBearScale() {
        return unitBearScale;
    }

    public void setUnitBearScale(String unitBearScale) {
        this.unitBearScale = unitBearScale;
    }

    public String getPersonBearScale() {
        return personBearScale;
    }

    public void setPersonBearScale(String personBearScale) {
        this.personBearScale = personBearScale;
    }

    public String getUnitBearAmount() {
        return unitBearAmount;
    }

    public void setUnitBearAmount(String unitBearAmount) {
        this.unitBearAmount = unitBearAmount;
    }

    public String getPersonBearAmount() {
        return personBearAmount;
    }

    public void setPersonBearAmount(String personBearAmount) {
        this.personBearAmount = personBearAmount;
    }

    public String getIllMedicalBase() {
        return illMedicalBase;
    }

    public void setIllMedicalBase(String illMedicalBase) {
        this.illMedicalBase = illMedicalBase;
    }

    public String getIllMedicalAmount() {
        return illMedicalAmount;
    }

    public void setIllMedicalAmount(String illMedicalAmount) {
        this.illMedicalAmount = illMedicalAmount;
    }

    public String getUnitIllMedicalScale() {
        return unitIllMedicalScale;
    }

    public void setUnitIllMedicalScale(String unitIllMedicalScale) {
        this.unitIllMedicalScale = unitIllMedicalScale;
    }

    public String getPersonIllMedicalScale() {
        return personIllMedicalScale;
    }

    public void setPersonIllMedicalScale(String personIllMedicalScale) {
        this.personIllMedicalScale = personIllMedicalScale;
    }

    public String getUnitIllMedicalAmount() {
        return unitIllMedicalAmount;
    }

    public void setUnitIllMedicalAmount(String unitIllMedicalAmount) {
        this.unitIllMedicalAmount = unitIllMedicalAmount;
    }

    public String getPersonIllMedicalAmount() {
        return personIllMedicalAmount;
    }

    public void setPersonIllMedicalAmount(String personIllMedicalAmount) {
        this.personIllMedicalAmount = personIllMedicalAmount;
    }

    public String getUnemployBase() {
        return unemployBase;
    }

    public void setUnemployBase(String unemployBase) {
        this.unemployBase = unemployBase;
    }

    public String getUnemployAmount() {
        return unemployAmount;
    }

    public void setUnemployAmount(String unemployAmount) {
        this.unemployAmount = unemployAmount;
    }

    public String getUnitUnemployScale() {
        return unitUnemployScale;
    }

    public void setUnitUnemployScale(String unitUnemployScale) {
        this.unitUnemployScale = unitUnemployScale;
    }

    public String getPersonUnemployScale() {
        return personUnemployScale;
    }

    public void setPersonUnemployScale(String personUnemployScale) {
        this.personUnemployScale = personUnemployScale;
    }

    public String getUnitUnemployAmount() {
        return unitUnemployAmount;
    }

    public void setUnitUnemployAmount(String unitUnemployAmount) {
        this.unitUnemployAmount = unitUnemployAmount;
    }

    public String getPersonUnemployAmount() {
        return personUnemployAmount;
    }

    public void setPersonUnemployAmount(String personUnemployAmount) {
        this.personUnemployAmount = personUnemployAmount;
    }

    public String getInjuryBase() {
        return injuryBase;
    }

    public void setInjuryBase(String injuryBase) {
        this.injuryBase = injuryBase;
    }

    public String getInjuryAmount() {
        return injuryAmount;
    }

    public void setInjuryAmount(String injuryAmount) {
        this.injuryAmount = injuryAmount;
    }

    public String getUnitInjuryScale() {
        return unitInjuryScale;
    }

    public void setUnitInjuryScale(String unitInjuryScale) {
        this.unitInjuryScale = unitInjuryScale;
    }

    public String getPersonInjuryScale() {
        return personInjuryScale;
    }

    public void setPersonInjuryScale(String personInjuryScale) {
        this.personInjuryScale = personInjuryScale;
    }

    public String getUnitInjuryAmount() {
        return unitInjuryAmount;
    }

    public void setUnitInjuryAmount(String unitInjuryAmount) {
        this.unitInjuryAmount = unitInjuryAmount;
    }

    public String getPersonInjuryAmount() {
        return personInjuryAmount;
    }

    public void setPersonInjuryAmount(String personInjuryAmount) {
        this.personInjuryAmount = personInjuryAmount;
    }

    public String getAccumulaHundBase() {
        return accumulaHundBase;
    }

    public void setAccumulaHundBase(String accumulaHundBase) {
        this.accumulaHundBase = accumulaHundBase;
    }

    public String getAccumulaHundAmount() {
        return accumulaHundAmount;
    }

    public void setAccumulaHundAmount(String accumulaHundAmount) {
        this.accumulaHundAmount = accumulaHundAmount;
    }

    public String getUnitAccumulaHundScale() {
        return unitAccumulaHundScale;
    }

    public void setUnitAccumulaHundScale(String unitAccumulaHundScale) {
        this.unitAccumulaHundScale = unitAccumulaHundScale;
    }

    public String getPersonAccumulaHundScale() {
        return personAccumulaHundScale;
    }

    public void setPersonAccumulaHundScale(String personAccumulaHundScale) {
        this.personAccumulaHundScale = personAccumulaHundScale;
    }

    public String getUnitAccumulaHundAmount() {
        return unitAccumulaHundAmount;
    }

    public void setUnitAccumulaHundAmount(String unitAccumulaHundAmount) {
        this.unitAccumulaHundAmount = unitAccumulaHundAmount;
    }

    public String getPersonAccumulaHundAmount() {
        return personAccumulaHundAmount;
    }

    public void setPersonAccumulaHundAmount(String personAccumulaHundAmount) {
        this.personAccumulaHundAmount = personAccumulaHundAmount;
    }

    @Length(min = 0, max = 18, message = "单位残疾人基金长度必须介于 0 和 18 之间")
    public String getUnitDisabledHund() {
        return unitDisabledHund;
    }

    public void setUnitDisabledHund(String unitDisabledHund) {
        this.unitDisabledHund = unitDisabledHund;
    }

    @Length(min = 0, max = 18, message = "单位社保小计长度必须介于 0 和 18 之间")
    public String getUnitSocialSecurityAmount() {
        return unitSocialSecurityAmount;
    }

    public void setUnitSocialSecurityAmount(String unitSocialSecurityAmount) {
        this.unitSocialSecurityAmount = unitSocialSecurityAmount;
    }

    public String getUnitAccumulaHund() {
        return unitAccumulaHund;
    }

    public void setUnitAccumulaHund(String unitAccumulaHund) {
        this.unitAccumulaHund = unitAccumulaHund;
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

    public String getManagementAmount() {
        return managementAmount;
    }

    public void setManagementAmount(String managementAmount) {
        this.managementAmount = managementAmount;
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
