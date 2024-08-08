package com.geeke.induction.entity;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.data.Parameter;
import com.geeke.induction.enums.YesOrNoEnum;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmInductionBasic extends WfDataEntity<TsmInductionBasic> {

    private static final long serialVersionUID = 1457254743943432647L;

    private String name; // name

    private DictItem gender; // 0：女性，1：男性

    private DictItem ethnicity; // 民族

    private String idNumber; // 身份证号码

    private String birthDate; // 出生日期

    private Integer age; // 年龄

    private String idCardExpiryDate; // 证件到期时间

    private String phoneNumber; // 手机号码

    private String residenceAddress; // 居住地址

    private String nativePlace; // 籍贯

    private DictItem healthStatus; // 0：健康或良好，1：一般或较弱，2：有疾病或残疾

    private String email; // 个人邮箱

    private String dataValidation = YesOrNoEnum.NO.getCode(); // data_validation

    private List<TsmInductionInfo> tsmInductionInfoList = Lists.newArrayList(); // 子表列表

    private List<TsmPost> tsmPostList = Lists.newArrayList(); // 子表列表

    private List<TsmWorkExperience> tsmWorkExperienceList = Lists.newArrayList(); // 子表列表

    private List<TsmProjectExperience> tsmProjectExperienceList = Lists.newArrayList(); // 子表列表

    private List<TsmTrainingExperience> tsmTrainingExperienceList = Lists.newArrayList(); // 子表列表

    private List<TsmQualification> tsmQualificationList = Lists.newArrayList(); // 子表列表

    private List<TsmEducationalRecords> tsmEducationalRecordsList = Lists.newArrayList(); // 子表列表

    private List<TsmPersonalSkills> tsmPersonalSkillsList = Lists.newArrayList(); // 子表列表

    private List<TsmFamilyMemberInfo> tsmFamilyMemberInfoList = Lists.newArrayList(); // 子表列表

    private List<TsmEmergencyContact> tsmEmergencyContactList = Lists.newArrayList(); // 子表列表

    private List<TsmCard> tsmCardList = Lists.newArrayList(); // 子表列表

    private List<TsmAssetInformation> tsmAssetInformationList = Lists.newArrayList(); // 子表列表

    private List<TsmEmployeeAttachments> tsmEmployeeAttachmentsList = Lists.newArrayList(); // 子表列表

    private List<TsmContractInformation> tsmContractInformationList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public TsmInductionBasic() {
        super();
    }

    public TsmInductionBasic(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 50, message = "name长度必须介于 1 和 50 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "0：女性，1：男性不能为空")
    public DictItem getGender() {
        return gender;
    }

    public void setGender(DictItem gender) {
        this.gender = gender;
    }

    public DictItem getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(DictItem ethnicity) {
        this.ethnicity = ethnicity;
    }

    @Length(min = 1, max = 18, message = "身份证号码长度必须介于 1 和 18 之间")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Length(min = 1, max = 64, message = "出生日期长度必须介于 1 和 64 之间")
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @NotNull(message = "年龄不能为空")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Length(min = 1, max = 50, message = "证件到期时间长度必须介于 1 和 50 之间")
    public String getIdCardExpiryDate() {
        return idCardExpiryDate;
    }

    public void setIdCardExpiryDate(String idCardExpiryDate) {
        this.idCardExpiryDate = idCardExpiryDate;
    }

    @Length(min = 0, max = 20, message = "手机号码长度必须介于 0 和 20 之间")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Length(min = 1, max = 100, message = "居住地址长度必须介于 1 和 100 之间")
    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    @Length(min = 0, max = 100, message = "籍贯长度必须介于 0 和 100 之间")
    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    @NotNull(message = "0：健康或良好，1：一般或较弱，2：有疾病或残疾不能为空")
    public DictItem getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(DictItem healthStatus) {
        this.healthStatus = healthStatus;
    }

    @Length(min = 0, max = 100, message = "个人邮箱长度必须介于 0 和 100 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 8, message = "data_validation长度必须介于 0 和 8 之间")
    public String getDataValidation() {
        return dataValidation;
    }

    public void setDataValidation(String dataValidation) {
        this.dataValidation = dataValidation;
    }

    public List<TsmInductionInfo> getTsmInductionInfoList() {
        return tsmInductionInfoList;
    }

    public void setTsmInductionInfoList(List<TsmInductionInfo> tsmInductionInfoList) {
        this.tsmInductionInfoList = tsmInductionInfoList;
    }

    public List<TsmPost> getTsmPostList() {
        return tsmPostList;
    }

    public void setTsmPostList(List<TsmPost> tsmPostList) {
        this.tsmPostList = tsmPostList;
    }

    public List<TsmWorkExperience> getTsmWorkExperienceList() {
        return tsmWorkExperienceList;
    }

    public void setTsmWorkExperienceList(List<TsmWorkExperience> tsmWorkExperienceList) {
        this.tsmWorkExperienceList = tsmWorkExperienceList;
    }

    public List<TsmProjectExperience> getTsmProjectExperienceList() {
        return tsmProjectExperienceList;
    }

    public void setTsmProjectExperienceList(List<TsmProjectExperience> tsmProjectExperienceList) {
        this.tsmProjectExperienceList = tsmProjectExperienceList;
    }

    public List<TsmTrainingExperience> getTsmTrainingExperienceList() {
        return tsmTrainingExperienceList;
    }

    public void setTsmTrainingExperienceList(List<TsmTrainingExperience> tsmTrainingExperienceList) {
        this.tsmTrainingExperienceList = tsmTrainingExperienceList;
    }

    public List<TsmQualification> getTsmQualificationList() {
        return tsmQualificationList;
    }

    public void setTsmQualificationList(List<TsmQualification> tsmQualificationList) {
        this.tsmQualificationList = tsmQualificationList;
    }

    public List<TsmEducationalRecords> getTsmEducationalRecordsList() {
        return tsmEducationalRecordsList;
    }

    public void setTsmEducationalRecordsList(List<TsmEducationalRecords> tsmEducationalRecordsList) {
        this.tsmEducationalRecordsList = tsmEducationalRecordsList;
    }

    public List<TsmPersonalSkills> getTsmPersonalSkillsList() {
        return tsmPersonalSkillsList;
    }

    public void setTsmPersonalSkillsList(List<TsmPersonalSkills> tsmPersonalSkillsList) {
        this.tsmPersonalSkillsList = tsmPersonalSkillsList;
    }

    public List<TsmFamilyMemberInfo> getTsmFamilyMemberInfoList() {
        return tsmFamilyMemberInfoList;
    }

    public void setTsmFamilyMemberInfoList(List<TsmFamilyMemberInfo> tsmFamilyMemberInfoList) {
        this.tsmFamilyMemberInfoList = tsmFamilyMemberInfoList;
    }

    public List<TsmEmergencyContact> getTsmEmergencyContactList() {
        return tsmEmergencyContactList;
    }

    public void setTsmEmergencyContactList(List<TsmEmergencyContact> tsmEmergencyContactList) {
        this.tsmEmergencyContactList = tsmEmergencyContactList;
    }

    public List<TsmCard> getTsmCardList() {
        return tsmCardList;
    }

    public void setTsmCardList(List<TsmCard> tsmCardList) {
        this.tsmCardList = tsmCardList;
    }

    public List<TsmAssetInformation> getTsmAssetInformationList() {
        return tsmAssetInformationList;
    }

    public void setTsmAssetInformationList(List<TsmAssetInformation> tsmAssetInformationList) {
        this.tsmAssetInformationList = tsmAssetInformationList;
    }

    public List<TsmEmployeeAttachments> getTsmEmployeeAttachmentsList() {
        return tsmEmployeeAttachmentsList;
    }

    public void setTsmEmployeeAttachmentsList(List<TsmEmployeeAttachments> tsmEmployeeAttachmentsList) {
        this.tsmEmployeeAttachmentsList = tsmEmployeeAttachmentsList;
    }

    public List<TsmContractInformation> getTsmContractInformationList() {
        return tsmContractInformationList;
    }

    public void setTsmContractInformationList(List<TsmContractInformation> tsmContractInformationList) {
        this.tsmContractInformationList = tsmContractInformationList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_induction_basic";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432647";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "个人信息表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }

    /**
     * 填充不为空数组
     */
    public void fillArray() {
        if (tsmInductionInfoList.size() == 0) {
            tsmInductionInfoList.add(new TsmInductionInfo());
        }
        if (tsmPostList.size() == 0) {
            tsmPostList.add(new TsmPost());
        }
        if (tsmWorkExperienceList.size() == 0) {
            tsmWorkExperienceList.add(new TsmWorkExperience());
        }
        if (tsmProjectExperienceList.size() == 0) {
            tsmProjectExperienceList.add(new TsmProjectExperience());
        }
        if (tsmTrainingExperienceList.size() == 0) {
            tsmTrainingExperienceList.add(new TsmTrainingExperience());
        }
        if (tsmQualificationList.size() == 0) {
            tsmQualificationList.add(new TsmQualification());
        }
        if (tsmEducationalRecordsList.size() == 0) {
            tsmEducationalRecordsList.add(new TsmEducationalRecords());
        }
        if (tsmPersonalSkillsList.size() == 0) {
            tsmPersonalSkillsList.add(new TsmPersonalSkills());
        }
        if (tsmFamilyMemberInfoList.size() == 0) {
            tsmFamilyMemberInfoList.add(new TsmFamilyMemberInfo());
        }
        if (tsmEmergencyContactList.size() == 0) {
            tsmEmergencyContactList.add(new TsmEmergencyContact());
        }
        if (tsmCardList.size() == 0) {
            tsmCardList.add(new TsmCard());
        }
        if (tsmAssetInformationList.size() == 0) {
            tsmAssetInformationList.add(new TsmAssetInformation());
        }
        if (tsmEmployeeAttachmentsList.size() == 0) {
            tsmEmployeeAttachmentsList.add(new TsmEmployeeAttachments());
        }
        if (tsmContractInformationList.size() == 0) {
            tsmContractInformationList.add(new TsmContractInformation());
        }
    }

    public List<Parameter> queryInductionIdParam() {
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("induction_id", "=", id));
        return params;
    }

    public static List<Parameter> queryInductionIdParam(List<String> ids) {
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("induction_id", "in", ids));
        return params;
    }

    public TsmInductionInfo getTsmInfo() {
        if (CollUtil.isNotEmpty(getTsmInductionInfoList()) && getTsmInductionInfoList().size() > 0) {
            return getTsmInductionInfoList().get(0);
        }
        return new TsmInductionInfo();
    }

    public TsmPost getTsmPost() {
        if (CollUtil.isNotEmpty(getTsmPostList()) && getTsmPostList().size() > 0) {
            return getTsmPostList().get(0);
        }
        return new TsmPost();
    }
}
