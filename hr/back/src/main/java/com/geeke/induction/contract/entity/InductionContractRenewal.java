package com.geeke.induction.contract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.org.entity.Company;
import com.geeke.org.entity.Department;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 合约续签Entity
 * @author
 * @version
 */
public class InductionContractRenewal extends WfDataEntity<InductionContractRenewal> {

    private static final long serialVersionUID = 1538459003414151556L;

    private String code; // 合同编号

    private String theme; // 合同主题

    private String name; // 姓名

    private DictItem gender; // 性别

    private String phone; // 手机号

    private String idNumber; // 身份证号码

    private String email; // 邮箱

    private String address; // 居住地地址

    private String entryTime; // 入职时间

    private DictItem probationMonth; // 试用期

    private String employeeLevel; // 员工职级

    private String employeeNumber; // 工号

    private DictItem techPosition; // 员工职位

    private String bjob; // 从事工作

    private String contractStartTime; // 合同开始时间

    private String contractEndTime; // 合同结束时间

    private String probationEndTime; // 试用期结束时间

    private Company company; // 公司

    private Department department; // 部门

    private String companyEmail; // 公司邮箱

    private String contractSignUnit; // 合同签约单位

    private String laborLegalmana; // 合同签约方法人

    private String reportAddress; // 甲方住所地

    private String fullWages; // 全额工资（元）

    private String probationWages; // 试用期工资（元）

    private String basicWages; // 基本工资（元）

    private String probationBasicWages; // 试用期基本工资（元）

    private String secrecyReward; // 保密奖（元）

    private String systemReward; // 制度奖（元）

    private String basicPerformance; // 基本绩效（元）

    private String floatingPerformance; // 浮动绩效（元）

    private String probationBasicPerformance; // 试用期基本绩效（元）

    private String probationFloatingPerformance; // 试用期浮动绩效（元）

    private String contractDate; // 合同生成时间

    private String contractStartDate; // 合同发起时间

    private DictItem contractStatus; // 合约状态

    private DictItem signStatus; // 签署状态

    private String expirationDate; // 到期日期

    private String endDate; // 截止日期

    private String arriveName; // 送达人

    private String arrivePhone; // 送达人联系方式

    private String signDate; // 签字日期

    // 构造方法
    public InductionContractRenewal() {
        super();
    }

    public InductionContractRenewal(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "合同编号不能为空")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 0, max = 255, message = "合同主题长度必须介于 0 和 255 之间")
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Length(min = 1, max = 128, message = "姓名长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "性别不能为空")
    public DictItem getGender() {
        return gender;
    }

    public void setGender(DictItem gender) {
        this.gender = gender;
    }

    @Length(min = 1, max = 64, message = "手机号长度必须介于 1 和 64 之间")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 1, max = 255, message = "身份证号码长度必须介于 1 和 255 之间")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Length(min = 1, max = 128, message = "邮箱长度必须介于 1 和 128 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 1, max = 255, message = "居住地地址长度必须介于 1 和 255 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull(message = "入职时间不能为空")
    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    @NotNull(message = "试用期不能为空")
    public DictItem getProbationMonth() {
        return probationMonth;
    }

    public void setProbationMonth(DictItem probationMonth) {
        this.probationMonth = probationMonth;
    }

    public String getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

    @Length(min = 0, max = 64, message = "工号长度必须介于 0 和 64 之间")
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Length(min = 0, max = 21, message = "员工职位长度必须介于 0 和 21 之间")
    public DictItem getTechPosition() {
        return techPosition;
    }

    public void setTechPosition(DictItem techPosition) {
        this.techPosition = techPosition;
    }

    @Length(min = 0, max = 128, message = "从事工作长度必须介于 0 和 128 之间")
    public String getBjob() {
        return bjob;
    }

    public void setBjob(String bjob) {
        this.bjob = bjob;
    }

    @NotNull(message = "合同开始时间不能为空")
    public String getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(String contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    @NotNull(message = "合同结束时间不能为空")
    public String getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(String contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    @NotNull(message = "试用期结束时间不能为空")
    public String getProbationEndTime() {
        return probationEndTime;
    }

    public void setProbationEndTime(String probationEndTime) {
        this.probationEndTime = probationEndTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Length(min = 0, max = 100, message = "公司邮箱长度必须介于 0 和 100 之间")
    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Length(min = 0, max = 255, message = "合同签约单位长度必须介于 0 和 255 之间")
    public String getContractSignUnit() {
        return contractSignUnit;
    }

    public void setContractSignUnit(String contractSignUnit) {
        this.contractSignUnit = contractSignUnit;
    }

    @Length(min = 0, max = 128, message = "合同签约方法人长度必须介于 0 和 128 之间")
    public String getLaborLegalmana() {
        return laborLegalmana;
    }

    public void setLaborLegalmana(String laborLegalmana) {
        this.laborLegalmana = laborLegalmana;
    }

    @Length(min = 0, max = 255, message = "甲方住所地长度必须介于 0 和 255 之间")
    public String getReportAddress() {
        return reportAddress;
    }

    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    @Length(min = 0, max = 50, message = "全额工资（元）长度必须介于 0 和 50 之间")
    public String getFullWages() {
        return fullWages;
    }

    public void setFullWages(String fullWages) {
        this.fullWages = fullWages;
    }

    @Length(min = 0, max = 50, message = "试用期工资（元）长度必须介于 0 和 50 之间")
    public String getProbationWages() {
        return probationWages;
    }

    public void setProbationWages(String probationWages) {
        this.probationWages = probationWages;
    }

    @Length(min = 0, max = 50, message = "基本工资（元）长度必须介于 0 和 50 之间")
    public String getBasicWages() {
        return basicWages;
    }

    public void setBasicWages(String basicWages) {
        this.basicWages = basicWages;
    }

    @Length(min = 0, max = 50, message = "试用期基本工资（元）长度必须介于 0 和 50 之间")
    public String getProbationBasicWages() {
        return probationBasicWages;
    }

    public void setProbationBasicWages(String probationBasicWages) {
        this.probationBasicWages = probationBasicWages;
    }

    @Length(min = 0, max = 50, message = "保密奖（元）长度必须介于 0 和 50 之间")
    public String getSecrecyReward() {
        return secrecyReward;
    }

    public void setSecrecyReward(String secrecyReward) {
        this.secrecyReward = secrecyReward;
    }

    @Length(min = 0, max = 50, message = "制度奖（元）长度必须介于 0 和 50 之间")
    public String getSystemReward() {
        return systemReward;
    }

    public void setSystemReward(String systemReward) {
        this.systemReward = systemReward;
    }

    @Length(min = 0, max = 50, message = "基本绩效（元）长度必须介于 0 和 50 之间")
    public String getBasicPerformance() {
        return basicPerformance;
    }

    public void setBasicPerformance(String basicPerformance) {
        this.basicPerformance = basicPerformance;
    }

    @Length(min = 0, max = 50, message = "浮动绩效（元）长度必须介于 0 和 50 之间")
    public String getFloatingPerformance() {
        return floatingPerformance;
    }

    public void setFloatingPerformance(String floatingPerformance) {
        this.floatingPerformance = floatingPerformance;
    }

    @Length(min = 0, max = 50, message = "试用期基本绩效（元）长度必须介于 0 和 50 之间")
    public String getProbationBasicPerformance() {
        return probationBasicPerformance;
    }

    public void setProbationBasicPerformance(String probationBasicPerformance) {
        this.probationBasicPerformance = probationBasicPerformance;
    }

    @Length(min = 0, max = 50, message = "试用期浮动绩效（元）长度必须介于 0 和 50 之间")
    public String getProbationFloatingPerformance() {
        return probationFloatingPerformance;
    }

    public void setProbationFloatingPerformance(String probationFloatingPerformance) {
        this.probationFloatingPerformance = probationFloatingPerformance;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public DictItem getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(DictItem contractStatus) {
        this.contractStatus = contractStatus;
    }

    public DictItem getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(DictItem signStatus) {
        this.signStatus = signStatus;
    }

    @Length(min = 0, max = 24, message = "到期日期长度必须介于 0 和 24 之间")
    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Length(min = 0, max = 24, message = "截止日期长度必须介于 0 和 24 之间")
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Length(min = 0, max = 128, message = "送达人长度必须介于 0 和 128 之间")
    public String getArriveName() {
        return arriveName;
    }

    public void setArriveName(String arriveName) {
        this.arriveName = arriveName;
    }

    @Length(min = 0, max = 64, message = "送达人联系方式长度必须介于 0 和 64 之间")
    public String getArrivePhone() { return arrivePhone; }

    public void setArrivePhone(String arrivePhone) { this.arrivePhone = arrivePhone; }

    @Length(min = 0, max = 64, message = "签字日期长度必须介于 0 和 24 之间")
    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_induction_contract_renewal";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1538459003414151556";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "合约续签";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
