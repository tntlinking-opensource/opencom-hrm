package com.geeke.accumulation.dto;

import com.geeke.accumulation.entity.SocialSecurityFund;

public class SocialSecurityFundDto extends SocialSecurityFund {
    private String payCityConfigId; // 缴费城市id

    private String employeeName ;		// 员工姓名

    private String entryTime ;		// 入职时间

    private String deptId ;		// 部门

    private String deptName; // 部门名称

    private String socialAvgAmount; // 社会平均工资

    private String companyHighBase; // 公司最高基数

    private String companyLowBase; // 公司最低基数

    private String personHighBase; // 个人最高基数

    private String personLowBase; // 个人最低基数

    private String discountStatusName; // 所属状态名称

    private String enableName;// 启用禁用

    public String getPayCityConfigId() {
        return payCityConfigId;
    }

    public void setPayCityConfigId(String payCityConfigId) {
        this.payCityConfigId = payCityConfigId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

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

    public String getSocialAvgAmount() {
        return socialAvgAmount;
    }

    public void setSocialAvgAmount(String socialAvgAmount) {
        this.socialAvgAmount = socialAvgAmount;
    }

    public String getCompanyHighBase() {
        return companyHighBase;
    }

    public void setCompanyHighBase(String companyHighBase) {
        this.companyHighBase = companyHighBase;
    }

    public String getCompanyLowBase() {
        return companyLowBase;
    }

    public void setCompanyLowBase(String companyLowBase) {
        this.companyLowBase = companyLowBase;
    }

    public String getPersonHighBase() {
        return personHighBase;
    }

    public void setPersonHighBase(String personHighBase) {
        this.personHighBase = personHighBase;
    }

    public String getPersonLowBase() {
        return personLowBase;
    }

    public void setPersonLowBase(String personLowBase) {
        this.personLowBase = personLowBase;
    }

    public String getDiscountStatusName() {
        return discountStatusName;
    }

    public void setDiscountStatusName(String discountStatusName) {
        this.discountStatusName = discountStatusName;
    }

    public String getEnableName() {
        return enableName;
    }

    public void setEnableName(String enableName) {
        this.enableName = enableName;
    }
}
