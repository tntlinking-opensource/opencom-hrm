package com.geeke.wf.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.sys.dao.AttachmentDao;
import com.geeke.sys.entity.Attachment;
import com.geeke.wf.dao.WfDraftDao;
import com.geeke.wf.entity.WfDraft;
import com.google.common.collect.Lists;

/**
 * 我的草稿Service
 * @author lys
 * @version 2021-04-27
 */
 
@Service("wfDraftService")
@Transactional(readOnly = true)
public class WfDraftService extends CrudService<WfDraftDao, WfDraft>{

	@Autowired
	AttachmentDao attachmentDao;
	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int delete(WfDraft entity) {
		// 删除草稿附件
		attachmentDao.deleteByBizId(entity.getId());
		
		return super.delete(entity);
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public WfDraft save(WfDraft entity, MultipartFile[] files) throws IOException {
		
		// 删除原来的草稿附件
		attachmentDao.deleteByBizId(entity.getId());

		WfDraft wfDraftTemp = super.save(entity);


		if(Objects.nonNull(files) && files.length > 0){
			// 保存草稿附件
			for(MultipartFile file : files) {
				Attachment attachment = new Attachment(entity.getId(), file);
				attachment.preInsert();
				attachmentDao.insert(attachment);
			}
		}

		return wfDraftTemp;
	}
	
	
	/**
	 * 获取业务对象的文件附件列表
	 * @param bizId
	 * @return
	 */
	public List<Attachment> getAttachments(String bizId) {
		List<Parameter> params = Lists.newArrayList();
		params.add(new Parameter("biz_id", "=", bizId));
		return attachmentDao.listAll(new PageRequest(params));
	}	
	
}