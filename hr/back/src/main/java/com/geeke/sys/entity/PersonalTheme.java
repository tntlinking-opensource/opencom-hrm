package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 自定义主题Entity
 * @author
 * @version
 */

@ApiModel(description = "自定义主题Entity")
public class PersonalTheme extends DataEntity<PersonalTheme> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1332275284442751011L;

    @ApiModelProperty("用户")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId; // 用户

    @ApiModelProperty("主题")
    private String theme; // 主题

    // 构造方法
    public PersonalTheme() {
        super();
    }

    public PersonalTheme(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "用户不能为空")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Length(min = 1, max = -1, message = "主题长度必须介于 1 和 -1 之间")
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_personal_theme";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1332275284442751011";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
