package com.newtouch.cloud.common.dependency.logstash.interceptor;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author FallenRunning(TBH)
 * 默认的日志处理实现方式,其实就是默认不处理,防止SpringIOC没有找到BeanType而出现NPE
 */
public class DefaultLogHandler implements LogHandler{
    @Override
    public void beforeHandler(MethodInvocation invocation) {
        LogHandler.super.beforeHandler(invocation);
    }

    @Override
    public void successHandler(MethodInvocation invocation, Object result) {
        LogHandler.super.successHandler(invocation, result);
    }

    @Override
    public void exceptionHandler(MethodInvocation invocation, Throwable th) {
        LogHandler.super.exceptionHandler(invocation, th);
    }
}
