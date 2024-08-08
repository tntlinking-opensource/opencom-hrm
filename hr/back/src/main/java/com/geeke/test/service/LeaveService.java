
package com.geeke.test.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.test.dao.LeaveDao;
import com.geeke.test.entity.Leave;
import com.geeke.wf.service.NoneBizInProcService;
import com.geeke.camundaex.entity.ActResult;
import com.geeke.config.exception.CommonJsonException;
import com.google.common.collect.Maps;
import com.geeke.wf.entity.WfDraft;
import com.geeke.wf.service.WfDraftService;

/**
 * 请假管理Service
 * @author lys
 * @version 2022-01-28
 */
 
@Service("leaveService")
@Transactional(readOnly = true)
public class LeaveService extends NoneBizInProcService<LeaveDao, Leave>{
    



	/**
	 * 获取表单对象的class
	 * @return
	 */
	@Override
	protected Class<Leave> getBizClass() {
		return Leave.class;
	}

}