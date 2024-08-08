package com.geeke.induction.response;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;


/**
 * 此response 包含了info  card  tsmpost 数据
 * TsmInductionInfoCompletionResponse
 * @author
 * @version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsmInductionInfoCompletionResponse extends TsmInductionBasic {


    //************************ tsmPost **************************
    /**
     * tsmPostId
     */
    private String postId;
    /**
     * 期望薪资
     */
    private Integer expectedSalary;
    /**
     *   '招聘岗位',
     */
    private TsmRecruit jobPosition;

    /**
     *   '工作城市',
     */
    private String workCity;

    /**
     *   '所属公司',
     */
    private String companyName;

    /**
     *   '人事区域',
     */
    private String personnelArea;

    /**
     *   '部门',
     */
    private DictItem department;

    /**
     *   '0：正式员工，1：实习生，2：劳务派遣', 	员工类型
     */
    private DictItem employeeType;

    /**
     *   '0: 在职, 1: 试用期, 2: 离职',  		员工状态
     */
    private DictItem employeeStatus;

    /**
     *   '0：城镇员工，1：农村员工', 			员工性质
     */
    private DictItem employeeNature;

    /**
     *   '0：全职，1：兼职',  			工时类别
     */
    private DictItem workingHoursCategory;

    /**
     *   '入职时间',
     */
    private String entryTime;

    /**
     *   '试用期（月）',
     */
    private DictItem probationPeriod;
    /**
     * 备注 TsmPost 中的remarks
     */
    private String remarks;



//    tsm_induction_info
    //************************ tsm_induction_info **************************
    /**
     * tsm_induction_info id
     */
    private String infoId;
    /**
     * 公司邮箱
     */
    private String companyEmail;
    /**
     * 婚姻状况
     */
    private DictItem maritalStatus;
    /**
     * 政治面貌
     */
    private DictItem politicalStatus;
    /**
     *   '工号',
     */
    private String employeeNumber;


    //************************ tsm_contract_information **************************

//    tsm_contract_information
    //入职时间 tsm_contract_information 也需要
    //private String entryTime;
    /**
     * tsm_induction_info id
     */
    private String contractInfoId;
    /**
     * 试用期(月)填充
     */
    private DictItem probationMonth;
    /**
     * 试用结束时间
     */
    private String probationEndTime;
    /**
     * 技术职务
     */
    private DictItem techPosition;
    /**
     * 员工职级
     */
    private String employeeLevel;
    /**
     * 电脑情况
     */
    private DictItem computerCondition;
    /**
     * 招聘方式
     */
    private DictItem recruitmentMode;
    /**
     * 合同类型
     */
    private DictItem contractType;
    /**
     * 合同属性
     */
    private DictItem contractProperty;
    /**
     * 合同签约单位
     */
    private String contractSignUnit;
    /**
     * 合同开始时间
     */
    private String contractStartTime;
    /**
     * 合同期限（月）
     */
    private Integer contractMonths;
    /**
     * 合同结束时间
     */
    private String contractEndTime;
    /**
     * 全额工资（元）
     */
    private String fullWages;
    /**
     * 试用期工资（元）
     */
    private String probationWages;
    /**
     * 试用期基本工资（元）
     */
    private String probationBasicWages;
    /**
     * 基本工资（元）
     */
    private String basicWages;
    /**
     * 保密奖（元）
     */
    private String secrecyReward;
    /**
     * 制度奖（元）
     */
    private String systemReward;
    /**
     * 基本绩效（元）
     */
    private String basicPerformance;
    /**
     * 浮动绩效（元）
     */
    private String floatingPerformance;
    /**
     * 试用期基本绩效（元）
     */
    private String probationBasicPerformance;
    /**
     * 试用期浮动绩效（元）
     */
    private String probationFloatingPerformance;
    /**
     * 报道日期
     */
    private String reportTime;
    /**
     * 入职类型
     */
    private DictItem entryType;
    /**
     * 员工归属
     */
    private String employeeBelongs;
    /**
     * 报道地址
     */
    private String reportAddress;
    /**
     * 电脑特殊需求  TsmContractInformation 中的remarks
     */
    private String computerSpecialRemarks;
    /**
     * 公司法人
     */
    private String companyLegalPerson;
    /**
     * 从事工作
     */
    private String jobType;


    public TsmInductionInfoCompletionResponse tsmInductionBasicConvert(TsmInductionBasic basic,
                                                                       TsmRecruitService tsmRecruitService
                                                                       ) {
        BeanUtil.copyProperties(basic, this);
        this.buildAll(tsmRecruitService);
        return this;
    }

    public TsmInductionInfoCompletionResponse buildAll(TsmRecruitService tsmRecruitService) {
        buildTsmPost(tsmRecruitService);
        buildTsmInfo();
        buildTsmContractInfo();

        //招聘数据 对应 岗位信息
        contractSignUnit = companyName;
        return this;
    }


    public void buildTsmPost(TsmRecruitService tsmRecruitService) {
        TsmPost tsmPost;
        if (ObjUtil.isNull(tsmPost = CollUtil.getFirst(super.getTsmPostList()))) {
            return;
        }

        this.postId = tsmPost.getId();
        this.workCity = tsmPost.getWorkCity();
        this.companyName = tsmPost.getCompanyName();
        this.personnelArea = tsmPost.getPersonnelArea();
        this.employeeType = tsmPost.getEmployeeType();
        this.employeeStatus = tsmPost.getEmployeeStatus();
        this.employeeNature = tsmPost.getEmployeeNature();
        this.workingHoursCategory = tsmPost.getWorkingHoursCategory();
        this.entryTime = tsmPost.getEntryTime();
        this.probationPeriod = tsmPost.getProbationPeriod();
        this.remarks = tsmPost.getRemarks();
        this.expectedSalary = tsmPost.getExpectedSalary();
        this.department = tsmPost.getDepartment();

        Optional.ofNullable(tsmPost.getJobPosition())
            .map(DictItem::getId)
            .ifPresent(jobId -> this.jobPosition = tsmRecruitService.get(jobId));
    }

    public void buildTsmInfo() {
        TsmInductionInfo tsmInductionInfo;
        if (ObjUtil.isNull(tsmInductionInfo = CollUtil.getFirst(super.getTsmInductionInfoList()))) {
            return;
        }

        this.infoId= tsmInductionInfo.getId();
        this.companyEmail= tsmInductionInfo.getCompanyEmail();
        this.employeeNumber= tsmInductionInfo.getEmployeeNumber();
        this.maritalStatus= tsmInductionInfo.getMaritalStatus();
        this.politicalStatus= tsmInductionInfo.getPoliticalStatus();
    }

    public void buildTsmContractInfo() {
        TsmContractInformation tsmContractInformation;
        if (ObjUtil.isNull(tsmContractInformation = CollUtil.getFirst(super.getTsmContractInformationList()))) {
            return;
        }
        this.contractInfoId = tsmContractInformation.getId();
        this.entryTime = tsmContractInformation.getEntryTime();
        this.probationMonth = tsmContractInformation.getProbationMonth();
        this.probationEndTime = tsmContractInformation.getProbationEndTime();
        this.techPosition = tsmContractInformation.getTechPosition();
        this.employeeLevel = tsmContractInformation.getEmployeeLevel();
        this.computerCondition = tsmContractInformation.getComputerCondition();
        this.recruitmentMode = tsmContractInformation.getRecruitmentMode();
        this.contractType = tsmContractInformation.getContractType();
        this.contractProperty = tsmContractInformation.getContractProperty();
        this.contractSignUnit = tsmContractInformation.getContractSignUnit();
        this.contractStartTime = tsmContractInformation.getContractStartTime();
        this.contractMonths = tsmContractInformation.getContractMonths();
        this.contractEndTime = tsmContractInformation.getContractEndTime();
        this.fullWages = tsmContractInformation.getFullWages();
        this.probationWages = tsmContractInformation.getProbationWages();
        this.probationBasicWages = tsmContractInformation.getProbationBasicWages();
        this.basicWages = tsmContractInformation.getBasicWages();
        this.secrecyReward = tsmContractInformation.getSecrecyReward();
        this.systemReward = tsmContractInformation.getSystemReward();
        this.basicPerformance = tsmContractInformation.getBasicPerformance();
        this.floatingPerformance = tsmContractInformation.getFloatingPerformance();
        this.probationBasicPerformance = tsmContractInformation.getProbationBasicPerformance();
        this.probationFloatingPerformance = tsmContractInformation.getProbationFloatingPerformance();
        this.reportTime = tsmContractInformation.getReportTime();
        this.entryType = tsmContractInformation.getEntryType();
        this.employeeBelongs = tsmContractInformation.getEmployeeBelongs();
        this.reportAddress = tsmContractInformation.getReportAddress();
        this.computerSpecialRemarks = tsmContractInformation.getRemarks();
        this.companyLegalPerson = tsmContractInformation.getCompanyLegalPerson();
        this.jobType = tsmContractInformation.getJobType();
    }

}
