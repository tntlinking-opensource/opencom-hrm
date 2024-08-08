package com.geeke.induction.word;

import lombok.Data;

/**
 * @Description 经验数据
 * @Author Chenhao
 * @Date 2020/6/29
 **/
@Data
public class ExperienceData {
    private String companyName;
    private String deptName;
    private String startTime;
    private String endTime;
    private String post;
    /**打印未使用*/
    private String witness;
    private String content;
    private String wages;
}
