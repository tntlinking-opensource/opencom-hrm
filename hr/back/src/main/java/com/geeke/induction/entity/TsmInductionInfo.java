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
public class TsmInductionInfo extends WfDataEntity<TsmInductionInfo> {

    private static final long serialVersionUID = 1457254743943432648L;

    private String inductionId; // 员工ID

    private String workStartDate; // 参加工作日期

    private DictItem maritalStatus; // 0：未婚，1：已婚，2：离异，3：丧偶

    private DictItem childbirthStatus; // 0：未育，1：已育

    private DictItem politicalStatus; // 0: 群众, 1: 党员, 2: 团员, 3: 其他

    private String nationality; // 国籍或地区

    private DictItem educationBackground; // 最高学历

    private DictItem highestDegree; // 最高学位

    private String householdRegisterLocation; // 户口所在地

    private DictItem householdRegisterType; // 0：农业户口，1：城镇户口

    private String zodiac; // 生肖

    private DictItem bloodType; // 血型

    private String horoscope; // 星座

    private String employeeNumber; // 工号

    private DictItem employeeStatus; // 0: 在职, 1: 试用期, 2: 离职

    private String accumulationFundId; // 公积金账号ID

    private String salaryCardId; // 工资卡号id

    private String qqNumber; // QQ号码

    private String wechatId; // 微信号码

    private String otherContactInformation; // 其他联系方式

    private String companyPhoneNumber; // 公司座机号码

    private String companyExtensionNumber; // 分机号码

    private String companyEmail; // 公司邮箱

    private String recordStatus = YesOrNoEnum.NO.getCode(); // 归档

    private String dataSubmission = YesOrNoEnum.NO.getCode(); // 资料提交

    // 构造方法
    public TsmInductionInfo() {
        super();
    }

    public TsmInductionInfo(String id) {
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

    @Length(min = 1, max = 50, message = "参加工作日期长度必须介于 1 和 50 之间")
    public String getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
    }

    @NotNull(message = "0：未婚，1：已婚，2：离异，3：丧偶不能为空")
    public DictItem getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(DictItem maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @NotNull(message = "0：未育，1：已育不能为空")
    public DictItem getChildbirthStatus() {
        return childbirthStatus;
    }

    public void setChildbirthStatus(DictItem childbirthStatus) {
        this.childbirthStatus = childbirthStatus;
    }

    public DictItem getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(DictItem politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    @Length(min = 0, max = 50, message = "国籍或地区长度必须介于 0 和 50 之间")
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @NotNull(message = "最高学历不能为空")
    public DictItem getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(DictItem educationBackground) {
        this.educationBackground = educationBackground;
    }

    public DictItem getHighestDegree() {
        return highestDegree;
    }

    public void setHighestDegree(DictItem highestDegree) {
        this.highestDegree = highestDegree;
    }

    @Length(min = 1, max = 100, message = "户口所在地长度必须介于 1 和 100 之间")
    public String getHouseholdRegisterLocation() {
        return householdRegisterLocation;
    }

    public void setHouseholdRegisterLocation(String householdRegisterLocation) {
        this.householdRegisterLocation = householdRegisterLocation;
    }

    @NotNull(message = "0：农业户口，1：城镇户口不能为空")
    public DictItem getHouseholdRegisterType() {
        return householdRegisterType;
    }

    public void setHouseholdRegisterType(DictItem householdRegisterType) {
        this.householdRegisterType = householdRegisterType;
    }

    @Length(min = 0, max = 10, message = "生肖长度必须介于 0 和 10 之间")
    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public DictItem getBloodType() {
        return bloodType;
    }

    public void setBloodType(DictItem bloodType) {
        this.bloodType = bloodType;
    }

    @Length(min = 0, max = 20, message = "星座长度必须介于 0 和 20 之间")
    public String getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    @Length(min = 1, max = 64, message = "工号长度必须介于 1 和 64 之间")
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @NotNull(message = "0: 在职, 1: 试用期, 2: 离职不能为空")
    public DictItem getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(DictItem employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Length(min = 0, max = 50, message = "公积金账号ID长度必须介于 0 和 50 之间")
    public String getAccumulationFundId() {
        return accumulationFundId;
    }

    public void setAccumulationFundId(String accumulationFundId) {
        this.accumulationFundId = accumulationFundId;
    }

    @Length(min = 1, max = 50, message = "工资卡号id长度必须介于 1 和 50 之间")
    public String getSalaryCardId() {
        return salaryCardId;
    }

    public void setSalaryCardId(String salaryCardId) {
        this.salaryCardId = salaryCardId;
    }

    @Length(min = 0, max = 20, message = "QQ号码长度必须介于 0 和 20 之间")
    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    @Length(min = 0, max = 50, message = "微信号码长度必须介于 0 和 50 之间")
    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    @Length(min = 0, max = -1, message = "其他联系方式长度必须介于 0 和 -1 之间")
    public String getOtherContactInformation() {
        return otherContactInformation;
    }

    public void setOtherContactInformation(String otherContactInformation) {
        this.otherContactInformation = otherContactInformation;
    }

    @Length(min = 0, max = 20, message = "公司座机号码长度必须介于 0 和 20 之间")
    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    @Length(min = 0, max = 10, message = "分机号码长度必须介于 0 和 10 之间")
    public String getCompanyExtensionNumber() {
        return companyExtensionNumber;
    }

    public void setCompanyExtensionNumber(String companyExtensionNumber) {
        this.companyExtensionNumber = companyExtensionNumber;
    }

    @Length(min = 0, max = 100, message = "公司邮箱长度必须介于 0 和 100 之间")
    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    @Length(min = 0, max = 10, message = "归档长度必须介于 0 和 10 之间")
    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Length(min = 0, max = 10, message = "资料提交长度必须介于 0 和 10 之间")
    public String getDataSubmission() {
        return dataSubmission;
    }

    public void setDataSubmission(String dataSubmission) {
        this.dataSubmission = dataSubmission;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_induction_info";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432648";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "个人信息补充表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
