package com.geeke.induction.contract.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.induction.contract.entity.TsmContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合同DAO接口
 * @author
 * @version
 */
@Mapper
public interface TsmContractDao extends CrudDao<TsmContract> {

    /**
     * 根据发起合约信息ID获取单条数据
     * @param batchContractId
     * @return
     */
    public TsmContract getByBatchContractId(@Param("batchContractId") String batchContractId);

    /**
     * 根据契约锁合同ID获取单条数据
     * @param contractId
     * @return
     */
    public TsmContract getByContractId(@Param("contractId") String contractId);

}
