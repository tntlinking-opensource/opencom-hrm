package com.geeke.org.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.TreeEntity;
import com.geeke.org.entity.Company;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 部门管理Entity
 * @author
 * @version
 */

@ApiModel(description = "部门管理Entity")
public class Department extends TreeEntity<Department> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1331142598487023634L;

    @ApiModelProperty("公司")
    private Company company; // 公司

    @ApiModelProperty("编号")
    private String code; // 编号

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("总监")
    private User director; // 总监

    @ApiModelProperty("经理")
    private User manager; // 经理

    // 构造方法
    public Department() {
        super();
    }

    public Department(String id) {
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

    public User getDirector() {
        return director;
    }

    public void setDirector(User director) {
        this.director = director;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "org_department";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1331142598487023634";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
