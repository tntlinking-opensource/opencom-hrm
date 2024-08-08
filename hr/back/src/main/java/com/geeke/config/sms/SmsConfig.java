package com.geeke.config.sms;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.RetryPolicy;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: huihai.tang
 * @date: 2022/6/2
 * 百度云短信接口配置
 */
@Configuration
public class SmsConfig {

    @Value("${baidu.sms.accessKey}")
    private String accessKeyId;

    @Value("${baidu.sms.secretAccess}")
    private String secretAccessKey;

    @Value("${baidu.sms.endpoint}")
    private String endpoint;

    @Bean("baiduSmsClient")
    public SmsClient smsClient(){
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(accessKeyId,secretAccessKey));
        config.setEndpoint(endpoint);
        // 设置HTTP最大连接数为10
        config.setMaxConnections(10);
        // 设置TCP连接超时为5000毫秒
        config.setConnectionTimeoutInMillis(5000);
        // 设置最大的重试次数为3
        config.setRetryPolicy(RetryPolicy.DEFAULT_RETRY_POLICY);
        // 设置Socket传输数据超时的时间为2000毫秒
        config.setSocketTimeoutInMillis(2000);
        return new SmsClient(config);
    }
}
