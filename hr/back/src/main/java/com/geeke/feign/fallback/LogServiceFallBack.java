package com.geeke.feign.fallback;

import cn.hutool.json.JSONObject;
import com.geeke.feign.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author FallenRunning(TBH)
 * 服务降级
 */
@Slf4j
@Component
public class LogServiceFallBack implements LogService {
    @Override
    public JSONObject before(JSONObject jsonLog) {
        log.info("远程调用日志服务失败,已经触发服务降级,日志发生丢失,请检查相关服务!");
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","日志服务下线,请检查相关服务");
        return obj;
    }

    @Override
    public JSONObject success(JSONObject jsonLog) {
        log.info("远程调用日志服务失败,已经触发服务降级,日志发生丢失,请检查相关服务!");
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","日志服务下线,请检查相关服务");
        return obj;
    }

    @Override
    public JSONObject exception(JSONObject jsonLog) {
        log.info("远程调用日志服务失败,已经触发服务降级,日志发生丢失,请检查相关服务!");
        JSONObject obj = new JSONObject();
        obj.set("code",500);
        obj.set("msg","日志服务下线,请检查相关服务");
        return obj;
    }
}
