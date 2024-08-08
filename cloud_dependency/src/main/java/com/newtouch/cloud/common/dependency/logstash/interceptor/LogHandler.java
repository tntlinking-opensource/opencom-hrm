package com.newtouch.cloud.common.dependency.logstash.interceptor;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author FallenRunning(TBH)
 * 日志处理接口 由用户自定义日志处理接口
 * 在本接口三个方法中不能出现异常干扰逻辑
 */
public interface LogHandler {

    /**
     * 发生在方法执行之前
     * @param invocation 请求信息
     */
    default void beforeHandler(MethodInvocation invocation) {
    }

    /**
     * 发生在方法执行之后
     * @param invocation 请求信息
     * @param result 执行结果
     */
    default void successHandler(MethodInvocation invocation, Object result) {
    }

    /**
     * 发生在方法逻辑出现异常
     * @param invocation 请求信息
     * @param th 异常信息
     */
    default void exceptionHandler(MethodInvocation invocation, Throwable th) {
    }
}
