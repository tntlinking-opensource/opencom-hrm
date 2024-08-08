package com.geeke.induction.service;

import com.geeke.induction.dao.TsmContractInformationDao;
import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmContractInformationService")
@Transactional(readOnly = true)
public class TsmContractInformationService extends NoneBizInProcService<TsmContractInformationDao, TsmContractInformation> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmContractInformation> getBizClass() {
        return TsmContractInformation.class;
    }
}
