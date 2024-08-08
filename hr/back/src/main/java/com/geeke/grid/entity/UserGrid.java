package com.geeke.grid.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 用户自定义布局Entity
 * @author
 * @version
 */

@ApiModel(description = "用户自定义布局Entity")
public class UserGrid extends DataEntity<UserGrid> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1332231183718506511L;

    @ApiModelProperty("用户id")
    private String userId; // 用户id

    @ApiModelProperty("路由id")
    private String routerId; // 路由id

    @ApiModelProperty("grid_info")
    private String gridInfo; // grid_info

    @ApiModelProperty("选中展示的模块id")
    private String defaultCheck; // 选中展示的模块id

    // 构造方法
    public UserGrid() {
        super();
    }

    public UserGrid(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 21, message = "用户id长度必须介于 1 和 21 之间")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Length(min = 1, max = 21, message = "路由id长度必须介于 1 和 21 之间")
    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    @Length(min = 0, max = 1000, message = "grid_info长度必须介于 0 和 1000 之间")
    public String getGridInfo() {
        return gridInfo;
    }

    public void setGridInfo(String gridInfo) {
        this.gridInfo = gridInfo;
    }

    @Length(min = 0, max = 200, message = "选中展示的模块id长度必须介于 0 和 200 之间")
    public String getDefaultCheck() {
        return defaultCheck;
    }

    public void setDefaultCheck(String defaultCheck) {
        this.defaultCheck = defaultCheck;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "user_grid";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1332231183718506511";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
