package com.geeke.specialAddDeduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class TaxDeclareConfig {

    private Long id;

    private String code; //编码
    
    private String name;  //名称
    
    private String limitAmount; //上限金额
    
    private String isMergeTax; //是否合并计税

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getIsMergeTax() {
        return isMergeTax;
    }

    public void setIsMergeTax(String isMergeTax) {
        this.isMergeTax = isMergeTax;
    }
}
