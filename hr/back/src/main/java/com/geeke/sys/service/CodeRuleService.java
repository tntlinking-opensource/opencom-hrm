package com.geeke.sys.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.CodeRuleDao;
import com.geeke.sys.entity.CodeRule;
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
 * 系统编码规则Service
 * @author
 * @version
 */

@Service("codeRuleService")
@Transactional(readOnly = true)
public class CodeRuleService extends CrudService<CodeRuleDao, CodeRule> {

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public CodeRule save(CodeRule codeRule) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // 编码重复验证

        colMaps.clear();

        colMaps.put("code", "code");

        colMaps.put("del_flag", "delFlag");

        if (exists(dao, codeRule, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "已存在相同编码"));
        }

        CodeRule codeRuleTemp = super.save(codeRule);

        return codeRuleTemp;
    }

    public List<CodeRule> listNotInCompany(String companyId) {
        return dao.listNotInCompany(companyId);
    }
}
