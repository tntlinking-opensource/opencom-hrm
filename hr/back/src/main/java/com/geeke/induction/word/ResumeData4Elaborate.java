package com.geeke.induction.word;

import com.deepoove.poi.data.NumbericRenderData;
import lombok.Data;

/**
 * @Description 复杂模板
 * @Author Chenhao
 * @Date 2020/6/28
 **/
@Data
public class ResumeData4Elaborate extends BasicResumeData
{
        /**标签循环*/
        private NumbericRenderData stack;
}
