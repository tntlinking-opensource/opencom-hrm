package com.geeke.ten.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 租户管理Entity
 * @author
 * @version
 */

public class TenantAdmin extends DataEntity<TenantAdmin> {

    private static final long serialVersionUID = 1373952341416665146L;

    private Tenant tenant; // 租户

    private User user; // 用户

    // 构造方法
    public TenantAdmin() {
        super();
    }

    public TenantAdmin(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "租户不能为空")
    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @NotNull(message = "用户不能为空")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "ten_tenant_admin";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1373952341416665146";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
