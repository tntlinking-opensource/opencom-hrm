package com.geeke.induction.dto;

import com.geeke.sys.entity.DictItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * @author yi.luo
 * @date 2023-04-26 14:03
 */
@AllArgsConstructor
@Getter
@Setter
public class AppHomePageDto {

    /**
     * tsm_induction_basic
     */
    private String id;
    private String name;
    private DictItem gender;
    private DictItem ethnicity;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private String nativePlace;
    private String residenceAddress;

    /**
     * tsm_induction_info
     */
    private String infoId;
    private DictItem maritalStatus;
    private DictItem childbirthStatus;
    private DictItem politicalStatus;

    /**
     * 岗位信息 tsm_post
     */
    private String postId;
    private DictItem jobPosition;
    private String companyName;
    private String expectedSalary;

}
