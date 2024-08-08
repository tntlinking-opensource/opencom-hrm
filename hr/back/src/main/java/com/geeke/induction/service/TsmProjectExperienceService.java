package com.geeke.induction.service;

import com.geeke.induction.dao.TsmProjectExperienceDao;
import com.geeke.induction.entity.TsmProjectExperience;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmProjectExperienceService")
@Transactional(readOnly = true)
public class TsmProjectExperienceService extends NoneBizInProcService<TsmProjectExperienceDao, TsmProjectExperience> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmProjectExperience> getBizClass() {
        return TsmProjectExperience.class;
    }
}
