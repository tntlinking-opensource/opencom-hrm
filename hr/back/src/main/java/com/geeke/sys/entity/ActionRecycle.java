package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.Action;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 操作日志Entity
 * @author
 * @version
 */

public class ActionRecycle extends DataEntity<ActionRecycle> {

    private static final long serialVersionUID = 1220000000000000006L;

    private Action action; // 日志

    private String tableName; // 表名

    private String objectId; // 业务对象

    private String objectName; // 对象名称

    // 构造方法
    public ActionRecycle() {
        super();
    }

    public ActionRecycle(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "日志不能为空")
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Length(min = 1, max = 128, message = "表名长度必须介于 1 和 128 之间")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_action_recycle";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1220000000000000006";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
