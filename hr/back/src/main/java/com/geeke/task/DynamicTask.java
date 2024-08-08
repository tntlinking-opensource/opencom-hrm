package com.geeke.task;

import com.alibaba.fastjson.JSONObject;

import com.geeke.config.exception.CommonJsonException;
import com.geeke.schedule.common.Constants;
import com.geeke.schedule.common.SpringContextUtil;
import com.geeke.schedule.dao.ScheduleJobDao;
import com.geeke.schedule.entity.ScheduleJob;
import com.geeke.schedule.service.ScheduleJobService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: shenzy
 * @DateTime: 2021/10/22 16:42
 * @Description: TODO
 */
@Api(value = "/schedule/dynamicTask", tags = {"定时任务Controller"})
@RestController
@RequestMapping(value = "/schedule/dynamicTask")
@Slf4j
public class DynamicTask {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    /**
     * 启动定时器
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "scheduleJob", value = "", required = true)
    })
    @ApiOperation(value = "启动定时器", notes = "启动定时器", httpMethod = "POST",response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response =  String.class)
    })
    @PostMapping(value = "startTask")
    public ResponseEntity<JSONObject> startTask(@RequestBody ScheduleJob scheduleJob){
        String id = scheduleJob.getId();
        ScheduleJob finalScheduleJob = scheduleJobService.get(id);
//        if (finalScheduleJob.getIsLock().equals(Constants.TaskLockStatus.LOCKED.getCode())) {
//            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "该任务已禁用，不可启动"));
//        }
        ScheduleUtil.start(() -> {
            try {
                Class<?> proxyClass = Class.forName(finalScheduleJob.getJavaClass());
                Object beanObject = SpringContextUtil.getBean(proxyClass);
                if (StringUtils.isBlank(finalScheduleJob.getParams())) {
                    Method method = proxyClass.getMethod(finalScheduleJob.getJavaMethod());
                    method.invoke(beanObject);
                } else {
                    String[] group = finalScheduleJob.getParams().split(":");
                    if ("String".equals(group[0])) {
                        Method method = proxyClass.getMethod(finalScheduleJob.getJavaMethod(), String.class);
                        method.invoke(beanObject, String.valueOf(group[1]));
                    }
                    if ("Boolean".equals(group[0])) {
                        Method method = proxyClass.getMethod(finalScheduleJob.getJavaMethod(), Boolean.class);
                        method.invoke(beanObject, Boolean.valueOf(group[1]));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException e) {
                String errorMessage = null;
                if (e instanceof InvocationTargetException) {
                    StackTraceElement stackTraceElement = ((InvocationTargetException) e).getTargetException().getStackTrace()[0];
                    errorMessage = String.format("由%s#%s(%s)触发。业务代码报错信息：类型路径[%s]，方法[%s]，错误行号[%s]。",
                            finalScheduleJob.getJavaClass(), finalScheduleJob.getJavaMethod(), (StringUtils.isNotBlank(finalScheduleJob.getParams()) ? finalScheduleJob.getParams() : ""),
                            stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()
                    );
                }
                throw new RuntimeException("定时任务运行异常！异常信息：" + (StringUtils.isNotBlank(errorMessage) ? errorMessage : e.getMessage()));
            }
        }, new CronTrigger(finalScheduleJob.getCron()), finalScheduleJob.getId());
        // 变更 任务状态：运行中
        finalScheduleJob.setStatus(Constants.TaskRunStatus.RUNNING.getCode());
        // 更新任务
        scheduleJobService.save(finalScheduleJob);
        return ResponseEntity.ok(ResultUtil.successJson(String.format("编号：%s，任务名称：%s，启动成功。", id, finalScheduleJob.getName())));
    }

    /**
     * 停止定时任务
     * 定义一个方法实现用于终止定时任务
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "scheduleJob", value = "", required = true)
    })
    @ApiOperation(value = "停止定时任务", notes = "停止定时任务 定义一个方法实现用于终止定时任务", httpMethod = "POST",response = String.class)
    @PostMapping(value = "endTask")
    public ResponseEntity<JSONObject> endTask(@RequestBody ScheduleJob scheduleJob) {
        String id = scheduleJob.getId();
        ScheduleJob finalScheduleJob = scheduleJobService.get(id);
        // 取消任务
        ScheduleUtil.cancel(id);
        // 已暂停
        finalScheduleJob.setStatus(Constants.TaskRunStatus.STOPPED.getCode());
        // 更新任务
        scheduleJobService.save(finalScheduleJob);
        return ResponseEntity.ok(ResultUtil.successJson(id + " : 任务，取消成功"));
    }


    /**
     * 修改定时任务时间：changeCron
     * 改变调度的时间
     * 步骤：
     * 1,先停止定时器
     * 2,在启动定时器
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ScheduleJob", name = "scheduleJob", value = "", required = true)
    })
    @ApiOperation(value = "修改定时任务时间", notes = "修改定时任务时间：changeCron 改变调度的时间 步骤： 1,先停止定时器 2,在启动定时器", httpMethod = "POST",response = String.class)
    @PostMapping(value = "restartTask")
    public ResponseEntity<JSONObject> restartTask(@RequestBody ScheduleJob scheduleJob) {
        // 停止定时器
        endTask(scheduleJob);
        // 开启
        startTask(scheduleJob);
        return ResponseEntity.ok(ResultUtil.successJson(scheduleJob.getId() + " : 任务，重启成功"));
    }

}
