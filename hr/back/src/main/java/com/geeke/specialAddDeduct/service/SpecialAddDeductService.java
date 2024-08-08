package com.geeke.specialAddDeduct.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.salaryArchives.dao.SalaryArchivesDao;
import com.geeke.salaryArchives.dto.SalaryArchivesDto;
import com.geeke.salaryArchives.entity.SalaryArchives;
import com.geeke.salaryArchives.service.SalaryArchivesService;
import com.geeke.specialAddDeduct.dao.SpecialAddDeductDao;
import com.geeke.specialAddDeduct.dto.SpecialAddDeductDto;
import com.geeke.specialAddDeduct.entity.SpecialAddDeduct;
import com.geeke.specialAddDeduct.entity.TaxDeclareConfig;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.geeke.taxRate.enums.TaxType.*;
import static com.geeke.taxRate.enums.TaxType.LEAVE_COMPENSATION;

/**
 * 专项附加扣除Service
 * @author
 * @version
 */

@Service("specialAddDeductService")
@Transactional(readOnly = true)
public class SpecialAddDeductService extends CrudService<SpecialAddDeductDao, SpecialAddDeduct> {

    @Autowired
    private SalaryArchivesService salaryArchivesService;

    @Autowired
    private SalaryArchivesDao salaryArchivesDao;

    @Override
    public Page<SpecialAddDeduct> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
        int total = dao.count(pageRequest);
        List<SpecialAddDeduct> list = null;
        if(total > 0) {
            list = dao.listPage(pageRequest);
            for(SpecialAddDeduct specialAddDeduct : list) {
                parameters.clear();
                parameters.add(new Parameter("employee_no", "=",specialAddDeduct.getEmployeeNo()));
                parameters.add(new Parameter("company_id", "=", specialAddDeduct.getCompanyId()));
                List<SalaryArchives> salaryArchives = salaryArchivesService.listAll(parameters, null);
                if(CollectionUtils.isNotEmpty(salaryArchives)){
                    specialAddDeduct.setEmployeeStatus(salaryArchives.get(0).getEmployeeStatus());
                }
            }
        }
        return new Page<>(total, list);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void fileToData(String taxYearMonth, MultipartFile file) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        addHeader(reader);
        // 获取导入的数据
        List<SpecialAddDeductDto> specialAddDeductDtos = reader.read(1, 2, SpecialAddDeductDto.class);
        List<SpecialAddDeduct> specialAddDeducts = new ArrayList<>();
        String companyId = SessionUtils.getUser().getCompanyId();
        // 判断导入的数据是否为空
        if(CollectionUtils.isNotEmpty(specialAddDeductDtos)) {
            // 校验数据
            List<String> stringList = this.checkSpecialAddDeduct(taxYearMonth, specialAddDeductDtos);
            if (CollectionUtils.isNotEmpty(stringList)) {
                // 组装校验抛出异常数据
                StringBuilder stringBuilder = new StringBuilder("导入失败！");
                for (String str : stringList) {
                    stringBuilder.append(str);
                    if(!str.equals(stringList.get(stringList.size() - 1))) {
                        stringBuilder.append("、");
                    }
                }
                stringBuilder.deleteCharAt(stringList.size() - 1);
                stringBuilder.append("数据有误！请检查数据是否存在或者报税年月与所得期间是否一致。");
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, stringBuilder.toString()));
            } else {
                // 校验完成无异常数据，进行保存
                for (SpecialAddDeductDto specialAddDeductDto : specialAddDeductDtos) {
                    List<Parameter> parameters = new ArrayList<>();
                    parameters.add(new Parameter("tax_year_month", "=", taxYearMonth));
                    parameters.add(new Parameter("employee_no", "=", specialAddDeductDto.getEmployeeNo()));
                    parameters.add(new Parameter("company_id", "=", companyId));
                    List<SpecialAddDeduct> result = super.listAll(parameters, null);
                    specialAddDeductDto.setCompanyId(companyId);
                    specialAddDeductDto.setTaxYearMonth(taxYearMonth);
                    // 导入只能是同月份的专项数据，同月份重复导入覆盖原有数据，计算时取员工最新一版数据
                    if(CollectionUtils.isNotEmpty(result)){
                        specialAddDeductDto.setId(result.get(0).getId());
                        specialAddDeductDto.setUpdateBy(result.get(0).getUpdateBy());
                        specialAddDeductDto.setCreateDate(result.get(0).getCreateDate());
                        specialAddDeductDto.setCreateBy(result.get(0).getCreateBy());
                        specialAddDeductDto.setUpdateDate(format.format(new Date()));
                        specialAddDeductDto.setOldUpdateDate(result.get(0).getUpdateDate());
                        super.doUpdate(specialAddDeductDto);
                    } else {
                        specialAddDeducts.add(specialAddDeductDto);
                    }
                }
                //保存数据到数据库
                if (CollectionUtils.isNotEmpty(specialAddDeducts)) {
                    super.bulkInsert(specialAddDeducts);
                }
            }
        } else {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "导入数据为空！"));
        }
    }

    private void addHeader(ExcelReader reader){
        reader.addHeaderAlias("工号","employeeNo");
        reader.addHeaderAlias("姓名","employeeName");
        reader.addHeaderAlias("证件类型","idCardType");
        reader.addHeaderAlias("证件号码","idCardNo");
        reader.addHeaderAlias("所得期间起","periodStart");
        reader.addHeaderAlias("所得期间止","periodEnd");
        reader.addHeaderAlias("本期收入","currentIncome");
        reader.addHeaderAlias("本期免税收入","currentIncomeNoTax");
        reader.addHeaderAlias("基本养老保险费","baseAnnuity");
        reader.addHeaderAlias("基本医疗保险费","baseIllMedical");
        reader.addHeaderAlias("失业保险费","unemploy");
        reader.addHeaderAlias("住房公积金","accumulaFund");
        reader.addHeaderAlias("累计子女教育","accSubEducation");
        reader.addHeaderAlias("累计继续教育","accKeepEducation");
        reader.addHeaderAlias("累计住房贷款利息","accHouseLoanTax");
        reader.addHeaderAlias("累计住房租金","accHouseRent");
        reader.addHeaderAlias("累计赡养老人","accCareOld");
        reader.addHeaderAlias("累计3岁以下婴幼儿照护","accBabyPassport");
        reader.addHeaderAlias("累计个人养老金","accPersonAnnuity");
        reader.addHeaderAlias("企业年金","unitAnnuity");
        reader.addHeaderAlias("职业年金","professionAnnuity");
        reader.addHeaderAlias("商业健康保险","busHealthInsurance");
        reader.addHeaderAlias("税延养老保险","taxDelayOldInsurance");
        reader.addHeaderAlias("其他","otherInsurance");
        reader.addHeaderAlias("准予扣除的捐赠额","deductDonateAmount");
        reader.addHeaderAlias("税前扣除项目合计","preTaxDeductTotal");
        reader.addHeaderAlias("减免税额","deductTaxAmount");
        reader.addHeaderAlias("减除费用标准","deductExpenseStandard");
        reader.addHeaderAlias("已缴税额","taxPaidAmount");
        reader.addHeaderAlias("备注","remark");
    }

    /**
     * 校验专项附加检查项 *
     * @param specialAddDeductDtos 参数
     */
    public List<String> checkSpecialAddDeduct(String taxYearMonth, List<SpecialAddDeductDto> specialAddDeductDtos){
        List<String> strings = new ArrayList<>();
        String companyId = SessionUtils.getUser().getCompanyId();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        for(SpecialAddDeductDto dto : specialAddDeductDtos) {
            // 约束条件处理
            Map<String, String> colMaps = Maps.newHashMap();
            // 员工工号不能重复
            colMaps.put("employee_no", "employeeNo");
            SalaryArchives salaryArchives = new SalaryArchives();
            salaryArchives.setEmployeeNo(dto.getEmployeeNo());
            salaryArchives.setCompanyId(companyId);
            // 校验 1:校验员工是否在薪资档案已存在，若不存在，校验不通过 全量数据都不导入
            if (!salaryArchivesService.exists(salaryArchivesDao, salaryArchives, colMaps)) {
                strings.add(dto.getEmployeeNo() + "-" + dto.getEmployeeName());
                continue;
            }
            // 校验 2: 校验：填写的“报税年月”是否与表中“所得期间”月份对应，报税年月=“所得期间”月份+1，校验不通过，所有数据均不导入。

            if(taxYearMonth != null && dto.getPeriodEnd() != null){
                try {
                    calendar.setTime(format.parse(taxYearMonth));
                    calendar1.setTime(format.parse(dto.getPeriodEnd().substring(0,6)));
                    calendar1.add(Calendar.MONTH, 1);
                    if(!calendar.getTime().equals(calendar1.getTime())){
                        strings.add(dto.getEmployeeNo() + "-" + dto.getEmployeeName());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    return strings;
    }

    public List<TaxDeclareConfig> getTaxDeclareConfig(){
        return super.dao.getTaxDeclareConfig();
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int saveTaxDeclareConfig(TaxDeclareConfig taxDeclareConfig){
        if(taxDeclareConfig.getId() == null){
            return super.dao.insertTaxDeclareConfig(taxDeclareConfig);
        } else {
            return super.dao.updateTaxDeclareConfig(taxDeclareConfig);
        }
    }

}
