package com.geeke.induction.service;

import cn.hutool.core.collection.CollUtil;
import com.geeke.admin.entity.User;
import com.geeke.admin.service.UserService;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.induction.enums.YesOrNoEnum;
import com.geeke.induction.word.Resume;
import com.geeke.org.entity.Department;
import com.geeke.org.service.CompanyService;
import com.geeke.org.service.DepartmentService;
import com.geeke.sys.service.DictItemService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * inductionService
 * @author
 * @version
 */

@Service("InductionPcOtherService")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InductionPcOtherService {
    private final TsmInductionBasicService tsmInductionBasicService;
    private final TsmEducationalRecordsService tsmEducationalRecordsService;
    private final TsmWorkExperienceService tsmWorkExperienceService;
    private final TsmPersonalSkillsService tsmPersonalSkillsService;
    private final TsmQualificationService tsmQualificationService;
    private final TsmInductionInfoService tsmInductionInfoService;
    private final DepartmentService departmentService;
    private final CompanyService companyService;
    private final TsmPostService tsmPostService;
    private final DictItemService dictItemService;
    private final UserService userService;
    private final Resume resume;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public User archive(String inductionId) {
        TsmInductionBasic basic = tsmInductionBasicService.get(inductionId);
        TsmInductionInfo info = basic.getTsmInductionInfoList().get(0);
        TsmPost post = basic.getTsmPostList().get(0);

        User user = new User();
        user.setIsLocked("0");
        user.setName(basic.getName());
        user.setPhone(basic.getPhoneNumber());
        user.setEmail(basic.getEmail());
        user.setEmpolyno(info.getEmployeeNumber());
        user.setLoginPasswordUpdate(true);

        Department department = departmentService.get(post.getDepartment().getId());
        user.setDepartment(department);
        user.setCompany(department.getCompany());
        user.setPost(post.getTechnicalPosition());
        user.setLoginName(basic.getPhoneNumber());
        user.setLoginPassword(basic.getName());

        user.setRanks(post.getTechnicalPosition());
        user.setUserRoleList(Lists.newArrayList());

        //修改归档状态
        info.setRecordStatus("1");
        tsmInductionInfoService.save(info);
        return  userService.save(user);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public AtomicReference<TsmPost> saveInterviewResult(String result,TsmInductionBasic entity) {
        AtomicReference<TsmPost> tsmPostResult = new AtomicReference<>();
        Optional.ofNullable(entity.getTsmPostList())
            .map(CollUtil::getFirst)
            .map(TsmPost::getId)
            .map(tsmPostService::get)
            .ifPresent(tsmPost ->{
                tsmPost.setInterviewResult(result);
                tsmPostResult.set(tsmPostService.save(tsmPost));});
        return tsmPostResult;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public AtomicReference<TsmPost> getTsmPostAtomicReference(String inductionId) {
        AtomicReference<TsmPost> tsmPostResult = new AtomicReference<>();
        Optional.ofNullable(inductionId)
            .map(tsmPostService::getByInductionId)
            .map(CollUtil::getFirst)
            .ifPresent(tsmPost -> {
                tsmPost.setAbandonReporting(YesOrNoEnum.YES.getCode());
                tsmPostResult.set(tsmPostService.save(tsmPost));
            });
        return tsmPostResult;
    }


    public Boolean checkIdCardLast(String inductionId, String idCardLast) {
        return Optional.ofNullable(tsmInductionBasicService.simpleGet(inductionId))
            .map(TsmInductionBasic::getIdNumber)
            .map(idNum -> idNum.substring(idNum.length() - 4))
            .map(idNumLast -> idNumLast.equals(idCardLast))
            .orElse(false);
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void saveDataValidation(String result, String inductionId) {
        Optional.ofNullable(inductionId)
            .map(tsmInductionBasicService::simpleGet)
            .ifPresent(basic ->{
                basic.setDataValidation(result);
                tsmInductionBasicService.simpleSave(basic);});
    }

    public byte[] resumeTranslation(TsmInductionBasic basic, String resumeType) {
        if ("1".equals(resumeType)) {
            return resume.buildResume1(basic);
        }

        if ("2".equals(resumeType)) {
            return resume.buildResume2(basic);
        }

        return new byte[]{};
    }
}
