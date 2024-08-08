
package com.geeke.salaryPay.entity;


import org.hibernate.validator.constraints.Length;

import com.geeke.sys.entity.DictItem;

import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnore;


import com.geeke.common.persistence.DataEntity;


/**
 * 申请发放模板Entity
 *
 * @author
 */

public class SalaryPay extends DataEntity<SalaryPay> {


    private static final long serialVersionUID = 1740772218121938768L;


    private String companyId;        // 分公司ID


    private String id;        // 主键


    private String saBatchCode;        // 薪资批次号


    private String customerName;        // 客户名称


    private String customerCode;        // 客户编码


    private String customerId;        // 客户id


    private String saClassId;        // 薪资类别ID


    private String payYearMonth;        // 应发年月


    private DictItem isVerify = new DictItem("0");      // 是否数据确认　1：是　0：否


    private String verifyTime;        // 确认时间systime(审核通过时间)


    private String isPay;        // 是否支付


    private String payTime;        // 支付时间 systime（财务确认支付完成时间）


    private String payAuditId;        // 支付审核流程ID


    private String auditUser;        // 审核人（数据确认人）


    private String payName;        // 发放名称


    private String billYearMonth;        // 账单年月


    private String taxMonth;        // 计税年月


    private String isUniteTax = "1";        // 是否合并计税 1.是 0. 否


    private String remarks;        // 备注


    private String submitUserId;        // 提交审核人/客服（同收费单导入）


    private String submitTime;        // 提交审核时间


    private String applyState = "0";        // 审核申请状态：0：未申请 1：已申请


    private String auditState = "0";        // 审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成


    private BigDecimal mustDay;        // 应到账日


    private String rejectCause;        // 驳回备注


    private String ebankTime;        // 生成网银时间systime


    private String totalDecimal;        // 生成人数


    private BigDecimal totalAmount;        // 总金额


    private BigDecimal failDecimal;        // 生成失败金额


    private String payrollTypeBd;        // 工资单类型：0：不需要 1：保密工资单2：Email 3:保密+Email 4：信封5：保密+信封


    private String payrollStateBd = "0";        // 工资单情况：1:已打印 0：未打印


    private String isDel = "0";        // 是否删除 0:正常　1:删除


    private String createUserId;        // 创建人


    private String createDate;        // 创建日期


    private String lastUpdateUserId;        // 最后修改人


    private String lastUpdateDate;        // 最后修改日期


    private String applyPayDate;        // 申请发放日期


    private String auditTime;        // AUDIT_TIME


    private String genMoneyCode;        // 到款编号


    private String paySalaryType;        // 发放工资类型：0:有到款编号，1无到款编号


    private String notCodeReason;        // 无到款编号原因：1:垫付申请2:固定垫付3:客户打款凭证4:北京打款预收款5:预收款6:其他


    private String claimUserId;        // 工资到款认领人id


    private String claimDate;        // 认领时间


    private String realPayDay;        // 实际发放日


    private String payState = "0";        // 发放状态：0：待发放 1：已发放(财务确认支付完成)


    private String applyPayTime;        // 申请发放时间systime


    private String applyPayUserId;        // 申请工资发放人


    private String isCsVerify = "0";        // 是否客服数据确认　1：是　0：否(暂不用到)


    private String applyPayState = "0";        // 工资发放申请状态 0：未申请 1：已申请


    private String oldId;        // key


    private String batchCodeQg;        // 全国委托户导入批次号


    private String printDate;        // 工资单打印日期


    private String rejectReason;        // 驳回原因


    private Integer rejectNumber;        // 驳回人数


    // 构造方法
    public SalaryPay() {
        super();
    }

    public SalaryPay(String id) {
        super(id);
    }


    // 生成get和set方法


    @Length(min = 0, max = 19, message = "分公司ID长度必须介于 0 和 19 之间")


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    @Length(min = 1, max = 19, message = "主键长度必须介于 1 和 19 之间")


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Length(min = 0, max = 100, message = "薪资批次号长度必须介于 0 和 100 之间")


    public String getSaBatchCode() {
        return saBatchCode;
    }

    public void setSaBatchCode(String saBatchCode) {
        this.saBatchCode = saBatchCode;
    }


    @Length(min = 0, max = 100, message = "客户名称长度必须介于 0 和 100 之间")


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    @Length(min = 0, max = 128, message = "客户编码长度必须介于 0 和 128 之间")


    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }


    @Length(min = 0, max = 19, message = "客户id长度必须介于 0 和 19 之间")


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    @Length(min = 0, max = 19, message = "薪资类别ID长度必须介于 0 和 19 之间")


    public String getSaClassId() {
        return saClassId;
    }

    public void setSaClassId(String saClassId) {
        this.saClassId = saClassId;
    }


    @Length(min = 0, max = 100, message = "应发年月长度必须介于 0 和 100 之间")


    public String getPayYearMonth() {
        return payYearMonth;
    }

    public void setPayYearMonth(String payYearMonth) {
        this.payYearMonth = payYearMonth;
    }


    public DictItem getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(DictItem isVerify) {
        this.isVerify = isVerify;
    }


    @Length(min = 0, max = -1, message = "确认时间systime(审核通过时间)长度必须介于 0 和 -1 之间")


    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }


    @Length(min = 0, max = 128, message = "是否支付长度必须介于 0 和 128 之间")


    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }


    @Length(min = 0, max = -1, message = "支付时间 systime（财务确认支付完成时间）长度必须介于 0 和 -1 之间")


    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }


    @Length(min = 0, max = 19, message = "支付审核流程ID长度必须介于 0 和 19 之间")


    public String getPayAuditId() {
        return payAuditId;
    }

    public void setPayAuditId(String payAuditId) {
        this.payAuditId = payAuditId;
    }


    @Length(min = 0, max = 19, message = "审核人（数据确认人）长度必须介于 0 和 19 之间")


    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }


    @Length(min = 0, max = 100, message = "发放名称长度必须介于 0 和 100 之间")


    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }


    @Length(min = 0, max = 100, message = "账单年月 长度必须介于 0 和 100 之间")


    public String getBillYearMonth() {
        return billYearMonth;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth = billYearMonth;
    }


    @Length(min = 0, max = 100, message = "计税年月长度必须介于 0 和 100 之间")


    public String getTaxMonth() {
        return taxMonth;
    }

    public void setTaxMonth(String taxMonth) {
        this.taxMonth = taxMonth;
    }


    public String getIsUniteTax() {
        return isUniteTax;
    }

    public void setIsUniteTax(String isUniteTax) {
        this.isUniteTax = isUniteTax;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Length(min = 0, max = 19, message = "提交审核人/客服（同收费单导入）长度必须介于 0 和 19 之间")


    public String getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(String submitUserId) {
        this.submitUserId = submitUserId;
    }


    @Length(min = 0, max = -1, message = "提交审核时间长度必须介于 0 和 -1 之间")


    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }


    @Length(min = 0, max = 128, message = "审核申请状态：0：未申请 1：已申请  长度必须介于 0 和 128 之间")


    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }


    @Length(min = 0, max = 128, message = "审核状态：0：待审核 1：已通过　2：未通过（驳回）3：已完成长度必须介于 0 和 128 之间")


    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }


    public BigDecimal getMustDay() {
        return mustDay;
    }

    public void setMustDay(BigDecimal mustDay) {
        this.mustDay = mustDay;
    }


    @Length(min = 0, max = 2000, message = "驳回备注长度必须介于 0 和 2000 之间")


    public String getRejectCause() {
        return rejectCause;
    }

    public void setRejectCause(String rejectCause) {
        this.rejectCause = rejectCause;
    }


    @Length(min = 0, max = -1, message = "生成网银时间systime长度必须介于 0 和 -1 之间")


    public String getEbankTime() {
        return ebankTime;
    }

    public void setEbankTime(String ebankTime) {
        this.ebankTime = ebankTime;
    }


    @Length(min = 0, max = 18, message = "生成人数长度必须介于 0 和 18 之间")


    public String getTotalDecimal() {
        return totalDecimal;
    }

    public void setTotalDecimal(String totalDecimal) {
        this.totalDecimal = totalDecimal;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public BigDecimal getFailDecimal() {
        return failDecimal;
    }

    public void setFailDecimal(BigDecimal failDecimal) {
        this.failDecimal = failDecimal;
    }


    @Length(min = 0, max = 128, message = "工资单类型：0：不需要 1：保密工资单2：Email 3:保密+Email 4：信封5：保密+信封长度必须介于 0 和 128 之间")


    public String getPayrollTypeBd() {
        return payrollTypeBd;
    }

    public void setPayrollTypeBd(String payrollTypeBd) {
        this.payrollTypeBd = payrollTypeBd;
    }


    @Length(min = 0, max = 128, message = "工资单情况：1:已打印 0：未打印长度必须介于 0 和 128 之间")


    public String getPayrollStateBd() {
        return payrollStateBd;
    }

    public void setPayrollStateBd(String payrollStateBd) {
        this.payrollStateBd = payrollStateBd;
    }


    @Length(min = 0, max = 128, message = "是否删除 0:正常　1:删除长度必须介于 0 和 128 之间")


    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }


    @Length(min = 0, max = 40, message = "创建人长度必须介于 0 和 40 之间")


    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    @Length(min = 0, max = 40, message = "最后修改人长度必须介于 0 和 40 之间")


    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(String lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }


    @Length(min = 0, max = -1, message = "最后修改日期长度必须介于 0 和 -1 之间")


    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    @Length(min = 0, max = -1, message = "申请发放日期长度必须介于 0 和 -1 之间")


    public String getApplyPayDate() {
        return applyPayDate;
    }

    public void setApplyPayDate(String applyPayDate) {
        this.applyPayDate = applyPayDate;
    }


    @Length(min = 0, max = -1, message = "AUDIT_TIME长度必须介于 0 和 -1 之间")


    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }


    @Length(min = 0, max = 50, message = "到款编号长度必须介于 0 和 50 之间")


    public String getGenMoneyCode() {
        return genMoneyCode;
    }

    public void setGenMoneyCode(String genMoneyCode) {
        this.genMoneyCode = genMoneyCode;
    }


    @Length(min = 0, max = 128, message = "发放工资类型：0:有到款编号，1无到款编号长度必须介于 0 和 128 之间")


    public String getPaySalaryType() {
        return paySalaryType;
    }

    public void setPaySalaryType(String paySalaryType) {
        this.paySalaryType = paySalaryType;
    }


    @Length(min = 0, max = 128, message = "无到款编号原因：1:垫付申请2:固定垫付3:客户打款凭证4:北京打款预收款5:预收款6:其他长度必须介于 0 和 128 之间")


    public String getNotCodeReason() {
        return notCodeReason;
    }

    public void setNotCodeReason(String notCodeReason) {
        this.notCodeReason = notCodeReason;
    }


    @Length(min = 0, max = 19, message = "工资到款认领人id长度必须介于 0 和 19 之间")


    public String getClaimUserId() {
        return claimUserId;
    }

    public void setClaimUserId(String claimUserId) {
        this.claimUserId = claimUserId;
    }


    @Length(min = 0, max = -1, message = "认领时间长度必须介于 0 和 -1 之间")


    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }


    @Length(min = 0, max = -1, message = "实际发放日长度必须介于 0 和 -1 之间")


    public String getRealPayDay() {
        return realPayDay;
    }

    public void setRealPayDay(String realPayDay) {
        this.realPayDay = realPayDay;
    }


    @Length(min = 0, max = 128, message = "发放状态：0：待发放 1：已发放(财务确认支付完成)长度必须介于 0 和 128 之间")


    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }


    @Length(min = 0, max = -1, message = "申请发放时间systime长度必须介于 0 和 -1 之间")


    public String getApplyPayTime() {
        return applyPayTime;
    }

    public void setApplyPayTime(String applyPayTime) {
        this.applyPayTime = applyPayTime;
    }


    @Length(min = 0, max = 40, message = "申请工资发放人长度必须介于 0 和 40 之间")


    public String getApplyPayUserId() {
        return applyPayUserId;
    }

    public void setApplyPayUserId(String applyPayUserId) {
        this.applyPayUserId = applyPayUserId;
    }


    @Length(min = 0, max = 1, message = "是否客服数据确认　1：是　0：否(暂不用到)长度必须介于 0 和 1 之间")


    public String getIsCsVerify() {
        return isCsVerify;
    }

    public void setIsCsVerify(String isCsVerify) {
        this.isCsVerify = isCsVerify;
    }


    @Length(min = 0, max = 1, message = "工资发放申请状态 0：未申请 1：已申请  长度必须介于 0 和 1 之间")


    public String getApplyPayState() {
        return applyPayState;
    }

    public void setApplyPayState(String applyPayState) {
        this.applyPayState = applyPayState;
    }


    @Length(min = 0, max = 22, message = "key长度必须介于 0 和 22 之间")


    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }


    @Length(min = 0, max = 50, message = "全国委托户导入批次号长度必须介于 0 和 50 之间")


    public String getBatchCodeQg() {
        return batchCodeQg;
    }

    public void setBatchCodeQg(String batchCodeQg) {
        this.batchCodeQg = batchCodeQg;
    }


    @Length(min = 0, max = -1, message = "工资单打印日期长度必须介于 0 和 -1 之间")


    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }


    @Length(min = 0, max = 2, message = "驳回原因长度必须介于 0 和 2 之间")


    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }


    public Integer getRejectNumber() {
        return rejectNumber;
    }

    public void setRejectNumber(Integer rejectNumber) {
        this.rejectNumber = rejectNumber;
    }


    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "salary_batch";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1740772218121938768";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "员工薪资发放批次表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {

        return false;

    }
}
