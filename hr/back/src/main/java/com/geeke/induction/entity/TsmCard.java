package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmCard extends BaseInductionEntity<TsmCard> {

    private static final long serialVersionUID = 1457254743943432658L;

    private String accountNumber; // 账号

    private String accountType; // 类型

    private String bankAddress; // 开户行

    private String accountOwner; // 开户人

    private String bankCity; // 开户城市

    private String accountHolder; // 开户人

    private String bankName; // 银行名称

    // 构造方法
    public TsmCard() {
        super();
    }

    public TsmCard(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 50, message = "账号长度必须介于 1 和 50 之间")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Length(min = 1, max = 50, message = "类型长度必须介于 1 和 50 之间")
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Length(min = 1, max = 100, message = "开户行长度必须介于 1 和 100 之间")
    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    @Length(min = 1, max = 100, message = "开户人长度必须介于 1 和 100 之间")
    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    @Length(min = 1, max = 100, message = "开户城市长度必须介于 1 和 100 之间")
    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    @Length(min = 1, max = 100, message = "开户人长度必须介于 1 和 100 之间")
    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    @Length(min = 1, max = 100, message = "银行名称长度必须介于 1 和 100 之间")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_card";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432658";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
