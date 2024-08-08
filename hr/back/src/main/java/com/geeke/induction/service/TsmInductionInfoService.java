package com.geeke.induction.service;

import com.geeke.induction.dao.TsmInductionInfoDao;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmInductionInfoService")
@Transactional(readOnly = true)
public class TsmInductionInfoService extends NoneBizInProcService<TsmInductionInfoDao, TsmInductionInfo> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmInductionInfo> getBizClass() {
        return TsmInductionInfo.class;
    }

    public List<TsmInductionInfo> getByTsmInductionInfo(TsmInductionBasic basic) {
        return listAll(basic.queryInductionIdParam(), "");
    }

    public List<TsmInductionInfo> getByTsmInductionInfo(String inductionId) {
        return listAll(new TsmInductionBasic(inductionId).queryInductionIdParam(), "");
    }
}
