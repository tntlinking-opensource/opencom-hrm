package com.geeke.induction.request;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yi.luo
 * @date 2023-05-30 15:29
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InductionHomeSearchRequest extends SearchParams {
    //表basic如下字段
    private String name;
    private String phoneNumber;
    private String actStatus;

    //表post如下字段
    private String entryTime;

    //表info如下字段
    private String dataSubmission;
    private String dataValidation;
    private String recordStatus;
    private String abandonReporting;

    private PageRequest pageRequest = new PageRequest(null);
    public String getDEL_FLAG_NORMAL() {
        return  pageRequest.getDEL_FLAG_NORMAL();
    }

    public String getDEL_FLAG_DELETE() {
        return pageRequest.getDEL_FLAG_DELETE();
    }

    public String getDEL_FLAG_AUDIT() {
        return pageRequest.getDEL_FLAG_AUDIT();
    }
}
