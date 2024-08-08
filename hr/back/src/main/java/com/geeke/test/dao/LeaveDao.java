package com.geeke.test.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.geeke.wf.persistence.WfCrudDao;

import com.geeke.test.entity.Leave;

/**
 * 请假管理DAO接口
 * @author lys
 * @version 2022-01-28
 */
@Mapper
public interface LeaveDao extends WfCrudDao<Leave> {
}