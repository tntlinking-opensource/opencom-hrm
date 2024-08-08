package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.SysSeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统设置DAO接口
 * @author
 * @version
 */
@Mapper
public interface SysSetingDao extends CrudDao<SysSeting> {}
