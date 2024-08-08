package com.geeke.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;
import com.geeke.hrm.entity.HrmSalaryUploadSummary;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 薪资查看Entity
 * @author
 * @version
 */

public class HrmPayrollBillDetails extends DataEntity<HrmPayrollBillDetails> {

    private static final long serialVersionUID = 1457254743943434067L;

    private HrmSalaryUploadSummary summary; // 汇总表id

    private String salaryYearAndMonth; // 账单年月

    private String empolyno; // 工号

    private String name; // 姓名

    private String deptName; // 部门名称

    private BigDecimal totalSalary; // 全额工资

    private BigDecimal totalBonus; // 奖金合计

    private BigDecimal totalAllowanceAndDeduction; // 补扣款合计

    private BigDecimal personalSocialInsurance; // 社保小计(个人)

    private BigDecimal personalAccumulationFund; // 公积金小计(个人)

    private BigDecimal salaryNeedTax; // 计税工资

    private BigDecimal personalTax; // 个人税款

    private BigDecimal totalAfterTax; // 税后项合计

    private String remark; // 补充信息

    private BigDecimal netSalary2; // 实发工资2

    private String remark2; // 补充信息2

    private BigDecimal netSalary; // 实发工资

    private String tenantId; // tenant_id

    private String stateType; // 是否启用：0-启用，1-禁用

    private String creator; // 创建人工号

    private String updator; // 更新人工号

    private BigDecimal salaryTax; // 所得税

    // 构造方法
    public HrmPayrollBillDetails() {
        super();
    }

    public HrmPayrollBillDetails(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "汇总表id不能为空")
    public HrmSalaryUploadSummary getSummary() {
        return summary;
    }

    public void setSummary(HrmSalaryUploadSummary summary) {
        this.summary = summary;
    }

    @Length(min = 0, max = 100, message = "账单年月长度必须介于 0 和 100 之间")
    public String getSalaryYearAndMonth() {
        return salaryYearAndMonth;
    }

    public void setSalaryYearAndMonth(String salaryYearAndMonth) {
        this.salaryYearAndMonth = salaryYearAndMonth;
    }

    @Length(min = 0, max = 100, message = "工号长度必须介于 0 和 100 之间")
    public String getEmpolyno() {
        return empolyno;
    }

    public void setEmpolyno(String empolyno) {
        this.empolyno = empolyno;
    }

    @Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 255, message = "部门名称长度必须介于 0 和 255 之间")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public BigDecimal getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(BigDecimal totalBonus) {
        this.totalBonus = totalBonus;
    }

    public BigDecimal getTotalAllowanceAndDeduction() {
        return totalAllowanceAndDeduction;
    }

    public void setTotalAllowanceAndDeduction(BigDecimal totalAllowanceAndDeduction) {
        this.totalAllowanceAndDeduction = totalAllowanceAndDeduction;
    }

    public BigDecimal getPersonalSocialInsurance() {
        return personalSocialInsurance;
    }

    public void setPersonalSocialInsurance(BigDecimal personalSocialInsurance) {
        this.personalSocialInsurance = personalSocialInsurance;
    }

    public BigDecimal getPersonalAccumulationFund() {
        return personalAccumulationFund;
    }

    public void setPersonalAccumulationFund(BigDecimal personalAccumulationFund) {
        this.personalAccumulationFund = personalAccumulationFund;
    }

    public BigDecimal getSalaryNeedTax() {
        return salaryNeedTax;
    }

    public void setSalaryNeedTax(BigDecimal salaryNeedTax) {
        this.salaryNeedTax = salaryNeedTax;
    }

    public BigDecimal getPersonalTax() {
        return personalTax;
    }

    public void setPersonalTax(BigDecimal personalTax) {
        this.personalTax = personalTax;
    }

    public BigDecimal getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(BigDecimal totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    @Length(min = 0, max = 100, message = "补充信息长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getNetSalary2() {
        return netSalary2;
    }

    public void setNetSalary2(BigDecimal netSalary2) {
        this.netSalary2 = netSalary2;
    }

    @Length(min = 0, max = 100, message = "补充信息2长度必须介于 0 和 100 之间")
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
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

    public BigDecimal getSalaryTax() {
        return salaryTax;
    }

    public void setSalaryTax(BigDecimal salaryTax) {
        this.salaryTax = salaryTax;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "hrm_payroll_bill_details";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943434067";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "薪资条账单明细表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
