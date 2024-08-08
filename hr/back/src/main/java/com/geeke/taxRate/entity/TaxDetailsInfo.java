package com.geeke.taxRate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.Length;

/**
 * 税率详细信息Entity
 * @author
 * @version
 */

public class TaxDetailsInfo extends DataEntity<TaxDetailsInfo> {

    private static final long serialVersionUID = 1641034040897357277L;

    private String taxTypeInfoId; // 税率类型表标识

    private String rateLevel; // 速率级次

    private BigDecimal amountDown; // 金额上限

    private BigDecimal amountUp; // 金额下限

    private BigDecimal rate; // 税率

    private BigDecimal speedDeductAmount; // 速算扣除数

    private String monthTaxAmount; // 月纳税所得额

    private String freeTaxAmount; // 免税所得额

    // 构造方法
    public TaxDetailsInfo() {
        super();
    }

    public TaxDetailsInfo(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 21, message = "税率类型表标识长度必须介于 1 和 21 之间")
    public String getTaxTypeInfoId() {
        return taxTypeInfoId;
    }

    public void setTaxTypeInfoId(String taxTypeInfoId) {
        this.taxTypeInfoId = taxTypeInfoId;
    }

    @Length(min = 0, max = 18, message = "速率级次长度必须介于 0 和 18 之间")
    public String getRateLevel() {
        return rateLevel;
    }

    public void setRateLevel(String rateLevel) {
        this.rateLevel = rateLevel;
    }

    public BigDecimal getAmountDown() {
        return amountDown;
    }

    public void setAmountDown(BigDecimal amountDown) {
        this.amountDown = amountDown;
    }

    public BigDecimal getAmountUp() {
        return amountUp;
    }

    public void setAmountUp(BigDecimal amountUp) {
        this.amountUp = amountUp;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getSpeedDeductAmount() {
        return speedDeductAmount;
    }

    public void setSpeedDeductAmount(BigDecimal speedDeductAmount) {
        this.speedDeductAmount = speedDeductAmount;
    }

    public String getMonthTaxAmount() {
        return monthTaxAmount;
    }

    public void setMonthTaxAmount(String monthTaxAmount) {
        this.monthTaxAmount = monthTaxAmount;
    }

    public String getFreeTaxAmount() {
        return freeTaxAmount;
    }

    public void setFreeTaxAmount(String freeTaxAmount) {
        this.freeTaxAmount = freeTaxAmount;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tax_details_info";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357277";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "税率详细信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
