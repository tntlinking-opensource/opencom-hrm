package com.geeke.induction.response;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.geeke.induction.entity.TsmCard;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.org.service.DepartmentService;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Optional;


/**
 * 此response 包含了info  card  tsmpost 数据
 * TsmInductionBasicEnhanceResponse
 * @author
 * @version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TsmInductionBasicEnhanceResponse extends TsmInductionBasic {

    /**
     * TsmInductionInfo中id
     */
    private String infoId;
    /**
     *   公积金账号ID
     */
    private String accumulationFundId;
    /**
     * 工号
     */
    private String employeeNumber;

    /**
     *   工资卡号
     */
    private String salaryCardId;

    /**
     *   国籍或地区
     */
    private String nationality;

    /**
     *   户口所在地
     */
    private String householdRegisterLocation;

    /**
     *  户口状态 类型 0：农业户口，1：城镇户口
     */
    private DictItem householdRegisterType;

    /**
     *   0：未婚，1：已婚，2：离异，3：丧偶 婚姻状况
     */
    private DictItem maritalStatus;

    /**
     *   0: 群众, 1: 党员, 2: 团员, 3: 其他  政治面貌
     */
    private DictItem politicalStatus;

    /**
     *   0：未育，1：已育 生育状态
     */
    private DictItem childbirthStatus;

    /**
     *   最高学历
     */
    private DictItem educationBackground;

    /**
     *   最高学位
     */
    private DictItem highestDegree;

    /**
     *   参加工作日期
     */
    private String workStartDate;

    /**
     * 归档
     */
    private String recordStatus = "0";
    /**
     * 资料提交
     */
    private String dataSubmission = "0";

    //************************ tsmCard **************************
    /**
     * tsmCard中Id等于salaryCardId
     */
//    private String salaryCardId;
    /**
     * 银行账户
     */
    private String cardId;
    /**
     *   开户人
     */
    private String accountOwner;

    /**
     *   银行名称
     */
    private String bankName;

    /**
     *   开户行
     */
    private String bankAddress;

    /**
     *   开户城市
     */
    private String bankCity;

    //************************ tsmPost **************************
    /**
     * tsmPostId
     */
    private String postId;
    /**
     *   面试时间
     */
    private String interviewTime;

    /**
     *   期望薪资（税前）
     */
    private Integer expectedSalary;
    /**
     * 面试结果
     */
    private String interviewResult;
    /**
     * 放弃报道
     */
    private String abandonReporting;
    /**
     * 技术职位
     */
    private DictItem technicalPosition;
    /**
     *  招聘岗位
     */
    private TsmRecruit jobPosition;
    /**
     * 入职时间
     */
    private String entryTime;
    /**
     *   '部门',
     */
    private DictItem department;

    /**
     * 员工职级
     */
    private String employeeRank;



    public TsmInductionBasicEnhanceResponse tsmInductionBasicConvert(TsmInductionBasic basic, TsmRecruitService tsmRecruitService) {
        BeanUtil.copyProperties(basic, this);
         this.buildTsmInfo(basic.getTsmInductionInfoList())
            .buildTsmCard(basic.getTsmInductionInfoList(), basic.getTsmCardList())
            .buildTsmPost(basic.getTsmPostList(),tsmRecruitService);


        return this;
    }

    public TsmInductionBasicEnhanceResponse buildTsmInfo(List<TsmInductionInfo> tsmInductionInfos) {
        TsmInductionInfo tsmInductionInfo;
        if (ObjUtil.isNull(tsmInductionInfo = CollUtil.getFirst(tsmInductionInfos))) {
            return this;
        }

        this.infoId= tsmInductionInfo.getId();
        this.employeeNumber= tsmInductionInfo.getEmployeeNumber();
        this.accumulationFundId= tsmInductionInfo.getAccumulationFundId();
        this.salaryCardId= tsmInductionInfo.getSalaryCardId();
        this.nationality= tsmInductionInfo.getNationality();
        this.householdRegisterLocation= tsmInductionInfo.getHouseholdRegisterLocation();
        this.householdRegisterType= tsmInductionInfo.getHouseholdRegisterType();
        this.maritalStatus= tsmInductionInfo.getMaritalStatus();
        this.politicalStatus= tsmInductionInfo.getPoliticalStatus();
        this.childbirthStatus= tsmInductionInfo.getChildbirthStatus();
        this.educationBackground= tsmInductionInfo.getEducationBackground();
        this.highestDegree= tsmInductionInfo.getHighestDegree();
        this.workStartDate= tsmInductionInfo.getWorkStartDate();
        this.recordStatus = Optional.ofNullable(tsmInductionInfo.getRecordStatus()).orElse("0");
        this.dataSubmission = Optional.ofNullable(tsmInductionInfo.getDataSubmission()).orElse("0");
        return this;
    }

    public TsmInductionBasicEnhanceResponse buildTsmCard(List<TsmInductionInfo> tsmInductionInfos, List<TsmCard> tsmCards) {
        TsmInductionInfo tsmInductionInfo;
        if (ObjUtil.isNull(tsmInductionInfo = CollUtil.getFirst(tsmInductionInfos))) {
            return this;
        }
        if (CollUtil.isEmpty(tsmCards)) {
            return this;
        }
        tsmCards.stream()
            .filter(card -> card.getId().equals(tsmInductionInfo.getSalaryCardId()))
            .findFirst()
            .ifPresent(tsmCard -> {
                this.cardId= tsmCard.getAccountNumber();
                this.accountOwner = tsmCard.getAccountOwner();
                this.bankName = tsmCard.getBankName();
                this.bankAddress = tsmCard.getBankAddress();
                this.bankCity = tsmCard.getBankCity();
            });
        return this;
    }

    public TsmInductionBasicEnhanceResponse buildTsmPost(List<TsmPost> tsmInductionInfos, TsmRecruitService tsmRecruitService) {
        TsmPost tsmPost;
        if (ObjUtil.isNull(tsmPost = CollUtil.getFirst(tsmInductionInfos))) {
            return this;
        }
        this.postId = tsmPost.getId();
        this.interviewTime = tsmPost.getInterviewTime();
        this.expectedSalary = tsmPost.getExpectedSalary();
        this.interviewResult = tsmPost.getInterviewResult();
        this.abandonReporting = tsmPost.getAbandonReporting();
        this.technicalPosition = tsmPost.getTechnicalPosition();
        this.entryTime = tsmPost.getEntryTime();
        this.department = tsmPost.getDepartment();
        this.employeeRank = tsmPost.getEmployeeRank();
        Optional.ofNullable(tsmPost.getJobPosition())
            .map(DictItem::getId)
            .ifPresent(jobId -> this.jobPosition = tsmRecruitService.get(jobId));


        return this;
    }


    public TsmInductionBasicEnhanceResponse buildDepartment(DepartmentService departmentService) {

        if (ObjUtil.isNull(department)) {
            return this;
        }

        this.department = Optional.ofNullable(departmentService.get(department.getId()))
            .map(department -> { DictItem dictItem = new DictItem();
                dictItem.setId(department.getId());
                dictItem.setName(department.getName());
                return dictItem; }).orElse(department);

        return this;
    }

    /**
     * 为空赋默认值
     * @return
     */
    @Length(min = 0, max = 8, message = "data_validation长度必须介于 0 和 8 之间")
    @Override
    public String getDataValidation() {
        return StrUtil.isBlank(super.getDataValidation()) ? "0" : super.getDataValidation();
    }



}
