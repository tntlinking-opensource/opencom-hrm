package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.sys.entity.DictItem;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmFamilyMemberInfo extends BaseInductionEntity<TsmFamilyMemberInfo> {

    private static final long serialVersionUID = 1457254743943432656L;

    private String familyName; // 亲属姓名

    private DictItem kinship; // 家属关系

    private String career; // 职业

    private String contactAddress; // 联系地址

    private String contactType; // 联系方式

    // 构造方法
    public TsmFamilyMemberInfo() {
        super();
    }

    public TsmFamilyMemberInfo(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 255, message = "亲属姓名长度必须介于 1 和 255 之间")
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @NotNull(message = "家属关系不能为空")
    public DictItem getKinship() {
        return kinship;
    }

    public void setKinship(DictItem kinship) {
        this.kinship = kinship;
    }

    @Length(min = 1, max = 255, message = "职业长度必须介于 1 和 255 之间")
    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Length(min = 1, max = 255, message = "联系地址长度必须介于 1 和 255 之间")
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    @Length(min = 1, max = 255, message = "联系方式长度必须介于 1 和 255 之间")
    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_family_member_info";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432656";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "家庭信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
