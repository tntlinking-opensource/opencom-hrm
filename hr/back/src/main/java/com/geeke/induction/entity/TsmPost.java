package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.induction.enums.YesOrNoEnum;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmPost extends WfDataEntity<TsmPost> {

    private static final long serialVersionUID = 1457254743943432649L;

    private String inductionId; // 员工ID

    private DictItem jobPosition; // 招聘岗位

    private String workCity; // 工作城市

    private String companyName; // 所属公司

    private String personnelArea; // 人事区域

    private DictItem department; // 部门

    private String employeeNumber; // 工号

    private DictItem employeeType; // 0：正式员工，1：实习生，2：劳务派遣

    private DictItem employeeNature; // 0：城镇员工，1：农村员工

    private DictItem workingHoursCategory; // 0：全职，1：兼职

    private DictItem employeeStatus; // 0: 在职, 1: 试用期, 2: 离职

    private String employeeRank; // 员工职级

    private DictItem technicalPosition; // 技术职位

    private String entryTime; // 入职时间

    private String regularTime; // 转正时间

    private String reportToObject; // 汇报对象

    private DictItem probationPeriod; // 试用期（月）

    private String officeAddress; // 办公地址

    private Integer expectedSalary; // 期望薪资（税前）

    private String interviewTime; // 面试时间

    private String interviewResult; // interview_result

    private String abandonReporting = YesOrNoEnum.NO.getCode(); // 放弃报道

    // 构造方法
    public TsmPost() {
        super();
    }

    public TsmPost(String id) {
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

    @NotNull(message = "招聘岗位不能为空")
    public DictItem getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(DictItem jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Length(min = 1, max = 50, message = "工作城市长度必须介于 1 和 50 之间")
    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    @Length(min = 1, max = 100, message = "所属公司长度必须介于 1 和 100 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Length(min = 0, max = 50, message = "人事区域长度必须介于 0 和 50 之间")
    public String getPersonnelArea() {
        return personnelArea;
    }

    public void setPersonnelArea(String personnelArea) {
        this.personnelArea = personnelArea;
    }

    @NotNull(message = "部门不能为空")
    public DictItem getDepartment() {
        return department;
    }

    public void setDepartment(DictItem department) {
        this.department = department;
    }

    @Length(min = 1, max = 20, message = "工号长度必须介于 1 和 20 之间")
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @NotNull(message = "0：正式员工，1：实习生，2：劳务派遣不能为空")
    public DictItem getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(DictItem employeeType) {
        this.employeeType = employeeType;
    }

    @NotNull(message = "0：城镇员工，1：农村员工不能为空")
    public DictItem getEmployeeNature() {
        return employeeNature;
    }

    public void setEmployeeNature(DictItem employeeNature) {
        this.employeeNature = employeeNature;
    }

    @NotNull(message = "0：全职，1：兼职不能为空")
    public DictItem getWorkingHoursCategory() {
        return workingHoursCategory;
    }

    public void setWorkingHoursCategory(DictItem workingHoursCategory) {
        this.workingHoursCategory = workingHoursCategory;
    }

    @NotNull(message = "0: 在职, 1: 试用期, 2: 离职不能为空")
    public DictItem getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(DictItem employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Length(min = 1, max = 50, message = "员工职级长度必须介于 1 和 50 之间")
    public String getEmployeeRank() {
        return employeeRank;
    }

    public void setEmployeeRank(String employeeRank) {
        this.employeeRank = employeeRank;
    }

    public DictItem getTechnicalPosition() {
        return technicalPosition;
    }

    public void setTechnicalPosition(DictItem technicalPosition) {
        this.technicalPosition = technicalPosition;
    }

    @Length(min = 1, max = 50, message = "入职时间长度必须介于 1 和 50 之间")
    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    @Length(min = 0, max = 50, message = "转正时间长度必须介于 0 和 50 之间")
    public String getRegularTime() {
        return regularTime;
    }

    public void setRegularTime(String regularTime) {
        this.regularTime = regularTime;
    }

    @Length(min = 0, max = 50, message = "汇报对象长度必须介于 0 和 50 之间")
    public String getReportToObject() {
        return reportToObject;
    }

    public void setReportToObject(String reportToObject) {
        this.reportToObject = reportToObject;
    }

    public DictItem getProbationPeriod() {
        return probationPeriod;
    }

    public void setProbationPeriod(DictItem probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    @Length(min = 1, max = 100, message = "办公地址长度必须介于 1 和 100 之间")
    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Integer getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(Integer expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    @Length(min = 0, max = 50, message = "面试时间长度必须介于 0 和 50 之间")
    public String getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
    }

    @Length(min = 0, max = 8, message = "interview_result长度必须介于 0 和 8 之间")
    public String getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(String interviewResult) {
        this.interviewResult = interviewResult;
    }

    @Length(min = 0, max = 21, message = "abandon_reporting长度必须介于 0 和 21 之间")
    public String getAbandonReporting() {
        return abandonReporting;
    }

    public void setAbandonReporting(String abandonReporting) {
        this.abandonReporting = abandonReporting;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_post";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432649";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "岗位信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
