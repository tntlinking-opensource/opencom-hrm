package com.geeke.salaryData.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.salaryData.entity.SalaryArchivesInfo;
import com.geeke.salaryData.entity.SalaryArchivesInfo;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 薪资明细Entity
 * @author
 * @version
 */

public class SalaryData extends DataEntity<SalaryData> {

    public static final long serialVersionUID = 1683007003934705771L;

    public String companyId; // 分公司ID

    public String id; // 员工薪资明细id

    public String saBatchId; // 薪资批次发放ID

    public String saClassId; // 薪资类别ID

    public SalaryArchivesInfo saDoc; // 薪资档案ID

    public String empId; // 员工id

    public String departmentId; // 部门ID

    public String payYearMonth; // 应发年月

    public String stopFlag; // 停发标识：1：停发 0：正常

    public String isVerify; // 是否数据确认　1：是　0：否

    public String isDel = "0"; // 是否删除 0:正常　1:删除

    public String isPostpone; // 缓发标记 1：缓发，0：正常

    public String isEmail; // 是否发送Emial工资单：1：已发送 0：未发送

    public String emialTime; // 发送Emia时间

    public String emailUser; // 发送人

    public String emailRemarks; // Emial备注

    public BigDecimal def1; // 基本工资

    public BigDecimal def2; // 奖金

    public BigDecimal def3; // 绩效奖金

    public BigDecimal def4; // 加班费

    public BigDecimal def5; // 薪资调整

    public BigDecimal def6; // 餐饮补助

    public BigDecimal def7; // 交通补助

    public BigDecimal def8; // 差旅津贴

    public BigDecimal def9; // 通信补贴

    public BigDecimal def10; // 补其他

    public BigDecimal def11; // 高温费

    public BigDecimal def12; // 独生子女费

    public BigDecimal def13; // 十三薪

    public BigDecimal def14; // 迟到扣款

    public BigDecimal def15; // 病事假扣款

    public BigDecimal def16; // 本次工资应发合计

    public BigDecimal def17; // 养老保险个人扣除

    public BigDecimal def18; // 医疗保险个人扣除

    public BigDecimal def19; // 失业保险个人扣除

    public BigDecimal def20; // 大病保险个人扣除

    public BigDecimal def21; // 社保个人扣除

    public BigDecimal def22; // 公积金个人扣除

    public BigDecimal def23; // 公积金免税金额

    public BigDecimal def24; // 公积金超额款

    public BigDecimal def25; // 企业年金个人扣除

    public BigDecimal def26; // 企业年金免税金额

    public BigDecimal def27; // 企业年金合并计税金额

    public BigDecimal def29; // 工资薪金免税收入

    public BigDecimal def28; // 工会会费

    public BigDecimal def30; // 工资薪金免税事项

    public BigDecimal def31; // 工资薪金其他扣除

    public BigDecimal def32; // 工资薪金其他扣除事项

    public BigDecimal def33; // 工资薪金其他合并计税项(计税不发)

    public BigDecimal def34; // 当月免税所得额

    public BigDecimal def35; // 本次工资应计税金额

    public BigDecimal def36; // 上次累计工资应计税金额

    public BigDecimal def37; // 累计工资应计税金额

    public BigDecimal def38; // 子女教育免税累计

    public BigDecimal def39; // 继续教育免税累计

    public BigDecimal def40; // 租房免税累计

    public BigDecimal def41; // 住房贷款免税累计

    public BigDecimal def42; // 赡养老人免税累计

    public BigDecimal def43; // 当前累计工资计税金额

    public BigDecimal def44; // 当前累计工资个税

    public BigDecimal def45; // 上次累计工资个税

    public String salaryTaxBd; // 工资报税属性：0:发工资且报税 1:只发工资 2:只报税

    public String isLocalPay; // 是否本地发放：1：是　0：否

    public String taxFlag; // 报税属性：0-不报税 1-大户 2-单户自行报税 3-单户代报税

    public String isValid; // 是否有效 1:有效 0:无效

    public String taxRegionBd; // 报税区间：1:：报当月 0：报上月

    public BigDecimal def46; // 本次工资个税

    public BigDecimal def47; // 残疾人减免税额（薪资）

    public BigDecimal def48; // 本次工资应纳个税

    public BigDecimal def49; // 不计税补贴项（税后减项）

    public BigDecimal def50; // 不计税补贴项（税后增加项）

    public BigDecimal def51; // 本次工资项目实发金额

    public BigDecimal def52; // 本次年终奖金额

    public BigDecimal def53; // 年终金免税收入

    public BigDecimal def54; // 年终金免税事项

    public BigDecimal def55; // 年终金其他扣除

    public BigDecimal def56; // 年终金其他扣除事项

    public BigDecimal def57; // 年终金其他合并计税项(计税不发)

    public BigDecimal def58; // 本次年终奖应计税金额

    public BigDecimal def59; // 上次年终奖累计税金额

    public BigDecimal def60; // 累计年终奖计税金额

    public BigDecimal def61; // 累计年终奖个税

    public BigDecimal def62; // 上次累计年终奖个税

    public BigDecimal def63; // 本次年终奖个税

    public String note; // 备注

    public BigDecimal oldId; // key

    public BigDecimal def64; // 残疾人减免税额（年终奖）

    public BigDecimal def65; // 本次年终奖应纳个税

    public BigDecimal def66; // 本次年终奖实发金额

    public BigDecimal def67; // 本次劳务费金额

    public BigDecimal def68; // 劳务费免税收入

    public BigDecimal def69; // 劳务费免税事项

    public BigDecimal def70; // 劳务费其他扣除

    public BigDecimal def71; // 劳务费其他扣除事项

    public BigDecimal def72; // 劳务费其他合并计税项(计税不发)

    public BigDecimal def73; // 本次劳务费应计税金额

    public BigDecimal def74; // 上次劳务费累计税金额

    public BigDecimal def75; // 累计劳务费计税金额

    public BigDecimal def76; // 累计劳务费个税

    public BigDecimal def77; // 上次累计劳务费个税

    public BigDecimal def78; // 本次劳务报酬个税

    public BigDecimal def79; // 残疾人减免税额（劳务费）

    public BigDecimal def80; // 本次劳务费应纳个税

    public BigDecimal def81; // 本次劳务报酬实发金额

    public BigDecimal def82; // 实习生劳务费金额

    public BigDecimal def83; // 实习生劳务费免税收入

    public BigDecimal def84; // 实习生劳务费免税事项

    public BigDecimal def85; // 实习生劳务费其他扣除

    public BigDecimal def86; // 实习生劳务费其他扣除事项

    public BigDecimal def87; // 实习生劳务费其他合并计税项(计税不发)

    public BigDecimal def88; // 实习生本月免税所得额

    public BigDecimal def89; // 本次其他劳务费应计税金额

    public BigDecimal def90; // 上次累计其他劳务费计税金额

    public BigDecimal def91; // 累计其他劳务费计税金额

    public BigDecimal def92; // 累计其他劳务费个税

    public BigDecimal def93; // 上次累计其他劳务费个税

    public BigDecimal def94; // 本次其他劳务费个税

    public BigDecimal def95; // 残疾人减免税额(其他劳务费)

    public BigDecimal def96; // 本次其他劳务费应纳个税

    public BigDecimal def97; // 本次其它劳务费实发金额

    public BigDecimal def98; // 离职补偿金金额

    public BigDecimal def99; // 离职补偿金免税收入

    public BigDecimal def100; // 离职补偿金免税事项

    public BigDecimal def101; // 离职补偿金其他扣除

    public BigDecimal def102; // 离职补偿金其他扣除事项

    public BigDecimal def103; // 离职补偿金其他合并计税项(计税不发)

    public BigDecimal def104; // 离职补偿金应计税金额

    public BigDecimal def105; // 离职补偿金免税金额

    public BigDecimal def106; // 离职补偿金计税金额

    public BigDecimal def107; // 离职补偿金个税

    public BigDecimal def108; // 残疾人减免税额（离职）

    public BigDecimal def109; // 离职补偿金应纳个税

    public BigDecimal def110; // 离职补偿金实发金额

    public BigDecimal def111; // 本次应发合计

    public BigDecimal def112; // 本次实发合计

    public BigDecimal def113; // 本次扣税合计

    public BigDecimal def114; // DEF114

    public BigDecimal def115; // DEF115

    public BigDecimal def116; // DEF116

    public BigDecimal def117; // DEF117

    public BigDecimal def118; // DEF118

    public BigDecimal def119; // DEF119

    public BigDecimal def120; // DEF120

    public BigDecimal def121; // DEF121

    public BigDecimal def122; // DEF122

    public BigDecimal def123; // DEF123

    public BigDecimal def124; // DEF124

    public BigDecimal def125; // DEF125

    public BigDecimal def126; // DEF126

    public BigDecimal def127; // DEF127

    public BigDecimal def128; // DEF128

    public BigDecimal def129; // DEF129

    public BigDecimal def130; // DEF130

    public BigDecimal def131; // DEF131

    public BigDecimal def132; // DEF132

    public BigDecimal def133; // DEF133

    public BigDecimal def134; // DEF134

    public BigDecimal def135; // DEF135

    public BigDecimal def136; // DEF136

    public BigDecimal def137; // DEF137

    public BigDecimal def138; // DEF138

    public BigDecimal def139; // DEF139

    public BigDecimal def140; // DEF140

    public BigDecimal def141; // DEF141

    public BigDecimal def142; // DEF142

    public BigDecimal def143; // DEF143

    public BigDecimal def144; // DEF144

    public BigDecimal def145; // DEF145

    public BigDecimal def146; // DEF146

    public BigDecimal def147; // DEF147

    public BigDecimal def148; // DEF148

    public BigDecimal def149; // DEF149

    public BigDecimal def150; // DEF150

    public String remarks; // 报税备注

    public List<SalaryArchivesInfo> salaryArchivesInfoList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public SalaryData() {
        super();
    }

    public SalaryData(String id) {
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

    @Length(min = 1, max = 19, message = "员工薪资明细id长度必须介于 1 和 19 之间")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 0, max = 19, message = "薪资批次发放ID长度必须介于 0 和 19 之间")
    public String getSaBatchId() {
        return saBatchId;
    }

    public void setSaBatchId(String saBatchId) {
        this.saBatchId = saBatchId;
    }

    @Length(min = 0, max = 19, message = "薪资类别ID长度必须介于 0 和 19 之间")
    public String getSaClassId() {
        return saClassId;
    }

    public void setSaClassId(String saClassId) {
        this.saClassId = saClassId;
    }

    public SalaryArchivesInfo getSaDoc() {
        return saDoc;
    }

    public void setSaDoc(SalaryArchivesInfo saDoc) {
        this.saDoc = saDoc;
    }

    @Length(min = 0, max = 19, message = "员工id长度必须介于 0 和 19 之间")
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Length(min = 0, max = 19, message = "部门ID长度必须介于 0 和 19 之间")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Length(min = 0, max = 100, message = "应发年月长度必须介于 0 和 100 之间")
    public String getPayYearMonth() {
        return payYearMonth;
    }

    public void setPayYearMonth(String payYearMonth) {
        this.payYearMonth = payYearMonth;
    }

    @Length(min = 0, max = 128, message = "停发标识：1：停发 0：正常长度必须介于 0 和 128 之间")
    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

    @Length(min = 0, max = 128, message = "是否数据确认　1：是　0：否长度必须介于 0 和 128 之间")
    public String getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(String isVerify) {
        this.isVerify = isVerify;
    }

    @Length(min = 0, max = 128, message = "是否删除 0:正常　1:删除长度必须介于 0 和 128 之间")
    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    @Length(min = 0, max = 128, message = "缓发标记 1：缓发，0：正常长度必须介于 0 和 128 之间")
    public String getIsPostpone() {
        return isPostpone;
    }

    public void setIsPostpone(String isPostpone) {
        this.isPostpone = isPostpone;
    }

    @Length(min = 0, max = 128, message = "是否发送Emial工资单：1：已发送 0：未发送长度必须介于 0 和 128 之间")
    public String getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(String isEmail) {
        this.isEmail = isEmail;
    }

    @Length(min = 0, max = -1, message = "发送Emia时间长度必须介于 0 和 -1 之间")
    public String getEmialTime() {
        return emialTime;
    }

    public void setEmialTime(String emialTime) {
        this.emialTime = emialTime;
    }

    @Length(min = 0, max = 100, message = "发送人长度必须介于 0 和 100 之间")
    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    @Length(min = 0, max = 2000, message = "Emial备注长度必须介于 0 和 2000 之间")
    public String getEmailRemarks() {
        return emailRemarks;
    }

    public void setEmailRemarks(String emailRemarks) {
        this.emailRemarks = emailRemarks;
    }

    public BigDecimal getDef1() {
        return def1 == null? new BigDecimal(0):def1;
    }

    public void setDef1(BigDecimal def1) {
        this.def1 = def1;
    }

    public BigDecimal getDef2() {
        return def2 == null ? new BigDecimal(0):def2;
    }

    public void setDef2(BigDecimal def2) {
        this.def2 = def2;
    }

    public BigDecimal getDef3() {
        return def3 == null ? new BigDecimal(0):def3;
    }

    public void setDef3(BigDecimal def3) {
        this.def3 = def3;
    }

    public BigDecimal getDef4() {
        return def4 == null ? new BigDecimal(0):def4;
    }

    public void setDef4(BigDecimal def4) {
        this.def4 = def4;
    }

    public BigDecimal getDef5() {
        return def5 == null ? new BigDecimal(0):def5;
    }

    public void setDef5(BigDecimal def5) {
        this.def5 = def5;
    }

    public BigDecimal getDef6() {
        return def6 == null ? new BigDecimal(0):def6;
    }

    public void setDef6(BigDecimal def6) {
        this.def6 = def6;
    }

    public BigDecimal getDef7() {
        return def7 == null ? new BigDecimal(0):def7;
    }

    public void setDef7(BigDecimal def7) {
        this.def7 = def7;
    }

    public BigDecimal getDef8() {
        return def8 == null ? new BigDecimal(0):def8;
    }

    public void setDef8(BigDecimal def8) {
        this.def8 = def8;
    }

    public BigDecimal getDef9() {
        return def9 == null ? new BigDecimal(0):def9;
    }

    public void setDef9(BigDecimal def9) {
        this.def9 = def9;
    }

    public BigDecimal getDef10() {
        return def10 == null ? new BigDecimal(0):def10;
    }

    public void setDef10(BigDecimal def10) {
        this.def10 = def10;
    }

    public BigDecimal getDef11() {
        return def11 == null ? new BigDecimal(0):def11;
    }

    public void setDef11(BigDecimal def11) {
        this.def11 = def11;
    }

    public BigDecimal getDef12() {
        return def12 == null ? new BigDecimal(0):def12;
    }

    public void setDef12(BigDecimal def12) {
        this.def12 = def12;
    }

    public BigDecimal getDef13() {
        return def13 == null ? new BigDecimal(0):def13;
    }

    public void setDef13(BigDecimal def13) {
        this.def13 = def13;
    }

    public BigDecimal getDef14() {
        return def14 == null ? new BigDecimal(0):def14;
    }

    public void setDef14(BigDecimal def14) {
        this.def14 = def14;
    }

    public BigDecimal getDef15() {
        return def15 == null ? new BigDecimal(0):def15;
    }

    public void setDef15(BigDecimal def15) {
        this.def15 = def15;
    }

    public BigDecimal getDef16() {
        return def16 == null ? new BigDecimal(0):def16;
    }

    public void setDef16(BigDecimal def16) {
        this.def16 = def16;
    }

    public BigDecimal getDef17() {
        return def17 == null ? new BigDecimal(0):def17;
    }

    public void setDef17(BigDecimal def17) {
        this.def17 = def17;
    }

    public BigDecimal getDef18() {
        return def18 == null ? new BigDecimal(0):def18;
    }

    public void setDef18(BigDecimal def18) {
        this.def18 = def18;
    }

    public BigDecimal getDef19() {
        return def19 == null ? new BigDecimal(0):def19;
    }

    public void setDef19(BigDecimal def19) {
        this.def19 = def19;
    }

    public BigDecimal getDef20() {
        return def20 == null ? new BigDecimal(0):def20;
    }

    public void setDef20(BigDecimal def20) {
        this.def20 = def20;
    }

    public BigDecimal getDef21() {
        return def21 == null ? new BigDecimal(0):def21;
    }

    public void setDef21(BigDecimal def21) {
        this.def21 = def21;
    }

    public BigDecimal getDef22() {
        return def22 == null ? new BigDecimal(0):def22;
    }

    public void setDef22(BigDecimal def22) {
        this.def22 = def22;
    }

    public BigDecimal getDef23() {
        return def23 == null ? new BigDecimal(0):def23;
    }

    public void setDef23(BigDecimal def23) {
        this.def23 = def23;
    }

    public BigDecimal getDef24() {
        return def24 == null ? new BigDecimal(0):def24;
    }

    public void setDef24(BigDecimal def24) {
        this.def24 = def24;
    }

    public BigDecimal getDef25() {
        return def25 == null ? new BigDecimal(0):def25;
    }

    public void setDef25(BigDecimal def25) {
        this.def25 = def25;
    }

    public BigDecimal getDef26() {
        return def26 == null ? new BigDecimal(0):def26;
    }

    public void setDef26(BigDecimal def26) {
        this.def26 = def26;
    }

    public BigDecimal getDef27() {
        return def27 == null ? new BigDecimal(0):def27;
    }

    public void setDef27(BigDecimal def27) {
        this.def27 = def27;
    }

    public BigDecimal getDef29() {
        return def29 == null ? new BigDecimal(0):def29;
    }

    public void setDef29(BigDecimal def29) {
        this.def29 = def29;
    }

    public BigDecimal getDef28() {
        return def28 == null ? new BigDecimal(0):def28;
    }

    public void setDef28(BigDecimal def28) {
        this.def28 = def28;
    }

    public BigDecimal getDef30() {
        return def30 == null ? new BigDecimal(0):def30;
    }

    public void setDef30(BigDecimal def30) {
        this.def30 = def30;
    }

    public BigDecimal getDef31() {
        return def31 == null ? new BigDecimal(0):def31;
    }

    public void setDef31(BigDecimal def31) {
        this.def31 = def31;
    }

    public BigDecimal getDef32() {
        return def32 == null ? new BigDecimal(0):def32;
    }

    public void setDef32(BigDecimal def32) {
        this.def32 = def32;
    }

    public BigDecimal getDef33() {
        return def33;
    }

    public void setDef33(BigDecimal def33) {
        this.def33 = def33;
    }

    public BigDecimal getDef34() {
        return def34;
    }

    public void setDef34(BigDecimal def34) {
        this.def34 = def34;
    }

    public BigDecimal getDef35() {
        return def35;
    }

    public void setDef35(BigDecimal def35) {
        this.def35 = def35;
    }

    public BigDecimal getDef36() {
        return def36;
    }

    public void setDef36(BigDecimal def36) {
        this.def36 = def36;
    }

    public BigDecimal getDef37() {
        return def37;
    }

    public void setDef37(BigDecimal def37) {
        this.def37 = def37;
    }

    public BigDecimal getDef38() {
        return def38;
    }

    public void setDef38(BigDecimal def38) {
        this.def38 = def38;
    }

    public BigDecimal getDef39() {
        return def39;
    }

    public void setDef39(BigDecimal def39) {
        this.def39 = def39;
    }

    public BigDecimal getDef40() {
        return def40;
    }

    public void setDef40(BigDecimal def40) {
        this.def40 = def40;
    }

    public BigDecimal getDef41() {
        return def41;
    }

    public void setDef41(BigDecimal def41) {
        this.def41 = def41;
    }

    public BigDecimal getDef42() {
        return def42;
    }

    public void setDef42(BigDecimal def42) {
        this.def42 = def42;
    }

    public BigDecimal getDef43() {
        return def43;
    }

    public void setDef43(BigDecimal def43) {
        this.def43 = def43;
    }

    public BigDecimal getDef44() {
        return def44;
    }

    public void setDef44(BigDecimal def44) {
        this.def44 = def44;
    }

    public BigDecimal getDef45() {
        return def45;
    }

    public void setDef45(BigDecimal def45) {
        this.def45 = def45;
    }

    @Length(min = 0, max = 128, message = "工资报税属性：0:发工资且报税 1:只发工资 2:只报税长度必须介于 0 和 128 之间")
    public String getSalaryTaxBd() {
        return salaryTaxBd;
    }

    public void setSalaryTaxBd(String salaryTaxBd) {
        this.salaryTaxBd = salaryTaxBd;
    }

    @Length(min = 0, max = 128, message = "是否本地发放：1：是　0：否长度必须介于 0 和 128 之间")
    public String getIsLocalPay() {
        return isLocalPay;
    }

    public void setIsLocalPay(String isLocalPay) {
        this.isLocalPay = isLocalPay;
    }

    @Length(min = 0, max = 128, message = "报税属性：0-不报税 1-大户 2-单户自行报税 3-单户代报税长度必须介于 0 和 128 之间")
    public String getTaxFlag() {
        return taxFlag;
    }

    public void setTaxFlag(String taxFlag) {
        this.taxFlag = taxFlag;
    }

    @Length(min = 0, max = 128, message = "是否有效 1:有效 0:无效长度必须介于 0 和 128 之间")
    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    @Length(min = 0, max = 128, message = "报税区间：1:：报当月 0：报上月长度必须介于 0 和 128 之间")
    public String getTaxRegionBd() {
        return taxRegionBd;
    }

    public void setTaxRegionBd(String taxRegionBd) {
        this.taxRegionBd = taxRegionBd;
    }

    public BigDecimal getDef46() {
        return def46;
    }

    public void setDef46(BigDecimal def46) {
        this.def46 = def46;
    }

    public BigDecimal getDef47() {
        return def47;
    }

    public void setDef47(BigDecimal def47) {
        this.def47 = def47;
    }

    public BigDecimal getDef48() {
        return def48;
    }

    public void setDef48(BigDecimal def48) {
        this.def48 = def48;
    }

    public BigDecimal getDef49() {
        return def49;
    }

    public void setDef49(BigDecimal def49) {
        this.def49 = def49;
    }

    public BigDecimal getDef50() {
        return def50;
    }

    public void setDef50(BigDecimal def50) {
        this.def50 = def50;
    }

    public BigDecimal getDef51() {
        return def51;
    }

    public void setDef51(BigDecimal def51) {
        this.def51 = def51;
    }

    public BigDecimal getDef52() {
        return def52;
    }

    public void setDef52(BigDecimal def52) {
        this.def52 = def52;
    }

    public BigDecimal getDef53() {
        return def53;
    }

    public void setDef53(BigDecimal def53) {
        this.def53 = def53;
    }

    public BigDecimal getDef54() {
        return def54;
    }

    public void setDef54(BigDecimal def54) {
        this.def54 = def54;
    }

    public BigDecimal getDef55() {
        return def55;
    }

    public void setDef55(BigDecimal def55) {
        this.def55 = def55;
    }

    public BigDecimal getDef56() {
        return def56;
    }

    public void setDef56(BigDecimal def56) {
        this.def56 = def56;
    }

    public BigDecimal getDef57() {
        return def57;
    }

    public void setDef57(BigDecimal def57) {
        this.def57 = def57;
    }

    public BigDecimal getDef58() {
        return def58;
    }

    public void setDef58(BigDecimal def58) {
        this.def58 = def58;
    }

    public BigDecimal getDef59() {
        return def59;
    }

    public void setDef59(BigDecimal def59) {
        this.def59 = def59;
    }

    public BigDecimal getDef60() {
        return def60;
    }

    public void setDef60(BigDecimal def60) {
        this.def60 = def60;
    }

    public BigDecimal getDef61() {
        return def61;
    }

    public void setDef61(BigDecimal def61) {
        this.def61 = def61;
    }

    public BigDecimal getDef62() {
        return def62;
    }

    public void setDef62(BigDecimal def62) {
        this.def62 = def62;
    }

    public BigDecimal getDef63() {
        return def63;
    }

    public void setDef63(BigDecimal def63) {
        this.def63 = def63;
    }

    @Length(min = 0, max = 4000, message = "备注长度必须介于 0 和 4000 之间")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getOldId() {
        return oldId;
    }

    public void setOldId(BigDecimal oldId) {
        this.oldId = oldId;
    }

    public BigDecimal getDef64() {
        return def64;
    }

    public void setDef64(BigDecimal def64) {
        this.def64 = def64;
    }

    public BigDecimal getDef65() {
        return def65;
    }

    public void setDef65(BigDecimal def65) {
        this.def65 = def65;
    }

    public BigDecimal getDef66() {
        return def66;
    }

    public void setDef66(BigDecimal def66) {
        this.def66 = def66;
    }

    public BigDecimal getDef67() {
        return def67;
    }

    public void setDef67(BigDecimal def67) {
        this.def67 = def67;
    }

    public BigDecimal getDef68() {
        return def68;
    }

    public void setDef68(BigDecimal def68) {
        this.def68 = def68;
    }

    public BigDecimal getDef69() {
        return def69;
    }

    public void setDef69(BigDecimal def69) {
        this.def69 = def69;
    }

    public BigDecimal getDef70() {
        return def70;
    }

    public void setDef70(BigDecimal def70) {
        this.def70 = def70;
    }

    public BigDecimal getDef71() {
        return def71;
    }

    public void setDef71(BigDecimal def71) {
        this.def71 = def71;
    }

    public BigDecimal getDef72() {
        return def72;
    }

    public void setDef72(BigDecimal def72) {
        this.def72 = def72;
    }

    public BigDecimal getDef73() {
        return def73;
    }

    public void setDef73(BigDecimal def73) {
        this.def73 = def73;
    }

    public BigDecimal getDef74() {
        return def74;
    }

    public void setDef74(BigDecimal def74) {
        this.def74 = def74;
    }

    public BigDecimal getDef75() {
        return def75;
    }

    public void setDef75(BigDecimal def75) {
        this.def75 = def75;
    }

    public BigDecimal getDef76() {
        return def76;
    }

    public void setDef76(BigDecimal def76) {
        this.def76 = def76;
    }

    public BigDecimal getDef77() {
        return def77;
    }

    public void setDef77(BigDecimal def77) {
        this.def77 = def77;
    }

    public BigDecimal getDef78() {
        return def78;
    }

    public void setDef78(BigDecimal def78) {
        this.def78 = def78;
    }

    public BigDecimal getDef79() {
        return def79;
    }

    public void setDef79(BigDecimal def79) {
        this.def79 = def79;
    }

    public BigDecimal getDef80() {
        return def80;
    }

    public void setDef80(BigDecimal def80) {
        this.def80 = def80;
    }

    public BigDecimal getDef81() {
        return def81;
    }

    public void setDef81(BigDecimal def81) {
        this.def81 = def81;
    }

    public BigDecimal getDef82() {
        return def82;
    }

    public void setDef82(BigDecimal def82) {
        this.def82 = def82;
    }

    public BigDecimal getDef83() {
        return def83;
    }

    public void setDef83(BigDecimal def83) {
        this.def83 = def83;
    }

    public BigDecimal getDef84() {
        return def84;
    }

    public void setDef84(BigDecimal def84) {
        this.def84 = def84;
    }

    public BigDecimal getDef85() {
        return def85;
    }

    public void setDef85(BigDecimal def85) {
        this.def85 = def85;
    }

    public BigDecimal getDef86() {
        return def86;
    }

    public void setDef86(BigDecimal def86) {
        this.def86 = def86;
    }

    public BigDecimal getDef87() {
        return def87;
    }

    public void setDef87(BigDecimal def87) {
        this.def87 = def87;
    }

    public BigDecimal getDef88() {
        return def88;
    }

    public void setDef88(BigDecimal def88) {
        this.def88 = def88;
    }

    public BigDecimal getDef89() {
        return def89;
    }

    public void setDef89(BigDecimal def89) {
        this.def89 = def89;
    }

    public BigDecimal getDef90() {
        return def90;
    }

    public void setDef90(BigDecimal def90) {
        this.def90 = def90;
    }

    public BigDecimal getDef91() {
        return def91;
    }

    public void setDef91(BigDecimal def91) {
        this.def91 = def91;
    }

    public BigDecimal getDef92() {
        return def92;
    }

    public void setDef92(BigDecimal def92) {
        this.def92 = def92;
    }

    public BigDecimal getDef93() {
        return def93;
    }

    public void setDef93(BigDecimal def93) {
        this.def93 = def93;
    }

    public BigDecimal getDef94() {
        return def94;
    }

    public void setDef94(BigDecimal def94) {
        this.def94 = def94;
    }

    public BigDecimal getDef95() {
        return def95;
    }

    public void setDef95(BigDecimal def95) {
        this.def95 = def95;
    }

    public BigDecimal getDef96() {
        return def96;
    }

    public void setDef96(BigDecimal def96) {
        this.def96 = def96;
    }

    public BigDecimal getDef97() {
        return def97;
    }

    public void setDef97(BigDecimal def97) {
        this.def97 = def97;
    }

    public BigDecimal getDef98() {
        return def98;
    }

    public void setDef98(BigDecimal def98) {
        this.def98 = def98;
    }

    public BigDecimal getDef99() {
        return def99;
    }

    public void setDef99(BigDecimal def99) {
        this.def99 = def99;
    }

    public BigDecimal getDef100() {
        return def100;
    }

    public void setDef100(BigDecimal def100) {
        this.def100 = def100;
    }

    public BigDecimal getDef101() {
        return def101;
    }

    public void setDef101(BigDecimal def101) {
        this.def101 = def101;
    }

    public BigDecimal getDef102() {
        return def102;
    }

    public void setDef102(BigDecimal def102) {
        this.def102 = def102;
    }

    public BigDecimal getDef103() {
        return def103;
    }

    public void setDef103(BigDecimal def103) {
        this.def103 = def103;
    }

    public BigDecimal getDef104() {
        return def104;
    }

    public void setDef104(BigDecimal def104) {
        this.def104 = def104;
    }

    public BigDecimal getDef105() {
        return def105;
    }

    public void setDef105(BigDecimal def105) {
        this.def105 = def105;
    }

    public BigDecimal getDef106() {
        return def106;
    }

    public void setDef106(BigDecimal def106) {
        this.def106 = def106;
    }

    public BigDecimal getDef107() {
        return def107;
    }

    public void setDef107(BigDecimal def107) {
        this.def107 = def107;
    }

    public BigDecimal getDef108() {
        return def108;
    }

    public void setDef108(BigDecimal def108) {
        this.def108 = def108;
    }

    public BigDecimal getDef109() {
        return def109;
    }

    public void setDef109(BigDecimal def109) {
        this.def109 = def109;
    }

    public BigDecimal getDef110() {
        return def110;
    }

    public void setDef110(BigDecimal def110) {
        this.def110 = def110;
    }

    public BigDecimal getDef111() {
        return def111;
    }

    public void setDef111(BigDecimal def111) {
        this.def111 = def111;
    }

    public BigDecimal getDef112() {
        return def112;
    }

    public void setDef112(BigDecimal def112) {
        this.def112 = def112;
    }

    public BigDecimal getDef113() {
        return def113;
    }

    public void setDef113(BigDecimal def113) {
        this.def113 = def113;
    }

    public BigDecimal getDef114() {
        return def114;
    }

    public void setDef114(BigDecimal def114) {
        this.def114 = def114;
    }

    public BigDecimal getDef115() {
        return def115;
    }

    public void setDef115(BigDecimal def115) {
        this.def115 = def115;
    }

    public BigDecimal getDef116() {
        return def116;
    }

    public void setDef116(BigDecimal def116) {
        this.def116 = def116;
    }

    public BigDecimal getDef117() {
        return def117;
    }

    public void setDef117(BigDecimal def117) {
        this.def117 = def117;
    }

    public BigDecimal getDef118() {
        return def118;
    }

    public void setDef118(BigDecimal def118) {
        this.def118 = def118;
    }

    public BigDecimal getDef119() {
        return def119;
    }

    public void setDef119(BigDecimal def119) {
        this.def119 = def119;
    }

    public BigDecimal getDef120() {
        return def120;
    }

    public void setDef120(BigDecimal def120) {
        this.def120 = def120;
    }

    public BigDecimal getDef121() {
        return def121;
    }

    public void setDef121(BigDecimal def121) {
        this.def121 = def121;
    }

    public BigDecimal getDef122() {
        return def122;
    }

    public void setDef122(BigDecimal def122) {
        this.def122 = def122;
    }

    public BigDecimal getDef123() {
        return def123;
    }

    public void setDef123(BigDecimal def123) {
        this.def123 = def123;
    }

    public BigDecimal getDef124() {
        return def124;
    }

    public void setDef124(BigDecimal def124) {
        this.def124 = def124;
    }

    public BigDecimal getDef125() {
        return def125;
    }

    public void setDef125(BigDecimal def125) {
        this.def125 = def125;
    }

    public BigDecimal getDef126() {
        return def126;
    }

    public void setDef126(BigDecimal def126) {
        this.def126 = def126;
    }

    public BigDecimal getDef127() {
        return def127;
    }

    public void setDef127(BigDecimal def127) {
        this.def127 = def127;
    }

    public BigDecimal getDef128() {
        return def128;
    }

    public void setDef128(BigDecimal def128) {
        this.def128 = def128;
    }

    public BigDecimal getDef129() {
        return def129;
    }

    public void setDef129(BigDecimal def129) {
        this.def129 = def129;
    }

    public BigDecimal getDef130() {
        return def130;
    }

    public void setDef130(BigDecimal def130) {
        this.def130 = def130;
    }

    public BigDecimal getDef131() {
        return def131;
    }

    public void setDef131(BigDecimal def131) {
        this.def131 = def131;
    }

    public BigDecimal getDef132() {
        return def132;
    }

    public void setDef132(BigDecimal def132) {
        this.def132 = def132;
    }

    public BigDecimal getDef133() {
        return def133;
    }

    public void setDef133(BigDecimal def133) {
        this.def133 = def133;
    }

    public BigDecimal getDef134() {
        return def134;
    }

    public void setDef134(BigDecimal def134) {
        this.def134 = def134;
    }

    public BigDecimal getDef135() {
        return def135;
    }

    public void setDef135(BigDecimal def135) {
        this.def135 = def135;
    }

    public BigDecimal getDef136() {
        return def136;
    }

    public void setDef136(BigDecimal def136) {
        this.def136 = def136;
    }

    public BigDecimal getDef137() {
        return def137;
    }

    public void setDef137(BigDecimal def137) {
        this.def137 = def137;
    }

    public BigDecimal getDef138() {
        return def138;
    }

    public void setDef138(BigDecimal def138) {
        this.def138 = def138;
    }

    public BigDecimal getDef139() {
        return def139;
    }

    public void setDef139(BigDecimal def139) {
        this.def139 = def139;
    }

    public BigDecimal getDef140() {
        return def140;
    }

    public void setDef140(BigDecimal def140) {
        this.def140 = def140;
    }

    public BigDecimal getDef141() {
        return def141;
    }

    public void setDef141(BigDecimal def141) {
        this.def141 = def141;
    }

    public BigDecimal getDef142() {
        return def142;
    }

    public void setDef142(BigDecimal def142) {
        this.def142 = def142;
    }

    public BigDecimal getDef143() {
        return def143;
    }

    public void setDef143(BigDecimal def143) {
        this.def143 = def143;
    }

    public BigDecimal getDef144() {
        return def144;
    }

    public void setDef144(BigDecimal def144) {
        this.def144 = def144;
    }

    public BigDecimal getDef145() {
        return def145;
    }

    public void setDef145(BigDecimal def145) {
        this.def145 = def145;
    }

    public BigDecimal getDef146() {
        return def146;
    }

    public void setDef146(BigDecimal def146) {
        this.def146 = def146;
    }

    public BigDecimal getDef147() {
        return def147;
    }

    public void setDef147(BigDecimal def147) {
        this.def147 = def147;
    }

    public BigDecimal getDef148() {
        return def148;
    }

    public void setDef148(BigDecimal def148) {
        this.def148 = def148;
    }

    public BigDecimal getDef149() {
        return def149;
    }

    public void setDef149(BigDecimal def149) {
        this.def149 = def149;
    }

    public BigDecimal getDef150() {
        return def150;
    }

    public void setDef150(BigDecimal def150) {
        this.def150 = def150;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SalaryArchivesInfo> getSalaryArchivesInfoList() {
        return salaryArchivesInfoList;
    }

    public void setSalaryArchivesInfoList(List<SalaryArchivesInfo> salaryArchivesInfoList) {
        this.salaryArchivesInfoList = salaryArchivesInfoList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "salary_data";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1683007003934705771";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "薪资明细";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return false;
    }
}
