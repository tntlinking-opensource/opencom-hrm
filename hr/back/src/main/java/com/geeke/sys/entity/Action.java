package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.DictItem;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 操作日志Entity
 * @author
 * @version
 */

public class Action extends DataEntity<Action> {

    private static final long serialVersionUID = 1220000000000000005L;

    private DictItem actionType; // 操作类型

    private String objectType; // 业务表名称

    private String objectId; // 业务对象

    private String objectName; // 对象名称

    private String objectComments; // 对象描述

    private String status = "0"; // 已还原

    private List<ActionRecycle> actionRecycleList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public Action() {
        super();
    }

    public Action(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "操作类型不能为空")
    public DictItem getActionType() {
        return actionType;
    }

    public void setActionType(DictItem actionType) {
        this.actionType = actionType;
    }

    @Length(min = 1, max = 64, message = "业务表名称长度必须介于 1 和 64 之间")
    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @Length(min = 1, max = 21, message = "业务对象长度必须介于 1 和 21 之间")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Length(min = 0, max = 128, message = "对象名称长度必须介于 0 和 128 之间")
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Length(min = 0, max = 128, message = "对象描述长度必须介于 0 和 128 之间")
    public String getObjectComments() {
        return objectComments;
    }

    public void setObjectComments(String objectComments) {
        this.objectComments = objectComments;
    }

    @Length(min = 1, max = 1, message = "已还原长度必须介于 1 和 1 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ActionRecycle> getActionRecycleList() {
        return actionRecycleList;
    }

    public void setActionRecycleList(List<ActionRecycle> actionRecycleList) {
        this.actionRecycleList = actionRecycleList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_action";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1220000000000000005";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
