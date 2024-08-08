package com.geeke.basic.service;

import com.geeke.basic.dao.UserPropListDao;
import com.geeke.basic.entity.UserPropList;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础配置Service
 * @author
 * @version
 */

@Service("userPropListService")
@Transactional(readOnly = true)
public class UserPropListService extends CrudService<UserPropListDao, UserPropList> {

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public UserPropList save(UserPropList userPropList) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // code不重复

        colMaps.clear();

        colMaps.put("code", "code");

        colMaps.put("tenant_id", "tenantId");

        if (exists(dao, userPropList, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "code不能重复"));
        }

        UserPropList userPropListTemp = super.save(userPropList);

        return userPropListTemp;
    }
}
