package com.geeke.induction.service;

import com.geeke.induction.dao.TsmEmployeeAttachmentsDao;
import com.geeke.induction.entity.TsmEmployeeAttachments;
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

@Service("tsmEmployeeAttachmentsService")
@Transactional(readOnly = true)
public class TsmEmployeeAttachmentsService extends NoneBizInProcService<TsmEmployeeAttachmentsDao, TsmEmployeeAttachments> {

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<TsmEmployeeAttachments> getBizClass() {
        return TsmEmployeeAttachments.class;
    }

    /**
     * 通过查询inductionId 获取文件相关信息
     * @param inductionId
     * @return
     */
    public List<TsmEmployeeAttachments> getByInductionId(String inductionId) {
        return listAll(new TsmInductionBasic(inductionId).queryInductionIdParam(), "");
    }
}
