package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 系统编码规则Entity
 * @author
 * @version
 */

@ApiModel(description = "系统编码规则Entity")
public class CodeRule extends DataEntity<CodeRule> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1331128150216990723L;

    @ApiModelProperty("编号")
    private String code; // 编号

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("规则")
    private String ruleDef; // 规则

    // 构造方法
    public CodeRule() {
        super();
    }

    public CodeRule(String id) {
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

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 512, message = "规则长度必须介于 1 和 512 之间")
    public String getRuleDef() {
        return ruleDef;
    }

    public void setRuleDef(String ruleDef) {
        this.ruleDef = ruleDef;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_code_rule";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1331128150216990723";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
