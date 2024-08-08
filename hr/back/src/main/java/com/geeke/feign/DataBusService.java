package com.geeke.feign;

import com.alibaba.fastjson.JSONObject;
import com.geeke.feign.fallback.DataBusServiceFallBack;
import com.geeke.feign.fallback.LogServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author FallenRunning(TBH)
 */
@FeignClient(name = "cloud-geeke-devtool", path = "/databus/dataBusBaseInterface" ,fallback = DataBusServiceFallBack.class)
public interface DataBusService {
    @PostMapping("/receive")
    ResponseEntity<JSONObject> receive(
            @RequestHeader String tenantId ,
            @RequestHeader String sourceAppId ,
            @RequestBody JSONObject jsonObject);

    @PostMapping("/initdata")
    ResponseEntity<JSONObject> initdata(@RequestBody JSONObject jsonObject);

}
