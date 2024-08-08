package com.geeke.induction.service;

import com.geeke.common.data.Parameter;
import com.geeke.induction.dao.TsmPostDao;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmPost;
import com.geeke.wf.service.NoneBizInProcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * inductionService
 * @author
 * @version
 */

@Service("tsmPostService")
@Transactional(readOnly = true)
public class TsmPostService extends NoneBizInProcService<TsmPostDao, TsmPost> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmPost> getBizClass() {
        return TsmPost.class;
    }

    public List<TsmPost> getByInductionId(String inductionId) {
        List<Parameter> list = new TsmInductionBasic(inductionId).queryInductionIdParam();
        return super.listAll(list, "");
    }
}
