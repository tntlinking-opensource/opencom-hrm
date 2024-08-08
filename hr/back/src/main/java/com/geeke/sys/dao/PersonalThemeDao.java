package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.PersonalTheme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义主题DAO接口
 * @author
 * @version
 */
@Mapper
public interface PersonalThemeDao extends CrudDao<PersonalTheme> {}
