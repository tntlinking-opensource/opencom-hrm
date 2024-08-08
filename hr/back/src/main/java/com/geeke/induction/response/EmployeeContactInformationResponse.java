package com.geeke.induction.response;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import com.geeke.induction.entity.TsmEmergencyContact;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 此response 包含了info  card  tsmpost 数据
 * TsmInductionBasicEnhanceResponse
 * @author
 * @version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeContactInformationResponse extends TsmInductionBasic {

    /**
     * TsmInductionInfo中id
     */
    private String infoId;
    /**
     *   公积金账号ID
     */
    private String accumulationFundId;
    /**
     * 工号
     */
    private String employeeNumber;

    /**
     *   工资卡号
     */
    private String salaryCardId;

    /**
     *   国籍或地区
     */
    private String nationality;

    /**
     *   户口所在地
     */
    private String householdRegisterLocation;

    /**
     *  户口状态 类型 0：农业户口，1：城镇户口
     */
    private DictItem householdRegisterType;

    /**
     *   0：未婚，1：已婚，2：离异，3：丧偶 婚姻状况
     */
    private DictItem maritalStatus;

    /**
     *   0: 群众, 1: 党员, 2: 团员, 3: 其他  政治面貌
     */
    private DictItem politicalStatus;

    /**
     *   0：未育，1：已育 生育状态
     */
    private DictItem childbirthStatus;

    /**
     *   最高学历
     */
    private DictItem educationBackground;

    /**
     *   最高学位
     */
    private DictItem highestDegree;

    /**
     *   参加工作日期
     */
    private String workStartDate;

    /**
     * 工作年限
     */
    private long workYearNum;

    /**
     * 生肖
     */
    private String zodiac;

    /**
     * 血型
     */
    private DictItem bloodType;

    /**
     * 星座
     */
    private String horoscope;

    private String qqNumber;
    private String wechatId;
    private String otherContactInformation;
    private String companyEmail;
    private String companyPhoneNumber;
    private String companyExtensionNumber;



    //TsmEmergencyContact 紧急联系人

    /**
     * 紧急联系人id
     */
    private String emergencyContactId;
    /**
     * 紧急联系人姓名
     */
    private String emergencyContactName;

    /**
     * 紧急联系人关系
     */
    private DictItem emergencyContactRelationship;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhoneNumber;

    /**
     * 紧急联系地址
     */
    private String emergencyContactAddress;


    public EmployeeContactInformationResponse tsmInductionBasicConvert(TsmInductionBasic basic) {
        BeanUtil.copyProperties(basic, this);
        this.buildTsmInfo(basic.getTsmInductionInfoList());
        this.buildTsmEmergency(basic.getTsmEmergencyContactList());
        return this;
    }

    public EmployeeContactInformationResponse buildTsmInfo(List<TsmInductionInfo> tsmInductionInfos) {
        TsmInductionInfo tsmInductionInfo;
        if (ObjUtil.isNull(tsmInductionInfo = CollUtil.getFirst(tsmInductionInfos))) {
            return this;
        }

        this.infoId= tsmInductionInfo.getId();
        this.employeeNumber= tsmInductionInfo.getEmployeeNumber();

        this.qqNumber= tsmInductionInfo.getQqNumber();
        this.wechatId= tsmInductionInfo.getWechatId();
        this.otherContactInformation= tsmInductionInfo.getOtherContactInformation();
        this.companyEmail= tsmInductionInfo.getCompanyEmail();
        this.companyPhoneNumber= tsmInductionInfo.getCompanyPhoneNumber();
        this.companyExtensionNumber= tsmInductionInfo.getCompanyExtensionNumber();

        this.accumulationFundId= tsmInductionInfo.getAccumulationFundId();
        this.salaryCardId= tsmInductionInfo.getSalaryCardId();
        this.nationality= tsmInductionInfo.getNationality();
        this.householdRegisterLocation= tsmInductionInfo.getHouseholdRegisterLocation();
        this.householdRegisterType= tsmInductionInfo.getHouseholdRegisterType();
        this.maritalStatus= tsmInductionInfo.getMaritalStatus();
        this.politicalStatus= tsmInductionInfo.getPoliticalStatus();
        this.childbirthStatus= tsmInductionInfo.getChildbirthStatus();
        this.educationBackground= tsmInductionInfo.getEducationBackground();
        this.highestDegree= tsmInductionInfo.getHighestDegree();
        this.workStartDate= tsmInductionInfo.getWorkStartDate();
        this.workYearNum = DateUtil.betweenYear(DateUtil.parse(tsmInductionInfo.getWorkStartDate()), DateUtil.date(),false);;
        this.bloodType = tsmInductionInfo.getBloodType();
        this.zodiac = new ChineseDate(DateUtil.parse(this.getBirthDate())).getChineseZodiac();
        this.horoscope = DateUtil.getZodiac(DateUtil.parse(this.getBirthDate()).month(),DateUtil.parse(this.getBirthDate()).dayOfMonth());

        return this;
    }

    public EmployeeContactInformationResponse buildTsmEmergency(List<TsmEmergencyContact> tsmEmergencyContactList) {
        TsmEmergencyContact tsmEmergencyContact;
        if (ObjUtil.isNull(tsmEmergencyContact = CollUtil.getFirst(tsmEmergencyContactList))) {
            return this;
        }

        this.emergencyContactId = tsmEmergencyContact.getId();
        this.emergencyContactName = tsmEmergencyContact.getName();
        this.emergencyContactRelationship = tsmEmergencyContact.getRelationship();
        this.emergencyContactPhoneNumber = tsmEmergencyContact.getPhoneNumber();
        this.emergencyContactAddress = tsmEmergencyContact.getAddress();

        return this;
    }



}
