package com.geeke.accumulation.entity.vo;

/**
 * 账单列表展示 entity *
 */
public class BillListVo {

    private String batchNo; // 批次号

    private String billYearMonth; // 账单生成年月

    private String billAmount; // 账单金额

    private String billPeopleNumber; // 账单人数

    private String createDate; // 生成时间

    private String createBy; // 生成人

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBillYearMonth() {
        return billYearMonth;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth = billYearMonth;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillPeopleNumber() {
        return billPeopleNumber;
    }

    public void setBillPeopleNumber(String billPeopleNumber) {
        this.billPeopleNumber = billPeopleNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
