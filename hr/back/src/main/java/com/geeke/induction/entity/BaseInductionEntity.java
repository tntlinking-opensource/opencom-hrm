package com.geeke.induction.entity;

import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * @author yi.luo
 * @date 2023-05-26 15:48
 */
public class BaseInductionEntity<T> extends WfDataEntity<T> {
    /**
     * 员工id
     */
    private String inductionId;

    @Length(min = 1, max = 20, message = "员工ID长度必须介于 1 和 20 之间")
    public String getInductionId() {
        return inductionId;
    }

    public void setInductionId(String inductionId) {
        this.inductionId = inductionId;
    }


    public BaseInductionEntity() {
        super();
    }

    public BaseInductionEntity(String id) {
        super(id);
    }
}
