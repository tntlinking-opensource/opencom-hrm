package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmContractInformation extends WfDataEntity<TsmContractInformation> {

    private static final long serialVersionUID = 1457254743943432661L;

    private String inductionId; // 员工ID

    private String entryTime; // 入职时间

    private DictItem probationMonth; // 试用期(月)

    private String probationEndTime; // 试用结束时间

    private DictItem techPosition; // 技术职务

    private String employeeLevel; // 员工职级

    private DictItem computerCondition; // 电脑情况

    private DictItem recruitmentMode; // 招聘方式

    private DictItem contractType; // 合同类型

    private DictItem contractProperty; // 合同属性

    private String contractSignUnit; // 合同签约单位

    private String contractStartTime; // 合同开始时间

    private Integer contractMonths = 0; // 合同期限（月）

    private String contractEndTime; // 合同结束时间

    private String fullWages = "0"; // 全额工资（元）

    private String probationWages = "0"; // 试用期工资（元）

    private String probationBasicWages = "0"; // 试用期基本工资（元）

    private String basicWages = "0"; // 基本工资（元）

    private String secrecyReward = "0"; // 保密奖（元）

    private String systemReward = "0"; // 制度奖（元）

    private String basicPerformance = "0"; // 基本绩效（元）

    private String floatingPerformance = "0"; // 浮动绩效（元）

    private String probationBasicPerformance = "0"; // 试用期基本绩效（元）

    private String probationFloatingPerformance = "0"; // 试用期浮动绩效（元）

    private String reportTime; // 报道日期

    private DictItem entryType; // 入职类型

    private String employeeBelongs; // 员工归属

    private String reportAddress; // 报道地址

    private String companyLegalPerson; // company_legal_person

    private String jobType; // job_type

    // 构造方法
    public TsmContractInformation() {
        super();
    }

    public TsmContractInformation(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 20, message = "员工ID长度必须介于 1 和 20 之间")
    public String getInductionId() {
        return inductionId;
    }

    public void setInductionId(String inductionId) {
        this.inductionId = inductionId;
    }

    @Length(min = 1, max = 50, message = "入职时间长度必须介于 1 和 50 之间")
    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    @NotNull(message = "试用期(月)不能为空")
    public DictItem getProbationMonth() {
        return probationMonth;
    }

    public void setProbationMonth(DictItem probationMonth) {
        this.probationMonth = probationMonth;
    }

    @Length(min = 1, max = 50, message = "试用结束时间长度必须介于 1 和 50 之间")
    public String getProbationEndTime() {
        return probationEndTime;
    }

    public void setProbationEndTime(String probationEndTime) {
        this.probationEndTime = probationEndTime;
    }

    public DictItem getTechPosition() {
        return techPosition;
    }

    public void setTechPosition(DictItem techPosition) {
        this.techPosition = techPosition;
    }

    @Length(min = 0, max = 255, message = "员工职级长度必须介于 0 和 255 之间")
    public String getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

    public DictItem getComputerCondition() {
        return computerCondition;
    }

    public void setComputerCondition(DictItem computerCondition) {
        this.computerCondition = computerCondition;
    }

    public DictItem getRecruitmentMode() {
        return recruitmentMode;
    }

    public void setRecruitmentMode(DictItem recruitmentMode) {
        this.recruitmentMode = recruitmentMode;
    }

    public DictItem getContractType() {
        return contractType;
    }

    public void setContractType(DictItem contractType) {
        this.contractType = contractType;
    }

    public DictItem getContractProperty() {
        return contractProperty;
    }

    public void setContractProperty(DictItem contractProperty) {
        this.contractProperty = contractProperty;
    }

    @Length(min = 0, max = 255, message = "合同签约单位长度必须介于 0 和 255 之间")
    public String getContractSignUnit() {
        return contractSignUnit;
    }

    public void setContractSignUnit(String contractSignUnit) {
        this.contractSignUnit = contractSignUnit;
    }

    @Length(min = 1, max = 50, message = "合同开始时间长度必须介于 1 和 50 之间")
    public String getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(String contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    @NotNull(message = "合同期限（月）不能为空")
    public Integer getContractMonths() {
        return contractMonths;
    }

    public void setContractMonths(Integer contractMonths) {
        this.contractMonths = contractMonths;
    }

    @Length(min = 1, max = 50, message = "合同结束时间长度必须介于 1 和 50 之间")
    public String getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(String contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    @Length(min = 1, max = 50, message = "全额工资（元）长度必须介于 1 和 50 之间")
    public String getFullWages() {
        return fullWages;
    }

    public void setFullWages(String fullWages) {
        this.fullWages = fullWages;
    }

    @Length(min = 1, max = 50, message = "试用期工资（元）长度必须介于 1 和 50 之间")
    public String getProbationWages() {
        return probationWages;
    }

    public void setProbationWages(String probationWages) {
        this.probationWages = probationWages;
    }

    @Length(min = 1, max = 50, message = "试用期基本工资（元）长度必须介于 1 和 50 之间")
    public String getProbationBasicWages() {
        return probationBasicWages;
    }

    public void setProbationBasicWages(String probationBasicWages) {
        this.probationBasicWages = probationBasicWages;
    }

    @Length(min = 1, max = 50, message = "基本工资（元）长度必须介于 1 和 50 之间")
    public String getBasicWages() {
        return basicWages;
    }

    public void setBasicWages(String basicWages) {
        this.basicWages = basicWages;
    }

    @Length(min = 1, max = 50, message = "保密奖（元）长度必须介于 1 和 50 之间")
    public String getSecrecyReward() {
        return secrecyReward;
    }

    public void setSecrecyReward(String secrecyReward) {
        this.secrecyReward = secrecyReward;
    }

    @Length(min = 1, max = 50, message = "制度奖（元）长度必须介于 1 和 50 之间")
    public String getSystemReward() {
        return systemReward;
    }

    public void setSystemReward(String systemReward) {
        this.systemReward = systemReward;
    }

    @Length(min = 1, max = 50, message = "基本绩效（元）长度必须介于 1 和 50 之间")
    public String getBasicPerformance() {
        return basicPerformance;
    }

    public void setBasicPerformance(String basicPerformance) {
        this.basicPerformance = basicPerformance;
    }

    @Length(min = 1, max = 50, message = "浮动绩效（元）长度必须介于 1 和 50 之间")
    public String getFloatingPerformance() {
        return floatingPerformance;
    }

    public void setFloatingPerformance(String floatingPerformance) {
        this.floatingPerformance = floatingPerformance;
    }

    @Length(min = 1, max = 50, message = "试用期基本绩效（元）长度必须介于 1 和 50 之间")
    public String getProbationBasicPerformance() {
        return probationBasicPerformance;
    }

    public void setProbationBasicPerformance(String probationBasicPerformance) {
        this.probationBasicPerformance = probationBasicPerformance;
    }

    @Length(min = 1, max = 50, message = "试用期浮动绩效（元）长度必须介于 1 和 50 之间")
    public String getProbationFloatingPerformance() {
        return probationFloatingPerformance;
    }

    public void setProbationFloatingPerformance(String probationFloatingPerformance) {
        this.probationFloatingPerformance = probationFloatingPerformance;
    }

    @Length(min = 1, max = 50, message = "报道日期长度必须介于 1 和 50 之间")
    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public DictItem getEntryType() {
        return entryType;
    }

    public void setEntryType(DictItem entryType) {
        this.entryType = entryType;
    }

    @Length(min = 0, max = 255, message = "员工归属长度必须介于 0 和 255 之间")
    public String getEmployeeBelongs() {
        return employeeBelongs;
    }

    public void setEmployeeBelongs(String employeeBelongs) {
        this.employeeBelongs = employeeBelongs;
    }

    @Length(min = 0, max = 255, message = "报道地址长度必须介于 0 和 255 之间")
    public String getReportAddress() {
        return reportAddress;
    }

    public void setReportAddress(String reportAddress) {
        this.reportAddress = reportAddress;
    }

    @Length(min = 0, max = 100, message = "company_legal_person长度必须介于 0 和 100 之间")
    public String getCompanyLegalPerson() {
        return companyLegalPerson;
    }

    public void setCompanyLegalPerson(String companyLegalPerson) {
        this.companyLegalPerson = companyLegalPerson;
    }

    @Length(min = 0, max = 100, message = "job_type长度必须介于 0 和 100 之间")
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_contract_information";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432661";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "合约信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
