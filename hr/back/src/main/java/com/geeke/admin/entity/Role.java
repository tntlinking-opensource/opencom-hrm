package com.geeke.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.org.entity.Company;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 角色Entity
 * @author
 * @version
 */

@ApiModel(description = "角色Entity")
public class Role extends DataEntity<Role> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1290602453716598906L;

    @ApiModelProperty("公司")
    private Company company; // 公司

    @ApiModelProperty("编号")
    private String code; // 编号

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("禁用")
    private String isLocked = "0"; // 禁用

    // 构造方法
    public Role() {
        super();
    }

    public Role(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "公司不能为空")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Length(min = 1, max = 64, message = "编号长度必须介于 1 和 64 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_role";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1290602453716598906";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
