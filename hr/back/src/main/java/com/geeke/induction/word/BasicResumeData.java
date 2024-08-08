package com.geeke.induction.word;

import com.deepoove.poi.data.PictureRenderData;
import lombok.Data;

import java.util.List;

/**
 * @Description 简历类
 * @Author Chenhao
 * @Date 2020/6/29
 **/
@Data
public class BasicResumeData
{
    /**1头像*/
    private PictureRenderData portrait;
    /**1身份证*/
    private String staff_idCard;

    /**1姓名*/
    private String staff_user_name;
    /**1邮编--目前没有设置该参数*/
    private String staff_post_code;
    /**1性别*/
    private String staff_user_gender;
    /**1电话*/
    private String staff_user_mobile;
    /**1籍贯*/
    private String staff_native;
    /**1户籍地址*/
    private String staff_nativeplace;
    /**1邮箱*/
    private String staff_user_email;
    /**1所在地*/
    private String staff_location;
    /**1开始工作时间*/
    private String staff_startwork;
    /**1教育方式*/
    private String method;
    /**1学位*/
    private String degree;
    /**1学历*/
    private String background;
    /**1学校*/
    private String college;
    /**1入学时间*/
    private String college_start;
    /**1毕业时间*/
    private String college_end;
    /**1证书编码*/
    private String gratitude_code;
    /**1专业*/
    private String major;
    /**1民族*/
    private String staff_nation_name;
    /**1婚姻*/
    private String staff_marriage_status;
    /**1微信号--目前没有设置该参数*/
    private String wechat;
    /**1政治面貌*/
    private String staff_political_status;
    /**1年龄**/
    private String staff_age;
    /**1工作年限*/
    private String expYear;
    /**1头像地址*/
    private String portrait_url;
    /**生日*/
    private String birthday;
    /**1计算机语言*/
    private String excellent;
    private String proficient;
    private String know;
    /**取数*/
    /**工作*/
    private List<ExperienceData> experiences;
    /**技能*/
    private List<LanguageData> languages;
    /**项目*/
    private List<ProjectData> projects;
}
