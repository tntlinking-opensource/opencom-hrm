package com.newtouch.cloud.common.dependency.logstash.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author FallenRunning(TBH)
 * @date 2023/02/08
 * 自定义日志拦截器使用切面的方式，可动态配置aspectj表达式 定制多样化请求
 */
public class LogInterceptor implements MethodInterceptor {

    private final LogHandler logHandler;

    public LogInterceptor(LogHandler logHandler){
        this.logHandler = logHandler;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proceed = null;
        logHandler.beforeHandler(invocation);
        try{
            proceed = invocation.proceed();
        }catch (Throwable th){
            logHandler.exceptionHandler(invocation,th);
            throw th;
        }
        logHandler.successHandler(invocation,proceed);
        return proceed;
    }
}
