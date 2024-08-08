package com.geeke.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 工资上传Entity
 * @author
 * @version
 */

public class HrmSalaryUploadSummary extends DataEntity<HrmSalaryUploadSummary> {

    private static final long serialVersionUID = 1457254743943434047L;

    private String name; // name

    private String salaryYearAndMonth; // 账单年月

    private String salaryPeopleNumber; // 薪资账单人数

    private String tenantId; // tenant_id

    private String stateType; // 是否启用：0-启用，1-禁用

    private String creator; // 创建人工号

    private String updator; // 更新人工号

    // 构造方法
    public HrmSalaryUploadSummary() {
        super();
    }

    public HrmSalaryUploadSummary(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 64, message = "name长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 100, message = "账单年月长度必须介于 0 和 100 之间")
    public String getSalaryYearAndMonth() {
        return salaryYearAndMonth;
    }

    public void setSalaryYearAndMonth(String salaryYearAndMonth) {
        this.salaryYearAndMonth = salaryYearAndMonth;
    }

    @Length(min = 1, max = 100, message = "薪资账单人数长度必须介于 1 和 100 之间")
    public String getSalaryPeopleNumber() {
        return salaryPeopleNumber;
    }

    public void setSalaryPeopleNumber(String salaryPeopleNumber) {
        this.salaryPeopleNumber = salaryPeopleNumber;
    }

    @Length(min = 0, max = 100, message = "tenant_id长度必须介于 0 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 0, max = 100, message = "是否启用：0-启用，1-禁用长度必须介于 0 和 100 之间")
    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    @Length(min = 0, max = 100, message = "创建人工号长度必须介于 0 和 100 之间")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Length(min = 0, max = 100, message = "更新人工号长度必须介于 0 和 100 之间")
    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "hrm_salary_upload_summary";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943434047";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "薪资条账单汇总表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
