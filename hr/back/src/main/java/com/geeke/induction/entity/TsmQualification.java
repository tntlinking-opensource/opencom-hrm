package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmQualification extends WfDataEntity<TsmQualification> {

    private static final long serialVersionUID = 1457254743943432653L;

    private String inductionId; // 员工ID

    private String certificateName; // 证书名称

    private String certificateNum; // 证书编号

    private String certificateDate; // 发证时间

    // 构造方法
    public TsmQualification() {
        super();
    }

    public TsmQualification(String id) {
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

    @Length(min = 1, max = 128, message = "证书名称长度必须介于 1 和 128 之间")
    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    @Length(min = 1, max = 128, message = "证书编号长度必须介于 1 和 128 之间")
    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    @Length(min = 1, max = 32, message = "发证时间长度必须介于 1 和 32 之间")
    public String getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(String certificateDate) {
        this.certificateDate = certificateDate;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_qualification";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432653";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资格证书";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
