package com.geeke.induction.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.admin.entity.User;
import com.geeke.feign.FileService;
import com.geeke.induction.dto.AppHomePageDto;
import com.geeke.induction.entity.TsmEducationalRecords;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPersonalSkills;
import com.geeke.induction.entity.TsmQualification;
import com.geeke.induction.entity.TsmWorkExperience;
import com.geeke.induction.service.EmployeeInfoService;
import com.geeke.induction.service.InductionAppService;
import com.geeke.induction.service.TsmEducationalRecordsService;
import com.geeke.induction.service.TsmInductionBasicService;
import com.geeke.induction.service.TsmPersonalSkillsService;
import com.geeke.induction.service.TsmQualificationService;
import com.geeke.induction.service.TsmWorkExperienceService;
import com.geeke.induction.utils.QRCodeGenerator;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.sys.controller.BaseController;
import com.geeke.utils.JwtUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.constants.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * inductionController
 *
 * @author
 */
@RestController
@RequestMapping(value = "/induction/app/")
@RequiredArgsConstructor
public class InductionAppControl extends BaseController {
    private final TsmInductionBasicService tsmInductionBasicService;
    private final TsmEducationalRecordsService tsmEducationalRecordsService;
    private final TsmWorkExperienceService tsmWorkExperienceService;
    private final TsmPersonalSkillsService tsmPersonalSkillsService;
    private final TsmQualificationService tsmQualificationService;
    private final JwtUtils jwtUtils;
    private final InductionAppService inductionAppService;
    private final FileService fileService;
    private final EmployeeInfoService employeeInfoService;

    /**
     * 资料提交
     */
    @PostMapping(value = "/dataSubmission/{inductionId}")
    public ResponseEntity<JSONObject> dataSubmission(@PathVariable("inductionId") String inductionId) {
        inductionAppService.dataSubmission(inductionId);
        return ResponseEntity.ok(ResultUtil.successJson());
    }

    @PostMapping(path = "/upload/many/{inductionId}")
    public ResponseEntity<JSONObject> uploadMany(
        @PathVariable("inductionId") String inductionId,
        @RequestParam(value = "files", required = false) MultipartFile[] files,
        @RequestParam(value = "deletes", required = false) String[] deletes,
        @RequestParam(value = "names") String fileName) throws IOException {

        if (StrUtil.isBlank(fileName) || ArrayUtil.isEmpty(files)) {
            return ResponseEntity.ok(ResultUtil.successJson());
        }
        inductionAppService.uploadMany(inductionId, files, fileName, deletes);
        return ResponseEntity.ok(ResultUtil.successJson());
    }


    @GetMapping("/deleteFIle/{fileBase64Id}")
    public ResponseEntity<JSONObject> delete(@PathVariable("fileBase64Id") String fileBase64Id) {
        return ResponseEntity.ok(ResultUtil.successJson(fileService.fdeleteById(fileBase64Id)));
    }


    @GetMapping("/downByEmployeeNo/{employeeNo}")
    public ResponseEntity<JSONObject> downByEmployeeNo(@PathVariable("employeeNo") String employeeNo) {
        TsmInductionInfo info;
        if (ObjUtil.isNull(info = employeeInfoService.findInductionInfos(employeeNo))) {
            return ResponseEntity.ok(ResultUtil.errorJson(ErrorEnum.E_80001));
        }
        return ResponseEntity.ok(ResultUtil.successJson(inductionAppService.downMany(info.getInductionId())));
    }

    @GetMapping("/downByInductionId/{inductionId}")
    public ResponseEntity<JSONObject> downByInductionId(@PathVariable("inductionId") String inductionId) {
        return ResponseEntity.ok(ResultUtil.successJson(inductionAppService.downMany(inductionId)));}
    /**
     * 保存首页数据
     *
     * @return
     */
    @PostMapping(value = "/saveInductionBasic")
    public ResponseEntity<JSONObject> saveHome(@RequestBody AppHomePageDto entity) {
        TsmInductionBasic basic = tsmInductionBasicService.buildHomeTsmInductionBasic(entity);
        return ResponseEntity.ok(ResultUtil.successJson(tsmInductionBasicService
            .saveTsmInductionBasic(basic, TsmRecruit.PROC_FLAG_DRAFT)));
    }


    /**
     * 保存教育经历数据
     *
     * @return
     */
    @PostMapping(value = "/saveInductionEducational")
    public ResponseEntity<JSONObject> saveInductionEducational(@RequestBody List<TsmEducationalRecords> req) {
        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmEducationalRecordsService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmEducationalRecordsService::save)
            .collect(Collectors.toList())));
    }

    /**
     * 保存工作经验
     *
     * @return
     */
    @PostMapping(value = "/saveInductionWork")
    public ResponseEntity<JSONObject> saveInductionWork(@RequestBody List<TsmWorkExperience> req) {
        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmWorkExperienceService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmWorkExperienceService::save)
            .collect(Collectors.toList())));
    }

    /**
     * 保存个人技能
     *
     * @return
     */
    @PostMapping(value = "/saveInductionPersonalSkills")
    public ResponseEntity<JSONObject> saveInductionPersonalSkills(@RequestBody List<TsmPersonalSkills> req) {
        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmPersonalSkillsService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmPersonalSkillsService::save)
            .collect(Collectors.toList())));
    }

    /**
     * 保存证书
     *
     * @return
     */
    @PostMapping(value = "/saveInductionQualification")
    public ResponseEntity<JSONObject> saveInductionQualification(@RequestBody List<TsmQualification> req) {
        return ResponseEntity.ok(ResultUtil.successJson(req.stream()
            .peek(data -> Optional.ofNullable(data.getId())
                .map(tsmQualificationService::get)
                .ifPresent(edu -> data.setUpdateDate(edu.getUpdateDate())))
            .map(tsmQualificationService::save)
            .collect(Collectors.toList())));
    }


    /**
     * 流程保存草稿
     *
     * @param strDraft       草稿对象
     * @param strEntity      业务对象
     * @param attachmentsReq 审批附件
     * @return
     */
    @PostMapping(value = "/home/saveDraft")
    public ResponseEntity<JSONObject> saveDraft(
        @RequestParam(value = "strDraft", required = false) String strDraft,
        @RequestParam(value = "strEntity", required = false) String strEntity,
        @RequestParam(value = "attachments", required = false) MultipartFile[] attachmentsReq
    ) throws java.io.IOException {
        AppHomePageDto entity = JSONObject.parseObject(strEntity, AppHomePageDto.class);
        return ResponseEntity.ok(ResultUtil.successJson(tsmInductionBasicService.saveDraft(strDraft, attachmentsReq, entity)));
    }


    /**
     * 第一次扫码 基本信息填写
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/first/qrCode")
    public ResponseEntity<String> getFirstQrCode(@RequestHeader("X-Token") String token) throws Exception {
        User user = SessionUtils.getUser();
        // 从本地文件系统读取图片
        //todo 临时解决发方案
        String imageStream = QRCodeGenerator.generateQRCode(user.getCompany().getAppUrl()
            + "/#/main"
            + "?X-Token=" + token
            + "&order=" + 1
            + "&companyName=" + URLEncoder.encode(user.getCompany().getFullName(), "UTF-8"), 300, 300, 0, "png");
        return ResponseEntity.ok(imageStream);
    }


    /**
     * 第二次扫码 入职填写
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/second/qrCode/{inductionId}")
    public ResponseEntity<String> getSecondQrCode(@PathVariable("inductionId") String inductionId,
                                                  @RequestHeader("X-Token") String token) throws Exception {
        User user = SessionUtils.getUser();
        JWT jwt = JWTUtil.parseToken(token);
        //7天时间过期
        LocalDateTime expiration = LocalDateTime.now().plusDays(7);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(jwt.getPayload("sub").toString());
        jsonObject.set("sessionId", UUID.randomUUID().toString());
        jsonObject.set("qrCodeTime", expiration.toInstant(ZoneOffset.UTC).toEpochMilli());

        // 从本地文件系统读取图片
        String sign = jwtUtils.createJWT(jsonObject.toString(), expiration.toInstant(ZoneOffset.UTC).toEpochMilli());
        //todo 临时解决发方案
        String imageStream = QRCodeGenerator.generateQRCode(user.getCompany().getAppUrl()
            + "/#/main"
            + "?X-Token=" + sign
            + "&order=" + 2
            + "&companyName=" + URLEncoder.encode(user.getCompany().getFullName(), "UTF-8")
            + "&inductionId=" + inductionId, 300, 300, 0, "png");
        return ResponseEntity.ok(imageStream);
    }


}
