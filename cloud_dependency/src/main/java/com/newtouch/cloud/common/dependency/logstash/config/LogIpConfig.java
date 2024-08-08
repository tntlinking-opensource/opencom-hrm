package com.newtouch.cloud.common.dependency.logstash.config;

import org.springframework.context.annotation.Configuration;
import ch.qos.logback.core.PropertyDefinerBase;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Slf4j
public class LogIpConfig extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取ip异常", e.toString());
        }
        return ip;
    }
}
