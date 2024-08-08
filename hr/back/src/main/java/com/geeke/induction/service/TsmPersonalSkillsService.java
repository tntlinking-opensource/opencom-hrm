package com.geeke.induction.service;

import com.geeke.induction.dao.TsmPersonalSkillsDao;
import com.geeke.induction.entity.TsmPersonalSkills;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmPersonalSkillsService")
@Transactional(readOnly = true)
public class TsmPersonalSkillsService extends NoneBizInProcService<TsmPersonalSkillsDao, TsmPersonalSkills> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmPersonalSkills> getBizClass() {
        return TsmPersonalSkills.class;
    }
}
