package com.geeke.sys.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.PersonalThemeDao;
import com.geeke.sys.entity.PersonalTheme;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 自定义主题Service
 * @author
 * @version
 */

@Service("personalThemeService")
@Transactional(readOnly = true)
public class PersonalThemeService extends CrudService<PersonalThemeDao, PersonalTheme> {}
