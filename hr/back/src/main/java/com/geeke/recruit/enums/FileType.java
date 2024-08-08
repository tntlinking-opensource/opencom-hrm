package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 申请枚举值
 * @Author Chenhao
 * @Date 2020/9/3
 **/
@Getter
public enum FileType
{
    /**
     * 申请类型
     */
    INTERVIEW("interview","面试文件");

    private final String code;
    private final String msg;

     FileType(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static FileType getByMsg(String msg)
    {
        for(FileType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("申请类型错误: " + msg);
    }
}
