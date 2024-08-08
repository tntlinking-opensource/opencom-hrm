package com.geeke.schedule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 定时任务管理Entity
 * @author
 * @version
 */

@ApiModel(description = "定时任务管理Entity")
public class ScheduleJob extends DataEntity<ScheduleJob> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1295275790451941378L;

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("任务表达式")
    private String cron; // 任务表达式

    @ApiModelProperty("任务状态")
    private String status; // 任务状态

    @ApiModelProperty("是否禁用")
    private String isLock; // 是否禁用

    @ApiModelProperty("定时任务类全路径")
    private String javaClass; // 定时任务类全路径

    @ApiModelProperty("定时任务方法名称")
    private String javaMethod; // 定时任务方法名称

    @ApiModelProperty("定时任务方法入参")
    private String params; // 定时任务方法入参

    // 构造方法
    public ScheduleJob() {
        super();
    }

    public ScheduleJob(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 128, message = "名称长度必须介于 0 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 128, message = "任务表达式长度必须介于 0 和 128 之间")
    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    @Length(min = 1, max = 150, message = "定时任务类全路径长度必须介于 1 和 150 之间")
    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    @Length(min = 1, max = 30, message = "定时任务方法名称长度必须介于 1 和 30 之间")
    public String getJavaMethod() {
        return javaMethod;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    @Length(min = 0, max = 100, message = "定时任务方法入参长度必须介于 0 和 100 之间")
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "schedule_job";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1295275790451941378";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
