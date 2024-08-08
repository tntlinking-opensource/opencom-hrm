package com.newtouch.cloud.common.dependency.remote.config;

import com.newtouch.cloud.common.dependency.remote.feign.DynamicFeignClient;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author FallenRunning(TBH)
 * 远程调用服务的配置
 */
@Configuration
public class RemoteConfig {

    @ConditionalOnMissingBean
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        //配置HTTP超时时间
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(6000);
        httpRequestFactory.setConnectTimeout(6000);
        httpRequestFactory.setReadTimeout(6000);
        return new RestTemplate(httpRequestFactory);
    }

    @ConditionalOnClass(RestTemplate.class)
    @Bean
    public DynamicRestTemplate dynamicRestTemplate(@LoadBalanced RestTemplate restTemplate) {
        return new DynamicRestTemplate(restTemplate);
    }

    @ConditionalOnMissingBean
    @Bean
    public DynamicFeignClient dynamicFeignClient(ApplicationContext applicationContext) {
        return new DynamicFeignClient(applicationContext);
    }
}
