package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictItem;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 字典管理Entity
 * @author
 * @version
 */

@ApiModel(description = "字典管理Entity")
public class DictType extends DataEntity<DictType> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1290867985774919712L;

    @ApiModelProperty("编号")
    private String code; // 编号

    @ApiModelProperty("名称")
    private String name; // 名称

    @ApiModelProperty("是否系统级")
    private String isSystem; // 是否系统级

    @ApiModelProperty("子表列表")
    private List<DictItem> dictItemList = Lists.newArrayList(); // 子表列表

    // 构造方法
    public DictType() {
        super();
    }

    public DictType(String id) {
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

    @NotNull(message = "是否系统级不能为空")
    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem;
    }

    public List<DictItem> getDictItemList() {
        return dictItemList;
    }

    public void setDictItemList(List<DictItem> dictItemList) {
        this.dictItemList = dictItemList;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_dict_type";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1290867985774919712";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
