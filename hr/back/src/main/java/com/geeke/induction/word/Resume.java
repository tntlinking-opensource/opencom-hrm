package com.geeke.induction.word;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONObject;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.geeke.feign.FileService;
import com.geeke.induction.entity.TsmEducationalRecords;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.service.EmployeeInfoService;
import com.geeke.sys.entity.SysFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yi.luo
 * @date 2023-06-05 10:21
 */

@Service
@RequiredArgsConstructor
public class Resume {
    private final EmployeeInfoService employeeInfoService;
    private final FileService fileService;

    public byte[] buildResume1(TsmInductionBasic basic) {

        ResumeData4Minimalist fullData = (ResumeData4Minimalist) getBasicResumeData(basic, new ResumeData4Minimalist());

        MiniTableRenderData table = WordUtil.createContactTable();
        MiniTableRenderData table2 = WordUtil.createExperienceTable2(basic.getTsmWorkExperienceList());
        MiniTableRenderData table3 = WordUtil.createLanguageTable(basic.getTsmPersonalSkillsList());
        MiniTableRenderData table4 = WordUtil.createProjectTable(basic.getTsmProjectExperienceList());

        fullData.setFamilyTable(table);
        fullData.setContactTable(table);
        fullData.setExperienceTable(table2);
        fullData.setLanguageTable(table3);
        fullData.setProjectTable(table4);

        try
        {
            return WordUtil.testResumeExample2("resume.docx", fullData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public byte[] buildResume2(TsmInductionBasic basic) {
        ResumeData4Elaborate resumeUrl2 = (ResumeData4Elaborate) getBasicResumeData(basic,new ResumeData4Elaborate());
        //渲染数据
        ResumeData4Elaborate fullData = WordUtil.init(resumeUrl2,basic);
        //输出文件名
        try
        {
            return WordUtil.testResumeExample("resume2.docx", fullData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new byte[] {};
    }

    public BasicResumeData getBasicResumeData(TsmInductionBasic basic ,BasicResumeData basicResumeData) {
        BasicResumeData fullData = basicResumeData;

        List<SysFile> headSculpture = employeeInfoService.getHeadSculpture(basic.getTsmPost().getEmployeeNumber());
        JSONObject viewBase64;
        if (headSculpture.size() > 0 && ObjUtil.isNotNull(viewBase64 = fileService.getViewBase64(headSculpture.get(0).getId()))) {
            String[] parts = viewBase64.get("data").toString().split(",");
            String imageString = parts[1];
            byte[] data = Base64.getDecoder().decode(imageString);

            fullData.setPortrait(new PictureRenderData(110, 110,
                headSculpture.get(0).getFileType(),
                data));
        }


        fullData.setStaff_idCard(basic.getIdNumber());
        fullData.setStaff_user_name(basic.getName());
        fullData.setStaff_post_code(basic.getEmail());
        fullData.setStaff_user_gender(basic.getGender().getName());
        fullData.setStaff_user_mobile(basic.getPhoneNumber());
        fullData.setStaff_native(basic.getNativePlace());
        fullData.setStaff_nativeplace(basic.getTsmInfo().getHouseholdRegisterLocation());
        fullData.setStaff_user_email(basic.getEmail());
        fullData.setStaff_location(basic.getResidenceAddress());
        fullData.setStaff_startwork(basic.getTsmInfo().getWorkStartDate());


        if (CollUtil.isNotEmpty(basic.getTsmEducationalRecordsList()) || basic.getTsmEducationalRecordsList().size() > 0) {

            TsmEducationalRecords tsmEducationalRecords = basic.getTsmEducationalRecordsList().stream()
                .max(Comparator.comparing(TsmEducationalRecords::getGraduationTime))
                .orElse(new TsmEducationalRecords());
            fullData.setMethod(tsmEducationalRecords.getEducationMethod().getName());
//            fullData.setDegree();
            fullData.setBackground(tsmEducationalRecords.getHighestEducation().getName());
            fullData.setCollege(tsmEducationalRecords.getGraduatedSchool());
            fullData.setCollege_start(tsmEducationalRecords.getAdmissionTime());
            fullData.setCollege_end(tsmEducationalRecords.getGraduationTime());
            fullData.setGratitude_code(tsmEducationalRecords.getDiplomaNumber());
            fullData.setMajor(tsmEducationalRecords.getMajor());
        }

        fullData.setStaff_nation_name(basic.getEthnicity().getName());
        fullData.setStaff_marriage_status(basic.getTsmInfo().getMaritalStatus().getName());
        fullData.setWechat(basic.getTsmInfo().getWechatId());
        fullData.setStaff_political_status(basic.getTsmInfo().getPoliticalStatus().getName());
        fullData.setStaff_age(basic.getAge().toString());
        fullData.setExpYear(String.valueOf(DateUtil.betweenYear(DateUtil.parse(basic.getTsmInfo().getWorkStartDate()), DateUtil.date(), false)));

//        fullData.setPortrait_url("");
        fullData.setBirthday(basic.getBirthDate());
//        fullData.setExcellent("");
//        fullData.setProficient("");
//        fullData.setKnow("");

        List<ProjectData> projectDataList =  basic.getTsmProjectExperienceList().stream()
            .map(x -> {
                ProjectData pa = new ProjectData();
                pa.setProject_name(x.getProjectName());
                pa.setProject_post(x.getPosition());
                pa.setStart_time(x.getStartTime());
                pa.setEnd_time(x.getEndTime());
                pa.setProject_desc(x.getDescription());
                return pa;
            })
            .collect(Collectors.toList());
        fullData.setProjects(projectDataList);

        List<ExperienceData> experienceDataList =  basic.getTsmWorkExperienceList().stream()
            .map(x -> {
                ExperienceData experienceData = new ExperienceData();
                experienceData.setCompanyName(x.getWorkUnit());
                experienceData.setStartTime(x.getStartTime());
                experienceData.setEndTime(x.getEndTime());
                experienceData.setPost(x.getPosition());
                return experienceData;
            })
            .collect(Collectors.toList());
        fullData.setExperiences(experienceDataList);
        return fullData;
    }



}
