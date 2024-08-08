package com.geeke.induction.contract.dao;

import com.geeke.induction.contract.entity.InductionContractRenewal;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 合约续签DAO接口
 * @author
 * @version
 */
@Mapper
public interface InductionContractRenewalDao extends WfCrudDao<InductionContractRenewal> {}
