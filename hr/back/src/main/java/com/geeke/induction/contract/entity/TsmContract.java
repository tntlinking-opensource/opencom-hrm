package com.geeke.induction.contract.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictItem;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 合同Entity
 * @author
 * @version
 */

public class TsmContract extends DataEntity<TsmContract> {

    private static final long serialVersionUID = 1538459003414151442L;

    private String batchContractId; // 发起合约关联ID

    private String contractId; // 合约id;契约锁定义的id

    private String contractFileId; // 合同文件

    private DictItem isRenew; // 合约分类

    private DictItem ifSign; // 是否签字

    private String signDate; // 签字时间

    private String sealDate; // 盖章时间

    // 构造方法
    public TsmContract() {
        super();
    }

    public TsmContract(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "发起合约信息不能为空")
    public String getBatchContractId() {
        return batchContractId;
    }

    public void setBatchContractId(String batchContractId) {
        this.batchContractId = batchContractId;
    }

    @Length(min = 0, max = 255, message = "合约id;契约锁定义的id长度必须介于 0 和 255 之间")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Length(min = 0, max = 255, message = "合同文件长度必须介于 0 和 255 之间")
    public String getContractFileId() {
        return contractFileId;
    }

    public void setContractFileId(String contractFileId) {
        this.contractFileId = contractFileId;
    }

    public DictItem getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(DictItem isRenew) {
        this.isRenew = isRenew;
    }

    public DictItem getIfSign() {
        return ifSign;
    }

    public void setIfSign(DictItem ifSign) {
        this.ifSign = ifSign;
    }

    @Length(min = 0, max = 255, message = "签字时间长度必须介于 0 和 255 之间")
    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    @Length(min = 0, max = 255, message = "盖章时间长度必须介于 0 和 255 之间")
    public String getSealDate() {
        return sealDate;
    }

    public void setSealDate(String sealDate) {
        this.sealDate = sealDate;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_contract";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1538459003414151442";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "合同";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
