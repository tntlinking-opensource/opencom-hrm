package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.Theme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统主题DAO接口
 * @author
 * @version
 */
@Mapper
public interface ThemeDao extends CrudDao<Theme> {
    void updateIsDefault();
}
