package com.geeke.hrm.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.geeke.admin.service.UserService;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.hrm.entity.HrmPayrollBillDetails;
import com.geeke.hrm.response.HrmHomeContractResponse;
import com.geeke.hrm.response.HrmHomeDataResponse;
import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.induction.service.TsmContractInformationService;
import com.geeke.induction.service.TsmEducationalRecordsService;
import com.geeke.induction.service.TsmInductionBasicService;
import com.geeke.induction.service.TsmInductionInfoService;
import com.geeke.induction.service.TsmPostService;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.QueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 离职类型Service
 *
 * @author
 */

@Service("HrmHomeService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HrmHomeService {
    private final TsmInductionBasicService tsmInductionBasicService;
    private final TsmRecruitService tsmRecruitService;
    private final UserService userService;
    private final HrmLaidValueService hrmLaidValueService;
    private final HrmUserLaidService hrmUserLaidService;
    private final TsmEducationalRecordsService tsmEducationalRecordsService;
    private final TsmPostService tsmPostService;
    private final HrmPayrollBillDetailsService hrmPayrollBillDetailsService;
    private final TsmContractInformationService tsmContractInformationService;
    private final TsmInductionInfoService tsmInductionInfoService;

    private static final int REMINDER_DAYS = 31;

    public HrmHomeContractResponse contractReminder() {
        LocalDateTime nowDate = LocalDateTime.now();
        String newDatePlus31 = LocalDateTimeUtil.format(nowDate.plusDays(REMINDER_DAYS), "yyyy-MM-dd");
        //合同对应字段
        //contract_start_time  合同开始时间
        //contract_end_time  合同结束时间
        //probation_end_time  试用结束时间

        //续签合约
        //当前时间+30天 >=  合同结束时间    续签合约
        Map<String, TsmContractInformation> renewalOfContractMap = tsmContractInformationService.listAll(ListUtil.of(
            new Parameter("contract_end_time", "<=", newDatePlus31)), null)
            .stream()
            .collect(Collectors.toMap(TsmContractInformation::getInductionId, Function.identity()));

        //转正合约
        //当前时间+30天 >=  合同结束时间    转正合约
        //当前时间+30天 >=  合同结束时间  当前时间<合同结束时间   <=  转正合约
        Map<String, TsmContractInformation> employmentContractMap = tsmContractInformationService.listAll(ListUtil.of(
            new Parameter("probation_end_time","<=", newDatePlus31),
            new Parameter("probation_end_time",">", LocalDateTimeUtil.format(nowDate, "yyyy-MM-dd"))), null)
            .stream()
            .collect(Collectors.toMap(TsmContractInformation::getInductionId, Function.identity()))
            ;

        //收集inductionId
        List<Object> inductionIdList = CollUtil.union(renewalOfContractMap.keySet(),employmentContractMap.keySet())
            .stream()
            .map(s -> (Object) s)
            .collect(Collectors.toList());

        //查询inductionId--》员工号
        Map<String, String> inductionIdToEmployeeMap = tsmInductionInfoService.listAll(QueryUtils.queryInList("induction_id", inductionIdList), null)
            .stream()
            .collect(Collectors.toMap(TsmInductionInfo::getInductionId, TsmInductionInfo::getEmployeeNumber));
        //查询inductionId--》部门
        Map<String, DictItem> inductionIdToDepMap = tsmPostService.listAll(QueryUtils.queryInList("induction_id", inductionIdList), null)
            .stream()
            .collect(Collectors.toMap(TsmPost::getInductionId, TsmPost::getDepartment));
        //查询inductionId--》name
        Map<String, String> inductionIdToNameMap = tsmInductionBasicService.listAll(QueryUtils.queryInList("id", inductionIdList), null)
            .stream()
            .collect(Collectors.toMap(TsmInductionBasic::getId, TsmInductionBasic::getName));

        //续签
        List<HrmHomeContractResponse.ContractRemind> renewalOfContract =renewalOfContractMap.values()
            .stream()
            .map(x -> new HrmHomeContractResponse.ContractRemind(
                inductionIdToDepMap.get(x.getInductionId()).getName(),
                inductionIdToNameMap.get(x.getInductionId()),
                x.getContractEndTime(),
                inductionIdToEmployeeMap.get(x.getInductionId())
            )).collect(Collectors.toList());

        //转正
        List<HrmHomeContractResponse.ContractRemind> employmentContract = employmentContractMap.values()
            .stream()
            .map(x -> new HrmHomeContractResponse.ContractRemind(
                inductionIdToDepMap.get(x.getInductionId()).getName(),
                inductionIdToNameMap.get(x.getInductionId()),
                x.getProbationEndTime(),
                inductionIdToEmployeeMap.get(x.getInductionId())
            )).collect(Collectors.toList());

        return new HrmHomeContractResponse(renewalOfContract, employmentContract);
    }

    public HrmHomeDataResponse getInfo(String companyId, String inductionId) {
        //办理中查询器
        List<Parameter> processingParameters = QueryUtils.queryAndList("proc_status", 8044);

        //公司人数员工总数
        long peopleTotalNum = userService.getCount(companyId);

        //招聘办理人数-招聘申请通过的
        long alreadyRecruit =   tsmRecruitService.listAll(processingParameters,"")
            .stream().map(TsmRecruit::getCandidate).mapToLong(Long::parseLong).sum();
        long staffNum =   tsmRecruitService.listAll(processingParameters,"")
            .stream().map(TsmRecruit::getCandidate).mapToLong(Long::parseLong).sum();
        long recruitProcessingNum = alreadyRecruit - staffNum;

        //入职办理人数 未归档的数据，但不包含面试失败、放弃报道的人员数据。 post-》面试结果、放弃报道   info-》归档
        List<String> tsmPostInductionId = tsmPostService.listAll(
            QueryUtils.queryList(
                new Parameter("interview_result","!=","0"),
                new Parameter("abandon_reporting","!=","1"),
                new Parameter("interview_result","is null","","OR")
            ),"").stream()
            .map(TsmPost::getInductionId)
            .collect(Collectors.toList());
        long inductionProcessingNum = tsmInductionInfoService.getAllCount(new PageRequest(
            QueryUtils.queryList(
                new Parameter("record_status","=","0"),
                new Parameter("induction_id","in",tsmPostInductionId)
        )));

        //离职办理人数
        long departProcessingNum = hrmUserLaidService.getAllCount(
            new PageRequest(QueryUtils.queryAndList("laidStatus", "1")));


        //todo 上月薪酬支出
        BigDecimal lastMonthSalaryExpenses = hrmPayrollBillDetailsService.listAll(new ArrayList<>(),"")
            .stream()
            .map(HrmPayrollBillDetails::getSalaryNeedTax)
            .reduce(BigDecimal.ZERO, BigDecimal::add);


        //审批查询器
        PageRequest queryApprovalRequest = new PageRequest(QueryUtils.queryAndList("proc_status", 8045));
        //招聘审批
        long recruitApproval = tsmRecruitService.getAllCount(queryApprovalRequest);
        //入职审批
        long inductionApproval = tsmInductionBasicService.getAllCount(queryApprovalRequest);
        //离职审批
        long departApproval = hrmUserLaidService.getAllCount(queryApprovalRequest);
        //薪资审批 todo 辉海确认
        long salaryApproval = hrmPayrollBillDetailsService.getAllCount();


        //毕业工作到现在
        List<Parameter> inductionQueryList = QueryUtils.queryAndList("induction_id", inductionId);
        LocalDate maxGraduationTime = tsmEducationalRecordsService.listAll(inductionQueryList, null)
            .stream()
            .max(Comparator.comparing(x -> LocalDate.parse(x.getGraduationTime())))
            .map(x -> LocalDate.parse(x.getGraduationTime()))
            .orElse(LocalDate.now())
            ;
        long allWorkDays = Period.between(maxGraduationTime, LocalDate.now()).getDays();
        //到本公司工作到现在
        LocalDate companyWorkDate = Optional.ofNullable(tsmPostService.getByInductionId(inductionId))
            .filter(x -> x.size() > 0)
            .map(x -> x.get(0))
            .map(TsmPost::getEntryTime)
            .map(LocalDate::parse)
            .orElse(LocalDate.now());
        long companyDays = Period.between(companyWorkDate, LocalDate.now()).getDays();


        HrmHomeDataResponse hrmHomeDataResponse = new HrmHomeDataResponse();
        hrmHomeDataResponse.setPeopleTotalNum(String.valueOf(peopleTotalNum));
        hrmHomeDataResponse.setRecruitProcessingNum(String.valueOf(recruitProcessingNum));
        hrmHomeDataResponse.setInductionProcessingNum(String.valueOf(inductionProcessingNum));
        hrmHomeDataResponse.setDepartProcessingNum(String.valueOf(departProcessingNum));
        hrmHomeDataResponse.setLastMonthSalaryExpenses(lastMonthSalaryExpenses);
        hrmHomeDataResponse.setRecruitApproval(String.valueOf(recruitApproval));
        hrmHomeDataResponse.setInductionApproval(String.valueOf(inductionApproval));
        hrmHomeDataResponse.setDepartApproval(String.valueOf(departApproval));
        hrmHomeDataResponse.setSalaryApproval(String.valueOf(salaryApproval));
        hrmHomeDataResponse.setAllWorkDays(String.valueOf(allWorkDays));
        hrmHomeDataResponse.setCompanyDays(String.valueOf(companyDays));
        return hrmHomeDataResponse;
    }



}
