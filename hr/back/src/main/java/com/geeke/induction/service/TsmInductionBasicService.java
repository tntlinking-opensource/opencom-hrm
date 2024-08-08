package com.geeke.induction.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.service.UserService;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.BaseEntity;
import com.geeke.induction.contract.service.InductionBatchContractService;
import com.geeke.induction.dao.*;
import com.geeke.induction.dto.AppHomePageDto;
import com.geeke.induction.dto.ContractDemandDto;
import com.geeke.induction.entity.*;
import com.geeke.induction.enums.OperationEnum;
import com.geeke.induction.request.InductionHomeSearchRequest;
import com.geeke.induction.request.TsmInductionBasicEnhanceRequest;
import com.geeke.induction.request.TsmInductionInfoCompletionRequest;
import com.geeke.induction.response.TsmInductionBasicEnhanceResponse;
import com.geeke.org.service.DepartmentService;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.IdGen;
import com.geeke.utils.Reflections;
import com.geeke.utils.StringUtils;
import com.geeke.wf.entity.WfDraft;
import com.geeke.wf.persistence.WfDataEntity;
import com.geeke.wf.service.NoneBizInProcService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmInductionBasicService")
@Transactional(readOnly = true)
public class TsmInductionBasicService extends NoneBizInProcService<TsmInductionBasicDao, TsmInductionBasic> {

    @Autowired
    private TsmInductionInfoDao tsmInductionInfoDao;

    @Autowired
    private TsmInductionInfoService tsmInductionInfoService;

    @Autowired
    private TsmPostDao tsmPostDao;

    @Autowired
    private TsmPostService tsmPostService;

    @Autowired
    private TsmWorkExperienceDao tsmWorkExperienceDao;

    @Autowired
    private TsmWorkExperienceService tsmWorkExperienceService;

    @Autowired
    private TsmProjectExperienceDao tsmProjectExperienceDao;

    @Autowired
    private TsmProjectExperienceService tsmProjectExperienceService;

    @Autowired
    private TsmTrainingExperienceDao tsmTrainingExperienceDao;

    @Autowired
    private TsmTrainingExperienceService tsmTrainingExperienceService;

    @Autowired
    private TsmQualificationDao tsmQualificationDao;

    @Autowired
    private TsmQualificationService tsmQualificationService;

    @Autowired
    private TsmEducationalRecordsDao tsmEducationalRecordsDao;

    @Autowired
    private TsmEducationalRecordsService tsmEducationalRecordsService;

    @Autowired
    private TsmPersonalSkillsDao tsmPersonalSkillsDao;

    @Autowired
    private TsmPersonalSkillsService tsmPersonalSkillsService;

    @Autowired
    private TsmFamilyMemberInfoDao tsmFamilyMemberInfoDao;

    @Autowired
    private TsmFamilyMemberInfoService tsmFamilyMemberInfoService;

    @Autowired
    private TsmEmergencyContactDao tsmEmergencyContactDao;

    @Autowired
    private TsmEmergencyContactService tsmEmergencyContactService;

    @Autowired
    private TsmCardDao tsmCardDao;

    @Autowired
    private TsmCardService tsmCardService;

    @Autowired
    private TsmAssetInformationDao tsmAssetInformationDao;

    @Autowired
    private TsmAssetInformationService tsmAssetInformationService;

    @Autowired
    private TsmEmployeeAttachmentsDao tsmEmployeeAttachmentsDao;

    @Autowired
    private TsmEmployeeAttachmentsService tsmEmployeeAttachmentsService;

    @Autowired
    private TsmContractInformationDao tsmContractInformationDao;

    @Autowired
    private TsmContractInformationService tsmContractInformationService;

    @Autowired
    private TsmInductionBasicDao tsmInductionBasicDao;

    @Autowired
    private TsmRecruitService tsmRecruitService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private InductionBatchContractService inductionBatchContractService;

    @Override
    public TsmInductionBasic get(String id) {
        TsmInductionBasic tsmInductionBasic = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   岗位信息*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmPostList(tsmPostService.listAll(params, ""));

        /*获取子表列表   工作经历*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmWorkExperienceList(tsmWorkExperienceService.listAll(params, ""));

        /*获取子表列表   项目经历*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmProjectExperienceList(tsmProjectExperienceService.listAll(params, ""));

        /*获取子表列表   培训经历*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmTrainingExperienceList(tsmTrainingExperienceService.listAll(params, ""));

        /*获取子表列表   资格证书*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmQualificationList(tsmQualificationService.listAll(params, ""));

        /*获取子表列表   教育经历*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmEducationalRecordsList(tsmEducationalRecordsService.listAll(params, ""));

        /*获取子表列表   个人技能*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmPersonalSkillsList(tsmPersonalSkillsService.listAll(params, ""));

        /*获取子表列表   家庭信息*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmFamilyMemberInfoList(tsmFamilyMemberInfoService.listAll(params, ""));

        /*获取子表列表   紧急联系人*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmEmergencyContactList(tsmEmergencyContactService.listAll(params, ""));

        /*获取子表列表   卡片*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmCardList(tsmCardService.listAll(params, ""));

        /*获取子表列表   资产信息*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmAssetInformationList(tsmAssetInformationService.listAll(params, ""));

        /*获取子表列表   员工附件*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmEmployeeAttachmentsList(tsmEmployeeAttachmentsService.listAll(params, ""));

        /*获取子表列表   info*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmInductionInfoList(tsmInductionInfoService.listAll(params, ""));

        /*获取子表列表   info*/
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        tsmInductionBasic.setTsmContractInformationList(tsmContractInformationService.listAll(params, ""));

        return tsmInductionBasic;
    }

    /**
     * 删除
     * @param tsmInductionBasic
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     个人信息补充表 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmInductionInfoList(tsmInductionInfoDao.listAll(pageRequest));

        if (tsmInductionBasic.getTsmInductionInfoList() != null && tsmInductionBasic.getTsmInductionInfoList().size() > 0) {
            tsmInductionInfoService.bulkDelete(tsmInductionBasic.getTsmInductionInfoList());
        }

        /* 处理子表     岗位信息 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmPostList(tsmPostDao.listAll(pageRequest));

        if (tsmInductionBasic.getTsmPostList() != null && tsmInductionBasic.getTsmPostList().size() > 0) {
            tsmPostService.bulkDelete(tsmInductionBasic.getTsmPostList());
        }

        /* 处理子表     工作经历 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmWorkExperienceList(tsmWorkExperienceDao.listAll(pageRequest));

        if (tsmInductionBasic.getTsmWorkExperienceList() != null && tsmInductionBasic.getTsmWorkExperienceList().size() > 0) {
            tsmWorkExperienceService.bulkDelete(tsmInductionBasic.getTsmWorkExperienceList());
        }

        /* 处理子表     项目经历 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmProjectExperienceList(tsmProjectExperienceDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmProjectExperienceList() != null &&
            tsmInductionBasic.getTsmProjectExperienceList().size() > 0
        ) {
            tsmProjectExperienceService.bulkDelete(tsmInductionBasic.getTsmProjectExperienceList());
        }

        /* 处理子表     培训经历 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmTrainingExperienceList(tsmTrainingExperienceDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmTrainingExperienceList() != null &&
            tsmInductionBasic.getTsmTrainingExperienceList().size() > 0
        ) {
            tsmTrainingExperienceService.bulkDelete(tsmInductionBasic.getTsmTrainingExperienceList());
        }

        /* 处理子表     资格证书 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmQualificationList(tsmQualificationDao.listAll(pageRequest));

        if (tsmInductionBasic.getTsmQualificationList() != null && tsmInductionBasic.getTsmQualificationList().size() > 0) {
            tsmQualificationService.bulkDelete(tsmInductionBasic.getTsmQualificationList());
        }

        /* 处理子表     教育经历 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmEducationalRecordsList(tsmEducationalRecordsDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmEducationalRecordsList() != null &&
            tsmInductionBasic.getTsmEducationalRecordsList().size() > 0
        ) {
            tsmEducationalRecordsService.bulkDelete(tsmInductionBasic.getTsmEducationalRecordsList());
        }

        /* 处理子表     个人技能 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmPersonalSkillsList(tsmPersonalSkillsDao.listAll(pageRequest));

        if (tsmInductionBasic.getTsmPersonalSkillsList() != null && tsmInductionBasic.getTsmPersonalSkillsList().size() > 0) {
            tsmPersonalSkillsService.bulkDelete(tsmInductionBasic.getTsmPersonalSkillsList());
        }

        /* 处理子表     家庭信息 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmFamilyMemberInfoList(tsmFamilyMemberInfoDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmFamilyMemberInfoList() != null && tsmInductionBasic.getTsmFamilyMemberInfoList().size() > 0
        ) {
            tsmFamilyMemberInfoService.bulkDelete(tsmInductionBasic.getTsmFamilyMemberInfoList());
        }

        /* 处理子表     紧急联系人 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmEmergencyContactList(tsmEmergencyContactDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmEmergencyContactList() != null && tsmInductionBasic.getTsmEmergencyContactList().size() > 0
        ) {
            tsmEmergencyContactService.bulkDelete(tsmInductionBasic.getTsmEmergencyContactList());
        }

        /* 处理子表     卡片 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmCardList(tsmCardDao.listAll(pageRequest));

        if (tsmInductionBasic.getTsmCardList() != null && tsmInductionBasic.getTsmCardList().size() > 0) {
            tsmCardService.bulkDelete(tsmInductionBasic.getTsmCardList());
        }

        /* 处理子表     资产信息 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmAssetInformationList(tsmAssetInformationDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmAssetInformationList() != null && tsmInductionBasic.getTsmAssetInformationList().size() > 0
        ) {
            tsmAssetInformationService.bulkDelete(tsmInductionBasic.getTsmAssetInformationList());
        }

        /* 处理子表     员工附件 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmEmployeeAttachmentsList(tsmEmployeeAttachmentsDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmEmployeeAttachmentsList() != null &&
            tsmInductionBasic.getTsmEmployeeAttachmentsList().size() > 0
        ) {
            tsmEmployeeAttachmentsService.bulkDelete(tsmInductionBasic.getTsmEmployeeAttachmentsList());
        }

        /* 处理子表     合约信息 */
        params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        pageRequest = new PageRequest(params);
        tsmInductionBasic.setTsmContractInformationList(tsmContractInformationDao.listAll(pageRequest));

        if (
            tsmInductionBasic.getTsmContractInformationList() != null &&
            tsmInductionBasic.getTsmContractInformationList().size() > 0
        ) {
            tsmContractInformationService.bulkDelete(tsmInductionBasic.getTsmContractInformationList());
        }

        int rows = super.delete(tsmInductionBasic);
        return rows;
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TsmInductionBasic entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (TsmInductionInfo child : entity.getTsmInductionInfoList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmPost child : entity.getTsmPostList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmWorkExperience child : entity.getTsmWorkExperienceList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmProjectExperience child : entity.getTsmProjectExperienceList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmTrainingExperience child : entity.getTsmTrainingExperienceList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmQualification child : entity.getTsmQualificationList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmEducationalRecords child : entity.getTsmEducationalRecordsList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmPersonalSkills child : entity.getTsmPersonalSkillsList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmFamilyMemberInfo child : entity.getTsmFamilyMemberInfoList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmEmergencyContact child : entity.getTsmEmergencyContactList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmCard child : entity.getTsmCardList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmAssetInformation child : entity.getTsmAssetInformationList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmEmployeeAttachments child : entity.getTsmEmployeeAttachmentsList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }

            for (TsmContractInformation child : entity.getTsmContractInformationList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    @Override
    protected int doInsert(TsmInductionBasic tsmInductionBasic) {
        int rows = dao.insert(tsmInductionBasic);
        if (rows > 0) {
            /* 保存子表数据     个人信息补充表 */
            saveTsmInductionInfoList(tsmInductionBasic);

            /* 保存子表数据     岗位信息 */
            saveTsmPostList(tsmInductionBasic);

            /* 保存子表数据     工作经历 */
            saveTsmWorkExperienceList(tsmInductionBasic);

            /* 保存子表数据     项目经历 */
            saveTsmProjectExperienceList(tsmInductionBasic);

            /* 保存子表数据     培训经历 */
            saveTsmTrainingExperienceList(tsmInductionBasic);

            /* 保存子表数据     资格证书 */
            saveTsmQualificationList(tsmInductionBasic);

            /* 保存子表数据     教育经历 */
            saveTsmEducationalRecordsList(tsmInductionBasic);

            /* 保存子表数据     个人技能 */
            saveTsmPersonalSkillsList(tsmInductionBasic);

            /* 保存子表数据     家庭信息 */
            saveTsmFamilyMemberInfoList(tsmInductionBasic);

            /* 保存子表数据     紧急联系人 */
            saveTsmEmergencyContactList(tsmInductionBasic);

            /* 保存子表数据     卡片 */
            saveTsmCardList(tsmInductionBasic);

            /* 保存子表数据     资产信息 */
            saveTsmAssetInformationList(tsmInductionBasic);

            /* 保存子表数据     员工附件 */
            saveTsmEmployeeAttachmentsList(tsmInductionBasic);

            /* 保存子表数据     合约信息 */
            saveTsmContractInformationList(tsmInductionBasic);
        }
        return rows;
    }

    @Override
    protected int doUpdate(TsmInductionBasic tsmInductionBasic) {
        int rows = dao.update(tsmInductionBasic);
        if (rows > 0) {
            /* 保存子表数据     个人信息补充表 */
            saveTsmInductionInfoList(tsmInductionBasic);

            /* 保存子表数据     岗位信息 */
            saveTsmPostList(tsmInductionBasic);

            /* 保存子表数据     工作经历 */
            saveTsmWorkExperienceList(tsmInductionBasic);

            /* 保存子表数据     项目经历 */
            saveTsmProjectExperienceList(tsmInductionBasic);

            /* 保存子表数据     培训经历 */
            saveTsmTrainingExperienceList(tsmInductionBasic);

            /* 保存子表数据     资格证书 */
            saveTsmQualificationList(tsmInductionBasic);

            /* 保存子表数据     教育经历 */
            saveTsmEducationalRecordsList(tsmInductionBasic);

            /* 保存子表数据     个人技能 */
            saveTsmPersonalSkillsList(tsmInductionBasic);

            /* 保存子表数据     家庭信息 */
            saveTsmFamilyMemberInfoList(tsmInductionBasic);

            /* 保存子表数据     紧急联系人 */
            saveTsmEmergencyContactList(tsmInductionBasic);

            /* 保存子表数据     卡片 */
            saveTsmCardList(tsmInductionBasic);

            /* 保存子表数据     资产信息 */
            saveTsmAssetInformationList(tsmInductionBasic);

            /* 保存子表数据     员工附件 */
            saveTsmEmployeeAttachmentsList(tsmInductionBasic);

            /* 保存子表数据     合约信息 */
            saveTsmContractInformationList(tsmInductionBasic);
        }
        return rows;
    }

    /* 保存子表数据     个人信息补充表 */
    private void saveTsmInductionInfoList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmInductionInfo> list_TsmInductionInfo = tsmInductionInfoDao.listAll(pageRequest);

        List<TsmInductionInfo> deletes = Lists.newArrayList(); // 删除列表
        List<TsmInductionInfo> inserts = Lists.newArrayList(); // 添加列表
        List<TsmInductionInfo> updates = Lists.newArrayList(); // 更新列表
        for (TsmInductionInfo tsmInductionInfoSaved : list_TsmInductionInfo) {
            boolean found = false;
            for (TsmInductionInfo tsmInductionInfo : tsmInductionBasic.getTsmInductionInfoList()) {
                if (tsmInductionInfoSaved.getId().equals(tsmInductionInfo.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmInductionInfoSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmInductionInfoService.bulkDelete(deletes);
        }
        for (TsmInductionInfo tsmInductionInfo : tsmInductionBasic.getTsmInductionInfoList()) {
            if (StringUtils.isBlank(tsmInductionInfo.getId())) {
                tsmInductionInfo.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmInductionInfo);
            } else {
                updates.add(tsmInductionInfo);
            }
        }
        if (updates.size() > 0) {
            tsmInductionInfoService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmInductionInfoService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     岗位信息 */
    private void saveTsmPostList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmPost> list_TsmPost = tsmPostDao.listAll(pageRequest);

        List<TsmPost> deletes = Lists.newArrayList(); // 删除列表
        List<TsmPost> inserts = Lists.newArrayList(); // 添加列表
        List<TsmPost> updates = Lists.newArrayList(); // 更新列表
        for (TsmPost tsmPostSaved : list_TsmPost) {
            boolean found = false;
            for (TsmPost tsmPost : tsmInductionBasic.getTsmPostList()) {
                if (tsmPostSaved.getId().equals(tsmPost.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmPostSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmPostService.bulkDelete(deletes);
        }
        for (TsmPost tsmPost : tsmInductionBasic.getTsmPostList()) {
            if (StringUtils.isBlank(tsmPost.getId())) {
                tsmPost.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmPost);
            } else {
                updates.add(tsmPost);
            }
        }
        if (updates.size() > 0) {
            tsmPostService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmPostService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     工作经历 */
    private void saveTsmWorkExperienceList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmWorkExperience> list_TsmWorkExperience = tsmWorkExperienceDao.listAll(pageRequest);

        List<TsmWorkExperience> deletes = Lists.newArrayList(); // 删除列表
        List<TsmWorkExperience> inserts = Lists.newArrayList(); // 添加列表
        List<TsmWorkExperience> updates = Lists.newArrayList(); // 更新列表
        for (TsmWorkExperience tsmWorkExperienceSaved : list_TsmWorkExperience) {
            boolean found = false;
            for (TsmWorkExperience tsmWorkExperience : tsmInductionBasic.getTsmWorkExperienceList()) {
                if (tsmWorkExperienceSaved.getId().equals(tsmWorkExperience.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmWorkExperienceSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmWorkExperienceService.bulkDelete(deletes);
        }
        for (TsmWorkExperience tsmWorkExperience : tsmInductionBasic.getTsmWorkExperienceList()) {
            if (StringUtils.isBlank(tsmWorkExperience.getId())) {
                tsmWorkExperience.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmWorkExperience);
            } else {
                updates.add(tsmWorkExperience);
            }
        }
        if (updates.size() > 0) {
            tsmWorkExperienceService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmWorkExperienceService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     项目经历 */
    private void saveTsmProjectExperienceList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmProjectExperience> list_TsmProjectExperience = tsmProjectExperienceDao.listAll(pageRequest);

        List<TsmProjectExperience> deletes = Lists.newArrayList(); // 删除列表
        List<TsmProjectExperience> inserts = Lists.newArrayList(); // 添加列表
        List<TsmProjectExperience> updates = Lists.newArrayList(); // 更新列表
        for (TsmProjectExperience tsmProjectExperienceSaved : list_TsmProjectExperience) {
            boolean found = false;
            for (TsmProjectExperience tsmProjectExperience : tsmInductionBasic.getTsmProjectExperienceList()) {
                if (tsmProjectExperienceSaved.getId().equals(tsmProjectExperience.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmProjectExperienceSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmProjectExperienceService.bulkDelete(deletes);
        }
        for (TsmProjectExperience tsmProjectExperience : tsmInductionBasic.getTsmProjectExperienceList()) {
            if (StringUtils.isBlank(tsmProjectExperience.getId())) {
                tsmProjectExperience.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmProjectExperience);
            } else {
                updates.add(tsmProjectExperience);
            }
        }
        if (updates.size() > 0) {
            tsmProjectExperienceService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmProjectExperienceService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     培训经历 */
    private void saveTsmTrainingExperienceList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmTrainingExperience> list_TsmTrainingExperience = tsmTrainingExperienceDao.listAll(pageRequest);

        List<TsmTrainingExperience> deletes = Lists.newArrayList(); // 删除列表
        List<TsmTrainingExperience> inserts = Lists.newArrayList(); // 添加列表
        List<TsmTrainingExperience> updates = Lists.newArrayList(); // 更新列表
        for (TsmTrainingExperience tsmTrainingExperienceSaved : list_TsmTrainingExperience) {
            boolean found = false;
            for (TsmTrainingExperience tsmTrainingExperience : tsmInductionBasic.getTsmTrainingExperienceList()) {
                if (tsmTrainingExperienceSaved.getId().equals(tsmTrainingExperience.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmTrainingExperienceSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmTrainingExperienceService.bulkDelete(deletes);
        }
        for (TsmTrainingExperience tsmTrainingExperience : tsmInductionBasic.getTsmTrainingExperienceList()) {
            if (StringUtils.isBlank(tsmTrainingExperience.getId())) {
                tsmTrainingExperience.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmTrainingExperience);
            } else {
                updates.add(tsmTrainingExperience);
            }
        }
        if (updates.size() > 0) {
            tsmTrainingExperienceService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmTrainingExperienceService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     资格证书 */
    private void saveTsmQualificationList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmQualification> list_TsmQualification = tsmQualificationDao.listAll(pageRequest);

        List<TsmQualification> deletes = Lists.newArrayList(); // 删除列表
        List<TsmQualification> inserts = Lists.newArrayList(); // 添加列表
        List<TsmQualification> updates = Lists.newArrayList(); // 更新列表
        for (TsmQualification tsmQualificationSaved : list_TsmQualification) {
            boolean found = false;
            for (TsmQualification tsmQualification : tsmInductionBasic.getTsmQualificationList()) {
                if (tsmQualificationSaved.getId().equals(tsmQualification.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmQualificationSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmQualificationService.bulkDelete(deletes);
        }
        for (TsmQualification tsmQualification : tsmInductionBasic.getTsmQualificationList()) {
            if (StringUtils.isBlank(tsmQualification.getId())) {
                tsmQualification.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmQualification);
            } else {
                updates.add(tsmQualification);
            }
        }
        if (updates.size() > 0) {
            tsmQualificationService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmQualificationService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     教育经历 */
    private void saveTsmEducationalRecordsList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmEducationalRecords> list_TsmEducationalRecords = tsmEducationalRecordsDao.listAll(pageRequest);

        List<TsmEducationalRecords> deletes = Lists.newArrayList(); // 删除列表
        List<TsmEducationalRecords> inserts = Lists.newArrayList(); // 添加列表
        List<TsmEducationalRecords> updates = Lists.newArrayList(); // 更新列表
        for (TsmEducationalRecords tsmEducationalRecordsSaved : list_TsmEducationalRecords) {
            boolean found = false;
            for (TsmEducationalRecords tsmEducationalRecords : tsmInductionBasic.getTsmEducationalRecordsList()) {
                if (tsmEducationalRecordsSaved.getId().equals(tsmEducationalRecords.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmEducationalRecordsSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmEducationalRecordsService.bulkDelete(deletes);
        }
        for (TsmEducationalRecords tsmEducationalRecords : tsmInductionBasic.getTsmEducationalRecordsList()) {
            if (StringUtils.isBlank(tsmEducationalRecords.getId())) {
                tsmEducationalRecords.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmEducationalRecords);
            } else {
                updates.add(tsmEducationalRecords);
            }
        }
        if (updates.size() > 0) {
            tsmEducationalRecordsService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmEducationalRecordsService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     个人技能 */
    private void saveTsmPersonalSkillsList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmPersonalSkills> list_TsmPersonalSkills = tsmPersonalSkillsDao.listAll(pageRequest);

        List<TsmPersonalSkills> deletes = Lists.newArrayList(); // 删除列表
        List<TsmPersonalSkills> inserts = Lists.newArrayList(); // 添加列表
        List<TsmPersonalSkills> updates = Lists.newArrayList(); // 更新列表
        for (TsmPersonalSkills tsmPersonalSkillsSaved : list_TsmPersonalSkills) {
            boolean found = false;
            for (TsmPersonalSkills tsmPersonalSkills : tsmInductionBasic.getTsmPersonalSkillsList()) {
                if (tsmPersonalSkillsSaved.getId().equals(tsmPersonalSkills.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmPersonalSkillsSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmPersonalSkillsService.bulkDelete(deletes);
        }
        for (TsmPersonalSkills tsmPersonalSkills : tsmInductionBasic.getTsmPersonalSkillsList()) {
            if (StringUtils.isBlank(tsmPersonalSkills.getId())) {
                tsmPersonalSkills.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmPersonalSkills);
            } else {
                updates.add(tsmPersonalSkills);
            }
        }
        if (updates.size() > 0) {
            tsmPersonalSkillsService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmPersonalSkillsService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     家庭信息 */
    private void saveTsmFamilyMemberInfoList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmFamilyMemberInfo> list_TsmFamilyMemberInfo = tsmFamilyMemberInfoDao.listAll(pageRequest);

        List<TsmFamilyMemberInfo> deletes = Lists.newArrayList(); // 删除列表
        List<TsmFamilyMemberInfo> inserts = Lists.newArrayList(); // 添加列表
        List<TsmFamilyMemberInfo> updates = Lists.newArrayList(); // 更新列表
        for (TsmFamilyMemberInfo tsmFamilyMemberInfoSaved : list_TsmFamilyMemberInfo) {
            boolean found = false;
            for (TsmFamilyMemberInfo tsmFamilyMemberInfo : tsmInductionBasic.getTsmFamilyMemberInfoList()) {
                if (tsmFamilyMemberInfoSaved.getId().equals(tsmFamilyMemberInfo.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmFamilyMemberInfoSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmFamilyMemberInfoService.bulkDelete(deletes);
        }
        for (TsmFamilyMemberInfo tsmFamilyMemberInfo : tsmInductionBasic.getTsmFamilyMemberInfoList()) {
            if (StringUtils.isBlank(tsmFamilyMemberInfo.getId())) {
                tsmFamilyMemberInfo.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmFamilyMemberInfo);
            } else {
                updates.add(tsmFamilyMemberInfo);
            }
        }
        if (updates.size() > 0) {
            tsmFamilyMemberInfoService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmFamilyMemberInfoService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     紧急联系人 */
    private void saveTsmEmergencyContactList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmEmergencyContact> list_TsmEmergencyContact = tsmEmergencyContactDao.listAll(pageRequest);

        List<TsmEmergencyContact> deletes = Lists.newArrayList(); // 删除列表
        List<TsmEmergencyContact> inserts = Lists.newArrayList(); // 添加列表
        List<TsmEmergencyContact> updates = Lists.newArrayList(); // 更新列表
        for (TsmEmergencyContact tsmEmergencyContactSaved : list_TsmEmergencyContact) {
            boolean found = false;
            for (TsmEmergencyContact tsmEmergencyContact : tsmInductionBasic.getTsmEmergencyContactList()) {
                if (tsmEmergencyContactSaved.getId().equals(tsmEmergencyContact.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmEmergencyContactSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmEmergencyContactService.bulkDelete(deletes);
        }
        for (TsmEmergencyContact tsmEmergencyContact : tsmInductionBasic.getTsmEmergencyContactList()) {
            if (StringUtils.isBlank(tsmEmergencyContact.getId())) {
                tsmEmergencyContact.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmEmergencyContact);
            } else {
                updates.add(tsmEmergencyContact);
            }
        }
        if (updates.size() > 0) {
            tsmEmergencyContactService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmEmergencyContactService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     卡片 */
    private void saveTsmCardList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmCard> list_TsmCard = tsmCardDao.listAll(pageRequest);

        List<TsmCard> deletes = Lists.newArrayList(); // 删除列表
        List<TsmCard> inserts = Lists.newArrayList(); // 添加列表
        List<TsmCard> updates = Lists.newArrayList(); // 更新列表
        for (TsmCard tsmCardSaved : list_TsmCard) {
            boolean found = false;
            for (TsmCard tsmCard : tsmInductionBasic.getTsmCardList()) {
                if (tsmCardSaved.getId().equals(tsmCard.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmCardSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmCardService.bulkDelete(deletes);
        }
        for (TsmCard tsmCard : tsmInductionBasic.getTsmCardList()) {
            if (StringUtils.isBlank(tsmCard.getId())) {
                tsmCard.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmCard);
            } else {
                updates.add(tsmCard);
            }
        }
        if (updates.size() > 0) {
            tsmCardService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmCardService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     资产信息 */
    private void saveTsmAssetInformationList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmAssetInformation> list_TsmAssetInformation = tsmAssetInformationDao.listAll(pageRequest);

        List<TsmAssetInformation> deletes = Lists.newArrayList(); // 删除列表
        List<TsmAssetInformation> inserts = Lists.newArrayList(); // 添加列表
        List<TsmAssetInformation> updates = Lists.newArrayList(); // 更新列表
        for (TsmAssetInformation tsmAssetInformationSaved : list_TsmAssetInformation) {
            boolean found = false;
            for (TsmAssetInformation tsmAssetInformation : tsmInductionBasic.getTsmAssetInformationList()) {
                if (tsmAssetInformationSaved.getId().equals(tsmAssetInformation.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmAssetInformationSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmAssetInformationService.bulkDelete(deletes);
        }
        for (TsmAssetInformation tsmAssetInformation : tsmInductionBasic.getTsmAssetInformationList()) {
            if (StringUtils.isBlank(tsmAssetInformation.getId())) {
                tsmAssetInformation.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmAssetInformation);
            } else {
                updates.add(tsmAssetInformation);
            }
        }
        if (updates.size() > 0) {
            tsmAssetInformationService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmAssetInformationService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     员工附件 */
    private void saveTsmEmployeeAttachmentsList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmEmployeeAttachments> list_TsmEmployeeAttachments = tsmEmployeeAttachmentsDao.listAll(pageRequest);

        List<TsmEmployeeAttachments> deletes = Lists.newArrayList(); // 删除列表
        List<TsmEmployeeAttachments> inserts = Lists.newArrayList(); // 添加列表
        List<TsmEmployeeAttachments> updates = Lists.newArrayList(); // 更新列表
        for (TsmEmployeeAttachments tsmEmployeeAttachmentsSaved : list_TsmEmployeeAttachments) {
            boolean found = false;
            for (TsmEmployeeAttachments tsmEmployeeAttachments : tsmInductionBasic.getTsmEmployeeAttachmentsList()) {
                if (tsmEmployeeAttachmentsSaved.getId().equals(tsmEmployeeAttachments.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmEmployeeAttachmentsSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmEmployeeAttachmentsService.bulkDelete(deletes);
        }
        for (TsmEmployeeAttachments tsmEmployeeAttachments : tsmInductionBasic.getTsmEmployeeAttachmentsList()) {
            if (StringUtils.isBlank(tsmEmployeeAttachments.getId())) {
                tsmEmployeeAttachments.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmEmployeeAttachments);
            } else {
                updates.add(tsmEmployeeAttachments);
            }
        }
        if (updates.size() > 0) {
            tsmEmployeeAttachmentsService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmEmployeeAttachmentsService.bulkInsert(inserts);
        }
    }

    /* 保存子表数据     合约信息 */
    private void saveTsmContractInformationList(TsmInductionBasic tsmInductionBasic) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("induction_id", "=", tsmInductionBasic.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TsmContractInformation> list_TsmContractInformation = tsmContractInformationDao.listAll(pageRequest);

        List<TsmContractInformation> deletes = Lists.newArrayList(); // 删除列表
        List<TsmContractInformation> inserts = Lists.newArrayList(); // 添加列表
        List<TsmContractInformation> updates = Lists.newArrayList(); // 更新列表
        for (TsmContractInformation tsmContractInformationSaved : list_TsmContractInformation) {
            boolean found = false;
            for (TsmContractInformation tsmContractInformation : tsmInductionBasic.getTsmContractInformationList()) {
                if (tsmContractInformationSaved.getId().equals(tsmContractInformation.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(tsmContractInformationSaved);
            }
        }
        if (deletes.size() > 0) {
            tsmContractInformationService.bulkDelete(deletes);
        }
        for (TsmContractInformation tsmContractInformation : tsmInductionBasic.getTsmContractInformationList()) {
            if (StringUtils.isBlank(tsmContractInformation.getId())) {
                tsmContractInformation.setInductionId(tsmInductionBasic.getId());

                inserts.add(tsmContractInformation);
            } else {
                updates.add(tsmContractInformation);
            }
        }
        if (updates.size() > 0) {
            tsmContractInformationService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            tsmContractInformationService.bulkInsert(inserts);
        }
    }

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmInductionBasic> getBizClass() {
        return TsmInductionBasic.class;
    }


    /**
     * 保存单个招聘实体
     * @param entity 数据实体
     * @param procFlag 流程flag
     * @return 保存后的数据实体，一个填充了id的数据
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public TsmInductionBasic saveTsmInductionBasic(TsmInductionBasic entity, String procFlag) {
        TsmInductionBasic oldTsmRecruit;
        if (StrUtil.isNotBlank(entity.getId()) && ObjUtil.isNotNull(oldTsmRecruit = get(entity.getId()))) {
            entity.setUpdateDate(oldTsmRecruit.getUpdateDate());
            entity.preUpdate();
            entity.setProcStatusByValue(procFlag);
            doUpdate(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_UPDATED, entity));
        } else {
            entity.preInsert();
            entity.setProcStatusByValue(procFlag);
            doInsert(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, entity));
        }
        return entity;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int saveDraft(String strDraft, MultipartFile[] attachmentsReq, AppHomePageDto entity) throws IOException {
        TsmInductionBasic basic = this.buildHomeTsmInductionBasic(entity);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        MultipartFile[] attachments = ArrayUtil.isEmpty(attachmentsReq) ? new MultipartFile[]{} : attachmentsReq;

        //这里的entity set数据是为了 存入草稿时候的json数据 能够pass一些规则  度过更新逻辑
        basic.setProcStatusByValue(WfDataEntity.PROC_FLAG_DRAFT);
        basic.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int ret = this.saveDraft(wfDraft, basic, ArrayUtil.isEmpty(attachments) ? new MultipartFile[]{} : attachments);

        //这里的set是为了存入草稿id和附件id 连通逻辑与前端交互
//        basic.setProcdefId(wfDraft.getProcdefId());
        basic.setId(wfDraft.getId());
        this.saveTsmInductionBasic(basic, TsmRecruit.PROC_FLAG_DRAFT);
        return ret;
    }

    /**
     *
     * @param request
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void saveEnhanceBasic(TsmInductionBasicEnhanceRequest request) {
        TsmInductionBasic basic = Optional.ofNullable(request.getId())
            .map(this::get)
            .orElseGet(() -> new TsmInductionBasic(IdGen.uuid()));
        request.tsmInductionBasicConvert(basic);
        basic.preUpdate();
        tsmInductionBasicDao.update(basic);

        TsmCard card = Optional.ofNullable(request.getSalaryCardId())
            .map(tsmCardService::get)
            .orElse(new TsmCard());
        if (StrUtil.isNotBlank(request.getCardId())) {
            request.tsmCardConvert(card, basic.getId());
            tsmCardService.save(card);
        }

        TsmInductionInfo info = Optional.ofNullable(request.getInfoId())
            .map(tsmInductionInfoService::get)
            .orElse(new TsmInductionInfo());
        request.tsmInductionInfoConvert(info, basic.getId(), card.getId());
        tsmInductionInfoService.save(info);

        TsmPost post = Optional.ofNullable(request.getPostId())
            .map(tsmPostService::get)
            .orElse(new TsmPost());
        request.tsmPostConvert(post,basic.getId());
        tsmPostService.save(post);
    }

    /**
     * 补全信息
     * @param request 请求
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void saveInfoCompletion(TsmInductionInfoCompletionRequest request) {
        TsmPost post = Optional.ofNullable(request.getPostId())
            .map(tsmPostService::get)
            .orElse(new TsmPost());
        TsmInductionInfo info = Optional.ofNullable(request.getInfoId())
            .map(tsmInductionInfoService::get)
            .orElse(new TsmInductionInfo());
        TsmContractInformation contract = Optional.ofNullable(request.getContractInfoId())
            .map(tsmContractInformationService::get)
            .orElse(new TsmContractInformation());

        request.tsmPostConvert(post,request.getId());
        request.tsmInductionInfoConvert(info,request.getId());
        request.tsmContractInfoConvert(contract, request.getId());

        tsmPostService.save(post);
        tsmInductionInfoService.save(info);
        tsmContractInformationService.save(contract);


        OperationEnum operation = StrUtil.isBlank(request.getContractInfoId()) ? OperationEnum.CREATE : OperationEnum.EDIT;
        ContractDemandDto contractDemandDto = new ContractDemandDto(
            post,
            contract,
            departmentService.get(request.getDepartment().getId()),
            info,
            simpleGet(request.getId())
            );
        ////todo 合约修改
        inductionBatchContractService.saveOrUpdate(contractDemandDto, operation);

    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public ActResult save(String procDefId, TsmInductionBasic entity, MultipartFile[] attachments) {
        //id为空 insertFlag 为true
        boolean insertFlag = StrUtil.isBlank(entity.getId());
        if (insertFlag) {
            entity.preInsert();
        } else {
            entity.preUpdate();
        }

        ActResult actResult = wfUtils.create(procDefId, entity, attachments, this);
        if(actResult.getCode() == ActResult.EnumCodes.AutoSubmit || actResult.getCode() == ActResult.EnumCodes.Done) {
            if (insertFlag) {
                this.doInsert(entity);
            } else {
                this.doUpdate(entity);
            }
        }
        return actResult;
    }

    public TsmInductionBasic buildHomeTsmInductionBasic(AppHomePageDto entity) {
        //这里的entity set数据是为了 存入草稿时候的json数据 能够pass一些规则  度过更新逻辑
        TsmInductionBasic basic = Optional.ofNullable(entity.getId())
            .map(this::get)
            .orElse(new TsmInductionBasic());
        basic.setName(entity.getName());
        basic.setGender(new DictItem(entity.getGender().getId()));
        basic.setEthnicity(entity.getEthnicity());
        basic.setIdNumber(entity.getIdNumber());
        basic.setBirthDate(IdcardUtil.getBirth(entity.getIdNumber()));
        basic.setAge(IdcardUtil.getAgeByIdCard(entity.getIdNumber()));
        //basic.setIdCardExpiryDate("");
        basic.setPhoneNumber(entity.getPhoneNumber());
        basic.setResidenceAddress(entity.getResidenceAddress());
        basic.setNativePlace(entity.getNativePlace());
        //basic.setHealthStatus(new DictItem(entity.));
        basic.setEmail(entity.getEmail());
        basic.setName(entity.getName());
        basic.setId(entity.getId());

        TsmInductionInfo tsmInductionInfo = Optional
            .ofNullable(entity.getInfoId())
            .map(tsmInductionInfoService::get)
            .orElse(new TsmInductionInfo());
        tsmInductionInfo.setInductionId(StrUtil.isNotBlank(basic.getId()) ? basic.getId() : null);
        tsmInductionInfo.setId(entity.getInfoId());
        tsmInductionInfo.setMaritalStatus(new DictItem(entity.getMaritalStatus().getId()));
        tsmInductionInfo.setChildbirthStatus(new DictItem(entity.getChildbirthStatus().getId()));
        tsmInductionInfo.setPoliticalStatus(new DictItem(entity.getPoliticalStatus().getId()));
        basic.setTsmInductionInfoList(Lists.newArrayList(tsmInductionInfo));

        TsmPost tsmPost = Optional.ofNullable(entity.getPostId())
            .map(tsmPostService::get)
            .orElse(new TsmPost());
        tsmPost.setInductionId(StrUtil.isNotBlank(basic.getId()) ? basic.getId() : null);
        tsmPost.setId(entity.getPostId());
        tsmPost.setJobPosition(entity.getJobPosition());
        tsmPost.setCompanyName(entity.getCompanyName());
        tsmPost.setExpectedSalary(Integer.valueOf(entity.getExpectedSalary()));
        tsmPost.setInterviewTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        basic.setTsmPostList(Lists.newArrayList(tsmPost));

        return basic;
    }

//    public TsmInductionBasicEnhanceResponse getBasicInfoById(String id) {
//        TsmInductionBasic basic = super.get(id);
//
//        List<TsmInductionInfo> tsmInfos = tsmInductionInfoService.listAll(basic.queryInductionIdParam(), "");
//        List<TsmCard> tsmCards = tsmCardService.listAll(basic.queryInductionIdParam(), "");
//        List<TsmPost> tsmPosts = tsmPostService.listAll(basic.queryInductionIdParam(), "");
//
//        return new TsmInductionBasicEnhanceResponse()
//            .tsmInductionBasicConvert(basic)
//            .buildTsmPost(tsmPosts)
//            .buildTsmCard(tsmInfos,tsmCards)
//            .buildTsmInfo(tsmInfos);
//    }


    public TsmInductionBasic simpleGet(String id) {
        return super.get(id);
    }

    public int simpleUpdate(TsmInductionBasic basic) {
        basic.preUpdate();
        return tsmInductionBasicDao.update(basic);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public TsmInductionBasic simpleSave(TsmInductionBasic entity) {
        TsmInductionBasic oldTsmRecruit;
        if (StrUtil.isNotBlank(entity.getId()) && ObjUtil.isNotNull(oldTsmRecruit = get(entity.getId()))) {
            entity.setUpdateDate(oldTsmRecruit.getUpdateDate());
            entity.preUpdate();
            super.doUpdate(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_UPDATED, entity));
        } else {
            entity.preInsert();
            super.doInsert(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, entity));
        }
        return entity;
    }

    /**
     * 查询分页数据
     * @param parameters 查询参数
     * @param offset
     * @param limit
     * @param orderby
     * @return
     */
    public Page<TsmInductionBasicEnhanceResponse> listEnhancePage(List<Parameter> parameters, int offset, int limit, String orderby) {
        PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);

        int total = dao.count(pageRequest);
        List<TsmInductionBasicEnhanceResponse> responses = null;
        if(total > 0) {
            List<TsmInductionBasic> list = dao.listPage(pageRequest);
            responses = this.buildTsmInductionBasicEnhanceResponses(list);
        }

        return new Page(total, responses);
    }

    public Page<TsmInductionBasicEnhanceResponse> inductionHomeSearch(InductionHomeSearchRequest searchParams) {
        //查询出符合条件count
        int total = dao.homeSearchCount(searchParams);
        List<TsmInductionBasicEnhanceResponse> responses = null;
        if(total > 0) {
            //查询出符合条件basic
            List<TsmInductionBasic> list = dao.homeSearchListPage(searchParams);
            responses = this.buildTsmInductionBasicEnhanceResponses(list);
        }
        return new Page(total, responses);
    }

    public List<TsmInductionBasicEnhanceResponse> buildTsmInductionBasicEnhanceResponses(List<TsmInductionBasic> list) {
        List<TsmInductionBasicEnhanceResponse> responses;
        List<String> inductionIds = list.stream().map(BaseEntity::getId)
            .collect(Collectors.toList());

        Map<String, List<TsmInductionInfo>> tsmInductionInfoMap = tsmInductionInfoService
            .listAll(TsmInductionBasic.queryInductionIdParam(inductionIds), "")
            .stream()
            .collect(Collectors.groupingBy(TsmInductionInfo::getInductionId));
        Map<String, List<TsmPost>> tsmPostMap = tsmPostService
            .listAll(TsmInductionBasic.queryInductionIdParam(inductionIds), "")
            .stream()
            .collect(Collectors.groupingBy(TsmPost::getInductionId));

        responses = list.stream()
            .peek(basic -> basic.setTsmInductionInfoList(tsmInductionInfoMap.get(basic.getId())))
            .peek(basic -> basic.setTsmPostList(tsmPostMap.get(basic.getId())))
            .map(basic -> new TsmInductionBasicEnhanceResponse()
                .tsmInductionBasicConvert(basic,tsmRecruitService)
                .buildDepartment(departmentService)
            )
            .collect(Collectors.toList());
        return responses;
    }

    /**
     * 审批同意
     * @param taskId
     * @param entity
     * @param attachments
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public ActResult agree(String taskId, TsmInductionBasic entity, MultipartFile[] attachments) {
        ActResult actResult = wfUtils.agree(taskId, entity, attachments, this);
        if(entity.isApproved()) {
            this.simpleSave(entity);
        }
        return actResult;
    }
}
