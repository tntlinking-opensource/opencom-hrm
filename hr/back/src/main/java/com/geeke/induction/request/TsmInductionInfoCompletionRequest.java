package com.geeke.induction.request;

import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * TsmInductionBasicEnhanceResponse
 * @author
 * @version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsmInductionInfoCompletionRequest {
    /**
     * basic的id
     */
    private String id;

    //************************ tsmPost **************************
    /**
     * tsmPostId
     */
    private String postId;
    /**
     *   '招聘岗位',
     */
    private DictItem jobPosition;

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


    public TsmPost tsmPostConvert(TsmPost tsmPost, String inductionId) {
        tsmPost.setInductionId(inductionId);
        tsmPost.setJobPosition(jobPosition);
        tsmPost.setWorkCity(workCity);
        tsmPost.setCompanyName(companyName);
        tsmPost.setPersonnelArea(personnelArea);
        tsmPost.setDepartment(department);
        tsmPost.setEmployeeType(employeeType);
        tsmPost.setEmployeeStatus(employeeStatus);
        tsmPost.setEmployeeNature(employeeNature);
        tsmPost.setWorkingHoursCategory(workingHoursCategory);
        tsmPost.setEntryTime(entryTime);
        tsmPost.setProbationPeriod(probationMonth);
        tsmPost.setRemarks(remarks);
        tsmPost.setEmployeeNumber(employeeNumber);

        tsmPost.setRegularTime(probationEndTime);
        tsmPost.setOfficeAddress(reportAddress);

        tsmPost.setTechnicalPosition(techPosition);
        tsmPost.setEmployeeRank(employeeLevel);

        return tsmPost;
    }

    public TsmInductionInfo tsmInductionInfoConvert(TsmInductionInfo info, String inductionId) {
        info.setInductionId(inductionId);
        info.setCompanyEmail(companyEmail);
        info.setEmployeeNumber(employeeNumber);
        return info;
    }

    public TsmContractInformation tsmContractInfoConvert(TsmContractInformation tsmContractInformation, String inductionId) {
        tsmContractInformation.setInductionId(inductionId);
        tsmContractInformation.setEntryTime(entryTime);
        tsmContractInformation.setProbationMonth(probationMonth);
        tsmContractInformation.setProbationEndTime(probationEndTime);
        tsmContractInformation.setTechPosition(techPosition);
        tsmContractInformation.setEmployeeLevel(employeeLevel);
        tsmContractInformation.setComputerCondition(computerCondition);
        tsmContractInformation.setRecruitmentMode(recruitmentMode);
        tsmContractInformation.setContractType(contractType);
        tsmContractInformation.setContractProperty(contractProperty);
        tsmContractInformation.setContractSignUnit(contractSignUnit);
        tsmContractInformation.setContractStartTime(contractStartTime);
        tsmContractInformation.setContractMonths(contractMonths);
        tsmContractInformation.setContractEndTime(contractEndTime);
        tsmContractInformation.setFullWages(fullWages);
        tsmContractInformation.setProbationWages(probationWages);
        tsmContractInformation.setProbationBasicWages(probationBasicWages);
        tsmContractInformation.setBasicWages(basicWages);
        tsmContractInformation.setSecrecyReward(secrecyReward);
        tsmContractInformation.setSystemReward(systemReward);
        tsmContractInformation.setBasicPerformance(basicPerformance);
        tsmContractInformation.setFloatingPerformance(floatingPerformance);
        tsmContractInformation.setProbationBasicPerformance(probationBasicPerformance);
        tsmContractInformation.setProbationFloatingPerformance(probationFloatingPerformance);
        tsmContractInformation.setReportTime(reportTime);
        tsmContractInformation.setEntryType(entryType);
        tsmContractInformation.setEmployeeBelongs(employeeBelongs);
        tsmContractInformation.setReportAddress(reportAddress);
        tsmContractInformation.setRemarks(computerSpecialRemarks);
        tsmContractInformation.setCompanyLegalPerson(companyLegalPerson);
        tsmContractInformation.setJobType(jobType);
        return tsmContractInformation;
    }
}
