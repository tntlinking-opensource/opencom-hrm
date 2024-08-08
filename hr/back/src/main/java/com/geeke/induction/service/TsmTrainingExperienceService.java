package com.geeke.induction.service;

import com.geeke.induction.dao.TsmTrainingExperienceDao;
import com.geeke.induction.entity.TsmTrainingExperience;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmTrainingExperienceService")
@Transactional(readOnly = true)
public class TsmTrainingExperienceService extends NoneBizInProcService<TsmTrainingExperienceDao, TsmTrainingExperience> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmTrainingExperience> getBizClass() {
        return TsmTrainingExperience.class;
    }
}
