package com.geeke.wf.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.geeke.common.persistence.CrudDao;
import com.geeke.wf.entity.WfDraft;

/**
 * 我的草稿DAO接口
 * @author lys
 * @version 2021-04-27
 */
@Mapper
public interface WfDraftDao extends CrudDao<WfDraft> {
	
}