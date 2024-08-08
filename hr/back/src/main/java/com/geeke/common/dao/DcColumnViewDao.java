package com.geeke.common.dao;

import com.geeke.common.entity.DcColumnView;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 查询列DAO接口
 * @author
 * @version
 */
@Mapper
public interface DcColumnViewDao extends CrudDao<DcColumnView> {}
