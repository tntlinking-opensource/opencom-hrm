package com.geeke.recruit.dao;

import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * recruitDAO接口
 * @author
 * @version
 */
@Mapper
public interface TsmRecruitDao extends WfCrudDao<TsmRecruit> {}
