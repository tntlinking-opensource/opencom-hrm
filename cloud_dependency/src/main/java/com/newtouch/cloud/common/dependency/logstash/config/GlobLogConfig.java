package com.newtouch.cloud.common.dependency.logstash.config;

import com.newtouch.cloud.common.dependency.logstash.interceptor.DefaultLogHandler;
import com.newtouch.cloud.common.dependency.logstash.interceptor.LogHandler;
import com.newtouch.cloud.common.dependency.logstash.interceptor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

/**
 * @author FallenRunning(TBH)
 * @date 2023/02/08
 * 全局日志配置 使用时 @Import({GlobLogConfig.class})导入本类加入SpringIOC中
 * 定制日志处理方式(保存日志) 请实现LogHandler接口 重写方法
 * 日志处理方式 优先从SpringIOC中加载 如果没加载到就启动默认的处理方式
 * 默认的处理方式 就是不处理
 */
@Slf4j
public class GlobLogConfig {

    @ConditionalOnMissingBean
    @Bean("globLogAdvisor")
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Value("${glob.log.point-cut: execution(public * com.geeke.*.controller..*.*(..)) || execution(public * com.newtouch.*.controller..*.*(..))}") String aspectj, @Autowired(required = false) LogHandler logHandler) {
        if (Objects.isNull(logHandler)) {
            logHandler = new DefaultLogHandler();
        }
        log.info("aspectj表达式:{}", aspectj);
        log.debug("可以配置glob.log.point-cut更改规则!");
        log.info("使用的日志处理器:{}",logHandler.getClass().getName());
        LogInterceptor interceptor = new LogInterceptor(logHandler);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(aspectj);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}