package com.geeke.taxRate.enums;

public enum TaxType {
    SALARY("1", "工资薪金税率表"),
    YEAR_BONUS("2", "年终奖税率表"),
    REWARD_REMUNERATION("3", "劳务报酬税率表"),
    LEAVE_COMPENSATION("4", "离职补偿税率表");

    String type;
    String typeName;

    TaxType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
