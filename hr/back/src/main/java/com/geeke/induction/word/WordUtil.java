package com.geeke.induction.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmPersonalSkills;
import com.geeke.induction.entity.TsmProjectExperience;
import com.geeke.induction.entity.TsmWorkExperience;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WordUtil
{

    /**
     * @Description 样例输出
     * @Paras 模板的文件名
     * @Retrn 输出文件的文件名
     * @Author Chenhao
     * @Date 2020/5/27
     **/
    public static byte[] testResumeExample(String templateFileName, ResumeData4Elaborate init) throws Exception {
        XWPFTemplate template = XWPFTemplate.compile(templateFileName).render(init);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        template.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        template.close();
        return bytes;
    }
    /**
     * @Description 样例输出
     * @Paras 模板的文件名
     * @Retrn 输出文件的文件名
     * @Author Chenhao
     * @Date 2020/5/27
     **/
    public static byte[] testResumeExample2(String templateFileName, ResumeData4Minimalist init) throws Exception {
        XWPFTemplate template = XWPFTemplate.compile(templateFileName).render(init);


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        template.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        outputStream.close();
        template.close();
        return bytes;
    }

    /**
     * @Description 主数据拼凑
     * @Author Chenhao
     * @Date 2020/5/27
     **/
    //合并生成主数据
    public static ResumeData4Elaborate init(ResumeData4Elaborate datas
    , TsmInductionBasic basic)
    {
//        String portrait_url = portrait_suffix+datas.getPortrait_url();
//        datas.setPortrait(new PictureRenderData(110, 110, new File(portrait_url)));
        // 技术栈部分
        List<TsmPersonalSkills> skills = basic.getTsmPersonalSkillsList();
        List<TextRenderData> textRenderDatas = new ArrayList<>();

        Style style = new Style();
        style.setFontSize(10);
        style.setColor("7F7F7F");
        style.setFontFamily("微软雅黑");
        for(TsmPersonalSkills languageData:skills)
        {
            String skill_name = languageData.getSkillName();
            String skill_level = languageData.getSkillLevel().getName();
            String notice = languageData.getRemarks();
            TextRenderData textRenderData = new TextRenderData(skill_name+"-"+skill_level+"-"+notice);
            textRenderData.setStyle(style);
            textRenderDatas.add(textRenderData);
        }

        datas.setStack(new NumbericRenderData(textRenderDatas));

        return datas;
    }

//    /**
//     * @Description 主数据拼凑
//     * @Paras a
//     * @Retrn a
//     * @Author Chenhao
//     * @Date 2020/5/27
//     **/
//    //合并生成主数据
//    public static ResumeData4Minimalist init2(ResumeData4Minimalist datas,String portrait_suffix) {
//        String portrait_url = portrait_suffix+datas.getPortrait_url();
//        datas.setPortrait(new PictureRenderData(110, 110, new File(portrait_url)));
//
//        datas.setGratitude_code("1xdfg123");
//
//        return datas;
//    }


    /**
     * @Description 联系人表格
     * @Paras a
     * @Retrn a
     * @Author Chenhao
     * @Date 2020/5/27
     **/
    public static MiniTableRenderData createContactTable()
    {
        //准备数据源
        List<ContactPersonData> contactPersons = getContactList();
        //设置表头数据
        //设置表头格式
        Style style = setStyle();

        //根据格式创建表头数据
        RowRenderData rowHeaderData = RowRenderData.
                build(new TextRenderData("姓名",style),new TextRenderData("称谓",style),
                        new TextRenderData( "出生年月",style),new TextRenderData("工作单位",style),
                        new TextRenderData("居住地址",style),new TextRenderData("联系电话",style));
        //设置表的行数据
        List<RowRenderData> list = new ArrayList();
        for(int i =0;i<contactPersons.size();i++)
        {
            ContactPersonData contactPersonData = contactPersons.get(i);
            //写值
            list.add(RowRenderData.build(contactPersonData.getCname(),
                    contactPersonData.getCrelation(),
                    contactPersonData.getCbirthday(),
                    contactPersonData.getCcompany(),
                    contactPersonData.getCaddress(),
                    contactPersonData.getCmobile()));
        }
        return new MiniTableRenderData(rowHeaderData, list,15.04f);
    }


    /**
     * @Description 语言表格
     * @Paramsa
     * @Retur a
     * @Author Chenhao
     * @Date 2020/5/27
     **/
    public static MiniTableRenderData createLanguageTable(List<TsmPersonalSkills> languageDataList)
    {
        //设置表头数据
        //设置表头格式
        Style style = setStyle();

        //根据格式创建表头数据
        RowRenderData rowHeaderData = RowRenderData.
                build(new TextRenderData("语言类型",style),new TextRenderData("熟练程度",style),
                        new TextRenderData("备注",style));
        //设置表的行数据
        List<RowRenderData> list = new ArrayList();
        for(int i =0;i<languageDataList.size();i++)
        {
            TsmPersonalSkills languageData = languageDataList.get(i);
            //写值
            list.add(RowRenderData.build(
                    languageData.getSkillName(),
                    languageData.getSkillLevel().getValue(),
                    languageData.getRemarks()));
        }
        return new MiniTableRenderData(rowHeaderData, list,15.04f);
    }

    /**
     * @Description 经验表格
     * @Params a
     * @Return a
     * @Author Chenhao
     * @Date 2020/5/27
     **/
    public static MiniTableRenderData createExperienceTable2( List<TsmWorkExperience> experienceDatas)
    {
        //设置表头数据
        //设置表头格式
        Style style = setStyle();
        //根据格式创建表头数据
        RowRenderData rowHeaderData = RowRenderData.
                build(new TextRenderData("公司名称",style),new TextRenderData("开始日期",style),
                        new TextRenderData( "结束日期",style), new TextRenderData("职位",style));
        //设置表的行数据
        List<RowRenderData> list = new ArrayList();
        for(int i =0;i<experienceDatas.size();i++)
        {
            TsmWorkExperience experienceData = experienceDatas.get(i);
            //写值
            list.add(RowRenderData.build(experienceData.getWorkUnit(),
                    experienceData.getStartTime(),
                    experienceData.getEndTime(),
                    experienceData.getPosition()));
        }
        return new MiniTableRenderData(rowHeaderData, list,15.04f);
    }

    /**
     * @Description 项目表格
     * @Params a
     * @Return a
     * @Author Chenhao
     * @Date 2020/6/27
     **/
    public static MiniTableRenderData createProjectTable( List<TsmProjectExperience> projectDatas)
    {
        //设置表头数据
        //设置表头格式
        Style style = setStyle();
        //根据格式创建表头数据
        RowRenderData rowHeaderData = RowRenderData.
                build(new TextRenderData("项目名称",style),new TextRenderData("开始日期",style),
                        new TextRenderData( "结束日期",style), new TextRenderData("职位",style),
                        new TextRenderData("项目描述",style));
        //设置表的行数据
        List<RowRenderData> list = new ArrayList();
        for(int i =0;i<projectDatas.size();i++)
        {
            TsmProjectExperience projectData = projectDatas.get(i);
            //写值
            list.add(RowRenderData.build(
                    projectData.getProjectName(),
                    projectData.getStartTime(),
                    projectData.getEndTime(),
                    projectData.getPosition(),
                    projectData.getDescription()));
        }
        return new MiniTableRenderData(rowHeaderData, list,15.04f);
    }

    /**
     * @Description 设置表头格式
     * @Author Chenhao
     * @Date 2020/6/12
     **/
    public static Style setStyle()
    {
        Style style = new Style();
        style.setColor("000000");
        style.setFontFamily("新宋体");
        style.setFontSize(10);
        style.setBold(true);
        return style;
    }

    /**
     * @Description 设置联系人数据
     * @Author Chenhao
     * @Date 2020/6/12
     **/
    public static List<ContactPersonData> getContactList()
    {
        List<ContactPersonData> contactPersons = new ArrayList<>();
        ContactPersonData contact1 = new ContactPersonData();
        ContactPersonData contact2 = new ContactPersonData();
        contact1.setCrelation("兄弟");
        contact1.setCname("陈杰伦");
        contact1.setCaddress("成都市春熙路");
        contact1.setCbirthday("1988-01-01");
        contact1.setCcompany("优衣库");
        contact1.setCmobile("13708207777");
        contact2.setCrelation("父子");
        contact2.setCname("陈俊杰");
        contact2.setCaddress("成都市金融城");
        contact2.setCbirthday("1963-01-01");
        contact2.setCcompany("浦发银行");
        contact2.setCmobile("13708206115");
        contactPersons.add(contact1);
        contactPersons.add(contact2);
        return contactPersons;
    }
}
