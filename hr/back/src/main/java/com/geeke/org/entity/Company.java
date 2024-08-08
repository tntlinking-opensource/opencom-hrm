package com.geeke.org.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 公司管理Entity
 * @author
 * @version
 */

@ApiModel(description = "公司管理Entity")
public class Company extends TreeEntity<Company> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1287908822026887245L;

    @ApiModelProperty("编号")
    private String code; // 编号

    @ApiModelProperty("全称")
    private String fullName; // 全称

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("董事长")
    private User chairman; // 董事长

    @ApiModelProperty("总经理")
    private User manager; // 总经理

    @ApiModelProperty("手机端地址")
    private String appUrl; // 手机端地址

    // 构造方法
    public Company() {
        super();
    }

    public Company(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 64, message = "编号长度必须介于 1 和 64 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 512, message = "全称长度必须介于 1 和 512 之间")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getChairman() {
        return chairman;
    }

    public void setChairman(User chairman) {
        this.chairman = chairman;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "org_company";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1287908822026887245";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
