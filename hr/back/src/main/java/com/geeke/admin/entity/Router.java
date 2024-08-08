package com.geeke.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.TreeEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 路由管理Entity
 * @author
 * @version
 */

@ApiModel(description = "路由管理Entity")
public class Router extends TreeEntity<Router> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1289036439920697347L;

    @ApiModelProperty("编号")
    private String code; // 编号

    @ApiModelProperty("URL")
    private String url; // URL

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("可以授权")
    private String canPermission; // 可以授权

    @ApiModelProperty("禁用")
    private String isLocked; // 禁用

    @ApiModelProperty("扩展属性")
    private String properties; // 扩展属性

    @ApiModelProperty("系统标识")
    private String systemCode; // 系统标识

    @ApiModelProperty("图片地址")
    private String iconId; // 图片地址

    // 子表列表字段

    // 构造方法
    public Router() {
        super();
    }

    public Router(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 64, message = "编号长度必须介于 0 和 64 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 0, max = 128, message = "URL长度必须介于 0 和 128 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "可以授权不能为空")
    public String getCanPermission() {
        return canPermission;
    }

    public void setCanPermission(String canPermission) {
        this.canPermission = canPermission;
    }

    @NotNull(message = "禁用不能为空")
    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    @Length(min = 1, max = 1024, message = "扩展属性长度必须介于 1 和 1024 之间")
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    @NotNull(message = "系统标识不能为空")
    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_router";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1289036439920697347";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
