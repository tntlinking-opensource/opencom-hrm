package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 申请枚举值
 * @Author Chenhao
 * @Date 2020/9/3
 **/
@Getter
public enum InductionJsonType
{
    /**
     * 申请类型
     */
    WORK("WORK","工作简历"),
    FAMILY("FAMILY","家庭构成"),
    EMERGENCY("EMERGENCY","紧急联系人"),
    SKILL("SKILL","技能"),
    EDU("EDU","教育经历"),
    CERTIFICATE("CERTIFICATE","证书");

    private final String code;
    private final String msg;

     InductionJsonType(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static InductionJsonType getByMsg(String msg)
    {
        for(InductionJsonType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("json数据类型错误: " + msg);
    }

    public static InductionJsonType getByCode(String code)
    {
        for(InductionJsonType staffType : values())
        {
            if(staffType.getCode().equals(code))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("json数据类型错误: " + code);
    }
}
