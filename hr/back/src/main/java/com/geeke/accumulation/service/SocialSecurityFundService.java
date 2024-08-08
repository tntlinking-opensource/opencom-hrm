package com.geeke.accumulation.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.geeke.accumulation.dto.SocialSecurityFundDto;
import com.geeke.accumulation.dto.SocialSecurityFundExportDto;
import com.geeke.accumulation.entity.DefaultPayConfig;
import com.geeke.accumulation.entity.PayCityConfig;
import com.geeke.accumulation.enums.DefaultConfigEnableEnum;
import com.geeke.accumulation.enums.DefaultConfigStatus;
import com.geeke.accumulation.enums.DefaultPayConfigEnum;
import com.geeke.accumulation.enums.ProductType;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.common.service.ServiceException;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.accumulation.dao.SocialSecurityFundDao;
import com.geeke.accumulation.entity.SocialSecurityFund;
import com.geeke.salaryArchives.entity.SalaryArchives;
import com.geeke.salaryArchives.service.SalaryArchivesService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.io.Reader;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * 社保公积金Service
 * @author
 * @version
 */

@Service("socialSecurityFundService")
@Transactional(readOnly = false)
public class SocialSecurityFundService extends CrudService<SocialSecurityFundDao, SocialSecurityFund> {
    @Autowired
    private SocialSecurityFundDao socialSecurityFundDao;
    @Lazy
    @Autowired
    private SalaryArchivesService salaryArchivesService;
    @Lazy
    @Autowired
    private PayCityConfigService payCityConfigService;
    @Lazy
    @Autowired
    private DefaultPayConfigService defaultPayConfigService;

    public SocialSecurityFund saveWithVerify(SocialSecurityFund socialSecurityFund) {
        saveVerify(socialSecurityFund);
        return super.save(socialSecurityFund);
    }

    public SocialSecurityFundDto get(String id) {
        SocialSecurityFund socialSecurityFund = super.get(id);
        SocialSecurityFundDto socialSecurityFundDto = entityToDto(socialSecurityFund);
        return socialSecurityFundDto;
    }

    /**
     * 全量初始化
     * @param companyId
     */
    public void initAllEmployeeConfig(String companyId) {
        Parameter companyIdParam = new Parameter("company_id", "=", companyId);
        List<SalaryArchives> allEmployee = salaryArchivesService.listAll(Arrays.asList(companyIdParam), null);
        if (CollectionUtils.isEmpty(allEmployee)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请先初始化薪资档案"));
        }
        List<String> employeeNoList = allEmployee.stream().map(SalaryArchives::getEmployeeNo).collect(Collectors.toList());
        initEmployConfig(employeeNoList, companyId, false);
    }
    /**
     * 初始化社保公积金
     * verify: true-员工数据已存在将抛出异常， false-删除原数据，然后新增
     * @param employeeNoList
     * @param companyId
     * @param verify
     */
    @Transactional(rollbackFor = Exception.class)
    public void initEmployConfig(List<String> employeeNoList, String companyId, boolean verify) {
        if (CollectionUtils.isEmpty(employeeNoList)) {
            return;
        }
        // 删除已存在的记录
        Parameter companyIdParam = new Parameter("company_id", "=", companyId);
        Parameter employeeNoParam = new Parameter("employee_no", "in", employeeNoList);
        List<SocialSecurityFund> socialSecurityFunds = super.listAll(Arrays.asList(companyIdParam, employeeNoParam), null);
        if (CollectionUtils.isNotEmpty(socialSecurityFunds)) {
            if (verify) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "员工数据已存在，是否继续初始化"));
            }
            super.bulkDelete(socialSecurityFunds);
        }
        // 生成数据
        List<SocialSecurityFund> insertData = generateInitData(employeeNoList, companyId);
        // 保存
        if (CollectionUtils.isNotEmpty(insertData)) {
            socialSecurityFundDao.bulkDeleteByEmployId(insertData);
            socialSecurityFundDao.bulkInsert(insertData);
        }
    }


    public void saveVerify(SocialSecurityFund socialSecurityFund) {
        HashMap<String, String> map = Maps.newHashMap();
        map.put("company_id", "companyId");
        map.put("employee_no", "employeeNo");
        map.put("product_type", "productType");
        map.put("enable", "enable");
        if (super.exists(socialSecurityFundDao, socialSecurityFund, map)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "数据重复，请检查后重新填写"));
        }
    }

    public SocialSecurityFundDto entityToDto(SocialSecurityFund socialSecurityFund) {

        if (socialSecurityFund == null) {
            return null;
        }
        SocialSecurityFundDto result = new SocialSecurityFundDto();
        BeanUtils.copyProperties(socialSecurityFund, result);
        // 组装员工信息
        Parameter companyIdParam = new Parameter("company_id", "=", result.getCompanyId());
        Parameter employeeNoParam = new Parameter("employee_no", "=", result.getEmployeeNo());
        List<SalaryArchives> salaryArchivesList = salaryArchivesService.listAll(Arrays.asList(companyIdParam, employeeNoParam), null);
        if (CollectionUtils.isNotEmpty(salaryArchivesList)) {
            SalaryArchives salaryArchives = salaryArchivesList.get(0);
            result.setEmployeeName(salaryArchives.getEmployeeName());
            result.setEntryTime(salaryArchives.getEntryTime());
            // 组装部门
            Map<String, String> companyDept = salaryArchivesService.getCompanyDept(result.getCompanyId());
            result.setDeptId(salaryArchives.getDeptId());
            result.setDeptName(companyDept.get(salaryArchives.getDeptId()));
        }
        // 组装社会平均工资
        Map<String, PayCityConfig> cityNameMap = payCityConfigService.getCityNameMap(Arrays.asList(companyIdParam));
        PayCityConfig payCityConfig = cityNameMap.get(result.getCityName());
        result.setSocialAvgAmount(payCityConfig == null ? null : payCityConfig.getSocialAvgAmount());
        result.setPayCityConfigId(payCityConfig == null ? null : payCityConfig.getId());
        // 组装默认配置
        DefaultPayConfig defaultConfig = getDefaultConfigByProductName(socialSecurityFund, payCityConfig);
        result.setCompanyHighBase(defaultConfig.getCompanyHighBase());
        result.setCompanyLowBase(defaultConfig.getCompanyLowBase());
        result.setPersonHighBase(defaultConfig.getPersonHighBase());
        result.setPersonLowBase(defaultConfig.getPersonLowBase());

        return result;
    }

    public List<SocialSecurityFundDto> entityListToDtoList(List<SocialSecurityFund> socialSecurityFundList, String companyId) {
        ArrayList<SocialSecurityFundDto> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(socialSecurityFundList)) {
            return result;
        }
        Parameter companyIdParam = new Parameter("company_id", "=", companyId);
        Map<String, PayCityConfig> cityNameMap = payCityConfigService.getCityNameMap(Arrays.asList(companyIdParam));
        Map<String, String> companyDept = salaryArchivesService.getCompanyDept(companyId);
        for (SocialSecurityFund socialSecurityFund : socialSecurityFundList) {
            SocialSecurityFundDto socialSecurityFundDto = new SocialSecurityFundDto();
            BeanUtils.copyProperties(socialSecurityFund, socialSecurityFundDto);
            // 组装员工信息
            Parameter employeeNoParam = new Parameter("employee_no", "=", socialSecurityFundDto.getEmployeeNo());
            List<SalaryArchives> salaryArchivesList = salaryArchivesService.listAll(Arrays.asList(companyIdParam, employeeNoParam), null);
            if (CollectionUtils.isNotEmpty(salaryArchivesList)) {
                SalaryArchives salaryArchives = salaryArchivesList.get(0);
                socialSecurityFundDto.setEmployeeName(salaryArchives.getEmployeeName());
                socialSecurityFundDto.setEntryTime(salaryArchives.getEntryTime());
                // 组装部门
                socialSecurityFundDto.setDeptId(salaryArchives.getDeptId());
                socialSecurityFundDto.setDeptName(companyDept.get(salaryArchives.getDeptId()));
            }
            // 组装社会平均工资
            PayCityConfig payCityConfig = cityNameMap.get(socialSecurityFundDto.getCityName());
            socialSecurityFundDto.setSocialAvgAmount(payCityConfig == null ? null : payCityConfig.getSocialAvgAmount());
            socialSecurityFundDto.setPayCityConfigId(payCityConfig == null ? null : payCityConfig.getId());
            // 组装默认配置
            if (payCityConfig != null) {
                DefaultPayConfig defaultConfig = getDefaultConfigByProductName(socialSecurityFund, payCityConfig);
                socialSecurityFundDto.setCompanyHighBase((defaultConfig != null && defaultConfig.getCompanyHighBase() != null) ? defaultConfig.getCompanyHighBase() : "0");
                socialSecurityFundDto.setCompanyLowBase((defaultConfig != null && defaultConfig.getCompanyLowBase() != null) ? defaultConfig.getCompanyLowBase() : "0");
                socialSecurityFundDto.setPersonHighBase((defaultConfig != null && defaultConfig.getPersonHighBase() != null) ? defaultConfig.getPersonHighBase() : "0");
                socialSecurityFundDto.setCompanyLowBase((defaultConfig != null && defaultConfig.getCompanyLowBase() != null) ? defaultConfig.getCompanyLowBase() : "0");
            }
            // 组装所属状态
            if(socialSecurityFundDto.getDiscountStatus() != null) {
                socialSecurityFundDto.setDiscountStatusName(socialSecurityFundDto.getDiscountStatus().equals(DefaultConfigStatus.NORMAL_DISCOUNT.getValue()) ? DefaultConfigStatus.NORMAL_DISCOUNT.getDesc() : DefaultConfigStatus.NO_DISCOUNT.getDesc());
            }
            // 组装启用状态
            if(socialSecurityFundDto.getEnable() != null) {
                socialSecurityFundDto.setEnableName(socialSecurityFundDto.getEnable().equals("0") ? "启用" : "停用");
            }
            result.add(socialSecurityFundDto);
        }
        return result;
    }

    public DefaultPayConfig getDefaultConfigByProductName(SocialSecurityFund socialSecurityFund, PayCityConfig payCityConfig) {
        Parameter companyIdParam = new Parameter("company_id", "=", socialSecurityFund.getCompanyId());
        Parameter cityParam = new Parameter("pay_city_id", "=", payCityConfig.getId());
        Parameter productTypeParam = new Parameter("product_type", "=", socialSecurityFund.getProductType());
        Parameter unitScaleParam = new Parameter("unit_scale", "=", socialSecurityFund.getCompanyPayScale());
        Parameter personScaleParam = new Parameter("person_scale", "=", socialSecurityFund.getPersonPayScale());
        List<DefaultPayConfig> defaultPayConfigs = defaultPayConfigService.listAll(Arrays.asList(companyIdParam, cityParam, productTypeParam, unitScaleParam, personScaleParam), null);
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return null;
        }
        return defaultPayConfigs.get(0);
    }


    /**
     * 生成社保公积金初始化数据
     * @param employeeNoList
     * @param companyId
     * @return
     */
    public List<SocialSecurityFund> generateInitData(List<String> employeeNoList, String companyId) {
        Parameter companyIdParam = new Parameter("company_id", "=", companyId);
        // 获取城市表配置
        List<PayCityConfig> payCityConfigs = payCityConfigService.listAll(Arrays.asList(companyIdParam), null);
        if (CollectionUtils.isEmpty(payCityConfigs)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "城市表配置不存在！"));
        }
        PayCityConfig payCityConfig = payCityConfigs.get(0);
        // 获取默认配置数据
        Parameter payCityIdParam = new Parameter("pay_city_id", "=", payCityConfig.getId());
        Parameter isDefaultParam = new Parameter("is_default", "=", DefaultPayConfigEnum.IS_DEFAULT.getValue());
        List<DefaultPayConfig> defaultPayConfigs = defaultPayConfigService.listAll(Arrays.asList(companyIdParam, payCityIdParam, isDefaultParam), null);
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请先初始化薪资档案！"));
        }
        ArrayList<SocialSecurityFund> result = Lists.newArrayList();
        // 组装
        for (String employeeNo : employeeNoList) {
            List<SocialSecurityFund> socialSecurityFunds = associateFundData(employeeNo, defaultPayConfigs, payCityConfig, companyId);
            if (CollectionUtils.isNotEmpty(socialSecurityFunds)) {
                result.addAll(socialSecurityFunds);
            }
        }
        return result;
    }

    private List<SocialSecurityFund> associateFundData(String employeeNo, List<DefaultPayConfig> defaultPayConfigs, PayCityConfig payCityConfig, String companyId) {
        ArrayList<SocialSecurityFund> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return result;
        }

        for (DefaultPayConfig defaultPayConfig : defaultPayConfigs) {
            SocialSecurityFund socialSecurityFund = new SocialSecurityFund();
            socialSecurityFund.setEmployeeNo(employeeNo);
            socialSecurityFund.setCityName(payCityConfig.getPayCityName());
            socialSecurityFund.setDefaultPayConfigId(defaultPayConfig.getId());
            socialSecurityFund.setCompanyId(companyId);

            socialSecurityFund.setProbationSalary("0");
            socialSecurityFund.setRagularSalary("0");
            socialSecurityFund.setProductType(defaultPayConfig.getProductType());
            socialSecurityFund.setProductName(defaultPayConfig.getProductName());
            socialSecurityFund.setDiscountStatus(defaultPayConfig.getStatus());
            socialSecurityFund.setCompanyPayScale(defaultPayConfig.getUnitScale());
            socialSecurityFund.setCompanyPayBase(defaultPayConfig.getCompanyLowBase());
            socialSecurityFund.setPersonPayScale(defaultPayConfig.getPersonScale());
            socialSecurityFund.setPersonPayBase(defaultPayConfig.getPersonLowBase());
            socialSecurityFund.setDiscountStatus(defaultPayConfig.getStatus());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = LocalDateTime.of(now.getYear(), 1, 1, 0, 0, 0);
            LocalDateTime endTime = LocalDateTime.of(now.getYear(), 12, 31, 23, 59, 59);
            socialSecurityFund.setEffectEndDate(endTime.format(DateTimeFormatter.ofPattern("yyyyMM")));
            socialSecurityFund.setEffectStartDate((startTime.format(DateTimeFormatter.ofPattern("yyyyMM"))));
            socialSecurityFund.setEnable(DefaultConfigEnableEnum.ENABLE.getValue());

            socialSecurityFund.preInsert();
            result.add(socialSecurityFund);
        }
        return result;
    }

    public List<String> importDataFromExcel(InputStream inputStream) {
        ArrayList<String> failedResult = Lists.newArrayList();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        addHeader(reader);
        List<SocialSecurityFund> read = reader.read(2, 3, SocialSecurityFund.class);
        if(CollectionUtils.isEmpty(read)) {
            return failedResult;
        }

        // 补充entity，verify
        for (SocialSecurityFund socialSecurityFund : read) {
            boolean verifyPass = excelDataProceed(socialSecurityFund);
            if (!verifyPass) {
                failedResult.add(socialSecurityFund.getEmployeeNo());
                continue;
            }
            try {
                saveVerify(socialSecurityFund);
                socialSecurityFundDao.insert(socialSecurityFund);
            } catch (Exception e) {
                failedResult.add(socialSecurityFund.getEmployeeNo());
            }

        }
        // exception
        if(CollectionUtils.isNotEmpty(failedResult)) {
            StringBuilder stringBuilder = new StringBuilder();
            failedResult.forEach(item -> stringBuilder.append("、\n").append(item));
            String msg = failedResult.size() == read.size() ? "导入失败！" : "部分数据导入失败， 员工工号： " + stringBuilder.toString().replaceFirst("、\n", "");
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, msg));
        }
        return failedResult;

    }

    public boolean excelDataProceed(SocialSecurityFund socialSecurityFund) {
        Parameter companyIdParam = new Parameter("company_id", "=", SessionUtils.getUser().getCompanyId());
        // 工号校验
        if (Strings.isBlank(socialSecurityFund.getEmployeeNo())) {
            return false;
        }
        Parameter employeeNoParam = new Parameter("employee_no", "=", socialSecurityFund.getEmployeeNo());
        List<SalaryArchives> salaryArchives = salaryArchivesService.listAll(Arrays.asList(companyIdParam, employeeNoParam), null);
        if (CollectionUtils.isEmpty(salaryArchives)) {
            return false;
        }
        // 城市校验
        if (Strings.isBlank(socialSecurityFund.getCityName())) {
            return false;
        }
        Parameter cityNameParam = new Parameter("pay_city_name", "=", socialSecurityFund.getCityName());
        List<PayCityConfig> payCityConfigs = payCityConfigService.listAll(Arrays.asList(companyIdParam, cityNameParam), null);
        if (CollectionUtils.isEmpty(payCityConfigs)) {
            return false;
        }
        // 产品类型
        boolean checkType = ProductType.checkType(socialSecurityFund.getProductType());
        if (!checkType) {
            return false;
        }
        // 产品默认配置
        boolean checkStatus = DefaultConfigStatus.checkStatus(socialSecurityFund.getDiscountStatus());
        if (!checkStatus) {
            return false;
        }
        Parameter productTypeParam = new Parameter("product_type", "=", socialSecurityFund.getProductType());
        Parameter payCityIdParam = new Parameter("pay_city_id", "=", payCityConfigs.get(0).getId());
        Parameter isDefaultParam = new Parameter("is_default", "=", DefaultPayConfigEnum.IS_DEFAULT.getValue());
        List<DefaultPayConfig> defaultPayConfigs = defaultPayConfigService.listAll(Arrays.asList(companyIdParam, payCityIdParam, productTypeParam, isDefaultParam), null);
        if (CollectionUtils.isEmpty(defaultPayConfigs)) {
            return false;
        }

        setExcelDataProperty(socialSecurityFund, defaultPayConfigs.get(0));
        return true;
    }

    public void setExcelDataProperty(SocialSecurityFund socialSecurityFund, DefaultPayConfig defaultPayConfigs) {
        socialSecurityFund.setCompanyId(SessionUtils.getUser().getCompanyId());
        socialSecurityFund.setDefaultPayConfigId(defaultPayConfigs.getId());
        socialSecurityFund.setProductName(defaultPayConfigs.getProductName());
        socialSecurityFund.setCompanyPayScale(defaultPayConfigs.getUnitScale());
        socialSecurityFund.setPersonPayScale(defaultPayConfigs.getPersonScale());
        socialSecurityFund.setCompanyId(SessionUtils.getUser().getCompanyId());

        socialSecurityFund.setEnable(DefaultConfigEnableEnum.ENABLE.getValue());
        socialSecurityFund.preInsert();

    }

    private void addHeader(ExcelReader reader){
        reader.addHeaderAlias("缴费城市","cityName");
        reader.addHeaderAlias("员工工号","employeeNo");
        reader.addHeaderAlias("试用期工资","probationSalary");
        reader.addHeaderAlias("转正工资","ragularSalary");
        reader.addHeaderAlias("社保公积金类型","productType");
        reader.addHeaderAlias("公司缴纳基数","companyPayBase");
        reader.addHeaderAlias("个人缴纳基数","personPayBase");
        reader.addHeaderAlias("起始年月","effectStartDate");
        reader.addHeaderAlias("终止年月","effectEndDate");
        reader.addHeaderAlias("所属类别(正常折扣/不折扣)","discountStatus");
        reader.addHeaderAlias("备注","remark");
    }

    public void exportExcel(SearchParams searchParams, HttpServletResponse response) {
        String companyId = SessionUtils.getUser().getCompanyId();
        List<Parameter> parameters = Arrays.asList(new Parameter("company_id", "=", companyId));
        parameters.addAll(searchParams.getParams());
        List<SocialSecurityFund> result = this.listAll(
                parameters,
                searchParams.getOrderby()
        );
        List<SocialSecurityFundDto> socialSecurityFundDtos = this.entityListToDtoList(result, companyId);

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String excelName = URLEncoder.encode("比例维护列表信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLS.getValue());
            EasyExcel.write(response.getOutputStream(), SocialSecurityFundExportDto.class)
                    .sheet("比例维护列表信息")
                    .doWrite(socialSecurityFundDtos);
        } catch (Exception e) {
            throw new ServiceException("导出比例维护列表信息失败");
        }
    }
}
