package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 员工类型枚举值
 * @Author Chenhao
 * @Date 2020/7/30
 **/
@Getter
public enum StaffStatus
{
    /**
     * 员工类型
     */
    ON(0,"在职"),
    TRY(1,"试用"),
    TRAINNING(2,"实习");
    private final Integer code;
    private final String msg;

     StaffStatus(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static StaffStatus getByMsg(String msg)
    {
        for(StaffStatus staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("用户状态错误: " + msg);
    }
    public static String getByCode(String code)
    {
        for(StaffStatus staffType : values())
        {
            if(staffType.getCode().toString().equals(code))
            {
                return staffType.msg;
            }
        }
        throw new IllegalArgumentException("用户状态错误");
    }
    public static int getByCodeMsg(String msg)
    {
        for(StaffStatus staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType.code;
            }
        }
        return 0;
    }
}
