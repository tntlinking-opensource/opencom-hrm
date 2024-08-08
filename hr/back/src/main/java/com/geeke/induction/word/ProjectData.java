package com.geeke.induction.word;

import lombok.Data;

@Data
public class ProjectData {
    /**项目名称*/
    private String project_name;
    /**项目职位*/
    private String project_post;
    /**项目开始时间*/
    private String start_time;
    /**项目结束时间*/
    private String end_time;
    /**项目描述*/
    private String project_desc;
}
