package com.geeke.induction.dao;

import com.geeke.induction.entity.TsmEmergencyContact;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * inductionDAO接口
 * @author
 * @version
 */
@Mapper
public interface TsmEmergencyContactDao extends WfCrudDao<TsmEmergencyContact> {}
