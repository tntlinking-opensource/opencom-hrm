package com.geeke.schedule.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.schedule.dao.ScheduleJobDao;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务管理Service
 * @author
 * @version
 */

@Service("scheduleJobService")
@Transactional(readOnly = true)
public class ScheduleJobService extends CrudService<ScheduleJobDao, ScheduleJob> {}
