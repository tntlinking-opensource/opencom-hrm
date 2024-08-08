package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 申请枚举值
 * @Author Chenhao
 * @Date 2020/9/3
 **/
@Getter
public enum ApplyType
{
    /**
     * 申请类型
     */
    INDUCTION("HRRZ","入职申请"),
    SHAYUN("SYRZ","鲨云入职申请"),
    LAIDOFF("HRLZ","离职申请"),
    INVOICE("HRZM","证明申请"),
    CONTRACT("HRHY","合约申请"),
    CONTRACT_RENEW("HYXQ","合约续签"),
    LAIDOFF_FILE("HRLZ-FILE","离职文件签署"),
    LAIDOFF_TRANSACTION("HRLZ-TRANSACTION","离职事务办理"),
    THIRDPARTY("THIRDPARTY","三方协议签订"),
    EXCEPTIONAPPLY("KQSQ","考勤异常申请"),
    RECRUIT("REC","招聘申请"),
    GENERATE_ATTENDANCE("KQ","通过cube数据生成考勤报表"),
    RECRUIT_CODE("ZP","招聘code");


    private final String code;
    private final String msg;

     ApplyType(String code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static ApplyType getByMsg(String msg)
    {
        for(ApplyType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("申请类型错误: " + msg);
    }
}
