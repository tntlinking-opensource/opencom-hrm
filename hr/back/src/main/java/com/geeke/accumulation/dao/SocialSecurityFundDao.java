package com.geeke.accumulation.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.accumulation.entity.SocialSecurityFund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社保公积金DAO接口
 * @author
 * @version
 */
@Mapper
public interface SocialSecurityFundDao extends CrudDao<SocialSecurityFund> {
    int bulkDeleteByEmployId(@Param("entitys")List<SocialSecurityFund> entitys);
}
