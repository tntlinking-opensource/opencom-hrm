package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 性别枚举值
 * @Author Chenhao
 * @Date 2021/5/25
 **/
@Getter
public enum GenderType {
    MALE(0,"男"),
    FEMALE(1,"女");
    private final Integer code;
    private final String msg;

    GenderType(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static String getByCode(String code)
    {
        for(GenderType genderType : values())
        {
            if(genderType.getCode().toString().equals(code))
            {
                return genderType.msg;
            }
        }
        throw new IllegalArgumentException("上传的用户类型错误");
    }
}
