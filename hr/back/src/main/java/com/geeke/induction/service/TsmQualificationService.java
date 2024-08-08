package com.geeke.induction.service;

import com.geeke.induction.dao.TsmQualificationDao;
import com.geeke.induction.entity.TsmQualification;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmQualificationService")
@Transactional(readOnly = true)
public class TsmQualificationService extends NoneBizInProcService<TsmQualificationDao, TsmQualification> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmQualification> getBizClass() {
        return TsmQualification.class;
    }
}
