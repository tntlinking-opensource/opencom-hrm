package com.geeke.induction.contract.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合约发起DAO接口
 * @author
 * @version
 */
@Mapper
public interface InductionBatchContractDao extends WfCrudDao<InductionBatchContract> {}
