package com.geeke.induction.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.service.CrudService;
import com.geeke.induction.entity.*;
import com.geeke.induction.request.EmployeeContactInformationRequest;
import com.geeke.induction.request.EmployeeInfoRequest;
import com.geeke.induction.response.EmployeeContactInformationResponse;
import com.geeke.induction.response.EmployeeInfoResponse;
import com.geeke.induction.service.*;
import com.geeke.org.service.DepartmentService;
import com.geeke.recruit.service.TsmRecruitService;
import com.geeke.sys.controller.BaseController;
import com.geeke.sys.entity.SysFile;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * inductionController
 *
 * @author
 */
@RestController
@RequestMapping(value = "/induction/employeeInfo/")
@RequiredArgsConstructor
public class EmployeeInfoControl extends BaseController {
    private final TsmInductionBasicService tsmInductionBasicService;
    private final TsmRecruitService tsmRecruitService;
    private final DepartmentService departmentService;
    private final EmployeeInfoService employeeInfoService;
    private final TsmPostService tsmPostService;
    private final TsmWorkExperienceService tsmWorkExperienceService;
    private final TsmProjectExperienceService tsmProjectExperienceService;
    private final TsmTrainingExperienceService tsmTrainingExperienceService;
    private final TsmQualificationService tsmQualificationService;
    private final TsmEducationalRecordsService tsmEducationalRecordsService;
    private final TsmPersonalSkillsService tsmPersonalSkillsService;

    private final TsmFamilyMemberInfoService tsmFamilyMemberInfoService;
    private final TsmContractInformationService tsmContractInformationService;
    private final TsmCardService tsmCardService;
    private final TsmAssetInformationService tsmAssetInformationService;
    private final SysFileService sysFileService;


    @GetMapping("/{employeeNo}")
    public ResponseEntity<JSONObject> getInfo(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info = employeeInfoService.findInductionInfos(employeeNo);
        if (ObjUtil.isNull(info)) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        TsmInductionBasic basic = tsmInductionBasicService.get(info.getInductionId());
        EmployeeInfoResponse response = new EmployeeInfoResponse();
        response.tsmInductionBasicConvert(basic, tsmRecruitService).buildDepartment(departmentService);
        return ResponseEntity.ok(ResultUtil.successJson(response));
    }

    @PostMapping("/basic/save")
    public ResponseEntity<JSONObject> saveInfo(@RequestBody EmployeeInfoRequest request) {
        return ResponseEntity.ok(ResultUtil.successJson(employeeInfoService.saveBasicInfo(request)));
    }

    @GetMapping("/post/{employeeNo}")
    public ResponseEntity<JSONObject> getPostInfo(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        List<TsmPost> byInductionId = tsmPostService.getByInductionId(info.getInductionId());
        return ResponseEntity.ok(ResultUtil.successJson(byInductionId));
    }

    @PostMapping("/post/save")
    public ResponseEntity<JSONObject> saveInfo(@RequestBody TsmPost tsmPost) {
        return ResponseEntity.ok(ResultUtil.successJson(tsmPostService.save(tsmPost)));
    }

    /**
     * 保存工作经验
     *
     * @return
     */
    @PostMapping(value = "/workExperience/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveInductionWork(
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<TsmWorkExperience> req) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmWorkExperienceService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmWorkExperienceService::save)
            .collect(Collectors.toList())));
    }

    @GetMapping("/workExperience/{employeeNo}")
    public ResponseEntity<JSONObject> getWorkExperience(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(tsmWorkExperienceService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 保存项目经验
     *
     * @return
     */
    @PostMapping(value = "/projectExperience/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveProjectExperience(
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<TsmProjectExperience> req) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmProjectExperienceService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmProjectExperienceService::save)
            .collect(Collectors.toList())));
    }

    @GetMapping("/projectExperience/{employeeNo}")
    public ResponseEntity<JSONObject> getProjectExperience(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(tsmProjectExperienceService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 保存培训经历
     *
     * @return
     */
    @PostMapping(value = "/trainingExperience/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveTrainingExperience(
        @RequestBody List<TsmTrainingExperience> req,
        @PathVariable("employeeNo") String employeeNo) {

        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmTrainingExperienceService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmTrainingExperienceService::save)
            .collect(Collectors.toList())));
    }

    @GetMapping("/trainingExperience/{employeeNo}")
    public ResponseEntity<JSONObject> getTrainingExperience(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(tsmTrainingExperienceService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 保存资格证书
     *
     * @return
     */
    @PostMapping(value = "/qualification/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveQualification(
        @RequestBody List<TsmQualification> req,
        @PathVariable("employeeNo") String employeeNo) {

        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmQualificationService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmQualificationService::save)
            .collect(Collectors.toList())));
    }

    @GetMapping("/qualification/{employeeNo}")
    public ResponseEntity<JSONObject> getQualification(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(tsmQualificationService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 保存教育经历
     *
     * @return
     */
    @PostMapping(value = "/educationalRecords/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveEducationalRecords(
        @RequestBody List<TsmEducationalRecords> req,
        @PathVariable("employeeNo") String employeeNo) {

        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmEducationalRecordsService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmEducationalRecordsService::save)
            .collect(Collectors.toList())));
    }

    @GetMapping("/educationalRecords/{employeeNo}")
    public ResponseEntity<JSONObject> getEducationalRecords(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(tsmEducationalRecordsService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 保存个人技能
     *
     * @return
     */
    @PostMapping(value = "/personalSkills/save/{employeeNo}")
    public ResponseEntity<JSONObject> savePersonalSkills(
        @RequestBody List<TsmPersonalSkills> req,
        @PathVariable("employeeNo") String employeeNo) {

        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmPersonalSkillsService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmPersonalSkillsService::save)
            .collect(Collectors.toList())));
    }

    @GetMapping("/personalSkills/{employeeNo}")
    public ResponseEntity<JSONObject> getPersonalSkills(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(tsmPersonalSkillsService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 保存联系人信息
     *
     * @return
     */
    @PostMapping(value = "/contacts/save")
    public ResponseEntity<JSONObject> saveContacts(@RequestBody EmployeeContactInformationRequest request) {
        return ResponseEntity.ok(ResultUtil.successJson(employeeInfoService.saveContacts(request)));
    }

    @GetMapping("/contacts/{employeeNo}")
    public ResponseEntity<JSONObject> getContacts(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        EmployeeContactInformationResponse employeeContactInformationResponse = employeeInfoService.getContacts(info.getInductionId());
        return ResponseEntity.ok(ResultUtil.successJson(employeeContactInformationResponse));
    }


    /**
     * 其他多种信息
     *
     * @param employeeNo 员工号
     * @return
     */
    @GetMapping("/otherInfo/{serviceName}/{employeeNo}")
    public ResponseEntity<JSONObject> getOtherInfoInfo(
        @PathVariable("serviceName") String serviceName,
        @PathVariable("employeeNo") String employeeNo
    ) {

        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        CrudService crudService = employeeInfoService.getProcessService(serviceName);
        return ResponseEntity.ok(ResultUtil.successJson(crudService
            .listAll(new TsmInductionBasic(info.getInductionId()).queryInductionIdParam(), "")));
    }

    /**
     * 其他多种信息
     *
     * @return
     */
    @PostMapping(value = "/otherInfo/save/{serviceName}/{employeeNo}")
    public ResponseEntity<JSONObject> savePersonalSkills(
        @PathVariable("serviceName") String serviceName,
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<JSONObject> reqList
    ) {

//        TsmInductionInfo info;
//        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
//            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
//        }
//        CrudService crudService = employeeInfoService.getProcessService(serviceName);
//
//        return ResponseEntity.ok(ResultUtil.successJson(reqList.stream()
//            .peek(data -> data.setInductionId(info.getInductionId()))
//            .peek(data -> Optional.ofNullable(data.getId())
//                .map(crudService::get)
//                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
//            .map(x -> crudService.save(x))
//            .collect(Collectors.toList())));

        return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
    }

    /**
     * 家庭情况
     *
     * @return
     */
    @PostMapping(value = "/familyMemberInfo/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveFamilyMemberInfo(
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<TsmFamilyMemberInfo> reqList
    ) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(reqList.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmFamilyMemberInfoService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmFamilyMemberInfoService::save)
            .collect(Collectors.toList())));
    }

    /**
     * 合同信息
     *
     * @return
     */
    @PostMapping(value = "/contractInformation/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveContractInformation(
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<TsmContractInformation> reqList
    ) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(reqList.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmContractInformationService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmContractInformationService::save)
            .collect(Collectors.toList())));
    }

    /**
     * 卡片
     *
     * @return
     */
    @PostMapping(value = "/card/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveCard(
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<TsmCard> reqList
    ) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(reqList.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmCardService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmCardService::save)
            .collect(Collectors.toList())));
    }

    /**
     * 资产
     *
     * @return
     */
    @PostMapping(value = "/assetInformation/save/{employeeNo}")
    public ResponseEntity<JSONObject> saveAssetInformation(
        @PathVariable("employeeNo") String employeeNo,
        @RequestBody List<TsmAssetInformation> reqList
    ) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }

        return ResponseEntity.ok(ResultUtil.successJson(reqList.stream()
            .peek(data -> data.setInductionId(info.getInductionId()))
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmAssetInformationService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmAssetInformationService::save)
            .collect(Collectors.toList())));
    }


    @PostMapping(path = "/headSculpture/upload/{employeeNo}")
    public ResponseEntity<JSONObject> headSculptureUpload(
        @PathVariable("employeeNo") String employeeNo,
        @RequestParam(value = "file") MultipartFile files,
        @RequestParam(value = "name") String fileName)
        throws IOException {
        if (StrUtil.isBlank(fileName) || ArrayUtil.isEmpty(files)) {
            return ResponseEntity.ok(ResultUtil.successJson());
        }
        List<String> ids = sysFileService.getByObjectId(employeeNo).stream().map(SysFile::getId).collect(Collectors.toList());
        sysFileService.delete(ids.toArray(new String[]{}));

        List<MultipartFile> filesGroup = new ArrayList<>();
        filesGroup.add(files);
        sysFileService
            .changeAndSaveSysFileList(filesGroup.toArray(new MultipartFile[filesGroup.size()]), employeeNo);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @GetMapping("/headSculpture/down/{employeeNo}")
    public ResponseEntity<JSONObject> headSculptureDown(@PathVariable("employeeNo") String employeeNo) {
        List<SysFile> headSculptures = employeeInfoService.getHeadSculpture(employeeNo);
        return ResponseEntity.ok(ResultUtil.successJson(headSculptures));
    }



}
