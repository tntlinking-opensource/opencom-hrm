package com.geeke.induction.service;

import com.geeke.induction.dao.TsmCardDao;
import com.geeke.induction.entity.TsmCard;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmCardService")
@Transactional(readOnly = true)
public class TsmCardService extends NoneBizInProcService<TsmCardDao, TsmCard> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmCard> getBizClass() {
        return TsmCard.class;
    }
}
