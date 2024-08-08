package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 字典管理Entity
 * @author
 * @version
 */

public class DictItem extends DataEntity<DictItem> {

    private static final long serialVersionUID = 1290867985774919713L;

    @ApiModelProperty(value = "字典类",required = true)
    private DictType dictType; // 字典类

    @ApiModelProperty(value = "名称",required = true)
    private String name; // 名称

    @ApiModelProperty(value = "值",required = true)
    private String value; // 值

    // 构造方法
    public DictItem() {
        super();
    }

    public DictItem(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "字典类不能为空")
    public DictType getDictType() {
        return dictType;
    }

    public void setDictType(DictType dictType) {
        this.dictType = dictType;
    }

    @Length(min = 1, max = 128, message = "名称长度必须介于 1 和 128 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 128, message = "值长度必须介于 1 和 128 之间")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_dict_item";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1290867985774919713";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
