package com.geeke.recruit.service;

import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Parameter;
import com.geeke.recruit.dao.TsmRecruitDao;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.wf.persistence.WfDataEntity;
import com.geeke.wf.service.BizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * hrRecruitService
 * @author
 * @version
 */

@Service("tsmHrRecruitService")
@Transactional(readOnly = true)
public class TsmHrRecruitService  extends BizInProcService<TsmRecruitDao, TsmRecruit> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmRecruit> getBizClass() {
        return TsmRecruit.class;
    }


    public void procAndActStatusParameterBuild( SearchParams searchParams) {
        List<Parameter> parameters = Optional
            .ofNullable(searchParams.getParams())
            .orElse(new ArrayList<>());

        WfDataEntity<TsmRecruit> wfDataEntity = new TsmRecruit();
        wfDataEntity.setProcStatusByValue(TsmRecruit.PROC_FLAG_COMPLETED);

        parameters.add(new Parameter("act_status", "=", wfDataEntity.getProcStatus().getId()));

        searchParams.setParams(parameters);
    }
}
