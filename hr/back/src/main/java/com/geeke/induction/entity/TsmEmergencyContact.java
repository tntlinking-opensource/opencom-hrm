package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmEmergencyContact extends WfDataEntity<TsmEmergencyContact> {

    private static final long serialVersionUID = 1457254743943432657L;

    private String inductionId; // 员工ID

    private String name; // 联系人姓名

    private DictItem relationship; // 联系人关系

    private String phoneNumber; // 联系人电话

    private String address; // 联系地址

    // 构造方法
    public TsmEmergencyContact() {
        super();
    }

    public TsmEmergencyContact(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 20, message = "员工ID长度必须介于 1 和 20 之间")
    public String getInductionId() {
        return inductionId;
    }

    public void setInductionId(String inductionId) {
        this.inductionId = inductionId;
    }

    @Length(min = 1, max = 255, message = "联系人姓名长度必须介于 1 和 255 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "联系人关系不能为空")
    public DictItem getRelationship() {
        return relationship;
    }

    public void setRelationship(DictItem relationship) {
        this.relationship = relationship;
    }

    @Length(min = 1, max = 255, message = "联系人电话长度必须介于 1 和 255 之间")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Length(min = 1, max = 255, message = "联系地址长度必须介于 1 和 255 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_emergency_contact";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432657";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "紧急联系人";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
