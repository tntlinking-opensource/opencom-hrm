package com.geeke.salaryArchives.dto;

import com.geeke.salaryArchives.entity.SalaryArchives;
import com.geeke.taxRate.annotion.CompanyId;

@CompanyId
public class SalaryArchivesDto extends SalaryArchives {

    public String deptName;

    private String salaryRateTableName;        // 工资薪金税率表

    private String yearEndRateTableName;        // 年终奖税率表

    private String rewardRateTableName;        // 劳务报酬税率表

    private String leaveCompensationRateTableName;        // 离职补偿税率表


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSalaryRateTableName() {
        return salaryRateTableName;
    }

    public void setSalaryRateTableName(String salaryRateTableName) {
        this.salaryRateTableName = salaryRateTableName;
    }

    public String getYearEndRateTableName() {
        return yearEndRateTableName;
    }

    public void setYearEndRateTableName(String yearEndRateTableName) {
        this.yearEndRateTableName = yearEndRateTableName;
    }

    public String getRewardRateTableName() {
        return rewardRateTableName;
    }

    public void setRewardRateTableName(String rewardRateTableName) {
        this.rewardRateTableName = rewardRateTableName;
    }

    public String getLeaveCompensationRateTableName() {
        return leaveCompensationRateTableName;
    }

    public void setLeaveCompensationRateTableName(String leaveCompensationRateTableName) {
        this.leaveCompensationRateTableName = leaveCompensationRateTableName;
    }
}
