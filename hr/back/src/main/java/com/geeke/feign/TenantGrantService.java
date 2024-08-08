package com.geeke.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author TBH
 */
@FeignClient(name = "cloud-geeke-organisation",contextId = "tenantGrantService", path = "/tenant")
public interface TenantGrantService {

    @GetMapping("/admin/find/{id}")
    JSONObject tenantFindAdmin(@PathVariable("id") String tenantId);
}
