package com.geeke.noticesend.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.geeke.common.persistence.DataEntity;
import com.geeke.utils.IdGen;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.noticesend.dao.NoticeReceiveSiteDao;
import com.geeke.noticesend.entity.NoticeReceiveSite;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

/**
 * 消息接收站点Service
 * @author ycy
 * @version 2021-11-24
 */
 
@Service("noticeReceiveSiteService")
@Transactional(readOnly = true)
public class NoticeReceiveSiteService extends CrudService<NoticeReceiveSiteDao, NoticeReceiveSite>{


    @Override
    @Transactional(
            readOnly = false
    )
    public List<String> bulkInsert(List<NoticeReceiveSite> entitys) {
        List<String> ids = Lists.newArrayList();
        Iterator var3 = entitys.iterator();

        while(var3.hasNext()) {
            NoticeReceiveSite entity = (NoticeReceiveSite) var3.next();
            entity.setId(IdGen.uuid());
            ids.add(entity.getId());
        }

        this.dao.bulkInsert(entitys);
        return ids;
    }

}