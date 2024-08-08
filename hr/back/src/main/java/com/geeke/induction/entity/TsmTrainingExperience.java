package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmTrainingExperience extends WfDataEntity<TsmTrainingExperience> {

    private static final long serialVersionUID = 1457254743943432652L;

    private String inductionId; // 员工ID

    private String course; // 培训课程

    private String startTime; // 培训开始时间

    private String endTime; // 培训结束时间

    private Integer duration; // 培训时长（小时）

    private String institutionName; // 培训机构名称

    private String location; // 培训地点

    // 构造方法
    public TsmTrainingExperience() {
        super();
    }

    public TsmTrainingExperience(String id) {
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

    @Length(min = 1, max = 100, message = "培训课程长度必须介于 1 和 100 之间")
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Length(min = 1, max = 50, message = "培训开始时间长度必须介于 1 和 50 之间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Length(min = 1, max = 50, message = "培训结束时间长度必须介于 1 和 50 之间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @NotNull(message = "培训时长（小时）不能为空")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Length(min = 1, max = 100, message = "培训机构名称长度必须介于 1 和 100 之间")
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    @Length(min = 1, max = 100, message = "培训地点长度必须介于 1 和 100 之间")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_training_experience";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432652";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "培训经历";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
