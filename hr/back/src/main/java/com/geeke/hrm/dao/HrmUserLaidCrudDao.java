package com.geeke.hrm.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.hrm.entity.HrmUserLaid;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author TBH
 */
@Mapper
public interface HrmUserLaidCrudDao extends CrudDao<HrmUserLaid> {
}
