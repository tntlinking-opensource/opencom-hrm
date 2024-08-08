package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmWorkExperience extends WfDataEntity<TsmWorkExperience> {

    private static final long serialVersionUID = 1457254743943432650L;

    private String inductionId; // 员工ID

    private String workUnit; // 工作单位

    private String position; // 职位

    private String startTime; // 开始时间

    private String endTime; // 结束时间

    private String resignReason; // 离职原因

    private String referenceName; // 证明人

    private String referencePhone; // 证明人电话

    // 构造方法
    public TsmWorkExperience() {
        super();
    }

    public TsmWorkExperience(String id) {
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

    @Length(min = 1, max = 100, message = "工作单位长度必须介于 1 和 100 之间")
    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    @Length(min = 1, max = 50, message = "职位长度必须介于 1 和 50 之间")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Length(min = 1, max = 50, message = "开始时间长度必须介于 1 和 50 之间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Length(min = 1, max = 50, message = "结束时间长度必须介于 1 和 50 之间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Length(min = 0, max = 100, message = "离职原因长度必须介于 0 和 100 之间")
    public String getResignReason() {
        return resignReason;
    }

    public void setResignReason(String resignReason) {
        this.resignReason = resignReason;
    }

    @Length(min = 1, max = 50, message = "证明人长度必须介于 1 和 50 之间")
    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    @Length(min = 1, max = 20, message = "证明人电话长度必须介于 1 和 20 之间")
    public String getReferencePhone() {
        return referencePhone;
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_work_experience";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432650";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "工作经历";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
