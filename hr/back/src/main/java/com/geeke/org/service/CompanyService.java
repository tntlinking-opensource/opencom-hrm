package com.geeke.org.service;

import com.geeke.common.data.Parameter;
import com.geeke.common.service.TreeService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.dao.CompanyDao;
import com.geeke.org.entity.Company;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 公司管理Service
 * @author
 * @version
 */

@Service("companyService")
@Transactional(readOnly = true)
public class CompanyService extends TreeService<CompanyDao, Company> {

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public Company save(Company company) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // code

        colMaps.clear();

        colMaps.put("code", "code");

        if (exists(dao, company, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "已存在相同的公司编码"));
        }

        Company companyTemp = super.save(company);

        return companyTemp;
    }

    public Optional<Company> getByCompanyName(String companyName) {
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("full_name", "=", companyName));
        return listAll(params, "").stream().findFirst();
    }
}
