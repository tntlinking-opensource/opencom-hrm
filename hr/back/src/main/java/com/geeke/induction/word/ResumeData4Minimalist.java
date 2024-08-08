package com.geeke.induction.word;

import com.deepoove.poi.data.MiniTableRenderData;
import lombok.Data;

/**
 * @Description 简约模板
 * @Author Chenhao
 * @Date 2020/6/28
 **/
@Data
public class ResumeData4Minimalist extends BasicResumeData
{
        /**1手绘表格*/
        /**家庭情况*/
        private MiniTableRenderData familyTable;
        /**联系人*/
        private MiniTableRenderData contactTable;
        /**工作经验*/
        private MiniTableRenderData experienceTable;
        /**掌握技能*/
        private MiniTableRenderData languageTable;
        /**项目经验*/
        private MiniTableRenderData projectTable;
}
