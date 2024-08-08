package com.geeke.induction.service;

import cn.hutool.core.collection.CollUtil;
import com.geeke.common.service.CrudService;
import com.geeke.induction.entity.TsmEmergencyContact;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.request.EmployeeContactInformationRequest;
import com.geeke.induction.request.EmployeeInfoRequest;
import com.geeke.induction.response.EmployeeContactInformationResponse;
import com.geeke.induction.response.EmployeeInfoResponse;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.IdGen;
import com.geeke.utils.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("employeeInfoService")
@Transactional(readOnly = true)
public class EmployeeInfoService {
    private final TsmEmergencyContactService tsmEmergencyContactService;
    private final TsmInductionBasicService tsmInductionBasicService;
    private final TsmInductionInfoService tsmInductionInfoService;
    private final TsmRecruitService tsmRecruitService;
    private final SysFileService sysFileService;

    private final Map<String,CrudService> serviceMap;


    public EmployeeInfoService(
            TsmAssetInformationService tsmAssetInformationService,
            TsmCardService tsmCardService,
            TsmContractInformationService tsmContractInformationService,
            TsmEducationalRecordsService tsmEducationalRecordsService,
            TsmEmergencyContactService tsmEmergencyContactService,
            TsmEmployeeAttachmentsService tsmEmployeeAttachmentsService,
            TsmFamilyMemberInfoService tsmFamilyMemberInfoService,
            TsmInductionBasicService tsmInductionBasicService,
            TsmInductionInfoService tsmInductionInfoService,
            TsmPersonalSkillsService tsmPersonalSkillsService,
            TsmPostService tsmPostService,
            TsmProjectExperienceService tsmProjectExperienceService,
            TsmQualificationService tsmQualificationService,
            TsmTrainingExperienceService tsmTrainingExperienceService,
            TsmWorkExperienceService tsmWorkExperienceService,
            TsmRecruitService tsmRecruitService,
            SysFileService sysFileService
    ) {
        serviceMap = new HashMap();
       serviceMap.put("assetInformation", tsmAssetInformationService);
       serviceMap.put("card", tsmCardService);
       serviceMap.put("contractInformation", tsmContractInformationService);
       serviceMap.put("educationalRecords", tsmEducationalRecordsService);
       serviceMap.put("emergencyContact", tsmEmergencyContactService);
       serviceMap.put("employeeAttachments", tsmEmployeeAttachmentsService);
       serviceMap.put("familyMemberInfo", tsmFamilyMemberInfoService);
       serviceMap.put("inductionBasic", tsmInductionBasicService);
       serviceMap.put("inductionInfo", tsmInductionInfoService);
       serviceMap.put("personalSkills", tsmPersonalSkillsService);
       serviceMap.put("post", tsmPostService);
       serviceMap.put("projectExperience", tsmProjectExperienceService);
       serviceMap.put("qualification", tsmQualificationService);
       serviceMap.put("trainingExperience", tsmTrainingExperienceService);
       serviceMap.put("workExperience", tsmWorkExperienceService);
       serviceMap.put("recruit", tsmRecruitService);

        this.tsmEmergencyContactService = tsmEmergencyContactService;
        this.tsmInductionBasicService = tsmInductionBasicService;
        this.tsmInductionInfoService = tsmInductionInfoService;
        this.tsmRecruitService = tsmRecruitService;
        this.sysFileService = sysFileService;

    }


    public EmployeeContactInformationResponse getContacts(String inductionId) {
        TsmInductionBasic newBasicData = tsmInductionBasicService.simpleGet(inductionId);
        newBasicData.setTsmInductionInfoList(tsmInductionInfoService.getByTsmInductionInfo(inductionId));
        newBasicData.setTsmEmergencyContactList(tsmEmergencyContactService.getByTsmInductionInfo(inductionId));

        return new EmployeeContactInformationResponse().tsmInductionBasicConvert(newBasicData);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public EmployeeContactInformationResponse saveContacts(@RequestBody EmployeeContactInformationRequest request) {
        TsmInductionBasic basic = Optional.ofNullable(request.getId())
            .map(tsmInductionBasicService::simpleGet)
            .orElseGet(() -> new TsmInductionBasic(IdGen.uuid()));
        request.tsmInductionBasicConvert(basic);
        basic.preUpdate();
        tsmInductionBasicService.simpleUpdate(basic);

        TsmInductionInfo info = Optional.ofNullable(request.getInfoId())
            .map(tsmInductionInfoService::get)
            .orElse(new TsmInductionInfo());
        request.tsmInductionInfoConvert(info, basic.getId(), null);
        tsmInductionInfoService.save(info);

        TsmEmergencyContact tsmEmergencyContact = Optional.ofNullable(request.getEmergencyContactId())
            .map(tsmEmergencyContactService::get)
            .orElse(new TsmEmergencyContact());
        request.tsmEmergencyContactConvert(tsmEmergencyContact,basic.getId());
        tsmEmergencyContactService.save(tsmEmergencyContact);

        TsmInductionBasic newBasicData = tsmInductionBasicService.simpleGet(request.getId());
        newBasicData.setTsmInductionInfoList(tsmInductionInfoService.getByTsmInductionInfo(request.getId()));
        return new EmployeeContactInformationResponse().tsmInductionBasicConvert(newBasicData);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public EmployeeInfoResponse saveBasicInfo(@RequestBody EmployeeInfoRequest request) {
        TsmInductionBasic basic = Optional.ofNullable(request.getId())
            .map(tsmInductionBasicService::simpleGet)
            .orElseGet(() -> new TsmInductionBasic(IdGen.uuid()));
        request.tsmInductionBasicConvert(basic);
        basic.preUpdate();
        tsmInductionBasicService.simpleUpdate(basic);

        TsmInductionInfo info = Optional.ofNullable(request.getInfoId())
            .map(tsmInductionInfoService::get)
            .orElse(new TsmInductionInfo());
        request.tsmInductionInfoConvert(info, basic.getId(), null);
        tsmInductionInfoService.save(info);

        TsmInductionBasic newBasicData = tsmInductionBasicService.simpleGet(request.getId());
        newBasicData.setTsmInductionInfoList(tsmInductionInfoService.getByTsmInductionInfo(request.getId()));
        EmployeeInfoResponse response = new EmployeeInfoResponse();
        return response.tsmInductionBasicConvert(newBasicData,tsmRecruitService);
    }

    public TsmInductionInfo findInductionInfos(String employeeNo) {
        List<TsmInductionInfo> infos = tsmInductionInfoService
            .listAll(QueryUtils.queryAndList("employee_number", employeeNo), null);
        if (CollUtil.isEmpty(infos) || infos.size() > 1) {
            return null;
        }
        return infos.get(0);
    }

    public CrudService getProcessService(String serviceName) {
        return serviceMap.get(serviceName);
    }

    public List<SysFile> getHeadSculpture(String employeeNo) {
        return sysFileService.getByObjectId(employeeNo);
    }

}
