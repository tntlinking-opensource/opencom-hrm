package com.geeke.induction.dto;

import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.entity.TsmPost;
import com.geeke.org.entity.Company;
import com.geeke.org.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yi.luo
 * @date 2023-06-01 15:12
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDemandDto {

    private TsmPost tsmPost;
    private TsmContractInformation tsmContractInformation;
    private Department department;
    private TsmInductionInfo info;
    private TsmInductionBasic basic;

    public Company getCompany() {
        return department.getCompany();
    }
}
