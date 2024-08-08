package com.geeke.accumulation.enums;

public enum DefaultConfigEnableEnum {
    ENABLE("0", "启用"),
    FORBIDDEN("1", "禁用")
    ;

    String value;
    String desc;

    DefaultConfigEnableEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
