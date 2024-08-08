package com.geeke.recruit.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.recruit.dao.TsmRecruitDao;
import com.geeke.recruit.entity.TsmRecruit;
import com.geeke.wf.entity.WfDraft;
import com.geeke.wf.service.BizInProcService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * recruitService
 *
 * @author
 */

@Service("tsmRecruitService")
@Transactional(readOnly = true)
public class TsmRecruitService extends BizInProcService<TsmRecruitDao, TsmRecruit> {
    @Autowired
    private SequenceService sequenceService;
    /**
     * 获取表单对象的class
     *
     * @return
     */
    @Override
    protected Class<TsmRecruit> getBizClass() {
        return TsmRecruit.class;
    }



    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int saveDraft(String strDraft, String strEntity, MultipartFile[] attachmentsReq) throws IOException {
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);
        WfDraft wfDraft = JSONObject.parseObject(strDraft, WfDraft.class);
        MultipartFile[] attachments = ArrayUtil.isEmpty(attachmentsReq) ? new MultipartFile[]{} : attachmentsReq;

        Optional.ofNullable(entity.getId())
            .ifPresent(wfDraft::setId);

        //这里的entity set数据是为了 存入草稿时候的json数据 能够pass一些规则  度过更新逻辑
        entity.setProcStatusByValue(TsmRecruit.PROC_FLAG_DRAFT);
        entity.setUpdateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        int ret = this.saveDraft(wfDraft, entity, ArrayUtil.isEmpty(attachments) ? new MultipartFile[]{} : attachments);

        //这里的set是为了存入草稿id和附件id 连通逻辑与前端交互
        entity.setprocdefId(wfDraft.getProcdefId());
        entity.setId(wfDraft.getId());
        this.saveTsmRecruit(entity,TsmRecruit.PROC_FLAG_DRAFT);
        return ret;
    }


    /**
     * 保存单个招聘实体
     * @param entity 数据实体
     * @param procFlag 流程flag
     * @return 保存后的数据实体，一个填充了id的数据
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public TsmRecruit saveTsmRecruit(TsmRecruit entity,String procFlag) {
        TsmRecruit oldTsmRecruit;
        entity.genRecruitCode(sequenceService);

        if (StrUtil.isNotBlank(entity.getId()) && ObjUtil.isNotNull(oldTsmRecruit = get(entity.getId()))) {
            entity.setUpdateDate(oldTsmRecruit.getUpdateDate());
            entity.preUpdate();
            entity.setProcStatusByValue(procFlag);
            doUpdate(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_UPDATED, entity));
        } else {
            entity.preInsert();
            entity.setProcStatusByValue(procFlag);
            doInsert(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, entity));
        }
        return entity;
    }

    /**
     * 创建流程
     * @param procDefId
     * @param strEntity
     * @param attachments
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public ActResult createTsmRecruit(String procDefId, String strEntity, MultipartFile[] attachments) {
        TsmRecruit entity = JSONObject.parseObject(strEntity, TsmRecruit.class);

        ActResult actResult = this.create(procDefId, entity, attachments);

        if(actResult.getCode() == ActResult.EnumCodes.AutoSubmit || actResult.getCode() == ActResult.EnumCodes.Done) {
            this.saveTsmRecruit(entity,TsmRecruit.PROC_FLAG_APPROVE);
            //删除草稿
            Optional.ofNullable(wfDraftService.get(entity.getId()))
                .ifPresent(wfDraft -> wfDraftService.delete(wfDraft));
        }
        return actResult;
    }

}
