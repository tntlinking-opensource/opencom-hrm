package com.geeke.accumulation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.taxRate.annotion.CompanyId;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 默认配置Entity
 * @author
 * @version
 */
@CompanyId
public class DefaultPayConfig extends DataEntity<DefaultPayConfig> {

    private static final long serialVersionUID = 1641034040897358476L;

    private String companyId; // 公司id

    private String payCityId; // 缴费城市id

    private String productType; // 产品类型

    private String productName; // 产品名称

    private String unitScale; // 单位比例

    private String personScale; // 个人比例

    private String companyHighBase; // 公司最高基数

    private String companyLowBase; // 公司最低基数

    private String personHighBase; // 个人最高基数

    private String personLowBase; // 个人最低基数

    private String companyPayType; // 公司缴费方式

    private String companyPayAmount; // 公司缴费额度

    private String companyPayMonth; // 公司缴费月份

    private String personPayType; // 个人缴费方式

    private String personPayAmount; // 个人缴费额度

    private String personPayMonth; // 个人缴费月份

    private String enable; // 启用禁用

    private String isDefault; // 是否是默认比例

    private String status; // 所属状态


    // 构造方法
    public DefaultPayConfig() {
        super();
    }

    public DefaultPayConfig(String id) {
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 1, max = 21, message = "缴费城市id长度必须介于 1 和 21 之间")
    public String getPayCityId() {
        return payCityId;
    }

    public void setPayCityId(String payCityId) {
        this.payCityId = payCityId;
    }

    @Length(min = 1, max = 64, message = "产品类型长度必须介于 1 和 64 之间")
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Length(min = 0, max = 18, message = "单位比例长度必须介于 0 和 18 之间")
    public String getUnitScale() {
        return unitScale;
    }

    public void setUnitScale(String unitScale) {
        this.unitScale = unitScale;
    }

    @Length(min = 0, max = 18, message = "个人比例长度必须介于 0 和 18 之间")
    public String getPersonScale() {
        return personScale;
    }

    public void setPersonScale(String personScale) {
        this.personScale = personScale;
    }

    @Length(min = 1, max = 18, message = "公司最高基数长度必须介于 1 和 18 之间")
    public String getCompanyHighBase() {
        return companyHighBase;
    }

    public void setCompanyHighBase(String companyHighBase) {
        this.companyHighBase = companyHighBase;
    }

    @Length(min = 1, max = 18, message = "公司最低基数长度必须介于 1 和 18 之间")
    public String getCompanyLowBase() {
        return companyLowBase;
    }

    public void setCompanyLowBase(String companyLowBase) {
        this.companyLowBase = companyLowBase;
    }

    @Length(min = 1, max = 18, message = "个人最高基数长度必须介于 1 和 18 之间")
    public String getPersonHighBase() {
        return personHighBase;
    }

    public void setPersonHighBase(String personHighBase) {
        this.personHighBase = personHighBase;
    }

    @Length(min = 1, max = 18, message = "个人最低基数长度必须介于 1 和 18 之间")
    public String getPersonLowBase() {
        return personLowBase;
    }

    public void setPersonLowBase(String personLowBase) {
        this.personLowBase = personLowBase;
    }

    @Length(min = 0, max = 64, message = "公司缴费方式长度必须介于 1 和 64 之间")
    public String getCompanyPayType() {
        return companyPayType;
    }

    public void setCompanyPayType(String companyPayType) {
        this.companyPayType = companyPayType;
    }

    @Length(min = 0, max = 18, message = "公司缴费额度长度必须介于 0 和 18 之间")
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

    @Length(min = 0, max = 18, message = "个人缴费额度长度必须介于 0 和 18 之间")
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

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "default_pay_config";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358476";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "缴费城市";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
