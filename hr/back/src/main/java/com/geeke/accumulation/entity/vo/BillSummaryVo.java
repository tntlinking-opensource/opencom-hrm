package com.geeke.accumulation.entity.vo;

public class BillSummaryVo {

    private String batchNo; // 批次号

    private String billYearMonth; // 账单生成年月

    private String unitSocialSecurityAmount; // 单位社保

    private String personSocialSecurityAmount; // 个人社保

    private String totalSocialSecurityAmount; // 社保小计 = 单位社保 + 个人社保
}
