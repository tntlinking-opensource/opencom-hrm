package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 系统主题Entity
 * @author
 * @version
 */

@ApiModel(description = "系统主题Entity")
public class Theme extends DataEntity<Theme> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1331132582623264772L;

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("主题")
    private String theme; // 主题

    @ApiModelProperty("默认")
    private String isDefault = "1"; // 默认

    // 构造方法
    public Theme() {
        super();
    }

    public Theme(String id) {
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

    @Length(min = 1, max = -1, message = "主题长度必须介于 1 和 -1 之间")
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Length(min = 0, max = 1, message = "默认长度必须介于 0 和 1 之间")
    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_theme";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1331132582623264772";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
