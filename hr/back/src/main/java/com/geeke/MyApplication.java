package com.geeke;

import com.geeke.config.rocketmq.MySink;
import com.geeke.config.rocketmq.MySource;

import com.newtouch.cloud.common.dependency.logstash.config.LogIpConfig;
import com.newtouch.cloud.common.dependency.remote.config.RemoteConfig;
import com.newtouch.cloud.common.dependency.swagger.Swagger2Config;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Import;

/**
 * @author: lys
 * @description: SpringBoot启动类
 * @date: 2017/10/24 11:55
 */
@Import({Swagger2Config.class, LogIpConfig.class , RemoteConfig.class})
@EnableFeignClients({"com.geeke.feign"})
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding({Source.class, MySource.class, Sink.class, MySink.class})
// @MapperScan({"com.geeke.sys.dao", "com.geeke.gen.dao", "com.geeke.common.dao"})
public class MyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MyApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(MyApplication.class);
    }
}
