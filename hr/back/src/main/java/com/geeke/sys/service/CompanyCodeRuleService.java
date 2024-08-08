package com.geeke.sys.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.CompanyCodeRuleDao;
import com.geeke.sys.entity.CompanyCodeRule;
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
 * 公司编码规则Service
 * @author
 * @version
 */

@Service("companyCodeRuleService")
@Transactional(readOnly = true)
public class CompanyCodeRuleService extends CrudService<CompanyCodeRuleDao, CompanyCodeRule> {

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public CompanyCodeRule save(CompanyCodeRule companyCodeRule) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // 公司，编码验证

        colMaps.clear();

        colMaps.put("company_id", "company.id");

        colMaps.put("code", "code");

        colMaps.put("del_flag", "delFlag");

        if (exists(dao, companyCodeRule, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "已存在相同的数据（公司，编码）"));
        }

        CompanyCodeRule companyCodeRuleTemp = super.save(companyCodeRule);

        return companyCodeRuleTemp;
    }
}
