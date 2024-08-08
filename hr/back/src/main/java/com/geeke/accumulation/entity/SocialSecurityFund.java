package com.geeke.accumulation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 社保公积金Entity
 * @author
 * @version
 */

public class SocialSecurityFund extends DataEntity<SocialSecurityFund> {

    private static final long serialVersionUID = 1641034040897358441L;

    private String companyId;

    private String defaultPayConfigId; // 对应defaultPayConfig id

    private String cityName; // 城市名称

    private String employeeNo; // 员工工号

    private String probationSalary; // 试用期薪资

    private String ragularSalary; // 转正薪资

    private String productType; // 产品类型

    private String productName; // 产品名称

    private String discountStatus; // 所属状态,选项：正常折扣、不折扣

    private String companyPayBase; // 公司缴纳基数

    private String companyPayScale; // 公司缴费比例

    private String personPayBase; // 个人缴费基数

    private String personPayScale; // 个人缴费比例

    private String effectStartDate; // 有效起始年月

    private String effectEndDate; // 有效终止年月

    private String remark; // 备注

    private String companyPayType; // 公司缴费方式

    private String companyPayAmount; // 公司缴费额度

    private String companyPayMonth; // 公司缴费月份

    private String personPayType; // 个人缴费方式

    private String personPayAmount; // 个人缴费额度

    private String personPayMonth; // 个人缴费月份

    private String enable;// 启用禁用

    // 构造方法
    public SocialSecurityFund() {
        super();
    }

    public SocialSecurityFund(String id) {
        super(id);
    }

    // 生成get和set方法


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDefaultPayConfigId() {
        return defaultPayConfigId;
    }

    public void setDefaultPayConfigId(String defaultPayConfigId) {
        this.defaultPayConfigId = defaultPayConfigId;
    }

    @Length(min = 1, max = 64, message = "城市名称长度必须介于 1 和 64 之间")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Length(min = 1, max = 64, message = "员工工号长度必须介于 1 和 64 之间")
    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    @Length(min = 0, max = 18, message = "试用期薪资长度必须介于 1 和 18 之间")
    public String getProbationSalary() {
        return probationSalary;
    }

    public void setProbationSalary(String probationSalary) {
        this.probationSalary = probationSalary;
    }

    @Length(min = 1, max = 18, message = "转正薪资长度必须介于 1 和 18 之间")
    public String getRagularSalary() {
        return ragularSalary;
    }

    public void setRagularSalary(String ragularSalary) {
        this.ragularSalary = ragularSalary;
    }

    @Length(min = 1, max = 64, message = "产品类型长度必须介于 1 和 64 之间")
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Length(min = 1, max = 64, message = "产品名称长度必须介于 1 和 64 之间")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 1, max = 18, message = "所属状态,选项：正常折扣、不折扣长度必须介于 1 和 18 之间")
    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    @Length(min = 1, max = 18, message = "公司缴纳基数长度必须介于 1 和 18 之间")
    public String getCompanyPayBase() {
        return companyPayBase;
    }

    public void setCompanyPayBase(String companyPayBase) {
        this.companyPayBase = companyPayBase;
    }

    @Length(min = 1, max = 18, message = "公司缴费比例长度必须介于 1 和 18 之间")
    public String getCompanyPayScale() {
        return companyPayScale;
    }

    public void setCompanyPayScale(String companyPayScale) {
        this.companyPayScale = companyPayScale;
    }

    @Length(min = 1, max = 18, message = "个人缴费基数长度必须介于 1 和 18 之间")
    public String getPersonPayBase() {
        return personPayBase;
    }

    public void setPersonPayBase(String personPayBase) {
        this.personPayBase = personPayBase;
    }

    @Length(min = 1, max = 18, message = "个人缴费比例长度必须介于 1 和 18 之间")
    public String getPersonPayScale() {
        return personPayScale;
    }

    public void setPersonPayScale(String personPayScale) {
        this.personPayScale = personPayScale;
    }

    @Length(min = 1, max = 24, message = "有效起始年月长度必须介于 1 和 24 之间")
    public String getEffectStartDate() {
        return effectStartDate;
    }

    public void setEffectStartDate(String effectStartDate) {
        this.effectStartDate = effectStartDate;
    }

    @Length(min = 0, max = 24, message = "有效终止年月长度必须介于 0 和 24 之间")
    public String getEffectEndDate() {
        return effectEndDate;
    }

    public void setEffectEndDate(String effectEndDate) {
        this.effectEndDate = effectEndDate;
    }

    @Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 64, message = "公司缴费方式长度必须介于 1 和 64 之间")
    public String getCompanyPayType() {
        return companyPayType;
    }

    public void setCompanyPayType(String companyPayType) {
        this.companyPayType = companyPayType;
    }

    @Length(min = 0, max = 18, message = "公司缴费额度长度必须介于 1 和 18 之间")
    public String getCompanyPayAmount() {
        return companyPayAmount;
    }

    public void setCompanyPayAmount(String companyPayAmount) {
        this.companyPayAmount = companyPayAmount;
    }

    @Length(min = 0, max = 10, message = "公司缴费月份长度必须介于 0 和 10 之间")
    public String getCompanyPayMonth() {
        return companyPayMonth;
    }

    public void setCompanyPayMonth(String companyPayMonth) {
        this.companyPayMonth = companyPayMonth;
    }

    @Length(min = 0, max = 64, message = "个人缴费方式长度必须介于 1 和 64 之间")
    public String getPersonPayType() {
        return personPayType;
    }

    public void setPersonPayType(String personPayType) {
        this.personPayType = personPayType;
    }

    @Length(min = 0, max = 18, message = "个人缴费额度长度必须介于 1 和 18 之间")
    public String getPersonPayAmount() {
        return personPayAmount;
    }

    public void setPersonPayAmount(String personPayAmount) {
        this.personPayAmount = personPayAmount;
    }

    @Length(min = 0, max = 10, message = "个人缴费月份长度必须介于 0 和 10 之间")
    public String getPersonPayMonth() {
        return personPayMonth;
    }

    public void setPersonPayMonth(String personPayMonth) {
        this.personPayMonth = personPayMonth;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "social_security_fund";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358441";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "社保公积金";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
