package com.geeke.induction.service;

import com.geeke.induction.dao.TsmWorkExperienceDao;
import com.geeke.induction.entity.TsmWorkExperience;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmWorkExperienceService")
@Transactional(readOnly = true)
public class TsmWorkExperienceService extends NoneBizInProcService<TsmWorkExperienceDao, TsmWorkExperience> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmWorkExperience> getBizClass() {
        return TsmWorkExperience.class;
    }
}
