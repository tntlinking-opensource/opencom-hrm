package com.geeke.taxRate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.taxRate.annotion.CompanyId;
import com.geeke.taxRate.entity.TaxDetailsInfo;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 税率类型Entity
 * @author
 * @version
 */
@CompanyId
public class TaxTypeInfo extends DataEntity<TaxTypeInfo> {

    private static final long serialVersionUID = 1641034040897357276L;

    private String companyId;

    private String taxName; // 税率表名称

    private String taxType; // 类型

    private BigDecimal nationalTariff; // 固定税率

    private BigDecimal expDeduction; // 费用扣除

    private BigDecimal monthAvg; // 月平均金额

    private BigDecimal monthTaxIncome; // 月纳税所得额

    private BigDecimal deductRate; // 扣除费率

    private List<TaxDetailsInfo> taxDetailsInfoList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TaxTypeInfo() {
        super();
    }

    public TaxTypeInfo(String id) {
        super(id);
    }

    // 生成get和set方法
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 255, message = "税率表名称长度必须介于 0 和 255 之间")
    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    @Length(min = 0, max = 255, message = "类型长度必须介于 0 和 255 之间")
    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public BigDecimal getNationalTariff() {
        return nationalTariff;
    }

    public void setNationalTariff(BigDecimal nationalTariff) {
        this.nationalTariff = nationalTariff;
    }

    public BigDecimal getExpDeduction() {
        return expDeduction;
    }

    public void setExpDeduction(BigDecimal expDeduction) {
        this.expDeduction = expDeduction;
    }

    public BigDecimal getMonthAvg() {
        return monthAvg;
    }

    public void setMonthAvg(BigDecimal monthAvg) {
        this.monthAvg = monthAvg;
    }

    public BigDecimal getMonthTaxIncome() {
        return monthTaxIncome;
    }

    public void setMonthTaxIncome(BigDecimal monthTaxIncome) {
        this.monthTaxIncome = monthTaxIncome;
    }

    public BigDecimal getDeductRate() {
        return deductRate;
    }

    public void setDeductRate(BigDecimal deductRate) {
        this.deductRate = deductRate;
    }

    public List<TaxDetailsInfo> getTaxDetailsInfoList() {
        return taxDetailsInfoList;
    }

    public void setTaxDetailsInfoList(List<TaxDetailsInfo> taxDetailsInfoList) {
        this.taxDetailsInfoList = taxDetailsInfoList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tax_type_info";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897357276";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "税率类型";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
