package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmProjectExperience extends WfDataEntity<TsmProjectExperience> {

    private static final long serialVersionUID = 1457254743943432651L;

    private String inductionId; // 员工ID

    private String projectName; // 项目名称

    private String position; // 担任职位

    private String startTime; // 项目开始时间

    private String endTime; // 项目结束时间

    private String description; // 项目描述

    // 构造方法
    public TsmProjectExperience() {
        super();
    }

    public TsmProjectExperience(String id) {
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

    @Length(min = 1, max = 100, message = "项目名称长度必须介于 1 和 100 之间")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Length(min = 1, max = 50, message = "担任职位长度必须介于 1 和 50 之间")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Length(min = 1, max = 50, message = "项目开始时间长度必须介于 1 和 50 之间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Length(min = 1, max = 50, message = "项目结束时间长度必须介于 1 和 50 之间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Length(min = 1, max = 500, message = "项目描述长度必须介于 1 和 500 之间")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_project_experience";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432651";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "项目经历";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
