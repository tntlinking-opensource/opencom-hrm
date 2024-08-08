package com.geeke.recruit.entity;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * recruitEntity
 * @author
 * @version
 */
public class TsmRecruit extends WfDataEntity<TsmRecruit> {

    private static final long serialVersionUID = 1457254743943430518L;

    private String recruitCode; // 招聘编号

    private String postName; // 招聘岗位

    private String postDescribe; // 岗位描述

    private String postDemand; // 任职资格

    private String salaryLow; // 薪资下限

    private String salaryHigh; // 薪资上限

    private String salarySpecial; // 特殊工资范围

    private String staffNum; // 招聘人数

    private String deptId; // 部门id

    private String deptCode; // 事业部编号

    private String deptName; // 部门名称

    private String company; // 所属公司名称

    private String workCity; // 工作城市

    private String recruitType; // 招聘类型

    private String firstInterviewer; // 初试面试官

    private String secondInterviewer; // 复试面试官

    private String finalInterviewer; // 最终面试官

    private String formNum; // form_num

    private String recruitStatus; // 招聘状态 0-未招聘 2-招聘中 3-招聘完成 4-招聘取消

    private String candidate; // 已招聘人数

    private String actStatus = "0"; // 审批状态。0-未发起，2-审批中，3-审批通过，4-退回

    private String applyDate; // 申请时间

    private String tenantId; // tenant_id

    private String recruitStudy; // 选择学历

    private String recruitExperience; // 选择经历

    private String procdefId; // 选择经历


    // 构造方法
    public TsmRecruit() {
        super();
        this.initBuild();
    }

    public TsmRecruit(String id) {
        super(id);
        this.initBuild();
    }

    private void initBuild() {
        applyDate = LocalDateTimeUtil.format(LocalDateTime.now(), "yyy-MM-dd HH:mm:ss");
    }

    public void genRecruitCode(SequenceService sequenceService) {
        recruitCode = StrUtil.isBlank(recruitCode)
            ? sequenceService.generate("recruit", this)
            : recruitCode;
    }

    // 生成get和set方法

    @Length(min = 0, max = 32, message = "招聘编号长度必须介于 0 和 32 之间")
    public String getRecruitCode() {
        return recruitCode;
    }

    public void setRecruitCode(String recruitCode) {
        this.recruitCode = recruitCode;
    }

    @Length(min = 0, max = 64, message = "招聘岗位长度必须介于 0 和 64 之间")
    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Length(min = 0, max = -1, message = "岗位描述长度必须介于 0 和 -1 之间")
    public String getPostDescribe() {
        return postDescribe;
    }

    public void setPostDescribe(String postDescribe) {
        this.postDescribe = postDescribe;
    }

    @Length(min = 0, max = -1, message = "任职资格长度必须介于 0 和 -1 之间")
    public String getPostDemand() {
        return postDemand;
    }

    public void setPostDemand(String postDemand) {
        this.postDemand = postDemand;
    }

    @Length(min = 0, max = 32, message = "薪资下限长度必须介于 0 和 32 之间")
    public String getSalaryLow() {
        return salaryLow;
    }

    public void setSalaryLow(String salaryLow) {
        this.salaryLow = salaryLow;
    }

    @Length(min = 0, max = 32, message = "薪资上限长度必须介于 0 和 32 之间")
    public String getSalaryHigh() {
        return salaryHigh;
    }

    public void setSalaryHigh(String salaryHigh) {
        this.salaryHigh = salaryHigh;
    }

    @Length(min = 0, max = 32, message = "特殊工资范围长度必须介于 0 和 32 之间")
    public String getSalarySpecial() {
        return salarySpecial;
    }

    public void setSalarySpecial(String salarySpecial) {
        this.salarySpecial = salarySpecial;
    }

    @Length(min = 0, max = 12, message = "招聘人数长度必须介于 0 和 12 之间")
    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    @Length(min = 0, max = 255, message = "部门id长度必须介于 0 和 255 之间")
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Length(min = 0, max = 32, message = "事业部编号长度必须介于 0 和 32 之间")
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Length(min = 0, max = 32, message = "部门名称长度必须介于 0 和 32 之间")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Length(min = 0, max = 32, message = "所属公司名称长度必须介于 0 和 32 之间")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Length(min = 0, max = 32, message = "工作城市长度必须介于 0 和 32 之间")
    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    @Length(min = 0, max = 32, message = "招聘类型长度必须介于 0 和 32 之间")
    public String getRecruitType() {
        return recruitType;
    }

    public void setRecruitType(String recruitType) {
        this.recruitType = recruitType;
    }

    @Length(min = 0, max = 255, message = "初试面试官长度必须介于 0 和 255 之间")
    public String getFirstInterviewer() {
        return firstInterviewer;
    }

    public void setFirstInterviewer(String firstInterviewer) {
        this.firstInterviewer = firstInterviewer;
    }

    @Length(min = 0, max = 255, message = "复试面试官长度必须介于 0 和 255 之间")
    public String getSecondInterviewer() {
        return secondInterviewer;
    }

    public void setSecondInterviewer(String secondInterviewer) {
        this.secondInterviewer = secondInterviewer;
    }

    @Length(min = 0, max = 255, message = "最终面试官长度必须介于 0 和 255 之间")
    public String getFinalInterviewer() {
        return finalInterviewer;
    }

    public void setFinalInterviewer(String finalInterviewer) {
        this.finalInterviewer = finalInterviewer;
    }

    @Length(min = 0, max = 100, message = "form_num长度必须介于 0 和 100 之间")
    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }

    @Length(min = 0, max = 10, message = "招聘状态 0-未招聘 2-招聘中 3-招聘完成 4-招聘取消长度必须介于 0 和 10 之间")
    public String getRecruitStatus() {
        return recruitStatus;
    }

    public void setRecruitStatus(String recruitStatus) {
        this.recruitStatus = recruitStatus;
    }

    @Length(min = 0, max = 100, message = "已招聘人数长度必须介于 0 和 100 之间")
    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    @Length(min = 0, max = 2, message = "审批状态。0-未发起，2-审批中，3-审批通过，4-退回长度必须介于 0 和 2 之间")
    public String getActStatus() {
        return actStatus;
    }

    public void setActStatus(String actStatus) {
        this.actStatus = actStatus;
    }

    @Length(min = 0, max = -1, message = "申请时间长度必须介于 0 和 -1 之间")
    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    @Length(min = 0, max = 100, message = "tenant_id长度必须介于 0 和 100 之间")
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Length(min = 0, max = 2, message = "选择学历长度必须介于 0 和 2 之间")
    public String getRecruitStudy() {
        return recruitStudy;
    }

    public void setRecruitStudy(String recruitStudy) {
        this.recruitStudy = recruitStudy;
    }

    @Length(min = 0, max = 2, message = "选择经历长度必须介于 0 和 2 之间")
    public String getRecruitExperience() {
        return recruitExperience;
    }

    public void setRecruitExperience(String recruitExperience) {
        this.recruitExperience = recruitExperience;
    }

    @Length(min = 0, max = 21, message = "选择经历长度必须介于 0 和 21 之间")
    public String getprocdefId() {
        return procdefId;
    }

    public void setprocdefId(String procdefId) {
        this.procdefId = procdefId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tsm_recruit";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1457254743943430518";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "招聘表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }

    @Override
    public void setProcStatus(DictItem procStatus) {
        this.procStatus = procStatus;
        this.actStatus = StrUtil.isNotBlank(procStatus.getId()) ? procStatus.getId() : this.actStatus;
    }
}
