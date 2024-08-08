package com.geeke.induction.service;

import com.geeke.induction.dao.TsmEducationalRecordsDao;
import com.geeke.induction.entity.TsmEducationalRecords;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmEducationalRecordsService")
@Transactional(readOnly = true)
public class TsmEducationalRecordsService extends NoneBizInProcService<TsmEducationalRecordsDao, TsmEducationalRecords> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmEducationalRecords> getBizClass() {
        return TsmEducationalRecords.class;
    }
}
