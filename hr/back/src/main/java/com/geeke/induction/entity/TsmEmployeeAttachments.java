package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
@AllArgsConstructor
public class TsmEmployeeAttachments extends WfDataEntity<TsmEmployeeAttachments> {

    private static final long serialVersionUID = 1457254743943432660L;

    private String inductionId; // 员工ID

    private String attachmentName; // 附件名称

    private String attachmentUrl; // 附件地址

    private DictItem isRequired = new DictItem("0"); // 是否必传，0-非必传，1-必传

    private DictItem isUploaded = new DictItem("0"); // 是否已上传，0-未上传，1-已上传

    // 构造方法
    public TsmEmployeeAttachments() {
        super();
    }

    public TsmEmployeeAttachments(String id) {
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

    @Length(min = 1, max = 255, message = "附件名称长度必须介于 1 和 255 之间")
    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Length(min = 1, max = 255, message = "附件地址长度必须介于 1 和 255 之间")
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    @NotNull(message = "是否必传，0-非必传，1-必传不能为空")
    public DictItem getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(DictItem isRequired) {
        this.isRequired = isRequired;
    }

    @NotNull(message = "是否已上传，0-未上传，1-已上传不能为空")
    public DictItem getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(DictItem isUploaded) {
        this.isUploaded = isUploaded;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_employee_attachments";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432660";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "员工附件";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
