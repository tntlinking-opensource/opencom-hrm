package com.geeke.induction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yi.luo
 * @date 2023-06-01 15:43
 */
@AllArgsConstructor
@Getter
public enum YesOrNoEnum {
    YES("1"),NO("0");

    private String code;



}
