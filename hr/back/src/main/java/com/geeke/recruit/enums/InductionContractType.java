package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 申请枚举值
 * @Author Chenhao
 * @Date 2020/9/3
 **/
@Getter
public enum InductionContractType
{
    /**
     * 申请类型
     */
    LABOR(0,"劳动合同"),
    LABORTRANS(1,"劳务合同"),
    INTERNSHIP(2,"实习协议");


    private final Integer code;
    private final String msg;

     InductionContractType(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static InductionContractType getByMsg(String msg)
    {
        for(InductionContractType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("合约状态错误: " + msg);
    }

    public static String getByCode(String code)
    {
        for(InductionContractType staffType : values())
        {
            if(staffType.getCode().toString().equals(code))
            {
                return staffType.msg;
            }
        }
        throw new IllegalArgumentException("上传的合同类型错误");
    }
}
