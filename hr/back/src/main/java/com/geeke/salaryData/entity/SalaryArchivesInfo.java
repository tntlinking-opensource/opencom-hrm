
package com.geeke.salaryData.entity;


import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.geeke.common.persistence.DataEntity;


/**
 * 薪资明细Entity
 *
 * @author
 */

public class SalaryArchivesInfo extends DataEntity<SalaryArchivesInfo> {


    private static final long serialVersionUID = 1683007003934705948L;


    private String companyId;        // 公司id


    private String employeeNo;        // 员工工号


    private String employeeName;        // 员工姓名


    private String sex;        // 性别，选项：男、女


    private String idCardType;        // 证件类型,选项：身份证、护照


    private String idCardNo;        // 证件号码


    private String birthDate;        // 出生日期


    private String phoneNumber;        // 手机号


    private String nationality;        // 国籍


    private String deptId;        // 部门


    private String employeeType;        // 员工类型，选项：正式员工、实习生


    private String employeeBelongs;        // 员工归属


    private String employeeStatus;        // 员工状态，选项：在职、离职


    private String entryTime;        // 入职时间


    private String probationMonth;        // 试用期


    private String probationEndTime;        // 试用期结束


    private String leaveTime;        // 离职时间


    private String postName;        // 岗位


    private String bankCardType;        // 银行卡类型


    private String bankName;        // 开户行


    private String bankNo;        // 银行卡号


    private String bankUserName;        // 开户姓名


    private String bankCityName;        // 账号市区名


    private String salaryRateTable;        // 工资薪金税率表


    private String yearEndRateTable;        // 年终奖税率表


    private String rewardRateTable;        // 劳务报酬税
//劳务报酬税率表


    private String leaveCompensationRateTable;        // 离职补偿税率表


    // 构造方法
    public SalaryArchivesInfo() {
        super();
    }

    public SalaryArchivesInfo(String id) {
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


    @Length(min = 1, max = 30, message = "员工工号长度必须介于 1 和 30 之间")


    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }


    @Length(min = 1, max = 30, message = "员工姓名长度必须介于 1 和 30 之间")


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }


    @Length(min = 1, max = 30, message = "性别，选项：男、女长度必须介于 1 和 30 之间")


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Length(min = 1, max = 30, message = "证件类型,选项：身份证、护照长度必须介于 1 和 30 之间")


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


    @Length(min = 0, max = 24, message = "出生日期长度必须介于 0 和 24 之间")


    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


    @Length(min = 1, max = 11, message = "手机号长度必须介于 1 和 11 之间")


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Length(min = 0, max = 10, message = "国籍长度必须介于 0 和 10 之间")


    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    @Length(min = 1, max = 30, message = "部门长度必须介于 1 和 30 之间")


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }


    @Length(min = 1, max = 30, message = "员工类型，选项：正式员工、实习生长度必须介于 1 和 30 之间")


    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }


    @Length(min = 0, max = 30, message = "员工归属长度必须介于 0 和 30 之间")


    public String getEmployeeBelongs() {
        return employeeBelongs;
    }

    public void setEmployeeBelongs(String employeeBelongs) {
        this.employeeBelongs = employeeBelongs;
    }


    @Length(min = 1, max = 30, message = "员工状态，选项：在职、离职长度必须介于 1 和 30 之间")


    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }


    @Length(min = 1, max = 24, message = "入职时间长度必须介于 1 和 24 之间")


    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }


    @Length(min = 1, max = 24, message = "试用期长度必须介于 1 和 24 之间")


    public String getProbationMonth() {
        return probationMonth;
    }

    public void setProbationMonth(String probationMonth) {
        this.probationMonth = probationMonth;
    }


    @Length(min = 1, max = 24, message = "试用期结束 长度必须介于 1 和 24 之间")


    public String getProbationEndTime() {
        return probationEndTime;
    }

    public void setProbationEndTime(String probationEndTime) {
        this.probationEndTime = probationEndTime;
    }


    @Length(min = 0, max = 24, message = "离职时间长度必须介于 0 和 24 之间")


    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }


    @Length(min = 1, max = 30, message = "岗位长度必须介于 1 和 30 之间")


    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }


    @Length(min = 0, max = 30, message = "银行卡类型长度必须介于 0 和 30 之间")


    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }


    @Length(min = 1, max = 30, message = "开户行长度必须介于 1 和 30 之间")


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    @Length(min = 1, max = 30, message = "银行卡号长度必须介于 1 和 30 之间")


    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


    @Length(min = 1, max = 30, message = "开户姓名长度必须介于 1 和 30 之间")


    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }


    @Length(min = 0, max = 30, message = "账号市区名长度必须介于 0 和 30 之间")


    public String getBankCityName() {
        return bankCityName;
    }

    public void setBankCityName(String bankCityName) {
        this.bankCityName = bankCityName;
    }


    @Length(min = 0, max = 30, message = "工资薪金税率表长度必须介于 0 和 30 之间")


    public String getSalaryRateTable() {
        return salaryRateTable;
    }

    public void setSalaryRateTable(String salaryRateTable) {
        this.salaryRateTable = salaryRateTable;
    }


    @Length(min = 0, max = 30, message = "年终奖税率表长度必须介于 0 和 30 之间")


    public String getYearEndRateTable() {
        return yearEndRateTable;
    }

    public void setYearEndRateTable(String yearEndRateTable) {
        this.yearEndRateTable = yearEndRateTable;
    }


    @Length(min = 0, max = 30, message = "劳务报酬税 劳务报酬税 率表长度必须介于 0 和 30 之间")


    public String getRewardRateTable() {
        return rewardRateTable;
    }

    public void setRewardRateTable(String rewardRateTable) {
        this.rewardRateTable = rewardRateTable;
    }


    @Length(min = 0, max = 30, message = "离职补偿税率表长度必须介于 0 和 30 之间")


    public String getLeaveCompensationRateTable() {
        return leaveCompensationRateTable;
    }

    public void setLeaveCompensationRateTable(String leaveCompensationRateTable) {
        this.leaveCompensationRateTable = leaveCompensationRateTable;
    }


    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "salary_archives_info";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1683007003934705948";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "薪酬档案信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {

        return true;

    }
}
