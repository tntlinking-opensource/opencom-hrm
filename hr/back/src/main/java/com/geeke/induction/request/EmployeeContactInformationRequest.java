package com.geeke.induction.request;

import cn.hutool.core.util.StrUtil;
import com.geeke.induction.entity.TsmEmergencyContact;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * EmployeeInfoRequest
 * @author
 * @version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeContactInformationRequest {
    /**
     * basic的id
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * 0：女性，1：男性
     */
    private DictItem gender;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 个人邮箱
     */
    private String email;
    /**
     * 身份证号码
     */
    private String idNumber;
    /**
     * 证件到期时间
     */
    private String idCardExpiryDate;
    /**
     * 民族
     */
    private DictItem ethnicity;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 居住地址
     */
    private String residenceAddress;
    /**
     * 0：健康或良好，1：一般或较弱，2：有疾病或残疾 健康状态
     */
    private DictItem healthStatus;

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


    //************************ TsmInductionInfo **************************
    /**
     * TsmInductionInfo中id
     */
    private String infoId;
    /**
     *   公积金账号ID
     */
    private String accumulationFundId;

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


    public TsmInductionBasic tsmInductionBasicConvert(TsmInductionBasic basic) {
        basic.setName(name);
        basic.setGender(gender);
        basic.setPhoneNumber(phoneNumber);
        basic.setEmail(email);
        basic.setIdNumber(idNumber);
        basic.setIdCardExpiryDate(idCardExpiryDate);
        basic.setEthnicity(ethnicity);
        basic.setNativePlace(nativePlace);
        basic.setResidenceAddress(residenceAddress);
        basic.setHealthStatus(healthStatus);
        return basic;
    }

    public TsmInductionInfo tsmInductionInfoConvert(TsmInductionInfo info, String inductionId, String cardId) {
        info.setInductionId(inductionId);
        info.setAccumulationFundId(accumulationFundId);
        info.setSalaryCardId(StrUtil.isBlank(cardId) ? info.getSalaryCardId() : cardId);
        info.setNationality(nationality);
        info.setHouseholdRegisterLocation(householdRegisterLocation);
        info.setHouseholdRegisterType(householdRegisterType);
        info.setMaritalStatus(maritalStatus);
        info.setPoliticalStatus(politicalStatus);
        info.setChildbirthStatus(childbirthStatus);
        info.setEducationBackground(educationBackground);
        info.setHighestDegree(highestDegree);
        info.setWorkStartDate(workStartDate);
        info.setZodiac(zodiac);
        info.setBloodType(bloodType);
        info.setHoroscope(horoscope);

        info.setQqNumber(qqNumber);
        info.setWechatId(wechatId);
        info.setOtherContactInformation(otherContactInformation);
        info.setCompanyEmail(companyEmail);
        info.setCompanyPhoneNumber(companyPhoneNumber);
        info.setCompanyExtensionNumber(companyExtensionNumber);
        return info;
    }

    public TsmEmergencyContact tsmEmergencyContactConvert(TsmEmergencyContact tsmEmergencyContact, String inductionId) {
        tsmEmergencyContact.setInductionId(inductionId);
        tsmEmergencyContact.setName(emergencyContactName);
        tsmEmergencyContact.setRelationship(emergencyContactRelationship);
        tsmEmergencyContact.setPhoneNumber(emergencyContactPhoneNumber);
        tsmEmergencyContact.setAddress(emergencyContactAddress);
        return tsmEmergencyContact;
    }
}
