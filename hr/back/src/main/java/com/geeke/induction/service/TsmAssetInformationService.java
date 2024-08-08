package com.geeke.induction.service;

import com.geeke.induction.dao.TsmAssetInformationDao;
import com.geeke.induction.entity.TsmAssetInformation;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmAssetInformationService")
@Transactional(readOnly = true)
public class TsmAssetInformationService extends NoneBizInProcService<TsmAssetInformationDao, TsmAssetInformation> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmAssetInformation> getBizClass() {
        return TsmAssetInformation.class;
    }
}
