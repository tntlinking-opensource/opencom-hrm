package com.geeke.ten.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 租户管理Entity
 * @author
 * @version
 */

public class Tenant extends DataEntity<Tenant> {

    private static final long serialVersionUID = 1373952341416665145L;

    private String name; // 名称

    private String isLocked = "0"; // 禁用

    private List<TenantAdmin> tenantAdminList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public Tenant() {
        super();
    }

    public Tenant(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 1, message = "禁用长度必须介于 1 和 1 之间")
    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public List<TenantAdmin> getTenantAdminList() {
        return tenantAdminList;
    }

    public void setTenantAdminList(List<TenantAdmin> tenantAdminList) {
        this.tenantAdminList = tenantAdminList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "ten_tenant";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1373952341416665145";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
