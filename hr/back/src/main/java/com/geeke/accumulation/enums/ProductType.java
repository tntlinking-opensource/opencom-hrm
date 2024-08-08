package com.geeke.accumulation.enums;

public enum ProductType {
    INDUSTRIAL_INJURY_INSURANCE("0", "工伤保险"),
    UNEMPLOYMENT_INSURANCE("1", "失业保险"),
    MEDICAL_INSURANCE("2", "医疗保险"),
    MATERNITY_INSURANCE("3", "生育保险"),
    ENDOWMENT_INSURANCE("4", "养老保险"),
    FUND_FOR_DISABLED("5", "残疾人基金"),
    CRITICAL_ILLNESS_INSURANCE("6", "大病保险"),
    HOUSING_ACCUMULATION_FUND("7", "住房公积金")
    ;
    String type;
    String name;

    ProductType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getNameByType(String type) {
        ProductType[] values = ProductType.values();
        for (ProductType value : values) {
            if (value.getType().equals(type)) {
                return value.getName();
            }
        }
        return "";
    }

    public static boolean checkType(String type) {
        ProductType[] values = ProductType.values();
        for (ProductType value : values) {
            if (value.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static String getCompleteNameByType(String type, String companyPayScale, String personPayScale) {
        ProductType[] values = ProductType.values();
        for (ProductType value : values) {
            if (value.getType().equals(type)) {
                return value.getName() + "(" +companyPayScale + "+" + personPayScale + ")";
            }
        }
        return "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
