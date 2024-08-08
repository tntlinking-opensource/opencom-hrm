package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 字典管理DAO接口
 * @author
 * @version
 */
@Mapper
public interface DictItemDao extends CrudDao<DictItem> {}
