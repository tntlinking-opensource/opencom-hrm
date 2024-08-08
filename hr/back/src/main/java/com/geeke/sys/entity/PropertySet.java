package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 属性集Entity
 * @author
 * @version
 */

@ApiModel(description = "属性集Entity")
public class PropertySet extends DataEntity<PropertySet> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1305565930798669899L;

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("属性定义")
    private String propertiesDef; // 属性定义

    // 构造方法
    public PropertySet() {
        super();
    }

    public PropertySet(String id) {
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

    @Length(min = 1, max = -1, message = "属性定义长度必须介于 1 和 -1 之间")
    public String getPropertiesDef() {
        return propertiesDef;
    }

    public void setPropertiesDef(String propertiesDef) {
        this.propertiesDef = propertiesDef;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_property_set";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1305565930798669899";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
