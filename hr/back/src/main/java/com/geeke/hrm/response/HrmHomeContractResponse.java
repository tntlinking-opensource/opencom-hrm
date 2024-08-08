package com.geeke.hrm.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * @author yi.luo
 * @date 2023-05-31 11:30
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HrmHomeContractResponse {

    private List<ContractRemind> renewalOfContract;
    private List<ContractRemind> employmentContract;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContractRemind {
        private String departmentName;
        private String name;
        private String daysApart;
        private String employeeNo;
    }
}
