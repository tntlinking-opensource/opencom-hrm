package com.geeke.hrm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author yi.luo
 * @date 2023-05-31 11:30
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HrmHomeDataResponse {

    //人数
    /**
     * 员工总数
     */
    private String peopleTotalNum;
    /**
     * 招聘办理
     */
    private String recruitProcessingNum;
    /**
     * 入职办理
     */
    private String inductionProcessingNum;
    /**
     * 离职办理
     */
    private String departProcessingNum;
    /**
     * 上月薪酬支出
     */
    private BigDecimal lastMonthSalaryExpenses;


    //我的待办
    /**
     * 招聘审批
     */
    private String recruitApproval;
    /**
     * 入职审批
     */
    private String inductionApproval;
    /**
     * 离职审批
     */
    private String departApproval;
    /**
     * 薪资审批
     */
    private String salaryApproval;


    //头目
    /**
     * 毕业后工作天数
     */
    private String allWorkDays;

    /**
     * 到本公司工作到现在
     */
    private  String companyDays;

}
