package com.geeke.dc.entity;

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
 * 查询条件Entity
 * @author
 * @version
 */

@ApiModel(description = "查询条件Entity")
public class DcCondition extends DataEntity<DcCondition> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1339823929984901123L;

    @ApiModelProperty("用户Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId; // 用户Id

    @ApiModelProperty("路由Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long routerId; // 路由Id

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("排序")
    private Integer sort; // 排序

    @ApiModelProperty("条件")
    private String conditions; // 条件

    // 构造方法
    public DcCondition() {
        super();
    }

    public DcCondition(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "用户Id不能为空")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @NotNull(message = "路由Id不能为空")
    public Long getRouterId() {
        return routerId;
    }

    public void setRouterId(Long routerId) {
        this.routerId = routerId;
    }

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "排序不能为空")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = -1, message = "条件长度必须介于 1 和 -1 之间")
    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "dc_condition";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1339823929984901123";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
