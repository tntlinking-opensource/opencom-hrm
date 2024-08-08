package com.geeke.hrm.dao;

import com.geeke.hrm.entity.HrmUserLaid;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 离职申请DAO接口
 * @author
 * @version
 */
@Mapper
public interface HrmUserLaidDao extends WfCrudDao<HrmUserLaid> {}
