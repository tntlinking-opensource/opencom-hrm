package com.geeke.schedule.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.schedule.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 定时任务管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface ScheduleJobDao extends CrudDao<ScheduleJob> {}
