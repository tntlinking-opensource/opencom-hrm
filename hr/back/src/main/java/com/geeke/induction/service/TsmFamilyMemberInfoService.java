package com.geeke.induction.service;

import com.geeke.induction.dao.TsmFamilyMemberInfoDao;
import com.geeke.induction.entity.TsmFamilyMemberInfo;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmFamilyMemberInfoService")
@Transactional(readOnly = true)
public class TsmFamilyMemberInfoService extends NoneBizInProcService<TsmFamilyMemberInfoDao, TsmFamilyMemberInfo> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmFamilyMemberInfo> getBizClass() {
        return TsmFamilyMemberInfo.class;
    }
}
