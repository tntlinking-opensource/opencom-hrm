package com.geeke.accumulation.enums;

public enum DefaultConfigStatus {
    NORMAL_DISCOUNT("0", "正常折扣"),
    NO_DISCOUNT("1", "无折扣")
    ;

    String value;
    String desc;

    public static boolean checkStatus(String status) {
        DefaultConfigStatus[] values = DefaultConfigStatus.values();
        for (DefaultConfigStatus value : values) {
            if (value.getValue().equals(status)) {
                return true;
            }
        }
        return false;
    }

    DefaultConfigStatus(String value, String desc) {
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
