package com.geeke.hrm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.hrm.entity.HrmLaidValue;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 离职类型Entity
 * @author
 * @version
 */

public class HrmLaidType extends DataEntity<HrmLaidType> {

    private static final long serialVersionUID = 1457254743943433140L;

    private String name; // 类型

    private List<HrmLaidValue> hrmLaidValueList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public HrmLaidType() {
        super();
    }

    public HrmLaidType(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 64, message = "类型长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HrmLaidValue> getHrmLaidValueList() {
        return hrmLaidValueList;
    }

    public void setHrmLaidValueList(List<HrmLaidValue> hrmLaidValueList) {
        this.hrmLaidValueList = hrmLaidValueList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "hrm_laid_type";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943433140";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "离职类型";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
