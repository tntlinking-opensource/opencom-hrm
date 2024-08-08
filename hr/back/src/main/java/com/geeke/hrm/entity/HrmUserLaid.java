package com.geeke.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.org.entity.Company;
import com.geeke.org.entity.Department;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 离职申请Entity
 * @author
 * @version
 */
public class HrmUserLaid extends WfDataEntity<HrmUserLaid> {

    private static final long serialVersionUID = 1457254743943433245L;

    private String name; // name

    private HrmLaidType laidtype; // 离职类型。0-个人原因，1-工作性质2-公司平台3-人际关系4-内部待遇

    private HrmLaidValue laidmaincause; // 离职主要原因

    private String laidcause; // 离职原因

    private String handingname; // 交接人姓名

    private User handinguserid; // 交接人id

    private String laidapplydate; // 离职申请日期

    private String lastworkdate; // 最后工作日

    private String laiddealdate; // 离职办理日期

    private String research1; // 离职调研0-完全同意1-同意2不知道3不同意4完全反对

    private String research2; // research2

    private String research3; // research3

    private String research4; // research4

    private String research5; // research5

    private String research6; // research6

    private String research7; // research7

    private String research8; // research8

    private String research9; // research9

    private String research10; // research10

    private String research11; // research11

    private String research12; // research12

    private String research13; // research13

    private String research14; // research14

    private String research15; // research15

    private User user; // 申请人

    private String handingstatus; // 交接状态

    private String laidstatus; // 离职状态

    private String adminaffairsstatus; // 行政事务办理

    private String financeaffairsstatus; // 财务事务办理

    private String humanaffairsstatus; // 人事办理

    private String fileid; // 离职详情单文件id

    private String equipment; // 设备是否归还 0-未损坏1-已损坏

    private String equipmentmoney; // 设备损坏扣款

    private String card; // 胸卡是否归还0-未归还1-已归还

    private String cardmoney; // 胸卡归还扣款

    private String adminsign; // 行政签名

    private String admindate; // 行政签名日期

    private String borrow; // 是否有个人借款0-没有1-有

    private String borrowmoney; // 个人借款金额

    private String financesign; // 财务事务签名

    private String financedate; // 财务签名日期

    private String laidtalk; // 离职面谈，0-无1-有

    private String falsify; // 违约金，0-无1-有

    private String falsifymoney; // 违约金金额

    private String other; // 其他退款0-无1-有

    private String othermoney; // 其他退款金额

    private String damages; // 经济补偿 0-无 1-有

    private String annuity; // 经济补偿

    private String humansign; // 人事确认签名

    private String humandate; // 人事签名日期

    private String lastsocial; // 最后社保缴纳日期

    private String lastsalary; // 最后工资发放日期

    private String handingremark; // 交接人备注

    private String applyfileid; // 离职申请单文件id

    private String finalremark; // 离职确认备注

    // 构造方法
    public HrmUserLaid() {
        super();
    }

    public HrmUserLaid(String id) {
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

    public HrmLaidType getLaidtype() {
        return laidtype;
    }

    public void setLaidtype(HrmLaidType laidtype) {
        this.laidtype = laidtype;
    }

    public HrmLaidValue getLaidmaincause() {
        return laidmaincause;
    }

    public void setLaidmaincause(HrmLaidValue laidmaincause) {
        this.laidmaincause = laidmaincause;
    }

    @Length(min = 0, max = 256, message = "离职原因长度必须介于 0 和 256 之间")
    public String getLaidcause() {
        return laidcause;
    }

    public void setLaidcause(String laidcause) {
        this.laidcause = laidcause;
    }

    @Length(min = 0, max = 100, message = "交接人姓名长度必须介于 0 和 100 之间")
    public String getHandingname() {
        return handingname;
    }

    public void setHandingname(String handingname) {
        this.handingname = handingname;
    }

    public User getHandinguserid() {
        return handinguserid;
    }

    public void setHandinguserid(User handinguserid) {
        this.handinguserid = handinguserid;
    }

    @Length(min = 0, max = -1, message = "离职申请日期长度必须介于 0 和 -1 之间")
    public String getLaidapplydate() {
        return laidapplydate;
    }

    public void setLaidapplydate(String laidapplydate) {
        this.laidapplydate = laidapplydate;
    }

    @Length(min = 0, max = -1, message = "最后工作日长度必须介于 0 和 -1 之间")
    public String getLastworkdate() {
        return lastworkdate;
    }

    public void setLastworkdate(String lastworkdate) {
        this.lastworkdate = lastworkdate;
    }

    @Length(min = 0, max = -1, message = "离职办理日期长度必须介于 0 和 -1 之间")
    public String getLaiddealdate() {
        return laiddealdate;
    }

    public void setLaiddealdate(String laiddealdate) {
        this.laiddealdate = laiddealdate;
    }

    @Length(min = 0, max = 21, message = "离职调研0-完全同意1-同意2不知道3不同意4完全反对长度必须介于 0 和 21 之间")
    public String getResearch1() {
        return research1;
    }

    public void setResearch1(String research1) {
        this.research1 = research1;
    }

    @Length(min = 0, max = 21, message = "research2长度必须介于 0 和 21 之间")
    public String getResearch2() {
        return research2;
    }

    public void setResearch2(String research2) {
        this.research2 = research2;
    }

    @Length(min = 0, max = 21, message = "research3长度必须介于 0 和 21 之间")
    public String getResearch3() {
        return research3;
    }

    public void setResearch3(String research3) {
        this.research3 = research3;
    }

    @Length(min = 0, max = 21, message = "research4长度必须介于 0 和 21 之间")
    public String getResearch4() {
        return research4;
    }

    public void setResearch4(String research4) {
        this.research4 = research4;
    }

    @Length(min = 0, max = 21, message = "research5长度必须介于 0 和 21 之间")
    public String getResearch5() {
        return research5;
    }

    public void setResearch5(String research5) {
        this.research5 = research5;
    }

    @Length(min = 0, max = 21, message = "research6长度必须介于 0 和 21 之间")
    public String getResearch6() {
        return research6;
    }

    public void setResearch6(String research6) {
        this.research6 = research6;
    }

    @Length(min = 0, max = 21, message = "research7长度必须介于 0 和 21 之间")
    public String getResearch7() {
        return research7;
    }

    public void setResearch7(String research7) {
        this.research7 = research7;
    }

    @Length(min = 0, max = 21, message = "research8长度必须介于 0 和 21 之间")
    public String getResearch8() {
        return research8;
    }

    public void setResearch8(String research8) {
        this.research8 = research8;
    }

    @Length(min = 0, max = 21, message = "research9长度必须介于 0 和 21 之间")
    public String getResearch9() {
        return research9;
    }

    public void setResearch9(String research9) {
        this.research9 = research9;
    }

    @Length(min = 0, max = 21, message = "research10长度必须介于 0 和 21 之间")
    public String getResearch10() {
        return research10;
    }

    public void setResearch10(String research10) {
        this.research10 = research10;
    }

    @Length(min = 0, max = 21, message = "research11长度必须介于 0 和 21 之间")
    public String getResearch11() {
        return research11;
    }

    public void setResearch11(String research11) {
        this.research11 = research11;
    }

    @Length(min = 0, max = 21, message = "research12长度必须介于 0 和 21 之间")
    public String getResearch12() {
        return research12;
    }

    public void setResearch12(String research12) {
        this.research12 = research12;
    }

    @Length(min = 0, max = 21, message = "research13长度必须介于 0 和 21 之间")
    public String getResearch13() {
        return research13;
    }

    public void setResearch13(String research13) {
        this.research13 = research13;
    }

    @Length(min = 0, max = 21, message = "research14长度必须介于 0 和 21 之间")
    public String getResearch14() {
        return research14;
    }

    public void setResearch14(String research14) {
        this.research14 = research14;
    }

    @Length(min = 0, max = 21, message = "research15长度必须介于 0 和 21 之间")
    public String getResearch15() {
        return research15;
    }

    public void setResearch15(String research15) {
        this.research15 = research15;
    }

    @NotNull(message = "申请人不能为空")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Length(min = 0, max = 100, message = "交接状态长度必须介于 0 和 100 之间")
    public String getHandingstatus() {
        return handingstatus;
    }

    public void setHandingstatus(String handingstatus) {
        this.handingstatus = handingstatus;
    }

    @Length(min = 0, max = 100, message = "离职状态长度必须介于 0 和 100 之间")
    public String getLaidstatus() {
        return laidstatus;
    }

    public void setLaidstatus(String laidstatus) {
        this.laidstatus = laidstatus;
    }

    @Length(min = 0, max = 100, message = "行政事务办理长度必须介于 0 和 100 之间")
    public String getAdminaffairsstatus() {
        return adminaffairsstatus;
    }

    public void setAdminaffairsstatus(String adminaffairsstatus) {
        this.adminaffairsstatus = adminaffairsstatus;
    }

    @Length(min = 0, max = 100, message = "财务事务办理长度必须介于 0 和 100 之间")
    public String getFinanceaffairsstatus() {
        return financeaffairsstatus;
    }

    public void setFinanceaffairsstatus(String financeaffairsstatus) {
        this.financeaffairsstatus = financeaffairsstatus;
    }

    @Length(min = 0, max = 100, message = "人事办理长度必须介于 0 和 100 之间")
    public String getHumanaffairsstatus() {
        return humanaffairsstatus;
    }

    public void setHumanaffairsstatus(String humanaffairsstatus) {
        this.humanaffairsstatus = humanaffairsstatus;
    }

    @Length(min = 0, max = 100, message = "离职详情单文件id长度必须介于 0 和 100 之间")
    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    @Length(min = 0, max = 100, message = "设备是否归还 0-未损坏1-已损坏长度必须介于 0 和 100 之间")
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    @Length(min = 0, max = 100, message = "设备损坏扣款长度必须介于 0 和 100 之间")
    public String getEquipmentmoney() {
        return equipmentmoney;
    }

    public void setEquipmentmoney(String equipmentmoney) {
        this.equipmentmoney = equipmentmoney;
    }

    @Length(min = 0, max = 100, message = "胸卡是否归还0-未归还1-已归还长度必须介于 0 和 100 之间")
    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    @Length(min = 0, max = 100, message = "胸卡归还扣款长度必须介于 0 和 100 之间")
    public String getCardmoney() {
        return cardmoney;
    }

    public void setCardmoney(String cardmoney) {
        this.cardmoney = cardmoney;
    }

    @Length(min = 0, max = 100, message = "行政签名长度必须介于 0 和 100 之间")
    public String getAdminsign() {
        return adminsign;
    }

    public void setAdminsign(String adminsign) {
        this.adminsign = adminsign;
    }

    @Length(min = 0, max = 100, message = "行政签名日期长度必须介于 0 和 100 之间")
    public String getAdmindate() {
        return admindate;
    }

    public void setAdmindate(String admindate) {
        this.admindate = admindate;
    }

    @Length(min = 0, max = 100, message = "是否有个人借款0-没有1-有长度必须介于 0 和 100 之间")
    public String getBorrow() {
        return borrow;
    }

    public void setBorrow(String borrow) {
        this.borrow = borrow;
    }

    @Length(min = 0, max = 100, message = "个人借款金额长度必须介于 0 和 100 之间")
    public String getBorrowmoney() {
        return borrowmoney;
    }

    public void setBorrowmoney(String borrowmoney) {
        this.borrowmoney = borrowmoney;
    }

    @Length(min = 0, max = 100, message = "财务事务签名长度必须介于 0 和 100 之间")
    public String getFinancesign() {
        return financesign;
    }

    public void setFinancesign(String financesign) {
        this.financesign = financesign;
    }

    @Length(min = 0, max = 100, message = "财务签名日期长度必须介于 0 和 100 之间")
    public String getFinancedate() {
        return financedate;
    }

    public void setFinancedate(String financedate) {
        this.financedate = financedate;
    }

    @Length(min = 0, max = 100, message = "离职面谈，0-无1-有长度必须介于 0 和 100 之间")
    public String getLaidtalk() {
        return laidtalk;
    }

    public void setLaidtalk(String laidtalk) {
        this.laidtalk = laidtalk;
    }

    @Length(min = 0, max = 100, message = "违约金，0-无1-有长度必须介于 0 和 100 之间")
    public String getFalsify() {
        return falsify;
    }

    public void setFalsify(String falsify) {
        this.falsify = falsify;
    }

    @Length(min = 0, max = 100, message = "违约金金额长度必须介于 0 和 100 之间")
    public String getFalsifymoney() {
        return falsifymoney;
    }

    public void setFalsifymoney(String falsifymoney) {
        this.falsifymoney = falsifymoney;
    }

    @Length(min = 0, max = 100, message = "其他退款0-无1-有长度必须介于 0 和 100 之间")
    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Length(min = 0, max = 100, message = "其他退款金额长度必须介于 0 和 100 之间")
    public String getOthermoney() {
        return othermoney;
    }

    public void setOthermoney(String othermoney) {
        this.othermoney = othermoney;
    }

    @Length(min = 0, max = 100, message = "经济补偿 0-无 1-有长度必须介于 0 和 100 之间")
    public String getDamages() {
        return damages;
    }

    public void setDamages(String damages) {
        this.damages = damages;
    }

    @Length(min = 0, max = 100, message = "经济补偿长度必须介于 0 和 100 之间")
    public String getAnnuity() {
        return annuity;
    }

    public void setAnnuity(String annuity) {
        this.annuity = annuity;
    }

    @Length(min = 0, max = 100, message = "人事确认签名长度必须介于 0 和 100 之间")
    public String getHumansign() {
        return humansign;
    }

    public void setHumansign(String humansign) {
        this.humansign = humansign;
    }

    @Length(min = 0, max = 100, message = "人事签名日期长度必须介于 0 和 100 之间")
    public String getHumandate() {
        return humandate;
    }

    public void setHumandate(String humandate) {
        this.humandate = humandate;
    }

    @Length(min = 0, max = 100, message = "最后社保缴纳日期长度必须介于 0 和 100 之间")
    public String getLastsocial() {
        return lastsocial;
    }

    public void setLastsocial(String lastsocial) {
        this.lastsocial = lastsocial;
    }

    @Length(min = 0, max = 100, message = "最后工资发放日期长度必须介于 0 和 100 之间")
    public String getLastsalary() {
        return lastsalary;
    }

    public void setLastsalary(String lastsalary) {
        this.lastsalary = lastsalary;
    }

    @Length(min = 0, max = 256, message = "交接人备注长度必须介于 0 和 256 之间")
    public String getHandingremark() {
        return handingremark;
    }

    public void setHandingremark(String handingremark) {
        this.handingremark = handingremark;
    }

    @Length(min = 0, max = 100, message = "离职申请单文件id长度必须介于 0 和 100 之间")
    public String getApplyfileid() {
        return applyfileid;
    }

    public void setApplyfileid(String applyfileid) {
        this.applyfileid = applyfileid;
    }

    @Length(min = 0, max = 100, message = "离职确认备注长度必须介于 0 和 100 之间")
    public String getFinalremark() {
        return finalremark;
    }

    public void setFinalremark(String finalremark) {
        this.finalremark = finalremark;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "hrm_user_laid";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433245";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "离职申请";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
