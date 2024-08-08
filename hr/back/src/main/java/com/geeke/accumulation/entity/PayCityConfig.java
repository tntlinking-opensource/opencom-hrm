package com.geeke.accumulation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.taxRate.annotion.CompanyId;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 缴费城市Entity
 * @author
 * @version
 */
@CompanyId
public class PayCityConfig extends DataEntity<PayCityConfig> {

    private static final long serialVersionUID = 1641034040897358777L;

    private String companyId; // 公司id

    private String payCityName; // 缴费城市名称

    private String socialAvgAmount; // 社会平均工资


    // 构造方法
    public PayCityConfig() {
        super();
    }

    public PayCityConfig(String id) {
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

    @Length(min = 1, max = 20, message = "缴费城市名称长度必须介于 1 和 20 之间")
    public String getPayCityName() {
        return payCityName;
    }

    public void setPayCityName(String payCityName) {
        this.payCityName = payCityName;
    }

    @Length(min = 1, max = 18, message = "社会平均工资长度必须介于 1 和 18 之间")
    public String getSocialAvgAmount() {
        return socialAvgAmount;
    }

    public void setSocialAvgAmount(String socialAvgAmount) {
        this.socialAvgAmount = socialAvgAmount;
    }


    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "pay_city_config";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897358777";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "缴费城市配置";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
