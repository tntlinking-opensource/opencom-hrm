package com.geeke.accumulation.dto;

import java.util.List;

public class InitEmployeeFundParam {
    private List<String> employeeNos;

    private boolean needVerify;

    public List<String> getEmployeeNos() {
        return employeeNos;
    }

    public void setEmployeeNos(List<String> employeeNos) {
        this.employeeNos = employeeNos;
    }

    public boolean getNeedVerify() {
        return needVerify;
    }

    public void setNeedVerify(boolean needVerify) {
        this.needVerify = needVerify;
    }
}
