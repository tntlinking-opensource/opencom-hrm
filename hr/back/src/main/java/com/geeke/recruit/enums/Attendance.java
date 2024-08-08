package com.geeke.recruit.enums;

import lombok.Getter;

@Getter
public enum Attendance
{
    SICK(0,"病假"),
    OUTSIDE(1,"公出"),
    BUSINESS(2,"出差");
    private final Integer code;
    private final String msg;

    Attendance(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static String getByCode(String code)
    {
        for(Attendance genderType : values())
        {
            if(genderType.getCode().toString().equals(code))
            {
                return genderType.msg;
            }
        }
        throw new IllegalArgumentException("上传的用户类型错误");
    }
}
