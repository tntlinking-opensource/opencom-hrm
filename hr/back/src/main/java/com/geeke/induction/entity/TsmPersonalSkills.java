package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmPersonalSkills extends WfDataEntity<TsmPersonalSkills> {

    private static final long serialVersionUID = 1457254743943432655L;

    private String inductionId; // 员工ID

    private String skillName; // 技能名称

    private DictItem skillLevel; // 熟练程度

    // 构造方法
    public TsmPersonalSkills() {
        super();
    }

    public TsmPersonalSkills(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 20, message = "员工ID长度必须介于 1 和 20 之间")
    public String getInductionId() {
        return inductionId;
    }

    public void setInductionId(String inductionId) {
        this.inductionId = inductionId;
    }

    @Length(min = 1, max = 255, message = "技能名称长度必须介于 1 和 255 之间")
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @NotNull(message = "熟练程度不能为空")
    public DictItem getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(DictItem skillLevel) {
        this.skillLevel = skillLevel;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_personal_skills";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432655";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "个人技能";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
