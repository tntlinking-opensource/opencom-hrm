package com.geeke.recruit.enums;

import lombok.Getter;

/**
 * @Description 员工类型枚举值
 * @Author Chenhao
 * @Date 2020/7/30
 **/
@Getter
public enum StaffType
{
    /**
     * 员工类型
     */
    /*CASUAL(0,"临时工"),
    PART_TIME(1,"兼职工"),
    DISPATCH(2,"劳务工"),
    INTERNSHIP(3,"实习生"),
    FORMAL(4,"正式员工"),
    FORMAL_ISSUE(5,"正式员工-代发"),
    FORMAL_LOGISTICS(6,"正式员工-后勤"),
    FORMAL_FOREIGNER(7,"正式员工-外籍"),
    FORMAL_DISABLED(8,"正式员工-残疾人"),
    RE_EMPLOYED(9,"退休返聘");*/
    FORMAL(0,"正式员工"),
    DISPATCH(1,"劳务工"),
    INTERNSHIP(2,"实习生"),
    RE_EMPLOYED(3,"退休返聘"),
    FORMAL_DISABLED(4,"正式员工-残疾人"),
    PART_TIME(5,"兼职工"),
    CASUAL(6,"临时工"),
    SUPPLIER(7,"供应商"),
    FORMAL_LINKED_DISABLED(8,"正式员工-挂靠残疾人"),
    FORMAL_FOREIGNER(9,"正式员工-外籍"),
    FORMAL_LINKED(10,"正式员工-挂靠"),
    FORMAL_LOGISTICS(11,"正式员工-后勤"),
    FORMAL_EDM(12,"正式员工-代发"),
    DISPATCH_EDM(13,"劳务工-代发"),
    RE_EMPLOYED_LINKED(14,"退休返聘-挂靠"),
    DISPATCH_LINKED(15,"劳务工-挂靠"),
    PART_TIME_LINKED(16,"兼职工-挂靠"),
    ;
    private final Integer code;
    private final String msg;

     StaffType(Integer code,String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public static StaffType getByMsg(String msg)
    {
        for(StaffType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType;
            }
        }
        throw new IllegalArgumentException("上传的用户类型错误: " + msg);
    }
    public static String getByCode(String code)
    {
        for(StaffType staffType : values())
        {
            if(staffType.getCode().toString().equals(code))
            {
                return staffType.msg;
            }
        }
        throw new IllegalArgumentException("上传的用户类型错误");
    }
    public static int getByCodeMsg(String msg)
    {
        for(StaffType staffType : values())
        {
            if(staffType.getMsg().equals(msg))
            {
                return staffType.code;
            }
        }
        return 0;
    }
}
