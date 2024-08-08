package com.geeke.induction.response;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.induction.entity.TsmEducationalRecords;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
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
public class TsmInductionFlowInitResponse extends TsmInductionBasic {

//    tsm_post
    /**
     * '招聘岗位',
     */
    private TsmRecruit jobPosition;
    /**
     * '部门',
     */
    private DictItem department;
    /**
     * '工作城市',
     */
    private String workCity;
    /**
     * '所属公司',
     */
    private String companyName;
    /**
     * // 人事区域
     */
    private DictItem employeeNature;
    /**
     * '0：城镇员工，1：农村员工',          员工性质
     */
    private String personnelArea;
    /**
     *   '0：正式员工，1：实习生，2：劳务派遣',    员工类型
     */
    private DictItem employeeType;
    /**
     * 0：全职，1：兼职
     */
    private DictItem workingHoursCategory;
    /**
     * 员工状态
     */
    private DictItem employeeStatus;


    //tsm_induction_info
    /**
     *  公司邮箱,
     */
    private String companyEmail;

    /**
     *    工号
     */
    private String employeeNumber;


// tsm_contract_information;
    /**
     *                 电脑情况
     */
    private DictItem computerCondition;

    /**
     *                         入职时间
     */
    private String entryTime;
    /**
     *                    试用期工资（元）
     */
    private String probationWages;
    /**
     *                         全额工资（元） 转正
     */
    private String fullWages;
    /**
     *                     员工职级
     */
    private String employeeLevel;
    /**
     *                      技术职务
     */
    private DictItem techPosition;
    /**
     *                      合同类型
     */
    private DictItem contractType;
    /**
     *                   招聘方式
     */
    private DictItem recruitmentMode;
    /**
     *                 试用结束时间
     */
    private String probationEndTime;
    /**
     *                 合同签约单位
     */
    private String contractSignUnit;
    /**
     *                    合同期限（月）
     */
    private Integer contractMonths;
    /**
     *  // 入职类型
     */
    private DictItem entryType;
    /**
     *                        报道日期
     */
    private String reportTime;
    /**
     *  合同开始时间
     */
    private String contractStartTime;
    /**
     * 合同结束时间
     */
    private String contractEndTime;
    /**
     * 试用期（月）
     */
    private DictItem probationMonth;

// TsmEducationalRecords 最高
    /**
     *  毕业学校
     */
    private String graduatedSchool;
    /**
     *  最高学历
     */
    private DictItem highestEducation;
    /**
     *  毕业时间
     */
    private String graduationTime;


    private String computerSpecialRemarks;
    private String remarks;


    public TsmInductionFlowInitResponse tsmInductionBasicConvert(TsmInductionBasic basic,TsmRecruitService tsmRecruitService) {
        TsmInductionFlowInitResponse response = new TsmInductionFlowInitResponse();
        BeanUtil.copyProperties(basic, response);
        response.buildAll(tsmRecruitService);
        return response;
    }

    public TsmInductionFlowInitResponse buildAll(TsmRecruitService tsmRecruitService) {
        buildTsmPost(tsmRecruitService);
        buildTsmInfo();
        buildTsmContractInfo();
        buildTsmEducationalRecords();

        return this;
    }



    public void buildTsmPost(TsmRecruitService tsmRecruitService) {
        TsmPost tsmPost;
        if (ObjUtil.isNull(tsmPost = CollUtil.getFirst(super.getTsmPostList()))) {
            return;
        }

        Optional.ofNullable(tsmPost.getJobPosition())
            .map(DictItem::getId)
            .ifPresent(jobId -> this.jobPosition = tsmRecruitService.get(jobId));

        this.department = tsmPost.getDepartment();
        this.workCity = tsmPost.getWorkCity();
        this.companyName = tsmPost.getCompanyName();
        this.personnelArea = tsmPost.getPersonnelArea();
        this.employeeNature = tsmPost.getEmployeeNature();
        this.employeeType = tsmPost.getEmployeeType();
        this.workingHoursCategory = tsmPost.getWorkingHoursCategory();
        this.employeeStatus = tsmPost.getEmployeeStatus();

    }

    public void buildTsmInfo() {
        TsmInductionInfo tsmInductionInfo;
        if (ObjUtil.isNull(tsmInductionInfo = CollUtil.getFirst(super.getTsmInductionInfoList()))) {
            return;
        }

        this.companyEmail = tsmInductionInfo.getCompanyEmail();
        this.employeeNumber = tsmInductionInfo.getEmployeeNumber();
        this.remarks   = tsmInductionInfo.getRemarks();
    }

    public void buildTsmContractInfo() {
        TsmContractInformation tsmContractInformation;
        if (ObjUtil.isNull(tsmContractInformation = CollUtil.getFirst(super.getTsmContractInformationList()))) {
            return;
        }

        this.computerCondition = tsmContractInformation.getComputerCondition();
        this.entryTime = tsmContractInformation.getEntryTime();
        this.probationWages = tsmContractInformation.getProbationWages();
        this.fullWages = tsmContractInformation.getFullWages();
        this.employeeLevel = tsmContractInformation.getEmployeeLevel();
        this.techPosition = tsmContractInformation.getTechPosition();
        this.contractType = tsmContractInformation.getContractType();
        this.recruitmentMode = tsmContractInformation.getRecruitmentMode();
        this.probationEndTime = tsmContractInformation.getProbationEndTime();
        this.contractSignUnit = tsmContractInformation.getContractSignUnit();
        this.contractMonths = tsmContractInformation.getContractMonths();
        this.entryType = tsmContractInformation.getEntryType();
        this.reportTime = tsmContractInformation.getReportTime();
        this.contractStartTime = tsmContractInformation.getContractStartTime();
        this.contractEndTime = tsmContractInformation.getContractEndTime();
        this.probationMonth = tsmContractInformation.getProbationMonth();
        this.computerSpecialRemarks = tsmContractInformation.getRemarks();

    }

    public void buildTsmEducationalRecords() {
        TsmEducationalRecords tsmEducationalRecords;
        if (CollUtil.isEmpty(super.getTsmEducationalRecordsList()) || super.getTsmEducationalRecordsList().size() == 0) {
            return;
        }

        tsmEducationalRecords = super.getTsmEducationalRecordsList().stream()
            .max(Comparator.comparing(TsmEducationalRecords::getGraduationTime))
            .get();

        this.graduatedSchool = tsmEducationalRecords.getGraduatedSchool();
        this.highestEducation = tsmEducationalRecords.getHighestEducation();
        this.graduationTime = tsmEducationalRecords.getGraduationTime();
    }


}
