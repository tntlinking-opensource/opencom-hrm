package com.geeke.accumulation.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class SocialSecurityFundExportDto {

    private static final long serialVersionUID = 1641034040897358441L;

    @ExcelIgnore
    private String companyId;

    @ExcelProperty(value = "缴费城市", index = 0)
    @ColumnWidth(value = 18)
    private String cityName; // 城市名称

    @ExcelProperty(value = "姓名", index = 1)
    @ColumnWidth(value = 18)
    private String employeeName ;		// 员工姓名

    @ExcelProperty(value = "部门", index = 2)
    @ColumnWidth(value = 18)
    private String deptId ;		// 部门

    @ExcelProperty(value = "工号", index = 3)
    @ColumnWidth(value = 18)
    private String employeeNo; // 员工工号

    @ExcelProperty(value = "全额工资", index = 4)
    @ColumnWidth(value = 18)
    private String ragularSalary; // 转正薪资

    @ExcelProperty(value = "试用期工资", index = 5)
    @ColumnWidth(value = 18)
    private String probationSalary; // 试用期薪资

    @ExcelProperty(value = "入职时间", index = 6)
    @ColumnWidth(value = 18)
    private String entryTime;

    @ExcelProperty(value = "折扣状态", index = 7)
    @ColumnWidth(value = 18)
    private String discountStatus; // 所属状态,选项：正常折扣、不折扣

    @ExcelProperty(value = "产品类型", index = 8)
    @ColumnWidth(value = 18)
    private String productTypeName; // 产品类型

    @ExcelProperty(value = "产品名称", index = 9)
    @ColumnWidth(value = 18)
    private String productName; // 产品名称


    @ExcelProperty(value = "公司比例", index = 9)
    @ColumnWidth(value = 18)
    private String companyPayBase; // 公司缴纳基数

    private String companyPayScale; // 公司缴费比例

    private String personPayBase; // 个人缴费基数

    private String personPayScale; // 个人缴费比例

    private String effectStartDate; // 有效起始年月

    private String effectEndDate; // 有效终止年月

    private String remark; // 备注

    private String companyPayType; // 公司缴费方式

    private String companyPayAmount; // 公司缴费额度

    private String companyPayMonth; // 公司缴费月份

    private String personPayType; // 个人缴费方式

    private String personPayAmount; // 个人缴费额度

    private String personPayMonth; // 个人缴费月份

    private String enable;// 启用禁用

    private String deptName; // 部门名称

    private String socialAvgAmount; // 社会平均工资

    private String companyHighBase; // 公司最高基数

    private String companyLowBase; // 公司最低基数

    private String personHighBase; // 个人最高基数

    private String personLowBase; // 个人最低基数




}
