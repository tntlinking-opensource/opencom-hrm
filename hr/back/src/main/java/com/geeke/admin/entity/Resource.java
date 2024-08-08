package com.geeke.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.Router;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 资源管理Entity
 * @author
 * @version
 */

@ApiModel(description = "资源管理Entity")
public class Resource extends DataEntity<Resource> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1331145570604408856L;

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("编号")
    private String code = "This.router.code"; // 编号

    @ApiModelProperty("URL")
    private String url = "This.router.url"; // URL

    @ApiModelProperty("权限")
    private String permission; // 权限

    @ApiModelProperty("可以授权")
    private String canPermission = "1"; // 可以授权

    @ApiModelProperty("默认资源")
    private String isDefault = "0"; // 默认资源

    @ApiModelProperty("禁用")
    private String isLocked = "0"; // 禁用

    @ApiModelProperty("路由")
    private Router router; // 路由

    @ApiModelProperty("扩展属性")
    private String properties; // 扩展属性

    // 构造方法
    public Resource() {
        super();
    }

    public Resource(String id) {
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

    @Length(min = 1, max = 64, message = "编号长度必须介于 1 和 64 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 128, message = "URL长度必须介于 1 和 128 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 0, max = 32, message = "权限长度必须介于 0 和 32 之间")
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Length(min = 1, max = 1, message = "可以授权长度必须介于 1 和 1 之间")
    public String getCanPermission() {
        return canPermission;
    }

    public void setCanPermission(String canPermission) {
        this.canPermission = canPermission;
    }

    @Length(min = 1, max = 1, message = "默认资源长度必须介于 1 和 1 之间")
    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Length(min = 0, max = 1, message = "禁用长度必须介于 0 和 1 之间")
    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    @NotNull(message = "路由不能为空")
    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    @Length(min = 0, max = 1024, message = "扩展属性长度必须介于 0 和 1024 之间")
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_resource";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1331145570604408856";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
