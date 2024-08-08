package com.geeke.induction.request;

import com.geeke.induction.entity.TsmCard;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * TsmInductionBasicEnhanceResponse
 * @author
 * @version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsmInductionBasicEnhanceRequest {
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

    //************************ tsmCard **************************
    /**
     * tsmCard中Id等于salaryCardId
     */
//    private String salaryCardId;


    /**
     * 银行账户
     */
    private String cardId;
    /**
     *   开户人
     */
    private String accountOwner;

    /**
     *   银行名称
     */
    private String bankName;

    /**
     *   开户行
     */
    private String bankAddress;

    /**
     *   开户城市
     */
    private String bankCity;

    //************************ tsmPost **************************
    /**
     * tsmPostId
     */
    private String postId;
    /**
     *   面试时间
     */
    private String interviewTime;

    /**
     *   期望薪资（税前）
     */
    private Integer expectedSalary;


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
        info.setSalaryCardId(cardId);
        info.setNationality(nationality);
        info.setHouseholdRegisterLocation(householdRegisterLocation);
        info.setHouseholdRegisterType(householdRegisterType);
        info.setMaritalStatus(maritalStatus);
        info.setPoliticalStatus(politicalStatus);
        info.setChildbirthStatus(childbirthStatus);
        info.setEducationBackground(educationBackground);
        info.setHighestDegree(highestDegree);
        info.setWorkStartDate(workStartDate);
        return info;
    }

    public TsmCard tsmCardConvert(TsmCard tsmCard, String inductionId) {
        tsmCard.setInductionId(inductionId);
        tsmCard.setAccountNumber(cardId);
        tsmCard.setAccountOwner(accountOwner);
        tsmCard.setBankName(bankName);
        tsmCard.setBankAddress(bankAddress);
        tsmCard.setBankCity(bankCity);
        return tsmCard;
    }

    public TsmPost tsmPostConvert(TsmPost tsmPost, String inductionId) {
        tsmPost.setInductionId(inductionId);
        tsmPost.setInterviewTime(interviewTime);
        tsmPost.setExpectedSalary(expectedSalary);
        return tsmPost;
    }
}
