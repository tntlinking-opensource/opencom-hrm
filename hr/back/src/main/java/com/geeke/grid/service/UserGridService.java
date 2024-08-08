package com.geeke.grid.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.grid.dao.UserGridDao;
import com.geeke.grid.entity.UserGrid;
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
 * 用户自定义布局Service
 * @author
 * @version
 */

@Service("userGridService")
@Transactional(readOnly = true)
public class UserGridService extends CrudService<UserGridDao, UserGrid> {

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public UserGrid save(UserGrid userGrid) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // 唯一布局验证

        colMaps.clear();

        colMaps.put("user_id", "userId");

        colMaps.put("router_id", "routerId");

        colMaps.put("del_flag", "delFlag");

        if (exists(dao, userGrid, colMaps)) {
            this.doUpdate(userGrid);
            return userGrid;
        }

        UserGrid userGridTemp = super.save(userGrid);

        return userGridTemp;
    }
}
