package com.geeke.noticesend.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.geeke.common.persistence.CrudDao;
import com.geeke.noticesend.entity.NoticeReceiveSite;

/**
 * 消息接收站点DAO接口
 * @author ycy
 * @version 2021-11-24
 */
@Mapper
public interface NoticeReceiveSiteDao extends CrudDao<NoticeReceiveSite> {
}