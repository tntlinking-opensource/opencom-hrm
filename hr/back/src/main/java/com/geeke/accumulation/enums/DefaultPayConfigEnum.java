package com.geeke.accumulation.enums;

public enum DefaultPayConfigEnum {
    IS_DEFAULT("0", "是"),
    NOT_DEFAULT("1", "否");

    String value;
    String desc;

    DefaultPayConfigEnum(String value, String desc) {
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
