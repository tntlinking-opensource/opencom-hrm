package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 员工归属
 * @Author Chenhao
 * @Date 2020/9/3
 **/
@Getter
public enum HrmAttributionType
{
    /**
     * 申请类型
     */
    SELF(0,"本公司"),
    PROXY(1,"中崟云-代理"),
    OUTSIDE(2,"中崟云-外包");

    private final Integer code;
    private final String msg;

     HrmAttributionType(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static HrmAttributionType getByMsg(String msg)
    {
        for(HrmAttributionType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("员工归属错误: " + msg);
    }

    public static String getByCode(String code)
    {
        for(HrmAttributionType staffType : values())
        {
            if(staffType.getCode().toString().equals(code))
            {
                return staffType.msg;
            }
        }
        throw new IllegalArgumentException("员工归属错误");
    }
}
