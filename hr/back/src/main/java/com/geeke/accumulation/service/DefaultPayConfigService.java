package com.geeke.accumulation.service;

import com.geeke.accumulation.dao.DefaultPayConfigDao;
import com.geeke.accumulation.dto.DefaultPayConfigDto;
import com.geeke.accumulation.entity.DefaultPayConfig;
import com.geeke.accumulation.entity.PayCityConfig;
import com.geeke.accumulation.entity.SocialSecurityFund;
import com.geeke.accumulation.enums.DefaultPayConfigEnum;
import com.geeke.accumulation.enums.ProductType;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 默认配置Service
 * @author
 * @version
 */

@Service("defaultPayConfigService")
@Transactional(readOnly = false)
public class DefaultPayConfigService extends CrudService<DefaultPayConfigDao, DefaultPayConfig> {
    @Autowired
    private DefaultPayConfigDao defaultPayConfigDao;
    @Autowired
    private PayCityConfigService payCityConfigService;
    @Autowired
    private SocialSecurityFundService socialSecurityFundService;

    public  Page<DefaultPayConfigDto> listDtoPage(SearchParams searchParams, String companyId) {
        Page<DefaultPayConfig> result = super.listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );

        return new Page<>(result.getTotal(), entityToDto(result.getRows(),companyId));
    }

    public DefaultPayConfig save(DefaultPayConfig defaultPayConfig) {
        boolean isInsert = StringUtils.isNullOrEmpty(defaultPayConfig.getId());
        // 插入
        if (isInsert) {
            insertVerify(defaultPayConfig);
            defaultPayConfig = super.save(defaultPayConfig);
        } else {
            updateVerify(defaultPayConfig);
            super.save(defaultPayConfig);
        }

        return defaultPayConfig;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> bulkUpdateWithVerify(List<DefaultPayConfig> defaultPayConfigs) {
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return null;
        }
        for (DefaultPayConfig defaultPayConfig : defaultPayConfigs) {
            updateVerify(defaultPayConfig);
        }
        return super.bulkUpdate(defaultPayConfigs);
    }

    @Transactional(rollbackFor = Exception.class)
    public int bulkDeleteWithVerify(List<DefaultPayConfig> defaultPayConfigs) {
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return 0;
        }
        for (DefaultPayConfig defaultPayConfig : defaultPayConfigs) {
            DefaultPayConfig oldDefaultPayConfig = super.get(defaultPayConfig.getId());
            if (productAssociateEmployeeVerify(oldDefaultPayConfig)) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "产品已有关联数据，无法删除，请检查！"));
            }
        }
        return super.bulkDelete(defaultPayConfigs);
    }

    /**
     * 验证失败：
     * 1. 同城市同产品名称不能重复
     * 2. 同产品类型只允许有一条默认数据
     * @param defaultPayConfig
     */
    public void insertVerify(DefaultPayConfig defaultPayConfig) {
        Parameter companyIdParam = new Parameter("company_id", "=", defaultPayConfig.getCompanyId());
        // 同城市同产品名称不能重复
        Parameter cityParam = new Parameter("pay_city_id", "=", defaultPayConfig.getPayCityId());
        Parameter productTypeParam = new Parameter("product_type", "=", defaultPayConfig.getProductType());
        Parameter productNameParam = new Parameter("product_name", "=", defaultPayConfig.getUnitScale());
        List<DefaultPayConfig> defaultPayConfigs = listAll(Arrays.asList(companyIdParam, cityParam, productTypeParam, productNameParam), null);
        if (CollectionUtils.isNotEmpty(defaultPayConfigs)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该城市已有对应产品，请检查！"));
        }

        // 同产品类型只允许有一条默认数据
        if (!defaultPayConfig.getIsDefault().equals(DefaultPayConfigEnum.IS_DEFAULT.getValue())) {
            return;
        }
        Parameter cityParam1 = new Parameter("pay_city_id", "=", defaultPayConfig.getPayCityId());
        Parameter productTypeParam1 = new Parameter("product_type", "=", defaultPayConfig.getProductType());
        Parameter isDefaultParam1 = new Parameter("is_default", "=", defaultPayConfig.getIsDefault());
        List<DefaultPayConfig> defaultPayConfigs1 = listAll(Arrays.asList(companyIdParam, cityParam1, productTypeParam1, isDefaultParam1), null);
        if (CollectionUtils.isNotEmpty(defaultPayConfigs1)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该产品类型已有默认数据。"));
        }
    }

    /**
     * 验证失败：
     * 1. 同城市同产品名称不能重复（不包含自身）
     * 2. 同产品类型只允许有一条默认数据（不包含自身）
     * 3. 该数据不能关联员工数据(老数据是否关联)
     * @param defaultPayConfig
     */
    public void updateVerify(DefaultPayConfig defaultPayConfig) {
        Parameter companyIdParam = new Parameter("company_id", "=", defaultPayConfig.getCompanyId());
        Parameter idParam = new Parameter("id", "<>", defaultPayConfig.getId());
        // 同城市同产品名称不能重复
        Parameter cityParam = new Parameter("pay_city_id", "=", defaultPayConfig.getPayCityId());
        Parameter productTypeParam = new Parameter("product_type", "=", defaultPayConfig.getProductType());
        Parameter productNameParam = new Parameter("product_name", "=", defaultPayConfig.getUnitScale());
        List<DefaultPayConfig> defaultPayConfigs = listAll(Arrays.asList(companyIdParam, idParam, cityParam, productTypeParam, productNameParam), null);
        if (CollectionUtils.isNotEmpty(defaultPayConfigs)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该城市已有对应产品，请检查！"));
        }

        // 同产品类型只允许有一条默认数据
        if (defaultPayConfig.getIsDefault().equals(DefaultPayConfigEnum.IS_DEFAULT.getValue())) {
            Parameter cityParam1 = new Parameter("pay_city_id", "=", defaultPayConfig.getPayCityId());
            Parameter productTypeParam1 = new Parameter("product_type", "=", defaultPayConfig.getProductType());
            Parameter isDefaultParam1 = new Parameter("is_default", "=", defaultPayConfig.getIsDefault());
            List<DefaultPayConfig> defaultPayConfigs1 = listAll(Arrays.asList(companyIdParam, idParam, cityParam1, productTypeParam1, isDefaultParam1), null);
            if (CollectionUtils.isNotEmpty(defaultPayConfigs1)) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该产品类型已有默认数据。"));
            }
        }

        // 该数据不能关联员工数据
        DefaultPayConfig oldDefaultPayConfig = super.get(defaultPayConfig.getId());
        if (productAssociateEmployeeVerify(oldDefaultPayConfig)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该产品已关联数据，无法修改。"));
        }

    }

    public boolean productAssociateEmployeeVerify(DefaultPayConfig defaultPayConfig) {
        Parameter companyIdParam = new Parameter("company_id", "=", defaultPayConfig.getCompanyId());
        PayCityConfig payCityConfig = payCityConfigService.get(defaultPayConfig.getPayCityId());
        Parameter cityNameParam = new Parameter("city_name", "=", payCityConfig.getName());
        Parameter productNameParam = new Parameter("default_pay_config_id", "=", defaultPayConfig.getId());
        List<SocialSecurityFund> socialSecurityFunds = socialSecurityFundService.listAll(Arrays.asList(companyIdParam, cityNameParam, productNameParam), null);

        return CollectionUtils.isNotEmpty(socialSecurityFunds);
    }

    public List<DefaultPayConfigDto> entityToDto(List<DefaultPayConfig> defaultPayConfigs, String companyId) {
        ArrayList<DefaultPayConfigDto> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return result;
        }
        // 所有城市
        Parameter companyIdParam = new Parameter("company_id", "=", companyId);
        List<Parameter> parameters = Arrays.asList(companyIdParam);
        Map<String, PayCityConfig> allCity = payCityConfigService.getCityIdMap(parameters);
        // 组装
        for (DefaultPayConfig defaultPayConfig : defaultPayConfigs) {
            DefaultPayConfigDto defaultPayConfigDto = new DefaultPayConfigDto();
            BeanUtils.copyProperties(defaultPayConfig, defaultPayConfigDto);
            PayCityConfig payCityConfig = allCity.get(defaultPayConfig.getPayCityId());
            defaultPayConfigDto.setCityName(payCityConfig != null ? payCityConfig.getPayCityName() : "");
            defaultPayConfigDto.setSocialAvgAmount(payCityConfig != null ? payCityConfig.getSocialAvgAmount() : "");

            result.add(defaultPayConfigDto);
        }
        return result;
    }
}
