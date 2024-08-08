package com.newtouch.cloud.common.dependency.remote.feign;

import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;

/**
 * @author FallenRunning(TBH)
 * 动态feign调用
 */
public class DynamicFeignClient {

    private final FeignClientBuilder feignClientBuilder;

    public DynamicFeignClient(ApplicationContext appContext) {
        this.feignClientBuilder = new FeignClientBuilder(appContext);
    }

    public <T> T getFeignClient(final Class<T> type, String serviceId) {
        return this.feignClientBuilder.forType(type, serviceId).build();
    }
}
