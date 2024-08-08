package com.geeke.induction.service;

import com.geeke.induction.dao.TsmEmergencyContactDao;
import com.geeke.induction.entity.TsmEmergencyContact;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmEmergencyContactService")
@Transactional(readOnly = true)
public class TsmEmergencyContactService extends NoneBizInProcService<TsmEmergencyContactDao, TsmEmergencyContact> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmEmergencyContact> getBizClass() {
        return TsmEmergencyContact.class;
    }

    public List<TsmEmergencyContact> getByTsmInductionInfo(String inductionId) {
        return listAll(new TsmInductionBasic(inductionId).queryInductionIdParam(), "");
    }

}
