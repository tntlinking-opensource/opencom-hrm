package com.geeke.feign;

import cn.hutool.json.JSONObject;
import com.geeke.feign.fallback.LogServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author FallenRunning(TBH)
 */
@FeignClient(name = "eb-oec-log-system-cloud", path = "/log" ,fallback = LogServiceFallBack.class)
public interface LogService {
    @PostMapping("/before")
    JSONObject before(@RequestBody JSONObject log);

    @PostMapping("/success")
    JSONObject success(@RequestBody JSONObject log);

    @PostMapping("/exception")
    JSONObject exception(@RequestBody JSONObject log);
}
