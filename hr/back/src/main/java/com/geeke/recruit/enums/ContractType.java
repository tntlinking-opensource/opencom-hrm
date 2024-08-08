package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 申请枚举值
 * @Author Chenhao
 * @Date 2020/9/3
 **/
@Getter
public enum ContractType
{
    /**
     * 申请类型
     */
    DRAFT(0,"草稿"),
    PROCESSED(4,"已绑定流程，待签字"),
    RENDER(9,"已生成"),
    SIGNED(2,"已签署"),
    BACK(3,"退回"),
    SEALING(5,"待盖章");


    private final Integer code;
    private final String msg;

     ContractType(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static ContractType getByMsg(String msg)
    {
        for(ContractType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("合约状态错误: " + msg);
    }
}
