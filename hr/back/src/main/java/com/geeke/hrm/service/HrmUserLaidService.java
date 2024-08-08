package com.geeke.hrm.service;

import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.hrm.dao.HrmUserLaidDao;
import com.geeke.hrm.entity.HrmUserLaid;
import com.geeke.utils.StringUtils;
import com.geeke.wf.service.BizInProcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 离职申请Service
 * @author
 * @version
 */

@Service("hrmUserLaidService")
@Transactional(readOnly = true)
public class HrmUserLaidService extends BizInProcService<HrmUserLaidDao, HrmUserLaid> {
    private static final String CODE_RULE = "laid";

    @Autowired
    private SequenceService sequenceService;
    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<HrmUserLaid> getBizClass() {
        return HrmUserLaid.class;
    }

    @Override
    public ActResult create(String procDefId, HrmUserLaid entity, MultipartFile[] attachments) {
        //设置表单编号
        if(StringUtils.isBlank(entity.getId())){
            String code = sequenceService.generate(CODE_RULE, entity);
            entity.setName(code);
        }
        //设置交接人姓名
        entity.setHandingname(entity.getHandinguserid().getName());
        //设置交接状态 为交接中
        entity.setHandingstatus("0");
        //设置行政事务办理
        entity.setAdminaffairsstatus("0");
        //设置财务事务办理
        entity.setFinanceaffairsstatus("0");
        //设置人事办理状态
        entity.setHumanaffairsstatus("0");
        //设置离职状态
        entity.setLaidstatus("0");
        return super.create(procDefId, entity, attachments);
    }
}
