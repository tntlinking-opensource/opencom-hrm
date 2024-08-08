package com.geeke.salaryArchives.service;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Page;
import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.CrudDao;
import com.geeke.common.persistence.DataEntity;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Department;
import com.geeke.org.service.DepartmentService;
import com.geeke.salaryArchives.dao.SalaryArchivesDao;
import com.geeke.salaryArchives.dto.SalaryArchivesDto;
import com.geeke.salaryArchives.entity.SalaryArchives;
import com.geeke.salaryArchives.utils.EncryptUtil;
import com.geeke.taxRate.entity.TaxTypeInfo;
import com.geeke.taxRate.service.TaxTypeInfoService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.geeke.taxRate.enums.TaxType.*;

/**
 * 薪酬档案Service
 * @author
 * @version
 */

@Service("salaryArchivesService")
@Transactional(readOnly = false)
public class SalaryArchivesService extends CrudService<SalaryArchivesDao, SalaryArchives> {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TaxTypeInfoService taxTypeInfoService;

    public Page<? extends SalaryArchives> listPageWithParam(SearchParams searchParams, String companyId) {
        Page<SalaryArchives> result = listPage(
                searchParams.getParams(),
                searchParams.getOffset(),
                searchParams.getLimit(),
                searchParams.getOrderby()
        );
        List<SalaryArchives> rows = result.getRows();
        if (CollectionUtils.isEmpty(rows)) {
            return result;
        }
        // 获取公司部门
        Map<String, String> companyDept = getCompanyDept(companyId);
        // 获取公司税率表
        Map<String, Map<String, String>> taxInfo = getTaxInfo(companyId);
        // 组装
        List<SalaryArchivesDto> collect = rows.stream().map(item -> {
            SalaryArchivesDto salaryArchivesDto = new SalaryArchivesDto();
            BeanUtils.copyProperties(item, salaryArchivesDto);
            // 组装公司部门
            salaryArchivesDto.setDeptName(companyDept.get(salaryArchivesDto.getDeptId()));
            // 组装税率表
            Map<String, String> salaryMap = taxInfo.get(SALARY.getType());
            salaryArchivesDto.setSalaryRateTableName(MapUtils.isNotEmpty(salaryMap) ? salaryMap.get(salaryArchivesDto.getSalaryRateTable()) : null);
            Map<String, String> yearBonusyMap = taxInfo.get(YEAR_BONUS.getType());
            salaryArchivesDto.setYearEndRateTableName(MapUtils.isNotEmpty(yearBonusyMap) ? yearBonusyMap.get(salaryArchivesDto.getYearEndRateTable()) : null);
            Map<String, String> rewardRemuneration = taxInfo.get(REWARD_REMUNERATION.getType());
            salaryArchivesDto.setRewardRateTableName(MapUtils.isNotEmpty(rewardRemuneration) ? rewardRemuneration.get(salaryArchivesDto.getRewardRateTable()) : null);
            Map<String, String> leaveCompensation = taxInfo.get(LEAVE_COMPENSATION.getType());
            salaryArchivesDto.setLeaveCompensationRateTableName(MapUtils.isNotEmpty(leaveCompensation) ? leaveCompensation.get(salaryArchivesDto.getLeaveCompensationRateTable()) : null);
            // 加密
            salaryArchivesDto.setBankNo(EncryptUtil.bankNo(item.getBankNo()));
            salaryArchivesDto.setIdCardNo(EncryptUtil.idNumber(item.getIdCardNo()));
            return salaryArchivesDto;
        }).collect(Collectors.toList());
        return new Page<>(result.getTotal(), collect);
    }



    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public SalaryArchives save(SalaryArchives salaryArchives) {
        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();

        // 员工工号不能重复

        colMaps.clear();

        colMaps.put("employee_no", "employeeNo");

        if (exists(dao, salaryArchives, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "员工工号不能重复"));
        }

        SalaryArchives salaryArchivesTemp = super.save(salaryArchives);

        return salaryArchivesTemp;
    }

    public ResponseEntity<JSONObject> fileToData(MultipartFile file) throws Exception {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        addHeader(reader);
        List<SalaryArchivesDto> salaryArchivesList = reader.read(3, 4, SalaryArchivesDto.class);
        List<SalaryArchives> salaryArchivesListImport = new ArrayList<>();
        String companyId = SessionUtils.getUser().getCompanyId();
        StringBuffer stringBuffer = new StringBuffer();
        // 获取公司税率表
        Map<String, Map<String, String>> taxInfo = getTaxInfo(companyId);
        // 校验必填项是否为空
        for(SalaryArchivesDto salaryArchives : salaryArchivesList ) {
            try {
                if (validateSalaryArchives(salaryArchives)){
                    // 设置公司id
                    salaryArchives.setCompanyId(companyId);
                    // 设置工资薪金税率表id
                    Map<String, String> tempMap = taxInfo.get(SALARY.getType());
                    Map<String, String> map =  tempMap.entrySet().stream().collect(Collectors.toMap(entity-> entity.getValue(),entity-> entity.getKey()));
                    salaryArchives.setSalaryRateTable(MapUtils.isNotEmpty(map) ? map.get(salaryArchives.getSalaryRateTableName()) : "");
                    // 设置年终奖税率表id
                    tempMap = taxInfo.get(YEAR_BONUS.getType());
                    map =  tempMap.entrySet().stream().collect(Collectors.toMap(entity-> entity.getValue(),entity-> entity.getKey()));
                    salaryArchives.setYearEndRateTable(MapUtils.isNotEmpty(map) ? map.get(salaryArchives.getYearEndRateTableName()) : "");
                    // 设置劳务报酬税率表id
                    tempMap = taxInfo.get(REWARD_REMUNERATION.getType());
                    map =  tempMap.entrySet().stream().collect(Collectors.toMap(entity-> entity.getValue(),entity-> entity.getKey()));
                    salaryArchives.setRewardRateTable(MapUtils.isNotEmpty(map) ? map.get(salaryArchives.getRewardRateTableName()) : "");
                    // 设置离职补偿税率表id
                    tempMap = taxInfo.get(LEAVE_COMPENSATION.getType());
                    map =  tempMap.entrySet().stream().collect(Collectors.toMap(entity-> entity.getValue(),entity-> entity.getKey()));
                    salaryArchives.setLeaveCompensationRateTable(MapUtils.isNotEmpty(map) ? map.get(salaryArchives.getLeaveCompensationRateTableName()) : "");

                    salaryArchivesListImport.add(salaryArchives);
                }
            } catch(CommonJsonException e) {
                e.printStackTrace();
                stringBuffer.append(e.getResultJson().get("msg"));
            }
        }

        Map map = new HashMap();
        map.put("msg", stringBuffer);
        map.put("code", 100);
        if (stringBuffer.length() > 0) {
            map.put("code", 50001);
        }

        //保存数据到数据库
        if (CollectionUtils.isNotEmpty(salaryArchivesListImport)) {
            super.bulkInsert(salaryArchivesListImport);
        }
        return ResponseEntity.ok(new JSONObject(map));
    }

    private void addHeader(ExcelReader reader){
        reader.addHeaderAlias("员工工号","employeeNo");
        reader.addHeaderAlias("员工姓名","employeeName");
        reader.addHeaderAlias("性别","sex");
        reader.addHeaderAlias("证件类型","idCardType");
        reader.addHeaderAlias("身份证号码","idCardNo");
        reader.addHeaderAlias("出生日期","birthDate");
        reader.addHeaderAlias("手机号","phoneNumber");
        reader.addHeaderAlias("国籍","nationality");
        reader.addHeaderAlias("部门","deptId");
        reader.addHeaderAlias("员工类型","employeeType");
        reader.addHeaderAlias("员工归属","employeeBelongs");
        reader.addHeaderAlias("员工状态","employeeStatus");
        reader.addHeaderAlias("入职时间","entryTime");
        reader.addHeaderAlias("试用期","probationMonth");
        reader.addHeaderAlias("试用期结束时间","probationEndTime");
        reader.addHeaderAlias("离职时间","leaveTime");
        reader.addHeaderAlias("岗位","postName");
        reader.addHeaderAlias("银行卡类型","bankCardType");
        reader.addHeaderAlias("开户行","bankName");
        reader.addHeaderAlias("银行卡账号","bankNo");
        reader.addHeaderAlias("开户姓名","bankUserName");
        reader.addHeaderAlias("账号市区名","bankCityName");
        reader.addHeaderAlias("工资薪金税率表","salaryRateTableName");
        reader.addHeaderAlias("年终奖税率表","yearEndRateTableName");
        reader.addHeaderAlias("劳务报酬税率表","rewardRateTableName");
        reader.addHeaderAlias("离职补偿税率表","leaveCompensationRateTableName");
    }

    public boolean validateSalaryArchives(SalaryArchivesDto entity) {

        // 约束条件处理
        Map<String, String> colMaps = Maps.newHashMap();
        // 员工工号不能重复
        colMaps.put("employee_no", "employeeNo");

        if (exists(dao, entity, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "员工工号为【" + entity.getEmployeeNo() + "】重复"));
        }

        if (entity.getEmployeeNo()==null || entity.getEmployeeName()==null || entity.getSex()==null || entity.getIdCardNo()==null || entity.getIdCardType()==null ||
        entity.getPhoneNumber()==null || entity.getDeptId()==null || entity.getEmployeeType()==null|| entity.getEmployeeStatus()==null ||
        entity.getEntryTime()==null || entity.getProbationMonth()==null || entity.getProbationEndTime()== null || entity.getPostName()==null ||
        entity.getBankNo()==null || entity.getBankName()==null || entity.getBankUserName()==null) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "员工编号为【" + entity.getEmployeeNo() +
                    "】，员工姓名为【" + entity.getEmployeeName() + "】中存在必填项为空的数据，无法导入！"));
        }
        return true;
    }

    /**
     * 查询公司的所有税率表
     * @param companyId
     * @return Map<String, Map<String, String>>   Map<type, Map<id, taxname>>
     */
    public Map<String, Map<String, String>> getTaxInfo(String companyId) {
        HashMap<String, Map<String, String>> result = new HashMap<>();
        List<TaxTypeInfo> allTax = taxTypeInfoService.listAll(Arrays.asList(new Parameter("company_id", "=", companyId)), null);
        Map<String, List<TaxTypeInfo>> collect = allTax.stream().collect(Collectors.groupingBy(TaxTypeInfo::getTaxType));
        // group
        Set<String> keys = collect.keySet();
        keys.stream().filter(item -> CollectionUtils.isNotEmpty(collect.get(item)))
                    .forEach(item -> {
                        List<TaxTypeInfo> taxTypeInfos = collect.get(item);
                        Map<String, String> valueMap = taxTypeInfos.stream().collect(Collectors.toMap(TaxTypeInfo::getId, TaxTypeInfo::getTaxName));
                        result.put(item, valueMap);
                    });
        return result;
    }

    /**
     * 查询公司所有部门
     * @param companyId
     * @return Map<String, String> Map<dept_id, dept_name>
     */
    public Map<String, String> getCompanyDept(String companyId) {
        HashMap<String, String> result = new HashMap<>();
        List<Department> departments = departmentService.listAll(Arrays.asList(new Parameter("company_id", "=", companyId)), null);
        if (CollectionUtils.isEmpty(departments)) {
            return result;
        }
        Map<String, String> collect = departments.stream().collect(Collectors.toMap(Department::getId, Department::getName));
        result.putAll(collect);
        return result;
    }

    public boolean exists(SalaryArchivesDao curdDao, SalaryArchives entity, Map<String, String> colMaps) {
        return super.exists(curdDao, entity, colMaps);
    }
}
