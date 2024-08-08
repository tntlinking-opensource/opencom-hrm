package com.geeke.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.hrm.entity.HrmLaidType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 离职类型Entity
 * @author
 * @version
 */

public class HrmLaidValue extends DataEntity<HrmLaidValue> {

    private static final long serialVersionUID = 1457254743943433287L;

    private HrmLaidType hrmLaidType; // 类型

    private String name; // name

    private String value; // value

    // 构造方法
    public HrmLaidValue() {
        super();
    }

    public HrmLaidValue(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "类型不能为空")
    public HrmLaidType getHrmLaidType() {
        return hrmLaidType;
    }

    public void setHrmLaidType(HrmLaidType hrmLaidType) {
        this.hrmLaidType = hrmLaidType;
    }

    @Length(min = 0, max = 64, message = "name长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 64, message = "value长度必须介于 0 和 64 之间")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "hrm_laid_value";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433287";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "离职类型项";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
