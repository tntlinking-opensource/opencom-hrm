package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmAssetInformation extends BaseInductionEntity<TsmAssetInformation> {

    private static final long serialVersionUID = 1457254743943432659L;

    private String assetName; // 资产名称

    private String employers; // 单位

    private String assetNumber; // 资产编号

    private String collectionTime; // 领用时间

    private String returnTime; // 归还时间

    // 构造方法
    public TsmAssetInformation() {
        super();
    }

    public TsmAssetInformation(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 1, max = 255, message = "资产名称长度必须介于 1 和 255 之间")
    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    @Length(min = 1, max = 100, message = "单位长度必须介于 1 和 100 之间")
    public String getEmployers() {
        return employers;
    }

    public void setEmployers(String employers) {
        this.employers = employers;
    }

    @Length(min = 1, max = 50, message = "资产编号长度必须介于 1 和 50 之间")
    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    @Length(min = 1, max = 50, message = "领用时间长度必须介于 1 和 50 之间")
    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    @Length(min = 0, max = 100, message = "归还时间长度必须介于 0 和 100 之间")
    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_asset_information";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943432659";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "资产信息";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
