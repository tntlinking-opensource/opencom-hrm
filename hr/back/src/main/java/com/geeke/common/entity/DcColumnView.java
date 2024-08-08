package com.geeke.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.Router;
import com.geeke.admin.entity.User;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 查询列Entity
 * @author
 * @version
 */

public class DcColumnView extends DataEntity<DcColumnView> {

    private static final long serialVersionUID = 1471853921909678085L;

    private User user; // 用户

    private Router router; // 路由

    private String json; // 列参数

    // 构造方法
    public DcColumnView() {
        super();
    }

    public DcColumnView(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "用户不能为空")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotNull(message = "路由不能为空")
    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    @Length(min = 0, max = -1, message = "列参数长度必须介于 0 和 -1 之间")
    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "dc_column_view";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1471853921909678085";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
