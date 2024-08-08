package com.geeke.basic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import org.hibernate.validator.constraints.Length;

/**
 * 基础配置Entity
 * @author
 * @version
 */

public class UserPropList extends DataEntity<UserPropList> {

    private static final long serialVersionUID = 1457254743943430905L;

    private String code; // code

    private String name; // name

    private String attribute0; // attribute0

    private String attribute1; // attribute1

    private String attribute2; // attribute2

    private String attribute3; // attribute3

    private String attribute4; // attribute4

    private String tenantId; // 租户id

    // 构造方法
    public UserPropList() {
        super();
    }

    public UserPropList(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 100, message = "code长度必须介于 1 和 100 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 200, message = "name长度必须介于 1 和 200 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = -1, message = "attribute0长度必须介于 0 和 -1 之间")
    public String getAttribute0() {
        return attribute0;
    }

    public void setAttribute0(String attribute0) {
        this.attribute0 = attribute0;
    }

    @Length(min = 0, max = -1, message = "attribute1长度必须介于 0 和 -1 之间")
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    @Length(min = 0, max = -1, message = "attribute2长度必须介于 0 和 -1 之间")
    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    @Length(min = 0, max = -1, message = "attribute3长度必须介于 0 和 -1 之间")
    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    @Length(min = 0, max = -1, message = "attribute4长度必须介于 0 和 -1 之间")
    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    @Length(min = 0, max = 21, message = "租户id长度必须介于 0 和 21 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "user_prop_list";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430905";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "基础信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
