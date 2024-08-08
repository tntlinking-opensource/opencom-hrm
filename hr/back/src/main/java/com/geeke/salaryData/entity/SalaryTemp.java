package com.geeke.salaryData.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 薪资项目模板Entity
 * @author
 * @version
 */

public class SalaryTemp extends DataEntity<SalaryTemp> {

    private static final long serialVersionUID = 1709759135450792891L;

    private String id; // 主键

    private String customerId; // 客户id

    private String saItemName; // 薪资项目中文名称

    private String saItemEname; // 薪资项目英文名称

    private String saItemDesc; // 薪资项目描述

    private String property; // 增减属性:0 增，1 减，2 其他

    private String decimalNum; // 小数位数

    private String isValid = "1"; // 是否有效 1:有效 0:无效

    private String dataSource; // 数据来源 1 公式，2 固定值，3 手输，4 上月项目，5 合同数据

    private String computeOrder; // 计算顺序

    private String isDisplay; // 是否显示 1:显示，0:不显示

    private String createUserId; // 创建人

    private String createDate; // 创建日期

    private String lastUpdateUserId; // 最后修改人

    private String lastUpdateDate; // 最后修改日期

    private String dataField; // 对应薪资数据字段名

    private String isTax; // 是否扣税

    private String itemCostGroup; // 薪资项成本分组a1：基本工资，a2：法定社会福利，a3：企业补充福利，a4：临时服务成本，a5：超时服务成本，a6：差旅补贴成本 a7:专项附加扣除

    private String computeLevel; // 薪资项计算级别（数字越大，计算级别越高，顺序越晚）

    private String isSystem; // 是否系统项 1 是 2 否

    private String saType = "0"; // 0：薪资1：年终奖2：劳务报酬3：实习生劳务费4：离职补偿金5：合计

    // 构造方法
    public SalaryTemp() {
        super();
    }

    public SalaryTemp(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 19, message = "主键长度必须介于 1 和 19 之间")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 0, max = 19, message = "客户id长度必须介于 0 和 19 之间")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Length(min = 0, max = 100, message = "薪资项目中文名称长度必须介于 0 和 100 之间")
    public String getSaItemName() {
        return saItemName;
    }

    public void setSaItemName(String saItemName) {
        this.saItemName = saItemName;
    }

    @Length(min = 0, max = 100, message = "薪资项目英文名称长度必须介于 0 和 100 之间")
    public String getSaItemEname() {
        return saItemEname;
    }

    public void setSaItemEname(String saItemEname) {
        this.saItemEname = saItemEname;
    }

    @Length(min = 0, max = 150, message = "薪资项目描述长度必须介于 0 和 150 之间")
    public String getSaItemDesc() {
        return saItemDesc;
    }

    public void setSaItemDesc(String saItemDesc) {
        this.saItemDesc = saItemDesc;
    }

    @Length(min = 0, max = 128, message = "增减属性:0 增，1 减，2 其他长度必须介于 0 和 128 之间")
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Length(min = 0, max = 18, message = "小数位数长度必须介于 0 和 18 之间")
    public String getDecimalNum() {
        return decimalNum;
    }

    public void setDecimalNum(String decimalNum) {
        this.decimalNum = decimalNum;
    }

    @Length(min = 0, max = 128, message = "是否有效 1:有效 0:无效长度必须介于 0 和 128 之间")
    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Length(min = 0, max = 128, message = "数据来源 1 公式，2 固定值，3 手输，4 上月项目，5 合同数据长度必须介于 0 和 128 之间")
    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Length(min = 0, max = 18, message = "计算顺序长度必须介于 0 和 18 之间")
    public String getComputeOrder() {
        return computeOrder;
    }

    public void setComputeOrder(String computeOrder) {
        this.computeOrder = computeOrder;
    }

    @Length(min = 0, max = 128, message = "是否显示 1:显示，0:不显示长度必须介于 0 和 128 之间")
    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    @Length(min = 0, max = 19, message = "创建人长度必须介于 0 和 19 之间")
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

    @Length(min = 0, max = 19, message = "最后修改人长度必须介于 0 和 19 之间")
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

    @Length(min = 0, max = 128, message = "对应薪资数据字段名长度必须介于 0 和 128 之间")
    public String getDataField() {
        return dataField;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    @Length(min = 0, max = 1, message = "是否扣税长度必须介于 0 和 1 之间")
    public String getIsTax() {
        return isTax;
    }

    public void setIsTax(String isTax) {
        this.isTax = isTax;
    }

    @Length(
        min = 0,
        max = 20,
        message = "薪资项成本分组a1：基本工资，a2：法定社会福利，a3：企业补充福利，a4：临时服务成本，a5：超时服务成本，a6：差旅补贴成本 a7:专项附加扣除长度必须介于 0 和 20 之间"
    )
    public String getItemCostGroup() {
        return itemCostGroup;
    }

    public void setItemCostGroup(String itemCostGroup) {
        this.itemCostGroup = itemCostGroup;
    }

    @Length(min = 0, max = 18, message = "薪资项计算级别（数字越大，计算级别越高，顺序越晚）长度必须介于 0 和 18 之间")
    public String getComputeLevel() {
        return computeLevel;
    }

    public void setComputeLevel(String computeLevel) {
        this.computeLevel = computeLevel;
    }

    @Length(min = 0, max = 1, message = "是否系统项 1 是 2 否长度必须介于 0 和 1 之间")
    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    @Length(
        min = 0,
        max = 100,
        message = "0：薪资1：年终奖2：劳务报酬3：实习生劳务费4：离职补偿金5：合计长度必须介于 0 和 100 之间"
    )
    public String getSaType() {
        return saType;
    }

    public void setSaType(String saType) {
        this.saType = saType;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "salary_temp";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1709759135450792891";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "薪资项目模板表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
