package com.geeke.accumulation.service;

import com.geeke.accumulation.dao.DefaultPayConfigDao;
import com.geeke.accumulation.entity.BillDetails;
import com.geeke.accumulation.entity.DefaultPayConfig;
import com.geeke.accumulation.entity.SocialSecurityFund;
import com.geeke.accumulation.enums.DefaultConfigEnableEnum;
import com.geeke.accumulation.enums.DefaultConfigStatus;
import com.geeke.accumulation.enums.DefaultPayConfigEnum;
import com.geeke.accumulation.enums.ProductType;
import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.BaseEntity;
import com.geeke.common.service.CrudService;
import com.geeke.accumulation.dao.PayCityConfigDao;
import com.geeke.accumulation.entity.PayCityConfig;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 缴费城市Service
 * @author
 * @version
 */
@Service("payCityConfigService")
@Transactional(readOnly = false)
public class PayCityConfigService extends CrudService<PayCityConfigDao, PayCityConfig> {
    @Autowired
    private PayCityConfigDao payCityConfigDao;
    @Autowired
    private SocialSecurityFundService socialSecurityFundService;
    @Autowired
    private DefaultPayConfigDao defaultPayConfigDao;
    @Lazy
    @Autowired
    private DefaultPayConfigService defaultPayConfigService;

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public PayCityConfig saveWithDefaultConfig(PayCityConfig payCityConfig) {
        // update or insert
        boolean isInsert = StringUtils.isNullOrEmpty(payCityConfig.getId());
        if (isInsert) {
            insertVerify(payCityConfig);
        } else {
            updateVerify(payCityConfig);
        }
        payCityConfig = super.save(payCityConfig);
        // 生成默认数据(单独调用 bulkInsert, 避免事物冲突)
        insertDefaultData(payCityConfig);
        return payCityConfig;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int deleteCityWithDefaultConfig(PayCityConfig payCityConfig) {
        // 该城市是否关联员工数据
        if (cityAssociateEmployeeVerify(payCityConfig.getId())) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该城市已关联数据，无法删除！"));
        }
        int i = super.delete(payCityConfig);
        DefaultPayConfig deleteParam = new DefaultPayConfig();
        deleteParam.setPayCityId(payCityConfig.getId());
        deleteParam.setUpdateDate(payCityConfig.getUpdateDate());
        deleteParam.setUpdateBy(payCityConfig.getUpdateBy());
        defaultPayConfigDao.deleteByPayCityId(deleteParam);
        return i;
    }

    public void insertVerify(PayCityConfig payCityConfig) {
        Map<String, String> colMaps = Maps.newHashMap();
        colMaps.clear();

        colMaps.put("company_id", "companyId");
        colMaps.put("pay_city_name", "payCityName");
        // 城市名称已经存在 不允许插入
        if (exists(payCityConfigDao, payCityConfig, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, payCityConfig.getPayCityName() + "已经存在"));
        }
    }

    public void updateVerify(PayCityConfig payCityConfig) {
        String payCityId = payCityConfig.getId();
        // 城市对应的配置已经关联员工 不允许修改(以城市名称和产品名称为判断标准)
        if (cityAssociateEmployeeVerify(payCityId)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该城市已关联数据，无法修改"));
        }

        // 城市名称重复 不允许修改
        Map<String, String> colMaps = Maps.newHashMap();
        colMaps.clear();
        colMaps.put("pay_city_name", "payCityName");
        colMaps.put("company_id", "companyId");
        if (exists(payCityConfigDao, payCityConfig, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, payCityConfig.getPayCityName() + "已经存在"));
        }
    }

    /**
     * // 城市对应的配置已经关联员工 不允许修改(以城市名称和产品名称为判断标准)
     * @param payCityId
     * @return
     */
    public boolean cityAssociateEmployeeVerify( String payCityId) {
        PayCityConfig oldPayCity = super.get(payCityId);
        Parameter payCityIdParam = new Parameter("pay_city_id", "=", oldPayCity.getId());
        Parameter companyIdParam = new Parameter("company_id", "=", oldPayCity.getCompanyId());
        List<Parameter> defaultConfigSearchParam = Arrays.asList(payCityIdParam, companyIdParam);
        List<DefaultPayConfig> defaultPayConfigs = defaultPayConfigService.listAll(defaultConfigSearchParam, null);
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return false;
        }
        for (DefaultPayConfig item : defaultPayConfigs) {
            Parameter cityNameParam = new Parameter("city_name", "=", oldPayCity.getPayCityName());
            Parameter productNameParam = new Parameter("default_pay_config_id", "=", item.getId());
            List<Parameter> param = Arrays.asList(companyIdParam, cityNameParam, productNameParam);
            List<SocialSecurityFund> socialSecurityFunds = socialSecurityFundService.listAll(param, null);
            if (CollectionUtils.isNotEmpty(socialSecurityFunds)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成默认数据
     * @param payCityConfig
     * @return
     */
    public List<DefaultPayConfig> generateDefaultData(PayCityConfig payCityConfig) {
        ArrayList<DefaultPayConfig> result = Lists.newArrayList();
        ProductType[] productTypes = ProductType.values();
        for (ProductType productType : productTypes) {
            DefaultPayConfig defaultPayConfig = new DefaultPayConfig();

            defaultPayConfig.setCompanyId(payCityConfig.getCompanyId());
            defaultPayConfig.setPayCityId(payCityConfig.getId());
            defaultPayConfig.setProductType(productType.getType());
            defaultPayConfig.setUnitScale("0");
            defaultPayConfig.setPersonScale("0");
            defaultPayConfig.setProductName(ProductType.getCompleteNameByType(productType.getType(), "0", "0"));
            defaultPayConfig.setCompanyHighBase(new BigDecimal(payCityConfig.getSocialAvgAmount()).multiply(BigDecimal.valueOf(3.00)).toString());
            defaultPayConfig.setCompanyLowBase(new BigDecimal(payCityConfig.getSocialAvgAmount()).multiply(BigDecimal.valueOf(0.60)).toString());
            defaultPayConfig.setPersonHighBase(new BigDecimal(payCityConfig.getSocialAvgAmount()).multiply(BigDecimal.valueOf(3.00)).toString());
            defaultPayConfig.setPersonLowBase(new BigDecimal(payCityConfig.getSocialAvgAmount()).multiply(BigDecimal.valueOf(0.60)).toString());
            defaultPayConfig.setIsDefault(DefaultPayConfigEnum.IS_DEFAULT.getValue());
            defaultPayConfig.setEnable(DefaultConfigEnableEnum.ENABLE.getValue());
            defaultPayConfig.setStatus(DefaultConfigStatus.NORMAL_DISCOUNT.getValue());

            defaultPayConfig.preInsert();

            result.add(defaultPayConfig);
        }
        return result;
    }

    public void insertDefaultData(PayCityConfig payCityConfig) {
        DefaultPayConfig deleteParam = new DefaultPayConfig();
        deleteParam.setPayCityId(payCityConfig.getId());
        deleteParam.setUpdateDate(payCityConfig.getUpdateDate());
        deleteParam.setUpdateBy(payCityConfig.getUpdateBy());
        defaultPayConfigDao.deleteByPayCityId(deleteParam);

        defaultPayConfigDao.bulkInsert(generateDefaultData(payCityConfig));
    }

    /**
     * 获取城市id map
     * @param params
     * @return Map<String, PayCityConfig> Map<ID, PayCityConfig>
     */
    public Map<String, PayCityConfig> getCityIdMap(List<Parameter> params) {
        List<PayCityConfig> payCityConfigs = super.listAll(params, null);
        HashMap<String, PayCityConfig> result = new HashMap<>();
        if (CollectionUtils.isEmpty(payCityConfigs)) {
            return result;
        }
        for (PayCityConfig payCityConfig : payCityConfigs) {
            result.put(payCityConfig.getId(), payCityConfig);
        }
        return result;
    }

    /**
     * 获取城市名称map
     * @param params
     * @return Map<String, PayCityConfig> Map<City name, PayCityConfig>
     */
    public Map<String, PayCityConfig> getCityNameMap(List<Parameter> params) {
        List<PayCityConfig> payCityConfigs = super.listAll(params, null);
        HashMap<String, PayCityConfig> result = new HashMap<>();
        if (CollectionUtils.isEmpty(payCityConfigs)) {
            return result;
        }
        for (PayCityConfig payCityConfig : payCityConfigs) {
            result.put(payCityConfig.getPayCityName(), payCityConfig);
        }
        return result;
    }

}
