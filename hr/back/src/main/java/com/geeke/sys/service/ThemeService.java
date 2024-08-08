package com.geeke.sys.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.ThemeDao;
import com.geeke.sys.entity.Theme;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统主题Service
 * @author
 * @version
 */

@Service("themeService")
@Transactional(readOnly = true)
public class ThemeService extends CrudService<ThemeDao, Theme> {

    /** 默认主题 */
    private final String isDefault = "1";

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public Theme save(Theme theme) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // 名称已存在

        colMaps.clear();

        colMaps.put("name", "name");

        colMaps.put("del_flag", "delFlag");

        if (exists(dao, theme, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "	 主题名称已存在"));
        }

        // 保存或编辑 修改为默认主题，需要把原有默认主题修改为非默认
        if (theme.getIsDefault().equals(isDefault)) {
            dao.updateIsDefault();
        }

        Theme themeTemp = super.save(theme);

        return themeTemp;
    }
}
