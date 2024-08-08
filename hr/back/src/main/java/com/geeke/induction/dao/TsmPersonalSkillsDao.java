package com.geeke.induction.dao;

import com.geeke.induction.entity.TsmPersonalSkills;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * inductionDAO接口
 * @author
 * @version
 */
@Mapper
public interface TsmPersonalSkillsDao extends WfCrudDao<TsmPersonalSkills> {}
